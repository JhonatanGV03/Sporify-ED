package com.uq.sporify.persistencia;

import com.uq.sporify.lib.ArbolBinario;
import com.uq.sporify.lib.HashMapJava;
import com.uq.sporify.lib.ListaCircular;
import com.uq.sporify.lib.ListaDobleEnlazada;
import com.uq.sporify.model.Artista;
import com.uq.sporify.model.Cancion;
import com.uq.sporify.model.TiendaMusica;
import com.uq.sporify.model.Usuario;

import java.util.*;

public class Persistencia {

	public static final String RUTA_ARCHIVO_ARTISTAS = "xml/artistas.xml";
	public static final String RUTA_ARCHIVO_CANCIONES = "xml/canciones.xml";
	public static final String RUTA_ARCHIVO_USUARIOS = "xml/usuarios.xml";
	
public static void cargarRecursoSporifyXML() {
	TiendaMusica sporify = TiendaMusica.getInstance();
	ArbolBinario<Artista> listaArtistas = new ArbolBinario<>();
	ListaCircular<Cancion> listaCanciones = new ListaCircular<>();
	HashMap<String,Usuario> listaUsuarios = new HashMap<>();
	ListaDobleEnlazada<Artista> auxArt = ArchivoUtil.leerListaObjetosXML(RUTA_ARCHIVO_ARTISTAS);
	ListaDobleEnlazada<Cancion> auxCanciones = ArchivoUtil.leerListaObjetosXML(RUTA_ARCHIVO_CANCIONES);
	ListaDobleEnlazada<Usuario> auxUsuarios = ArchivoUtil.leerListaObjetosXML(RUTA_ARCHIVO_USUARIOS);

	for (Artista art:auxArt){
		listaArtistas.agregar(art);
	}
	for (Cancion cancion : auxCanciones){
		listaCanciones.agregar(cancion);
	}
	for (Usuario usuario: auxUsuarios){
		listaUsuarios.put(usuario.getUsuario(), usuario);
	}
	sporify.setListaArtistas(listaArtistas);
	sporify.setListaUsuarios(listaUsuarios);
	sporify.setListaCanciones(listaCanciones);
	//return sporify;
	}

 public static void guardarRecursoSporifyXML() {
	 TiendaMusica sporify = TiendaMusica.getInstance();
	 ListaDobleEnlazada<Artista> auxArt = new ListaDobleEnlazada<>();
	 ListaDobleEnlazada<Usuario> auxUsuarios = new ListaDobleEnlazada<>();
	 ListaDobleEnlazada<Cancion> auxCanciones = new ListaDobleEnlazada<>();
	 ArbolBinario<Artista> listaArtistas= sporify.getListaArtistas();
	 ListaCircular<Cancion> listaCanciones = sporify.getListaCanciones();
	 HashMap<String,Usuario> listaUsuarios = sporify.getListaUsuarios();
	 for (Artista art: listaArtistas){
		 auxArt.agregarAlFinal(art);
	 }
	 for (Cancion cancion : listaCanciones){
		 auxCanciones.agregarAlFinal(cancion);
	 }

	 Iterator<Map.Entry<String, Usuario>> it = (listaUsuarios).entrySet().iterator();
	 while (it.hasNext()) {
		 Map.Entry<String, Usuario> entry = it.next(); // Si el nombre de usuario o la contrase�a
		 String key = entry.getKey(); // coinciden con los valores almacenados en el mapa
		 Usuario value = entry.getValue();
		 auxUsuarios.agregarAlFinal(value);
	 }

ArchivoUtil.guardarListaObjetosXML(auxArt,RUTA_ARCHIVO_ARTISTAS);
ArchivoUtil.guardarListaObjetosXML(auxUsuarios,RUTA_ARCHIVO_USUARIOS);
ArchivoUtil.guardarListaObjetosXML(auxCanciones,RUTA_ARCHIVO_CANCIONES);
 }
}
