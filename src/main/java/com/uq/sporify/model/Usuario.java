package com.uq.sporify.model;

import com.uq.sporify.lib.ListaCircular;
import com.uq.sporify.lib.Pila;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
@XmlRootElement
public class Usuario implements Serializable {
    private String usuario;
    private String contrasenia;
    private String email;
    private ListaCircular<Cancion> listaCanciones;
    private Pila<Cancion> cambiosRecientes;
    private String favoritos;
    public String getUsuario() {
        return usuario;
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

    public void guardarCancion(Cancion cancion) {
        listaCanciones.agregar(cancion);
        actualizarFavoritos();

    }

    public void eliminarCancion(Cancion cancion) {
        listaCanciones.eliminar(cancion);
        actualizarFavoritos();
        //cambiosRecientes.push(cancion);
    }



    public void deshacer(Cancion cancion) {
        cambiosRecientes.push(cancion);
    }
    public Cancion rehacer(){
        return cambiosRecientes.pop();
    }
    public Pila<Cancion> getCambiosRecientes()
    {
        return cambiosRecientes;
    }
    public Usuario(String usuario, String contrasenia, String email) {
        super();
        this.cambiosRecientes=new Pila<>();
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
        this.cambiosRecientes = new Pila<>();
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


    public void ordenarPorAtributo(String atributo) {
        Comparator<Cancion> comparator = null;
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
                return;
        }
        mergeSort(listaCanciones, comparator);
    }

    private static <T> void mergeSort(ListaCircular<T> lista, Comparator<? super T> c) {
        if (lista.getTamanio() > 1) {
            int mitad = lista.getTamanio() / 2;
            ListaCircular<T> izquierda = new ListaCircular<>();
            ListaCircular<T> derecha = new ListaCircular<>();
            for (int i=0;i<mitad; i++) {
                izquierda.agregar(lista.obtener(i));
            }
            for (int i=mitad;i<lista.getTamanio();i++) {
                derecha.agregar(lista.obtener(i));
            }
            mergeSort(izquierda, c);
            mergeSort(derecha, c);
            merge(izquierda, derecha, lista, c);
        }
    }

    private static <T> void merge(ListaCircular<T> izquierda, ListaCircular<T> derecha, ListaCircular<T> lista, Comparator<? super T> c) {
        int indiceIzq = 0;
        int indiceDer = 0;
        int indiceUlt = 0;
        while (indiceIzq < izquierda.getTamanio() && indiceDer < derecha.getTamanio()) {
            if (c.compare(izquierda.obtener(indiceIzq), derecha.obtener(indiceDer)) < 0) {
                lista.modificar(indiceUlt++, izquierda.obtener(indiceIzq++));
            } else {
                lista.modificar(indiceUlt++, derecha.obtener(indiceDer++));
            }
        }
        while (indiceIzq < izquierda.getTamanio()) {
            lista.modificar(indiceUlt++, izquierda.obtener(indiceIzq++));
        }
        while (indiceDer < derecha.getTamanio()) {
            lista.modificar(indiceUlt++, derecha.obtener(indiceDer++));
        }
    }
}
