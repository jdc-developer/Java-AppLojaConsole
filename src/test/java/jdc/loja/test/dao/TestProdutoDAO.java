package jdc.loja.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.dao.impl.ProdutoDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe para testar o GenericDAO
 * @author Jorge Do Carmo
 *
 */
public class TestProdutoDAO {

	private static final Logger log = LoggerFactory.getLogger(TestProdutoDAO.class);
	private static ProdutoDAO dao;
	
	@BeforeClass
	public static void inicializar() {
		log.debug("Instanciando DAO de teste");
		
		try {
			dao = new ProdutoDAOImpl("produtos\\");
		} catch (Excecao e) {
			log.error("Erro ao iniciar teste");
			e.printStackTrace();
		}
		log.debug("DAO instanciado");
	}
	
	@AfterClass
	public static void destroy() {
		dao.closeStream();
	}
	
	public ProdutoBean cadastro() throws Excecao {
		
		ProdutoBean bean = new ProdutoBean();
		bean.setDescricao("Salgadinho Cheetos");
		bean.setPreco(5);
		dao.cadastrar(bean);
		
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
			
			ProdutoBean busca = dao.buscar(bean.getCodigo());
			
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
			ProdutoBean bean = cadastro();
			
			bean.setDescricao("Salgadinho Fandangos");
			bean.setPreco(6);
			
			dao.editar(bean);
			
			Assert.assertEquals("Salgadinho Fandangos", dao.buscar(bean.getCodigo()).getDescricao());
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void count() {
		try {
			long count = dao.count();
			System.out.println(count);
		} catch(Excecao e) {
			
		}
	}
}
