package com.umg.gt.gestionbodega.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.umg.gt.gestionbodega.orm.InventarioORM;

public class InventarioDAO extends GeneralDAO{
	
	public List<InventarioORM> getListaInventarioEntrada(Connection conn, InventarioORM obj, Boolean blTipo) throws Exception{
		String SQL = "SELECT BOD_CODIGO,";
		SQL += SQL + "        INV_TIPO, ";
		SQL = SQL + "        LOT_CODIGO, ";
		SQL = SQL + "        INV_CODIGO, ";
		SQL = SQL + "        PRD_CODIGO, ";
		SQL = SQL + " 		 INV_CANTIDAD, ";
		SQL = SQL + "TO_CHAR(INV_FCH_CREADO,'dd/MM/yyyy') AS INV_FCH_CREADO, ";
		SQL = SQL + "        INV_USER_CREADO, ";
		SQL = SQL + "   FROM GB_INVENTARIO WHERE 1 = 1 ";
		
		if(blTipo == true)
		{
			SQL = SQL + "	AND INV_TIPO = 'ENTRADA' ";
		}
		else
		{
			SQL = SQL + "	AND INV_TIPO = 'SALIDA' ";
		}
		
		System.out.println("getListaProducto(): " + SQL);
		QueryRunner qr = new QueryRunner();
		BeanListHandler<InventarioORM> blh = new BeanListHandler<InventarioORM>(InventarioORM.class);
		return qr.query(conn, SQL, blh);
	}
}
