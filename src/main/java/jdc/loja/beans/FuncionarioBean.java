package jdc.loja.beans;

import java.io.Serializable;

public class FuncionarioBean implements Serializable{

	private static final long serialVersionUID = 5295237389012375960L;
	
	private int codigo;
	private String nome;
	private float salario;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getSalario() {
		return salario;
	}
	public void setSalario(float salario) {
		this.salario = salario;
	}
	public FuncionarioBean(int codigo, String nome, float salario) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.salario = salario;
	}
	public FuncionarioBean() {
		super();
	}
}
