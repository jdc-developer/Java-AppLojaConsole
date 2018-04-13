package jdc.loja.view;

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
		int[] validos = new int[] {1, 2, 3};
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
			
			int[] validos = new int[] {1, 2, 3, 9};
			
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
}
