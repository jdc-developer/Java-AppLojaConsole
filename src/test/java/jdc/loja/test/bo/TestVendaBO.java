package jdc.loja.test.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.beans.ItemVendaBean;
import jdc.loja.beans.ProdutoBean;
import jdc.loja.beans.VendaBean;
import jdc.loja.bo.FuncionarioBO;
import jdc.loja.bo.ItemVendaBO;
import jdc.loja.bo.ProdutoBO;
import jdc.loja.bo.VendaBO;
import jdc.loja.exception.Excecao;

public class TestVendaBO {
	
	@AfterClass
	public static void destroy() {
		VendaBO.destroy();
		ProdutoBO.destroy();
		FuncionarioBO.destroy();
		ItemVendaBO.destroy();
	}

	public ItemVendaBean cadastro() throws Excecao {
		
		ItemVendaBean bean = new ItemVendaBean();
		bean.setProduto(cadastroProd());
		bean.setQuantidade(2);
		
		List<ItemVendaBean> itens = new ArrayList<ItemVendaBean>();
		itens.add(bean);
		bean.setVenda(cadastroVenda(itens));
		
		ItemVendaBO.cadastrar(bean);
		
		return bean;
	}
	
	public ProdutoBean cadastroProd() throws Excecao {
		ProdutoBean bean = new ProdutoBean();
		bean.setDescricao("Chocolate Suflair");
		bean.setPreco(3);
		
		ProdutoBO.cadastrar(bean);
		
		return bean;
	}
	
	public VendaBean cadastroVenda(List<ItemVendaBean> itens) throws Excecao {
		VendaBean bean = new VendaBean();
		bean.setDataVenda(Calendar.getInstance());
		bean.setItens(itens);
		bean.setFuncionario(cadastroFuncionario());
		
		float valor = 0;
		
		for(ItemVendaBean item : itens) {
			valor += item.getProduto().getPreco();
		}
		
		bean.setValor(valor);
		
		VendaBO.cadastrar(bean);
		
		return bean;
	}
	
	public FuncionarioBean cadastroFuncionario() throws Excecao {
		FuncionarioBean bean = new FuncionarioBean();
		bean.setNome("Gustavo Rocha");
		bean.setSalario(2000);
		
		FuncionarioBO.cadastrar(bean);
		
		return bean;
	}
	
	@Test
	public void cadastrar() {
		try {
			List<ItemVendaBean> itens = new ArrayList<ItemVendaBean>();
			itens.add(cadastro());
			VendaBean bean = cadastroVenda(itens);
			
			VendaBO.cadastrar(bean);
			
			Assert.assertNotEquals(bean.getCodigo(), 0);
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void buscar() {
		try {
			List<ItemVendaBean> itens = new ArrayList<ItemVendaBean>();
			itens.add(cadastro());
			VendaBean bean = cadastroVenda(itens);
			
			VendaBean busca = VendaBO.buscar(bean.getCodigo());
			
			Assert.assertNotNull(busca.getCodigo());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void deletar() {
		try {
			List<ItemVendaBean> itens = new ArrayList<ItemVendaBean>();
			itens.add(cadastro());
			VendaBean bean = cadastroVenda(itens);
			
			VendaBO.deletar(bean.getCodigo());
			
			Assert.assertNull(VendaBO.buscar(bean.getCodigo()));
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void editar() {
		try {
			List<ItemVendaBean> itens = new ArrayList<ItemVendaBean>();
			itens.add(cadastro());
			VendaBean bean = cadastroVenda(itens);
			
			GregorianCalendar data = new GregorianCalendar(1955, Calendar.JANUARY, 5);
			
			bean.setDataVenda(data);
			
			VendaBO.editar(bean);
			
			Assert.assertEquals(data, VendaBO.buscar(bean.getCodigo()).getDataVenda());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
