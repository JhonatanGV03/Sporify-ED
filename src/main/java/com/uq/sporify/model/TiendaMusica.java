package com.uq.sporify.model;

import com.uq.sporify.lib.*;
import com.uq.sporify.persistencia.Persistencia;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class TiendaMusica {
	// Declaracion de variables
	private static TiendaMusica tienda;
	ArbolBinario<Artista> listaArtistas;
	HashMapJava<String, Usuario> listaUsuarios;
	ListaCircular<Cancion> listaCanciones;
	Pila<Cancion> cambiosRecientes;

	public static TiendaMusica getInstance() {
		if(tienda==null) {
			tienda = new TiendaMusica();
		}
		return tienda;
	}
	// Segundo constructor de la clase
	private TiendaMusica() {
		super();
		this.cambiosRecientes=null;
		this.listaArtistas=null;
		this.listaUsuarios=null;
		this.listaCanciones=null;
	}

	// Metodos getter and setter
	public TiendaMusica getTienda() {
		return tienda;
	}

	public void setTienda(TiendaMusica tienda) {
		this.tienda = tienda;
	}

	public ArbolBinario<Artista> getListaArtistas() {
		return listaArtistas;
	}

	public void setListaArtistas(ArbolBinario<Artista> listaArtistas) {
		this.listaArtistas = listaArtistas;
	}

	public HashMapJava<String, Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(HashMapJava<String, Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ListaCircular<Cancion> getListaCanciones() {
		return listaCanciones;
	}

	public void setListaCanciones(ListaCircular<Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}

	public Pila<Cancion> getCambiosRecientes() {
		return cambiosRecientes;
	}

	public void setcambiosRecientes(Pila<Cancion> cambiosRecientes) {
		this.cambiosRecientes = cambiosRecientes;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		return Objects.hash(listaArtistas, listaCanciones, listaUsuarios, cambiosRecientes);
	}

	@Override
	// compara dos objetos
	public boolean equals(Object obj) {
		if (this == obj) // Verifica si ambos objetos son el mismo
			return true;
		if (obj == null) // verifica si el objeto pasado como par�metro es nulo
			return false;
		if (getClass() != obj.getClass()) // Verifica si ambos objetos son de la misma clase
			return false;
		TiendaMusica other = (TiendaMusica) obj;
		return Objects.equals(listaArtistas, other.listaArtistas) && Objects.equals(listaCanciones, other.listaCanciones)
				&& Objects.equals(listaUsuarios, other.listaUsuarios) && Objects.equals(cambiosRecientes, other.cambiosRecientes);
	}

	@Override
	public String toString() {
		return "TiendaMusica [listaArtistas=" + listaArtistas + ", listaUsuarios=" + listaUsuarios + ", listaCanciones="
				+ listaCanciones + ", cambiosRecientes=" + cambiosRecientes + "]";
	}

	// deshace cambios realizados en la lista de canciones
	public void deshacerCambio(Cancion cancion) {
		cambiosRecientes.push(cancion);
	}

	// funci�n que recibe un objeto de tipo Canci�n y lo agrega a una pila
	public void eliminarCambio(Cancion cancion) {
		cambiosRecientes.push(cancion);
	}

	// Devuelve el objeto Canci�n en la cima de la pila cambiosRecientes
	public Cancion rehacerCambio() {
		return cambiosRecientes.pop();
	}

	// Recibe un objeto de tipo Artista y lo agrega a la lista de artistas
	public void agregarArtista(Artista artista) {
		listaArtistas.agregar(artista);
	}

	// Recibe un objeto de tipo Artista y lo elimina de la lista de artistas.
	public void eliminarArtista(Artista artista) {
		listaArtistas.eliminar(artista);
	}

	// Recibe un objeto de tipo Cancion y lo agrega a la lista de canciones
	public void guardarCancion(Cancion cancion) {
		listaCanciones.agregar(cancion);
	}

	// Recibe un objeto de tipo Cancion y lo elimina de la lista de canciones
	public void eliminarCancion(Cancion cancion) {
		listaCanciones.eliminar(cancion);
	}

	/*
	 *  Recibe un objeto de tipo Artista y devuelve una lista
	 *  doblemente enlazada de canciones asociadas a ese artista
	 */
	public ListaDobleEnlazada<Cancion> buscarArtista(Artista artista){
		Artista art = listaArtistas.encontrar(artista);
		return art.getListaCanciones();
	}

	/*
	 * Recibe tres atributos y devuelve una lista doblemente enlazada de canciones que cumplen
	 * con al menos uno de los atributos
	 */
	public ListaDobleEnlazada<Cancion> buscarAtributosEnO(String atributo1,String atributo2, String atributo3) {
		ListaDobleEnlazada<Cancion> resultadosBusqueda= new ListaDobleEnlazada<Cancion>();
	    return listaArtistas.buscarO(resultadosBusqueda,atributo1, atributo2, atributo3);
	}

	/*
	 * Recibe tres atributos y devuelve una lista doblemente enlazada de canciones que cumplen con todos los atributos
	 */
	public ListaDobleEnlazada<Cancion> buscarAtributosEnY(String atributo1,String atributo2, String atributo3) {
		ListaDobleEnlazada<Cancion> resultadosBusqueda= new ListaDobleEnlazada<Cancion>();
	    return listaArtistas.buscarY(resultadosBusqueda,atributo1, atributo2, atributo3);
	}

	/*
	 * crea un objeto Usuario vac�o y luego itera
	 * a trav�s de una lista de usuarios y contrase�as
	 * almacenados en un mapa utilizando listas enlazadas
	 */
	public Usuario iniciarSesion(String usuario, String contrasenia) {
		Usuario user = new Usuario();
		Iterator<Map.Entry<String, Usuario>> it = ((Map) listaUsuarios).entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, Usuario> entry = it.next(); // Si el nombre de usuario o la contrase�a
		    String key = entry.getKey(); // coinciden con los valores almacenados en el mapa
		    String value = entry.getValue().getContrasenia();
		    if(usuario.equals(key)||contrasenia.equals(value)) {
		    	user=entry.getValue();
		    }
		}
		return user; // Devuelve el objeto Usuario.
	}

	/*
	 *  Carga informacion desde un recurso XML
	 */
	public void cargarInfo () throws IOException {
		
		TiendaMusica.tienda = Persistencia.cargarRecursoSporifyXML();

	}
	public static void guardarInfo() throws IOException {
		Persistencia.guardarRecursoSporifyXML();
	}
}

