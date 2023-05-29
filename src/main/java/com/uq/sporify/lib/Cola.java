package com.uq.sporify.lib;

import java.io.Serializable;

public class Cola <T> implements Serializable {
    // Atribtos
    private Nodo<T> primero;
    private Nodo<T> ultimo;

    // Metodo constructor de la clase Cola
    public Cola() {
        primero = null;
        ultimo = null;
    }

    // Metodos getter and setter
    public Nodo<T> getPrimero() {
        return primero;
    }

    public void setPrimero(Nodo<T> primero) {
        this.primero = primero;
    }

    public Nodo<T> getUltimo() {
        return ultimo;
    }

    public void setUltimo(Nodo<T> ultimo) {
        this.ultimo = ultimo;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    // Metodo para encolar
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<T>(dato);
        if (estaVacia()) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
    }

    // Metodo para desencolar
    public T desencolar() {
        if (estaVacia()) {
            return null;
        } else {
            T dato = primero.getValor();
            primero = primero.getSiguiente();
            if (primero == null) {
                ultimo = null;
            }
            return dato;
        }
    }

    // Tamaño de la cola
    public int tamanio() {
        int tamanio = 0;
        Nodo<T> actual = primero;
        while (actual != null) {
            tamanio++;
            actual = actual.getSiguiente();
        }
        return tamanio;
    }
}