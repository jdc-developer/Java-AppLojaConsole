package jdc.loja.bo;

import java.util.ArrayList;
import java.util.List;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.dao.ProdutoDAO;
import jdc.loja.dao.impl.ProdutoDAOImpl;
import jdc.loja.exception.Excecao;

/**
 * Classe de tratamento de regras de negócio
 * @author Jorge Do Carmo
 *
 */
public abstract class ProdutoBO {
	
	private static ProdutoDAO dao;
	
	public static ProdutoDAO getInstance(){
		if (dao == null){
			try {
				dao = new ProdutoDAOImpl("produtos\\");
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
	
	public static void cadastrar(ProdutoBean bean) throws Excecao {
		if(bean.getPreco() == 0) {
			throw new Excecao("Preço não pode ser 0");
		}
		if(bean.getPreco() > 99999) {
			throw new Excecao("Preço acima do limite permitido");
		}
		if(bean.getDescricao().length() == 0) {
			throw new Excecao("Descrição obrigatória");
		}
		if(bean.getDescricao().length() > 200) {
			throw new Excecao("Descrição não pode exceder o limite de 200 carácteres");
		}
		bean.setDescricao(bean.getDescricao().toUpperCase());
		getInstance().cadastrar(bean);
	}
	
	public static ProdutoBean buscar(int codigo) throws Excecao {
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
	
	public static void editar(ProdutoBean bean) throws Excecao {
		if(bean.getCodigo() == 0) {
			throw new Excecao("Produto inválido");
		}
		if(bean.getPreco() == 0) {
			throw new Excecao("Preço não pode ser 0");
		}
		if(bean.getPreco() > 99999) {
			throw new Excecao("Preço acima do limite permitido");
		}
		if(bean.getDescricao().length() == 0) {
			throw new Excecao("Descrição obrigatória");
		}
		if(bean.getDescricao().length() > 200) {
			throw new Excecao("Descrição não pode exceder o limite de 200 carácteres");
		}
		bean.setDescricao(bean.getDescricao().toUpperCase());
		getInstance().editar(bean);
	}
	
	public static long count() throws Excecao{
		long count = 0;
		count = getInstance().count();
		
		return count;
	}
	
	public static List<ProdutoBean> listar(int pagina) throws Excecao{
		List<ProdutoBean> lista = new ArrayList<ProdutoBean>();
		lista = getInstance().listar(pagina);
		
		return lista;
	}
	
	public static ProdutoBean buscarPorNome(String nome) throws Excecao{
		nome = nome.toUpperCase();
		return dao.buscarPorNome(nome);
	}

}
