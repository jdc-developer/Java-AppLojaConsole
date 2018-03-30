package jdc.loja.dao.impl;

import java.util.List;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.exception.Excecao;

public class ProdutoDAOImpl extends GenericDAOImpl<ProdutoBean, Integer> implements ProdutoDAO{

	public ProdutoDAOImpl(String pasta) throws Excecao {
		super(pasta);
		// TODO Auto-generated constructor stub
	}

}
