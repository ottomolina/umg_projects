package com.umg.gt.gestionbodega.controller;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;

import com.umg.gt.gestionbodega.orm.UsuarioORM;
import com.umg.gt.gestionbodega.util.Conf;

public class ControladorBase extends GenericForwardComposer<Component> {
	private static final long serialVersionUID = 1L;
	
	private final String CONN = "ONLINE"; // Esta variable tendrá valores: LOCAL, ONLINE
	
	public void doAfterCompose(Component comp){
		try{
			super.doAfterCompose(comp);
			
		}catch(Exception e){
			
		}
		
	}
	
	public UsuarioORM getUsuario(){
		UsuarioORM user = null;
		if(CONN.equals("LOCAL")){
			user = new UsuarioORM();
			user.setUsu_nombre_usuario("Ottoniel Molina");
			user.setUsu_id_usuario("ottoniel.molina");
			user.setBod_codigo("1");
			desktop.getSession().setAttribute("USER", user);
		}else{
			if(desktop.getSession().getAttribute("USER") != null){
				user = (UsuarioORM)desktop.getSession().getAttribute("USER");
			}
		}
		return user;
	}
	
	protected void validaLogin(){
		if(getUsuario() == null){
			Executions.sendRedirect("index.zul");
		}
	}
	
	public Connection getConLocal() throws Exception {
		Connection conexion = null;
		try {
			conexion = obtenerDataSource(Conf.JNDI_BODEGA).getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
			throw new Exception("Surgio un inconveniente con la fuente de Datos. Recurso: " + Conf.JNDI_BODEGA);
		}
		return conexion;
	}
	
	private DataSource obtenerDataSource(String strJndi) throws NamingException {
		DataSource ret = null;
		if (desktop.getSession().getAttribute("dtsjndi"+strJndi) == null) {
			Context contexto= new InitialContext();
			ret = (DataSource)contexto.lookup("java:comp/env/"+strJndi);
			desktop.getSession().setAttribute("dtsjndi"+strJndi, ret);
			return ret;
		} else {
			return (DataSource) desktop.getSession().getAttribute("dtsjndi"+strJndi);
		}
	}
	
	public void cerrarConexion(Connection conn){
		try{
			if(conn != null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void mostrarMensaje(String p_mensaje){
		Messagebox.show(p_mensaje, "Información", Messagebox.OK, Messagebox.INFORMATION);
	}

}
