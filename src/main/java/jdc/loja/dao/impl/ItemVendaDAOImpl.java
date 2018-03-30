package jdc.loja.dao.impl;

import jdc.loja.beans.ItemVendaBean;
import jdc.loja.dao.ItemVendaDAO;
import jdc.loja.exception.Excecao;

public class ItemVendaDAOImpl extends GenericDAOImpl<ItemVendaBean, Integer> implements ItemVendaDAO{

	public ItemVendaDAOImpl(String pasta) throws Excecao {
		super(pasta);
	}
}
