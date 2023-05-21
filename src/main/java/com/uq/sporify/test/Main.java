package com.uq.sporify.test;

import com.uq.sporify.lib.ArbolBinario;
import com.uq.sporify.lib.HashMapJava;
import com.uq.sporify.lib.ListaCircular;
import com.uq.sporify.lib.ListaDobleEnlazada;
import com.uq.sporify.model.Artista;
import com.uq.sporify.model.Cancion;
import com.uq.sporify.model.Usuario;

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
		// Crea la Tienda
		//1 TiendaMusica sporify = new TiendaMusica();
		//Artistas
		ArbolBinario<Artista> listaArtistas = new ArbolBinario<>();
		ListaDobleEnlazada<Cancion> listaCancionesCancerbero = new ListaDobleEnlazada<>();
		Cancion epico = new Cancion("epico","cancerbero","","","",10,"rap","",Cancion.generarCodigo());
		Cancion tripolar = new Cancion("tripolar","cancerbero","","","",10,"rap","",Cancion.generarCodigo());
		listaCancionesCancerbero.agregarAlFinal(epico); // Agrega la cancion al final de la lista
		listaCancionesCancerbero.agregarAlFinal(tripolar); // Agrega la cancion al final de la lista
		Artista art1 = new Artista("cancerbero","venezuela","solo",listaCancionesCancerbero);
		listaArtistas.agregar(art1); // Agrega al artista


		ListaCircular<Cancion> listaCanciones = new ListaCircular<>();
		listaCanciones.agregar(tripolar);
		listaCanciones.agregar(epico);

		//Usuario
		ListaCircular<Cancion> playlist = new ListaCircular<>();
		playlist.agregar(tripolar);
		Usuario user = new Usuario("user","123","user@gmail.com",playlist);
		HashMapJava<String, Usuario> listaUsuarios = new HashMapJava<>();
		listaUsuarios.agregar(user.getUsuario(), user);

		//2
		//Asignacion
		//sporify.setListaArtistas(listaArtistas);
		//sporify.setListaCanciones(listaCanciones);
		//sporify.setListaUsuarios(listaUsuarios);

		//3 try {
			//TiendaMusica.guardarInfo(sporify);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

		//4 Imprime la informacion
		//System.out.println(sporify.getListaArtistas().encontrar(art1).getListaCanciones().obtener(0));
		//System.out.println(sporify.getListaUsuarios().obtener("user").getListaCanciones().obtener(0).getArtista());
	}
}
