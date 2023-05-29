package com.uq.sporify.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
@XmlRootElement
public class Cancion implements Serializable {
    // Declaración de variables
    private String codigo;
    private String nombre;
    private String artista;
    private String caratula;
    private int anio;
    private String urlYoutube;
    private int duracion; // en segundos
    private String genero;
    private String album;

    // Constructor super de la clase Cancion
    public Cancion(String nombre, String artista, String caratula, String anio, String urlYoutube,
				   int duracion, String genero, String album) {
		super();
		this.codigo = generarCodigo();
		this.nombre = nombre;
		this.artista = artista;
		this.caratula = setearCaratula(caratula);
		this.anio = Integer.parseInt(anio);
		this.urlYoutube = urlYoutube;
		this.duracion = duracion;
		this.genero = genero;
		this.album = album;
	}
	public Cancion(String codigo, String nombre, String artista, String caratula, String anio, String urlYoutube,
				   int duracion, String genero, String album) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.artista = artista;
		this.caratula = setearCaratula(caratula);
		this.anio = Integer.parseInt(anio);
		this.urlYoutube = urlYoutube;
		this.duracion = duracion;
		this.genero = genero;
		this.album = album;
	}

    // Constructor de la clase
    public Cancion() {
		super();
		this.codigo=generarCodigo();
		this.nombre="";
		this.artista="";
		this.caratula="/com/uq/sporify/caratulas/songNotFoundDefault.png";
		this.anio =0;
		this.urlYoutube="";
		this.duracion=0;
		this.genero="";
		this.album="";
	}

	public String setearCaratula(String ruta){
		String rutaCaratul;
		if(ruta.equals("")){
			rutaCaratul="/com/uq/sporify/caratulas/songNotFoundDefault.png";
		}else {
			rutaCaratul = ruta;
		}
		return rutaCaratul;
	}

    /*
     * metodos getter and setter
     */
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public String getAnio() {
		return anio+"";
	}

	public void setAnio(String anio) {
		this.anio = Integer.parseInt(anio);
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(String urlYoutube) {
		this.urlYoutube = urlYoutube;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	public void editar(String nombre, String artista, String caratula, String anio, String urlYoutube,
					   int duracion, String genero, String album){
		this.nombre=nombre;
		this.artista=artista;
		this.album=album;
		this.caratula=caratula;
		this.anio=Integer.parseInt(anio);
		this.urlYoutube=urlYoutube;
		this.duracion=duracion;
		this.genero=genero;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		return Objects.hash(album, artista, anio, caratula, codigo, duracion, genero, nombre, urlYoutube);
	}

	// Compara el objeto actual con otro objeto pasado como parámetro.
	public boolean comprobar(Object obj) {
		if (this == obj) // Verifica si ambos objetos son el mismo
			return true;
		if (obj == null) // verifica si el objeto pasado como parámetro es nulo
			return false;
		if (getClass() != obj.getClass()) // Verifica si ambos objetos son de la misma clase
			return false;
		Cancion other = (Cancion) obj;
		return Objects.equals(album, other.album) && Objects.equals(artista, other.artista)
				&& Objects.equals(anio, other.anio) && Objects.equals(caratula, other.caratula)
				&& Objects.equals(codigo, other.codigo) && duracion == other.duracion
				&& Objects.equals(genero, other.genero) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(urlYoutube, other.urlYoutube);
	}

	@Override
	public String toString() {
		return "Cancion [codigo=" + codigo + ", nombre=" + nombre + ", artista=" + artista + ", caratula=" + caratula
				+ ", año=" + anio + ", urlYoutube=" + urlYoutube + ", duracion=" + duracion + ", genero=" + genero
				+ ", album=" + album + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cancion other = (Cancion) obj;
		return Objects.equals(album, other.album) || Objects.equals(artista, other.artista)
				|| Objects.equals(anio, other.anio) || Objects.equals(caratula, other.caratula)
				|| Objects.equals(codigo, other.codigo) || duracion == other.duracion
				|| Objects.equals(genero, other.genero) || Objects.equals(nombre, other.nombre)
				|| Objects.equals(urlYoutube, other.urlYoutube);
	}

	// Genera un código alfanumérico aleatorio de longitud 10
	public static String generarCodigo() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Crea una cadena de  mayúsculas y dígitos
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int indice = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(indice));
        }
        return codigo.toString();
    }
}


