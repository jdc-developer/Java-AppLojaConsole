package jdc.loja.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.exception.Excecao;

public class ProdutoDAOImpl extends GenericDAOImpl<ProdutoBean, Integer> implements ProdutoDAO{

	private static final Logger log = LoggerFactory.getLogger(ProdutoDAOImpl.class);
	
	public ProdutoDAOImpl(String pasta) throws Excecao {
		super(pasta);
	}

	/**
	 * Busca por parte do nome customizada
	 */
	public List<ProdutoBean> buscarPorNome(String nome) throws Excecao {
		log.info("Buscando...");
		
		List<ProdutoBean> lista = new ArrayList<ProdutoBean>();

	    try {
	    	
	    	File f = new File(persistence);
	    	File [] files = f.listFiles();
	    	
	    	for(int i = 0; i < files.length; i++) {
	    		if(!files[i].getPath().endsWith("sequence.txt")) {
	    			stream.setInput(new ObjectInputStream(new FileInputStream(files[i])));
	    			ProdutoBean busca = (ProdutoBean) stream.getInput().readObject();

					if(busca.getDescricao().contains(nome)) {
						lista.add(busca);
					}
	    		}
	    	}
	    	
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Excecao("Erro ao buscar");
	    }
	    
	    if(lista.isEmpty()) {
	    	log.warn("Não encontrado");
	    } else {
	    	log.info("Sucesso");
	    }
	    
		return lista;
	}
}
