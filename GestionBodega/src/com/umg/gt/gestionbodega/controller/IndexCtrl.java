package com.umg.gt.gestionbodega.controller;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

import com.umg.gt.gestionbodega.util.Conf;

public class IndexCtrl extends ControladorBase {
	private static final long serialVersionUID = 1L;
	
	Include inc_menu;
	
	Label lblUsuario;
	Label lblFecha;
	
	@Override
	public void doAfterCompose(Component comp) {
		super.doAfterCompose(comp);
		autenticacion();
	}
	
	public void autenticacion(){
		if(getUsuario() == null){
			inc_menu.setSrc("zul/login.zul");
//			inc_menu.setVisible(false);
//			inc_login.setVisible(true);
		}else{
			inc_menu.setSrc("menu2.zul");
//			inc_login.setVisible(false);
			lblUsuario.setValue("Bienvenido " + getUsuario().getUsu_nombre_usuario() + "");
			lblFecha.setValue(Conf.FORMATO_FECHA.format(new Date()));
		}
	}
	
}
