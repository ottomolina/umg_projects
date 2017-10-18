package com.umg.gt.gestionbodega.controller;

import java.sql.Connection;

import org.apache.commons.codec.digest.DigestUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;

import com.umg.gt.gestionbodega.dao.LoginDAO;
import com.umg.gt.gestionbodega.orm.UsuarioORM;

public class LoginCtrl extends ControladorBase {
	private static final long serialVersionUID = 1L;
	
	Window wdwCredenciales;
	Textbox txtUsuario;
	Textbox txtPass;
	
	@Override
	public void doAfterCompose(Component comp) {
		super.doAfterCompose(comp);
		
		wdwCredenciales.doHighlighted();
		
		Button btnIniciar = (Button) wdwCredenciales.getFellow("btnIniciar");
		Button btnRecordar = (Button) wdwCredenciales.getFellow("btnRecordar");
		
		txtUsuario = (Textbox) wdwCredenciales.getFellow("txtUsuario");
		txtPass = (Textbox) wdwCredenciales.getFellow("txtPass");
		
		btnIniciar.removeEventListener("onClick", onClick_btnIniciar);
		btnRecordar.removeEventListener("onClick", onClick_btnRecordar);
		
		btnIniciar.addEventListener("onClick", onClick_btnIniciar);
		btnRecordar.addEventListener("onClick", onClick_btnRecordar);
		
		txtUsuario.removeEventListener("onOK", onOK_txtUsuario);
		txtUsuario.addEventListener("onOK", onOK_txtUsuario);
		
		txtPass.removeEventListener("onOK", onOK_txtPassword);
		txtPass.addEventListener("onOK", onOK_txtPassword);
	}
	
	public void autentica(Connection conn) throws Exception {
		UsuarioORM user = new UsuarioORM();
		user.setUsu_id_usuario(txtUsuario.getText());
		user.setUsu_password( DigestUtils.md5Hex(txtPass.getText()) );
		
		user = new LoginDAO().obtieneListaUsuario(conn, user).get(0);
		
		desktop.getSession().setAttribute("USER", user);
		
		Executions.sendRedirect(null);
	}
	
	EventListener<Event> onClick_btnIniciar = new EventListener<Event>() {
		public void onEvent(Event event) {
			if(txtUsuario.getText().trim().equals("")){
				mostrarMensaje("Debe ingresar su usuario.");
				return;
			}
			if(txtPass.getText().trim().equals("")){
				mostrarMensaje("Debe ingresar la contraseña de su usuario.");
				return;
			}
			
			Connection conn = null;
			try{
				conn = getConLocal();
				String valida = new LoginDAO().validaAutenticacion(conn, txtUsuario.getText(), DigestUtils.md5Hex(txtPass.getText()));
				if(valida.equals("OK")){
					autentica(conn);
				}else{
					mostrarMensaje(valida);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				cerrarConexion(conn);
			} 
		}
	};
	
	EventListener<Event> onClick_btnRecordar = new EventListener<Event>() {
		public void onEvent(Event event) {
			mostrarMensaje("Aun no definido...");
		}
	};
	
	EventListener<Event> onOK_txtUsuario = new EventListener<Event>() {
		public void onEvent(Event event) {
			Button btnIniciar = (Button) wdwCredenciales.getFellow("btnIniciar");
			Events.echoEvent("onClick", btnIniciar, null);
		}
	};
	
	EventListener<Event> onOK_txtPassword = new EventListener<Event>() {
		public void onEvent(Event event) {
			Button btnIniciar = (Button) wdwCredenciales.getFellow("btnIniciar");
			Events.echoEvent("onClick", btnIniciar, null);
		}
	};
	
}

