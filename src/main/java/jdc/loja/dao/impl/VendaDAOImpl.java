package jdc.loja.dao.impl;

import jdc.loja.beans.VendaBean;
import jdc.loja.dao.VendaDAO;
import jdc.loja.exception.Excecao;

public class VendaDAOImpl extends GenericDAOImpl<VendaBean, Integer> implements VendaDAO{

	public VendaDAOImpl(String pasta) throws Excecao {
		super(pasta);
	}

}
