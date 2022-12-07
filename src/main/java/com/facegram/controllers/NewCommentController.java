package com.facegram.controllers;

import com.facegram.model.DAO.CommentDAO;
import com.facegram.model.DAO.PostDAO;
import com.facegram.utils.badWords.BadWords;
import com.facegram.utils.message.ConfirmMessage;
import com.facegram.utils.message.ErrorMessage;
import com.facegram.utils.message.InfoMessage;
import com.facegram.utils.message.Message;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.sql.Connection;

public class NewCommentController extends Controller {

    /**
     * Atributos bindeados con javafx
     */
    @FXML private TextArea textComment;

    /**
     * Crea un comentario nuevo
     */
    @FXML
    private void addComment() {
        String text = this.textComment.getText();
        CommentDAO cDao = new CommentDAO();
        Message ms = new ConfirmMessage("¿Desea publicar el comentario?");
        ms.showMessage();
        if(((ConfirmMessage) ms).getBt() == ButtonType.OK) {
            if (!text.equals("")) {
                Connection conn = BadWords.getConnect();
                if (!BadWords.isFound(text)) {
                    cDao.setText(text);
                    cDao.setUser(permanentUser);
                    cDao.setPost(new PostDAO().get(permanentIdPost));
                    if (cDao.insert()) {
                        new InfoMessage("Comentario publicado.").showMessage();
                    } else {
                        new ErrorMessage("No se pudo publicar el comentario.").showMessage();
                    }
                    textComment.setText("");
                } else {
                    new ErrorMessage("No está permitido el mal uso del lenguaje.").showMessage();
                    this.textComment.setText("");
                }
            }
            else {
                new InfoMessage("El comentario no puede estar vacío.").showMessage();
            }
       }
    }
}

