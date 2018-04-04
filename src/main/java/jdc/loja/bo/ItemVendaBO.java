package jdc.loja.bo;

import org.junit.BeforeClass;

import jdc.loja.beans.ItemVendaBean;
import jdc.loja.dao.ItemVendaDAO;
import jdc.loja.dao.impl.ItemVendaDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de negócio
 * @author Jorge Do Carmo
 *
 */
public class ItemVendaBO {

	private static ItemVendaDAO dao;
	
	@BeforeClass
	public void inicializar() {
		try {
			dao = new ItemVendaDAOImpl("itemvendas\\");
		} catch (Excecao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cadastrar(ItemVendaBean bean) throws Excecao {
		if(bean.getQuantidade() == 0) {
			throw new Excecao("Quantidade não pode ser 0");
		}
		if(bean.getQuantidade() > 100) {
			throw new Excecao("Quantidade acima do limite permitido");
		}
		if(bean.getProduto() == null) {
			throw new Excecao("Item sem produto");
		}
		if(bean.getVenda() == null) {
			throw new Excecao("Item sem venda");
		}
		dao.cadastrar(bean);
	}
	
	public static ItemVendaBean buscar(int codigo) throws Excecao {
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
	
	public static void editar(ItemVendaBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Item inválido");
		}
		if(bean.getQuantidade() == 0) {
			throw new Excecao("Quantidade não pode ser 0");
		}
		if(bean.getQuantidade() > 100) {
			throw new Excecao("Quantidade acima do limite permitido");
		}
		if(bean.getProduto() == null) {
			throw new Excecao("Item sem produto");
		}
		if(bean.getVenda() == null) {
			throw new Excecao("Item sem venda");
		}
		dao.editar(bean);
	}
}
