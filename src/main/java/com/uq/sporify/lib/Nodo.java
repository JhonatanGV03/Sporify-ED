package com.uq.sporify.lib;

public class Nodo <T> {
    // Declaracion de variables
	T valor;
    Nodo siguiente;
    Nodo anterior;

    // Constructor de la clase Nodo
    public Nodo(T valor) {
       this.valor = valor;
       this.siguiente = null;
       this.anterior = null;
    }

    // Metodos getter and setter
	public T getValor() {
		return valor;
	}

	public void setValor(T valor) {
		this.valor = valor;
	}

	public Nodo<T> getSiguiente() {
		return getSiguiente();
	}

	public void setSiguiente(Nodo<?> siguiente) {
		this.siguiente = siguiente;
	}

	public Nodo<?> getAnterior() {
		return anterior;
	}

	public void setAnterior(Nodo<?> anterior) {
		this.anterior = anterior;
	}

 }
