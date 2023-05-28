package com.uq.sporify.model;

import com.uq.sporify.lib.ListaDobleEnlazada;

import java.util.Objects;
import java.util.Random;

public class Artista implements Comparable<Artista>{
    // Declaracion de variables
    private String nombre;
    private String nacionalidad;
    private String codigo;
    private String grupo;
    private int reproducciones;
    private ListaDobleEnlazada<Cancion> listaCanciones;

    // Constructor de la clase Astista
	public Artista(String nombre, String nacionalidad, String grupo,
			ListaDobleEnlazada<Cancion> listaCanciones) {
		super();
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.codigo = generarCodigo();
		this.grupo = grupo;
		this.listaCanciones = listaCanciones;
		this.reproducciones=0;
	}

	// Segundo constructor de la clase
	public Artista() {
		super();
		this.nombre="";
		this.nacionalidad="";
		this.codigo="";
		this.grupo="";
		this.listaCanciones=null;
		this.reproducciones=0;
	}

	// Metodos getter and setter
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		nacionalidad = nacionalidad;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public ListaDobleEnlazada<Cancion> getListaCanciones() {
		return listaCanciones;
	}
	public void setListaCanciones(ListaDobleEnlazada<Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}

	public int getReproducciones() {
		return reproducciones;
	}
	public void setReproducciones(int reproducciones) {
		this.reproducciones = reproducciones;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Recibe un objeto de tipo Cancion y lo agrega al final de una lista de canciones
    public void agregarCancion(Cancion cancion) {
    	Boolean bandera = false;
		for(Cancion song:listaCanciones)
		{
			if (cancion.comprobar(song)){
				bandera = true;
			}

		}
		if(bandera == false){
			listaCanciones.agregarAlFinal(cancion);

		}
    }

    // Verifica si la lista esta vacia
	public boolean estaVacia() {
		return listaCanciones.estaVacia();
	}

	// Recibe un índice y elimina el elemento correspondiente de la lista de canciones
	public void eliminar(int indice) {
		listaCanciones.eliminar(indice);
	}

	public void eliminarPorValor (String codCancion)
	{
		for (int i = 0; i<listaCanciones.tamanio();i++){
			String aux = listaCanciones.obtener(i).getCodigo();
			if (aux.equals(codCancion)){
				listaCanciones.eliminar(i);
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(nacionalidad, codigo, grupo, listaCanciones, nombre, reproducciones);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artista other = (Artista) obj;
		return Objects.equals(nacionalidad, other.nacionalidad) && Objects.equals(codigo, other.codigo)
				&& Objects.equals(grupo, other.grupo) && Objects.equals(listaCanciones, other.listaCanciones)
				&& Objects.equals(nombre, other.nombre) && reproducciones == other.reproducciones;
	}

	@Override
	// compara el nombre de dos objetos Artista y devuelve un valor entero basado en la comparación
	public int compareTo(Artista otroArtista) {
		return this.nombre.compareTo(otroArtista.getNombre());
	}

	// Devuelve una cadena de seis caracteres alfanuméricos aleatorios
	private String generarCodigo() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int indice = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(indice));
        }
        return codigo.toString();
    }

}

