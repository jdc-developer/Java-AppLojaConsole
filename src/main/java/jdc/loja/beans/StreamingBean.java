package jdc.loja.beans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe criada para gerenciar os streams, writers e readers do dao generico
 * @author Jorge Do Carmo
 *
 */
public class StreamingBean {

	private static final Logger log = LoggerFactory.getLogger(StreamingBean.class);
	
	private ObjectOutputStream output;
	private FileReader reader;
	private BufferedReader buffReader;
	
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
	
	public StreamingBean(String path) throws IOException {
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
}
