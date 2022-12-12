package com.facegram.model.DAO;

import com.facegram.connection.DBConnection;
import com.facegram.model.dataobject.Post;
import com.facegram.model.dataobject.User;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    /**
     * Atributos de clase
     */
    private static EntityManager manager;

    /**
     * Inserta un nuevo post en la base de datos
     * @return True o false si la operación se realizó con éxito o no
     */
    public static boolean insert(Post post) {
        boolean result = false;
        manager = DBConnection.getConnect().createEntityManager();
        manager.getTransaction().begin();
        manager.persist(post);
        result=true;
        manager.getTransaction().commit();
        closeManager();
        return result;
    }

    /**
     * Obtiene un post dado por su id
     * @param id Id del objeto a buscar
     * @return Post o null si no existe
     */

    public static Post get(Integer id) {
        manager = DBConnection.getConnect().createEntityManager();
        Post post =null;
        post = manager.find(Post.class,id);
        closeManager();
        return post;
    }

    /**
     * Obtiene todos los post del usuario
     * @return Lista de post o null si no existe ninguno.
     */
    public static List<Post> getAll() {
        manager = DBConnection.getConnect().createEntityManager();
        List<Post> result = new ArrayList<Post>();
        result = manager.createQuery("FROM Post").getResultList();
        closeManager();
        return result;
    }

    /**
     * Obiene todos los post de un usuario
     * @param user Usuario del que queremos sus post
     * @return Lista de post
     */
    public static List<Post> getPostOfUser(User user){
        List<Post> result = new ArrayList<Post>();
        manager = DBConnection.getConnect().createEntityManager();
        result = manager.createQuery("SELECT id, date, edit_date, text FROM post WHERE id_user="+String.valueOf(user.getId())).getResultList();
        closeManager();
        return result;
    }

    /**
     * Actualiza el post
     * @return 1 o 0 si la opoeración se realizó con éxito o no
     */
    public static boolean update(Post post) {
        boolean result = false;
        manager = DBConnection.getConnect().createEntityManager();
        manager.getTransaction().begin();
        manager.merge(post);
        result= true;
        manager.getTransaction().commit();
        closeManager();
        return result;
    }

    /**
     * Elimina un post
     * @return 1 o 0 si la opoeración se realizó con éxito o no
     */
    public static boolean delete(int id) {
        boolean result = false;
        manager = DBConnection.getConnect().createEntityManager();
        manager.getTransaction().begin();
        manager.remove(id);
        result= true;
        manager.getTransaction().commit();
        closeManager();
        return result;
    }

    private static void closeManager() { manager.close(); }
}