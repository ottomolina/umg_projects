package com.umg.gt.gestionbodega.controller;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

import com.umg.gt.gestionbodega.dao.AlmacenDAO;
import com.umg.gt.gestionbodega.dao.GeneralDAO;
import com.umg.gt.gestionbodega.dao.LoginDAO;
import com.umg.gt.gestionbodega.orm.AlmacenORM;
import com.umg.gt.gestionbodega.orm.ParamORM;
import com.umg.gt.gestionbodega.orm.UsuarioORM;
import com.umg.gt.gestionbodega.util.Conf;

public class UsuarioCtrl extends ControladorBase {
	private static final long serialVersionUID = 1L;
	private LoginDAO dao;
	private String codAlmacen;
	Window wdwUsuario;
	Window wdwConfUsuario;
	Window wdwChangePass;
	
	Listbox lbxAlmacen;
	Listbox lbxUsuario;

	@Override
	public void doAfterCompose(Component comp) {
		super.doAfterCompose(comp);
		dao = new LoginDAO();
		Connection conn = null;
		try{
			conn = getConLocal();
			loadLbxAlmacen(conn, null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cerrarConexion(conn);
		}
	}
	
	public void loadLbxAlmacen(Connection conn, AlmacenORM filtro) throws Exception {
		lbxAlmacen.getItems().clear();
		lbxAlmacen.setEmptyMessage("No existen almacenes creados.");
		
		List<AlmacenORM> lista = new AlmacenDAO().obtieneListaAlmacen(conn, filtro);
		
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
			
			item.setValue(obj);
			item.removeEventListener("onClick", onClick_lbxAlmacen);
			item.addEventListener("onClick", onClick_lbxAlmacen);
			item.setParent(lbxAlmacen);
		}
	}
	
	public void loadLbxUsuario(Connection conn, UsuarioORM filtro) throws Exception {
		lbxUsuario.getItems().clear();
		lbxUsuario.setEmptyMessage("No existen usuarios para el almacén seleccionado.");
		
		List<UsuarioORM> lista = new LoginDAO().obtieneListaUsuario(conn, filtro);
		
		Iterator<UsuarioORM> iList = lista.iterator();
		
		Listitem item;
		Listcell cell;
		
		while(iList.hasNext()){
			UsuarioORM obj = iList.next();
			
			item = new Listitem();
			
			cell = new Listcell("");
			cell.setParent(item);
			
			cell = new Listcell(obj.getUsu_id_usuario());
			cell.setParent(item);
			
			cell = new Listcell(obj.getUsu_nombre_usuario());
			cell.setParent(item);
			
			cell = new Listcell(obj.getUsu_tipo());
			cell.setParent(item);
			
			cell = new Listcell(obj.getUsu_fch_creado());
			cell.setParent(item);
			
			item.setValue(obj);
			item.removeEventListener("onDoubleClick", onDoubleClick_lbxUsuario);
			item.addEventListener("onDoubleClick", onDoubleClick_lbxUsuario);
			item.setParent(lbxUsuario);
		}
	}
	
