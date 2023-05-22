package com.uq.sporify;

import com.uq.sporify.test.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/uq/sporify/images/logoBlack.png")));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("vista/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 679, 456);
        stage.getIcons().add(icon);
        stage.setTitle("Sporify - Iniciar Sesión");
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);  //Para que la ventana no tenga la barra superior
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        Main.main(args);
    }
}