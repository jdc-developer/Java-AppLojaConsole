package jdc.loja.dao;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.exception.Excecao;

public interface ProdutoDAO extends GenericDAO<ProdutoBean, Integer>{

	ProdutoBean buscarPorNome(String nome) throws Excecao;
}
