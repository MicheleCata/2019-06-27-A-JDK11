package it.polito.tdp.crimes.model;

public class Adiacenze {
	
	private String e1;
	private String e2;
	private Integer peso;
	
	public Adiacenze(String e1, String e2, Integer peso) {
		super();
		this.e1 = e1;
		this.e2 = e2;
		this.peso = peso;
	}

	public String getE1() {
		return e1;
	}

	public void setE1(String e1) {
		this.e1 = e1;
	}

	public String getE2() {
		return e2;
	}

	public void setE2(String e2) {
		this.e2 = e2;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	public String toString() {
		return this.e1+" - "+ this.e2+" "+ this.peso;
	}
	
	

}
