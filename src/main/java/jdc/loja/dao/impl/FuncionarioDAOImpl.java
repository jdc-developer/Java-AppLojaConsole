package jdc.loja.dao.impl;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.exception.Excecao;

public class FuncionarioDAOImpl extends GenericDAOImpl<FuncionarioBean, Integer> implements FuncionarioDAO{

	public FuncionarioDAOImpl(String pasta) throws Excecao {
		super(pasta);
	}

}
