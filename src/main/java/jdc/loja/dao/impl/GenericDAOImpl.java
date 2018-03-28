package jdc.loja.dao.impl;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
public class GenericDAOImpl<C, K> implements GenericDAO<C, K>{

	protected ObjectOutputStream output;
	
	public GenericDAOImpl(ObjectOutputStream output) {
		this.output = output;
	}
	
	public void cadastrar(C bean) throws Excecao {
		try {
			output.writeObject(bean);
		} catch (IOException e) {
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
