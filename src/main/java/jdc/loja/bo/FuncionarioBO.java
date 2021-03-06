package jdc.loja.bo;

import java.util.ArrayList;
import java.util.List;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.dao.impl.FuncionarioDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de neg�cio
 * @author Jorge Do Carmo
 *
 */
public abstract class FuncionarioBO {

	private static FuncionarioDAO dao;
	
	public static FuncionarioDAO getInstance(){
		if (dao == null){
			try {
				dao = new FuncionarioDAOImpl("funcionarios\\");
			} catch (Excecao e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dao;
	}
	
	public static void destroy() {
		getInstance().closeStream();
	}
	
	public static void cadastrar(FuncionarioBean bean) throws Excecao {
		if(bean.getSalario() == 0) {
			throw new Excecao("Sal�rio n�o pode ser 0");
		}
		if(bean.getSalario() > 99999) {
			throw new Excecao("Sal�rio acima do limite permitido");
		}
		if(bean.getNome().length() == 0) {
			throw new Excecao("Nome obrigat�rio");
		}
		if(bean.getNome().length() > 200) {
			throw new Excecao("Nome n�o pode exceder o limite de 200 car�cteres");
		}
		bean.setNome(bean.getNome().toUpperCase());
		getInstance().cadastrar(bean);
	}
	
	public static FuncionarioBean buscar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("C�digo inv�lido");
		}
		return getInstance().buscar(codigo);
	}
	
	public static void deletar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("C�digo inv�lido");
		}
		getInstance().deletar(codigo);
	}
	
	public static void editar(FuncionarioBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Funcion�rio inv�lido");
		}
		if(bean.getSalario() == 0) {
			throw new Excecao("Sal�rio n�o pode ser 0");
		}
		if(bean.getSalario() > 99999) {
			throw new Excecao("Sal�rio acima do limite permitido");
		}
		if(bean.getNome().length() == 0) {
			throw new Excecao("Nome obrigat�rio");
		}
		if(bean.getNome().length() > 200) {
			throw new Excecao("Nome n�o pode exceder o limite de 200 car�cteres");
		}
		bean.setNome(bean.getNome().toUpperCase());
		getInstance().editar(bean);
	}
	
	public static long count() throws Excecao{
		long count = 0;
		count = getInstance().count();
		
		return count;
	}
	
	public static List<FuncionarioBean> listar(int pagina) throws Excecao{
		List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
		lista = getInstance().listar(pagina);
		
		return lista;
	}
	
	public static FuncionarioBean buscarPorNome(String nome) throws Excecao{
		nome = nome.toUpperCase();
		return dao.buscarPorNome(nome);
	}
}
