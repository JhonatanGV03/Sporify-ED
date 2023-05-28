package com.uq.sporify.controller;

import com.uq.sporify.App;
import com.uq.sporify.lib.ListaCircular;
import com.uq.sporify.lib.ListaDobleEnlazada;
import com.uq.sporify.model.Artista;
import com.uq.sporify.model.Cancion;
import com.uq.sporify.model.TiendaMusica;
import com.uq.sporify.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

//Nota: Al final del proyecto se eliminan los identificadores, variables y metodos que no se
// usen o que se puedan optimizar
public class UserController implements Initializable {

    //Variables
    private String busquedaTx = "";

    private TiendaMusica sporify;
    //Identificadores
    @FXML
    private Button btnAnterior, btnBuscar, btnBusquedaArtista, btnBusquedaO, btnBusquedaY,
            btnDeshacer1, btnDeshacer2, btnDeshacer3, btnEliminar1, btnGuardar1, btnGuardar2, btnInicio,
            btnListaA1, btnListaA2, btnListaA3, btnListaA4, btnMute, btnPausar, btnPlayR, btnPlay1, btnPlay2,
            btnPlay3, btnPlay4, btnRehacer1, btnRehacer2, btnRehacer3, btnReproducir1, btnReproducir2,
            btnReproducir3, btnSalir, btnSalir2, btnSiguiente, btnVideo, btnVideo2, btnBiblioteca, btnGuardarF;

    @FXML
    private Circle cArtista1, cArtista2, cArtista3, cArtista4, cArtistaEncontrado, cCaratula1, cCaratula2,
            cCaratula3, cCaratula4;

   // @FXML
    // private ComboBox<String> cbOdenarPor;

    @FXML
    private ChoiceBox<String> cbOdenarPor;

    @FXML
    private HBox hbBusqueda, hbTiposBusqueda;

    @FXML
    private ImageView ivCaratula, ivCaratulaR, ivBtnGuardar;

    @FXML
    private Label lbAlbum, lbAnioCancion, lbBienvenido, lbCodicoCancion, lbCodigoArtistaE, lbDuracion,
            lbGenero, lbNacionalidadArtistaE, lbNombreArtista, lbNombreArtistaR, lbNombreCancion,
            lbNombreCancionR, lbTArtistaE, lbTiempo, lbDuracion2, lbNombreCVideo, lbmusica, lbArtSugerido1,
            lbArtSugerido2, lbArtSugerido3, lbArtSugerido4, lbCanSugerida1, lbCanSugerida2, lbCanSugerida3, lbCanSugerida4;

    @FXML
    private Pane paneInfoBusqueda, paneInfoCancion, panelVideo;

    @FXML
    private ProgressBar pbProgreso, pbVolumen;

    @FXML
    private Slider sliderProgreso, sliderVolumen;

    @FXML
    private Text tNomArtistaE, lbUrl;

    @FXML
    private TextField tfBuscador;

    @FXML
    private TableView<Cancion> tvListaCanciones1;
    @FXML
    private TableColumn<?, ?> codListCan1, nomListCan1, artListCan1, albumListCan1, anioListCan1, duraLisCan1;

    @FXML
    private TableView<Cancion> tvListaCanciones2;
    @FXML
    private TableColumn<?, ?> codListCan2, nomListCan2, artListCan2, albumListCan2, anioListCan2, duraLisCan2;

    @FXML
    private TableView<Cancion> tvListaCanciones3;
    @FXML
    private TableColumn<?, ?> codListCan3, nomListCan3, artListCan3, albumListCan3, anioListCan3, duraLisCan3;

    @FXML
    private VBox vbBArtista, vbBusquedaOY, vbIncio, vbSugerencias, vbBuscar;

    @FXML
    private WebView wbVideo;

    private WebEngine webEngine;
    
    private ObservableList<Cancion> listCanciones = FXCollections.observableArrayList();
    private Cancion canSeleccionada;
    private Artista artistaEncontrado;
    private Boolean esBusquedaY = false;
    private String[] nomArtistasSugeridos = new String[4];
    private String[] codCancionesSugeridas = new String[4];
    private ListaCircular<Cancion> canSugeridas= new ListaCircular<>();

    private ListaCircular<Artista> artSugeridas= new ListaCircular<>();


