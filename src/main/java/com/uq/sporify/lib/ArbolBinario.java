package com.uq.sporify.lib;

import com.uq.sporify.model.Artista;
import com.uq.sporify.model.Cancion;
import javafx.scene.Node;

import java.beans.XMLEncoder;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
/*
 * Clase que permite al usuario crear una estructura de datos conocida como �rbol
 * binario que cada nodo tiene un apuntador a su hijo izquierdo y su hijo derecho
 *
 * @param <T> Generico
 * @param <E>
 */
public class ArbolBinario  <T extends Comparable<T>>  implements Iterable<T>, Serializable {
	// Declaracion de variables
	private NodoArbolBinario<T> raiz; // Raiz del arbol

	// Constructor de la clase ArbolBinario
	public ArbolBinario() {
        this.raiz = null; // Se inicializa el atributo en null para indicar que inicia vacio
	}

	/*
	 * Metodo para agregar todos los elementos de una coleccion al arbol
	 */
	public void addAll(Collection<T> valores) {
		for(T valor : valores) {
			agregar(valor); // agrega los valores al arbol
		}
	}

	/*
	 * Metodo que invoca el metodo agregar inicializando el parametro de nodo
	 */
	public void agregar(T valor) {
		this.raiz = agregar(this.raiz, valor);
	}

	/**
     * Metodo recursivo que permite agregar los nodos del arbol binario
     * dependiendo si es mayor o menor que el nodo con el que se esta comparando
     * @param nodo
     * @param valor
     * @return
     */
    private NodoArbolBinario<T> agregar(NodoArbolBinario<T> nodo, T valor) {
    	//si el arbol esta vacio o si es una hoja agrega el valor
        if (nodo == null) {
            return new NodoArbolBinario<T>(valor);
        }
        //si el nuevo nodod es menor que el nodo con el que se esta comparando se agrega a la izquierda
        if (valor.compareTo(nodo.obtenerValor()) < 0) {
            nodo.establecerHijoIzquierdo(agregar(nodo.obtenerHijoIzquierdo(), valor));
        //si el nodo nuevo es mayor que el nodo con el que se esta comparando se agrega a la derecha
        } else if (valor.compareTo(nodo.obtenerValor()) > 0) {
            nodo.establecerHijoDerecho(agregar(nodo.obtenerHijoDerecho(), valor));
        }
        return nodo;
    }

    /**
     * Metodo que invoca a la funcion eliminar
     * @param valor
     */
    public void eliminar(T valor) {
        this.raiz = eliminar(this.raiz, valor);
    }

    /*
     * Metodo recursivo que permite eliminar los nodos del arbol binario
     * @param nodo
     * @param valor
     * @return
     */
    private NodoArbolBinario<T> eliminar(NodoArbolBinario<T> nodo, T valor) {
        if (nodo == null) { // Compara si el nodo esta vacio
            return null; // y retorna null es caso de que se cumpla la condicion
        }
        if (valor.compareTo(nodo.obtenerValor()) < 0) {
            nodo.establecerHijoIzquierdo(eliminar(nodo.obtenerHijoIzquierdo(), valor));
        } else if (valor.compareTo(nodo.obtenerValor()) > 0) {
            nodo.establecerHijoDerecho(eliminar(nodo.obtenerHijoDerecho(), valor));
        } else {
            if (nodo.obtenerHijoIzquierdo() == null) {
                return nodo.obtenerHijoDerecho();
            } else if (nodo.obtenerHijoDerecho() == null) {
                return nodo.obtenerHijoIzquierdo();
            }
            NodoArbolBinario<T> minimo = encontrarMinimo(nodo.obtenerHijoDerecho()); // invoca al metodo encontrarMinimo
            nodo.establecerValor(minimo.obtenerValor());
            nodo.establecerHijoDerecho(eliminar(nodo.obtenerHijoDerecho(), minimo.obtenerValor()));
        }
        return nodo;
    }

    /*
     *  Metodo que busca el valor minimo del arbol binario, siempre es el valor
     *  que esta mas a la izquierda
     *  @param nodo
     */
    private NodoArbolBinario<T> encontrarMinimo(NodoArbolBinario<T> nodo) {
        while (nodo.obtenerHijoIzquierdo() != null) {
            nodo = nodo.obtenerHijoIzquierdo();
        }
        return nodo;
    }

    /*
     *  Invoca el metodo buscar
     *  @param valor
     */
    public boolean buscar(T valor) {
        return buscar(this.raiz, valor);
    }

