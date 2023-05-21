package com.uq.sporify.lib;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMapJava<K, V> implements Iterable<HashMapJava.Entry<K, V>> {
	// Declaracion de varriables
    private static final int CAPACIDAD_POR_DEFECTO = 16;
    private Entry<K, V>[] tabla;
    private int tamanio;


    @SuppressWarnings("unchecked")
    /*
     *  Constructor de la clase HasMapJava que no contiene argumentos
     */
    public HashMapJava() {
        tabla = new Entry[CAPACIDAD_POR_DEFECTO];
        tamanio = 0;
    }

    @SuppressWarnings("unchecked")
    /*
     *  Constructor de la clase HasMapJava que contiene
     *  un argumentos entero denominado valor
     */
    public HashMapJava(int valor) {
        tabla = new Entry[valor];
        tamanio = 0;
    }

    public int tamanio() {
        return tamanio;
    }

    /*
     *  Devuelve true si el mapa no contiene ningún par clave-valor
     */
    public boolean estaVacia() {
        return tamanio == 0;
    }

    /*
     *  metodo que recibe una llave como parámetro y devuelve un valor
     *  booleano que indica si la tabla hash contiene esa llave o no
     */
    public boolean contieneLLave(K llave) {
        int hashCode = llave.hashCode();
        int index = hashCode % tabla.length;
        Entry<K, V> entry = tabla[index];
        while (entry != null) {
            if (entry.key.equals(llave)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    /*
     *  método que recibe una llave como parámetro y devuelve un valor
     *  booleano que indica si la tabla hash contiene esa valor o no
     */
    public boolean contieneValor(V valor) {
        for (int i = 0; i < tabla.length; i++) { // Recorre la tabla
            Entry<K, V> entry = tabla[i];
            while (entry != null) {
                if (entry.value.equals(valor)) { // Si encuentra el valor retorna true
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    /*
     * Devuelve el valor asociado a la llave en la tabla hash, calcula el índice de la tabla hash utilizando
     * la función hash de la llave, Si encuentra una entrada con la misma llave
     * devuelve el valor asociado a esa entrada, de lo contrario, devuelve null.
     */
    public V obtener(K llave) {
        int hashCode = llave.hashCode();
        int index = hashCode % tabla.length;
        Entry<K, V> entry = tabla[index];
        while (entry != null) {
            if (entry.key.equals(llave)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /*
     * Metodo que agrega una entrada a la tabla hash. Si encuentra
     * una entrada con la misma llave, actualiza el valor asociado a esa entrada
     * y devuelve el valor antiguo
     */
    public V agregar(K llave, V valor) {
        int hashCode = llave.hashCode();
        int index = hashCode % tabla.length;
        Entry<K, V> entry = tabla[index];
        while (entry != null) {
            if (entry.key.equals(llave)) {
                V oldValue = entry.value;
                entry.value = valor;
                return oldValue;
            }
            entry = entry.next;
        }
        Entry<K, V> newEntry = new Entry<K, V>(llave, valor);
        newEntry.next = tabla[index];
        tabla[index] = newEntry;
        tamanio++;
        return null;
    }

    /*
     * Metodo que recibe una colección de entradas y agrega cada entrada a la tabla hash
     * a traves de la funcion agregar
     */
    public void addAll(Collection<Entry<K,V>> valores) {
        for (Entry<K, V> entry : tabla) {
			agregar(entry.key,entry.value);
		}
    }

    /*
     * Metodo que elimina una entrada de la tabla hash
     * si existe una entrada con la llave especificada
     */
    public V eliminar(K llave) {
        int hashCode = llave.hashCode();
        int index = hashCode % tabla.length;
        Entry<K, V> entry = tabla[index];
        Entry<K, V> prevEntry = null;
        while (entry != null) {
            if (entry.key.equals(llave)) {
                if (prevEntry == null) {
                    tabla[index] = entry.next;
                } else {
                    prevEntry.next = entry.next;
                }
                tamanio--;
                return entry.value;
            }
            prevEntry = entry;
            entry = entry.next;
        }
        return null;
    }

    /*
     * Metodo que vacía la tabla hash eliminando todas
     * las entradas y estableciendo el tamaño en cero (0)
     */
    public void vaciar() {
        for (int i = 0; i < tabla.length; i++) { // Recorre la tabla
            tabla[i] = null; // Elimina la entrada dentro del indice
        }
        tamanio = 0;
    }

    // Metodo que se utiliza para recorrer los elementos de la tabla hash
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    public static class Entry<K, V> {
    	// Declaracion de variables
        private K key;
        private V value;
        private Entry<K, V> next;

        // Constructor de Entry
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        // Devuelve la clave
        public K getKey() {
            return key;
        }

        // Devuelve el valor
        public V getValue() {
            return value;
        }

        // Establece el valor y devuelve el valor antiguo
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        // Devuelve el siguiente nodo
        public Entry<K, V> getNext() {
            return next;
        }

        // Establece el siguiente nodo
        public void setNext(Entry<K, V> next) {
            this.next = next;
        }

        // Establece la clave de la entrada en el valor especificado
		public void setKey(K key) {
			this.key = key;
		}
    }

    /*
     *  Clase privada HashMapIterator, implementa de Iterator
     */
    private class HashMapIterator implements Iterator<Entry<K, V>> {
    	// Declaracion de las variables
        private int currentIndex = -1;
        private Entry<K, V> currentEntry = null;
        private Entry<K, V> lastReturnedEntry = null;



		@SuppressWarnings("unused")
		public void setLastReturnedEntry(Entry<K, V> lastReturnedEntry) {
			this.lastReturnedEntry = lastReturnedEntry;
		}

		/*
		 * Comprueba si hay más elementos en la tabla hash para recorrer
		 */
		public boolean hasNext() {
            if (currentEntry != null && currentEntry.getNext() != null) {
                return true;
            }
            for (int i = currentIndex + 1; i < tabla.length; i++) {
                if (tabla[i] != null) {
                    return true;
                }
            }
            return false;
        }

		/*
		 * Devuelve el siguiente elemento en la tabla hash y actualiza el estado del iterador
		 */
        public Entry<K, V> next() {
            if (currentEntry != null && currentEntry.getNext() != null) {
                currentEntry = currentEntry.getNext();
            } else {
                do {
                    currentIndex++;
                    if (currentIndex >= tabla.length) {
                        throw new NoSuchElementException();
                    }
                    currentEntry = tabla[currentIndex];
                } while (currentEntry == null);
            }
            lastReturnedEntry = currentEntry;
            return lastReturnedEntry;
        }

        @SuppressWarnings("unused")
        /*
         * elimina el último elemento devuelto por next() de la tabla hash
         */
		public void eliminar() {
            if (lastReturnedEntry == null) {
                throw new IllegalStateException();
            }
            HashMapJava.this.eliminar(lastReturnedEntry.getKey());
            lastReturnedEntry = null;
        }
    }
}
