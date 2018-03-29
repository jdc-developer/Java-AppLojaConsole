package jdc.loja.dao.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.dao.GenericDAO;
import jdc.loja.exception.Excecao;
import jdc.loja.test.dao.TestGenericDAO;
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
	private static final Logger log = LoggerFactory.getLogger(TestGenericDAO.class);
	
	/**
	 * Importante o streaming bean para gerenciar todas as instancias dos readers, streams e writers
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
			//criar pastas caso elas não existam
			path.mkdirs();
		}
		
		String caminho = persistence + "sequence.txt";
		try {
			//Criando o streaming bean
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
			
			//Fechando os streams
			printLocal.close();
			writerLocal.close();
			stream.getBuffReader().close();
			stream.getReader().close();
			stream.getOutput().close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao cadastrar");
		}
		log.info("Cadastrado com sucesso");
	}

	public List<C> listar() throws Excecao {
		// TODO Auto-generated method stub
		return null;
	}

	public C buscar(K codigo) throws Excecao {
		// TODO Auto-generated method stub
		return null;
	}

	public void deletar(K codigo) throws Excecao {
		// TODO Auto-generated method stub
		
	}

	public void editar(C bean) throws Excecao {
		// TODO Auto-generated method stub
		
	}

}
