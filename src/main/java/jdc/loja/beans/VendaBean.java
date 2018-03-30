package jdc.loja.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class VendaBean implements Serializable{

	private static final long serialVersionUID = -1535402284262323364L;
	
	private int codigo;
	private List<ItemVendaBean> itens;
	private Calendar dataVenda;
	private FuncionarioBean funcionario;
	private double valor;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public List<ItemVendaBean> getItens() {
		return itens;
	}
	public void setItens(List<ItemVendaBean> itens) {
		this.itens = itens;
	}
	public Calendar getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(Calendar dataVenda) {
		this.dataVenda = dataVenda;
	}
	public FuncionarioBean getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioBean funcionario) {
		this.funcionario = funcionario;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public VendaBean(int codigo, List<ItemVendaBean> itens, Calendar dataVenda, FuncionarioBean funcionario,
			double valor) {
		super();
		this.codigo = codigo;
		this.itens = itens;
		this.dataVenda = dataVenda;
		this.funcionario = funcionario;
		this.valor = valor;
	}
	public VendaBean() {
		super();
	}
}
