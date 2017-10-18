package com.umg.gt.gestionbodega.controller;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import com.umg.gt.gestionbodega.dao.ProductoDAO;

import com.umg.gt.gestionbodega.orm.ProductoORM;
import com.umg.gt.gestionbodega.util.Conf;

public class ProductoCtrl extends ControladorBase {
	private static final long serialVersionUID = 1L;
	private ProductoDAO dao;
	Window wdwConfProducto;
	Window wdwFiltroProducto;
	Listbox lbxInv;
	
	public void doAfterCompose(Component comp) {
		super.doAfterCompose(comp);
		dao = new ProductoDAO();
		
		Connection conn = null;
		try{
			conn = getConLocal();
			
			cargaLbxInv(conn);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cerrarConexion(conn);
		}
		
	}
	
	public void cargaLbxInv(Connection conn) throws Exception{
		lbxInv.getItems().clear();
		
		List<ProductoORM> listaProducto = dao.getListaProducto(conn, null);
		
		Iterator<ProductoORM> iList = listaProducto.iterator();
		ProductoORM obj;
		Listitem item = null;
		Listcell cell = null;
		
		while(iList.hasNext()){
			obj = iList.next();
			
			item = new Listitem();
			
			cell = new Listcell("");
			cell.setParent(item);
			
			cell = new Listcell(obj.getPrd_codigo_barra());
			cell.setParent(item);
			
			cell = new Listcell(obj.getPrd_nombre());
			cell.setParent(item);
			
			cell = new Listcell(obj.getPrd_descripcion());
			cell.setParent(item);
			
			cell = new Listcell(obj.getPrd_precio());
			cell.setParent(item);
			
			cell = new Listcell(obj.getPrd_user_creado());
			cell.setParent(item);
			
			cell = new Listcell(obj.getPrd_fch_creado());
			cell.setParent(item);
			
			item.setValue(obj);
			item.setTooltiptext("Dé doble clic para obtener más información.");
			item.removeEventListener("onDoubleClick", onDoubleClick_lbxInv);
			item.addEventListener("onDoubleClick", onDoubleClick_lbxInv);
			item.setParent(lbxInv);
		}
		
	}
	
	public void onClick$btnAgregar(){
		Button btnGuardar = (Button) wdwConfProducto.getFellow("btnGuardar");
		Textbox txtCodBarra = (Textbox)wdwConfProducto.getFellow("txtCodBarra");
		Textbox txtNombre = (Textbox)wdwConfProducto.getFellow("txtNombre");
		Decimalbox decPrecio = (Decimalbox)wdwConfProducto.getFellow("decPrecio");
		Textbox txtDescripcion = (Textbox)wdwConfProducto.getFellow("txtDescripcion");
		
		txtCodBarra.setText("");
		txtNombre.setText("");
		decPrecio.setText("");
		txtDescripcion.setText("");
		
		btnGuardar.removeEventListener("onClick", onClick_btnGuardar);
		btnGuardar.addEventListener("onClick", onClick_btnGuardar);
		wdwConfProducto.setTitle("Nuevo Producto");
		wdwConfProducto.removeAttribute(Conf.KEY_APERTURA);
		wdwConfProducto.setAttribute(Conf.KEY_APERTURA, Conf.KEY_NUEVO);
		wdwConfProducto.doHighlighted();
	}
	
	public void onClick$btnFiltro(){
		Button btnConsultar = (Button) wdwFiltroProducto.getFellow("btnConsultar");
		Textbox txtCodBarra = (Textbox)wdwFiltroProducto.getFellow("txtCodBarra");
		Textbox txtNombre = (Textbox)wdwFiltroProducto.getFellow("txtNombre");
		Decimalbox decPrecio = (Decimalbox)wdwFiltroProducto.getFellow("decPrecio");
		
		
		txtCodBarra.setText("");
		txtNombre.setText("");
		decPrecio.setText("");
		
		
		btnConsultar.removeEventListener("onClick", onClick_btnConsultar);
		btnConsultar.addEventListener("onClick", onClick_btnConsultar);
		wdwFiltroProducto.setTitle("Filtrar");
		wdwFiltroProducto.removeAttribute(Conf.KEY_APERTURA);
		wdwFiltroProducto.setAttribute(Conf.KEY_APERTURA, Conf.KEY_CONSULTA);
		wdwFiltroProducto.doHighlighted();
	}
	
