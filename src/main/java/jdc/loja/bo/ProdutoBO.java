package jdc.loja.bo;

import org.junit.BeforeClass;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.dao.impl.ProdutoDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de neg�cio
 * @author Jorge Do Carmo
 *
 */
public class ProdutoBO {
	
	private static ProdutoDAO dao;
	
	@BeforeClass
	public void inicializar() {
		try {
			dao = new ProdutoDAOImpl("produtos\\");
		} catch (Excecao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cadastrar(ProdutoBean bean) throws Excecao {
		if(bean.getPreco() == 0) {
			throw new Excecao("Pre�o n�o pode ser 0");
		}
		if(bean.getPreco() > 99999) {
			throw new Excecao("Pre�o acima do limite permitido");
		}
		if(bean.getDescricao().length() == 0) {
			throw new Excecao("Descri��o obrigat�ria");
		}
		if(bean.getDescricao().length() > 200) {
			throw new Excecao("Descri��o n�o pode exceder o limite de 200 car�cteres");
		}
		dao.cadastrar(bean);
	}
	
	public static ProdutoBean buscar(int codigo) throws Excecao {
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
	
	public static void editar(ProdutoBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Produto inv�lido");
		}
		if(bean.getPreco() == 0) {
			throw new Excecao("Pre�o n�o pode ser 0");
		}
		if(bean.getPreco() > 99999) {
			throw new Excecao("Pre�o acima do limite permitido");
		}
		if(bean.getDescricao().length() == 0) {
			throw new Excecao("Descri��o obrigat�ria");
		}
		if(bean.getDescricao().length() > 200) {
			throw new Excecao("Descri��o n�o pode exceder o limite de 200 car�cteres");
		}
		dao.editar(bean);
	}

}
