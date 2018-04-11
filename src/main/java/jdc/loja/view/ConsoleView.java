package jdc.loja.view;

import java.lang.reflect.Method;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.bo.ProdutoBO;
import jdc.loja.exception.Excecao;

/**
 * Aplicativo simples feito no console do Java simulando uma loja utilizando gerência de dados com objetos serializados
 * @author Jorge Do Carmo
 * @version 1.0
 */
public class ConsoleView {
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleView.class);
	private static Scanner entrada;

	/**
	 * O main apresenta a aplicação e chama a primeira ação. Todo o fluxo está dividido em ações para possibilitar redirecionamento
	 * @param args
	 */
	public static void main(String[] args) {
		entrada = new Scanner(System.in);
		log.info("Iniciando aplicação\n");
		
		System.out.println("--------------------------- BEM-VINDO AO JAVA-APP-LOJA ---------------------------");
		System.out.println("------------------------ Desenvolvido por Jorge Do Carmo -------------------------");
		System.out.println("----------------------------- Versão 1.0 / 2018 ----------------------------------");
		
		callAction(0);
	}
	
	public static void acao0() {
		System.out.println("\nDIGITE O CARACTERE CORRESPONDENTE À OPÇÃO DESEJADA:\n\n"
				+ "1. Produtos\n"
				+ "2. Funcionários\n"
				+ "3. Vendas");
		int[] validos = new int[] {1, 2, 3};
		int comando = 0;
		
		try {
			comando = comando(validos);
		} catch (Excecao e) {
			callAction(0);
		}
		
		if(comando != 0) {
			callAction(comando);
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
			
			int[] validos = new int[] {1, 2, 3, 9};
			
			comando = comando(validos);
		} catch (Excecao e) {
			callAction(1);
		}
		
		if(comando != 0 && comando != 9) {
			callAction(comando + 10);
		} else if (comando == 9) {
			callAction(0);
		}
	}
	
	public static void acao11() {
		
		System.out.println("--------------------------- CADASTRAR PRODUTO ---------------------------");
		System.out.println("\nDIGITE OS DADOS DO PRODUTO:");
		System.out.println("Descrição:");
		
		String descricao = null;
		float valor = 0;
		
		try {
			descricao = receberString();
		} catch (Excecao e) {
			callAction(11);
		}
		
		System.out.println("\nGravado");
		
		try {
			valor = receberFloat();
		} catch (Excecao e) {
			callAction(11);
		}
		
		ProdutoBean bean = new ProdutoBean(descricao, valor);
		
		try {
			ProdutoBO.cadastrar(bean);
		} catch (Excecao e) {
			e.printStackTrace();
		} finally {
			callAction(1);
		}
	}
	
	/**
	 * Método genérico para chamar outro método de acordo com o comando enviado
	 * @param action
	 */
	public static void callAction(int action) {
		try {
			Class<?> classe = Class.forName("jdc.loja.view.ConsoleView");
			Method metodo = classe.getMethod("acao" + action);
			metodo.invoke(classe);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Método genérico para receber comandos e validá-los de acordo com o tipo de dado e as opções válidas
	 * @param validos
	 * @return valor do comando
	 * @throws Excecao
	 */
	public static int comando(int[] validos) throws Excecao {
		boolean valido = false;
		String input = entrada.nextLine();
		StringBuilder processedChars = new StringBuilder();
		
		if(input.isEmpty()) {
			throw new Excecao("Comando inválido, digite novamente");
		}
		
		for(int i=0 ; i<input.length() ; i++){
		    char c = input.charAt(i);
		    if(!Character.isDigit(c)){
		    	throw new Excecao("Comando inválido, digite novamente");
		    }else{
		        processedChars.append(c);
		    }
		}
		
		int comando = Integer.parseInt(processedChars.toString());
		
		for(int i = 1; i < 10; i++) {
			if(i == comando) {
				valido = true;
			}
		}
		
		if(valido) {
			return comando;
		}
		else {
			throw new Excecao("Comando inválido, digite novamente");
		}
	}
	
	public static String receberString() throws Excecao{
		boolean valido = false;
		String input = entrada.next();
		StringBuilder processedChars = new StringBuilder();
		
		if(input.isEmpty()) {
			throw new Excecao("Valor inválido, digite novamente");
		}
		
		for(int i=0 ; i<input.length() ; i++){
		    char c = input.charAt(i);
		    if(Character.isLetter(c)){
		    	valido = true;
		    }
		    processedChars.append(c);
		}
		
		if(valido) {
			return processedChars.toString();
		}
		else {
			throw new Excecao("Valor inválido, digite novamente");
		}
	}
	
	public static float receberFloat() throws Excecao{
		String input = entrada.nextLine();
		StringBuilder processedChars = new StringBuilder();
		
		if(input.isEmpty()) {
			throw new Excecao("Valor inválido, digite novamente");
		}
		
		for(int i=0 ; i<input.length() ; i++){
		    char c = input.charAt(i);
		    if(!Character.isDigit(c)){
		    	throw new Excecao("Valor inválido, digite novamente");
		    }else{
		        processedChars.append(c);
		    }
		}
		
		float valor = Float.parseFloat(processedChars.toString());
		
		return valor;
	}
}
