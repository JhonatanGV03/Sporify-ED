package com.uq.sporify.controller;

import com.uq.sporify.App;
import com.uq.sporify.model.TiendaMusica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    //---------------------------------Atributos---------------------------------//
    //Atributos de la clase
    String user, password;
    TiendaMusica sporify;

    //--------------Identificadores de la vista asociada a login.fxml--------------//
    //Identificadores de los elementos de la vista
    @FXML
    private Button btnRegistrarse;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private TextField tfNomUsuario;
    @FXML
    private Label lbMensaje;

    //--------------------------------Metodo inicializador--------------------------------//
    //Metodo que se ejecuta al iniciar la ventana
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sporify = TiendaMusica.getInstance(); //Se instancia la tienda de musica
        try {
            sporify.cargarInfo(); //Se carga la informacion de la tienda de musica desde la persistencia
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------Metodos de accion de los botones y otros elementos de la vista--------------//

    /**
     * Metodo que se ejecuta al presionar el boton "Ingresar"
     * permite tomar los datos ingresados por el usuario y validarlos y define si se abre la ventana de admin o user
     * o si por el contrario se muestra un mensaje de error
     **/
    @FXML
    void onActionBtnIngresar(ActionEvent event) throws IOException {
        //Toma los datos ingresados por el usuario (Usuario y contraseña)
        user = tfNomUsuario.getText();
        password = pfContrasenia.getText();
        System.out.println(user  + "  " + password); //Verificacion en consola (pruebas)
        //Se valida que los campos no esten vacios y que el usuario y contraseña sean correctos
        if (user.isEmpty()||password.isEmpty()){
            lbMensaje.setVisible(true); //Se muestra el mensaje de error si alguno de los campos estan vacios
        }else if (user.equals("admin") && password.equals("$aDmiN")){
            sporify.cargarInfo();
            cambiarEscena("vista/admin.fxml", "Sporify - Admin", 1025, 656);  //Cambia a la ventana de admin
        }else if (TiendaMusica.getInstance().iniciarSesion(user, password) != null) {
            sporify.setUsuarioActual(sporify.getInstance().iniciarSesion(user,password));
            sporify.cargarInfo();
            cambiarEscena("vista/user.fxml", "Sporify", 1025, 656);  //Abre la ventana de usuario
        }else {
            lbMensaje.setVisible(true);
            lbMensaje.setText("Usuario o contraseña incorrectos");
        }
    }

    /**
    * Metodo que se ejecuta al presionar el boton "Registrarse"
    * Abre la ventana signUp.fxml para que el usuario pueda registrarse
     **/
    @FXML
    void onActionBtnRegistrarse(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("vista/signUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 679, 456);
        stage.setTitle("Sporify - Registrate");
        stage.setScene(scene);
    }

    //Metodo que se ejecuta al presionar enter en el campo de texto "Contraseña" llama al boton ingresar
    @FXML
    void onActionPFContrasenia(ActionEvent event) {
        try {
            onActionBtnIngresar(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo que se ejecuta al presionar enter en el campo de texto "Usuario" llama al campo de texto "Contraseña
    @FXML
    void onActionTFUsuario(ActionEvent event) {
        pfContrasenia.requestFocus();
    }

    /**
    *Metodo que se ejecuta al presionar el boton "Salir" cierra por completo la aplicacion
     **/
    @FXML
    void onActionBtnSalir(ActionEvent event) {
        System.exit(0);
    }

    //----------------------------------------------Metodos de la Clase----------------------------------------------//

    //----Metodos para optimizar bloques de codigo repetitivos-----------------//

    /**
     * Metodo que permite cambiar de escena
     * @param fxmlPath ruta del archivo fxml en el proyecto
     * @param title titulo de la ventana
     * @param width ancho de la ventana
     * @param height alto de la ventana
     * @throws IOException firma de excepcion en caso de que no se encuentre el archivo fxml
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

        Stage stage2 = (Stage) btnRegistrarse.getScene().getWindow();
        stage2.close();
    }
}
