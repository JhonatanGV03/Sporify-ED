package com.uq.sporify.model;

import com.uq.sporify.lib.ListaCircular;
import com.uq.sporify.lib.Pila;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@XmlRootElement
public class Usuario implements Serializable {
    // Atributos
    private String usuario;
    private String contrasenia;
    private String email;
    private ListaCircular<Cancion> listaCanciones;
    private Pila<Cancion> cambiosPosteriores;
    private Pila<Cancion> cambiosAnteriores;
    private String favoritos;
    public String getUsuario() {
        return usuario;
    }

    // Constructor de la clase
    public Usuario(String usuario, String contrasenia, String email) {
        super();
        this.cambiosAnteriores=new Pila<>();
        this.cambiosPosteriores=new Pila<>();
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.email = email;
        this.listaCanciones = new ListaCircular<Cancion>();
    }

    public Usuario() {
        super();
        this.usuario="";
        this.contrasenia ="";
        this.email="";
        this.listaCanciones= new ListaCircular<>();
        this.cambiosPosteriores = new Pila<>();
        this.cambiosAnteriores = new Pila<>();
    }
    @Override
    public int hashCode() {
        return Objects.hash(contrasenia, email, listaCanciones, usuario);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(email, other.email)
                && Objects.equals(listaCanciones, other.listaCanciones) && Objects.equals(usuario, other.usuario);
    }
    @Override
    public String toString() {
        return "Usuario [usuario=" + usuario + ", contraseña=" + contrasenia + ", email=" + email + ", listaCanciones="
                + listaCanciones + "]";
    }

    public String getFavoritos() {
        return favoritos;
    }

    public void actualizarFavoritos(){
        String aux="";
        for(Cancion canciones: listaCanciones){
           aux+= canciones.getCodigo()+";";
        }
        this.setFavoritos(aux);
    }
    public Boolean existeCancion(Cancion cancion){
    Boolean bandera = false;
    for(Cancion song:listaCanciones){
        if(song.comprobar(cancion)){
            bandera = true;
        }
    }
    return bandera;
    }

    // Metodos getter and setter
    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ListaCircular<Cancion> getListaCanciones() {
        return listaCanciones;
    }

    public void setListaCanciones(ListaCircular<Cancion> listaCanciones) {
        this.listaCanciones = listaCanciones;
    }

    // Agrega la cancion a la lista
    public void guardarCancion(Cancion cancion) {
        listaCanciones.agregar(cancion);
        actualizarFavoritos();
    }

    // Elimina la cancion de la lista
    public void eliminarCancion(Cancion cancion) {
        listaCanciones.eliminar(cancion);
        actualizarFavoritos();
    }

    public void ordenarPorAtributo(String atributo) {
        // Se crea un objeto Comparator vacío inicialmente
        Comparator<Cancion> comparator = null;
        // Se utiliza un switch para determinar el atributo de comparación y asignar el Comparator correspondiente
        switch(atributo.toLowerCase()) {
            case "nombre":
                comparator = Comparator.comparing(Cancion::getNombre);
                break;
            case "artista":
                comparator = Comparator.comparing(Cancion::getArtista);
                break;
            case "duracion":
                comparator = Comparator.comparingInt(Cancion::getDuracion);
                break;
            case "codigo":
                comparator = Comparator.comparing(Cancion::getCodigo);
                break;
            case "genero":
                comparator = Comparator.comparing(Cancion::getGenero);
                break;
            case "año":
                comparator = Comparator.comparing(Cancion::getAnio);
                break;
            case "album":
                comparator = Comparator.comparing(Cancion::getAlbum);
                break;
            default:
                System.out.println("Atributo no válido");
                return; // Si el atributo no es válido, se muestra un mensaje y se retorna sin hacer nada
        }

        // Se llama al método mergeSort para ordenar la lista de canciones utilizando el Comparator específico
        mergeSort(listaCanciones, comparator);
    }

    // Implementación del algoritmo de ordenamiento merge sort
    private static <T> void mergeSort(ListaCircular<T> lista, Comparator<? super T> c) {
        if (lista.getTamanio() > 1) {
            int mitad = lista.getTamanio() / 2;
            ListaCircular<T> izquierda = new ListaCircular<>();
            ListaCircular<T> derecha = new ListaCircular<>();
            // Se divide la lista original en dos sublistas, izquierda y derecha
            for (int i=0; i<mitad; i++) {
                izquierda.agregar(lista.obtener(i));
            }
            for (int i=mitad; i<lista.getTamanio(); i++) {
                derecha.agregar(lista.obtener(i));
            }

            // Se aplica recursivamente mergeSort a las sublistas izquierda y derecha
            mergeSort(izquierda, c);
            mergeSort(derecha, c);

            // Se realiza la fusión (merge) de las sublistas izquierda y derecha en la lista original
            merge(izquierda, derecha, lista, c);
        }
    }

    // Método para fusionar dos sublistas ordenadas en una lista
    private static <T> void merge(ListaCircular<T> izquierda, ListaCircular<T> derecha, ListaCircular<T> lista, Comparator<? super T> c) {
        int indiceIzq = 0;
        int indiceDer = 0;
        int indiceUlt = 0;
        // Se comparan los elementos de las sublistas y se van agregando a la lista en orden
        while (indiceIzq < izquierda.getTamanio() && indiceDer < derecha.getTamanio()) {
            if (c.compare(izquierda.obtener(indiceIzq), derecha.obtener(indiceDer)) < 0) {
                // Si el elemento de la sublista izquierda es menor, se agrega a la lista final
                lista.modificar(indiceUlt++, izquierda.obtener(indiceIzq++));
            } else {
                // Si el elemento de la sublista derecha es menor, se agrega a la lista final
                lista.modificar(indiceUlt++, derecha.obtener(indiceDer++));
            }
        }
        // Si alguna sublista aún tiene elementos restantes, se agregan a la lista final
        while (indiceIzq < izquierda.getTamanio()) {
            lista.modificar(indiceUlt++, izquierda.obtener(indiceIzq++));
        }
        while (indiceDer < derecha.getTamanio()) {
            lista.modificar(indiceUlt++, derecha.obtener(indiceDer++));
        }
    }
    public Pila<Cancion> getPreviusStates(){
        return cambiosAnteriores;
    }
    // Devuelve la pila cambiosAnteriores que contiene los estados anteriores de las canciones.

    public Pila<Cancion> getNextStates(){
        return cambiosPosteriores;
    }
    // Devuelve la pila cambiosPosteriores que contiene los estados posteriores de las canciones.

    public void addPreviusStates(Cancion cancion){
        cambiosAnteriores.push(cancion);
    }
    // Agrega una canción al estado anterior. La canción se agrega a la pila cambiosAnteriores utilizando el método push.

    public void addNextStates(Cancion cancion){
        cambiosPosteriores.push(cancion);
    }
    // Agrega una canción al estado posterior. La canción se agrega a la pila cambiosPosteriores utilizando el método push.

    public void ClearNextStates(){
        cambiosPosteriores.vaciar();
    }
    // Vacía la pila cambiosPosteriores, eliminando todos los estados posteriores de las canciones.

    public Cancion deletePreviusStates(){
        return cambiosAnteriores.pop();
    }
    // Elimina y devuelve la canción en el estado anterior más reciente. Utiliza el método pop para eliminar y devolver el elemento superior de la pila cambiosAnteriores.

    public Cancion deleteNextStates(){
        return cambiosPosteriores.pop();
    }
    // Elimina y devuelve la canción en el estado posterior más reciente. Utiliza el método pop para eliminar y devolver el elemento superior de la pila cambiosPosteriores.

}