    /*
     * Metodo recursivo que permite buscar los nodos del arbol binario
     * @param nodo
     * @param valor
     * @return
     */
    private boolean buscar(NodoArbolBinario<T> nodo, T valor) {
        if (nodo == null) { // compara si el valor del nodo es null y retorna false
            return false;
        }
        if (valor.compareTo(nodo.obtenerValor()) == 0) {
            return true;
        } else if (valor.compareTo(nodo.obtenerValor()) < 0) {
            return buscar(nodo.obtenerHijoIzquierdo(), valor);
        } else {
            return buscar(nodo.obtenerHijoDerecho(), valor);
        }
    }

    /*
     * Invoca el metodo encontrar
     * @param valor
     */
    public T encontrar(T valor) {
        return encontrar(this.raiz, valor);
    }

    /*
     * Metodo recursivo que permite buscar los nodos del arbol binario,
     * se utiliza para la lista de los artistas
     * @param nodo
     * @param valor
     * @return
     */
    private T encontrar(NodoArbolBinario<T> nodo, T valor) {
        if (nodo == null) { // compara si el valor del nodo es null
            return null;
        }
        if (valor.compareTo(nodo.obtenerValor()) == 0) {
            return valor;
        } else if (valor.compareTo(nodo.obtenerValor()) < 0) {
            return encontrar(nodo.obtenerHijoIzquierdo(), valor);
        } else {
            return encontrar(nodo.obtenerHijoDerecho(), valor);
        }
    }

    /*
     *  Invoca el recorrido preorden del arbol
     *  @param raiz
     */
    public void recorridoPreorden() {
        recorridoPreorden(this.raiz);
    }
    /*
     * Metodo para realizar el recorrido en preorden del arbol binario
     * @param nodo
     */
    private void recorridoPreorden(NodoArbolBinario<T> nodo) {
        if (nodo != null) { // compara si el nodo es diferente de null y realiza las acciones
            System.out.print(nodo.obtenerValor() + " "); // Imprime el valor del nodo mas un mensaje
            recorridoPreorden(nodo.obtenerHijoIzquierdo()); // Obtiene el hijo izquierdo del nodo
            recorridoPreorden(nodo.obtenerHijoDerecho()); // Obtiene el hijo derecho del nodo
        }
    }

    /*
     * Invoca el recorrido inorden del arbol
     * @param raiz
     */
    public void recorridoInorden() {
        recorridoInorden(this.raiz);
    }

    /*
     *Metodo para realizar el recorrido inorden del arbol binario
     *@param nodo
     */
    private void recorridoInorden(NodoArbolBinario<T> nodo) {
        if (nodo != null) { //compara si el nodo es diferente de null y realiza las acciones
            recorridoInorden(nodo.obtenerHijoIzquierdo()); // Obtiene el hijo izquierdo del arbol
            System.out.print(nodo.obtenerValor() + " "); // Imprime el valor del nodo mas un mensaje
            recorridoInorden(nodo.obtenerHijoDerecho()); // Obtiene el hijo derecho del nodo
        }
    }

    /*
     * Invoca el metodo de recorrido posorden del arbol binario
     * @param raiz
     */
    public void recorridoPostorden() {
        recorridoPostorden(this.raiz);
    }

    /*
     *Metodo para realizar el recorrido posorden del arbol binario
     *@param nodo
     */
    private void recorridoPostorden(NodoArbolBinario<T> nodo) {
        if (nodo != null) { //compara si el nodo es diferente de null y realiza las acciones
            recorridoPostorden(nodo.obtenerHijoIzquierdo()); // Obtiene el hijo izquierdo del arbol
            recorridoPostorden(nodo.obtenerHijoDerecho()); // Obtiene el hijo derecho del nodo
            System.out.print(nodo.obtenerValor() + " "); // Imprime el valor del nodo mas un mensaje
        }
    }

    /*
     * Metodo para obtener el tamaño del arbol binario
     */
    public int obtenerTamanio() {
        return obtenerTamanioRecursivo(this.raiz);
    }

    private int obtenerTamanioRecursivo(NodoArbolBinario nodo) {
        if (nodo == null) {
            return 0;
        } else {
            int tamanioIzquierdo = obtenerTamanioRecursivo(nodo.hijoIzquierdo);
            int tamanioDerecho = obtenerTamanioRecursivo(nodo.hijoDerecho);
            return 1 + tamanioIzquierdo + tamanioDerecho;
        }
    }

    /*
     * Metodo publico que invoca la funcion BuscarO
     */
    public ListaDobleEnlazada<Cancion> buscarO(ListaDobleEnlazada<Cancion> resultado,String[] arrayString) {
    	return buscarO(resultado,raiz,arrayString);
    }

