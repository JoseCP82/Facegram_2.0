package com.facegram.model.DAO;

import com.facegram.connection.DBConnection;
import com.facegram.model.dataobject.Comment;
import com.facegram.model.dataobject.Post;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    /**
     * Atributos de clase
     */
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
    private static EntityManager manager = emf.createEntityManager();

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
        manager = DBConnection.getConnect().createEntityManager();
        post=manager.find(Post.class,post.getId());
        post.getComments().size();
        closeManger();
        return post.getComments();
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
