package com.uq.sporify.lib;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaDobleEnlazada<T> implements Iterable<T>, Serializable {
	// Declaracion de variables
	private Nodo primero;
	private Nodo ultimo;
	private int tamanio;

	/*
	 * Constructor de la clase ListaDobleEnlazada
	 */
	public ListaDobleEnlazada() {
		this.primero = null;
		this.ultimo = null;
		this.tamanio = 0;
	}

	/*
	 * Metodos getter and setter
	 */
	public Nodo getPrimero() {
		return primero;
	}

	public void setPrimero(Nodo primero) {
		this.primero = primero;
	}

	/*
	 * agrega un valor al inicio de una lista doblemente enlazada
	 * La función toma un valor de tipo genérico T
	 */
	public void agregarAlInicio(T valor) {
		Nodo<T> nuevo = new Nodo<T>(valor);
		if (this.primero == null) { //Si la lista está vacía
			this.primero = nuevo; // el nuevo nodo se convierte en el primero
			this.ultimo = nuevo; // y último nodo de la lista
		} else {
			nuevo.siguiente = this.primero;
			this.primero.anterior = nuevo;
			this.primero = nuevo;
		}
		this.tamanio++; // Incrementa el tamaño de la lista
	}

	/*
	 * Agrega un valor al final de una lista doblemente enlazada
	 * La función toma un valor de tipo genérico T
	 */
	public void agregarAlFinal(T valor) {
		Nodo<T> nuevo = new Nodo<T>(valor);
		if (this.ultimo == null) {
			this.ultimo = nuevo;
			this.primero = nuevo;
		} else {
			nuevo.anterior = this.ultimo;
			this.ultimo.siguiente = nuevo;
			this.ultimo = nuevo;
		}
		this.tamanio++;
	}

	/*
	 * inserta un valor en una lista doblemente enlazada en una posición específica
	 */
	public void insertar(int indice, T valor) {
		if (indice < 0 || indice > this.tamanio) { // Si el índice está fuera de los límites de la lista
			throw new IndexOutOfBoundsException("El índice está fuera de los límites de la lista.");
		}
		if (indice == 0) { // Si el índice es cero, el valor se agrega al inicio
			agregarAlInicio(valor);
		} else if (indice == this.tamanio) { // i el índice es igual al tamaño de la lista, el valor se agrega al final
			agregarAlFinal(valor);
		} else { // crea un nuevo nodo con el valor proporcionado y se inserta en la posición especifica
			Nodo<T> nuevo = new Nodo<T>(valor);
			Nodo<T> actual = this.primero;
			for (int i = 0; i < indice - 1; i++) {
				actual = actual.siguiente;
			}
			nuevo.siguiente = actual.siguiente;
			nuevo.anterior = actual;
			actual.siguiente.anterior = nuevo;
			actual.siguiente = nuevo;
			this.tamanio++; // Incrementa el tamaño de la lista
		}
	}

	// Métodos básicos para eliminar elementos de la lista

	// Elimina al Inicio de la lista
	public void eliminarAlInicio() {
		if (this.primero == null) { // Si la lista esta vacia lanza una excepcion
			throw new NoSuchElementException("La lista está vacía.");
		}
		if (this.tamanio == 1) {
			this.primero = null;
			this.ultimo = null;
		} else {
			this.primero = this.primero.siguiente;
			this.primero.anterior = null;
		}
		this.tamanio--; // Disminuye el tamaño de la lista
	}

	// Elimina al final de lista
	public void eliminarAlFinal() {
		if (this.ultimo == null) { // Si la lista esta vacia lanza una excepcion
			throw new NoSuchElementException("La lista está vacía.");
		}
		if (this.tamanio == 1) {
			this.primero = null;
			this.ultimo = null;
		} else {
			this.ultimo = this.ultimo.anterior;
			this.ultimo.siguiente = null;
		}
		this.tamanio--; // Disminuye el tamaño de la lista
	}

	// Elimina un valor de la lista doblemente enlazada en una posición específica
	public void eliminar(int indice) {
		if (indice < 0 || indice >= this.tamanio) { //  si no esta en los limites de la lista, lanza la excepcion
			throw new IndexOutOfBoundsException("El índice está fuera de los límites de la lista.");
		}
		if (indice == 0) {
			eliminarAlInicio();
		} else if (indice == this.tamanio - 1) {
			eliminarAlFinal();
		} else {
			Nodo<T> actual = this.primero;
			for (int i = 0; i < indice; i++) {
				actual = actual.siguiente;
			}
			actual.anterior.siguiente = actual.siguiente;
			actual.siguiente.anterior = actual.anterior;
			this.tamanio--;
		}
	}

	// Otros métodos útiles

	// recupera un valor de una lista doblemente enlazada en un índice específico
	public T obtener(int indice) {
		if (indice < 0 || indice >= this.tamanio) { //  si no esta en los limites de la lista, lanza la excepcion
			throw new IndexOutOfBoundsException("El índice está fuera de los límites de la lista.");
		}
		Nodo<T> actual = this.primero;
		for (int i = 0; i < indice; i++) {
			actual = actual.siguiente;
		}
		return actual.getValor();
	}

	// Tamaño de la lista
	public int tamanio() {
		return this.tamanio;
	}

	// Verifica si la lista esta vacia
	public boolean estaVacia() {
		return this.tamanio == 0;
	}

	// Limpia una lista doblemente enlazada estableciendo los nodos
	public void limpiar() {
		this.primero = null;
		this.ultimo = null;
		this.tamanio = 0;
	}

	//  Recibe una colección de valores y agrega cada valor al final
	public void addAll(Collection<T> valores) {
        for (T valor : valores) {
            agregarAlFinal(valor);
        }
    }

	public String toString() {
		if (this.tamanio == 0) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder("[");
		Nodo<T> actual = this.primero;
		while (actual != null) {
			sb.append(actual.valor.toString());
			if (actual.siguiente != null) {
				sb.append(", ");
			}
			actual = actual.siguiente;
		}
		sb.append("]");
		return sb.toString();
	}

	public Iterator<T> iterator() {
		return new IteradorLista();
	}


	// Clase interna privada para el iterador
	private class IteradorLista implements Iterator<T>, Serializable {
		private Nodo<T> actual;

		public IteradorLista() {
			this.actual = primero;
		}

		@Override
		public boolean hasNext() {
			return this.actual != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new IndexOutOfBoundsException("No hay más elementos en la lista.");
			}
			T valor = (T) actual.valor;
			actual = actual.siguiente;
			return valor;
		}
	}

	// Invoca la funcion recorrer_recursivo
	public void recorrer_recursivo() {
		recorrer_recursivo(primero);
	}

	// Recorre una lista doblemente enlazada de manera recursiva.
	public void recorrer_recursivo (Nodo<T> n){
		if (n != null) {
			System.out.println(n.getValor());
			recorrer_recursivo (n.getSiguiente());
		}
	}
}

