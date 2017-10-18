package com.umg.gt.gestionbodega.controller;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import com.umg.gt.gestionbodega.dao.AlmacenDAO;
import com.umg.gt.gestionbodega.orm.AlmacenORM;
import com.umg.gt.gestionbodega.util.Conf;

public class AlmacenCtrl extends ControladorBase {
	private static final long serialVersionUID = 1L;
	private AlmacenDAO dao;
	
	Window wdwAlmacen;
	Window wdwConfAlmacen;
	
	Listbox lbxAlmacen;
	
	public void doAfterCompose(Component comp) {
		super.doAfterCompose(comp);
		dao = new AlmacenDAO();
		Connection conn = null;
		try{
			conn = getConLocal();
			cargaLbxAlmacen(conn, null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cerrarConexion(conn);
		}
	}
	
	public void cargaLbxAlmacen(Connection conn, AlmacenORM filtro) throws Exception {
		lbxAlmacen.getItems().clear();
		lbxAlmacen.setEmptyMessage("No existen almacenes creados.");
		
		List<AlmacenORM> lista = dao.obtieneListaAlmacen(conn, filtro);
		
		Iterator<AlmacenORM> iList = lista.iterator();
		
		Listitem item;
		Listcell cell;
		
		while(iList.hasNext()){
			AlmacenORM obj = iList.next();
			
			item = new Listitem();
			
			cell = new Listcell("");
			cell.setParent(item);
			
			cell = new Listcell(obj.getBod_nombre());
			cell.setParent(item);
			
			cell = new Listcell(obj.getBod_descripcion());
			cell.setParent(item);
			
			cell = new Listcell(obj.getBod_direccion());
			cell.setParent(item);
			
			cell = new Listcell(obj.getBod_fch_creado());
			cell.setParent(item);
			
			item.setValue(obj);
			item.removeEventListener("onDoubleClick", onDoubleClick_lbxAlmacen);
			item.addEventListener("onDoubleClick", onDoubleClick_lbxAlmacen);
			item.setParent(lbxAlmacen);
			
		}	
	}
	
	public void onClick$btnAgregar(){
		Button btnGuardar = (Button) wdwConfAlmacen.getFellow("btnGuardar");
		Textbox txtNombre = (Textbox) wdwConfAlmacen.getFellow("txtNombre");
		Textbox txtDireccion = (Textbox) wdwConfAlmacen.getFellow("txtDireccion");
		Textbox txtDescripcion = (Textbox) wdwConfAlmacen.getFellow("txtDescripcion");
		
		txtNombre.setText("");
		txtDireccion.setText("");
		txtDescripcion.setText("");
		
		btnGuardar.removeEventListener("onClick", onClick_btnGuardar);
		btnGuardar.addEventListener("onClick", onClick_btnGuardar);
		wdwConfAlmacen.setTitle("Nuevo Almacen");
		wdwConfAlmacen.removeAttribute(Conf.KEY_APERTURA);
		wdwConfAlmacen.setAttribute(Conf.KEY_APERTURA, Conf.KEY_NUEVO);
		wdwConfAlmacen.doHighlighted();
	}
	
	EventListener<Event> onClick_btnGuardar = new EventListener<Event>() {
		public void onEvent(Event event) {
			AlmacenORM obj = obtieneAlmacen();
			if(obj == null){
				return;
			}
			
			Connection conn = null;
			String k_ap = wdwConfAlmacen.getAttribute(Conf.KEY_APERTURA).toString();
			try{
				conn = getConLocal();
				
				int resp = 0;
				
				if(k_ap.equals(Conf.KEY_NUEVO)){
					String v_bodCodigo = dao.obtieneSecuencia(conn);
					obj.setBod_codigo(v_bodCodigo);
					resp = dao.insertaAlmacen(conn, obj, getUsuario().getUsu_id_usuario());
				}else if(k_ap.equals(Conf.KEY_EDITA)){
					String v_bodCodigo = ((AlmacenORM)lbxAlmacen.getSelectedItem().getValue()).getBod_codigo();
					obj.setBod_codigo(v_bodCodigo);
					resp = dao.actualizaAlmacen(conn, obj, getUsuario().getUsu_id_usuario());
				}
				
				if(resp == 0){
					mostrarMensaje("Ha ocurrido un inconveniente al intentar realizar esta acción, por favor consulte a su administrador.");
				}else{
					cargaLbxAlmacen(conn, null);
					wdwConfAlmacen.setVisible(false);
				}
				
			}catch(Exception e){
				mostrarMensaje("Ha ocurrido un inconveniente, si el problema persiste consulte a su administrador.");
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			}
		}
	};
	
	EventListener<Event> onDoubleClick_lbxAlmacen = new EventListener<Event>() {
		public void onEvent(Event event) {
			Button btnGuardar = (Button) wdwConfAlmacen.getFellow("btnGuardar");
			Textbox txtNombre = (Textbox) wdwConfAlmacen.getFellow("txtNombre");
			Textbox txtDireccion = (Textbox) wdwConfAlmacen.getFellow("txtDireccion");
			Textbox txtDescripcion = (Textbox) wdwConfAlmacen.getFellow("txtDescripcion");
			
			AlmacenORM obj = (AlmacenORM) lbxAlmacen.getSelectedItem().getValue();
			
			txtNombre.setText(obj.getBod_nombre());
			txtDireccion.setText(obj.getBod_direccion());
			txtDescripcion.setText(obj.getBod_descripcion());
			
			btnGuardar.removeEventListener("onClick", onClick_btnGuardar);
			btnGuardar.addEventListener("onClick", onClick_btnGuardar);
			wdwConfAlmacen.setTitle("Nuevo Almacen");
			wdwConfAlmacen.removeAttribute(Conf.KEY_APERTURA);
			wdwConfAlmacen.setAttribute(Conf.KEY_APERTURA, Conf.KEY_EDITA);
			wdwConfAlmacen.doHighlighted();
		}
	};
	
	public AlmacenORM obtieneAlmacen(){
		Textbox txtNombre = (Textbox) wdwConfAlmacen.getFellow("txtNombre");
		Textbox txtDireccion = (Textbox) wdwConfAlmacen.getFellow("txtDireccion");
		Textbox txtDescripcion = (Textbox) wdwConfAlmacen.getFellow("txtDescripcion");
		
		if(txtNombre.getText().trim().equals("")){
			mostrarMensaje("Debe ingresar el nombre del almacén.");
			return null;
		}else if(!Conf.isTextoValido(txtNombre.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en el nombre del almacén.");
			return null;
		}
		if(txtDireccion.getText().trim().equals("")){
			mostrarMensaje("Debe ingresar la dirección del almacén.");
			return null;
		}else if(!Conf.isTextoValido(txtDireccion.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en la dirección del almacén.");
			return null;
		}
		if(txtDescripcion.getText().trim().equals("")){
			mostrarMensaje("Debe ingresar la descripción del almacén.");
			return null;
		}else if(!Conf.isTextoValido(txtDescripcion.getText())){
			mostrarMensaje("Se han encontrado caracteres inválidos en la descripción del almacén.");
			return null;
		}
		
		AlmacenORM orm = new AlmacenORM();
		orm.setBod_nombre(txtNombre.getText());
		orm.setBod_direccion(txtDireccion.getText());
		orm.setBod_descripcion(txtDescripcion.getText());
		
		return orm;
	}
	
}
