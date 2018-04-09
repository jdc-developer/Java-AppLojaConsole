package jdc.loja.bo;

import java.util.ArrayList;
import java.util.List;

import jdc.loja.beans.FuncionarioBean;
import jdc.loja.dao.FuncionarioDAO;
import jdc.loja.dao.impl.FuncionarioDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de negócio
 * @author Jorge Do Carmo
 *
 */
public abstract class FuncionarioBO {

	private static FuncionarioDAO dao;
	
	public static FuncionarioDAO getInstance(){
		if (dao == null){
			try {
				dao = new FuncionarioDAOImpl("funcionarios\\");
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
		getInstance().cadastrar(bean);
	}
	
	public static FuncionarioBean buscar(int codigo) throws Excecao {
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
		getInstance().editar(bean);
	}
	
	public static long count() throws Excecao{
		long count = 0;
		count = getInstance().count();
		
		return count;
	}
	
	public static List<FuncionarioBean> listar(int pagina) throws Excecao{
		List<FuncionarioBean> lista = new ArrayList<FuncionarioBean>();
		lista = getInstance().listar(pagina);
		
		return lista;
	}
}
