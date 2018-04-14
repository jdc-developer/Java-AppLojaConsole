package jdc.loja.dao;

import java.util.List;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.exception.Excecao;

public interface ProdutoDAO extends GenericDAO<ProdutoBean, Integer>{

	List<ProdutoBean> buscarPorNome(String nome) throws Excecao;
}
