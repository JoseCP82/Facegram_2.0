package com.facegram.controllers;

import com.facegram.model.DAO.PostDAO;
import com.facegram.model.dataobject.Post;
import com.facegram.model.dataobject.User;
import com.facegram.utils.badWords.BadWords;
import com.facegram.utils.message.ConfirmMessage;
import com.facegram.utils.message.ErrorMessage;
import com.facegram.utils.message.InfoMessage;
import com.facegram.utils.message.Message;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewPostController extends Controller {

    /**
     * Atributos bindeados con javafx
     */
    @FXML
    private TextArea txtContent;

    /**
     * Crea un nuevo post y lo almacena en la base de datos
     */
    @FXML
    private void publishPost() {
        Message ms = new ConfirmMessage("¿Desea confirmar la publicación?");
        ms.showMessage();
        if(((ConfirmMessage) ms).getBt() == ButtonType.OK) {
            if(!this.txtContent.equals("")) {
                Connection conn = BadWords.getConnect();
                if(!BadWords.isFound(txtContent.getText())){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar calendar = Calendar.getInstance();
                    Date dateNow = calendar.getTime();
                    User u = permanentUser;
                    PostDAO pdao = new PostDAO(new Post(this.txtContent.getText(), dateNow, dateNow, permanentUser));
                    if(pdao.insert()){
                        new InfoMessage("Has realizado una nueva publicación!!!").showMessage();
                        txtContent.setText("");
                    }
                    else {
                        new ErrorMessage("Ocurrió un error inesperado.").showMessage();
                    }
                } else {
                    new ErrorMessage("No está permitido el mal uso del lenguaje.").showMessage();
                    this.txtContent.setText("");
                }
            }
            else {
                new InfoMessage("No ha añadido ningún contenido a la publicación!!!").showMessage();
            }
        }
    }
}
