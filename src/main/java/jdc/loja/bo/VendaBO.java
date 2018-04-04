package jdc.loja.bo;

import java.util.Calendar;

import org.junit.BeforeClass;

import jdc.loja.beans.ItemVendaBean;
import jdc.loja.beans.VendaBean;
import jdc.loja.dao.VendaDAO;
import jdc.loja.dao.impl.VendaDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de neg�cio
 * @author Jorge Do Carmo
 *
 */
public class VendaBO {

	private static VendaDAO dao;
	
	@BeforeClass
	public void inicializar() {
		try {
			dao = new VendaDAOImpl("vendas\\");
		} catch (Excecao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cadastrar(VendaBean bean) throws Excecao {
		if(bean.getFuncionario() == null) {
			throw new Excecao("Funcion�rio obrigat�rio");
		}
		if(bean.getItens() == null) {
			throw new Excecao("Venda sem itens");
		}
		
		//Valor da venda calculado automaticamente
		double valor = 0;
		for(ItemVendaBean item : bean.getItens()) {
			valor += item.getProduto().getPreco();
		}
		
		if(valor == 0) {
			throw new Excecao("Valor inv�lido");
		}
		
		bean.setValor(valor);
		
		bean.setDataVenda(Calendar.getInstance());
		dao.cadastrar(bean);
	}
	
	public static VendaBean buscar(int codigo) throws Excecao {
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
	
	public static void editar(VendaBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Venda inv�lida");
		}
		if(bean.getFuncionario() == null) {
			throw new Excecao("Funcion�rio obrigat�rio");
		}
		if(bean.getItens() == null) {
			throw new Excecao("Venda sem itens");
		}
		
		double valor = 0;
		for(ItemVendaBean item : bean.getItens()) {
			valor += item.getProduto().getPreco();
		}
		
		if(valor == 0) {
			throw new Excecao("Valor inv�lido");
		}
		
		bean.setValor(valor);
		dao.editar(bean);
	}
}
