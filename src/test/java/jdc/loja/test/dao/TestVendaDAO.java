package jdc.loja.test.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.beans.ItemVendaBean;
import jdc.loja.beans.ProdutoBean;
import jdc.loja.beans.VendaBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.dao.ItemVendaDAO;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.dao.VendaDAO;
import jdc.loja.dao.impl.FuncionarioDAOImpl;
import jdc.loja.dao.impl.ItemVendaDAOImpl;
import jdc.loja.dao.impl.ProdutoDAOImpl;
import jdc.loja.dao.impl.VendaDAOImpl;
import jdc.loja.exception.Excecao;

public class TestVendaDAO {
	
	private static final Logger log = LoggerFactory.getLogger(TestVendaDAO.class);
	private static ItemVendaDAO dao;
	private static ProdutoDAO daoProd;
	private static VendaDAO daoVenda;
	private static FuncionarioDAO daoFunc;
	
	@BeforeClass
	public static void inicializar() {
		log.debug("Instanciando DAO de teste");
		
		try {
			dao = new ItemVendaDAOImpl("itemvendas\\");
			daoProd = new ProdutoDAOImpl("produtos\\");
			daoVenda = new VendaDAOImpl("vendas\\");
			daoFunc = new FuncionarioDAOImpl("funcionarios\\");
		} catch (Excecao e) {
			log.error("Erro ao iniciar teste");
			e.printStackTrace();
		}
		log.debug("DAO instanciado");
	}
	
	@AfterClass
	public static void destroy() {
		dao.closeStream();
		daoProd.closeStream();
		daoVenda.closeStream();
		daoFunc.closeStream();
	}

	public ItemVendaBean cadastro() throws Excecao {
		
		ItemVendaBean bean = new ItemVendaBean();
		bean.setProduto(cadastroProd());
		bean.setQuantidade(2);
		
		List<ItemVendaBean> itens = new ArrayList<ItemVendaBean>();
		itens.add(bean);
		bean.setVenda(cadastroVenda(itens));
		
		dao.cadastrar(bean);
		
		return bean;
	}
	
	public ProdutoBean cadastroProd() throws Excecao {
		ProdutoBean bean = new ProdutoBean();
		bean.setDescricao("CHOCOLATE SUFLAIR");
		bean.setPreco(3);
		
		daoProd.cadastrar(bean);
		
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
		
		daoVenda.cadastrar(bean);
		
		return bean;
	}
	
	public FuncionarioBean cadastroFuncionario() throws Excecao {
		FuncionarioBean bean = new FuncionarioBean();
		bean.setNome("GUSTAVO ROCHA");
		bean.setSalario(2000);
		
		daoFunc.cadastrar(bean);
		
		return bean;
	}
	
	@Test
	public void cadastrar() {
		try {
			List<ItemVendaBean> itens = new ArrayList<ItemVendaBean>();
			itens.add(cadastro());
			VendaBean bean = cadastroVenda(itens);
			
			daoVenda.cadastrar(bean);
			
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
			
			VendaBean busca = daoVenda.buscar(bean.getCodigo());
			
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
			
			daoVenda.deletar(bean.getCodigo());
			
			Assert.assertNull(daoVenda.buscar(bean.getCodigo()));
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
			
			daoVenda.editar(bean);
			
			Assert.assertEquals(data, daoVenda.buscar(bean.getCodigo()).getDataVenda());
		} catch (Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void count() {
		try {
			long count = daoVenda.count();
			Assert.assertNotEquals(count, 0);
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void listar() {
		try {
			List<VendaBean> lista = daoVenda.listar(0);
			
			for(VendaBean bean : lista) {
				Assert.assertNotEquals(bean.getCodigo(), 0);
			}
		} catch(Excecao e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
