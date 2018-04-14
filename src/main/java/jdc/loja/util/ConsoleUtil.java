package jdc.loja.util;

import java.lang.reflect.Method;
import java.util.Scanner;

import jdc.loja.exception.Excecao;

/**
 * Classe para armazenar os métodos genéricos do console
 * @author Jorge Do Carmo
 *
 */
public class ConsoleUtil {

	private static Scanner entrada = new Scanner(System.in);
	
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
	public static int comando(Integer[] validos) throws Excecao {
		boolean valido = false;
		String input = entrada.next();
		StringBuilder processedChars = new StringBuilder();
		
		if(input.isEmpty()) {
			throw new Excecao("Comando inválido, digite novamente");
		}
		
		for(int i=0 ; i < input.length() ; i++){
		    char c = input.charAt(i);
		    if(!Character.isDigit(c)){
		    	throw new Excecao("Comando inválido, digite novamente");
		    }else{
		        processedChars.append(c);
		    }
		}
		
		int comando = Integer.parseInt(processedChars.toString());
		
		for(int i = 0; i < validos.length; i++) {
			if(validos[i] == comando) {
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
		String input = entrada.next();
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
	
	public static void confirmarAcao(String titulo, String[] valores, int retorno) {
		System.out.println(titulo);
		
		for(int i = 0; i < valores.length; i++) {
			System.out.println(valores[i]);
		}
		
		System.out.println("\n1. Confirmar / 2. Cancelar");
		
		int comando = 0;
		Integer validos[] = new Integer[] {1, 2};
		
		try {
			comando = comando(validos);
		} catch (Excecao e) {
			confirmarAcao(titulo, valores, retorno);
		}
		
		if(comando == 2) {
			System.out.println("Cancelado");
			callAction(retorno);
		}
	}
	
	public static String propriedadeString(String propriedade) {
		System.out.println("\n" + propriedade);
		
		String valor = null;
		
		try {
			valor = receberString();
		} catch (Excecao e) {
			propriedadeString(propriedade);
		}
		
		return valor;
	}
	
	public static float propriedadeFloat(String propriedade) {
		System.out.println("\n" + propriedade);
		
		float valor = 0;
		
		try {
			valor = receberFloat();
		} catch (Excecao e) {
			propriedadeFloat(propriedade);
		}
		
		return valor;
	}
}