    /*
     * Metodo privado que busca canciones que coinciden con alguna descripcion  a traves del thread
     */
    private ListaDobleEnlazada buscarO(ListaDobleEnlazada<Cancion> resultado,NodoArbolBinario raiz,String[] arrayString) {
        if (raiz == null) {
            return resultado;
        }
        // Crear hilos para el lado izquierdo y derecho
        Thread hiloIzquierdo = new Thread(() -> buscarO(raiz.hijoIzquierdo, resultado,arrayString));
        Thread hiloDerecho = new Thread(() -> buscarO(raiz.hijoDerecho, resultado,arrayString));
        // Iniciar los hilos
        hiloIzquierdo.start();
        hiloDerecho.start();
        // Esperar a que ambos hilos terminen su trabajo
        try {
            hiloIzquierdo.join();
            hiloDerecho.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    /*
     * Metodo de buscar en O que busca en el arbol binario que canciones coinciden con
     * al menos un de los atributos ingresados por el usuario y los agrega a una lista de canciones
     */
    private void buscarO(NodoArbolBinario nodo, ListaDobleEnlazada<Cancion> resultado,String[] arrayString) {
    	Artista art = (Artista) nodo.obtenerValor();
    	ListaDobleEnlazada<Cancion> listaCanciones = art.getListaCanciones();
        if (art == null) {
            return;
        }
        for(int i = 0;i< arrayString.length;i++){
            for (Cancion cancion : listaCanciones) {
                if(cancion.equals(arrayString[i])) {
                    resultado.agregarAlFinal(cancion);
                }
            }
        }

        buscarO(nodo.obtenerHijoIzquierdo(), resultado, arrayString);
        buscarO(nodo.obtenerHijoDerecho(), resultado, arrayString);
    }

    /*
     * Metodo publico que invoca la funcion BuscarY
     */
	public ListaDobleEnlazada<Cancion> buscarY(ListaDobleEnlazada<Cancion> resultado,String atributo1,String atributo2,String atributo3) {
		return buscarY(resultado,raiz,atributo1,atributo2,atributo3);
	}

	 /*
     * Metodo privado que busca canciones que coinciden con todas descripcion ingresadas por el usuario a traves del thread
     */
	private ListaDobleEnlazada buscarY(ListaDobleEnlazada<Cancion> resultado,NodoArbolBinario raiz,String atributo1,String atributo2,String atributo3) {
	    if (raiz == null) {
	        return resultado;
	    }

	    // Crear hilos para el lado izquierdo y derecho
	    Thread hiloIzquierdo = new Thread(() -> buscarY(raiz.hijoIzquierdo, resultado,atributo1,atributo2,atributo3));
	    Thread hiloDerecho = new Thread(() -> buscarY(raiz.hijoDerecho, resultado,atributo1,atributo2,atributo3));

	    // Iniciar los hilos
	    hiloIzquierdo.start();
	    hiloDerecho.start();

	    // Esperar a que ambos hilos terminen su trabajo
	    try {
	        hiloIzquierdo.join();
	        hiloDerecho.join();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    return resultado;
	}

	/*
     * Metodo de buscar en Y que busca en el arbol binario que canciones coinciden con
     * todos los atributos ingresados por el usuario y los agrega a una lista de canciones
     */
	private void buscarY(NodoArbolBinario nodo, ListaDobleEnlazada<Cancion> resultado,String atributo1,String atributo2,String atributo3) {
		Artista art = (Artista) nodo.obtenerValor();
		ListaDobleEnlazada<Cancion> listaCanciones = art.getListaCanciones();
	    if (art == null) {
	        return;
	    }
	    for (Cancion cancion : listaCanciones) {
	    	if(cancion.equals(atributo1)&&cancion.equals(atributo2)&&cancion.equals(atributo3)) {
	        	resultado.agregarAlFinal(cancion);
	        }
		}
	    buscarY(nodo.obtenerHijoIzquierdo(), resultado, atributo1, atributo2, atributo3);
	    buscarY(nodo.obtenerHijoDerecho(), resultado, atributo1, atributo2, atributo3);
	}

	/*
	 * Metodo publico del Iterador, devuelve un iterador para recorrer los elementos de un �rbol binario
	 */
    public Iterator<T> iterator() {
        return new IteradorArbolBinario<T>(this.raiz);
    }

    /*
     * Clase publica de NodoArbolBinario que extiende de Comparable
     */
    public class NodoArbolBinario<T extends Comparable<T>> implements Serializable{

    	// Declaracion de variables
        private T valor;
        private NodoArbolBinario<T> hijoIzquierdo;
        private NodoArbolBinario<T> hijoDerecho;

        // Constructor de la clase NodoArbolBinario
        public NodoArbolBinario(T valor) {
            this.valor = valor;
            this.hijoIzquierdo = null;
            this.hijoDerecho = null;
        }

        // Devuelve el valor almacenado en el nodo
        public T obtenerValor() {
            return this.valor;
        }

        // Devuelve el nodo hijo izquierdo del nodo actual
        public NodoArbolBinario<T> obtenerHijoIzquierdo() {
            return this.hijoIzquierdo;
        }

        // Devuelve el nodo hijo derecho del nodo actual
        public NodoArbolBinario<T> obtenerHijoDerecho() {
            return this.hijoDerecho;
        }

        // Establece el valor del nodo con el valor pasado como par�metro
        public void establecerValor(T valor) {
            this.valor = valor;
        }

        // establece el nodo hijo izquierdo del nodo actual con el nodo pasado como par�metro
        public void establecerHijoIzquierdo(NodoArbolBinario<T> nodo) {
            this.hijoIzquierdo = nodo;
        }

        // establece el nodo hijo derecho del nodo actual con el nodo pasado como par�metro
        public void establecerHijoDerecho(NodoArbolBinario<T> nodo) {
            this.hijoDerecho = nodo;
        }
    }

    /*
     * Clase publica denominada IteradorArbolBinario que extiende de Comparable
     * e implementa de Iterator, sirve para recorrer el arbol e insertar los valores
     */
    public class IteradorArbolBinario<T extends Comparable<T>> implements Iterator<T> {

    	// Declaracion de variables dentro la clase
        private Pila<NodoArbolBinario<T>> pila;

        /*
         * En el Constructor se inicializa una pila con el nodo ra�z y todos sus hijos izquierdos.
         * Esto se hace para que el primer elemento devuelto por el iterador
         * sea el nodo m�s a la izquierda del �rbol.
         */
        public IteradorArbolBinario(NodoArbolBinario<T> raiz) {
            this.pila = new Pila<NodoArbolBinario<T>>();
            while (raiz != null) {
                this.pila.push(raiz);
                raiz = raiz.obtenerHijoIzquierdo();
            }
        }

        // El metodo hasNext devuelve verdadero si la pila no est� vac�a.
        public boolean hasNext() {
            return !this.pila.isEmpty();
        }
        /*
         * devuelve el valor del nodo en la parte superior de la pila y luego agrega a la pila todos los
         * hijos izquierdos del hijo derecho del nodo que acaba de ser retirado de la pila.
         * Esto asegura que los nodos se devuelvan en orden.
         */
        public T next() {
            NodoArbolBinario<T> nodo = this.pila.pop();
            T valor = nodo.obtenerValor();
            if (nodo.obtenerHijoDerecho() != null) {
                nodo = nodo.obtenerHijoDerecho();
                while (nodo != null) {
                    this.pila.push(nodo);
                    nodo = nodo.obtenerHijoIzquierdo();
                }
                return valor;
            }
			return valor;
        }
    }
    public ListaDobleEnlazada<Cancion> searchSongsO(String[] attributes) {
        ListaDobleEnlazada<Cancion> result = new ListaDobleEnlazada<>();
        searchSongsHelperO(this.raiz, attributes, result);
        return result;
    }

    private void searchSongsHelperO(NodoArbolBinario node, String[] attributes, ListaDobleEnlazada<Cancion> result) {
        if (node == null) {
            return;
        }
        Artista art = (Artista) node.obtenerValor();
        ListaDobleEnlazada<Cancion> songs = art.getListaCanciones();
        for (Cancion song : songs) {
            for (String attribute : attributes) {
                if (song.toString().contains(attribute)) {
                    result.agregarAlFinal(song);
                    break;
                }
            }
        }
        Thread leftThread = new Thread(() -> searchSongsHelperO(node.hijoIzquierdo, attributes, result));
        Thread rightThread = new Thread(() -> searchSongsHelperO(node.hijoDerecho, attributes, result));
        leftThread.start();
        rightThread.start();
        try {
            leftThread.join();
            rightThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public ListaDobleEnlazada<Cancion> searchSongsY(String[] attributes) {
        ListaDobleEnlazada<Cancion> result = new ListaDobleEnlazada<>();
        searchSongsHelperY(raiz, attributes, result);
        return result;
    }

    private void searchSongsHelperY(NodoArbolBinario node, String[] attributes, ListaDobleEnlazada<Cancion> result) {
        if (node == null) {
            return;
        }
        Artista art = (Artista) node.obtenerValor();
        ListaDobleEnlazada<Cancion> songs = art.getListaCanciones();
        for (Cancion song : songs) {
            boolean match = true;
            for (String attribute : attributes) {
                if (!song.toString().contains(attribute)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                result.agregarAlFinal(song);
            }
        }
        Thread leftThread = new Thread(() -> searchSongsHelperY(node.hijoIzquierdo, attributes, result));
        Thread rightThread = new Thread(() -> searchSongsHelperY(node.hijoDerecho, attributes, result));
        leftThread.start();
        rightThread.start();
        try {
            leftThread.join();
            rightThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
