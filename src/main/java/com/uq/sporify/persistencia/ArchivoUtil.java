package com.uq.sporify.persistencia;

import com.uq.sporify.lib.ListaDobleEnlazada;
import com.uq.sporify.model.TiendaMusica;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class ArchivoUtil {
	public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

		XMLDecoder decodificadorXML;
		Object objetoXML;
		
		decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
		objetoXML = decodificadorXML.readObject();
		decodificadorXML.close();
		return objetoXML;
		
	}

	public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {
		
		XMLEncoder codificadorXML;
		
		codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
		codificadorXML.writeObject(objeto);
		codificadorXML.close();
		
	}
	public static String crearArchivoXML(String rutaCompleta) throws IOException {
	    File archivoXML = new File(rutaCompleta);
	    archivoXML.createNewFile();
	    return archivoXML.getAbsolutePath();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ListaDobleEnlazada<T> cargarRecursoSerializadoXMLListado(String rutaArchivo) throws IOException {
		ListaDobleEnlazada<T> listaObjetos;
		XMLDecoder decodificadorXML;
		decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
		listaObjetos = (ListaDobleEnlazada<T>) decodificadorXML.readObject();
		decodificadorXML.close();
		return (ListaDobleEnlazada<T>) listaObjetos;
	}

	public static <T> void salvarRecursoSerializadoXMLListado(String rutaArchivo, ListaDobleEnlazada<T> listaObjetos) throws IOException {
		XMLEncoder codificadorXML;
		codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
		codificadorXML.writeObject(listaObjetos);
		codificadorXML.close();
	}
	public static boolean existe(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
	
	public static void escribirObjetoAXML(Object objeto, String nombreArchivo) {
        try {
            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write("<" + objeto.getClass().getSimpleName() + ">\n");
            escribirAtributos(objeto, writer);
            escribirObjetosAnidados(objeto, writer);
            escribirListasDeObjetos(objeto, writer);
            writer.write("</" + objeto.getClass().getSimpleName() + ">");
            writer.close();
            System.out.println("Archivo XML escrito correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void escribirAtributos(Object objeto, FileWriter writer) throws Exception {
        Field[] campos = objeto.getClass().getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            String nombreCampo = campo.getName();
            Object valorCampo = campo.get(objeto);
            writer.write("\t<" + nombreCampo + ">" + valorCampo + "</" + nombreCampo + ">\n");
        }
    }

    private static void escribirObjetosAnidados(Object objeto, FileWriter writer) throws Exception {
        Field[] campos = objeto.getClass().getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            Object valorCampo = campo.get(objeto);
            if (valorCampo != null && !campo.getType().isPrimitive()) {
                writer.write("\t<" + campo.getName() + ">\n");
                escribirAtributos(valorCampo, writer);
                writer.write("\t</" + campo.getName() + ">\n");
            }
        }
    }

    private static void escribirListasDeObjetos(Object objeto, FileWriter writer) throws Exception {
        Field[] campos = objeto.getClass().getDeclaredFields();
        for (Field campo : campos) {
            campo.setAccessible(true);
            Object valorCampo = campo.get(objeto);
            if (valorCampo instanceof List) {
                List<?> lista = (List<?>) valorCampo;
                for (Object elemento : lista) {
                    writer.write("\t<" + campo.getName() + ">\n");
                    escribirAtributos(elemento, writer);
                    writer.write("\t</" + campo.getName() + ">\n");
                }
            }
        }
    }
   

    private static Object convertirValorCampo(Class<?> tipoCampo, String valorCampo) {
        if (tipoCampo.equals(String.class)) {
            return valorCampo;
        } else if (tipoCampo.equals(int.class) || tipoCampo.equals(Integer.class)) {
            return Integer.parseInt(valorCampo);
        } else if (tipoCampo.equals(double.class) || tipoCampo.equals(Double.class)) {
            return Double.parseDouble(valorCampo);
        } else if (tipoCampo.equals(boolean.class) || tipoCampo.equals(Boolean.class)) {
            return Boolean.parseBoolean(valorCampo);
        } else if (tipoCampo.equals(char.class) || tipoCampo.equals(Character.class)) {
            return valorCampo.charAt(0);
        } else {
            return null; // Manejar otros tipos de campo según sea necesario
        }
    }

    public static TiendaMusica leerObjetoDesdeXML(String nombreArchivo) {
        try {
            File archivo = new File(nombreArchivo);
            JAXBContext jaxbContext = JAXBContext.newInstance(TiendaMusica.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (TiendaMusica) unmarshaller.unmarshal(archivo);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
}