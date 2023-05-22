package com.uq.sporify.persistencia;

import com.uq.sporify.model.TiendaMusica;

import java.io.File;

public class Persistencia {

	private static File archivo = new File("com/uq/sporify/persistencia/sporify.xml");
	private static String path = archivo.getAbsolutePath();
	public static final String RUTA_ARCHIVO_TIENDA = "/com/uq/sporify/persistencia/sporify.xml";
	
public static TiendaMusica cargarRecursoSporifyXML() {
	System.out.println(path);
	TiendaMusica sporify = TiendaMusica.getInstance();
	System.out.println(sporify);
	sporify= (TiendaMusica) ArchivoUtil.leerObjetoDesdeXML(path);
	return sporify;
	}

 public static void guardarRecursoSporifyXML() {
	 TiendaMusica sporify = TiendaMusica.getInstance();
	 ArchivoUtil.escribirObjetoAXML(sporify, path);
 }
}