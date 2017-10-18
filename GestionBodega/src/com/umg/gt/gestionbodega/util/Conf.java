package com.umg.gt.gestionbodega.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

public class Conf {
	
	public static final String JNDI_BODEGA = "conn_bodega";
	
	public static final String KEY_APERTURA = "K-AP";
	public static final String KEY_NUEVO 	= "1";
	public static final String KEY_EDITA 	= "2";
	public static final String KEY_CONSULTA = "3";
	
	//objetos para formatear fechas
	public static final String TXT_FORMATO_FECHAHORA = "dd/MM/yyyy HH:mm";
	public static final String TXT_FORMATO_FECHA = "dd/MM/yyyy";
	public static final String TXT_FORMATO_HORA = "HH:mm";
	public static final String TXT_FORMATO_FECHAHORA_REPORTE = "ddMMyyyy_HHmm";

	public static final SimpleDateFormat FORMATO_FECHA_HORA = new SimpleDateFormat(TXT_FORMATO_FECHAHORA);
	public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat(TXT_FORMATO_FECHA);
	public static final SimpleDateFormat FORMATO_HORA = new SimpleDateFormat(TXT_FORMATO_HORA);
	public static final SimpleDateFormat FORMATO_FECHAHORA_REP = new SimpleDateFormat(TXT_FORMATO_FECHAHORA_REPORTE);
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,###,###.00");
	
	/**Listado de Meses */
	public static final String mes[] = {"enero", "febrero", "marzo", "abril", "mayo","junio", "julio", "agosto", "septiembre", "octubre", 
		"noviembre", "diciembre"};
	
	// Variable que maneja la impresion en consola de la aplicacion
	public static final boolean console = true;
	
	/****
	 * Muestra un mensaje en la consola del IDE si esta activo el parametro.
	 * @param p_mensaje
	 */
	public static void mensajeConsola(String p_mensaje){
		if(console){
			System.out.println(p_mensaje);
		}
	}
	
	public static boolean isEmailValido(String correo) {  
		//creamos una copia de la cadena para que no nos afecte despues de las conversiones
		String copiaCorreo = correo;
		int indice = copiaCorreo.indexOf("@"); 
		if(indice==-1){//si no encuentra la arroba en la cadena retornamos false
			return false;
		}
		copiaCorreo = copiaCorreo.substring(indice+1, correo.length());
		indice = copiaCorreo.indexOf("@");
		if(indice!=-1){//si encuentra una segunda arroba retornamos false
			return false;
		}
		Pattern pat = null;        
		Matcher mat = null;
		pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z]{1,9}.)+[a-zA-Z]{2,3})$");        
		mat = pat.matcher(correo);        
		if (mat.find()) {     
			return true;        
		}else{            
			return false;        
		}            
	}
	/**
	 *  GOMR 28/02/2017 VALIDA QUE EN EL TEXTO NO VENGAN CARACTERES ESPECIALES 
	 *  RETORNA FALSE SI ENCUENTRA UN CARACTER INVALIDO
	 *  */
	public static boolean isTextoValido(String texto){
		for(int i=0; i<texto.length(); i++){
			char c = texto.charAt(i);
			
			if(Character.isSpaceChar(c)){
				continue;
			}
			String excepcion = "аимсзя,.;:-_";
			if(excepcion.toUpperCase().indexOf(String.valueOf(c)) > -1){
				continue;
			}
			
			if(!Character.isLetter(c)){
				if(!Character.isDigit(c)){
					return false;
				}
			}
		}
		return true;
	}
	
	/** GOMR 28/02/2017 VALIDA QUE EN EL TEXTO VAYAN UNICA Y EXCLUSIVAMENTE LETRAS DEL ABECEDARIO */
	public static boolean isOnlyLetters(String texto){
		for(int i=0; i<texto.length(); i++){
			char c = texto.charAt(i);
			
			if(Character.isSpaceChar(c)){
				continue;
			}
			String excepcion = "я";
			if(String.valueOf(c).toUpperCase().indexOf(excepcion) > -1){
				continue;
			}
			
			if(!Character.isLetter(c)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNumero(String texto, String v){
		for(int i=0; i<texto.length(); i++){
			char c = texto.charAt(i);
			
			if(!Character.isDigit(c)){
				if(v.toUpperCase().equals("NIT")){
					if(!Character.toString(c).equals("-")){
						return false;
					}
				}else{
					return false;
				}
			}
		}
		return true;
	}
	
	public static String generaPassword(String url){
		 return DigestUtils.md5Hex(url);
	}
	
}
