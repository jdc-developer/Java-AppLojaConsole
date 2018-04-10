package jdc.loja.test.bo;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.bo.ProdutoBO;
import jdc.loja.exception.Excecao;

public class TestProdutoBO {
	
	@AfterClass
	public static void destroy() {
		ProdutoBO.destroy();
	}
	
	public ProdutoBean cadastro() throws Excecao {
		
		ProdutoBean bean = new ProdutoBean();
		bean.setDescricao("SALGADINHO CHEETOS");
		bean.setPreco(5);
		ProdutoBO.cadastrar(bean);
		
		return bean;
	}
	
	@Test
	public void cadastrar() {
		try {
			ProdutoBean bean = cadastro();
			
			Assert.assertNotEquals(bean.getCodigo(), 0);
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void buscar() {
		try {
			ProdutoBean bean = cadastro();
			
			ProdutoBean busca = ProdutoBO.buscar(bean.getCodigo());
			
			Assert.assertNotNull(busca.getCodigo());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void deletar() {
		try {
			ProdutoBean bean = cadastro();
			
			ProdutoBO.deletar(bean.getCodigo());
			
			Assert.assertNull(ProdutoBO.buscar(bean.getCodigo()));
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void editar() {
		try {
			ProdutoBean bean = cadastro();
			
			bean.setDescricao("SALGADINHO FANDANGOS");
			bean.setPreco(6);
			
			ProdutoBO.editar(bean);
			
			Assert.assertEquals("SALGADINHO FANDANGOS", ProdutoBO.buscar(bean.getCodigo()).getDescricao());
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void count() {
		try {
			long count = ProdutoBO.count();
			Assert.assertNotEquals(count, 0);
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void listar() {
		try {
			List<ProdutoBean> lista = ProdutoBO.listar(0);
			
			for(ProdutoBean bean : lista) {
				Assert.assertNotEquals(bean.getCodigo(), 0);
			}
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void buscarPorNome() {
		try {
			ProdutoBean bean = ProdutoBO.buscarPorNome("CHEETOS");
			
			Assert.assertTrue(bean.getDescricao().contains("CHEETOS"));
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
