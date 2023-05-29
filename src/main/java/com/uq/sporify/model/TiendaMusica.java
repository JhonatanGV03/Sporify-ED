package com.uq.sporify.model;

import com.uq.sporify.lib.*;
import com.uq.sporify.persistencia.ArchivoUtil;
import com.uq.sporify.persistencia.Persistencia;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
@XmlRootElement
public class TiendaMusica implements Serializable {
	// Declaracion de variables
	private static TiendaMusica tienda;
	private ArbolBinario<Artista> listaArtistas;
	private HashMap<String, Usuario> listaUsuarios;
	private ListaCircular<Cancion> listaCanciones;

	private Usuario usuarioActual;

	public static TiendaMusica getInstance() {
		if(tienda==null) {
			tienda = new TiendaMusica();
		}
		return tienda;
	}
	// Segundo constructor de la clase
	private TiendaMusica() {
		super();

		this.listaArtistas=new ArbolBinario<>();
		this.listaUsuarios=new HashMap<>();
		this.listaCanciones=new ListaCircular<>();
		this.usuarioActual = new Usuario();
	}

	// Metodos getter and setter
	public TiendaMusica getTienda() {
		return tienda;
	}

	public void setTienda(TiendaMusica tienda) {
		this.tienda = tienda;
	}

	public ArbolBinario<Artista> getListaArtistas() {
		return listaArtistas;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public void setListaArtistas(ArbolBinario<Artista> listaArtistas) {
		this.listaArtistas = listaArtistas;
	}

	public HashMap<String, Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(HashMap<String, Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ListaCircular<Cancion> getListaCanciones() {
		return listaCanciones;
	}

	public void setListaCanciones(ListaCircular<Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public int hashCode() {
		return Objects.hash(listaArtistas, listaCanciones, listaUsuarios);
	}

	@Override
	// compara dos objetos
	public boolean equals(Object obj) {
		if (this == obj) // Verifica si ambos objetos son el mismo
			return true;
		if (obj == null) // verifica si el objeto pasado como par�metro es nulo
			return false;
		if (getClass() != obj.getClass()) // Verifica si ambos objetos son de la misma clase
			return false;
		TiendaMusica other = (TiendaMusica) obj;
		return Objects.equals(listaArtistas, other.listaArtistas) && Objects.equals(listaCanciones, other.listaCanciones)
				&& Objects.equals(listaUsuarios, other.listaUsuarios);
	}

	@Override
	public String toString() {
		return "TiendaMusica [listaArtistas=" + listaArtistas + ", listaUsuarios=" + listaUsuarios + ", listaCanciones="
				+ listaCanciones + "]";
	}



	// Recibe un objeto de tipo Artista y lo agrega a la lista de artistas
	public void agregarArtista(Artista artista) {
		Boolean bandera = false;
		for (Artista art:listaArtistas) {
			if(art.equals(artista)) {
				bandera = true;
			}
		}
		if (bandera == false){
			listaArtistas.agregar(artista);
			agregarCancionesArtista();
		}
	}
	// Recibe un objeto de tipo Artista y lo elimina de la lista de artistas.
	public void eliminarArtista(Artista artista) {
		listaArtistas.eliminar(artista);
	}

	// Recibe un objeto de tipo Cancion y lo agrega a la lista de canciones
	public void guardarCancion(Cancion cancion) {
		if(encontrarCancion(cancion)==false)
		    this.listaCanciones.agregar(cancion);
	}
	public boolean encontrarCancion (Cancion cancion){
		Boolean bandera = false;
		for(Cancion song:listaCanciones)
		{
			String can = song.getCodigo();
			if (can.equals(cancion.getCodigo()))
				bandera=true;
		}
		return bandera;
	}
	// Recibe un objeto de tipo Cancion y lo elimina de la lista de canciones
	public void eliminarCancion(Cancion cancion) {
		listaCanciones.eliminar(cancion);
	}

	/*
	 *  Recibe un objeto de tipo Artista y devuelve una lista
	 *  doblemente enlazada de canciones asociadas a ese artista
	 */
	public ListaDobleEnlazada<Cancion> buscarArtista(Artista artista){
		Artista art = listaArtistas.encontrar(artista);
		return art.getListaCanciones();
	}

	private String[] separarAtributos(String atributos){
		String[] atributosSeparados = atributos.split(",");

		for (int i = 0; i < atributosSeparados.length; i++) {
			atributosSeparados[i] = atributosSeparados[i].trim();
			if (atributosSeparados[i]==null){
				atributosSeparados[i] = "";
			}
		}
		return atributosSeparados;
	}
	/*
	 * Recibe tres atributos y devuelve una lista doblemente enlazada de canciones que cumplen
	 * con al menos uno de los atributos
	 */
	public ListaDobleEnlazada<Cancion> buscarAtributosEnO(String atributos) {
		String[] atributosSeparados = atributos.split(",");

		for (int i = 0; i < atributosSeparados.length; i++) {
			atributosSeparados[i] = atributosSeparados[i].trim();
			if (atributosSeparados[i]==null){
				atributosSeparados[i] = "";
			}
		}
		ListaDobleEnlazada<Cancion> resultadosBusqueda= new ListaDobleEnlazada<Cancion>();
	    return listaArtistas.buscarO(resultadosBusqueda,atributosSeparados);
	}

	/*
	 * Recibe tres atributos y devuelve una lista doblemente enlazada de canciones que cumplen con todos los atributos
	 */
	public ListaDobleEnlazada<Cancion> buscarAtributosEnY(String atributo1,String atributo2, String atributo3) {
		ListaDobleEnlazada<Cancion> resultadosBusqueda= new ListaDobleEnlazada<Cancion>();
	    return listaArtistas.buscarY(resultadosBusqueda,atributo1, atributo2, atributo3);
	}

	/*
	 * crea un objeto Usuario vac�o y luego itera
	 * a trav�s de una lista de usuarios y contrase�as
	 * almacenados en un mapa utilizando listas enlazadas
	 */
	public Usuario iniciarSesion(String usuario, String contrasenia) {

		Usuario user = null;
		Iterator<Map.Entry<String, Usuario>> it = (listaUsuarios).entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, Usuario> entry = it.next(); // Si el nombre de usuario o la contrase�a
		    String key = entry.getKey(); // coinciden con los valores almacenados en el mapa
		    String value = entry.getValue().getContrasenia();
		    if(usuario.equals(key) && contrasenia.equals(value)) {
		    	user=entry.getValue();
		    }
		}
		return user; // Devuelve el objeto Usuario.
	}

	/*
	 *  Carga informacion desde un recurso XML
	 */
	public void cargarInfo() throws IOException {
		Persistencia.cargarRecursoSporifyXML();
		agregarCancionesArtista();
		agregarCancionesUsuario();
		tienda = getInstance();
	}
	public void guardarInfo() throws IOException {
		Persistencia.guardarRecursoSporifyXML();
		tienda = getInstance();
	}
	public boolean verificarUsuario(String user){
		return this.getListaUsuarios().containsKey(user);
	}
	public void crearUsuario (Usuario usuario){
		this.listaUsuarios.put(usuario.getUsuario(),usuario);
	}

	public void agregarCancionesArtista () {
		ArbolBinario<Artista> artistasaux = new ArbolBinario<Artista>();

	for (Artista art:listaArtistas)	{
		this.listaArtistas.encontrar(art).setListaCanciones(new ListaDobleEnlazada<Cancion>());
		for (Cancion cancion : tienda.listaCanciones) {
			String nombreArtista = cancion.getArtista();
			if (nombreArtista.equals(art.getNombre())) {
				this.tienda.listaArtistas.encontrar(art).agregarCancion(cancion);
			}
		}
		System.out.println("esta es una prueba");
	}
	}

	//
	public void agregarCancionesUsuario(){

		HashMap<String,Usuario> lstusraux= new HashMap<String,Usuario>();
		for (Map.Entry<String,Usuario> usuario: listaUsuarios.entrySet()){
			Usuario auxuser = usuario.getValue();
			String aux = auxuser.getFavoritos();
			if(aux != null){
			String[] arreglo = aux.split(";");
				for (int i=0;i<arreglo.length;i++){

					for (Cancion cancion: listaCanciones){
					String codCancion = cancion.getCodigo();
						if (codCancion.equals(arreglo[i])){
						this.tienda.listaUsuarios.get(usuario.getKey()).guardarCancion(cancion);
						}
					}
				}
			}
		}
	}

	public void AgregarPorArchivo (File archivo){
		try {
			ArchivoUtil.cargarInformacion(archivo);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	public String mostrarArtistaTendencia (){
		int tendencia=0;
		Artista aux= new Artista();
		for (Artista artista:listaArtistas){
			if(artista.getReproducciones()>tendencia){
				tendencia=artista.getReproducciones();
				aux=artista;
			}
		}
		return aux.getNombre();
	}
	public String mostrarGeneroTendendencia (){
		int contadorRock=0;
		int contadorPunk=0;
		int contadorRegueton=0;
		int contadorPop=0;
		int contadorElectronica=0;
		int contadorRap=0;
		for (Cancion cancion: listaCanciones){
			if(cancion.getGenero().equals("Rock")){
				contadorRock++;
			}
			else if(cancion.getGenero().equals("Punk")){
				contadorPunk++;
			}
			else if(cancion.getGenero().equals("Reggaeton")){
				contadorRegueton++;
			}
			else if(cancion.getGenero().equals("Pop")){
				contadorPop++;
			}
			else if(cancion.getGenero().equals("Electronica")){
				contadorElectronica++;
			}
			else if(cancion.getGenero().equals("Rap")){
				contadorRap++;
			}
		}

		return calcularNumeroMayor(contadorRock,contadorPunk,contadorRegueton,contadorPop,contadorElectronica,contadorRap);
	}
	public Artista retornarArtista(String nombreArtista){
		Artista artista=new Artista();
		for(Artista art: listaArtistas){
			if(art.getNombre().equals(nombreArtista))
				artista = art;
		}
		return artista;
	}
	public static String calcularNumeroMayor(int num1, int num2, int num3, int num4, int num5,int num6) {
		int numeroMayor = num1;
		String popular = "Rock";
		if (num2 > numeroMayor) {
			numeroMayor = num2;
			popular = "Punk";
		}

		if (num3 > numeroMayor) {
			numeroMayor = num3;
			popular = "Reggaeton";
		}

		if (num4 > numeroMayor) {
			numeroMayor = num4;
			popular = "Pop";
		}

		if (num5 > numeroMayor) {
			numeroMayor = num5;
			popular = "Electronica";
		}
		if (num6 > numeroMayor) {
			numeroMayor = num5;
			popular = "Rap";
		}
		return popular;
	}
	public Artista encontrarDueño(Cancion cancion)
	{
		Artista art =null;
		for (Artista artist:listaArtistas){
			if(artist.getNombre().equals(cancion.getArtista())){
				art = artist;
			}
		}
		return art;
	}
	public void reproducir (Cancion cancion)
	{
		Artista artaux=encontrarDueño(cancion);
		listaArtistas.encontrar(artaux).setReproducciones(listaArtistas.encontrar(artaux).getReproducciones()+1);
	}
	public ListaCircular<Cancion> depurarArtistayCanciones (Artista art)
	{
		TiendaMusica sporify = TiendaMusica.getInstance();
		Artista arte= sporify.retornarArtista(art.getNombre());
		ListaCircular<Cancion> aux = new ListaCircular<>();
		if(arte != null){
		for (Cancion cancion:listaCanciones){
			if(!cancion.getArtista().equals(arte.getNombre()))
			{
				aux.agregar(cancion);
			}
		}
		sporify.eliminarArtista(art);
	}
		return aux;
	}
	public HashMap<String,Usuario> depurarPlayList() {
		TiendaMusica sporify = TiendaMusica.getInstance();
		HashMap<String,Usuario> auxUsuarios = sporify.getListaUsuarios();
		for(Map.Entry<String,Usuario> users:auxUsuarios.entrySet()){
				Usuario
			for(Cancion playlist:users.getValue().getListaCanciones()){
				Boolean banderita = false;
				for(Cancion cancionesTienda:listaCanciones)
				{
					if(cancionesTienda.comprobar(playlist)){
						banderita = true;
					}
				}
				if(banderita==false){
					
				}
			}
		}

		return auxUsuarios;
	}
	//public ListaDobleEnlazada<Cancion> buscarFiltroO (String atributo1, String atributo2, String atributo3){
	//	ListaDobleEnlazada<Cancion> resultadosBusqueda = new ListaDobleEnlazada<>();
		//return listaArtistas.buscarO(resultadosBusqueda,atributo1,atributo2,atributo3);
	//}
	public ListaDobleEnlazada<Cancion> buscarFiltroY (String atributo1, String atributo2, String atributo3){
		ListaDobleEnlazada<Cancion> resultadosBusqueda = new ListaDobleEnlazada<>();
		return listaArtistas.buscarY(resultadosBusqueda,atributo1,atributo2,atributo3);
	}
	public ListaDobleEnlazada<Cancion> testBuscarO (String atributos){
		String[] atributosSeparados = atributos.split(",");

		for (int i = 0; i < atributosSeparados.length; i++) {
			atributosSeparados[i] = atributosSeparados[i].trim();

	}
		return listaArtistas.searchSongsO(atributosSeparados);
}
	public ListaDobleEnlazada<Cancion> testBuscarY (String atributos){
		ListaDobleEnlazada<Cancion> defecto = new ListaDobleEnlazada<>();
		String[] atributosSeparados = atributos.split(",");

		for (int i = 0; i < atributosSeparados.length; i++) {
			atributosSeparados[i] = atributosSeparados[i].trim();

		}
		if(atributosSeparados.length>=2){
			defecto = listaArtistas.searchSongsY(atributosSeparados);
		}
		return defecto;
	}
	public void eliminarUsuario(String usuario){
		listaUsuarios.remove(usuario);
	}
	public void agregarUsuario(Usuario usuario){
		listaUsuarios.put(usuario.getUsuario(),usuario);
	}
	public Cancion getCancioncita(String nombreCodigo){
		Cancion song = new Cancion();
		for(Cancion cancion:listaCanciones){
			if(cancion.getCodigo().equals(nombreCodigo)){
				song = cancion;
			}
		}
		return song;
	}
}

