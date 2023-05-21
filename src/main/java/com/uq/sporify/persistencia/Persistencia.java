package com.uq.sporify.persistencia;

import com.uq.sporify.model.TiendaMusica;

public class Persistencia {
	
	public static final String RUTA_ARCHIVO_TIENDA = "src/persistencia/sporify.xml";
	
public static TiendaMusica cargarRecursoSporifyXML() {
	TiendaMusica sporify = TiendaMusica.getInstance();
	sporify= (TiendaMusica) ArchivoUtil.leerObjetoDesdeXML(RUTA_ARCHIVO_TIENDA);
	return sporify;
	}

 public static void guardarRecursoSporifyXML() {
	 TiendaMusica sporify = TiendaMusica.getInstance();
	 ArchivoUtil.escribirObjetoAXML(sporify, RUTA_ARCHIVO_TIENDA);
 }
}