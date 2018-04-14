package jdc.loja.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.bo.ProdutoBO;
import jdc.loja.exception.Excecao;
import jdc.loja.util.ConsoleUtil;

/**
 * Aplicativo simples feito no console do Java simulando uma loja utilizando ger�ncia de dados com objetos serializados
 * @author Jorge Do Carmo
 * @version 1.0
 */
public class ConsoleView {
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleView.class);
	private static DecimalFormat format = new DecimalFormat("R$#0.00");

	/**
	 * O main apresenta a aplica��o e chama a primeira a��o. Todo o fluxo est� dividido em a��es para possibilitar redirecionamento
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("Iniciando aplica��o\n");
		
		System.out.println("--------------------------- BEM-VINDO AO JAVA-APP-LOJA ---------------------------");
		System.out.println("------------------------ Desenvolvido por Jorge Do Carmo -------------------------");
		System.out.println("----------------------------- Vers�o 1.0 / 2018 ----------------------------------");
		
		ConsoleUtil.callAction(0);
	}
	
	public static void acao0() {
		System.out.println("\nDIGITE O CARACTERE CORRESPONDENTE � OP��O DESEJADA:\n\n"
				+ "1. Produtos\n"
				+ "2. Funcion�rios\n"
				+ "3. Vendas");
		Integer[] validos = new Integer[] {1, 2, 3};
		int comando = 0;
		
		try {
			comando = ConsoleUtil.comando(validos);
		} catch (Excecao e) {
			ConsoleUtil.callAction(0);
		}
		
		if(comando != 0) {
			ConsoleUtil.callAction(comando);
		}
	}
	
	public static void acao1() {
		int comando = 0;
		
		try {
			System.out.println("--------------------------- PRODUTOS ---------------------------");
			System.out.println("------------------------ CADASTRADOS: " + ProdutoBO.count() + " ------------------------");
			System.out.println("\nDIGITE O CARACTERE CORRESPONDENTE � OP��O DESEJADA:\n\n"
					+ "1. Cadastrar Produto\n"
					+ "2. Listar Produtos\n"
					+ "3. Buscar Produto Existente\n"
					+ "9. Voltar");
			
			Integer[] validos = new Integer[] {1, 2, 3, 9};
			
			comando = ConsoleUtil.comando(validos);
		} catch (Excecao e) {
			ConsoleUtil.callAction(1);
		}
		
		if(comando != 0 && comando != 9) {
			ConsoleUtil.callAction(comando + 10);
		} else if (comando == 9) {
			ConsoleUtil.callAction(0);
		}
	}
	
	public static void acao11() {
		
		System.out.println("--------------------------- CADASTRAR PRODUTO ---------------------------");
		System.out.println("\nDIGITE OS DADOS DO PRODUTO:");
		
		String descricao = null;
		float preco = 0;
		
		descricao = ConsoleUtil.propriedadeString("Descri��o");
		
		System.out.println("\nDescri��o salva");
		
		preco = ConsoleUtil.propriedadeFloat("Pre�o:");
		
		String[] valores = new String[] {"\nDescri��o: " + descricao, "Pre�o: " + preco};
		ConsoleUtil.confirmarAcao("\nCadastrar este produto?", valores, 1);
		
		ProdutoBean bean = new ProdutoBean(descricao, preco);
		
		try {
			ProdutoBO.cadastrar(bean);
		} catch (Excecao e) {
			ConsoleUtil.callAction(11);
		} finally {
			ConsoleUtil.callAction(1);
		}
	}
	
	public static void acao12() {
		listarProd(1);
	}
	
	public static void listarProd(int pagina) {
		List<ProdutoBean> lista = new ArrayList<ProdutoBean>();
		int paginas = 0;
		long count = 0;
		
		try {
			lista = ProdutoBO.listar(pagina);
			count = ProdutoBO.count();
			paginas = (int) Math.ceil( (double) count / 10);
		} catch (Excecao e) {
			ConsoleUtil.callAction(1);
		}
		
		System.out.println("\n--------------------------- LISTAR PRODUTOS ---------------------------");
		System.out.println("------------------------------ TOTAL: " + count + " ------------------------------");
		System.out.println("---------------------------- P�gina " + pagina + " de " + paginas + " ----------------------------");
		System.out.println("\nDigite o n�mero da p�gina desejada para navegar ou digite 0 para voltar\n");
		
		for(ProdutoBean produto : lista) {
			System.out.print("C�digo: " + produto.getCodigo());
			System.out.print(" | Descri��o: " + produto.getDescricao());
			System.out.print(" | Pre�o: " + format.format(produto.getPreco()));
			System.out.println("");
		}
		
		List<Integer> valores = new ArrayList<Integer>();
		
		for(int i = 1; i <= paginas; i++) {
			valores.add(i);
		}
		
		Integer[] validos = new Integer[] {};
		valores.add(0);
		int comando = 0;
		
		try {
			comando = ConsoleUtil.comando(valores.toArray(validos));
		} catch (Excecao e) {
			ConsoleUtil.callAction(1);
		}
		
		if(comando == 0) {
			ConsoleUtil.callAction(1);
		}
		
		listarProd(comando);
	}
	
	public static void acao13() {
		System.out.println("\n--------------------------- BUSCAR PRODUTO ---------------------------");
		System.out.println("\nDIGITE A OP��O DESEJADA:");
		System.out.println("1. Buscar por c�digo / 2. Buscar por descri��o / 9. Voltar");
		
		int comando = 0;
		Integer[] validos = new Integer[] {1, 2, 9};
		
		try {
			comando = ConsoleUtil.comando(validos);
		} catch (Excecao e) {
			ConsoleUtil.callAction(13);
		}
		
		ProdutoBean bean = null;
		
		switch(comando) {
		case 1:
			System.out.println("\nDigite o c�digo:");
			float codigo = 0;
			
			try {
				codigo = ConsoleUtil.receberFloat();
				bean = ProdutoBO.buscar((int) codigo);
				
				if(bean == null) {
					ConsoleUtil.callAction(13);
				}
				
				System.out.println("\nProduto:");
				System.out.println("C�digo: " + bean.getCodigo() + " | Descri��o: " + bean.getDescricao() + " | Pre�o: " + format.format(bean.getPreco()));
			} catch (Excecao e) {
				ConsoleUtil.callAction(13);
			}
			break;
		case 2:
			System.out.println("\nDigite a descri��o:");
			String descricao = null;
			
			try {
				descricao = ConsoleUtil.receberString();
				List<ProdutoBean> lista = ProdutoBO.buscarPorNome(descricao);
				
				if(lista.isEmpty()) {
					ConsoleUtil.callAction(13);
				}
				
				System.out.println("\n" + lista.size() + " Resultados");
				for(ProdutoBean prod : lista) {
					System.out.println("\nProduto:");
					System.out.println("C�digo: " + prod.getCodigo() + " | Descri��o: " + prod.getDescricao() + " | Pre�o: " + format.format(prod.getPreco()));
				}
			} catch (Excecao e) {
				ConsoleUtil.callAction(13);
			}
			break;
		case 9:
			ConsoleUtil.callAction(1);
			break;
		}
	}
}
