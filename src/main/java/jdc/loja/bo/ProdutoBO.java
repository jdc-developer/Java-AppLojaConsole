package jdc.loja.bo;

import org.junit.BeforeClass;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.dao.impl.ProdutoDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de negócio
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
			throw new Excecao("Preço não pode ser 0");
		}
		if(bean.getPreco() > 99999) {
			throw new Excecao("Preço acima do limite permitido");
		}
		if(bean.getDescricao().length() == 0) {
			throw new Excecao("Descrição obrigatória");
		}
		if(bean.getDescricao().length() > 200) {
			throw new Excecao("Descrição não pode exceder o limite de 200 carácteres");
		}
		dao.cadastrar(bean);
	}
	
	public static ProdutoBean buscar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("Código inválido");
		}
		return dao.buscar(codigo);
	}
	
	public static void deletar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("Código inválido");
		}
		dao.deletar(codigo);
	}
	
	public static void editar(ProdutoBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Produto inválido");
		}
		if(bean.getPreco() == 0) {
			throw new Excecao("Preço não pode ser 0");
		}
		if(bean.getPreco() > 99999) {
			throw new Excecao("Preço acima do limite permitido");
		}
		if(bean.getDescricao().length() == 0) {
			throw new Excecao("Descrição obrigatória");
		}
		if(bean.getDescricao().length() > 200) {
			throw new Excecao("Descrição não pode exceder o limite de 200 carácteres");
		}
		dao.editar(bean);
	}

}
