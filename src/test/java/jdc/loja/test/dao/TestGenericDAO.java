package jdc.loja.test.dao;

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
public class TestGenericDAO {

	private static final Logger log = LoggerFactory.getLogger(TestGenericDAO.class);
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
	
	@Test
	public void cadastrar() {
		try {
			ProdutoBean bean = new ProdutoBean();
			bean.setDescricao("ProdutoTeste");
			bean.setPreco(15);
			dao.cadastrar(bean);
		} catch (Excecao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
