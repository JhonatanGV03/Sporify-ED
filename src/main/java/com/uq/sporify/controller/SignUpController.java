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

//Nota: Al final del proyecto se eliminan los identificadores, variables y metodos que no se
// usen o que se puedan optimizar

public class SignUpController {

    //Variables
    TiendaMusica sporify = TiendaMusica.getInstance();
    //Identificadores de la vista asociada a signUp.fxml
    @FXML
    private Button btnIniciarSesion, btnRegistrar, btnSalir;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private TextField tfCorreo, tfNomUsuario;


    public void initialize() {
        //Metodo que se ejecuta al iniciar la ventana
        try {
            sporify.cargarInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodos de acciones
    //Nota: todos los metodos tienen el codigo base, pero a la mayoria toca agregarle condiciones para realizar una
    //u otra accion
    @FXML
    void onActionBtnIniciarSesion(ActionEvent event) throws IOException {
        cambiarEscena("vista/login.fxml", "Sporify - Iniciar Sesion", 679, 456);
    }


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

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        System.exit(0);
    }

    //Metodos de entrada ((Cambiar el ingreso de texto cada que se apreta enter)
    @FXML
    void onActionPFContrasenia(ActionEvent event) {
        try {
            onActionBtnRegistrar(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionTFCorreo(ActionEvent event) {
        tfNomUsuario.requestFocus();
    }
    @FXML
    void onActionTFUser(ActionEvent event) {
        pfContrasenia.requestFocus();
    }

    @FXML
    void onInputTFUsuario(KeyEvent event) {
        //Este metodo puede ser usado para ir verificando si el nombre de usuario ya existe cada vez que el usuario
        //escribe una letra
        //Hay que agregarle la condicion de que el usuario no exista y que no este vacio (Enviar Alerta)
        if (sporify.verificarUsuario(tfNomUsuario.getText())) {
            //Alerta de que el usuario ya existe
            tfNomUsuario.setStyle("-fx-background-color: #EAEAEA; -fx-border-color: red; -fx-border-width: 0 0 2 0; -fx-background-radius: 5; -fx-border-radius: 5");
        }else {
            tfNomUsuario.setStyle("-fx-background-color: #EAEAEA; -fx-border-color:  #2b2b2b; -fx-border-width: 0 0 2 0; -fx-background-radius: 5; -fx-border-radius: 5");
        }
    }


    //Metodos de propios

    //Metodos para optimizar bloques de codigo repetitivos
    public void cambiarEscena(String fxmlPath, String title, int width, int height) throws IOException {
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle(title);
        stage.setScene(scene);
    }

    public void alerta(Alert.AlertType tipo, String titulo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
