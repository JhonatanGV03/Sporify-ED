package com.uq.sporify.controller;

import com.uq.sporify.App;
import com.uq.sporify.lib.ArbolBinario;
import com.uq.sporify.lib.ListaCircular;
import com.uq.sporify.lib.ListaDobleEnlazada;
import com.uq.sporify.model.Artista;
import com.uq.sporify.model.Cancion;
import com.uq.sporify.model.TiendaMusica;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    TiendaMusica sporify;
    Boolean tipo;

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
    private ImageView ivCaratula2, ivCaratulaI, ivCaratulaR, ivMute, ivCaratula21;

    @FXML
    private Label lbAlbumI, lbAnioCancionI, lbBienvenido, lbCodArtEA, lbCodCancionEA, lbCodiCancionI,
            lbCodigoArtistaS, lbDuracionI, lbDuracionR, lbGeneroI, lbNacionalidadArtistaS, lbNomArtistaI,
            lbNomArtistaR, lbNomCancionI, lbNomCancionR, lbNombreCVideo, lbTArtistaS, lbTiempo, lbEdAg, lbAgEdArt, lbCodCancionEA1;

    @FXML
    private Pane paneEditArtista, paneEditCancion, paneInfoCancion, panelVideo, paneEditCancion1;

    @FXML
    private ProgressBar pbProgreso, pbVolumen;

    @FXML
    private Slider sliderProgreso, sliderVolumen;

    @FXML
    private Text tNomArtistaS, lbUrlI;

    @FXML
    private TextField tfAlbumAE, tfAnioAE, tfArtCancionAE, tfDuracionAE, tfNacioArtAE,
            tfNomArtAE, tfNomCancionAE, tfUrlAE, tfAnioAE1, tfNomCancionAE1, tfArtCancionAE1, tfAlbumAE1
            , tfDuracionAE1, tfUrlAE1;

    @FXML
    private ChoiceBox<String> cbGeneroAE, cbGeneroAE1;

    @FXML
    private TableView<Artista> tvListaArtistas;
    @FXML
    private TableView<Cancion> tvListaCanciones;

    @FXML
    private TableColumn<?, ?> codListArt, nomListArt, nacioListArt, tipoListArt;

    @FXML
    private TableColumn<?, ?> codListCan, nomListCan, artListCan, albumListCan, anioListCan, duraListCan;

    @FXML
    private VBox vbArtistas, vbCanArtista;

    @FXML
    private WebView wbVideo;
    private WebEngine webEngine;
    private FloatControl volumeControl;

    //Variables de la clase
    private ObservableList<Artista> listArtistas = FXCollections.observableArrayList();
    private ObservableList<Cancion> listCanciones = FXCollections.observableArrayList();
    private Artista artSeleccionado;
    private Cancion canSeleccionada;

    private boolean esNuevoArtCan;


    //INITIALIZE AND CONSTRUCTO
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Esta parte se probara al final del proyecto para controlar el volumen (Aun no funciona)
        //Mixer mixer = AudioSystem.getMixer(null);
        //volumeControl = (FloatControl) mixer.getControl(FloatControl.Type.VOLUME);
        //sliderVolumen.setValue(volumeControl.getValue());


        webEngine = wbVideo.getEngine();

        sporify = TiendaMusica.getInstance();

        lbNombreCVideo.setText("No se esta reproduciendo ninguna cancion");
        lbNomCancionR.setText("No se encontró cancion en reproducción");
        lbNomArtistaR.setText("No se encontró cancion en reproducción");

        //Relleno de lista de artista
        this.codListArt.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.nomListArt.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.nacioListArt.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        this.tipoListArt.setCellValueFactory(new PropertyValueFactory<>("grupo"));

         cargarArtistas();

        //Relleno de lista de canciones
        this.codListCan.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.nomListCan.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.artListCan.setCellValueFactory(new PropertyValueFactory<>("artista"));
        this.albumListCan.setCellValueFactory(new PropertyValueFactory<>("album"));
        this.anioListCan.setCellValueFactory(new PropertyValueFactory<>("anio"));
        this.duraListCan.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        btnArtistaPopular.setText(sporify.mostrarArtistaTendencia());
        btnGeneroMayor.setText(sporify.mostrarGeneroTendendencia());

        cbGeneroAE.getItems().addAll("Rock", "Punk", "Pop", "Reggaeton", "Electrinica", "Rap");
        cbGeneroAE1.getItems().addAll("Rock", "Punk", "Pop", "Reggaeton", "Electrinica", "Rap");
        cbGeneroAE.setOnAction(this::handleChoiceBox);
    }

    private void handleChoiceBox(ActionEvent actionEvent) {
        String genero = cbGeneroAE.getValue();
        System.out.println(genero);
    }

    // METODO PARA CARGAR ARTISTAS EN EL TABLE VIEW
    public void cargarArtistas(){
        /*
         * En este punto se cargan los datos de los artistas al table view
         */
        Iterator<Artista> iterador = sporify.getListaArtistas().iterator();
        while (iterador.hasNext()) {

            Artista ArtistaList = iterador.next();
            Artista nuevoArtista = ArtistaList;

            if(!this.listArtistas.contains(nuevoArtista)) {
                this.listArtistas.add(nuevoArtista);
            }

        }
        this.tvListaArtistas.setItems(listArtistas);
    }

    //Cargar las canciones de un artista en el table view
    public void cargarCanciones(){

        ListaDobleEnlazada<Cancion> cancionList = sporify.retornarArtista(artSeleccionado.getNombre()).getListaCanciones();

        for (Cancion cancion : cancionList) {
            Cancion nuevaCancion = cancion;
            Boolean bandera = false;
             for (Cancion canAux : listCanciones) {
                if (canAux.getCodigo().equals(nuevaCancion.getCodigo())) {
                    bandera = true;
                }
            }
            if(bandera == false) {
                this.listCanciones.add(nuevaCancion);
            }
        }
        this.tvListaCanciones.setItems(listCanciones);
    }

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        try {
            sporify.guardarInfo();
            cambiarEscena("vista/login.fxml", "Sporify", 679, 456);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodos Relacionados con el panel de visualizacion de artistas
    @FXML
    void seleccionarTvListArtistas(MouseEvent event) {
        artSeleccionado = this.tvListaArtistas.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onActionBtnVerCan(ActionEvent event) {
        if (artSeleccionado != null) {
            cambiarVNodo(vbArtistas, vbCanArtista);
            btnAtras.setVisible(true);
            paneEditArtista.setVisible(false);
            lbCodigoArtistaS.setText(artSeleccionado.getCodigo());
            tNomArtistaS.setText(artSeleccionado.getNombre());
            lbNacionalidadArtistaS.setText(artSeleccionado.getNacionalidad());
            if(artSeleccionado.getGrupo()) lbTArtistaS.setText("Grupo");
            else lbTArtistaS.setText("Solista");

            cargarCanciones();
            System.out.println(artSeleccionado.getNombre() + " " + artSeleccionado.getNacionalidad());
            tvListaCanciones.refresh();
        }else {
            alerta(Alert.AlertType.ERROR, "---Error - No Seleccionado" , "Asegurese de seleccionar un artista para poder ver las canciones del artista");
        }


    }
    @FXML
    void onActionBtnEliminar(ActionEvent event) {
        if (artSeleccionado != null) {
            sporify.setListaCanciones(sporify.depurarArtistayCanciones(artSeleccionado));
            listArtistas.remove(artSeleccionado);

            cargarArtistas();
            tvListaArtistas.refresh();

            artSeleccionado = null;
        }else {
            alerta(Alert.AlertType.ERROR, "---Error - No Seleccionado" , "Asegurese de seleccionar un artista antes de eliminarlo");
        }
    }
    @FXML
    void onActionBtnCargarArts(ActionEvent event) { //Falta agregar el metodo que combinara los artistas cargados aqui con los actuales
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        File archivo = new File(fileChooser.getSelectedFile().getPath());
        sporify.AgregarPorArchivo(archivo);
        cargarArtistas();
        tvListaArtistas.refresh();
    }

    @FXML
    void onActionBtnEditarArt(ActionEvent event) {
        if (artSeleccionado != null) {
            lbAgEdArt.setText("Editar Artista");
            lbCodArtEA.setText(artSeleccionado.getCodigo());
            tfNomArtAE.setText(artSeleccionado.getNombre());
            tfNacioArtAE.setText(artSeleccionado.getNacionalidad());
            if (!artSeleccionado.getGrupo()) cbArtistaeAE.setSelected(true);
            else cbGrupoAE.setSelected(true);
            cambiarVNodo(paneInfoCancion, paneEditCancion, paneEditArtista);
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar un artista antes de editarlo");
        }

    }
    @FXML
    void onActionBtnAddArt(ActionEvent event) {
        lbAgEdArt.setText("Agregar Artista");
        cambiarVNodo(paneInfoCancion, paneEditCancion, paneEditArtista);
        esNuevoArtCan = true;
        lbCodArtEA.setText("Auto");
        tfNomArtAE.setText("");
        tfNacioArtAE.setText("");
        cbArtistaeAE.setSelected(false);
        cbGrupoAE.setSelected(false);
    }
    //Metodos relacionados con el panel de edicion y adicion de artistas sin los metodos guardar y cancelar
    //Metodos en revision porque tal vez se puede compactar en menos metodos
    @FXML
    void onActionTFAg_EdNomArt(ActionEvent event) {} //ES PROBABLE QUE ESTE METODO NO SE NECESITE
    @FXML
    void onActionTFAg_EdNacioArt(ActionEvent event) {} //ES PROBABLE QUE ESTE METODO NO SE NECESITE
    @FXML
    void onActionCBArtGro(ActionEvent event) {} //ES PROBABLE QUE ESTE METODO NO SE NECESITE

    //Metodos relacionados con la ventana de visualizacion de canciones del artista
    @FXML
    void onActionBtnReproducir(ActionEvent event) {
        if (canSeleccionada != null) {
            String url = organizarURL(canSeleccionada.getUrlYoutube());
            webEngine.load(url);
            wbVideo.setOpacity(1);
            cambiarVNodo(paneEditArtista, paneEditCancion, paneInfoCancion);
            panelVideo.setVisible(true);
            btnVideo.setStyle("-fx-background-color: red; -fx-background-radius: 40 " );
            mostrarInfoCancion();
            lbNomCancionR.setText(canSeleccionada.getNombre());
            lbNomArtistaR.setText(canSeleccionada.getArtista());
            lbNombreCVideo.setText(canSeleccionada.getNombre());

            sporify.getListaArtistas().encontrar(artSeleccionado).setReproducciones(sporify.getListaArtistas().encontrar(artSeleccionado).getReproducciones()+1);
            canSeleccionada = null;
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de reproducirla.");
        }
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

     //VARIABLE TEMPORAL
    @FXML
    void seleccionarTvListCanciones(MouseEvent event) {
        canSeleccionada = this.tvListaCanciones.getSelectionModel().getSelectedItem();
        if (canSeleccionada != null) {
            mostrarInfoCancion();
        }
        System.out.println(canSeleccionada);
    }


    //public void cargar
    @FXML
    void onActionBtnEditarCan(ActionEvent event) {
        if (canSeleccionada != null) {
            esNuevoArtCan = false;
            cambiarVNodo(paneInfoCancion, paneEditArtista,paneEditCancion);
            lbCodCancionEA.setText(canSeleccionada.getCodigo());
            tfNomCancionAE.setText(canSeleccionada.getNombre());
            tfArtCancionAE.setText(canSeleccionada.getArtista());
            tfAlbumAE.setText(canSeleccionada.getAlbum());
            cbGeneroAE.setValue(canSeleccionada.getGenero());
            tfUrlAE.setText(canSeleccionada.getUrlYoutube());
            tfDuracionAE.setText(String.valueOf(canSeleccionada.getDuracion()));
            tfAnioAE.setText(canSeleccionada.getAnio());
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de editarla");
        }

    }
    @FXML
    void onActionBtnAddCanc(ActionEvent event) {
        esNuevoArtCan = true;
        //tfNomArtAE.setText(artSeleccionado.getNombre());
        cambiarVNodo(paneInfoCancion, paneEditArtista, paneEditCancion1);
        lbCodCancionEA1.setText("Auto");
        tfNomCancionAE1.setText("");
        tfArtCancionAE1.setText(artSeleccionado.getNombre());
        System.out.println(artSeleccionado.getNombre());
        tfAlbumAE1.setText("");
        cbGeneroAE1.setValue("");
        tfUrlAE1.setText("");
        tfDuracionAE1.setText("");
        tfAnioAE1.setText("");
        canSeleccionada = null;
    }
    @FXML
    void onActionBtnEliminarCan(ActionEvent event) {
        if (canSeleccionada != null) {
            sporify.eliminarCancion(canSeleccionada);
            sporify.retornarArtista(artSeleccionado.getNombre()).eliminarPorValor(canSeleccionada.getCodigo());
            System.out.println(canSeleccionada);
            listCanciones.remove(canSeleccionada);
            cargarCanciones();
            tvListaArtistas.refresh();

        }else {
            alerta(Alert.AlertType.ERROR, "Sporify - No Seleccionado", "Asegurese de seleccionar una cancion antes de eliminarlo");
        }
        canSeleccionada = null;
    }
    @FXML
    void onActionBtnAtras(ActionEvent event) {
        cambiarVNodo(vbCanArtista, vbArtistas);
        artSeleccionado = new Artista();
        listCanciones.clear();
        tvListaCanciones.setItems(listCanciones);
        paneEditCancion.setVisible(false);
        paneEditCancion1.setVisible(false);
        paneInfoCancion.setVisible(false);
        btnAtras.setVisible(false);
    }

    //Metodos para editar y agregar canciones incluyendo los metodos guardar y cancelar que tambien se usan en la
    // ventana de editar y agregar artistas
    @FXML
    void onActionTFAg_EdCan(ActionEvent event) {} //ES PROBABLE QUE ESTE METODO NO SE NECESITE
    //modificar cancion ya existente
    @FXML
    void onActionBtnGuardarCan(ActionEvent event) { //Metodo para revision
        String nombre = tfNomCancionAE.getText();
        String artista = tfArtCancionAE.getText();
        String album = tfAlbumAE.getText();
        String anio = tfAnioAE.getText();
        int duracion = Integer.parseInt(tfDuracionAE.getText());
        String genero = cbGeneroAE.getValue();
        String url = tfUrlAE.getText();
        Cancion x =canSeleccionada;

//Editar



        if (verificarCamposCancion()){
            if(canSeleccionada !=null) {
                sporify.eliminarCancion(x);
                sporify.retornarArtista(artSeleccionado.getNombre()).eliminarPorValor(canSeleccionada.getCodigo());
                x.editar(nombre,artista,"",anio,url,duracion,genero,album);
                sporify.guardarCancion(x);
                sporify.retornarArtista(artSeleccionado.getNombre()).agregarCancion(x);

            }else{
                alerta(Alert.AlertType.ERROR, "---Error", "La cancion ya existe");
            }

        }else{
            alerta(Alert.AlertType.ERROR, "---Error", "No estan todos los campos completos");
        }
        listCanciones.clear();
        cargarCanciones();
        tvListaCanciones.refresh();
        esNuevoArtCan = false;
        canSeleccionada = null;
        paneEditCancion.setVisible(false);
    }
    //nueva cancion
    @FXML
    void onActionBtnGuardarCan1(ActionEvent event) { //Metodo para revision
        String nombre = tfNomCancionAE1.getText();
        String artista = tfArtCancionAE1.getText();
        String album = tfAlbumAE1.getText();
        String anio = tfAnioAE1.getText();
        int duracion = Integer.parseInt(tfDuracionAE1.getText());
        String genero = cbGeneroAE1.getValue();
        String url = tfUrlAE1.getText();
        Cancion x = new Cancion(nombre,artista,"",anio,url,duracion,genero,album);
//Agregar

        if (verificarCamposCancion1()) {
            if (!sporify.encontrarCancion(x)) {
                sporify.guardarCancion(x);
                sporify.retornarArtista(artSeleccionado.getNombre()).agregarCancion(x);
            } else {
                alerta(Alert.AlertType.ERROR, "---Error", "La cancion ya existe");
            }
            listCanciones.clear();
            cargarCanciones();
            tvListaCanciones.refresh();
        }else {
            alerta(Alert.AlertType.ERROR, "---Error", "No estan todos los campos completos");
        }
        esNuevoArtCan = false;
        canSeleccionada = null;
        paneEditCancion1.setVisible(false);
    }
    @FXML
    void onActionBtnGuardarArt(ActionEvent event) {

        String nombre = tfNomArtAE.getText();
        String nacionalidad = tfNacioArtAE.getText();
        Boolean grupo ;
        if (cbGrupoAE.isSelected())grupo = true;
            else grupo = false;

        if (verificarCamposArtista()){
            Artista x = new Artista(nombre, nacionalidad, grupo);
            if(!sporify.getListaArtistas().buscar(x)) {
                sporify.agregarArtista(x);
                System.out.println("Guardado " + sporify.getListaArtistas().encontrar(x));
            }else if (!esNuevoArtCan){
                sporify.eliminarArtista(artSeleccionado);
                listArtistas.remove(artSeleccionado);
                sporify.agregarArtista(x);
                System.out.println("Eliminado");

            }else {
                alerta(Alert.AlertType.ERROR, "---Error", "El artista ya existe");
            }
            cargarArtistas();
            tvListaArtistas.refresh();
        }
        artSeleccionado = null;
        paneEditArtista.setVisible(false);
        esNuevoArtCan = false;
    }

    public boolean verificarCamposCancion() {
        boolean cumple;
        if (tfNomCancionAE.getText().isEmpty() || tfArtCancionAE.getText().isEmpty() || tfAlbumAE.getText().isEmpty() ||
                tfAnioAE.getText().isEmpty() || tfDuracionAE.getText().isEmpty() || cbGeneroAE.getValue().isEmpty() ||
                tfUrlAE.getText().isEmpty() || !(cbGeneroAE.getItems().contains(cbGeneroAE.getValue()))){
            alerta(Alert.AlertType.ERROR, "---Error", "Debe llenar todos los campos");
            cumple= false;
        }else {
            cumple= true;
        }
        return cumple;
    }
    public boolean verificarCamposCancion1() {
        boolean cumple;
        if (tfNomCancionAE1.getText().isEmpty() || tfArtCancionAE1.getText().isEmpty() || tfAlbumAE1.getText().isEmpty() ||
                tfAnioAE1.getText().isEmpty() || tfDuracionAE1.getText().isEmpty() || cbGeneroAE1.getValue().isEmpty() ||
                tfUrlAE1.getText().isEmpty() || !(cbGeneroAE1.getItems().contains(cbGeneroAE1.getValue()))){
            alerta(Alert.AlertType.ERROR, "---Error", "Debe llenar todos los campos");
            cumple= false;
        }else {
            cumple= true;
        }
        return cumple;
    }

    public boolean verificarCamposArtista() {
            boolean cumple;
            if (tfNomArtAE.getText().isEmpty() || tfNacioArtAE.getText().isEmpty() || (!cbGrupoAE.isSelected() && !cbArtistaeAE.isSelected())) {
                alerta(Alert.AlertType.ERROR, "---Error", "Debe llenar todos los campos");
                cumple = false;
            } else if (cbGrupoAE.isSelected() && cbArtistaeAE.isSelected()) {
                alerta(Alert.AlertType.ERROR, "---Error", "No puede seleccionar dos opciones a la vez");
                cumple = false;
            } else {
                cumple = true;
            }
            return cumple;

    }
    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        paneEditCancion.setVisible(false);
        paneEditArtista.setVisible(false);
        canSeleccionada = null;
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
    private boolean esImgVol1 = true;
    @FXML
    void onActionBtnVolumen(ActionEvent event) {
        boolean isMuted = volumeControl.getValue() == volumeControl.getMinimum();
        if (isMuted) {
            // Unmute
            volumeControl.setValue((float) sliderVolumen.getValue());
            btnMute.setText("Mute");
        } else {
            // Mute
            volumeControl.setValue(volumeControl.getMinimum());
            btnMute.setText("Unmute");
        }

        Image imgVol;
        if (esImgVol1){
            imgVol = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/mute.png")));
            esImgVol1 = false;
            pbVolumen.setProgress(0);
            sliderVolumen.setValue(0);
        }else {
            imgVol = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/sound.png")));
            esImgVol1 = true;
            pbVolumen.setProgress(0.5);
            sliderVolumen.setValue(50);
        }
        ((ImageView) btnMute.getGraphic()).setImage(imgVol);
    }
    @FXML
    void onActionBtnSiguiente(ActionEvent event) {
        int posicionActual;//canSeleccionada.  //Siguiente cancion de la lista de canciones;
        //canSeleccionada = sporify.getListaCanciones().iterator(posicionActual+1);
    }
    @FXML
    void onActionBtnAnterior(ActionEvent event) {

    }

    @FXML
    void onDraggedVolume(MouseEvent event) {
        pbVolumen.setProgress(sliderVolumen.getValue()/ sliderVolumen.getMax());
        double volume = sliderVolumen.getValue();
        setSystemVolume(volume);
    }

    private void setSystemVolume(double volume) {
        volumeControl.setValue((float) volume);
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
    public void alerta(Alert.AlertType tipo, String titulo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
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

    public void depurarArtista1(Artista artista) {
        for (Cancion cancion : sporify.retornarArtista(artista.getNombre()).getListaCanciones()) {
            sporify.retornarArtista(artista.getNombre()).eliminarPorValor(cancion.getCodigo());
            sporify.getListaCanciones().eliminar(cancion);
        }
        sporify.eliminarArtista(artista);
    }

    public void depurarArtista(Artista artista) {
        ArbolBinario<Artista> listaArtistas = sporify.getListaArtistas();
        for (Cancion cancion : sporify.getListaCanciones()) {
            if (cancion.equals(artista)) {
                sporify.retornarArtista(artista.getNombre()).getListaCanciones();
                sporify.eliminarArtista(artista);
                break;
            }
        }
    }
    public void mostrarInfoCancion(){
        if (canSeleccionada!=null){
            lbCodiCancionI.setText(canSeleccionada.getCodigo());
            lbNomCancionI.setText(canSeleccionada.getNombre());
            lbNomArtistaI.setText(canSeleccionada.getArtista());
            lbAlbumI.setText(canSeleccionada.getAlbum());
            lbGeneroI.setText(canSeleccionada.getGenero());
            lbUrlI.setText(canSeleccionada.getUrlYoutube());
            lbDuracionI.setText(String.valueOf(canSeleccionada.getDuracion()));
            lbAnioCancionI.setText(canSeleccionada.getAnio());
            //imgCaratulaI.setImage(new Image(canSeleccionada.getCaratula()));
            cambiarVNodo(paneEditArtista, paneEditCancion, paneInfoCancion);
        }
    }
    }
