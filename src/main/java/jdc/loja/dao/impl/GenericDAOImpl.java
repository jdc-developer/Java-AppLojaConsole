package jdc.loja.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.dao.GenericDAO;
import jdc.loja.exception.Excecao;
import jdc.loja.util.StreamingUtil;

/**
 * Implementação do DAO Generico
 * @author Jorge Do Carmo
 *
 * @param <C> Classe generica
 * @param <K> Tipo de dado da chave primaria
 * @see GenericDAO
 */
public abstract class GenericDAOImpl<C , K> implements GenericDAO<C, K>{

	private String persistence = System.getProperty("user.dir") + "\\app-resources\\";
	private static final Logger log = LoggerFactory.getLogger(GenericDAOImpl.class);
	private static int MAX_FILES = 10000;
	
	/**
	 * Importante o stream para gerenciar todas as instancias dos readers, streams e writers
	 */
	private StreamingUtil stream;
	
	/**
	 * Inicializando a classe com a pasta de destino da entidade em questão
	 * Importante a inicialização do arquivo .txt de sequence.
	 * @param pasta
	 * @throws Excecao
	 */
	public GenericDAOImpl(String pasta) throws Excecao {
		log.debug("Iniciando operação");
		
		persistence += pasta;
		
		//Verificando se as pastas existem
		File path = new File(persistence);
		if (!path.exists()) {
			path.mkdirs();
		}
		
		String caminho = persistence + "sequence.txt";
		try {
			//Criando o stream
			stream = new StreamingUtil(caminho);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao criar diretório de arquivos");
		}
	}
	
	/**
	 * Ao cadastrar importante o resgate do dado do arquivo de sequence
	 * @see GenericDAOImpl
	 * @param classe generica
	 */
	public void cadastrar(C bean) throws Excecao {
		try {
			log.info("Cadastrando...");
			
			String beanClass = bean.getClass().toString();
			String beanAmigavel = beanClass.substring(beanClass.lastIndexOf(".") + 1, beanClass.length());
			String sequence = stream.getBuffReader().readLine();
			
			stream.resetBuff(persistence);
			
			String path = persistence + beanAmigavel + sequence;
			stream.setOutput(new ObjectOutputStream(new FileOutputStream(path)));
			
			Method metodo = getBeanMethod("setCodigo", int.class);
			
			//Invocando o método setando chave primária
			int id = Integer.parseInt(sequence);
			metodo.invoke(bean, id);
			
			//Commit
			stream.getOutput().writeObject(bean);
			FileWriter writerLocal = new FileWriter(persistence + "sequence.txt");
			PrintWriter printLocal = new PrintWriter(writerLocal);
			printLocal.println(id + 1);
			
			printLocal.close();
			writerLocal.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao cadastrar");
		}
		log.info("Cadastrado com sucesso");
	}

	@SuppressWarnings("unchecked")
	public C buscar(K codigo) throws Excecao {
		C bean = null;
		try {
			log.info("Buscando...");
			
			String path = getPath(codigo);
			stream.setInput(new ObjectInputStream(new FileInputStream(path)));
			bean = (C) stream.getInput().readObject();
			
		} catch (FileNotFoundException e1) {
			log.warn("Não encontrado");
			return null;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao realizar busca");
		} 
		
		log.info("Encontrado");
		return bean;
	}

	public void deletar(K codigo) throws Excecao {
		try {
			log.info("Deletando...");
			
			String path = getPath(codigo);
			stream.destroy();
			Path caminho = Paths.get(path);
			Files.delete(caminho);
			
			stream.resetBuff(persistence);
		} catch(Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao deletar");
		}
		log.info("Deletado com sucesso");
	}

	@SuppressWarnings("unchecked")
	public void editar(C bean) throws Excecao {
		try {
			log.info("Editando");
			
			Method metodo = getBeanMethod("getCodigo", null);
			K codigo = (K) metodo.invoke(bean);
			String path = getPath(codigo);
			
			stream.destroy();
			Path caminho = Paths.get(path);
			Files.delete(caminho);
			
			stream.setOutput(new ObjectOutputStream(new FileOutputStream(path)));
			stream.getOutput().writeObject(bean);
			stream.resetBuff(persistence);
		} catch(Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao editar");
		}
		log.info("Editado com sucesso");
	}
	
	public long count() throws Excecao{
		
		log.debug("Contando arquivos...");
		
		Path dir = Paths.get(persistence);
	    int i = 0;

	    try {
	    	DirectoryStream<Path> str = Files.newDirectoryStream(dir);
	        for (Path p : str) {
	            if(!p.endsWith(Paths.get("sequence.txt"))) {
	            	i++;
	            }
	            if (i > MAX_FILES) {
	                return MAX_FILES;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Excecao("Erro ao contar arquivos");
	    }
	    log.debug("Sucesso");
		return i;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<C> listar(Integer pagina) throws Excecao {
		log.info("Listando...");
	    
	    List<C> lista = new ArrayList<C>();
	    pagina = pagina * 10;

	    try {
	    	
	    	File f = new File(persistence);
	    	File [] files = f.listFiles();
	    	Arrays.sort( files, new Comparator()
	    	{
	    	    public int compare(Object o1, Object o2) {

	    	        if (((File)o1).lastModified() > ((File)o2).lastModified()) {
	    	            return -1;
	    	        } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
	    	            return +1;
	    	        } else {
	    	            return 0;
	    	        }
	    	    }

	    	}); 
	    	
	    	for(int i = 0; i < 10; i++) {
	    		stream.setInput(new ObjectInputStream(new FileInputStream(files[pagina])));
				C bean = (C) stream.getInput().readObject();
				lista.add(bean);
				
				pagina++;
	    	}
	    	
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Excecao("Erro ao listar");
	    }
	    
	    log.debug("Sucesso");
		return lista;
	}
	
	/**
	 * Método para retornar caminho de um bean generico através do código (Util para busca e edição)
	 * @param codigo
	 * @return
	 * @see buscar(), editar()
	 */
	public String getPath(K codigo) {
		//Buscando string de bean amigavel
		String beanName = persistence;
		int ind = beanName.lastIndexOf("\\");
		if( ind>=0 ) {
		    beanName = new StringBuilder(beanName).replace(ind, ind+1,"").toString();
		    beanName = beanName.substring(beanName.lastIndexOf("\\") + 1, beanName.length()); 
		}
		int ind2 = beanName.lastIndexOf("s");
		if( ind2>=0 ) {
		    beanName = new StringBuilder(beanName).replace(ind2, ind2+1,"").toString();
		}
		beanName =  Character.toUpperCase(beanName.charAt(0)) + beanName.substring(1);
		
		return persistence + beanName + "Bean" + codigo;
	}
	
	/**
	 * Método que retorna um método definido em uma classe genérica
	 * @param metodo
	 * @param type
	 * @return objeto método
	 * @throws Exception
	 * @see cadastrar(), editar()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Method getBeanMethod(String metodo, Class type) throws Exception {
		//Buscar o metodo setCodigo da classe genérica
		Class<C> classe = 
				(Class<C>) ((ParameterizedType)
						getClass().getGenericSuperclass())
							.getActualTypeArguments()[0];
		
		if(type == null) {
			return classe.getMethod(metodo);
		}
		return classe.getMethod(metodo, type);
	}
	
	public void closeStream() {
		try {
			stream.destroy();
		} catch (Excecao e) {
			e.printStackTrace();
		}
	}
}
