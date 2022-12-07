package com.facegram.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ItemCommentController {

    /**
     * Atributos bindeados con javafx
     */
    @FXML private Label lblUser;
    @FXML private TextArea txtComment;
    @FXML private Label lblDate;

    /**
     * Se setean los elementos del archivo fxml
     * @param userName Setea el nombre del usuario
     * @param comment Contenido del post
     * @param date Fecha de publicación del post
     */
    public void setComment(String userName, String comment, String date){
        this.lblUser.setText(userName);
        this.txtComment.setText(comment);
        this.lblDate.setText(date);
    }

}