    private final Image imgArtDefault = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/Artista.png")));


    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sporify = TiendaMusica.getInstance();
        try {
            sporify.cargarInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        lbBienvenido.setText("Bienvenido, " + sporify.getUsuarioActual().getUsuario());
        lbNombreCVideo.setText("No se esta reproduciendo ninguna cancion");
        lbNombreCancionR.setText("No se encontró cancion en reproducción");
        lbNombreArtistaR.setText("No se encontró cancion en reproducción");

        //Inicializa el motor de video
        webEngine = wbVideo.getEngine();

        //Relleno de lista de canciones del usuario
        initTabla(codListCan1, nomListCan1, artListCan1, albumListCan1, anioListCan1, duraLisCan1);

        //Relleno de lista de canciones del Artista
        initTabla(codListCan2, nomListCan2, artListCan2, albumListCan2, anioListCan2, duraLisCan2);

        //Relleno de lista de canciones de la busquedaoy
        initTabla(codListCan3, nomListCan3, artListCan3, albumListCan3, anioListCan3, duraLisCan3);

        //Inicializa los artistas y canciones random

        //Init ComboBox
        cbOdenarPor.getItems().addAll("Codigo", "Nombre", "Artista", "Album", "Año", "Duracion");
        cbOdenarPor.setValue("Nombre");
        cbOdenarPor.setOnAction(this::ordenarLista);
        sporify.getUsuarioActual().ordenarPorAtributo("Nombre");
        cargarCancionesTienda();
        artistasSugeridos();
        cancionesSugeridas();
    }

