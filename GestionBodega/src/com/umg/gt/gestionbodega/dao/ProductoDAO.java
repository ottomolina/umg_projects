package com.umg.gt.gestionbodega.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.umg.gt.gestionbodega.orm.ProductoORM;

public class ProductoDAO extends GeneralDAO {
	
	public List<ProductoORM> getListaProducto(Connection conn, ProductoORM obj) throws Exception{
		String SQL = "SELECT PRD_CODIGO, ";
		SQL = SQL + "        PRD_CODIGO_BARRA, ";
		SQL = SQL + "        PRD_NOMBRE, ";
		SQL = SQL + "        PRD_DESCRIPCION, ";
		SQL = SQL + "        PRD_PRECIO, ";
		SQL = SQL + "TO_CHAR(PRD_FCH_CREADO,'dd/MM/yyyy') AS PRD_FCH_CREADO, ";
		SQL = SQL + "        PRD_USER_CREADO, ";
		SQL = SQL + "TO_CHAR(PRD_FCH_MODIFICACION,'dd/MM/yyyy') AS PRD_FCH_MODIFICACION, ";
		SQL = SQL + "        PRD_USER_MODIFICACION ";
		SQL = SQL + "   FROM GB_PRODUCTO WHERE 1=1 ";
		
		if(obj != null)
		{
			if(obj.getPrd_codigo_barra() != ""){
				SQL = SQL + " AND PRD_CODIGO_BARRA = " + obj.getPrd_codigo_barra();
			}
			if(obj.getPrd_nombre() != ""){
				SQL = SQL + " AND PRD_NOMBRE = '" + obj.getPrd_nombre() + "'";
			}
			if(obj.getPrd_precio() != ""){
				SQL = SQL + " AND PRD_PRECIO = " + obj.getPrd_precio();
			}
		}
		System.out.println("getListaProducto(): " + SQL);
		QueryRunner qr = new QueryRunner();
		BeanListHandler<ProductoORM> blh = new BeanListHandler<ProductoORM>(ProductoORM.class);
		return qr.query(conn, SQL, blh);
	}
	
	public String obtieneSecuencia(Connection conn) throws Exception {
		String SQL = " select nvl(max(prd_codigo), 0) + 1 from gb_producto ";
		
		String ret = executeQuery(conn, "obtieneSecuencia() " + SQL, SQL);
		
		return ret;
	}
	
	public int insertaProducto(Connection conn, ProductoORM obj, String p_usuario) throws Exception {
		String SQL = "insert into gb_producto( PRD_CODIGO, " +
											"PRD_CODIGO_BARRA, " +
											"PRD_NOMBRE, " +
											"PRD_DESCRIPCION, " +
											"PRD_PRECIO, " +
											"PRD_FCH_CREADO, " +
											"PRD_USER_CREADO, " +
											"PRD_FCH_MODIFICACION, " +
											"PRD_USER_MODIFICACION) " +
					" VALUES (  " + obj.getPrd_codigo() + ", " +
								"'" + obj.getPrd_codigo_barra() + "', " +
								"'" + obj.getPrd_nombre() + "', " +
								"'" + obj.getPrd_descripcion() + "', " +
								"'" + obj.getPrd_precio() + "', " +
								"    sysdate, " +
								"'" + p_usuario + "'," +
								" null, " +
								" null ) ";
		int i = executeUpdate(conn, "insertaProducto() " + SQL, SQL);
		return i;
	}
	
	public int actualizaProducto(Connection conn, ProductoORM obj, String p_usuario) throws Exception {
		String SQL = "UPDATE GB_PRODUCTO SET " +
						" PRD_NOMBRE = '" + obj.getPrd_nombre() + "', " +
						" PRD_DESCRIPCION = '" + obj.getPrd_descripcion() + "', " +
						" PRD_PRECIO = '" + obj.getPrd_precio() + "', " +
						" PRD_USER_MODIFICACION = '" + p_usuario + "', " +
						" PRD_FCH_MODIFICACION = sysdate " +
					" WHERE PRD_CODIGO = " + obj.getPrd_codigo() + " ";
		
		int i = executeUpdate(conn, "actualizaProducto() " + SQL, SQL);
		return i;			
	}

}
