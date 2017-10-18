package com.umg.gt.gestionbodega.dao;

import java.sql.Connection;
import java.util.List;

import com.umg.gt.gestionbodega.orm.AlmacenORM;

public class AlmacenDAO extends GeneralDAO {
	
	public String obtieneSecuencia(Connection conn) throws Exception {
		String SQL = " select nvl(max(bod_codigo), 0) + 1 from gb_bodega ";
		
		String ret = executeQuery(conn, "obtieneSecuencia() " + SQL, SQL);
		
		return ret;
	}
	
	public int insertaAlmacen(Connection conn, AlmacenORM obj, String p_usuario) throws Exception {
		String SQL = "insert into gb_bodega( BOD_CODIGO, " +
											"BOD_NOMBRE, " +
											"BOD_DESCRIPCION, " +
											"BOD_DIRECCION, " +
											"BOD_FCH_CREADO, " +
											"BOD_USER_CREADO, " +
											"BOD_FCH_MODIFICACION, " +
											"BOD_USER_MODIFICACION) " +
					" VALUES (  " + obj.getBod_codigo() + ", " +
								"'" + obj.getBod_nombre() + "', " +
								"'" + obj.getBod_descripcion() + "', " +
								"'" + obj.getBod_direccion() + "', " +
								"    sysdate, " +
								"'" + p_usuario + "'," +
								" null, " +
								" null ) ";
		int i = executeUpdate(conn, "insertaAlmacen() " + SQL, SQL);
		return i;
	}
	
	public List<AlmacenORM> obtieneListaAlmacen(Connection conn, AlmacenORM filtro) throws Exception {
		String SQL = "SELECT BOD_CODIGO, " +
							"BOD_NOMBRE, " +
							"BOD_DESCRIPCION, " +
							"BOD_DIRECCION, " +
							"/*TO_CHAR(BOD_FCH_CREADO,'dd/MM/yyyy') AS*/ BOD_FCH_CREADO, " +
							"BOD_USER_CREADO, " +
							"BOD_FCH_MODIFICACION, " +
							"BOD_USER_MODIFICACION " +
					" FROM GB_BODEGA ";
		return selectStatement(conn, SQL, AlmacenORM.class, "obtieneListaAlmacen() " + SQL);
	}
	
	public int actualizaAlmacen(Connection conn, AlmacenORM obj, String p_usuario) throws Exception {
		String SQL = "UPDATE GB_BODEGA SET " +
						" BOD_NOMBRE = '" + obj.getBod_nombre() + "', " +
						" BOD_DESCRIPCION = '" + obj.getBod_descripcion() + "', " +
						" BOD_DIRECCION = '" + obj.getBod_direccion() + "', " +
						" BOD_USER_MODIFICACION = '" + p_usuario + "', " +
						" BOD_FCH_MODIFICACION = sysdate " +
					" WHERE BOD_CODIGO = " + obj.getBod_codigo() + " ";
		
		int i = executeUpdate(conn, "actualizaAlmacen() " + SQL, SQL);
		return i;			
	}
	
	public int eliminaAlmacen(Connection conn, String p_bod_codigo, String p_usuario) throws Exception {
		String SQL = "DELETE FROM GB_BODEGA " +
					" WHERE BOD_CODIGO = " + p_bod_codigo + " ";
		
		int i = executeUpdate(conn, "eliminaAlmacen() " + SQL, SQL);
		return i;			
	}
	
}
