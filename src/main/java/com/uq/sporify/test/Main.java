package com.uq.sporify.test;

import com.uq.sporify.lib.ArbolBinario;
import com.uq.sporify.lib.HashMapJava;
import com.uq.sporify.lib.ListaCircular;
import com.uq.sporify.lib.ListaDobleEnlazada;
import com.uq.sporify.model.Artista;
import com.uq.sporify.model.Cancion;
import com.uq.sporify.model.TiendaMusica;
import com.uq.sporify.model.Usuario;
import com.uq.sporify.persistencia.Persistencia;

import java.util.HashMap;
import java.util.Map;


public class Main {
	/**
	ArbolBinario<Artista> listaArtistas;
	HashMapJava<String, Usuario> listaUsuarios;
	ListaCircular<Cancion> listaCanciones;
	Pila<Cancion> cambiosRecientes;
	ListaCircular<Cancion> resultadosBusqueda;
	Persistencia persistencia;
	*/
	public static void main(String[] args) {
		 //Crea la Tienda

	TiendaMusica sporify = TiendaMusica.getInstance();
/**
		//Artistas
		ArbolBinario<Artista> listaArtistas = new ArbolBinario<>();
		ListaDobleEnlazada<Cancion> listaCancionesCancerbero = new ListaDobleEnlazada<>();
		Cancion epico = new Cancion("epico","cancerbero","","","",10,"rap","");
		Cancion tripolar = new Cancion("tripolar","cancerbero","","","",10,"rap","");
		listaCancionesCancerbero.agregarAlFinal(epico); // Agrega la cancion al final de la lista
		listaCancionesCancerbero.agregarAlFinal(tripolar); // Agrega la cancion al final de la lista
		Artista art1 = new Artista("cancerbero","venezuela",false);
		art1.setListaCanciones(listaCancionesCancerbero);
		listaArtistas.agregar(art1); // Agrega al artista


		ListaCircular<Cancion> listaCanciones = new ListaCircular<>();
		listaCanciones.agregar(tripolar);
		listaCanciones.agregar(epico);


		//Usuario
		Usuario user = new Usuario("user","123","user@gmail.com");
		user.guardarCancion(tripolar);
		user.guardarCancion(epico);
		HashMap<String, Usuario> listaUsuarios = new HashMap<>();
		listaUsuarios.put(user.getUsuario(), user);

		//Asignacion
		sporify.setListaArtistas(listaArtistas);
		sporify.setListaCanciones(listaCanciones);
		sporify.setListaUsuarios(listaUsuarios);
*/
		//TiendaMusica sporify = TiendaMusica.getInstance();
			try {
				//sporify.guardarInfo();
				sporify.cargarInfo();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		/**for (Map.Entry<String,Usuario> usr:listaUsuarios.entrySet()){
			System.out.println();
			System.out.println(usr.getValue().getFavoritos());
		}*/
		//TiendaMusica sporify = TiendaMusica.getInstance();
		//Imprime la informacion
		//String[] atributos = new String[3];
		//atributos[0] = "eminem";
		//atributos[1] = "slim shady";
		//atributos[2] = "cancerbero";
		//ListaDobleEnlazada<Cancion> resultados = new ListaDobleEnlazada<>();
		//sporify.agregarCancionesArtista();
		//System.out.println(sporify.testBuscarO("papa, JHCruz,Nach"));
		//System.out.println(sporify.getListaCanciones());
		//System.out.println(sporify.getListaArtistas().obtenerTamanio());
		System.out.println(sporify.getListaUsuarios().get("user").getListaCanciones().obtener(-1));
		//sporify.getListaUsuarios().get("user").eliminarCancion();
		//sporify.getListaUsuarios().get("user").eliminarCancion();
		System.out.println(sporify.getListaUsuarios().get("user").getListaCanciones().obtener(-1));
	}
}
