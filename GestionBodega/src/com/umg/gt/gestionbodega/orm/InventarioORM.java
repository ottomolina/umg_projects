package com.umg.gt.gestionbodega.orm;

public class InventarioORM {
	private String bod_codigo;
	private String inv_tipo;
	private String lot_codigo;
	private String inv_codigo;
	private String prd_codigo;
	private String inv_cantidad;
	private String inv_fch_creado;
	private String inv_user_creado;
	
	public String getBod_codigo() {
		return bod_codigo;
	}
	public void setBod_codigo(String bod_codigo) {
		this.bod_codigo = bod_codigo;
	}
	public String getInv_tipo() {
		return inv_tipo;
	}
	public void setInv_tipo(String inv_tipo) {
		this.inv_tipo = inv_tipo;
	}
	public String getLot_codigo() {
		return lot_codigo;
	}
	public void setLot_codigo(String lot_codigo) {
		this.lot_codigo = lot_codigo;
	}
	public String getInv_codigo() {
		return inv_codigo;
	}
	public void setInv_codigo(String inv_codigo) {
		this.inv_codigo = inv_codigo;
	}
	public String getPrd_codigo() {
		return prd_codigo;
	}
	public void setPrd_codigo(String prd_codigo) {
		this.prd_codigo = prd_codigo;
	}
	public String getInv_cantidad() {
		return inv_cantidad;
	}
	public void setInv_cantidad(String inv_cantidad) {
		this.inv_cantidad = inv_cantidad;
	}
	public String getInv_fch_creado() {
		return inv_fch_creado;
	}
	public void setInv_fch_creado(String inv_fch_creado) {
		this.inv_fch_creado = inv_fch_creado;
	}
	public String getInv_user_creado() {
		return inv_user_creado;
	}
	public void setInv_user_creado(String inv_user_creado) {
		this.inv_user_creado = inv_user_creado;
	}
	
}
