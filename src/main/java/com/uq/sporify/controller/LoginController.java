package com.uq.sporify.controller;

import com.uq.sporify.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;
import java.util.Objects;

//Nota: Al final del proyecto se eliminan los identificadores, variables y metodos que no se
// usen o que se puedan optimizar

public class LoginController {

    //Variables


    //Identificadores de la vista asociada a login.fxml
    @FXML
    private Button btnIngresar, btnRegistrarse, btnSalir;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private TextField tfNomUsuario;


    //Metodos de acciones
    //Nota: todos los metodos tienen el codigo base, pero a la mayoria toca agregarle condiciones para realizar una
    //u otra accion
    @FXML
    void onActionBtnIngresar(ActionEvent event) throws IOException {
        cambiarEscena("vista/user.fxml", "Sporify", 1025, 656);

        //Aqui hace falta la validacion de usuario y contraseña, Validar si es admin o user y si no existe dar alerta
        //Si se desea abrir admin en lugar de user de debe usar "cambiarEscena("vista/admin.fxml", "Sporify - Admin", 1025, 656);"

    }

    @FXML
    void onActionBtnRegistrarse(ActionEvent event) throws IOException {
        //Esta accion no necesita nada más
        Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("vista/signUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 679, 456);
        stage.setTitle("Sporify - Registrate");
        stage.setScene(scene);
    }



    @FXML
    void onActionPFContrasenia(ActionEvent event) {
        //Probablemente no se necesite
    }

    @FXML
    void onActionTFUsuario(ActionEvent event) {
        //Probablemente no se necesite
    }

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        System.exit(0);
    }




    //Metodos propios

    //Metodos para optimizar bloques de codigo repetitivos
    public void cambiarEscena(String fxmlPath, String title, int width, int height) throws IOException {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/logoBlack.png")));
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.getIcons().add(icon);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Stage stage2 = (Stage) btnRegistrarse.getScene().getWindow();
        stage2.close();
    }


}
