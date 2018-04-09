package jdc.loja.test.bo;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.bo.FuncionarioBO;
import jdc.loja.exception.Excecao;

public class TestFuncionarioBO {
	
	@AfterClass
	public static void destroy() {
		FuncionarioBO.destroy();
	}
	
	public FuncionarioBean cadastro() throws Excecao {
		FuncionarioBean bean = new FuncionarioBean();
		bean.setNome("Diego Costa");
		bean.setSalario(1500);
		FuncionarioBO.cadastrar(bean);
		
		return bean;
	}
	
	@Test
	public void cadastrar() {
		try {
			FuncionarioBean bean = cadastro();
			
			Assert.assertNotEquals(bean.getCodigo(), 0);
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void buscar() {
		try {
			FuncionarioBean bean = cadastro();
			
			FuncionarioBean busca = FuncionarioBO.buscar(bean.getCodigo());
			
			Assert.assertNotNull(busca.getCodigo());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void deletar() {
		try {
			FuncionarioBean bean = cadastro();
			
			FuncionarioBO.deletar(bean.getCodigo());
			
			Assert.assertNull(FuncionarioBO.buscar(bean.getCodigo()));
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void editar() {
		try {
			FuncionarioBean bean = cadastro();
			
			bean.setNome("João Augusto");
			bean.setSalario(1400);
			
			FuncionarioBO.editar(bean);
			
			Assert.assertEquals("João Augusto", FuncionarioBO.buscar(bean.getCodigo()).getNome());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void count() {
		try {
			long count = FuncionarioBO.count();
			Assert.assertNotEquals(count, 0);
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void listar() {
		try {
			List<FuncionarioBean> lista = FuncionarioBO.listar(0);
			
			for(FuncionarioBean bean : lista) {
				Assert.assertNotEquals(bean.getCodigo(), 0);
			}
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