    private void cancionesSugeridas(){
        int cont = 0;
        ListaCircular<Integer> numeros = generarNumerosAleatorios(sporify.getListaCanciones().getTamanio()-1, 0, sporify.getListaCanciones().getTamanio()-1);
        if(numeros.getTamanio()==1){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(imgArtDefault)); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()==2){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(imgArtDefault)); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(1)){
                    cCaratula2.setFill(new ImagePattern(imgArtDefault));
                    lbCanSugerida2.setText(cancion.getNombre());
                    codCancionesSugeridas[1] = cancion.getCodigo();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()==3){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(imgArtDefault)); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(1)){
                    cCaratula2.setFill(new ImagePattern(imgArtDefault));
                    lbCanSugerida2.setText(cancion.getNombre());
                    codCancionesSugeridas[1] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(2)){
                    cCaratula3.setFill(new ImagePattern(imgArtDefault));
                    lbCanSugerida3.setText(cancion.getNombre());
                    codCancionesSugeridas[2] = cancion.getNombre();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()>=4){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(imgArtDefault)); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(1)){
                    cCaratula2.setFill(new ImagePattern(imgArtDefault));
                    lbCanSugerida2.setText(cancion.getNombre());
                    codCancionesSugeridas[1] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(2)){
                    cCaratula3.setFill(new ImagePattern(imgArtDefault));
                    lbCanSugerida3.setText(cancion.getNombre());
                    codCancionesSugeridas[2] = cancion.getNombre();
                }
                if (cont == numeros.obtener(3)){
                    cCaratula4.setFill(new ImagePattern(imgArtDefault));
                    lbCanSugerida4.setText(cancion.getNombre());
                    codCancionesSugeridas[3] = cancion.getNombre();
                }
                cont++;
            }
        }
    }
    public static ListaCircular<Integer> generarNumerosAleatorios(int cantidad, int minimo, int maximo) {
        if (cantidad > (maximo - minimo + 1) || maximo < minimo) {
            throw new IllegalArgumentException("No es posible generar los números aleatorios solicitados.");
        }
        ListaCircular<Integer> numerosAleatorios = new ListaCircular<>();
        Random random = new Random();
        Set<Integer> numerosGenerados = new HashSet<>();
        while (numerosAleatorios.getTamanio() < cantidad) {
            int numeroAleatorio = random.nextInt(maximo - minimo + 1) + minimo;
            if (!numerosGenerados.contains(numeroAleatorio)) {
                numerosAleatorios.agregar(numeroAleatorio);
                numerosGenerados.add(numeroAleatorio);
            }
        }
        return numerosAleatorios;
    }
    private void artistasSugeridos (){
        //Recorrer la lista de artistas hasta una posicion random, y obtener el artista
        int cont = 0;
        ListaCircular<Integer> numeros = generarNumerosAleatorios(sporify.getListaArtistas().obtenerTamanio()-1, 0, sporify.getListaArtistas().obtenerTamanio()-1);
        if(numeros.getTamanio()==1){
            for (Artista artista: sporify.getListaArtistas()){
                if(cont == numeros.obtener(0)){
                    cArtista1.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido1.setText(artista.getNombre());
                    nomArtistasSugeridos[0] = artista.getNombre();
                }
                cont++;
            }
        }

        if(numeros.getTamanio()==2){
            for (Artista artista: sporify.getListaArtistas()){
                if(cont == numeros.obtener(0)){
                    cArtista1.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido1.setText(artista.getNombre());
                    nomArtistasSugeridos[0] = artista.getNombre();
                }
                else if (cont == numeros.obtener(1)){
                    cArtista2.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido2.setText(artista.getNombre());
                    nomArtistasSugeridos[1] = artista.getNombre();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()==3){
            for (Artista artista: sporify.getListaArtistas()){
                if(cont == numeros.obtener(0)){
                    cArtista1.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido1.setText(artista.getNombre());
                    nomArtistasSugeridos[0] = artista.getNombre();
                }
                else if (cont == numeros.obtener(1)){
                    cArtista2.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido2.setText(artista.getNombre());
                    nomArtistasSugeridos[1] = artista.getNombre();
                }
                else if (cont == numeros.obtener(2)){
                    cArtista3.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido3.setText(artista.getNombre());
                    nomArtistasSugeridos[2] = artista.getNombre();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()>=4){
        for (Artista artista: sporify.getListaArtistas()){
            if(cont == numeros.obtener(0)){
                cArtista1.setFill(new ImagePattern(imgArtDefault));
                lbArtSugerido1.setText(artista.getNombre());
                nomArtistasSugeridos[0] = artista.getNombre();
            }
            else if (cont == numeros.obtener(1)){
                cArtista2.setFill(new ImagePattern(imgArtDefault));
                lbArtSugerido2.setText(artista.getNombre());
                nomArtistasSugeridos[1] = artista.getNombre();
            }
            else if (cont == numeros.obtener(2)){
                cArtista3.setFill(new ImagePattern(imgArtDefault));
                lbArtSugerido3.setText(artista.getNombre());
                nomArtistasSugeridos[2] = artista.getNombre();
            }
            else if (cont == numeros.obtener(3)){
                cArtista4.setFill(new ImagePattern(imgArtDefault));
                lbArtSugerido4.setText(artista.getNombre());
                nomArtistasSugeridos[3] = artista.getNombre();
            }
            cont++;
        }
    }
    }

    @FXML
    private void ordenarLista(ActionEvent actionEvent) {
        String orden = cbOdenarPor.getValue();
        sporify.getUsuarioActual().ordenarPorAtributo(orden);
        System.out.println("Ordenando por: " + orden);

        listCanciones.clear();
        cargarCancionesPlaylist();
    }


    public void initTabla(TableColumn col1, TableColumn col2, TableColumn col3, TableColumn col4, TableColumn col5, TableColumn col6) {
        col1.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col3.setCellValueFactory(new PropertyValueFactory<>("artista"));
        col4.setCellValueFactory(new PropertyValueFactory<>("album"));
        col5.setCellValueFactory(new PropertyValueFactory<>("anio"));
        col6.setCellValueFactory(new PropertyValueFactory<>("duracion"));
    }


    public void cargarCancionesPlaylist(){

        ListaCircular<Cancion> cancionList = sporify.getUsuarioActual().getListaCanciones();

        for (Cancion cancion : cancionList) {
            Boolean bandera = false;
            for (Cancion canAux : listCanciones) {
                if (canAux.getCodigo().equals(cancion.getCodigo())) {
                    bandera = true;
                }
            }
            if(bandera == false) {
                this.listCanciones.add(cancion);
            }
        }
        this.tvListaCanciones1.setItems(listCanciones);
    }

    public void cargarCancionesTienda(){

        ListaCircular<Cancion> cancionList = sporify.getListaCanciones();

        for (Cancion cancion : cancionList) {
            Boolean bandera = false;
            for (Cancion canAux : listCanciones) {
                if (canAux.getCodigo().equals(cancion.getCodigo())) {
                    bandera = true;
                }
            }
            if(bandera == false) {
                this.listCanciones.add(cancion);
            }
        }
        this.tvListaCanciones1.setItems(listCanciones);
    }
    public void cargarCancionesBusquedaArtista(){

        ListaDobleEnlazada<Cancion> cancionList = sporify.retornarArtista(artistaEncontrado.getNombre()).getListaCanciones();

        for (Cancion cancion : cancionList) {
            Boolean bandera = false;
            for (Cancion canAux : listCanciones) {
                if (canAux.getCodigo().equals(cancion.getCodigo())) {
                    bandera = true;
                }
            }
            if(bandera == false) {
                this.listCanciones.add(cancion);
            }
        }
        this.tvListaCanciones2.setItems(listCanciones);
    }
    public void cargarCancionesBusquedaOY(){  //*Falta implementar
        listCanciones.clear();
        ListaDobleEnlazada<Cancion> cancionList = new ListaDobleEnlazada<>();

        if (esBusquedaY){
            cancionList = sporify.testBuscarY(tfBuscador.getText());
            System.out.println("Busqueda Y");
        }else {
            cancionList = sporify.testBuscarO(tfBuscador.getText());
            System.out.println("Busqueda O");
        }



        for (Cancion cancion : cancionList) {
            Boolean bandera = false;
            for (Cancion canAux : listCanciones) {
                if (canAux.getCodigo().equals(cancion.getCodigo())) {
                    bandera = true;
                }
            }
            if(bandera == false) {
                this.listCanciones.add(cancion);
            }
        }
        this.tvListaCanciones3.setItems(listCanciones);
    }



    //Metodos de Acciones de eventos

    //Metodos para cambiar entre las ventanas de Inicio y Buscar (Panel Superior Izquierdo) y salir
    @FXML
    void onActionBtnInicio() {
        btnInicio.setStyle("-fx-text-fill: white;" + "-fx-background-color: #121212");
        btnBuscar.setStyle("-fx-text-fill: #ababab;" + "-fx-background-color: #121212");
        btnDeshacer1.setVisible(false);
        cbOdenarPor.setVisible(false);
        btnEliminar1.setVisible(false);
        btnRehacer1.setVisible(false);
        btnGuardarF.setVisible(true);
        paneInfoBusqueda.setVisible(false);

        lbmusica.setText("Música");
        cambiarVNodo(vbBuscar, vbIncio);
        paneInfoBusqueda.setVisible(false);
        listCanciones.clear();
        cargarCancionesTienda();
        canSeleccionada = null;
        paneInfoCancion.setVisible(false);
    }

    @FXML
    void onActionBtnBuscar(ActionEvent event) {
        btnBuscar.setStyle("-fx-text-fill: white;" + "-fx-background-color: #121212");
        btnInicio.setStyle("-fx-text-fill: #ababab;" + "-fx-background-color: #121212");

        cambiarVNodo(vbIncio, vbBuscar);
        cambiarVNodo(paneInfoCancion, paneInfoBusqueda);
        canSeleccionada = null;
    }
    @FXML
    void onActionBtnBiblioteca(ActionEvent event) {
        btnBuscar.setStyle("-fx-text-fill: white;" + "-fx-background-color: #121212");
        btnInicio.setStyle("-fx-text-fill: #ababab;" + "-fx-background-color: #121212");
        btnDeshacer1.setVisible(true);
        cbOdenarPor.setVisible(true);
        btnEliminar1.setVisible(true);
        btnRehacer1.setVisible(true);
        btnGuardarF.setVisible(false);
        lbmusica.setText("Mi Lista de Reproduccion");
        cambiarVNodo(vbBuscar, vbIncio);
        paneInfoCancion.setVisible(false);

        listCanciones.clear();
        cargarCancionesPlaylist();
        canSeleccionada = null;
    }
    @FXML
    void seleccionarTvListCanciones(MouseEvent event) {
        if (vbIncio.isVisible()) {
            canSeleccionada = this.tvListaCanciones1.getSelectionModel().getSelectedItem();
        } else if (vbBArtista.isVisible()) {
            canSeleccionada = this.tvListaCanciones2.getSelectionModel().getSelectedItem();
        } else if (vbBusquedaOY.isVisible()){
            canSeleccionada = this.tvListaCanciones3.getSelectionModel().getSelectedItem();
        }
        mostrarInfoCancion();
    }

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        sporify.getUsuarioActual().actualizarFavoritos();
        Usuario userActual = sporify.getUsuarioActual();
        sporify.eliminarUsuario(userActual.getUsuario());
        sporify.agregarUsuario(userActual);
        try {
            sporify.guardarInfo();
            System.exit(0);
            //cambiarEscena("vista/login.fxml", "Sporify", 679, 456);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Metodos del reproductor y ventana de visualizacion de video

    @FXML
    void onActionBtnVideo(ActionEvent event) {
        if (!panelVideo.isVisible()){
            panelVideo.setVisible(true);
            cambiarVNodo(paneInfoBusqueda, paneInfoCancion);
            btnVideo.setStyle("-fx-background-color: red; -fx-background-radius: 40 " );
        }else {
            panelVideo.setVisible(false);
            btnVideo.setStyle("-fx-background-color: #f3f3f3; -fx-background-radius: 40 " );
        }
    }
    @FXML
    void onActionBtnAnterior(ActionEvent event) {
    }
    @FXML
    void onActionBtnSiguiente(ActionEvent event) {

    }
    @FXML
    void onActionBtnVolumen(ActionEvent event) {

    }

    @FXML
    void onDraggedVolume(MouseEvent event) {
        pbVolumen.setProgress(sliderVolumen.getValue()/ sliderVolumen.getMax());
    }

    //Metodos que sirven para todas las ventanas que contengan un boton de reproducir cancion, rehacer, deshacer y eliminar
    @FXML
    void onActionBtnReproducir(ActionEvent event) {
        if (canSeleccionada != null) {
                String url = organizarURL(canSeleccionada.getUrlYoutube());
                webEngine.load(url);
                wbVideo.setOpacity(1);
                cambiarVNodo(paneInfoBusqueda, paneInfoCancion);
                panelVideo.setVisible(true);
                btnVideo.setStyle("-fx-background-color: red; -fx-background-radius: 40 " );
                mostrarInfoCancion();
                lbNombreCVideo.setText(canSeleccionada.getNombre());
                lbNombreArtistaR.setText(canSeleccionada.getArtista());
                lbNombreCancionR.setText(canSeleccionada.getNombre());

                sporify.reproducir(canSeleccionada);
            canSeleccionada = null;
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de reproducirla.");
        }
    }

    @FXML
    void onActionBtnRehacer(ActionEvent event) {
        if (sporify.getUsuarioActual().getListaCanciones() != null) {
            Cancion cancionGuardada = sporify.getUsuarioActual().getCambiosRecientes().peek();
            sporify.getUsuarioActual().eliminarCancion(cancionGuardada);
            sporify.getUsuarioActual().deshacer(cancionGuardada);

            alerta(Alert.AlertType.WARNING, "Sporify - Cancion Repetida", "cambio reciente se ha deshecho");

        } else {
            alerta(Alert.AlertType.INFORMATION, "Sporify - Cancion ", "No hay cambios recientes por deshacer");
        }
        listCanciones.clear();
        cargarCancionesPlaylist();
        canSeleccionada = null;
        paneInfoCancion.setVisible(false);
    }

    @FXML
    void onActionBtnDeshacer(ActionEvent event) {

            if (sporify.getUsuarioActual().getCambiosRecientes() != null) {
                Cancion cancionEliminada = sporify.getUsuarioActual().rehacer();
                sporify.getUsuarioActual().guardarCancion(cancionEliminada);

                alerta(Alert.AlertType.WARNING, "Sporify - Cancion Repetida", "cambio reciente se ha deshecho");
            } else {
                alerta(Alert.AlertType.INFORMATION, "Sporify - Cancion ", "No hay cambios recientes por deshacer");
            }
        listCanciones.clear();
        cargarCancionesPlaylist();
        canSeleccionada = null;
        paneInfoCancion.setVisible(false);
    }
    @FXML
    void onActionBtnEliminar(ActionEvent event) {
        if (canSeleccionada != null) {
            if (sporify.getUsuarioActual().existeCancion(canSeleccionada)) {
                sporify.getUsuarioActual().eliminarCancion(canSeleccionada);
                sporify.getUsuarioActual().actualizarFavoritos();
                alerta(Alert.AlertType.INFORMATION, "Sporify - Cancion Eliminada", "La cancion seleccionada se ha eliminado de su lista de reproduccion.");
            } else {
                alerta(Alert.AlertType.WARNING, "Sporify - Cancion No Encontrada", "La cancion seleccionada no se encuentra en su lista de reproduccion.");
            }

            canSeleccionada = null;
            paneInfoCancion.setVisible(false);
        }
        listCanciones.clear();
        cargarCancionesPlaylist();
    }
    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        if (canSeleccionada != null) {
            if (sporify.getUsuarioActual().existeCancion(canSeleccionada)) {
                alerta(Alert.AlertType.WARNING, "Sporify - Cancion Repetida", "La cancion seleccionada ya se encuentra en su lista de reproduccion.");
            } else {
                sporify.getUsuarioActual().guardarCancion(canSeleccionada);
                sporify.getUsuarioActual().actualizarFavoritos();
                //sporify.getUsuarioActual().deshacer1();
                alerta(Alert.AlertType.INFORMATION, "Sporify - Cancion Agregada", "La cancion seleccionada se ha agregado a su lista de reproduccion.");
            }
            canSeleccionada = null;
            paneInfoCancion.setVisible(false);
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de agregarla.");
        }


    }

    //Metodos de botones de acceso a artistas y canciones sugeridas
    @FXML
    void onActionBtnLista(ActionEvent event) {
        //Obtener el fxid del boton presionado y realizar cambios con el
        Button btn = (Button) event.getSource();
        btn.getId();
        System.out.println(btn.getId());
        if (btn.getId().equals("btnListaA1")){
            tfBuscador.setText(nomArtistasSugeridos[0]);
        }else if (btn.getId().equals("btnListaA2")) {
            tfBuscador.setText(nomArtistasSugeridos[1]);
        }else if (btn.getId().equals("btnListaA3")) {
            tfBuscador.setText(nomArtistasSugeridos[2]);
        }else{
            tfBuscador.setText(nomArtistasSugeridos[3]);
        }
        onActionBusquedaArtista();
    }
    @FXML
    void onActionBtnPlayB(ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.getId();
        System.out.println(btn.getId());
        if (btn.getId().equals("btnPlay1")){
            tfBuscador.setText(codCancionesSugeridas[0]);
        }else if (btn.getId().equals("btnPlay2")) {
            tfBuscador.setText(codCancionesSugeridas[1]);
        }else if (btn.getId().equals("btnPlay3")) {
            tfBuscador.setText(codCancionesSugeridas[2]);
        }else{
            tfBuscador.setText(codCancionesSugeridas[3]);
        }
        canSeleccionada = sporify.getCancioncita(tfBuscador.getText());
        onActionBtnReproducir(event);
        hbTiposBusqueda.setDisable(false);
        onActionBusquedaO();
    }

    //Metodos de la barra de busqueda y los tipos de busqueda
    @FXML
    void onKeyTyped(KeyEvent event) {
        busquedaTx = this.tfBuscador.getText();


        if (this.tfBuscador.getText().isEmpty()) {
            cambiarVNodo(vbBArtista, vbBusquedaOY, vbSugerencias);
            hbTiposBusqueda.setDisable(true);
        } else if (!vbBArtista.isVisible() && !vbBusquedaOY.isVisible()) {
            onActionBusquedaO();
            hbTiposBusqueda.setDisable(false);
        } else if (vbBArtista.isVisible() && !vbBusquedaOY.isVisible()) {
            onActionBusquedaArtista();
            hbTiposBusqueda.setDisable(false);
        } else if (!vbBArtista.isVisible() && vbBusquedaOY.isVisible() && esBusquedaY) {
            onActionBusquedaY();
            hbTiposBusqueda.setDisable(false);
        }else if (!vbBArtista.isVisible() && vbBusquedaOY.isVisible() && !esBusquedaY) {
            onActionBusquedaO();
            hbTiposBusqueda.setDisable(false);
        }
    }

    @FXML
    void onActionBusquedaArtista() {
        String estilo = "-fx-background-color: #121212; -fx-text-fill: 9e9e9e; -fx-background-radius:20";
        cambiarEstiloNodos(btnBusquedaY, btnBusquedaO,  estilo);
        btnBusquedaArtista.setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: Black; -fx-background-radius:20");
        sporify.agregarCancionesArtista();
        artistaEncontrado = sporify.retornarArtista(tfBuscador.getText().trim());
        listCanciones.clear();

        if (!artistaEncontrado.getNombre().equals("")){
            System.out.println(artistaEncontrado.getListaCanciones());
            tNomArtistaE.setText(artistaEncontrado.getNombre());
            lbNacionalidadArtistaE.setText(artistaEncontrado.getNacionalidad());
            lbCodigoArtistaE.setText(artistaEncontrado.getCodigo());
            if (artistaEncontrado.getGrupo()) lbTArtistaE.setText("Grupo");
            else lbTArtistaE.setText("Solista");
            cargarCancionesBusquedaArtista();
        }else{
            tNomArtistaE.setText("No Encontrado");
            lbNacionalidadArtistaE.setText("N/A");
            lbCodigoArtistaE.setText("N/A");
            lbCodigoArtistaE.setText("Solita/Grupo");
            System.out.println("Artista no encontrado");
        }
        if(!this.tfBuscador.getText().isEmpty()){
            cambiarVNodo(vbSugerencias, vbBusquedaOY, vbBArtista);
        }
    }

    @FXML
    void onActionBusquedaO() {
        listCanciones.clear();
        esBusquedaY = false;
        btnBusquedaO.setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: Black; -fx-background-radius:20");
        String estilo = "-fx-background-color: #121212; -fx-text-fill: 9e9e9e; -fx-background-radius:20";
        cambiarEstiloNodos(btnBusquedaArtista, btnBusquedaY, estilo);

        cargarCancionesBusquedaOY();

        if(!this.tfBuscador.getText().isEmpty()){
            cambiarVNodo(vbSugerencias, vbBArtista, vbBusquedaOY);
        }
    }
    @FXML
    void onActionBusquedaY() {
        listCanciones.clear();
        esBusquedaY = true;
        btnBusquedaY.setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: Black; -fx-background-radius:20");
        cargarCancionesBusquedaOY();

        String estilo = "-fx-background-color: #121212; -fx-text-fill: 9e9e9e; -fx-background-radius:20";
        cambiarEstiloNodos(btnBusquedaArtista, btnBusquedaO, estilo);

        if(!this.tfBuscador.getText().isEmpty()){
            cambiarVNodo(vbSugerencias, vbBArtista, vbBusquedaOY);
        }
    }

    //Metodos Propios


    //Metodos usados en acciones repetitivas para optimizar codigo
    //Metodos para cambiar entre objetos de tipo Node
    public void cambiarVNodo(Node cierra1, Node cierra2, Node abre){
        cierra1.setVisible(false);
        cierra2.setVisible(false);
        abre.setVisible(true);
    }
    public void cambiarVNodo(Node cierra, Node abre){
        abre.setVisible(true);
        cierra.setVisible(false);
    }
    public void cambiarEstiloNodos(Node nodo1, Node nodo2, String estilo){
        nodo1.setStyle(estilo);
        nodo2.setStyle(estilo);
    }

    public void cambiarEscena(String fxmlPath, String title, int width, int height) throws IOException {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/logoBlack.png")));
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.getIcons().add(icon);
        stage.setTitle(title);
        stage.initStyle(StageStyle.TRANSPARENT);  //Para que la ventana no tenga la barra superior
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage stage2 = (Stage) btnVideo.getScene().getWindow();
        stage2.close();
    }
    private String organizarURL(String urlYoutube) {
        String url = urlYoutube;
        String urlAux = "";

        for (int i = url.length()-1; i >= 0; i--) {
            if (url.charAt(i) == '=' || url.charAt(i) == '/') {
                i=0;
            }else {
                urlAux += url.charAt(i);
            }
        }
        StringBuilder invertido = new StringBuilder(urlAux);
        invertido = invertido.reverse();
        String finalUrl = "https://www.youtube.com/embed/"+ invertido + "?autoplay=1";
        return finalUrl;
    }
    public void alerta(Alert.AlertType tipo, String titulo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void mostrarInfoCancion(){
        if (canSeleccionada!=null){
            lbCodicoCancion.setText(canSeleccionada.getCodigo());
            lbNombreCancion.setText(canSeleccionada.getNombre());
            lbNombreArtista.setText(canSeleccionada.getArtista());
            lbAlbum.setText(canSeleccionada.getAlbum());
            lbGenero.setText(canSeleccionada.getGenero());
            lbUrl.setText(canSeleccionada.getUrlYoutube());
            lbDuracion.setText(String.valueOf(canSeleccionada.getDuracion()));
            lbAnioCancion.setText(canSeleccionada.getAnio());
            //imgCaratulaI.setImage(new Image(canSeleccionada.getCaratula()));
            cambiarVNodo(paneInfoBusqueda, paneInfoCancion);
        }
    }
}
