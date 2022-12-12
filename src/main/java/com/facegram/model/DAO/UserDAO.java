package com.facegram.model.DAO;

import com.facegram.connection.DBConnection;
import com.facegram.model.dataobject.Post;
import com.facegram.model.dataobject.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDAO {

    /**
     * Atributos de la clase
     */
    private static EntityManager manager;
    private User u;

    /**
     * Mátodo que crea un User en la base de datos
     * @return true o false si lo crea o no
     */
    public boolean insert(User u) {
        manager = DBConnection.getConnect().createEntityManager();
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
    public User get(Integer id) {
        manager = DBConnection.getConnect().createEntityManager();
        User aux = manager.find(User.class, id);
        manager.close();
        return aux;
    }

    /**
     * Método que busca un User por su nombre en la base de datos
     * @param name Nombre del objeto a buscar
     * @return el User o null si lo ha encontrado o no
     */
    public User get(String name) {
        manager = DBConnection.getConnect().createEntityManager();
        User aux = manager.find(User.class, name);
        manager.close();
        return aux;
    }

    /**
     * Método que busca todos los Users de la base de datos
     * @return la lista de Users o null si los ha encontrado o no
     */
    public List<User> getAll() {
        manager = DBConnection.getConnect().createEntityManager();
        List<User> result = manager.createQuery("FROM user").getResultList();
        manager.close();
        return result;
    }

    /**
     * Método que busca todos los Followers y los followereds de un User de la base de datos
     * @return la lista de Users o null si los ha encontrado o no
     */
    public List<User> getFollowOfUser(User u) {
        manager = DBConnection.getConnect().createEntityManager();
        List<User> result = (List<User>) manager.find(User.class, u);
        manager.close();
        return result;
    }

    /**
     * Método que busca todos los Posts que tiene un User
     * @return la lista de Posts
     */
    public List<Post> getPosts(){
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
    public boolean addPosts(Post p){
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
    public List<User> getFollowers(){
        if(u.getFollowers()==null){
            u.setFollowers((User) this.getFollowOfUser(u));
        }
        return u.getFollowers();
    }

    /**
     * Método que añade un Follower a la lista de Followers de un User
     *
     * @param u follower que se va a añadir a la lista de Followers
     * @return true o false si inserta o actualiza el follower
     */
    public boolean addFollowers(User u){
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
        public List<User> getFollowereds(){
            if(u.getFollowereds()==null){
                u.setFollowereds((User) this.getFollowOfUser(u));
            }
            return u.getFollowereds();
        }

        /**
         *Método que añade un Followered a la lista de Followereds de un User
         * @param u followered que se va a añadir a la lista de Followereds
         * @return true o false si inserta o actualiza al followered
         */
        public boolean addFollowereds(User u) {
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
    public boolean update(User u) {
        boolean result = false;
        manager = DBConnection.getConnect().createEntityManager();
        if(manager.contains(u)){
            manager.getTransaction().begin();
            u.setName(this.u.getName());
            u.setPassword(this.u.getPassword());
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
    public boolean delete(User u) {
        boolean result = false;
        manager = DBConnection.getConnect().createEntityManager();
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