	EventListener<Event> onClick_btnGuardar = new EventListener<Event>() {
		public void onEvent(Event event) {
			ProductoORM obj = obtieneProducto();
			if(obj == null){
				return;
			}
			
			Connection conn = null;
			
			String k_ap = wdwConfProducto.getAttribute(Conf.KEY_APERTURA).toString();
			try{
				conn = getConLocal();
				
				int resp = 0;
				
				if(k_ap.equals(Conf.KEY_NUEVO)){
					String v_bodCodigo = dao.obtieneSecuencia(conn);
					obj.setPrd_codigo(v_bodCodigo);
					resp = dao.insertaProducto(conn, obj, getUsuario().getUsu_id_usuario());
				}else if(k_ap.equals(Conf.KEY_EDITA)){
					String v_bodCodigo = ((ProductoORM)lbxInv.getSelectedItem().getValue()).getPrd_codigo();
					obj.setPrd_codigo(v_bodCodigo);
					resp = dao.actualizaProducto(conn, obj, getUsuario().getUsu_id_usuario());
				}
				
				if(resp == 0){
					mostrarMensaje("Ha ocurrido un inconveniente al intentar realizar esta acción, por favor consulte a su administrador.");
				}else{
					cargaLbxInv(conn);
					wdwConfProducto.setVisible(false);
				}
				
			}catch(Exception e){
				mostrarMensaje("Ha ocurrido un inconveniente, si el problema persiste consulte a su administrador.");
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			}
		}
			
		
	};
	
	EventListener<Event> onDoubleClick_lbxInv = new EventListener<Event>() {
		public void onEvent(Event event) {
			Button btnGuardar = (Button) wdwConfProducto.getFellow("btnGuardar");
			Textbox txtCodBarra = (Textbox)wdwConfProducto.getFellow("txtCodBarra");
			Textbox txtNombre = (Textbox)wdwConfProducto.getFellow("txtNombre");
			Decimalbox decPrecio = (Decimalbox)wdwConfProducto.getFellow("decPrecio");
			Textbox txtDescripcion = (Textbox)wdwConfProducto.getFellow("txtDescripcion");
			
			ProductoORM obj = (ProductoORM) lbxInv.getSelectedItem().getValue();
			
			txtCodBarra.setText(obj.getPrd_codigo_barra());
			txtNombre.setText(obj.getPrd_nombre());
			decPrecio.setText(obj.getPrd_precio());
			txtDescripcion.setText(obj.getPrd_descripcion());
			
			btnGuardar.removeEventListener("onClick", onClick_btnGuardar);
			btnGuardar.addEventListener("onClick", onClick_btnGuardar);
			wdwConfProducto.setTitle("Nuevo Producto");
			wdwConfProducto.removeAttribute(Conf.KEY_APERTURA);
			wdwConfProducto.setAttribute(Conf.KEY_APERTURA, Conf.KEY_EDITA);
			wdwConfProducto.doHighlighted();
		}
	};
	
