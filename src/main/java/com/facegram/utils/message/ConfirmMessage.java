package com.facegram.utils.message;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class ConfirmMessage extends Message {

    /**
     * Atributo de clase
     */
    private ButtonType bt;

    /**
     * Constructor con parametros
     * @param message Mensaje a mostrar
     */
    public ConfirmMessage(String message) {
        super(message);
    }

    /**
     * Obtiene el contenido del botón
     * @return Valor dependiendo de si el usuario pulsó el boton cancela o aceptar
     */
    public ButtonType getBt() {
        return bt;
    }

    /**
     * Muestra una pantalla de alerta de tipo Confirmation
     */
    @Override
    public void showMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(this.message);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.setHeaderText(null);
        bt = alert.showAndWait().get();
    }
}
