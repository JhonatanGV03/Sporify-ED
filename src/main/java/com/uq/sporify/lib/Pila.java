package com.uq.sporify.lib;

import java.io.Serializable;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * Estructura de datos de tipo Stack Firts In Last Out
 * @param <T>
 */
public class Pila<T> implements Serializable {
	// Declaracion de variables
    private NodoPila<T> cima;

    /**
     *Constructor de la clase Pila
     */
    public Pila() {
        cima = null;
    }

    /**
     * Agrega un elemento a una pila
     * @param elemento
     */
    public void push(T elemento) {
        NodoPila<T> nuevoNodo = new NodoPila<>(elemento);
        if (isEmpty()) {
            cima = nuevoNodo;
        } else {
            nuevoNodo.siguiente = cima;
            cima = nuevoNodo;
        }
    }

    /**
     * Elimina y devuelve el elemento en la parte superior de la pila
     * @return
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T elemento = cima.elemento;
        cima = cima.siguiente;
        return elemento;
    }

    /**
     * Devuelve el objeto en la parte superior de la pila
     * @return
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return cima.elemento;
    }

    /**
     * Recibe una colección de valores y agrega cada valor al final
     * @param valores
     */
    public void addAll(Collection<T> valores) {
        for (T valor : valores) {
            push(valor);
        }
    }

    // Verifica si la lista esta vacia
    public boolean isEmpty() {
        return cima == null;
    }

    // Tamaño de la pila (cuenta los elementos)
    public int size() {
        int contador = 0;
        NodoPila<T> nodoActual = cima;
        while (nodoActual != null) {
            contador++;
            nodoActual = nodoActual.siguiente;
        }
        return contador;
    }

    //

    /**
     *
     */
    public void vaciar() {
        cima = null;
    }

    /**
     * Clase privada estatica NodoPila
     * @param <T>
     */
    private static class NodoPila<T> implements Serializable {
    	// Atributos
        private T elemento;
        private NodoPila<T> siguiente;

        /**
         * Constructor
         * @param elemento
         */
        public NodoPila(T elemento) {
            this.elemento = elemento;
            siguiente = null;
        }
    }
}

