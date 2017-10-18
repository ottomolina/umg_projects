package com.umg.gt.gestionbodega.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.umg.gt.gestionbodega.orm.ParamORM;
import com.umg.gt.gestionbodega.util.Conf;

public class GeneralDAO {
	
	public <T> List<T> selectStatement(Connection conn, String sql, Class<T> clase, String p_mensaje) throws Exception {
		Conf.mensajeConsola(p_mensaje);
		List<T> lst;
		
		QueryRunner qry = new QueryRunner();
		BeanListHandler<T> blh = new BeanListHandler<T>(clase);
		
		lst = (List<T>) qry.query(conn, sql, blh);
		
		return lst;
	}
	
	public int executeUpdate(Connection conn, String p_mensaje, String SQL) throws Exception {
		Conf.mensajeConsola(p_mensaje);
		Statement st = conn.createStatement();
		int i = st.executeUpdate(SQL);
		closeStatements(st, null);
		return i;
	}
	
	public String executeQuery(Connection conn, String p_mensaje, String SQL) throws Exception {
		Conf.mensajeConsola(p_mensaje);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(SQL);
		String ret = "";
		if(rs.next()){ ret = rs.getString(1); }
		closeStatements(st, rs);
		return ret;
	}
	
	/** 
	 * Cierra los statements utilizados en cada llamada a base de datos
	 * @param st
	 * @param rs
	 * @throws Exception
	 */
	public static void closeStatements(java.sql.Statement st, ResultSet rs) throws Exception {
		st.close();
		if(rs != null){
			rs.close();
		}
	}
	
	public List<ParamORM> obtieneParametros(Connection conn, String p_nombreParametro) throws Exception {
		String SQL = "select codigo, nombre_parametro, valor_parametro ";
		SQL = SQL + "   from gb_param ";
		SQL = SQL + "  where nombre_parametro = '" + p_nombreParametro + "' ";
		SQL = SQL + "  order by codigo asc ";
		
		return selectStatement(conn, SQL, ParamORM.class, "obtieneParametros(): " + SQL);
	} 
}
