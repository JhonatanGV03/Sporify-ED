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
import javafx.scene.input.KeyCode;
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

public class UserController implements Initializable {

    //------------------------------------Atributos y Variables----------------------------------------------------//
    // Atributos de la clase
    private TiendaMusica sporify;  //Instancia de la tienda de musica
    private ObservableList<Cancion> listCanciones = FXCollections.observableArrayList(); //Lista de seguimiento de canciones
    private Cancion canSeleccionada;  //Cancion seleccionada
    private Artista artistaEncontrado; //Artista encontrado
    private Boolean esBusquedaY = false; //Booleano que indica si la busqueda es de tipo Y o no
    private String[] nomArtistasSugeridos = new String[4];  //Arreglo de nombres de artistas sugeridos
    private String[] codCancionesSugeridas = new String[4];  //Arreglo de codigos de canciones sugeridas
    private String reproduccionAnterior;  //String que indica la reproduccion inmediatamente anterior
    //Imagen por defecto de un artista
    private final Image imgArtDefault = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/Artista.png")));

    //-----------------------------Identificadores de la vista asociada a user.fxml------------------------------------------//
    //Identificadores de los elementos de la vista
    @FXML
    private Button btnAnterior, btnBuscar, btnBusquedaArtista, btnBusquedaO, btnBusquedaY,
            btnDeshacer1, btnEliminar1, btnInicio, btnRehacer1, btnVideo, btnGuardarF;

    @FXML
    private Circle cArtista1, cArtista2, cArtista3, cArtista4, cArtistaEncontrado, cCaratula1, cCaratula2,
            cCaratula3, cCaratula4;

    @FXML
    private ChoiceBox<String> cbOdenarPor;

    @FXML
    private HBox hbTiposBusqueda;

    @FXML
    private ImageView ivCaratula, ivCaratulaR;

    @FXML
    private Label lbAlbum, lbAnioCancion, lbBienvenido, lbCodicoCancion, lbCodigoArtistaE, lbDuracion,
            lbGenero, lbNacionalidadArtistaE, lbNombreArtista, lbNombreArtistaR, lbNombreCancion,
            lbNombreCancionR, lbTArtistaE, lbNombreCVideo, lbmusica, lbArtSugerido1,
            lbArtSugerido2, lbArtSugerido3, lbArtSugerido4, lbCanSugerida1, lbCanSugerida2, lbCanSugerida3, lbCanSugerida4;

    @FXML
    private Pane paneInfoBusqueda, paneInfoCancion, panelVideo;

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

    private WebEngine webEngine; //Motor de pagina web

    //--------------------------------Metodo inicializador--------------------------------//
    // Metodo que se ejecuta al iniciar la ventana
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sporify = TiendaMusica.getInstance();  //Instancia de la tienda de musica
        try {
            sporify.cargarInfo();  //Carga la informacion de la tienda de musica
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Obtiene el nombre de usuario actual y lo muestra en la vista
        lbBienvenido.setText("Bienvenido, " + sporify.getUsuarioActual().getUsuario());
        webEngine = wbVideo.getEngine(); //Inicializa el motor de video
        //Organiza las columnas de la tabla de canciones de acuerdo al contenido que le pertenece (Playlist Y Tienda)
        initTabla(codListCan1, nomListCan1, artListCan1, albumListCan1, anioListCan1, duraLisCan1);
        //Organiza las columnas de la tabla de canciones de acuerdo al contenido que le pertenece (Canciones del artista encontrado)
        initTabla(codListCan2, nomListCan2, artListCan2, albumListCan2, anioListCan2, duraLisCan2);
        //Organiza las columnas de la tabla de canciones de acuerdo al contenido que le pertenece (Canciondes de la busqueda O y Y)
        initTabla(codListCan3, nomListCan3, artListCan3, albumListCan3, anioListCan3, duraLisCan3);
        //Init ChoiceBox y agrega las opciones de ordenamiento
        cbOdenarPor.getItems().addAll("Codigo", "Nombre", "Artista", "Album", "Año", "Duracion");
        cbOdenarPor.setValue("Nombre");
        cbOdenarPor.setOnAction(this::ordenarLista);
        sporify.getUsuarioActual().ordenarPorAtributo("Nombre");
        artistasSugeridos(); //Carga los artistas sugeridos en la busqueda
        cancionesSugeridas(); //Carga las canciones sugeridas en la busqueda
        cargarCancionesTienda(); //Carga las canciones de la tienda de musica
        System.out.println(sporify.getListaCanciones());
    }

