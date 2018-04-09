package jdc.loja.bo;

import java.util.ArrayList;
import java.util.List;

import jdc.loja.beans.ItemVendaBean;
import jdc.loja.dao.ItemVendaDAO;
import jdc.loja.dao.impl.ItemVendaDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de negócio
 * @author Jorge Do Carmo
 *
 */
public abstract class ItemVendaBO {

	private static ItemVendaDAO dao;
	
	public static ItemVendaDAO getInstance(){
		if (dao == null){
			try {
				dao = new ItemVendaDAOImpl("itemvendas\\");
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
		getInstance().cadastrar(bean);
	}
	
	public static ItemVendaBean buscar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("Código inválido");
		}
		return getInstance().buscar(codigo);
	}
	
	public static void deletar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("Código inválido");
		}
		getInstance().deletar(codigo);
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
		getInstance().editar(bean);
	}
	
	public static long count() throws Excecao{
		long count = 0;
		count = getInstance().count();
		
		return count;
	}
	
	public static List<ItemVendaBean> listar(int pagina) throws Excecao{
		List<ItemVendaBean> lista = new ArrayList<ItemVendaBean>();
		lista = getInstance().listar(pagina);
		
		return lista;
	}
}
