package jdc.loja.bo;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.dao.impl.ProdutoDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de neg�cio
 * @author Jorge Do Carmo
 *
 */
public abstract class ProdutoBO {
	
	private static ProdutoDAO dao;
	
	public static ProdutoDAO getInstance(){
		if (dao == null){
			try {
				dao = new ProdutoDAOImpl("produtos\\");
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
		getInstance().cadastrar(bean);
	}
	
	public static ProdutoBean buscar(int codigo) throws Excecao {
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
		getInstance().editar(bean);
	}

}
