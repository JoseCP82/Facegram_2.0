module com.facegram {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens com.facegram.model.dataobject;
    opens com.facegram.controllers to javafx.fxml;
    exports com.facegram.controllers;
}