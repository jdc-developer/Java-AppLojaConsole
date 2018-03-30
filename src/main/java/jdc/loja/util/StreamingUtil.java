package jdc.loja.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdc.loja.exception.Excecao;

/**
 * Classe criada para gerenciar os streams e readers do dao generico
 * @author Jorge Do Carmo
 *
 */
public class StreamingUtil {

	private static final Logger log = LoggerFactory.getLogger(StreamingUtil.class);
	
	private ObjectOutputStream output;
	private FileReader reader;
	private BufferedReader buffReader;
	private ObjectInputStream input;
	
	public static Logger getLog() {
		return log;
	}
	public ObjectInputStream getInput() {
		return input;
	}
	public void setInput(ObjectInputStream input) {
		this.input = input;
	}
	public ObjectOutputStream getOutput() {
		return output;
	}
	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}
	public FileReader getReader() {
		return reader;
	}
	public void setReader(FileReader reader) {
		this.reader = reader;
	}
	public BufferedReader getBuffReader() {
		return buffReader;
	}
	public void setBuffReader(BufferedReader buffReader) {
		this.buffReader = buffReader;
	}
	
	/**
	 * Método construtor verifica se existe o sequence e o cria caso não exista
	 * @param path
	 * @throws IOException
	 */
	public StreamingUtil(String path) throws IOException {
		super();
		
		try {
			log.debug("Buscando arquivo 'sequence.txt'");
			
			reader = new FileReader(path);
		} catch(FileNotFoundException e) {
			log.debug("Arquivo não encontrado, criando 'sequence.txt'");
			
			FileWriter writerLocal = new FileWriter(path);
			PrintWriter printLocal = new PrintWriter(writerLocal);
			printLocal.println(1);
			printLocal.close();
			writerLocal.close();
			
			reader = new FileReader(path);
		} finally {
			log.debug("'sequence.txt' encontrado");
			
			buffReader = new BufferedReader(reader);
		}
	}
	
	public void destroy() throws Excecao {
		log.debug("Fechando streams");
		try {
			buffReader.close();
			reader.close();
			if(output != null) {
				output.close();
			}
			if(input != null) {
				input.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Excecao("Erro ao fechar Streams");
		}
		log.debug("Streams fechados");
	}
	
	public void resetBuff(String path) throws Excecao {
		log.debug("Resetando Buffered Stream");
		try {
			buffReader.close();
			reader.close();
			reader = new FileReader(path + "\\sequence.txt");
			buffReader = new BufferedReader(reader);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Excecao("Erro ao resetar Buffered Stream");
		}
		log.debug("Resetado");
	}
}
