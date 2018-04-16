package jdc.loja.view;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jdc.loja.beans.ProdutoBean;
import jdc.loja.bo.ProdutoBO;
import jdc.loja.exception.Excecao;
import jdc.loja.util.ConsoleUtil;

public class ConsoleProduto {
	
	private static DecimalFormat format = new DecimalFormat("R$#0.00");

	public static void acao1() {
		
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
			ConsoleProduto.callAction(1);
		} finally {
			ProdutoBO.destroy();
			ConsoleUtil.callAction(1);
		}
	}
	
	public static void acao2() {
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
		ProdutoBO.destroy();
	}
	
	public static void acao3() {
		System.out.println("\n--------------------------- BUSCAR PRODUTO ---------------------------");
		System.out.println("\nDIGITE A OP��O DESEJADA:");
		System.out.println("1. Buscar por c�digo / 2. Buscar por descri��o / 9. Voltar");
		
		int comando = 0;
		Integer[] validos = new Integer[] {1, 2, 9};
		
		try {
			comando = ConsoleUtil.comando(validos);
		} catch (Excecao e) {
			ConsoleProduto.callAction(3);
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
		
		System.out.println("\nDeseja realizar mais uma a��o?");
		System.out.println("1. Excluir um produto / 2. Editar um produto / 3. Realizar outra busca / 9. Voltar");
		
		int comando2 = 0;
		Integer[] validos2 = new Integer[] {1, 2, 3, 9};
		
		try {
			comando2 = ConsoleUtil.comando(validos2);
		} catch (Excecao e) {
			ConsoleProduto.callAction(3);
		}
		
		ProdutoBO.destroy();
		
		switch(comando2) {
		case 1:
			ConsoleProduto.callAction(4);
			break;
		case 2:
			ConsoleProduto.callAction(5);
			break;
		case 3:
			ConsoleProduto.callAction(3);
			break;
		case 9:
			ConsoleUtil.callAction(1);
			break;
		}
	}
	
	public static void acao4() {
		System.out.println("\n--------------------------- EXCLUIR PRODUTO ---------------------------");
		System.out.println("\nDIGITE O C�DIGO DO PRODUTO:");
		
		float codigo = 0;
		ProdutoBean bean = null;
		
		try {
			 codigo = ConsoleUtil.receberFloat();
			 bean = ProdutoBO.buscar((int) codigo);
		} catch (Excecao e) {
			ConsoleProduto.callAction(4);
		}
		
		if (bean == null) {
			ConsoleProduto.callAction(4);
		}
		
		String[] valores = new String[] {"\nDescri��o: " + bean.getDescricao(), "Pre�o: " + bean.getPreco()};
		ConsoleUtil.confirmarAcao("\nExcluir este produto?", valores, 1);
		
		ProdutoBO.destroy();
		
		try {
			ProdutoBO.deletar((int) codigo);
		} catch (Excecao e) {
			
		} finally {
			ProdutoBO.destroy();
			ConsoleUtil.callAction(1);
		}
	}
	
	public static void callAction(int action) {
		try {
			Class<?> classe = Class.forName("jdc.loja.view.ConsoleProduto");
			Method metodo = classe.getMethod("acao" + action);
			metodo.invoke(classe);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
