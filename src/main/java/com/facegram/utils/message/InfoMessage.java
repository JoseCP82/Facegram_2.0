package com.facegram.utils.message;

import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class InfoMessage extends Message {
    /**
     * Constructor con parametros
     * @param message Mensaje a mostrar
     */
    public InfoMessage(String message) {
        super(message);
    }

    /**
     * Muestra una pantalla de alerta de tipo Error
     */
    @Override
    public void showMessage() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.initStyle(StageStyle.TRANSPARENT);
        a.setHeaderText(null);
        a.setContentText(this.message);
        a.showAndWait();
    }
}