    //------------------------Metodos de accion de los botones y otros elementos de la vista-----------------------------//
    //-----------------Metodos de los botones lateral superior izquierdo------------------//

    /**
     * Metodo que se ejecuta al presionar el boton de inicio se encarga de mostrar la vista de inicio
     *  carga las canciones tienda y oculta botones no necesarios
     **/
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

    /**
     * Metodo que se ejecuta al presionar el boton de buscar se encarga de mostrar la vista de busqueda
     **/
    @FXML
    void onActionBtnBuscar(ActionEvent event) {
        btnBuscar.setStyle("-fx-text-fill: white;" + "-fx-background-color: #121212");
        btnInicio.setStyle("-fx-text-fill: #ababab;" + "-fx-background-color: #121212");
        cambiarVNodo(vbIncio, vbBuscar);
        cambiarVNodo(paneInfoCancion, paneInfoBusqueda);
        canSeleccionada = null;
    }

    /**
     * Metodo que se ejecuta al presionar el boton de biblioteca se encarga de mostrar la vista de biblioteca
     * carga las canciones de la playlist y oculta botones no necesarios y muestra los elementos necesarios
     **/
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
        paneInfoBusqueda.setVisible(false);
        listCanciones.clear();
        cargarCancionesPlaylist();
        canSeleccionada = null;
    }

    //--------Metodos relacionados con las tablas y botones de accion de las mismas---------//

    /**
     * Metodo que se ejecuta al presionar el boton de guardar cancion en lista de reproduccion
     * Puede ser ejecutado desde la vista de inicio o desde la vista de busqueda de artista o Busqeda OY
     * Se encarga de guardar la cancion seleccionada en la lista de reproduccion del usuario actual
     **/
    @FXML
    void onActionBtnGuardar(ActionEvent event) {
        if (canSeleccionada != null) {
            if (sporify.getUsuarioActual().existeCancion(canSeleccionada)) {
                alerta(Alert.AlertType.WARNING, "Sporify - Cancion Repetida", "La cancion seleccionada ya se encuentra en su lista de reproduccion.");
            } else {
                sporify.getUsuarioActual().guardarCancion(canSeleccionada);
                sporify.getUsuarioActual().actualizarFavoritos();
                //sporify.getUsuarioActual().addPreviusStates(canSeleccionada);
                //sporify.getUsuarioActual().ClearNextStates();
                alerta(Alert.AlertType.INFORMATION, "Sporify - Cancion Agregada", "La cancion seleccionada se ha agregado a su lista de reproduccion.");
            }
            paneInfoCancion.setVisible(false);
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de agregarla.");
        }
        canSeleccionada = null;
    }

    /**
     * Metodo que se ejecuta al presionar el boton reproducir
     * se encarga de reproducir la cancion seleccionada de la lista que se encuentre en la vista
     * puede ser desde la vista de inicio o desde la vista de busqueda de artista o Busqeda OY
     * se encarga de mostrar la informacion de la cancion en el panel de informacion de la cancion
     * y de reproducir la cancion en el panel de video
     **/
    @FXML
    void onActionBtnReproducir(ActionEvent event) {
        if (canSeleccionada != null) {
            //String url = organizarURL(canSeleccionada.getUrlYoutube());
            webEngine.load(canSeleccionada.getUrlYoutube());
            wbVideo.setOpacity(1);
            cambiarVNodo(paneInfoBusqueda, paneInfoCancion);
            panelVideo.setVisible(true);
            btnVideo.setStyle("-fx-background-color: red; -fx-background-radius: 40 " );
            mostrarInfoCancion();
            lbNombreCVideo.setText(canSeleccionada.getNombre());
            lbNombreArtistaR.setText(canSeleccionada.getArtista());
            lbNombreCancionR.setText(canSeleccionada.getNombre());
            ivCaratulaR.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(canSeleccionada.getCaratula()))));
            sporify.reproducir(canSeleccionada);
            reproduccionAnterior = canSeleccionada.getCodigo();
            canSeleccionada = null;
            btnAnterior.setDisable(false);
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de reproducirla.");
        }
    }

    /**
     * Metodo que se ejecuta al seleccionar o presionar una cancion en una tabla de la vista
     * se encarga de mostrar la informacion de la cancion en el panel de informacion de la cancion
     * y de guardar la cancion seleccionada en la variable canSeleccionada
     **/
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
    void onActionBtnRehacer() {
        if (!sporify.getUsuarioActual().getNextStates().isEmpty()) {
            Cancion ultimaCancion = sporify.getUsuarioActual().getNextStates().pop();
            sporify.getUsuarioActual().eliminarCancion(ultimaCancion);
            sporify.getUsuarioActual().addPreviusStates(ultimaCancion);
        }
        btnDeshacer1.setDisable(false);
        btnRehacer1.setDisable(true);
        listCanciones.clear();
        cargarCancionesPlaylist();
        canSeleccionada = null;
        paneInfoCancion.setVisible(false);
    }

    @FXML
    void onActionBtnDeshacer() {
        if (!sporify.getUsuarioActual().getPreviusStates().isEmpty()) {
            Cancion ultimaCancion = sporify.getUsuarioActual().getPreviusStates().pop();
            sporify.getUsuarioActual().guardarCancion(ultimaCancion);
            sporify.getUsuarioActual().addNextStates(ultimaCancion);
        }
        btnDeshacer1.setDisable(true);
        btnRehacer1.setDisable(false);
        listCanciones.clear();
        cargarCancionesPlaylist();
        canSeleccionada = null;
        paneInfoCancion.setVisible(false);
    }

    /**
     * Metodo que se encarga de ordenar la playlist del usuario actual con el atributo seleccionado
     **/
    @FXML
    private void ordenarLista(ActionEvent actionEvent) {
        String orden = cbOdenarPor.getValue();
        sporify.getUsuarioActual().ordenarPorAtributo(orden);
        System.out.println("Ordenando por: " + orden);
        listCanciones.clear();
        cargarCancionesPlaylist();
    }

    /**
     * Metodo que se ejecuta al presionar el btn control mas el boton Z o Y
     * se encarga de deshacer o rehacer la ultima accion realizada por el usuario
     **/
    @FXML
    void inputListener(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL && event.getCode() == KeyCode.Z) {
            System.out.println("Control + Z");
            onActionBtnDeshacer();
        } else if (event.getCode() == KeyCode.CONTROL && event.getCode() == KeyCode.Y) {
            System.out.println("Control + Y");
            onActionBtnRehacer();
        }
    }

    /**
     * Metodo que se ejecuta al presionar el btn eliminar en la vista de biblioteca
     * se encarga de eliminar la cancion seleccionada de la playlist del usuario
     **/
    @FXML
    void onActionBtnEliminar(ActionEvent event) {
        if (canSeleccionada != null) {
            System.out.println("Selected song: " + canSeleccionada); // add this line
            if (sporify.getUsuarioActual().existeCancion(canSeleccionada)) {
                sporify.getUsuarioActual().eliminarCancion(canSeleccionada);
                System.out.println("Deleted song: " + canSeleccionada); // add this line
                sporify.getUsuarioActual().addPreviusStates(canSeleccionada);
                //sporify.getUsuarioActual().ClearNextStates();
                listCanciones.clear();
                cargarCancionesPlaylist();
                alerta(Alert.AlertType.INFORMATION, "Sporify - Cancion Eliminada", "La cancion seleccionada se ha eliminado de su lista de reproduccion.");
            } else {
                alerta(Alert.AlertType.WARNING, "Sporify - Cancion No Encontrada", "La cancion seleccionada no se encuentra en su lista de reproduccion.");
            }

        }
        canSeleccionada = null;
        paneInfoCancion.setVisible(false);

    }

    //---------------------Metodos relacionados con la busuqeda---------------------//

    /**
     * Metodo que se ejecuta al presionar el boton de la lista de artista sugerido en la vista de busqueda
     * se encarga de mostrar las canciones del artista seleccionado en la vista de artista y canciones sugeridas
     **/
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

    /**
     * Metodo que se ejecuta al presionar el boton de reproducir de una de las canciones sugeridas en la vista de busqueda
     * se encarga de reproducir la cancion sugerida seleccionada y mostrar la informacion de la cancion
     **/
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

    /**
     * Metodo que se ejecuta cada que se escribe un caracter en el buscador de la vista de busqueda
     * si el buscador esta vacio se ocultan las vistas de busqueda y se muestra la vista de artistas sugeridos
     * si el buscador no esta vacio se muestra la vista de busqueda O mientras el usuario escoge el tipo de busqueda
     * si el buscador no esta vacio y el usuario ya escogio el tipo de busqueda se muestra la vista de busqueda O o Y o de artistas
     * y se va actualizando la lista de canciones que coinciden con la busqueda
     **/
    @FXML
    void onKeyTyped(KeyEvent event) {
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

    /**
     * Metodo que se ejecuta al presionar el boton de busqueda de artista en la vista de busqueda
     * o que se llame en el metodo onKeyTyped, Se encarga de mostrar la vista de busqueda de artista
     * y de actualizar la lista de canciones que coinciden con el artista encontrado la busqueda
     * Si no se encuentra el artista se muestra un mensaje de no encontrado
     **/

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
        canSeleccionada = null;
    }

    /**
     * Metodo que se ejecuta al presionar el boton de busqueda O en la vista de busqueda
     * o que se llame en el metodo onKeyTyped, Se encarga de mostrar la vista de busqueda O (un atributo que coincida)
     * y de actualizar la lista de canciones que coinciden con la busqueda
     **/
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
        canSeleccionada = null;
    }

    /**
     * Metodo que se ejecuta al presionar el boton de busqueda Y en la vista de busqueda
     * o que se llame en el metodo onKeyTyped, Se encarga de mostrar la vista de busqueda Y (todos atributo que coincida)
     * y de actualizar la lista de canciones que coinciden con la busqueda
     **/
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
        canSeleccionada = null;
    }

    //----------Metodos relacionados con el reproductor inferior y la visualizacion del video ---------//

    /**
     * Metodo que se ejecuta al presionar el boton de visualizar video
     * Se encarga de mostrar la ventana de visualizacion de video u ocultarla si ya esta visible
     **/
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

    /**
     * Metodo que se ejecuta al presionar el boton de anterior en el reproductor
     * Se encarga de tomar la cancion antes reproducida y reproducirla (Solo funciona con una cancionanterior)
     **/
    @FXML
    void onActionBtnAnterior(ActionEvent event) {
        canSeleccionada = sporify.getCancioncita(reproduccionAnterior);
        onActionBtnReproducir(event);
    }

    /**
     * Metodo que se ejecuta al presionar el boton siguiente en el reproductor
     * Se encarga de tomar una cancion aleatoria en la lista de canciones del usuario actual y reproducirla
     **/
    @FXML
    void onActionBtnSiguiente(ActionEvent event) {
        int n = (int) Math.random()*(sporify.getUsuarioActual().getListaCanciones().getTamanio()-1);
        int cont = 0;
        for (Cancion cancion : sporify.getUsuarioActual().getListaCanciones()) {
            if (cont == n){
                canSeleccionada = sporify.getUsuarioActual().getListaCanciones().obtener(n);
            }
            cont++;
        }
        onActionBtnReproducir(event);
    }

    // -------------Salir ------------------//

    /**
     * Metodo que se ejecuta al presionar el boton de salir
     * Se encarga de regresar a la ventana de login y de guardar la informacion de los usuarios
     **/
    @FXML
    void onActionBtnSalir(ActionEvent event) {
        sporify.getUsuarioActual().actualizarFavoritos();
        Usuario userActual = sporify.getUsuarioActual();
        sporify.eliminarUsuario(userActual.getUsuario());
        sporify.agregarUsuario(userActual);
        try {
            sporify.guardarInfo();
            cambiarEscena("vista/login.fxml", "Sporify", 679, 456);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // --------------------------Metodos de la Clase --------------------------------------//

    /**
     * Metodo que se encarga de cargar las canciones, caratula y nombre que se mostraran en la vista de busqueda
     **/
    private void cancionesSugeridas(){
        int cont = 0;
        ListaCircular<Integer> numeros = generarNumerosAleatorios(sporify.getListaCanciones().getTamanio()-1, 0, sporify.getListaCanciones().getTamanio()-1);
        if(numeros.getTamanio()==1){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula()))))); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()==2){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula()))))); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(1)){
                    cCaratula2.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula())))));
                    lbCanSugerida2.setText(cancion.getNombre());
                    codCancionesSugeridas[1] = cancion.getCodigo();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()==3){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula()))))); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(1)){
                    cCaratula2.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula())))));
                    lbCanSugerida2.setText(cancion.getNombre());
                    codCancionesSugeridas[1] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(2)){
                    cCaratula3.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula())))));
                    lbCanSugerida3.setText(cancion.getNombre());
                    codCancionesSugeridas[2] = cancion.getNombre();
                }
                cont++;
            }
        }
        else if(numeros.getTamanio()>=4){
            for (Cancion cancion: sporify.getListaCanciones()){
                if(cont == numeros.obtener(0)){
                    cCaratula1.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula()))))); //Mientras reparo lo de la caraturla
                    lbCanSugerida1.setText(cancion.getNombre());
                    codCancionesSugeridas[0] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(1)){
                    cCaratula2.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula())))));
                    lbCanSugerida2.setText(cancion.getNombre());
                    codCancionesSugeridas[1] = cancion.getCodigo();
                }
                if (cont == numeros.obtener(2)){
                    cCaratula3.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula())))));
                    lbCanSugerida3.setText(cancion.getNombre());
                    codCancionesSugeridas[2] = cancion.getNombre();
                }
                if (cont == numeros.obtener(3)){
                    cCaratula4.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream(cancion.getCaratula())))));
                    lbCanSugerida4.setText(cancion.getNombre());
                    codCancionesSugeridas[3] = cancion.getNombre();
                }
                cont++;
            }
        }
    }

    /**
     * Metodo que se encarga de generar numeros aleatorios con un minimo, maximo y cantidad de numeros a generar
     * @param cantidad de numeros a generar
     * @param minimo valor posible
     * @param maximo valor posible
     * @return Lista Circular de tipo integer con los numeros generados
     **/
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

    /**
     * Metodo que se encarga de generar y cargar los artistas sugeridos que se mostraran en la ventana de busqueda
     **/
    private void artistasSugeridos () {
        //Recorrer la lista de artistas hasta una posicion random, y obtener el artista
        int cont = 0;
        ListaCircular<Integer> numeros = generarNumerosAleatorios(sporify.getListaArtistas().obtenerTamanio() - 1, 0, sporify.getListaArtistas().obtenerTamanio() - 1);
        if (numeros.getTamanio() == 1) {
            for (Artista artista : sporify.getListaArtistas()) {
                if (cont == numeros.obtener(0)) {
                    cArtista1.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido1.setText(artista.getNombre());
                    nomArtistasSugeridos[0] = artista.getNombre();
                }
                cont++;
            }
        }

        if (numeros.getTamanio() == 2) {
            for (Artista artista : sporify.getListaArtistas()) {
                if (cont == numeros.obtener(0)) {
                    cArtista1.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido1.setText(artista.getNombre());
                    nomArtistasSugeridos[0] = artista.getNombre();
                } else if (cont == numeros.obtener(1)) {
                    cArtista2.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido2.setText(artista.getNombre());
                    nomArtistasSugeridos[1] = artista.getNombre();
                }
                cont++;
            }
        } else if (numeros.getTamanio() == 3) {
            for (Artista artista : sporify.getListaArtistas()) {
                if (cont == numeros.obtener(0)) {
                    cArtista1.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido1.setText(artista.getNombre());
                    nomArtistasSugeridos[0] = artista.getNombre();
                } else if (cont == numeros.obtener(1)) {
                    cArtista2.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido2.setText(artista.getNombre());
                    nomArtistasSugeridos[1] = artista.getNombre();
                } else if (cont == numeros.obtener(2)) {
                    cArtista3.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido3.setText(artista.getNombre());
                    nomArtistasSugeridos[2] = artista.getNombre();
                }
                cont++;
            }
        } else if (numeros.getTamanio() >= 4) {
            for (Artista artista : sporify.getListaArtistas()) {
                if (cont == numeros.obtener(0)) {
                    cArtista1.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido1.setText(artista.getNombre());
                    nomArtistasSugeridos[0] = artista.getNombre();
                } else if (cont == numeros.obtener(1)) {
                    cArtista2.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido2.setText(artista.getNombre());
                    nomArtistasSugeridos[1] = artista.getNombre();
                } else if (cont == numeros.obtener(2)) {
                    cArtista3.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido3.setText(artista.getNombre());
                    nomArtistasSugeridos[2] = artista.getNombre();
                } else if (cont == numeros.obtener(3)) {
                    cArtista4.setFill(new ImagePattern(imgArtDefault));
                    lbArtSugerido4.setText(artista.getNombre());
                    nomArtistasSugeridos[3] = artista.getNombre();
                }
                cont++;
            }
        }
    }

    /**
     * Metodo que se encarga de cargar las canciones de la playlist del usuario actual
     **/
    public void cargarCancionesPlaylist() {
        System.out.println("Loading songs into playlist...");
        //sporify.getUsuarioActual().actualizarFavoritos();
        ListaCircular<Cancion> cancionList = sporify.getUsuarioActual().getListaCanciones();
        for (Cancion cancion : cancionList) {
            Boolean bandera = false;
            for (Cancion canAux : listCanciones) {
                if (canAux.getCodigo().equals(cancion.getCodigo())) {
                    bandera = true;
                }
            }
            if (bandera == false) {
                this.listCanciones.add(cancion);
            }
        }
        System.out.println("Loaded " + listCanciones.size() + " songs into playlist.");
        sporify.getUsuarioActual().actualizarFavoritos();
        this.tvListaCanciones1.setItems(listCanciones);
    }

    /**
     * Metodo que se encarga de cargar las canciones de la tienda
     **/
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

    /**
     * Metodo que se encarga de cargar las canciones de la busqueda de canciones de un artista
     **/
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

    /**
     * Metodo que se encarga de cargar las canciones de la busqueda O o Y de canciones
     **/
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

    // ---------------- Metodos de bloques de codigo repetidos ----------------//

    /**
     * Metodo que se encarga de inicializar las tablas  de las canciones segun se requiera
     * @param col1 columna de codigo
     * @param col2  columna de nombre
     * @param col3 columna de artista
     * @param col4 columna de album
     * @param col5 columna de anio
     * @param col6 columna de duracion
     **/
    public void initTabla(TableColumn col1, TableColumn col2, TableColumn col3, TableColumn col4, TableColumn col5, TableColumn col6) {
        col1.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col3.setCellValueFactory(new PropertyValueFactory<>("artista"));
        col4.setCellValueFactory(new PropertyValueFactory<>("album"));
        col5.setCellValueFactory(new PropertyValueFactory<>("anio"));
        col6.setCellValueFactory(new PropertyValueFactory<>("duracion"));
    }

    /**
     * Metodo que se encarga de cambiar la visibilidad de los nodos
     * @param cierra1 nodo 1 a cerrar
     * @param cierra2 nodo 2 a cerrar
     * @param abre nodo a abrir
     **/
    public void cambiarVNodo(Node cierra1, Node cierra2, Node abre){
        cierra1.setVisible(false);
        cierra2.setVisible(false);
        abre.setVisible(true);
    }

    /**
     * Metodo que se encarga de cambiar la visibilidad de los nodos
     * @param cierra nodo a cerrar
     * @param abre nodo a abrir
     **/
    public void cambiarVNodo(Node cierra, Node abre){
        abre.setVisible(true);
        cierra.setVisible(false);
    }

    /**
     * Metodo que se encarga de cambiar el estilo de los nodos
     * @param nodo1 nodo 1
     * @param nodo2 nodo 2
     * @param estilo estilo a aplicar
     **/
    public void cambiarEstiloNodos(Node nodo1, Node nodo2, String estilo){
        nodo1.setStyle(estilo);
        nodo2.setStyle(estilo);
    }

    /**
     * Metodo para cambiar de ventana
     * @param fxmlPath ruta del fxml
     * @param title titulo de la ventana
     * @param width ancho de la ventana
     * @param height alto de la ventana
     * @throws IOException excepcion de entrada y salida si no se encuentra el fxml
     **/
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

    /**
     * Metodo que se encarga de organizar la url de youtube para poder reproducir el video
     * @param urlYoutube url del video
     * @return url organizada
     **/
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

    /**
     * Metodo que se encarga de mostrar una alerta
     * @param tipo tipo de alerta
     * @param titulo titulo de la alerta
     * @param mensaje mensaje de la alerta
     **/
    public void alerta(Alert.AlertType tipo, String titulo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Metodo que se encarga de mostrar la informacion de la cancion seleccionada
     **/
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
            if (getClass().getResourceAsStream(canSeleccionada.getCaratula()) == null) canSeleccionada.setCaratula("/com/uq/sporify/caratulas/songNotFoundDefault.png");
            ivCaratula.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(canSeleccionada.getCaratula()))));
            cambiarVNodo(paneInfoBusqueda, paneInfoCancion);
        }
    }
}
