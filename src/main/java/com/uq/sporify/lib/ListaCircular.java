package com.uq.sporify.lib;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Estructura de datos lista circular simple enlazada que almacena los datos
 * que el usuario quiera almacenar y lo recorre de tal forma que el ultimo y el primer nodo sean iguales
 * @param <T> Generico que recibe la estructura de datos
 */
public class ListaCircular<T> implements Iterable<T>, Serializable {
	// Declaracion de variables
    private int tamanio;
    private Nodo<T> actual;

    // Constructor de la clase ListaCircular
    public ListaCircular() {
        tamanio = 0;
        actual = null;
    }

    // Clase privada Nodo, de tipo generica
    private static class Nodo<T> {
        // variables detro de la clase privada
    	T valor;
        Nodo<T> siguiente;
        // Constructor de Nodo
        Nodo(T valor) {
            this.valor = valor;
            siguiente = this;
        }
        // Metodos getter and setter
    	public T getValor() {
    		return valor;
    	}

    	public void setValor(T valor) {
    		this.valor = valor;
    	}

    	public Nodo<T> getSiguiente() {
    		return siguiente;
    	}

    	public void setSiguiente(Nodo<T> siguiente) {
    		this.siguiente = siguiente;
    	}

    }

    // Indica si la lista está vacía o no
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Metodo getTamaño de la clase ListaCircular
    public int getTamanio() {
        return tamanio;
    }

    // Agrega un nuevo nodo con un valor específico a la lista enlazada
    public void agregar(T valor) {
        Nodo<T> nuevo = new Nodo<T>(valor);
        if (estaVacia()) {
            nuevo.siguiente = nuevo;
            actual = nuevo;
        } else {
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    /**
     * Funcion que elimina un nodo específico de la lista enlazada
     * @param valor Generico
     **/
    public void eliminar(T valor) {
        if (!estaVacia()) {
            if (valor.equals(actual.valor)) {
                actual = actual.siguiente;
                tamanio--;
            } else {
                Nodo<T> anterior = actual;
                Nodo<T> siguiente = actual.siguiente;
                while (siguiente != null) {
                    if (valor.equals(siguiente.valor)) {
                        anterior.siguiente = siguiente.siguiente;
                        tamanio--;
                        break;
                    }
                    anterior = siguiente;
                    siguiente = siguiente.siguiente;
                }
            }
        }
    }

    /**
     * Función que devuelve el valor de un nodo en la lista
     * @param indice de la estructura de datos
     * @return El nodo si lo encuentra;
     **/
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) { // Verifica si indice esta dentro del rango
            throw new IndexOutOfBoundsException();
        }
        Nodo<T> buscado = actual.siguiente; // Apunta al siguiente nodo
        for (int i = 0; i < indice; i++) { // Recorre la lista, hasta llegar al nodo deseado
            buscado = buscado.siguiente;
        }
        return buscado.valor;
    }

    /**
     * Permite cambiar el valor de un nodo en la lista enlazada
     * @param indice
     * @param valor
     **/
    public void modificar(int indice, T valor) {
        if (indice < 0 || indice >= tamanio) { // Verifica si indice esta dentro del rango
            throw new IndexOutOfBoundsException();
        }
        Nodo<T> buscado = actual.siguiente; // Apunta al siguiente nodo
        for (int i = 0; i < indice; i++) { // Recorre la lista, hasta llegar al nodo deseado
            buscado = buscado.siguiente;
        }
        buscado.valor = valor;
    }

    /**
     * Permite agregar todos los elementos de una colección a otra
     * @param valores
     **/
    public void addAll(Collection<T> valores) {
        for (T valor : valores) {
            agregar(valor);
        }
    }

    /**
     * Permite recorrer los elementos de una colección
     **/
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
    // Clase pivada Iterator que implementa de Iterator
    private class Iterador implements Iterator<T> , Serializable{
       // Atributos
    	private int posicion;
        private boolean sePuedeEliminar;

        // Constructor
        Iterador() {
            posicion = 0;
            sePuedeEliminar = false;
        }

        //Devuelve un valor booleano que indica si hay más elementos en la colección
        @Override
        public boolean hasNext() {
            return posicion < tamanio;
        }

        /*
         * Devuelve el siguiente elemento de la colección
         * y avanza el iterador al siguiente elemento
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException(); // Excepcion en caso de que no haya elementos
            }
            T valor = actual.siguiente.valor;
            actual = actual.siguiente;
            posicion++;
            sePuedeEliminar = true;
            return valor;
        }

        //Elimina el último elemento devuelto por next()
        @Override
        public void remove() {
            if (!sePuedeEliminar) {
                throw new IllegalStateException();
            }
            eliminar(actual.siguiente.valor);
            sePuedeEliminar = false;
        }
    }
}
