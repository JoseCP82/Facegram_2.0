package com.facegram.model.DAO;

import com.facegram.connection.DBConnection;
import com.facegram.model.dataobject.Post;
import com.facegram.model.dataobject.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDAO {

    /**
     * Atributos de la clase
     */
    //private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
    //private static EntityManager manager = emf.createEntityManager();
    private static User u;

    /**
     * Mátodo que crea un User en la base de datos
     * @return true o false si lo crea o no
     */
    public static boolean insert(User u) {
        //manager = DBConnection.getConnect().createEntityManager();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();

        boolean result=false;
        if(!manager.contains(u)){
            manager.getTransaction().begin();
            manager.persist(u);
            result = true;
            manager.getTransaction().commit();
            manager.close();
        }
        return result;
    }

    /**
     * Método que busca un User por su id en la base de datos
     * @param id Id del objeto a buscar
     * @return el User o null si lo ha encontrado o no
     */
    public static User get(Integer id) {
        //manager = DBConnection.getConnect().createEntityManager();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();

        User aux = manager.find(User.class, id);
        manager.close();
        return aux;
    }

    /**
     * Método que busca un User por su nombre en la base de datos
     * @param name Nombre del objeto a buscar
     * @return el User o null si lo ha encontrado o no
     */
    public static User get(String name) {
        //manager = DBConnection.getConnect().createEntityManager();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();

        User aux = manager.find(User.class, name);
        manager.close();
        return aux;
    }

    /**
     * Método que busca todos los Users de la base de datos
     * @return la lista de Users o null si los ha encontrado o no
     */
    public static List<User> getAll() {
        //manager = DBConnection.getConnect().createEntityManager();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();


        List<User> result = manager.createQuery("FROM user").getResultList();
        manager.close();
        return result;
    }

    /**
     * Método que busca todos los Followers y los followereds de un User de la base de datos
     * @return la lista de Users o null si los ha encontrado o no
     */
    public static List<User> getFollowOfUser(User u) {
       // manager = DBConnection.getConnect().createEntityManager();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();

        List<User> result = (List<User>) manager.find(User.class, u);
        manager.close();
        return result;
    }

    /**
     * Método que busca todos los Posts que tiene un User
     * @return la lista de Posts
     */
    public static List<Post> getPosts(){
        if(u.getPosts()==null){
            u.setPosts(PostDAO.getPostOfUser(u));
        }
        return u.getPosts();
    }

    /**
     * Método que añade un Post a la lista de Posts de un User
     *
     * @param p post que se va a añadir a la lista de Posts
     * @return true o false si inserta o actualiza el Post
     */
    public static boolean addPosts(Post p){
        boolean result = false;
        p.setOwner(u);
        PostDAO pDAO = new PostDAO();
        if(p.getId()==-1){
            pDAO.insert(p);
            result = true;
        }else{
            pDAO.update(p);
            result = false;
        }
        u.addPosts(p);
        return result;
    }

    /**
     * Método que busca todos los Followers que tiene un User
     * @return la lista de Followers
     */
    public static List<User> getFollowers(){
        if(u.getFollowers()==null){
            u.setFollowers((User) getFollowOfUser(u));
        }
        return u.getFollowers();
    }

    /**
     * Método que añade un Follower a la lista de Followers de un User
     *
     * @param u follower que se va a añadir a la lista de Followers
     * @return true o false si inserta o actualiza el follower
     */
    public static boolean addFollowers(User u){
        boolean result = false;
        u.setFollowers(u);
        UserDAO uDAO = new UserDAO();
        if(u.getId()==-1){
            uDAO.insert(u);
            result = true;
        }else {
            uDAO.update(u);
            result = false;
        }
        return result;
    }

        /**
         * Método que busca todos los Followereds que tiene un User
         * @return la lista de Followereds
         */
        public static List<User> getFollowereds(){
            if(u.getFollowereds()==null){
                u.setFollowereds((User) getFollowOfUser(u));
            }
            return u.getFollowereds();
        }

        /**
         *Método que añade un Followered a la lista de Followereds de un User
         * @param u followered que se va a añadir a la lista de Followereds
         * @return true o false si inserta o actualiza al followered
         */
        public static boolean addFollowereds(User u) {
            boolean result = false;
            u.setFollowereds(u);
            UserDAO uDAO = new UserDAO();
            if (u.getId() == -1) {
                uDAO.insert(u);
                result = true;
            } else {
                uDAO.update(u);
                result = false;
            }
            return result;
        }

    /**
     * Método que edita los campos de la tabla User en la base de datos
     * @return true o false si los ha editado o no
     */
    public static boolean update(User u) {
        boolean result = false;
        //manager = DBConnection.getConnect().createEntityManager();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();

        if(manager.contains(u)){
            manager.getTransaction().begin();
            u.setName(u.getName());
            u.setPassword(u.getPassword());
            manager.merge(u);
            manager.getTransaction().commit();
            result = true;
            manager.close();
        }
        return result;
    }

    /**
     * Método que borra una tabla User de la base de datos
     * @return true o true si la ha borrado o no
     */
    public static boolean delete(User u) {
        boolean result = false;
        //manager = DBConnection.getConnect().createEntityManager();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();

        if(manager.contains(u)){
            manager.getTransaction().begin();
            manager.remove(u);
            manager.getTransaction().commit();
            result = true;
            manager.close();
        }
        return result;
    }

}