	EventListener<Event> onClick_btnConsultar = new EventListener<Event>() {
		public void onEvent(Event event) {
			ProductoORM obj = obtieneProductoFiltro();
			if(obj == null){
				return;
			}
			
			Connection conn = null;
			
			String k_ap = wdwFiltroProducto.getAttribute(Conf.KEY_APERTURA).toString();
			try{
				conn = getConLocal();
				if(k_ap.equals(Conf.KEY_CONSULTA)){
					lbxInv.getItems().clear();
					
					List<ProductoORM> listaProducto = dao.getListaProducto(conn, obj);
					
					Iterator<ProductoORM> iList = listaProducto.iterator();
					
					Listitem item = null;
					Listcell cell = null;
					
					while(iList.hasNext()){
						obj = iList.next();
						
						item = new Listitem();
						
						cell = new Listcell("");
						cell.setParent(item);
						
						cell = new Listcell(obj.getPrd_codigo_barra());
						cell.setParent(item);
						
						cell = new Listcell(obj.getPrd_nombre());
						cell.setParent(item);
						
						cell = new Listcell(obj.getPrd_descripcion());
						cell.setParent(item);
						
						cell = new Listcell(obj.getPrd_precio());
						cell.setParent(item);
						
						cell = new Listcell(obj.getPrd_user_creado());
						cell.setParent(item);
						
						cell = new Listcell(obj.getPrd_fch_creado());
						cell.setParent(item);
						
						item.setValue(obj);
						item.setTooltiptext("Dé doble clic para obtener más información.");
						item.removeEventListener("onDoubleClick", onDoubleClick_lbxInv);
						item.addEventListener("onDoubleClick", onDoubleClick_lbxInv);
						item.setParent(lbxInv);
					}
				}
				
				wdwFiltroProducto.setVisible(false);
				
				
			}catch(Exception e){
				mostrarMensaje("Ha ocurrido un inconveniente, si el problema persiste consulte a su administrador.");
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			}
		}
			
		
	};
	
	public ProductoORM obtieneProductoFiltro(){
		
		Textbox txtCodBarra = (Textbox)wdwFiltroProducto.getFellow("txtCodBarra");
		Textbox txtNombre = (Textbox)wdwFiltroProducto.getFellow("txtNombre");
		Decimalbox decPrecio = (Decimalbox)wdwFiltroProducto.getFellow("decPrecio");
		
		
		if(!Conf.isTextoValido(txtCodBarra.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en el codigo de barras.");
			return null;
		}
		if(!Conf.isTextoValido(txtNombre.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en el nombre.");
			return null;
		}
		if(!Conf.isTextoValido(decPrecio.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en el precio.");
			return null;
		}
		
		ProductoORM orm = new ProductoORM();
		orm.setPrd_codigo_barra(txtCodBarra.getText());
		orm.setPrd_nombre(txtNombre.getText());
		orm.setPrd_precio(decPrecio.getText());
		return orm;
		
	}
	
	public ProductoORM obtieneProducto(){
		
		Textbox txtCodBarra = (Textbox)wdwConfProducto.getFellow("txtCodBarra");
		Textbox txtNombre = (Textbox)wdwConfProducto.getFellow("txtNombre");
		Decimalbox decPrecio = (Decimalbox)wdwConfProducto.getFellow("decPrecio");
		Textbox txtDescripcion = (Textbox)wdwConfProducto.getFellow("txtDescripcion");
		
		if(txtCodBarra.getText().trim().equals("")){
			mostrarMensaje("Debe ingresar el Codigo de barras.");
			return null;
		}else if(!Conf.isTextoValido(txtCodBarra.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en el codigo de barras.");
			return null;
		}
		if(txtNombre.getText().trim().equals("")){
			mostrarMensaje("Debe ingresar el Nombre.");
			return null;
		}else if(!Conf.isTextoValido(txtNombre.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en el nombre.");
			return null;
		}
		if(txtDescripcion.getText().trim().equals("")){
			mostrarMensaje("Debe ingresar la descripcion.");
			return null;
		}else if(!Conf.isTextoValido(txtDescripcion.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en la descripcion.");
			return null;
		}
		if(decPrecio.getText().trim().equals("")){
			mostrarMensaje("Debe ingresar el precio.");
			return null;
		}else if(!Conf.isTextoValido(decPrecio.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en el precio.");
			return null;
		}
		
		ProductoORM orm = new ProductoORM();
		orm.setPrd_codigo_barra(txtCodBarra.getText());
		orm.setPrd_nombre(txtNombre.getText());
		orm.setPrd_precio(decPrecio.getText());
		orm.setPrd_descripcion(txtDescripcion.getText());
		return orm;
		
	}

}
