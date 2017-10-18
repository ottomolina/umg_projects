package com.umg.gt.gestionbodega.controller;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.umg.gt.gestionbodega.dao.InventarioDAO;
import com.umg.gt.gestionbodega.orm.InventarioORM;

public class InventarioCtrl extends ControladorBase {
	private static final long serialVersionUID = 1L;
	private InventarioDAO dao;
	Window wdwDetalleInventario;
	Window wdwFiltroInventario;
	Listbox lbxInv;
	String strTipoInventario;
	public void doAfterCompose(Component comp) {
		super.doAfterCompose(comp);
		dao = new InventarioDAO();
		
		Connection conn = null;
		try{
			strTipoInventario = Executions.getCurrent().getParameter("var1").toString();
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
		boolean blTipoInv;
		
		if(strTipoInventario == "1")
		{
			blTipoInv = true;
		}
		else{
			blTipoInv = false;
		}
		
		List<InventarioORM> listaProducto = dao.getListaInventarioEntrada(conn, null, blTipoInv);
		
		Iterator<InventarioORM> iList = listaProducto.iterator();
		InventarioORM obj;
		Listitem item = null;
		Listcell cell = null;
		
		while(iList.hasNext()){
			obj = iList.next();
			
			item = new Listitem();
			
			cell = new Listcell("");
			cell.setParent(item);
			
			//cell = new Listcell(obj.getInv_tipo());
			//cell.setParent(item);
			
			//cell = new Listcell(obj.getLot_codigo());
			//cell.setParent(item);
			
			cell = new Listcell(obj.getInv_codigo());
			cell.setParent(item);
			
			cell = new Listcell(obj.getPrd_codigo());
			cell.setParent(item);
			
			cell = new Listcell(obj.getInv_cantidad());
			cell.setParent(item);
			
			cell = new Listcell(obj.getInv_fch_creado());
			cell.setParent(item);
			
			item.setValue(obj);
			item.setTooltiptext("Dé doble clic para obtener más información.");
			item.removeEventListener("onDoubleClick", onDoubleClick_lbxInv);
			item.addEventListener("onDoubleClick", onDoubleClick_lbxInv);
			item.setParent(lbxInv);
		}
		
	}
	
	EventListener<Event> onDoubleClick_lbxInv = new EventListener<Event>() {
		public void onEvent(Event event) {
			//*Button btnGuardar = (Button) wdwConfProducto.getFellow("btnGuardar");
			//Textbox txtCodBarra = (Textbox)wdwConfProducto.getFellow("txtCodBarra");
			//Textbox txtNombre = (Textbox)wdwConfProducto.getFellow("txtNombre");
			//Decimalbox decPrecio = (Decimalbox)wdwConfProducto.getFellow("decPrecio");
			//Textbox txtDescripcion = (Textbox)wdwConfProducto.getFellow("txtDescripcion");
			
			//ProductoORM obj = (ProductoORM) lbxInv.getSelectedItem().getValue();
			
			//txtCodBarra.setText(obj.getPrd_codigo_barra());
			//txtNombre.setText(obj.getPrd_nombre());
			//decPrecio.setText(obj.getPrd_precio());
			//txtDescripcion.setText(obj.getPrd_descripcion());
			
			//btnGuardar.removeEventListener("onClick", onClick_btnGuardar);
			//btnGuardar.addEventListener("onClick", onClick_btnGuardar);
			//wdwConfProducto.setTitle("Nuevo Producto");
			//wdwConfProducto.removeAttribute(Conf.KEY_APERTURA);
			//wdwConfProducto.setAttribute(Conf.KEY_APERTURA, Conf.KEY_EDITA);
			//wdwConfProducto.doHighlighted();
		}
	};

	
}
