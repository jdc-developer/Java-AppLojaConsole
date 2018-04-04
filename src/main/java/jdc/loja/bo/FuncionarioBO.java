package jdc.loja.bo;

import org.junit.BeforeClass;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.dao.impl.FuncionarioDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de neg�cio
 * @author Jorge Do Carmo
 *
 */
public class FuncionarioBO {

	private static FuncionarioDAO dao;
	
	@BeforeClass
	public void inicializar() {
		try {
			dao = new FuncionarioDAOImpl("funcionarios\\");
		} catch (Excecao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		dao.cadastrar(bean);
	}
	
	public static FuncionarioBean buscar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("C�digo inv�lido");
		}
		return dao.buscar(codigo);
	}
	
	public static void deletar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("C�digo inv�lido");
		}
		dao.deletar(codigo);
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
		dao.editar(bean);
	}
}
