package com.umg.gt.gestionbodega.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.*;

import com.umg.gt.gestionbodega.orm.UsuarioORM;

public class MenuCtrl extends ControladorBase {
	private static final long serialVersionUID = 1L;
	Window wdwMenu;
	Div dvInicio;
	Div dvInvMenu;
	Div dvOrdMenu;
	
	Tabbox tabPrincipal;
	
	public void doAfterCompose(Component comp){
		try{
			super.doAfterCompose(comp);
			
			validaLogin();
			
			UsuarioORM user = getUsuario();
			if(!user.getUsu_tipo().equals("Administrador")){
				wdwMenu.getFellow("mncInfALmacen").setVisible(false);
				wdwMenu.getFellow("mncConfUser").setVisible(false);
			}
			
		}catch(Exception e){
			
		}
	}
	
	/* GOMR 14/05/2017 NAVIGATION OF A MENU */
	public void onDoubleClick$mncInv(){
		dvInicio.setVisible(false);
		dvInvMenu.setVisible(true);
	}
	
	public void onDoubleClick$mncOrd(){
		dvInicio.setVisible(false);
		dvOrdMenu.setVisible(true);
	}
	
	public void onClick$btnRegresarInv(){
		dvInicio.setVisible(true);
		dvInvMenu.setVisible(false);
	}
	
	public void onClick$btnRegresarOrd(){
		dvInicio.setVisible(true);
		dvOrdMenu.setVisible(false);
	}
	
/************************************/	
/** METODOS DE NAVEGACION DEL MENU **/
/************************************/
	/** Inventario de Entrada */
	public void onDoubleClick$mncInvIn(){
		String titulo = "Inventario Entrada";
		String url    = "/zul/inventario.zul?var=1";
		String id  	  = "incInvIn";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	/** Inventario de Salida */
	public void onDoubleClick$mncInvOut(){
		String titulo = "Inventario Salida";
		String url    = "/zul/inventario.zul";
		String id  	  = "incInvOut";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	/** Ordenes de Entrada */
	public void onDoubleClick$mncOrdIn(){
		String titulo = "Transacción Entrada";
		String url    = "/zul/transaccion.zul";
		String id  	  = "incOrdIn";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	/** Ordenes de Salida */
	public void onDoubleClick$mncOrdOut(){
		String titulo = "Transacción Salida";
		String url    = "/zul/transaccion.zul";
		String id  	  = "incOrdOut";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	/** Producto */
	public void onDoubleClick$mncInfProduct(){
		String titulo = "Productos";
		String url    = "/zul/producto.zul";
		String id  	  = "incProducto";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	public void onDoubleClick$mncInfALmacen(){
		String titulo = "Almacen";
		String url    = "/zul/almacen.zul";
		String id  	  = "incAlmacen";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	public void onDoubleClick$mncConfUser(){
		String titulo = "Configuracion Usuarios";
		String url    = "/zul/usuario.zul";
		String id  	  = "incConfUser";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	public void onDoubleClick$mncConfUserAdmin(){
		String titulo = "Configuracion Usuarios";
		String url    = "/zul/admin_user.zul";
		String id  	  = "incConfUser";
		String ico    = "/img/icons/almacen_16.png";
		if (!existeTab(id)){
			creaTab(id, titulo, url, ico);
		}
	}
	
	public void onDoubleClick$mncSalir(){
		desktop.getSession().removeAttribute("USER");
		Executions.sendRedirect(null);
	}
	
	
	public boolean existeTab(String id) {
		List<Tab> tabs = tabPrincipal.getTabs().getChildren();
		for (Tab tab : tabs){
			if (tab.getId().equals(id)) {
				tab.setSelected(true);
				return true;
			}
		}
		return false;
	}
	
	public void creaTab(String id, String titulo, String zul) {
		creaTab(id, titulo, zul, null);
	}
	
	@SuppressWarnings("deprecation")
	public void creaTab(String id, String titulo, String zul, String ico) {
		Tab tab = null;
		Tabpanel tabpanel = null;
		Include inc = null;
		Borderlayout bl = null;
		Center ce = null;
		Div div = null;
		
		tab = new Tab();
		tab.setId(id);
		tab.setLabel(titulo);
		tab.setClosable(true);
		tab.setParent(tabPrincipal.getTabs());
		tab.setSelected(true);
		tab.setStyle("height:36px; border:none; background:rgba(255, 255, 255, 0.5);");
		
		if (ico != null)
			tab.setImage(ico);
		
		tabpanel = new Tabpanel();
		tabpanel.setWidth("99.2%");
		tabpanel.setHeight("100%");
		tabpanel.setParent(tabPrincipal.getTabpanels());
		
		bl = new Borderlayout();
		ce = new Center();
		
		bl.setParent(tabpanel);
		bl.setStyle("background: transparent;");
		
		ce.setStyle("background: transparent;");
		ce.setParent(bl);
		ce.setBorder("none");
		ce.setFlex(true);
		ce.setAutoscroll(true);
		
		div = new Div();
		div.setAlign("center");
		div.setParent(ce);
		
		inc = new Include();
		inc.setParent(div);
		inc.setStyle("margin-top: 0px;");
		inc.setWidth("100%");
		inc.setHeight("100%");
		inc.setSrc(zul);
	}
	
}
