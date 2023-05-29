package com.uq.sporify.controller;

import com.uq.sporify.App;
import com.uq.sporify.model.TiendaMusica;
import com.uq.sporify.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    //Atributos de la clase. Permite acceder a la tienda de musica
    TiendaMusica sporify = TiendaMusica.getInstance();
    //--------------Identificadores de la vista asociada a signUp.fxml--------------//
    //Identificadores de los elementos de la vista
    @FXML
    private Button btnIniciarSesion, btnRegistrar, btnSalir;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private TextField tfCorreo, tfNomUsuario;

    //--------------------------------Metodo inicializador--------------------------------//
    //Metodo que se ejecuta al iniciar la ventana
    public void initialize() {
        try {
            sporify.cargarInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------Metodos de accion de los botones y otros elementos de la vista--------------//

    /**
     * Metodo que se ejecuta al presionar el boton "Iniciar Sesion
     * Cambia la escenea a la de login.fxml para que el usuario pueda iniciar sesion
     **/
    @FXML
    void onActionBtnIniciarSesion(ActionEvent event) throws IOException {
        cambiarEscena("vista/login.fxml", "Sporify - Iniciar Sesion", 679, 456);
    }

    /**
     * Metodo que se ejecuta al presionar el boton "Registrar"
     * Registra al usuario en la tienda de musica verificando si los campos no estan vacios, que el correco sea valido
     * y que el usuario no exista
     **/
    @FXML
    void onActionBtnRegistrar(ActionEvent event) throws IOException {
        if (tfNomUsuario.getText().isEmpty() || tfCorreo.getText().isEmpty() || pfContrasenia.getText().isEmpty()) {
            //Alerta de que no se puede registrar porque hay campos vacios
            alerta(Alert.AlertType.WARNING, "Sporify - Campos vacios", "No se puede registrar porque hay campos vacios");
        } else if (!tfCorreo.getText().contains("@") || !tfCorreo.getText().contains(".")) {
            //Alerta de que el correo no es valido
            alerta(Alert.AlertType.WARNING, "Sporify - Correo invalido", "El correo ingresado no es valido");
        } else if (sporify.verificarUsuario(tfNomUsuario.getText())) {
            //Alerta de que el usuario ya existe
            alerta(Alert.AlertType.WARNING, tfNomUsuario.getText(),"ya existe este usuario, porfavor intente uno diferente");
        } else {
            //registro fue exitoso
            sporify.crearUsuario(new Usuario(tfNomUsuario.getText(), pfContrasenia.getText(), tfCorreo.getText()));
            sporify.guardarInfo();
            cambiarEscena("vista/login.fxml", "Sporify - Iniciar Sesion", 679, 456);
        }
    }

    // Metodo que se ejecuta al presionar el boton "Salir"Cierra la aplicacion
    @FXML
    void onActionBtnSalir(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Metodo que se ejecuta al presionar la tecla "Enter" en el campo de texto "Contraseña"
     * Acciona el boton "Registrar"
     **/
    @FXML
    void onActionPFContrasenia(ActionEvent event) {
        try {
            onActionBtnRegistrar(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que se ejecuta al presionar la tecla "Enter" en el campo de texto "Correo"
     * Cambia el foco al campo de texto "Nombre de usuario"
     **/
    @FXML
    void onActionTFCorreo(ActionEvent event) {
        tfNomUsuario.requestFocus();
    }

    /**
     * Metodo que se ejecuta al presionar la tecla "Enter" en el campo de texto "Nombre de usuario"
     * Cambia el foco al campo de texto "Contraseña"
     **/
    @FXML
    void onActionTFUser(ActionEvent event) {
        pfContrasenia.requestFocus();
    }

    /**
     * Metodo que se ejecuta al presionar una tecla en el campo de texto "Usuario"
     * Verifica en cada llamado que el nombre de usuario no exista
     **/
    @FXML
    void onInputTFUsuario(KeyEvent event) {
        if (sporify.verificarUsuario(tfNomUsuario.getText())) {
            //Alerta de que el usuario ya existe cambiando de color el borde del campo de texto
            tfNomUsuario.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: red; -fx-border-width: 0 0 2 0; -fx-background-radius: 5; -fx-border-radius: 5");
        }else {
            tfNomUsuario.setStyle("-fx-background-color: #EAEAEA; -fx-border-color:  #2b2b2b; -fx-border-width: 0 0 2 0; -fx-background-radius: 5; -fx-border-radius: 5");
        }
    }

    //----------------------------------------------Metodos de la Clase----------------------------------------------//

    //----Metodos para optimizar bloques de codigo repetitivos-----------------//

    /**
     * Metodo que cambia la escena
     * @param fxmlPath ruta del archivo fxml
     * @param title titulo de la ventana
     * @param width ancho de la ventana
     * @param height alto de la ventana
     * @throws IOException firma de excepcion en caso de que no se encuentre el archivo fxml
     **/
    public void cambiarEscena(String fxmlPath, String title, int width, int height) throws IOException {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
    }

    /**
     * Metodo que muestra una alerta en pantalla al usuario
     * @param tipo tipo de alerta que se desea mostrar
     * @param titulo titulo de la alerta
     * @param mensaje mensaje de la alerta
     **/
    public void alerta(Alert.AlertType tipo, String titulo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
