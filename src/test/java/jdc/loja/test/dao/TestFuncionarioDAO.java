package jdc.loja.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.dao.impl.FuncionarioDAOImpl;
import jdc.loja.exception.Excecao;

public class TestFuncionarioDAO {

	private static final Logger log = LoggerFactory.getLogger(TestFuncionarioDAO.class);
	private static FuncionarioDAO dao;
	
	@BeforeClass
	public static void inicializar() {
		log.debug("Instanciando DAO de teste");
		
		try {
			dao = new FuncionarioDAOImpl("funcionarios\\");
		} catch (Excecao e) {
			log.error("Erro ao iniciar teste");
			e.printStackTrace();
		}
		log.debug("DAO instanciado");
	}
	
	public FuncionarioBean cadastro() throws Excecao {
		FuncionarioBean bean = new FuncionarioBean();
		bean.setNome("Diego Costa");
		bean.setSalario(1500);
		dao.cadastrar(bean);
		
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
			
			FuncionarioBean busca = dao.buscar(bean.getCodigo());
			
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
			
			dao.deletar(bean.getCodigo());
			
			Assert.assertNull(dao.buscar(bean.getCodigo()));
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
			
			dao.editar(bean);
			
			Assert.assertEquals("João Augusto", dao.buscar(bean.getCodigo()).getNome());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@AfterClass
	public static void destroy() {
		dao.closeStream();
	}
}
