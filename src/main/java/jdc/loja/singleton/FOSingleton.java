package jdc.loja.singleton;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import jdc.loja.exception.Excecao;

public class FOSingleton {

	private static ObjectOutputStream output;
	
	private FOSingleton() {};
	
	public ObjectOutputStream getInstance() throws Excecao {
		if(output == null) {
			try {
				output = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.dir") + "/app-resources"));
			} catch (Exception e) {
				e.printStackTrace();
				throw new Excecao("Erro ao efetuar criação do diretório de arquivos");
			}
		}
		return output;
	}
}
