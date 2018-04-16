package jdc.loja.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.bo.ProdutoBO;
import jdc.loja.exception.Excecao;
import jdc.loja.util.ConsoleUtil;

/**
 * Aplicativo simples feito no console do Java simulando uma loja utilizando gerência de dados com objetos serializados
 * @author Jorge Do Carmo
 * @version 1.0
 */
public class ConsoleView {
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleView.class);

	/**
	 * O main apresenta a aplicação e chama a primeira ação. Todo o fluxo está dividido em ações para possibilitar redirecionamento
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("Iniciando aplicação\n");
		
		System.out.println("--------------------------- BEM-VINDO AO JAVA-APP-LOJA ---------------------------");
		System.out.println("------------------------ Desenvolvido por Jorge Do Carmo -------------------------");
		System.out.println("----------------------------- Versão 1.0 / 2018 ----------------------------------");
		
		ConsoleUtil.callAction(0);
	}
	
	public static void acao0() {
		System.out.println("\nDIGITE O CARACTERE CORRESPONDENTE À OPÇÃO DESEJADA:\n\n"
				+ "1. Produtos\n"
				+ "2. Funcionários\n"
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
			System.out.println("\nDIGITE O CARACTERE CORRESPONDENTE À OPÇÃO DESEJADA:\n\n"
					+ "1. Cadastrar Produto\n"
					+ "2. Listar Produtos\n"
					+ "3. Buscar Produto Existente\n"
					+ "9. Voltar");
			
			Integer[] validos = new Integer[] {1, 2, 3, 9};
			
			comando = ConsoleUtil.comando(validos);
		} catch (Excecao e) {
			ConsoleUtil.callAction(1);
		}
		
		ProdutoBO.destroy();
		
		if(comando != 0 && comando != 9) {
			ConsoleProduto.callAction(comando);
		} else if (comando == 9) {
			ConsoleUtil.callAction(0);
		}
	}
}
