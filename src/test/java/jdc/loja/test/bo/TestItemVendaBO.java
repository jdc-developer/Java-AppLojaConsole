package jdc.loja.test.bo;

import java.util.ArrayList;
import java.util.Calendar;
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

public class TestItemVendaBO {
	
	@AfterClass
	public static void destroy() {
		ItemVendaBO.destroy();
		ProdutoBO.destroy();
		FuncionarioBO.destroy();
		VendaBO.destroy();
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
			ItemVendaBean bean = cadastro();
			
			Assert.assertNotEquals(bean.getCodigo(), 0);
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void buscar() {
		try {
			ItemVendaBean bean = cadastro();
			
			ItemVendaBean busca = ItemVendaBO.buscar(bean.getCodigo());
			
			Assert.assertNotNull(busca.getCodigo());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void deletar() {
		try {
			ItemVendaBean bean = cadastro();
			
			ItemVendaBO.deletar(bean.getCodigo());
			
			Assert.assertNull(ItemVendaBO.buscar(bean.getCodigo()));
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void editar() {
		try {
			ItemVendaBean bean = cadastro();
			
			bean.setQuantidade(8);
			
			ItemVendaBO.editar(bean);
			
			Assert.assertEquals(8, ItemVendaBO.buscar(bean.getCodigo()).getQuantidade());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void count() {
		try {
			long count = ItemVendaBO.count();
			Assert.assertNotEquals(count, 0);
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void listar() {
		try {
			List<ItemVendaBean> lista = ItemVendaBO.listar(0);
			
			for(ItemVendaBean bean : lista) {
				Assert.assertNotEquals(bean.getCodigo(), 0);
			}
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
