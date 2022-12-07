module com.facegram.facegram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.persistence;


    opens com.facegram.connection to java.xml.bind;
    exports com.facegram.connection;
    opens com.facegram.controllers to javafx.fxml;
    exports com.facegram.controllers;

}