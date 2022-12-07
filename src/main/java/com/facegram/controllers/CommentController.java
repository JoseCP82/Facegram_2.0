package com.facegram.controllers;

import com.facegram.log.Log;
import com.facegram.model.DAO.CommentDAO;
import com.facegram.model.DAO.PostDAO;
import com.facegram.model.DAO.UserDAO;
import com.facegram.model.dataobject.Comment;
import com.facegram.model.dataobject.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CommentController extends Controller implements Initializable {

    /**
     * Atributos de la clase
     */
    private Post post;

    /**
     * Atributos bindeados con javafx
     */
    @FXML private Label lblNoExists;
    @FXML private ScrollPane scrollComments;
    @FXML private AnchorPane anchorShowComment;
    @FXML private Button btnAddComment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       showComments();
    }

    /**
     * Lee de la bbdd todos los comentarios de un post y los muestra
     */
    private void showComments(){
        List<Comment> comments = new CommentDAO().getCommentsofPost(new PostDAO().get(permanentIdPost));
        if(!comments.isEmpty()){
            lblNoExists.setText("");
            Pane pane = null;
            int columns = 0;
            int row = 1;
            GridPane gp = new GridPane();
            gp.setPrefSize(170,320);
            for (Comment c : comments){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("comment.fxml"));
                    pane = fxmlLoader.load();
                    c.setUser(new UserDAO().get(c.getUser().getId()));
                    ItemCommentController icc = fxmlLoader.getController();
                    icc.setComment(c.getUser().getName(),c.getText(),c.getDate().toString());
                    if(columns==1) {
                        columns=0;
                        ++row;
                    }
                    gp.add(pane, ++columns, row);
                } catch (IOException e) {
                    Log.warningLogging(e+"");
                }
            }
            scrollComments.setContent(gp);
        } else {
            lblNoExists.setText("No existen comentarios aún.");
        }
    }

    /**
     * Metodo que crea un nuevo comentario
     */
    @FXML
    private void createNewComment(){
        try{
            scrollComments.setVisible(false);
            btnAddComment.setVisible(false);
            anchorShowComment.getChildren().remove(anchorShowComment.getChildren());
            anchorShowComment.getChildren().add(FXMLLoader.load(App.class.getResource("newComment.fxml")));
        } catch (IOException e) {
            Log.warningLogging(e+"");
        }

    }
}
