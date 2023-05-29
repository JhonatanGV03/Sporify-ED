package com.uq.sporify.test;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main2 {
    public static void main(String[] args) {
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
    }
}
