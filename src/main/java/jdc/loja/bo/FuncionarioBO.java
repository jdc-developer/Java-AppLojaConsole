package jdc.loja.bo;

import org.junit.BeforeClass;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.dao.impl.FuncionarioDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de negócio
 * @author Jorge Do Carmo
 *
 */
public class FuncionarioBO {

	private static FuncionarioDAO dao;
	
	@BeforeClass
	public void inicializar() {
		try {
			dao = new FuncionarioDAOImpl("funcionarios\\");
		} catch (Excecao e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cadastrar(FuncionarioBean bean) throws Excecao {
		if(bean.getSalario() == 0) {
			throw new Excecao("Salário não pode ser 0");
		}
		if(bean.getSalario() > 99999) {
			throw new Excecao("Salário acima do limite permitido");
		}
		if(bean.getNome().length() == 0) {
			throw new Excecao("Nome obrigatório");
		}
		if(bean.getNome().length() > 200) {
			throw new Excecao("Nome não pode exceder o limite de 200 carácteres");
		}
		dao.cadastrar(bean);
	}
	
	public static FuncionarioBean buscar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("Código inválido");
		}
		return dao.buscar(codigo);
	}
	
	public static void deletar(int codigo) throws Excecao {
		if(codigo == 0) {
			throw new Excecao("Código inválido");
		}
		dao.deletar(codigo);
	}
	
	public static void editar(FuncionarioBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Funcionário inválido");
		}
		if(bean.getSalario() == 0) {
			throw new Excecao("Salário não pode ser 0");
		}
		if(bean.getSalario() > 99999) {
			throw new Excecao("Salário acima do limite permitido");
		}
		if(bean.getNome().length() == 0) {
			throw new Excecao("Nome obrigatório");
		}
		if(bean.getNome().length() > 200) {
			throw new Excecao("Nome não pode exceder o limite de 200 carácteres");
		}
		dao.editar(bean);
	}
}
