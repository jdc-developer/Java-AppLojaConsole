package jdc.loja.beans;

import java.io.Serializable;

/**
 * Bean produto
 * @author Jorge do Carmo
 *
 */
public class ProdutoBean implements Serializable{

	private static final long serialVersionUID = -7559384053949317302L;

	private int codigo;
	private String descricao;
	private float preco;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public ProdutoBean(String descricao, float preco) {
		super();
		this.descricao = descricao;
		this.preco = preco;
	}
	public ProdutoBean() {
		super();
	}
}
