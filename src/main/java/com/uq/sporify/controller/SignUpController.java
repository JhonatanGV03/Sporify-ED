package com.uq.sporify.controller;

import com.uq.sporify.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    //Identificadores de la vista asociada a signUp.fxml
    @FXML
    private Button btnIniciarSesion, btnRegistrar, btnSalir;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private TextField tfCorreo, tfNomUsuario;


    //Metodos de acciones
    //Nota: todos los metodos tienen el codigo base, pero a la mayoria toca agregarle condiciones para realizar una
    //u otra accion
    @FXML
    void onActionBtnIniciarSesion(ActionEvent event) throws IOException {
        cambiarEscena("vista/login.fxml", "Sporify - Iniciar Sesion", 679, 456);
    }

    @FXML
    void onActionBtnRegistrar(ActionEvent event) throws IOException {
        //Este metodo debe validar que el usuario no exista, que haya escrito una contraseña y un correo valido que
        //contenga un "@" y un ".", tambien se debe alertar  si el registro  fue exitoso y con la siguiente linea
        // dentro de la condicion cambiar el fxml a la vista de login
        cambiarEscena("vista/login.fxml", "Sporify - Iniciar Sesion", 679, 456);
    }

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionPFContrasenia(ActionEvent event) {
        //Probablemente no se necesite
    }

    @FXML
    void onActionTFCorreo(ActionEvent event) {
        //Este metodo probablemente no se necesite
    }

    @FXML
    void onInputTFUsuario(KeyEvent event) {
        //Este metodo puede ser usado para ir verificando si el nombre de usuario ya existe cada vez que el usuario
        //escribe una letra

         //Hay que agregarle la condicion de que el usuario no exista y que no este vacio (Enviar Alerta)
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

}
