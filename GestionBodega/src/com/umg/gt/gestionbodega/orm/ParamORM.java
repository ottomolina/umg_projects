package com.umg.gt.gestionbodega.orm;

public class ParamORM {
	private String codigo;
	private String nombre_parametro;
	private String valor_parametro;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre_parametro() {
		return nombre_parametro;
	}
	public void setNombre_parametro(String nombre_parametro) {
		this.nombre_parametro = nombre_parametro;
	}
	public String getValor_parametro() {
		return valor_parametro;
	}
	public void setValor_parametro(String valor_parametro) {
		this.valor_parametro = valor_parametro;
	}
}
