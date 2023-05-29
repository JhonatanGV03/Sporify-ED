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

		try {
			sporify.cargarInfo();
			System.out.println();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("------------------------------------Test 2: Agregar Canciones-----------------------------------------");
		System.out.println();
	for(Cancion cancion: sporify.getListaCanciones()){
		System.out.println(cancion.getNombre());
		System.out.println();
	}
	Cancion pura_droga_sin_cortar = new Cancion("pura_drogra_sin_cortar","Kase-O","","2016","https://www.youtube.com/watch?v=B3GiCGz0gXM",10,"Rap","Circulo");
		System.out.println();
	sporify.guardarCancion(pura_droga_sin_cortar);

	for(Cancion cancion: sporify.getListaCanciones()){
		System.out.println(cancion.getNombre());
		System.out.println();
	}
		System.out.println("------------------------------------Test 3: Buscar por Hilo O-----------------------------------------");
		System.out.println();
	for(Cancion cancion:sporify.testBuscarO("Cancerbero,Eminem,Rock")){
		System.out.println(cancion.getNombre());
		System.out.println();
	}
		System.out.println("------------------------------------Test 4: Buscar Canciones Artista-----------------------------------------");
		System.out.println();
	for(Cancion cancion:sporify.retornarArtista("Kase-O").getListaCanciones()){
		System.out.println(cancion);
		System.out.println();
	}
		System.out.println("------------------------------------Test 5: Buscar por Hilo Y-----------------------------------------");
		System.out.println();
	for(Cancion cancion:sporify.testBuscarY("Ivy,Pop")){
		System.out.println(cancion);
		System.out.println();
	}
		System.out.println("------------------------------------Test 6: Iniciar Seccion-----------------------------------------");
		System.out.println();

	System.out.println(sporify.iniciarSesion("eric","123"));
		System.out.println();

		System.out.println("------------------------------------Test 7: Crear Cuenta-----------------------------------------");
		System.out.println();
	Usuario user = new Usuario("eric","123","eric@gmail.com");
	sporify.agregarUsuario(user);
	System.out.println(sporify.iniciarSesion(user.getUsuario(),user.getContrasenia()));
		System.out.println("------------------------------------Test 8: Genero Tendencia-----------------------------------------");
		System.out.println();
		System.out.println(sporify.mostrarGeneroTendendencia());
		System.out.println();
		System.out.println("------------------------------------Todos los Test Aprobados-----------------------------------------");

	}
}
