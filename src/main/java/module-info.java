module com.uq.spotify {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.web;
    requires java.desktop;
    requires jaxb.api.osgi;

    opens com.uq.sporify to javafx.fxml;
    exports com.uq.sporify;
    exports com.uq.sporify.controller;
    opens com.uq.sporify.controller to javafx.fxml;
}