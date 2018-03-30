package jdc.loja.beans;

import java.io.Serializable;

public class ItemVendaBean implements Serializable{

	private static final long serialVersionUID = -7385344287795055173L;

	private int codigo;
	private VendaBean venda;
	private ProdutoBean produto;
	private int quantidade;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public VendaBean getVenda() {
		return venda;
	}
	public void setVenda(VendaBean venda) {
		this.venda = venda;
	}
	public ProdutoBean getProduto() {
		return produto;
	}
	public void setProduto(ProdutoBean produto) {
		this.produto = produto;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public ItemVendaBean(int codigo, VendaBean venda, ProdutoBean produto, int quantidade) {
		super();
		this.codigo = codigo;
		this.venda = venda;
		this.produto = produto;
		this.quantidade = quantidade;
	}
	public ItemVendaBean() {
		super();
	}
}
