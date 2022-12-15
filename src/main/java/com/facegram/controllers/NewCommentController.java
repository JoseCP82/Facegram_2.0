package com.facegram.controllers;

import com.facegram.model.DAO.CommentDAO;
import com.facegram.model.DAO.PostDAO;
import com.facegram.model.dataobject.Comment;
import com.facegram.utils.badWords.BadWords;
import com.facegram.utils.message.ConfirmMessage;
import com.facegram.utils.message.ErrorMessage;
import com.facegram.utils.message.InfoMessage;
import com.facegram.utils.message.Message;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

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
        Comment comment = new Comment();
        Message ms = new ConfirmMessage("¿Desea publicar el comentario?");
        ms.showMessage();
        if(((ConfirmMessage) ms).getBt() == ButtonType.OK) {
            if (!text.equals("")) {
                Connection conn = BadWords.getConnect();
                if (!BadWords.isFound(text)) {
                    Calendar calendar = Calendar.getInstance();
                    Date dateNow = calendar.getTime();
                    comment.setDate(dateNow);
                    comment.setText(text);
                    comment.setUser(permanentUser);
                    comment.setPost(new PostDAO().get(permanentIdPost));
                    if (cDao.insert(comment)) {
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

