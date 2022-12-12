package com.facegram.model.DAO;

import com.facegram.connection.DBConnection;
import com.facegram.controllers.PostController;
import com.facegram.interfaces.IDAO;
import com.facegram.log.Log;
import com.facegram.model.dataobject.Comment;
import com.facegram.model.dataobject.Post;
import com.facegram.model.dataobject.User;

import javax.persistence.EntityManager;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommentDAO extends Comment {

    /**
     * Atributos de clase
     */
    private static EntityManager manager;

    /**
     * Inserta un nuevo comentario en la base de datos
     * @return True o false si la operación se realizón con éxito
     */
    public static boolean insert(Comment comment) {
        boolean result = false;
        manager = DBConnection.getConnect().createEntityManager();
        manager.getTransaction().begin();
        manager.persist(comment);
        result=true;
        manager.getTransaction().commit();
        closeManger();
        return result;
    }

    /**
     * Obtiene todos los comentarios asociados a un post
     * @param post Post al cuál estan asociados los comentarios
     * @return Lista de comentarios
     */
    public static List<Comment> getCommentsofPost(Post post) {
        List<Comment> result = new ArrayList<Comment>();
        manager = DBConnection.getConnect().createEntityManager();

        closeManger();
        return result;
    }


    /**
     * Elimina un comentario
     * @return 1 o 0 si se eliminó con éxito o no
     */
    public static boolean delete(Comment comment) {
        boolean result = false;
        manager = DBConnection.getConnect().createEntityManager();
        manager.getTransaction().begin();
        manager.remove(comment);
        result= true;
        manager.getTransaction().commit();
        closeManger();
        return result;
    }

    private static void closeManger() { manager.close(); }
}
