package jdc.loja.bo;

import java.util.Calendar;

import jdc.loja.beans.ItemVendaBean;
import jdc.loja.beans.VendaBean;
import jdc.loja.dao.VendaDAO;
import jdc.loja.dao.impl.VendaDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de negócio
 * @author Jorge Do Carmo
 *
 */
public abstract class VendaBO {

	private static VendaDAO dao;
	
	public static VendaDAO getInstance(){
		if (dao == null){
			try {
				dao = new VendaDAOImpl("vendas\\");
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
	
	public static void cadastrar(VendaBean bean) throws Excecao {
		if(bean.getFuncionario() == null) {
			throw new Excecao("Funcionário obrigatório");
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
			throw new Excecao("Valor inválido");
		}
		
		bean.setValor(valor);
		
		bean.setDataVenda(Calendar.getInstance());
		getInstance().cadastrar(bean);
	}
	
	public static VendaBean buscar(int codigo) throws Excecao {
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
	
	public static void editar(VendaBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Venda inválida");
		}
		if(bean.getFuncionario() == null) {
			throw new Excecao("Funcionário obrigatório");
		}
		if(bean.getItens() == null) {
			throw new Excecao("Venda sem itens");
		}
		
		double valor = 0;
		for(ItemVendaBean item : bean.getItens()) {
			valor += item.getProduto().getPreco();
		}
		
		if(valor == 0) {
			throw new Excecao("Valor inválido");
		}
		
		bean.setValor(valor);
		getInstance().editar(bean);
	}
}
