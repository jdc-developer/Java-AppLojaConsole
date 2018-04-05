package jdc.loja.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.exception.Excecao;

/**
 * Aplicativo simples feito no console do Java simulando uma loja utilizando gerência de dados com objetos serializados
 * @author Jorge Do Carmo
 * @version 1.0
 */
public class ConsoleView {
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleView.class);
	private static Scanner entrada;

	public static void main(String[] args) {
		entrada = new Scanner(System.in);
		log.info("Iniciando aplicação\n");
		
		System.out.println("--------------------------- BEM-VINDO AO JAVA-APP-LOJA ---------------------------");
		System.out.println("------------------------ Desenvolvido por Jorge Do Carmo -------------------------");
		System.out.println("----------------------------- Versão 1.0 / 2018 ----------------------------------");
		
		acao1();
	}
	
	public static void acao1() {
		System.out.println("\nDIGITE O CARACTERE CORRESPONDENTE À OPÇÃO DESEJADA:\n\n"
				+ "1. Cadastrar Produto\n"
				+ "2. Cadastrar Funcionario\n"
				+ "3. Realizar Venda");
		int[] validos = new int[] {1, 2, 3};
		try {
			int comando = comando(validos);
		} catch (Exception e) {
			int i = 1;
			while(entrada.hasNext()) {
				System.out.println(i);
				i++;
			}
			acao1();
		}
	}
	
	public static int comando(int[] validos) throws Exception {
		int comando = 0;
		boolean valido = false;
		comando = entrada.nextInt();
		
		for(int i = 0; i < validos.length; i++) {
			if(i == comando) {
				valido = true;
			}
		}
		
		if(valido) {
			return comando;
		}
		else {
			throw new Excecao("Comando inválido");
		}
	}
}