	public void onClick$btnAgregar() {
		
		if(lbxAlmacen.getSelectedItem() == null){
			mostrarMensaje("Debe seleccionar un almacén para realizar esta acción.");
			return;
		}
		AlmacenORM orm = lbxAlmacen.getSelectedItem().getValue();
		Button btnGuardar = (Button) wdwConfUsuario.getFellow("btnGuardar");
		
		Textbox txtAlmacen = (Textbox) wdwConfUsuario.getFellow("txtAlmacen");
		Textbox txtNombre = (Textbox) wdwConfUsuario.getFellow("txtNombre");
		Combobox cmbTipUser = (Combobox) wdwConfUsuario.getFellow("cmbTipUser");
		
		Textbox txtIdUser = (Textbox) wdwConfUsuario.getFellow("txtIdUser");
		Textbox txtPassUser = (Textbox) wdwConfUsuario.getFellow("txtPassUser");
		
		txtAlmacen.setText(orm.getBod_nombre());
		txtNombre.setText("");
		cmbTipUser.setSelectedItem(null);
		cmbTipUser.setText("");
		txtIdUser.setText("");
		txtPassUser.setText("");
		txtPassUser.setDisabled(false);
		
		btnGuardar.removeEventListener("onClick", onClick_btnGuardar);
		btnGuardar.addEventListener("onClick", onClick_btnGuardar);
		wdwConfUsuario.setTitle("Nuevo Usuario");
		wdwConfUsuario.removeAttribute(Conf.KEY_APERTURA);
		wdwConfUsuario.setAttribute(Conf.KEY_APERTURA, Conf.KEY_NUEVO);
		
		Connection conn = null;
		try{
			conn = getConLocal();
			List<ParamORM> paramORM = new GeneralDAO().obtieneParametros(conn, "TIPO_USUARIO");
			
			if(paramORM.size() == 0){
				mostrarMensaje("No están configurados los tipos de usuario.");
			}else{
				
				Comboitem item = null;
				ParamORM param = null;
				
				Iterator<ParamORM> iParam = paramORM.iterator();
				cmbTipUser.getItems().clear();
				
				while(iParam.hasNext()){
					param = iParam.next();
					
					item = new Comboitem();
					item.setLabel(param.getValor_parametro());
					item.setValue(param.getValor_parametro());
					
					item.setParent(cmbTipUser);
				}
				
				wdwConfUsuario.doHighlighted();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cerrarConexion(conn);
		}
	}
	
	public void onClick$btnPassword() {
		if(lbxUsuario.getSelectedItem() == null){
			mostrarMensaje("Debe seleccionar un registro de usuario.");
			return;
		}
		UsuarioORM orm = (UsuarioORM) lbxUsuario.getSelectedItem().getValue();
		Button btnGuardar = (Button) wdwChangePass.getFellow("btnGuardar");
		Textbox txtIdUser = (Textbox)wdwChangePass.getFellow("txtIdUser");
		Textbox txtPassUser = (Textbox)wdwChangePass.getFellow("txtPassUser");
		
		txtIdUser.setText(orm.getUsu_id_usuario());
		txtPassUser.setText("");
		
		btnGuardar.removeEventListener("onClick", onClick_btnGuardarPass);
		btnGuardar.addEventListener("onClick", onClick_btnGuardarPass);
		wdwChangePass.doHighlighted();
	}
	
	public UsuarioORM obtieneUsuario(){
		Textbox txtNombre = (Textbox) wdwConfUsuario.getFellow("txtNombre");
		Combobox cmbTipUser = (Combobox) wdwConfUsuario.getFellow("cmbTipUser");
		Textbox txtIdUser = (Textbox) wdwConfUsuario.getFellow("txtIdUser");
		Textbox txtPassUser = (Textbox) wdwConfUsuario.getFellow("txtPassUser");
		
		if(txtNombre.getText().equals("")){
			mostrarMensaje("Debe ingresar un nombre para el usuario.");
			return null;
		}else if(txtNombre.getText().indexOf("'") > -1){
			mostrarMensaje("El nombre contiene caracteres no permitidos.");
			return null;
		}
		if(txtIdUser.getText().equals("")){
			mostrarMensaje("Debe ingresar un identificador para el usuario.");
			return null;
		}else if(txtNombre.getText().indexOf("'") > -1){
			mostrarMensaje("El identificador del usuario contiene caracteres no permitidos.");
			return null;
		}
		if(txtPassUser.getText().equals("")){
			mostrarMensaje("Debe ingresar la contraseña para el usuario.");
			return null;
		}else if(txtPassUser.getText().indexOf("'") > -1){
			mostrarMensaje("La contraseña del usuario contiene caracteres no permitidos.");
			return null;
		}
		
		UsuarioORM obj = new UsuarioORM();
		obj.setBod_codigo(((AlmacenORM)lbxAlmacen.getSelectedItem().getValue()).getBod_codigo());
		obj.setUsu_id_usuario(txtIdUser.getText());
		obj.setUsu_nombre_usuario(txtNombre.getText());
		obj.setUsu_tipo(cmbTipUser.getSelectedItem().getLabel());
		obj.setUsu_user_creado(getUsuario().getUsu_id_usuario());
		obj.setUsu_password(DigestUtils.md5Hex( txtPassUser.getText()));
		return obj;
	}
	
	EventListener<Event> onClick_btnGuardarPass = new EventListener<Event>() {
		public void onEvent(Event event) {
			Textbox txtPassUser = (Textbox)wdwChangePass.getFellow("txtPassUser");
			if(txtPassUser.getText().equals("")){
				mostrarMensaje("Debe ingresar la contraseña para el usuario.");
				return;
			}
			
			UsuarioORM obj = (UsuarioORM) lbxUsuario.getSelectedItem().getValue();
			obj.setUsu_password(DigestUtils.md5Hex( txtPassUser.getText()));
			
			Connection conn = null;
			try{
				conn = getConLocal();
				
				int resp = dao.actualizaPassword(conn, obj, getUsuario().getUsu_id_usuario());
				
				if(resp == 0){
					mostrarMensaje("Ha ocurrido un inconveniente al intentar realizar esta acción, por favor consulte a su administrador.");
				}else{
					mostrarMensaje("La contraseña ha sido actualizada.");
					loadLbxUsuario(conn, null);
					wdwChangePass.setVisible(false);
				}
			}catch(Exception e){
				mostrarMensaje("Ha ocurrido un inconveniente, si el problema persiste consulte a su administrador.");
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			}
		}
	};
	
	EventListener<Event> onClick_btnGuardar = new EventListener<Event>() {
		public void onEvent(Event event) {
			UsuarioORM obj = obtieneUsuario();
			if(obj == null){
				return;
			}
			
			Connection conn = null;
			String k_ap = wdwConfUsuario.getAttribute(Conf.KEY_APERTURA).toString();
			try{
				conn = getConLocal();
				
				int resp = 0;
				if(k_ap.equals(Conf.KEY_NUEVO)){
					String v_usuCodigo = dao.obtieneSeqUsuario(conn, obj.getBod_codigo());
					obj.setUsu_codigo(v_usuCodigo);
					resp = dao.insertaUsuario(conn, obj, getUsuario().getUsu_id_usuario());
				}else if(k_ap.equals(Conf.KEY_EDITA)){
					String v_usuCodigo = ((UsuarioORM)lbxUsuario.getSelectedItem().getValue()).getUsu_codigo();
					obj.setUsu_codigo(v_usuCodigo);
					resp = dao.actualizaUsuario(conn, obj, getUsuario().getUsu_id_usuario());
				}
				
				if(resp == 0){
					mostrarMensaje("Ha ocurrido un inconveniente al intentar realizar esta acción, por favor consulte a su administrador.");
				}else{
					loadLbxUsuario(conn, null);
					wdwConfUsuario.setVisible(false);
				}
			}catch(Exception e){
				mostrarMensaje("Ha ocurrido un inconveniente, si el problema persiste consulte a su administrador.");
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			}
		}
	};
	
	EventListener<Event> onClick_lbxAlmacen = new EventListener<Event>() {
		public void onEvent(Event event) {
			AlmacenORM almacen = (AlmacenORM)lbxAlmacen.getSelectedItem().getValue();
			if(codAlmacen != null){
				if(almacen.getBod_codigo().equals(codAlmacen)){
					return;
				}
			}
			codAlmacen = almacen.getBod_codigo();
			
			Connection conn = null;
			try{
				conn = getConLocal();
				UsuarioORM filtro = new UsuarioORM();
				filtro.setBod_codigo(almacen.getBod_codigo());
				
				loadLbxUsuario(conn, filtro);
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			}
		}
	};
	
	EventListener<Event> onDoubleClick_lbxUsuario = new EventListener<Event>() {
		public void onEvent(Event event) {
			UsuarioORM orm = (UsuarioORM) lbxUsuario.getSelectedItem().getValue();
			AlmacenORM almacen = (AlmacenORM)lbxAlmacen.getSelectedItem().getValue();
			
			Textbox txtAlmacen = (Textbox)wdwConfUsuario.getFellow("txtAlmacen");
			Textbox txtNombre = (Textbox) wdwConfUsuario.getFellow("txtNombre");
			Combobox cmbTipUser = (Combobox) wdwConfUsuario.getFellow("cmbTipUser");
			Textbox txtIdUser = (Textbox) wdwConfUsuario.getFellow("txtIdUser");
			Textbox txtPassUser = (Textbox) wdwConfUsuario.getFellow("txtPassUser");
			Datebox dtbFecha = (Datebox) wdwConfUsuario.getFellow("dtbFecha");
			
			txtAlmacen.setText(almacen.getBod_nombre());
			txtNombre.setText(orm.getUsu_nombre_usuario());
			txtIdUser.setText(orm.getUsu_id_usuario());
			txtIdUser.setReadonly(true);
			txtPassUser.setReadonly(true);
			txtPassUser.setText(/*orm.getUsu_password()*/"password");
			dtbFecha.setText(orm.getUsu_fch_creado());
			
			Button btnGuardar = (Button)wdwConfUsuario.getFellow("btnGuardar");
			btnGuardar.removeEventListener("onClick", onClick_btnGuardar);
			btnGuardar.addEventListener("onClick", onClick_btnGuardar);
			
			wdwConfUsuario.setTitle("Modificar Usuario");
			wdwConfUsuario.removeAttribute(Conf.KEY_APERTURA);
			wdwConfUsuario.setAttribute(Conf.KEY_APERTURA, Conf.KEY_EDITA);
			
			Connection conn = null;
			try{
				conn = getConLocal();
				List<ParamORM> paramORM = new GeneralDAO().obtieneParametros(conn, "TIPO_USUARIO");
				
				if(paramORM.size() == 0){
					mostrarMensaje("No están configurados los tipos de usuario.");
				}else{
					
					Comboitem item = null;
					ParamORM param = null;
					
					Iterator<ParamORM> iParam = paramORM.iterator();
					cmbTipUser.getItems().clear();
					
					while(iParam.hasNext()){
						param = iParam.next();
						
						item = new Comboitem();
						item.setLabel(param.getValor_parametro());
						item.setValue(param.getValor_parametro());
						
						item.setParent(cmbTipUser);
					}
					
					wdwConfUsuario.doHighlighted();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			}
			cmbTipUser.setText(orm.getUsu_tipo());
		}
	};
	
}
