package jdc.loja.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.dao.GenericDAO;
import jdc.loja.exception.Excecao;
import jdc.loja.test.dao.TestProdutoDAO;
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
	private static final Logger log = LoggerFactory.getLogger(TestProdutoDAO.class);
	
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
		log.info("Iniciando operação");
		
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
	@SuppressWarnings("unchecked")
	public void cadastrar(C bean) throws Excecao {
		try {
			log.info("Cadastrando...");
			
			String beanClass = bean.getClass().toString();
			String beanAmigavel = beanClass.substring(beanClass.lastIndexOf(".") + 1, beanClass.length());
			String sequence = stream.getBuffReader().readLine();
			
			stream.resetBuff(persistence);
			
			String path = persistence + beanAmigavel + sequence;
			stream.setOutput(new ObjectOutputStream(new FileOutputStream(path)));
			
			//Buscar o metodo setCodigo da classe genérica
			Class<C> classe = 
					(Class<C>) ((ParameterizedType)
							getClass().getGenericSuperclass())
								.getActualTypeArguments()[0];
			
			Method metodo = classe.getMethod("setCodigo", int.class);
			
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
			beanName = Character.toUpperCase(beanName.charAt(0)) + beanName.substring(1);
			
			String path = persistence + beanName + "Bean" + codigo;
			stream.setInput(new ObjectInputStream(new FileInputStream(path)));
			bean = (C) stream.getInput().readObject();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao realizar busca");
		}
		
		if(bean == null) {
			throw new Excecao("Não encontrado");
		}
		
		log.info("Encontrado");
		return bean;
	}

	public void deletar(K codigo) throws Excecao {
		// TODO Auto-generated method stub
		
	}

	public void editar(C bean) throws Excecao {
		// TODO Auto-generated method stub
		
	}
	
	public void closeStream() {
		try {
			stream.destroy();
		} catch (Excecao e) {
			e.printStackTrace();
		}
	}
}
