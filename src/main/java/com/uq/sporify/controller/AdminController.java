package com.uq.sporify.controller;

import com.uq.sporify.App;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class AdminController implements Initializable {

    //------------------------------------Atributos y Variables----------------------------------------------------//
    /*
     * Atributos de la clase
     */
    TiendaMusica sporify;  //Instancia de la tienda de musica
    Boolean tipo;
    private Artista artSeleccionado;  //Artista seleccionado en la tabla de artistas
    private Cancion canSeleccionada;  //Cancion seleccionada en la tabla de canciones
    private String rutaTemp = "/com/uq/sporify/caratulas/songNotFoundDefault.png";   //Ruta de la caratula (Cambiar por la ruta de la caratula por defecto)
    private boolean esNuevoArtCan; //Booleano que indica si se esta creando un nuevo artista o cancion
    private ObservableList<Artista> listArtistas = FXCollections.observableArrayList();  //Lista de seguimiento de artistas que se cargan en la tabla
    private ObservableList<Cancion> listCanciones = FXCollections.observableArrayList();    //Lista de seguimiento de canciones que se cargan en la tabla


    //-----------------------------Identificadores de la vista asociada a admin.fxml------------------------------------------//
    /*
     * Identificadores de los elementos de la vista
     */
    @FXML
    private Button btnArtistaPopular, btnVideo, btnGeneroMayor, btnAtras;

    @FXML
    private CheckBox cbArtistaeAE, cbGrupoAE;

    @FXML
    private Circle cirArtistaS;

    @FXML
    private ImageView ivCaratula2, ivCaratulaI, ivCaratulaR, ivCaratula21;

    @FXML
    private Label lbAlbumI, lbAnioCancionI, lbCodArtEA, lbCodCancionEA, lbCodiCancionI,
            lbCodigoArtistaS, lbDuracionI, lbGeneroI, lbNacionalidadArtistaS, lbNomArtistaI,
            lbNomArtistaR, lbNomCancionI, lbNomCancionR, lbNombreCVideo, lbTArtistaS, lbAgEdArt, lbCodCancionEA1;

    @FXML
    private Pane paneEditArtista, paneEditCancion, paneInfoCancion, panelVideo, paneEditCancion1;

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
    private WebEngine webEngine;   //Motor de pagina web para mostrar el video

    //--------------------------------Metodo inicializador--------------------------------//
    /**
     * Metodo que se ejecuta al iniciar la ventana
     **/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sporify = TiendaMusica.getInstance();  //Obtiene la instancia de la tienda de musica

        webEngine = wbVideo.getEngine();  //obtiene el motor de pagina web

        //Organiza las columnas de la tabla de artistas de acuerdo al contenido que le pertenece
        this.codListArt.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.nomListArt.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.nacioListArt.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        this.tipoListArt.setCellValueFactory(new PropertyValueFactory<>("grupo"));

         cargarArtistas(); //Carga los artistas en la tabla

        //Organiza las columnas de la tabla de canciones de acuerdo al contenido que le pertenece
        this.codListCan.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        this.nomListCan.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.artListCan.setCellValueFactory(new PropertyValueFactory<>("artista"));
        this.albumListCan.setCellValueFactory(new PropertyValueFactory<>("album"));
        this.anioListCan.setCellValueFactory(new PropertyValueFactory<>("anio"));
        this.duraListCan.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        btnArtistaPopular.setText(sporify.mostrarArtistaTendencia());  //Muestra el artista mas popular
        btnGeneroMayor.setText(sporify.mostrarGeneroTendendencia());    //Muestra el genero con mas canciones

        //Inicia los valores de los choice box
        cbGeneroAE.getItems().addAll("Rock", "Punk", "Pop", "Reggaeton", "Electrinica", "Rap");
        cbGeneroAE1.getItems().addAll("Rock", "Punk", "Pop", "Reggaeton", "Electrinica", "Rap");

        //Carga la imagen de los artistas por defecto
        cirArtistaS.setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/Artista.png")))));
    }

    //------------------------Metodos de accion de los botones y otros elementos de la vista-----------------------------//

    //-------Metodos Relacionados con el panel de visualizacion de artistas (Incluyendo CRUD)---------//

    /*
     * Metodo que se ejecuta al presionar un elemento de la tabla de artistas
     * obtiene el artista seleccionado y lo almacena en la variable artSeleccionado
     */
    @FXML
    void seleccionarTvListArtistas(MouseEvent event) {
        artSeleccionado = this.tvListaArtistas.getSelectionModel().getSelectedItem();
    }

    /**
     * Metodo que se ejecuta al presionar el boton de ver canciones de un artista
     * Cambia el panel de visualizacion de artistas por el de visualizacion de canciones
     * Se cargan las canciones pertenecientes al artista seleccionado y la informacion del artista
     **/
    @FXML
    void onActionBtnVerCan(ActionEvent event) {
        if (artSeleccionado != null) {
            cambiarVNodo(vbArtistas, vbCanArtista);  //Cambia el panel de visualizacion de artistas por el de visualizacion de canciones
            btnAtras.setVisible(true);
            paneEditArtista.setVisible(false);
            lbCodigoArtistaS.setText(artSeleccionado.getCodigo());
            tNomArtistaS.setText(artSeleccionado.getNombre());
            lbNacionalidadArtistaS.setText(artSeleccionado.getNacionalidad());
            if(artSeleccionado.getGrupo()) lbTArtistaS.setText("Grupo");
            else lbTArtistaS.setText("Solista");
            cargarCanciones();     //Cargo las canciones del artista seleccionado
            System.out.println(artSeleccionado.getNombre() + " " + artSeleccionado.getNacionalidad());
            tvListaCanciones.refresh();
        }else {
            alerta(Alert.AlertType.ERROR, "---Error - No Seleccionado" , "Asegurese de seleccionar un artista para poder ver las canciones del artista");
        }
    }

    /**
     * Metodo que se ejecuta al presionar el boton eliminar del panel de los artistas
     * Elimina el artista seleccionado de la lista de artistas y actualiza la tabla de artistas
     * Tambien elimina las canciones del artista de la lista de canciones de la tienda
     **/
    @FXML
    void onActionBtnEliminar(ActionEvent event) {
        if (artSeleccionado != null) {
            sporify.setListaCanciones(sporify.depurarArtistayCanciones(artSeleccionado));  //Elimina las canciones del artista de la lista de canciones de la tienda
            sporify.setListaUsuarios(sporify.depurarPlayList());    //Elimina las canciones del artista de las playlist de los usuarios
            listArtistas.remove(artSeleccionado);  //Elimina el artista de la lista de seguimiento de artistas

            listArtistas.clear();
            cargarArtistas();   //Carga los artistas en la tabla
            tvListaArtistas.refresh();  //Actualiza la tabla de artistas

            artSeleccionado = null;
        }else {
            alerta(Alert.AlertType.ERROR, "---Error - No Seleccionado" , "Asegurese de seleccionar un artista antes de eliminarlo");
        }
    }

    /**
     * Metodo que ejecuta al presionar el boton cargar artistas
     * se abre un file chooser para seleccionar el archivo de artistas a cargar (txt)
     * y extrae de alli los artistas y los agrega a la lista de artistas
     **/
    @FXML
    void onActionBtnCargarArts(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();  //Abre un file chooser para seleccionar el archivo de artistas a cargar
        fileChooser.showOpenDialog(fileChooser);
        File archivo = new File(fileChooser.getSelectedFile().getPath());
        sporify.AgregarPorArchivo(archivo);    //Extrae los artistas del archivo y los agrega a la lista de artistas
        cargarArtistas();        //Carga los artistas en la tabla
        tvListaArtistas.refresh();
    }

    /**
     * Metodo que se ejecuta al presionar el boton de editar un artista
     * Se debe tener presionado el artista que se quiere editar sino se mostrara un mensaje de error
     **/
    @FXML
    void onActionBtnEditarArt(ActionEvent event) {
        if (artSeleccionado != null) {
            //Envia todos los valores del artista actual para que sean editados
            lbAgEdArt.setText("Editar Artista");
            lbCodArtEA.setText(artSeleccionado.getCodigo());
            tfNomArtAE.setText(artSeleccionado.getNombre());
            tfNacioArtAE.setText(artSeleccionado.getNacionalidad());
            if (!artSeleccionado.getGrupo()) cbArtistaeAE.setSelected(true);
            else cbGrupoAE.setSelected(true);
            cambiarVNodo(paneInfoCancion, paneEditCancion, paneEditArtista);    //Muestra el panel de edicion de artistas
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar un artista antes de editarlo");
        }

    }

    /**
     * Metodo que se ejecuta al presionar el boton de agregar un artista
     * Muestra el panel de agregar artista y limpia los campos de texto
     **/
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

    /**
     * Metodo que se ejecuta al presionar el boton salir
     * Cierra la ventana actual y abre la ventana de login
     **/
    @FXML
    void onActionBtnSalir(ActionEvent event) {
        try {
            sporify.guardarInfo();   //Carga la informacion de la tienda en los archivos
            cambiarEscena("vista/login.fxml", "Sporify", 679, 456);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //----------------------Metodos relacionados con el panel de edicion y adicion de artistas------------------------//
    /**
     * Metodo que se ejecuta al presionar el boton de guardar artista
     * Verifica que los campos de texto no esten vacios y agrega el artista a la lista de artistas
     * Verifica a traves de la bandera esNuevoArtCan si se esta editando o agregando un artista
     */
    @FXML
    void onActionBtnGuardarArt(ActionEvent event) {

        String nombre = tfNomArtAE.getText();
        String nacionalidad = tfNacioArtAE.getText();
        Boolean grupo ;
        if (cbGrupoAE.isSelected())grupo = true;    //Verifica si el artista es un grupo o no
        else grupo = false;

        if (verificarCamposArtista()){
            Artista x = new Artista(nombre, nacionalidad, grupo);
            if(!sporify.getListaArtistas().buscar(x)) {   //Si no existe se crea un nuevo artista
                sporify.agregarArtista(x);
                System.out.println("Guardado " + sporify.getListaArtistas().encontrar(x));
            }else if (!esNuevoArtCan){    //Si no es nuevo se edita el artista seleccionado
                sporify.eliminarArtista(artSeleccionado);
                listArtistas.remove(artSeleccionado);
                sporify.agregarArtista(x);
                System.out.println("Eliminado");

            }else {
                alerta(Alert.AlertType.ERROR, "---Error", "El artista ya existe");
            }
            cargarArtistas();   //Carga los artistas en la tabla
            tvListaArtistas.refresh();

            artSeleccionado = null;
            paneEditArtista.setVisible(false);   //Cierra el panel de edicion de artistas
            esNuevoArtCan = false;  //Se cambia la bandera a false para futuras ediciones o adiciones
        }
    }

    /**
     * Metodo que se ejecuta al presionar el boton de cancelar
     * Cierra el panel de edicion de artistas y limpia los campos de texto
     * (Sirve para cancelar la edicion o adicion de un artista y de una cancion)
     **/
    @FXML
    void onActionBtnCancelar(ActionEvent event) {
        paneEditCancion.setVisible(false);
        paneEditArtista.setVisible(false);
        paneEditCancion1.setVisible(false);
        canSeleccionada = null;
        rutaTemp = "";
        esNuevoArtCan = false;
    }

    //----------------------Metodos relacionados con el panel de visualizacion de canciones---------------------------//

    /**
     * Metodo que se ejecuta al presionar la tabla de canciones
     * Obtiene la cancion seleccionada y muestra su información
     **/
    @FXML
    void seleccionarTvListCanciones(MouseEvent event) {
        canSeleccionada = this.tvListaCanciones.getSelectionModel().getSelectedItem();
        if (canSeleccionada != null) {
            mostrarInfoCancion();
        }
        System.out.println(canSeleccionada);
    }

    /**
     * Metodo que se ejecuta al presionar el boton de editar una cancion
     * Debe tener seleccionada una cancion si no se mostrara un mensaje de error
     * Muestra el panel de edicion de canciones y carga la informacion de la cancion seleccionada a los campos de texto
     **/
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
            tfDuracionAE.setText(valueOf(canSeleccionada.getDuracion()));
            tfAnioAE.setText(canSeleccionada.getAnio());
            ivCaratula2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(canSeleccionada.getCaratula()))));
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de editarla");
        }
    }

    /**
     * Metodo que se ejecuta al presionar el boton de agregar una cancion nueva
     * Muestra el panel de adicionar cancion y setea los campos de texto en blanco y la caratulapor defecto
     **/
    @FXML
    void onActionBtnAddCanc(ActionEvent event) {
        esNuevoArtCan = true;
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
        ivCaratula21.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/caratulas/songNotFoundDefault.png"))));
    }

     /**
     * Metodo que se ejecuta al presionar el boton eliminar cancion
     * Elimina la cancion seleccionada de la lista de canciones, de la lista del artista y de la lista de reproduccion de los usuarios
     **/
    @FXML
    void onActionBtnEliminarCan(ActionEvent event) {
        if (canSeleccionada != null) {
            sporify.retornarArtista(artSeleccionado.getNombre()).eliminarPorValor(canSeleccionada.getCodigo());
            //System.out.println(sporify.retornarArtista(artSeleccionado.getNombre()).getListaCanciones());
            sporify.eliminarCancion(canSeleccionada);
            //System.out.println(sporify.getCancioncita(canSeleccionada.getCodigo()));
            sporify.setListaUsuarios(sporify.depurarPlayList());
            //System.out.println(canSeleccionada);
            listCanciones.remove(canSeleccionada);
            listCanciones.clear();
            cargarCanciones();     //Carga las canciones en la tabla actualizada
            tvListaArtistas.refresh();

        }else {
            alerta(Alert.AlertType.ERROR, "Sporify - No Seleccionado", "Asegurese de seleccionar una cancion antes de eliminarlo");
        }
        canSeleccionada = null;
    }

    /**
     * Metodo que se ejecuta al presionar el boton de atras en el panel de visualizacion de canciones
     * Cierra el panel de visualizacion de canciones y muestra el panel de visualizacion de artistas
     **/
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
        rutaTemp= "";
    }

    //------------------Metodos relacionados con el panel de edicion y adicion de canciones--------------------//

    /**
     * Metodo que se ejecuta al presionar el boton de guardar una cancion en el panel de edicion de canciones
     * Se verifica si los campos no estan vacios y actualiza la cancion seleccionada con la nueva informacion
     **/
    @FXML
    void onActionBtnGuardarCan(ActionEvent event) { //Metodo para revision
        String nombre = tfNomCancionAE.getText();
        String artista = tfArtCancionAE.getText();
        String album = tfAlbumAE.getText();
        String anio = tfAnioAE.getText();
        int duracion = Integer.parseInt(tfDuracionAE.getText());
        String genero = cbGeneroAE.getValue();
        String url = tfUrlAE.getText();
        String caratula = rutaTemp;
        Cancion x =canSeleccionada;
        //Editar
        if (verificarCamposCancion()){
            if(canSeleccionada !=null) {
                sporify.eliminarCancion(x);
                sporify.retornarArtista(artSeleccionado.getNombre()).eliminarPorValor(canSeleccionada.getCodigo());
                x.editar(nombre,artista,caratula,anio,url,duracion,genero,album);
                sporify.guardarCancion(x);
                sporify.retornarArtista(artSeleccionado.getNombre()).agregarCancion(x);
                listCanciones.clear();
                cargarCanciones();
                tvListaCanciones.refresh();
                esNuevoArtCan = false;
                canSeleccionada = null;
                paneEditCancion.setVisible(false);
                rutaTemp = "";
            }else{
                alerta(Alert.AlertType.ERROR, "---Error", "La cancion ya existe");
            }
        }else{
            alerta(Alert.AlertType.ERROR, "---Error", "No estan todos los campos completos");
        }
    }

    /**
     * Metodo que se ejecuta al presionar el boton de guardar una cancion en el panel de adicion de canciones
     * Si es una cancion nueva, se agrega a la lista de canciones de la tienda y del artista seleccionado
     **/
    @FXML
    void onActionBtnGuardarCan1(ActionEvent event) { //Metodo para revision
        String nombre = tfNomCancionAE1.getText();
        String artista = tfArtCancionAE1.getText();
        String album = tfAlbumAE1.getText();
        String anio = tfAnioAE1.getText();
        int duracion = Integer.parseInt(tfDuracionAE1.getText());
        String genero = cbGeneroAE1.getValue();
        String url = tfUrlAE1.getText();
        String caratula = rutaTemp;
        Cancion x = new Cancion(nombre,artista,caratula,anio,url,duracion,genero,album);
        //Agregar
        if (verificarCamposCancion1()) {
            if (!sporify.encontrarCancion(x)) {
                sporify.guardarCancion(x);
                sporify.retornarArtista(artSeleccionado.getNombre()).agregarCancion(x);
                listCanciones.clear();
                cargarCanciones();    //Carga las canciones en la tabla actualizada
                tvListaCanciones.refresh();
                esNuevoArtCan = false;   //Indica que ya no es una cancion nueva
                canSeleccionada = null;   //Indica que no hay ninguna cancion seleccionada
                paneEditCancion1.setVisible(false);    //Cierra el panel de adicion de canciones
            } else {
                alerta(Alert.AlertType.ERROR, "---Error", "La cancion ya existe");
            }
        }else {
            alerta(Alert.AlertType.ERROR, "---Error", "No estan todos los campos completos");
        }
    }

    /**
     * Metodo que se ejecuta al presionar la imagen de la caratula en el panel de edicion de canciones
     * Abre un explorador de archivos para seleccionar una imagen y la copia a la carpeta de caratulas
     * obtiene la ruta de la imagen y la asigna a la cancion seleccionada.
     * Si no se selecciona ninguna imagen, se asigna la imagen por defecto y se da aviso al usuario
     **/
    @FXML
    void onActionEditIMG(ActionEvent event) {
        File archivo = copiarArchivo();  //Copia el archivo seleccionado a la carpeta de caratulas y retorna el archivo
        if (canSeleccionada != null && archivo != null && archivo.exists()) {
            rutaTemp = "/com/uq/sporify/caratulas/" + archivo.getName(); //Guarda la ruta de la imagen en una variable temporal
            if (rutaTemp.contains(" ")){
                alerta(Alert.AlertType.ERROR, "Sporify - Error", "Archivo no valido - contiene espacios en el nombre");
                canSeleccionada.setCaratula("/com/uq/sporify/caratulas/songNotFoundDefault.png");
            }else {
                canSeleccionada.setCaratula("/com/uq/sporify/caratulas/" + archivo.getName()); //Asigna la ruta de la imagen a la cancion seleccionada
                System.out.println(canSeleccionada.getCaratula());
                ivCaratula2.setImage(new Image(getClass().getResourceAsStream(canSeleccionada.getCaratula())));
            }
        } else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado", "Asegurese de seleccionar un archivo de imagen valido");
            canSeleccionada.setCaratula("/com/uq/sporify/caratulas/songNotFoundDefault.png");
            rutaTemp = "/com/uq/sporify/caratulas/songNotFoundDefault.png";
        }
    }

    /**
     * Metodo que se ejecuta al presionar la imagen de la caratula en el panel de adicion de canciones
     * Abre un explorador de archivos para seleccionar una imagen y la copia a la carpeta de caratulas
     * obtiene la ruta de la imagen y la asigna a la cancion seleccionada.
     * Si no se selecciona ninguna imagen, se asigna la imagen por defecto y se da aviso al usuario
     **/
    @FXML
    void onActionCargarIMG(ActionEvent event) {
        File archivo = copiarArchivo();
        if (archivo != null){
            rutaTemp = "/com/uq/sporify/caratulas/" + archivo.getName();
            if (rutaTemp.contains(" ")){
                alerta(Alert.AlertType.ERROR, "Sporify - Error", "Archivo no valido - contiene espacios en el nombre");
                rutaTemp = "/com/uq/sporify/caratulas/songNotFoundDefault.png";
            }else{
                System.out.println(rutaTemp);
                ivCaratula21.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(rutaTemp))));
            }
        } else{
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar un archivo de imagenn valido");
        }
    }

    //-------------Metodos relacionados con la seccion inferior y la ventana de reproduccion--------------------//

    /**
     * Metodo que se ejecuta al presionar el boton reproducir en la ventana de la lista de canciones
     * Despliega la ventana de reproduccion y carga la cancion seleccionada en el reproductor
     * Si no hay ninguna cancion seleccionada, se da aviso al usuario
     **/
    @FXML
    void onActionBtnReproducir(ActionEvent event) {
        if (canSeleccionada != null) {
            //String url = organizarURL(canSeleccionada.getUrlYoutube());
            webEngine.load(canSeleccionada.getUrlYoutube());
            wbVideo.setOpacity(1);
            cambiarVNodo(paneEditArtista, paneEditCancion, paneInfoCancion);
            panelVideo.setVisible(true);
            btnVideo.setStyle("-fx-background-color: red; -fx-background-radius: 40 " );
            mostrarInfoCancion();
            lbNomCancionR.setText(canSeleccionada.getNombre());
            lbNomArtistaR.setText(canSeleccionada.getArtista());
            lbNombreCVideo.setText(canSeleccionada.getNombre());
            ivCaratulaR.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(canSeleccionada.getCaratula()))));
            sporify.getListaArtistas().encontrar(artSeleccionado).setReproducciones(sporify.getListaArtistas().encontrar(artSeleccionado).getReproducciones()+1);
            canSeleccionada = null;
        }else {
            alerta(Alert.AlertType.WARNING, "Sporify - No Seleccionado" , "Asegurese de seleccionar una cancion antes de reproducirla.");
        }
    }

    /**
     * Metodo que se ejecuta al presionar el boton de visualizar video en la ventana de reproduccion
     * permite cambiar de ventana y visualizar el video de la cancion que se esta reproduciendo en el momento
     **/
    @FXML
    void onActionBtnVideo(ActionEvent event) {
        if (!panelVideo.isVisible()){
            panelVideo.setVisible(true);
            cambiarVNodo(paneEditArtista, paneEditCancion, paneInfoCancion);
            paneEditCancion1.setVisible(false);
            btnVideo.setStyle("-fx-background-color: red; -fx-background-radius: 40 " );
        }else {
            panelVideo.setVisible(false);
            btnVideo.setStyle("-fx-background-color: #f3f3f3; -fx-background-radius: 40 " );
        }
    }

    //-----------Metodos para cargar los datos de los artistas y canciones en las tablas-------------//
    /**
     * Metodo para cargar los artistas en la tabla
     **/
    public void cargarArtistas(){
        /*
         * En este punto se cargan los datos de los artistas al table view
         */
        Iterator<Artista> iterador = sporify.getListaArtistas().iterator();
        while (iterador.hasNext()) {
            Artista ArtistaList = iterador.next();
            Artista nuevoArtista = ArtistaList;
            if(!this.listArtistas.contains(nuevoArtista)) {  //Verifica si ya se encuentra cargado el artista en la tabla
                this.listArtistas.add(nuevoArtista);
            }
        }
        this.tvListaArtistas.setItems(listArtistas);   //Carga los artistas en la tabla
    }

    /**
     * Metodo para cargar las canciones en la tabla, del artista seleccionado en el momento
     **/
    public void cargarCanciones(){
        //Obtiene el artista seleccionado en la tabla y su lista de canciones
        ListaDobleEnlazada<Cancion> cancionList = sporify.retornarArtista(artSeleccionado.getNombre()).getListaCanciones();
        for (Cancion cancion : cancionList) {   //Recorre la lista de canciones del artista
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
        this.tvListaCanciones.setItems(listCanciones);  //Carga las canciones en la tabla
    }


    /**
     * Metodo para organizar un url no embebido de youtube y convertirlo en un url embebido
     * @param urlYoutube url de youtube no embebido
     * @return url embebido
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
     * Metodo para mostrar un fileChooser y copiar el archivo seleccionado en la carpeta de caratulas
     * @return retorna el archivo seleccionado
     **/
    public File copiarArchivo(){
        JFileChooser file = new JFileChooser ();
        file.showOpenDialog(file);
        File archivo = file.getSelectedFile();
        String dest;
        if (archivo != null) {
            dest = System.getProperty("user.dir") + "/src/main/resources/com/uq/sporify/caratulas/" + archivo.getName();
            Path destino = Paths.get(dest);
            String orig = archivo.getPath();
            Path origen = Paths.get(orig);
            //Copiamos el nuevo archivo con la clase Files, reemplazamos si ya existe uno igual.
            try {
                Files.copy(origen, destino, REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("El archivo fué copiado con exito");
        }
        return archivo;
    }

    /**
     * Metodo para verificar si los campos de texto de la ventana de editar artista estan llenos
     * @return un boolean si los campos estan llenos o no
     **/
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

    /**
     * Metodo para verificar si los campos de texto de la ventana de agregar artista estan llenos
     * @return un boolean si los campos estan llenos o no
     **/
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

    /**
     * Metodo para verificar si los campos de texto de la ventana de agregar artista estan llenos
     * @return un boolean si los campos estan llenos o no
     **/
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

    //-----------------------------------------Metodos para bloques de codigo repetidos-------------------------------//

    /**
     * Metodo para cambiar la visibilidad de un nodo
     * @param cierra1 primer nodo a cerrar
     * @param cierra2 segundo nodo a cerrar
     * @param abre nodo a abrir
     **/
    public void cambiarVNodo(Node cierra1, Node cierra2, Node abre){
        cierra1.setVisible(false);
        cierra2.setVisible(false);
        abre.setVisible(true);
    }

    /**
     * Metodo para cambiar la visibilidad de un nodo
     * @param cierra nodo a cerrar
     * @param abre nodo a abrir
     **/
    public void cambiarVNodo(Node cierra, Node abre){
        abre.setVisible(true);
        cierra.setVisible(false);
    }

    /**
     * Metodo para cambiar la visibilidad de un nodo
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
     * Metodo para cambiar de ventana
     * @param fxmlPath ruta del fxml
     * @param title titulo de la ventana
     * @param width ancho de la ventana
     * @param height alto de la ventana
     * @throws IOException excepcion de entrada y salida para cuando no encuentre el fxml
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
     * Metodo para mostrar la informacion de la cancion seleccionada
     **/
    public void mostrarInfoCancion(){
        if (canSeleccionada!=null){
            lbCodiCancionI.setText(canSeleccionada.getCodigo());
            lbNomCancionI.setText(canSeleccionada.getNombre());
            lbNomArtistaI.setText(canSeleccionada.getArtista());
            lbAlbumI.setText(canSeleccionada.getAlbum());
            lbGeneroI.setText(canSeleccionada.getGenero());
            lbUrlI.setText(canSeleccionada.getUrlYoutube());
            lbDuracionI.setText(valueOf(canSeleccionada.getDuracion()));
            lbAnioCancionI.setText(canSeleccionada.getAnio());
            System.out.println(canSeleccionada.getCaratula());
            ivCaratulaI.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(canSeleccionada.getCaratula()))));
            cambiarVNodo(paneEditArtista, paneEditCancion, paneInfoCancion);
        }
    }
}
