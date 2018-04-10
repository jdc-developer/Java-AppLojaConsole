package jdc.loja.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.exception.Excecao;

public class FuncionarioDAOImpl extends GenericDAOImpl<FuncionarioBean, Integer> implements FuncionarioDAO{

	private static final Logger log = LoggerFactory.getLogger(FuncionarioDAOImpl.class);
	
	public FuncionarioDAOImpl(String pasta) throws Excecao {
		super(pasta);
	}

	/**
	 * Busca por parte do nome customizada
	 */
	public FuncionarioBean buscarPorNome(String nome) throws Excecao {
		log.info("Buscando...");
		
		FuncionarioBean bean = null;

	    try {
	    	
	    	File f = new File(persistence);
	    	File [] files = f.listFiles();
	    	
	    	for(int i = 0; i < files.length; i++) {
	    		if(!files[i].getPath().endsWith("sequence.txt")) {
	    			stream.setInput(new ObjectInputStream(new FileInputStream(files[i])));
	    			FuncionarioBean busca = (FuncionarioBean) stream.getInput().readObject();

					if(busca.getNome().contains(nome)) {
						bean = busca;
					}
	    		}
	    	}
	    	
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Excecao("Erro ao buscar");
	    }
	    
	    if(bean == null) {
	    	log.warn("Não encontrado");
	    } else {
	    	log.info("Sucesso");
	    }
	    
		return bean;
	}
}
