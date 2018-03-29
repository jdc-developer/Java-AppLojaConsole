package jdc.loja.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe de exceção customizada para maior controle sobre erros.
 * @author Jorge Do Carmo
 *
 */
public class Excecao extends Exception{
	
	private static final long serialVersionUID = -7257236006926906690L;
	
	private static final Logger log = LoggerFactory.getLogger(Excecao.class);
	private String msg;
	
	public void receberMsg(String msg){
		this.msg = msg;
	}
	public String retornarMsg(){
		return msg;
	}
	public Excecao(String msg){
		receberMsg(msg);
		log.error(msg);
	}
	public Excecao(Exception e){
		if(e.getClass().toString().equals("class java.sql.SQLException")){
			receberMsg("Erro no banco de dados");
			log.error("Erro no banco de dados");
		} else{
			receberMsg("Erro desconhecido");
			log.error("Erro desconhecido");
		}
		e.printStackTrace();
	}
}
