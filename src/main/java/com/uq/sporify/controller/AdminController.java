package com.uq.sporify.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class AdminController {


    //Identificadores de la vista asociada a admin.fxml
    //Nota: Al final del proyecto se eliminan los identificadores, variables y metodos que no se usen o que se puedan optimizar

    @FXML
    private Button btnAgreCancion, btnAgregarArtista, btnAnterior, btnArtistaPopular, btnCancelarArt,
            btnCancelarCan, btnCargarArtista, btnEditCancion, btnEditarArtista, btnElimCancion,
            btnEliminarArtista, btnGuardarArt, btnGuardarCan, btnMute, btnPausar, btnPlayR, btnReproducir,
            btnSalir, btnSiguiente, btnVerCanciones, btnVideo, btnVideo2, btnGeneroMayor, btnAtras;

    @FXML
    private CheckBox cbArtistaeAE, cbGrupoAE;

    @FXML
    private Circle cirArtistaS;

    @FXML
    private ImageView ivCaratula2, ivCaratulaI, ivCaratulaR;

    @FXML
    private Label lbAlbumI, lbAnioCancionI, lbBienvenido, lbCodArtEA, lbCodCancionEA, lbCodiCancionI,
            lbCodigoArtistaS, lbDuracionI, lbDuracionR, lbGeneroI, lbNacionalidadArtistaS, lbNomArtistaI,
            lbNomArtistaR, lbNomCancionI, lbNomCancionR, lbNombreCVideo, lbTArtistaS, lbTiempo, lbUrlI;


    @FXML
    private Pane paneEditArtista, paneEditCancion, paneInfoCancion, panelVideo;

    @FXML
    private ProgressBar pbProgreso, pbVolumen;

    @FXML
    private Slider sliderProgreso, sliderVolumen;

    @FXML
    private Text tNomArtistaS;

    @FXML
    private TextField tfAlbumAE, tfAnioAE, tfArtCancionAE, tfDuracionAE, tfGeneroAE, tfNacioArtAE,
            tfNomArtAE, tfNomCancionAE, tfUrlAE;

    @FXML
    private TableView<?> tvListaArtistas, tvListaCanciones;

    @FXML
    private TableColumn<?, ?> codListArt, nomListArt, nacioListArt, tipoListArt;

    @FXML
    private TableColumn<?, ?> codListCan, nomListCan, artListCan, albumListCan, anioListCan, duraListCan;




    @FXML
    private VBox vbArtistas, vbCanArtista;

    @FXML
    private WebView wbVideo;

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        System.exit(0);
    }


    //Metodos Relacionados con la ventana de visualizacion de artistas
    @FXML
    void onActionBtnVerCan(ActionEvent event) {
        cambiarVNodo(vbArtistas, vbCanArtista);
        cambiarVNodo(paneEditCancion, paneEditArtista,paneInfoCancion);
        btnAtras.setVisible(true);
    }
    @FXML
    void onActionBtnEliminar(ActionEvent event) {

    }
    @FXML
    void onActionBtnCargarArts(ActionEvent event) {

    }

    @FXML
    void onActionBtnEditarArt(ActionEvent event) {
        cambiarVNodo(paneInfoCancion,paneEditCancion, paneEditArtista);
    }
    @FXML
    void onActionBtnAddArt(ActionEvent event) {
        cambiarVNodo(paneInfoCancion, paneEditCancion, paneEditArtista);
    }
    //Metodos relacionados con el panel de edicion y adicion de artistas sin los metodos guardar y cancelar
    //Metodos en revision porque tal vez se puede compactar en menos metodos
    @FXML
    void onActionTFAg_EdNomArt(ActionEvent event) {

    }
    @FXML
    void onActionTFAg_EdNacioArt(ActionEvent event) {

    }
    @FXML
    void onActionCBArtGro(ActionEvent event) {

    }

    //Metodos relacionados con la ventana de visualizacion de canciones del artista
    @FXML
    void onActionBtnReproducir(ActionEvent event) {

    }
    @FXML
    void onActionBtnEditarCan(ActionEvent event) {
        cambiarVNodo(paneInfoCancion, paneEditArtista,paneEditCancion);
    }
    @FXML
    void onActionBtnAddCanc(ActionEvent event) {
        cambiarVNodo(paneInfoCancion, paneEditArtista, paneEditCancion);
    }
    @FXML
    void onActionBtnEliminarCan(ActionEvent event) {

    }
    @FXML
    void onActionBtnAtras(ActionEvent event) {
        cambiarVNodo(vbCanArtista, vbArtistas);
        paneInfoCancion.setVisible(false);
        paneEditCancion.setVisible(false);
        btnAtras.setVisible(false);
    }

    //Metodos para editar y agregar canciones incluyendo los metodos guardar y cancelar que tambien se usan en la
    // ventana de editar y agregar artistas
    @FXML
    void onActionTFAg_EdCan(ActionEvent event) {

    }
    @FXML
    void onActionBtnGuardar(ActionEvent event) {

    }
    @FXML
    void onActionBtnCancelar(ActionEvent event) {

    }


    //Metodos del reproductor inferior y ventana de video
    @FXML
    void onActionBtnVideo(ActionEvent event) {
        if (!panelVideo.isVisible()){
            panelVideo.setVisible(true);
            cambiarVNodo(paneEditArtista, paneEditCancion , paneInfoCancion);
            btnVideo.setStyle("-fx-background-color: red; -fx-background-radius: 40 " );
        }else {
            panelVideo.setVisible(false);
            btnVideo.setStyle("-fx-background-color: #f3f3f3; -fx-background-radius: 40 " );
        }
    }
    @FXML
    void onActionBtnVolumen(ActionEvent event) {
    }
    @FXML
    void onActionBtnSiguiente(ActionEvent event) {

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
    void onDraggedProgreso(MouseEvent event) {
        pbProgreso.setProgress(sliderProgreso.getValue()/ sliderProgreso.getMax());
    }

    @FXML
    void onDraggedVolume(MouseEvent event) {
        pbVolumen.setProgress(sliderVolumen.getValue()/ sliderVolumen.getMax());
    }



    //Metodos propios
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

}
