package jdc.loja.dao;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.exception.Excecao;

public interface FuncionarioDAO extends GenericDAO<FuncionarioBean, Integer>{

	FuncionarioBean buscarPorNome(String nome) throws Excecao;
}
