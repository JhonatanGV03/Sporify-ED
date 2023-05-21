package com.uq.sporify.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

//Nota: Al final del proyecto se eliminan los identificadores, variables y metodos que no se
// usen o que se puedan optimizar
public class UserController {

    //Variables
    private String busquedaTx = "";

    //Identificadores
    @FXML
    private Button btnAnterior, btnBuscar, btnBusquedaArtista, btnBusquedaO, btnBusquedaOY, btnBusquedaY,
            btnDeshacer1, btnDeshacer2, btnDeshacer3, btnEliminar1, btnGuardar1, btnGuardar2, btnInicio,
            btnListaA1, btnListaA2, btnListaA3, btnListaA4, btnMute, btnPausar, btnPlayR, btnPlay1, btnPlay2,
            btnPlay3, btnPlay4, btnRehacer1, btnRehacer2, btnRehacer3, btnReproducir1, btnReproducir2,
            btnReproducir3, btnSalir, btnSalir2, btnSiguiente, btnVideo, btnVideo2;

    @FXML
    private Circle cArtista1, cArtista2, cArtista3, cArtista4, cArtistaEncontrado, cCaratula1, cCaratula2,
            cCaratula3, cCaratula4;

    @FXML
    private ChoiceBox<?> cbOdenarPor;

    @FXML
    private HBox hbBusqueda, hbTiposBusqueda;

    @FXML
    private ImageView ivCaratula, ivCaratulaR;

    @FXML
    private Label lbAlbum, lbAnioCancion, lbBienvenido, lbCodicoCancion, lbCodigoArtistaE, lbDuracion,
            lbGenero, lbNacionalidadArtistaE, lbNombreArtista, lbNombreArtistaR, lbNombreCancion,
            lbNombreCancionR, lbTArtistaE, lbTiempo, lbUrl, lbDuracion2, lbNombreCVideo;

    @FXML
    private Pane paneInfoBusqueda, paneInfoCancion, panelVideo;

    @FXML
    private ProgressBar pbProgreso, pbVolumen;

    @FXML
    private Slider sliderProgreso, sliderVolumen;

    @FXML
    private Text tNomArtistaE;

    @FXML
    private TextField tfBuscador;

    @FXML
    private TableView<?> tvListaCanciones1;

    @FXML
    private TableView<?> tvListaCanciones2;

    @FXML
    private TableView<?> tvListaCanciones3;

    @FXML
    private VBox vbBArtista, vbBusquedaOY, vbIncio, vbSugerencias, vbBuscar;

    @FXML
    private WebView wbVideo;


    //Initialize
    public UserController() {
    }
    public void initialize(){
        onActionBtnInicio();
    }



    //Metodos de Acciones de eventos

    //Metodos para cambiar entre las ventanas de Inicio y Buscar (Panel Superior Izquierdo) y salir
    @FXML
    void onActionBtnInicio() {
        btnInicio.setStyle("-fx-text-fill: white;" + "-fx-background-color: #121212");
        btnBuscar.setStyle("-fx-text-fill: #ababab;" + "-fx-background-color: #121212");

        cambiarVNodo(vbBuscar, vbIncio);
        paneInfoBusqueda.setVisible(false);
    }

    @FXML
    void onActionBtnBuscar(ActionEvent event) {
        btnBuscar.setStyle("-fx-text-fill: white;" + "-fx-background-color: #121212");
        btnInicio.setStyle("-fx-text-fill: #ababab;" + "-fx-background-color: #121212");

        cambiarVNodo(vbIncio, vbBuscar);
        cambiarVNodo(paneInfoCancion, paneInfoBusqueda);
    }
    @FXML
    void onActionBtnSalir(ActionEvent event) {
        System.exit(0);
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
    void onActionBtnPausarPlay(ActionEvent event) {
        if(btnPlayR.isVisible()){
            cambiarVNodo(btnPlayR, btnPausar);
        }else {
            cambiarVNodo(btnPausar,btnPlayR);
        }
    }
    @FXML
    void onActionBtnSiguiente(ActionEvent event) {

    }
    @FXML
    void onActionBtnVolumen(ActionEvent event) {

    }

    //Metodos que sirven para todas las ventanas que contengan un boton de reproducir cancion, rehacer, deshacer y eliminar
    @FXML
    void onActionBtnReproducir(ActionEvent event) {

    }
    @FXML
    void onActionBtnRehacer(ActionEvent event) {

    }
    @FXML
    void onActionBtnDeshacer(ActionEvent event) {

    }
    @FXML
    void onActionBtnEliminar(ActionEvent event) {

    }    @FXML
    void onActionBtnGuardar(ActionEvent event) {

    }

    //Metodos de btones de acceso a artistas y canciones sugeridas
    @FXML
    void onActionBtnLista(ActionEvent event) {

    }
    @FXML
    void onActionBtnPlayB(ActionEvent event) {

    }

    //Metodos de la barra de busqueda y los tipos de busqueda
    @FXML
    void onKeyTyped(KeyEvent event) {
        busquedaTx = this.tfBuscador.getText();

        if (this.tfBuscador.getText().isEmpty()){
            cambiarVNodo(vbBArtista,vbBusquedaOY, vbSugerencias);
            hbTiposBusqueda.setDisable(true);
        } else if (!vbBArtista.isVisible() && !vbBusquedaOY.isVisible()) {
            onActionBusquedaO();
            hbTiposBusqueda.setDisable(false);
        }
    }
    @FXML
    void onActionBusquedaArtista() {
        btnBusquedaArtista.setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: Black; -fx-background-radius:20");

        String estilo = "-fx-background-color: #121212; -fx-text-fill: 9e9e9e; -fx-background-radius:20";
        cambiarEstiloNodos(btnBusquedaY, btnBusquedaO, btnBusquedaOY, estilo);

        if(!this.tfBuscador.getText().isEmpty()){
            cambiarVNodo(vbSugerencias, vbBusquedaOY, vbBArtista);
        }
    }

    @FXML
    void onActionBusquedaO() {
        btnBusquedaO.setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: Black; -fx-background-radius:20");

        String estilo = "-fx-background-color: #121212; -fx-text-fill: 9e9e9e; -fx-background-radius:20";
        cambiarEstiloNodos(btnBusquedaArtista, btnBusquedaY, btnBusquedaOY, estilo);

        if(!this.tfBuscador.getText().isEmpty()){
            cambiarVNodo(vbSugerencias, vbBArtista, vbBusquedaOY);
        }
    }

    @FXML
    void onActionBusquedaOY() {
        btnBusquedaOY.setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: Black; -fx-background-radius:20");

        String estilo = "-fx-background-color: #121212; -fx-text-fill: 9e9e9e; -fx-background-radius:20";
        cambiarEstiloNodos(btnBusquedaArtista, btnBusquedaO, btnBusquedaY, estilo);

        if(!this.tfBuscador.getText().isEmpty()){
            cambiarVNodo(vbSugerencias, vbBArtista, vbBusquedaOY);
        }
    }
    @FXML
    void onActionBusquedaY() {
        btnBusquedaY.setStyle("-fx-background-color: #d0d0d0; -fx-text-fill: Black; -fx-background-radius:20");

        String estilo = "-fx-background-color: #121212; -fx-text-fill: 9e9e9e; -fx-background-radius:20";
        cambiarEstiloNodos(btnBusquedaArtista, btnBusquedaO, btnBusquedaOY, estilo);

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
    public void cambiarEstiloNodos(Node nodo1, Node nodo2, Node nodo3, String estilo){
        nodo1.setStyle(estilo);
        nodo2.setStyle(estilo);
        nodo3.setStyle(estilo);
    }
}
