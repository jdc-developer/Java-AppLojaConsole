package jdc.loja.dao;

import jdc.loja.dao.impl.GenericDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Interface do DAO Generico
 * @author Jorge Do Carmo
 *
 * @param <C> classe generica
 * @param <K> tipo de dado da chave primaria da classe
 * @see GenericDAOImpl
 */
public interface GenericDAO<C, K> {

	void cadastrar(C bean) throws Excecao;
	C buscar(K codigo) throws Excecao;
	void deletar(K codigo) throws Excecao;
	void editar(C bean) throws Excecao;
	void closeStream();
}
