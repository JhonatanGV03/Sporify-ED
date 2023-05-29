package com.uq.sporify.persistencia;

import com.uq.sporify.lib.ArbolBinario;
import com.uq.sporify.lib.ListaDobleEnlazada;
import com.uq.sporify.model.Artista;
import com.uq.sporify.model.Cancion;
import com.uq.sporify.model.TiendaMusica;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class ArchivoUtil {
//metodo definitivo para guardarLista de Objetos en XML
    public static <T> void guardarListaObjetosXML(ListaDobleEnlazada<T> listaObjetos, String nombreArchivo) {
        try {
            // Crear un objeto FileOutputStream para escribir en el archivo.
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            // Crear un objeto XMLEncoder para codificar los objetos en formato XML.
            XMLEncoder encoder = new XMLEncoder(fileOut);

            // Iterar sobre los elementos de la lista y escribir cada objeto en formato XML.
            for (int i = 0; i < listaObjetos.tamanio(); i++) {
                T objeto = listaObjetos.obtener(i);
                encoder.writeObject(objeto);
            }
            // Cerrar los flujos de salida.
            encoder.close();
            fileOut.close();
            System.out.println("Lista de objetos guardada en el archivo " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }

    //metodo definitivo para leer un xml y guardarlo en la lista de objetos
    public static <T> ListaDobleEnlazada<T> leerListaObjetosXML(String nombreArchivo) {
        ListaDobleEnlazada<T> listaObjetos = new ListaDobleEnlazada<>();
        try {
            // Crear un objeto FileInputStream para leer el archivo.
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            // Crear un objeto XMLDecoder para decodificar los objetos desde XML.
            XMLDecoder decoder = new XMLDecoder(fileIn);

            // Leer los objetos del archivo XML hasta que se alcance el final del archivo.
            while (true) {
                try {
                    T objeto = (T) decoder.readObject();
                    listaObjetos.agregarAlFinal(objeto);
                } catch (ArrayIndexOutOfBoundsException e) {
                    // Se alcanzó el final del archivo XML.
                    break;
                }
            }
            // Cerrar los flujos de entrada.
            decoder.close();
            fileIn.close();
            System.out.println("Lista de objetos leída del archivo " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
        return listaObjetos;
    }

    // Metodo recibe un file como parametro, y recorre el archivo y lo carga
    public static void cargarInformacion(File archivo) throws FileNotFoundException {
        ListaDobleEnlazada<Artista> artistas = new ListaDobleEnlazada<>();
        TiendaMusica sporify = TiendaMusica.getInstance();
        Scanner scanner = new Scanner(archivo);
        Artista artistaActual = null;
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            if (linea.startsWith("#Artistas")) { // lee la informacion del artista
                continue;
            } else if (linea.startsWith("#Canciones")) { // lee la informacion de canciones
                continue;
            }
            String[]  campos = linea.split(";");
            if (campos.length == 4) { // Leyendo informacion del artista
                String codigo = campos[0];
                String nombre = campos[1];
                String nacionalidad = campos[2];
                boolean esGrupo = Boolean.parseBoolean(campos[3]);
                artistaActual = new Artista(codigo, nombre, nacionalidad, esGrupo);
                ArbolBinario<Artista> auxArbol= sporify.getListaArtistas();
                sporify.agregarArtista(artistaActual);
            } else if (campos.length == 8) { // Leyendo la informacion de una cancion
                String codCancion = campos[0];
                String nombreArtista = campos[1];
                String nombreCancion = campos[2];
                String nombreAlbum = campos[3];
                String anio = campos[4];
                int duracion = Integer.parseInt(campos[5]);
                String genero = campos[6];
                String urlCancionYoutube = campos[7];
                Cancion cancion = new Cancion(codCancion, nombreCancion, nombreArtista, "", anio, urlCancionYoutube, duracion, genero, nombreAlbum);
                sporify.guardarCancion(cancion);
                Iterator<Artista> iterator = artistas.iterator();
                while (iterator.hasNext()) {
                    Artista artista = iterator.next();
                    if (artista.getNombre().equals(nombreArtista)) {
                        artista.agregarCancion(cancion);
                        break;
                    }
                }
            }
        }
        scanner.close();
        sporify.agregarCancionesArtista();
    }
}