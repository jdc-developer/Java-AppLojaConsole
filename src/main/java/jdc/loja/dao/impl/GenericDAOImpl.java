package jdc.loja.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.List;

import jdc.loja.dao.GenericDAO;
import jdc.loja.exception.Excecao;

/**
 * Implementação do DAO Generico
 * @author Jorge Do Carmo
 *
 * @param <C> Classe generica
 * @param <K> Tipo de dado da chave primaria
 * @see GenericDAO
 */
public abstract class GenericDAOImpl<C, K> implements GenericDAO<C, K>{

	private static final String persistence = System.getProperty("user.dir") + "/app-resources/";
	protected ObjectOutputStream output;
	private BufferedReader buffReader;
	private String pasta;
	
	/**
	 * Inicializando a classe com a pasta de destino da entidade em questão
	 * Importante a inicialização do arquivo .txt de sequence.
	 * @param pasta
	 * @throws Excecao
	 */
	public GenericDAOImpl(String pasta) throws Excecao {
		String caminho = persistence + pasta + "sequence.txt";
		FileReader reader = null;
		try {
			try {
				reader = new FileReader(caminho);
			} catch (FileNotFoundException e) {
				FileWriter writer = new FileWriter(caminho);
				PrintWriter dados = new PrintWriter(writer);
				dados.println(1);
				dados.close();
				writer.close();
				reader = new FileReader(caminho);
			}
			finally {
				buffReader = new BufferedReader(reader);
				this.pasta = pasta;
				reader.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new Excecao("Erro ao criar diretório de arquivos");
		}
	}
	
	/**
	 * Ao cadastrar importante o resgate do dado do arquivo de sequence
	 * @see GenericDAOImpl
	 */
	public void cadastrar(C bean) throws Excecao {
		try {
			output = new ObjectOutputStream(new FileOutputStream(persistence + pasta + bean.getClass().toString() + buffReader.readLine()));
			bean.getClass().getDeclaredMethod("setCodigo", bean.getClass());
			output.writeObject(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Excecao("Erro ao cadastrar");
		}
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
