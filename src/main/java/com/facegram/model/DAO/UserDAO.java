package com.facegram.model.DAO;

import com.facegram.connection.DBConnection;
import com.facegram.interfaces.IDAO;
import com.facegram.log.Log;
import com.facegram.model.dataobject.Post;
import com.facegram.model.dataobject.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends User implements IDAO<User, Integer> {

    public UserDAO(){}
    public UserDAO(int id){
        this.get(id);
    }
    public UserDAO(User u){
    super(u.getId(), u.getName(), u.getPassword());
    }
    public UserDAO(int id, String name, String password){
        super(id, name, password);
    }
    /**
     * Sentencias de UserDAO
     */
    private final static String INSERT ="INSERT INTO user (name,password) VALUES (?,?)";


    private final static String SELECTBYID ="SELECT id, name, password FROM user WHERE id=?";
    private final static String SELECTBYNAME ="SELECT id, name, password FROM user WHERE name=?";
    private final static String SELECTFOLLOWERSBYUSER="SELECT id, name, password FROM user WHERE name=?";
    private final static String SELECTALL ="SELECT id, name, password FROM user";

    private final static String UPDATE ="UPDATE user SET, name=?, password=? WHERE id=?";
    private final static String DELETE ="DELETE FROM user WHERE id=?";

    /**
     * Mátodo que crea un User en la base de datos
     * @return true o false si lo crea o no
     */
    @Override
    public boolean insert() {
        boolean result=false;
        if(id==-1){
            Connection miCon = DBConnection.getConnect();
            if(miCon!=null){
                PreparedStatement ps;
                try{
                    ps = miCon.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1,this.name);
                    ps.setString(2,this.password);
                    ps.executeUpdate();
                    ResultSet rs= ps.getGeneratedKeys();
                    if(rs.next()){
                        this.id=rs.getInt(1);
                    }
                    ps.close();
                    rs.close();
                    result=true;
                } catch(SQLException e){
                    Log.warningLogging(e+"");
                    result=false;
                }
            }
        }
        return result;
    }

    /**
     * Método que busca un User por su id en la base de datos
     * @param id Id del objeto a buscar
     * @return el User o null si lo ha encontrado o no
     */
    @Override
    public User get(Integer id) {
        User aux=null;
        Connection miCon = DBConnection.getConnect();
        if(miCon!=null){
            PreparedStatement ps;
            try {
                ps = miCon.prepareStatement(SELECTBYID);
                ps.setInt(1,id);
                if(ps.execute()){
                    ResultSet rs = ps.getResultSet();
                    if(rs.next()){
                        this.id=rs.getInt("id");
                        this.name=rs.getString("name");
                        this.password=rs.getString("password");
                    }
                }
                ps.close();
                aux=new User(this.id,this.name,this.password);
            } catch (SQLException e) {
                Log.warningLogging(e+"");
                aux=null;
            }
        }
        return aux;
    }

    /**
     * Método que busca un User por su nombre en la base de datos
     * @param name Nombre del objeto a buscar
     * @return el User o null si lo ha encontrado o no
     */
    public User get(String name) {
        User aux=null;
        Connection miCon = DBConnection.getConnect();
        if(miCon!=null) {
            PreparedStatement ps;
            try {
                ps = miCon.prepareStatement(SELECTBYNAME);
                ps.setString(1, name);
                if (ps.execute()) {
                    ResultSet rs = ps.getResultSet();
                    if (rs.next()) {
                        this.id = rs.getInt("id");
                        this.name=rs.getString("name");
                        this.password = rs.getString("password");
                    }
                }
                ps.close();
                aux = new User(this.id, this.name, this.password);
            } catch (SQLException e) {
                Log.warningLogging(e + "");
                aux = null;
            }
        }
        return aux;
    }

    /**
     * Método que busca todos los Users de la base de datos
     * @return la lista de Users o null si los ha encontrado o no
     */
    public List<User> getAll() {
        List<User> result = new ArrayList<User>();
        Connection miCon = DBConnection.getConnect();
        if(miCon!=null){
            PreparedStatement ps;
            try{
                ps = miCon.prepareStatement(SELECTALL);
                if(ps.execute()){
                    ResultSet rs=ps.getResultSet();
                    while(rs.next()){
                        User aux = new User(rs.getInt("id"),rs.getString("name"),rs.getString("password"));
                        result.add(aux);
                    }
                    rs.close();
                }
                ps.close();
            }catch (SQLException e){
                Log.warningLogging(e+"");
                result=null;
            }
        }
        return result;
    }

    /**
     * Método que busca todos los Followers y los followereds de un User de la base de datos
     * @return la lista de Users o null si los ha encontrado o no
     */
    public List<User> getFollowOfUser(User u) {
        List<User> result = new ArrayList<User>();
        Connection miCon = DBConnection.getConnect();
        if(miCon!=null){
            PreparedStatement ps;
            try{
                ps = miCon.prepareStatement(SELECTFOLLOWERSBYUSER);
                ps.setString(1, u.getName());
                if(ps.execute()){
                    ResultSet rs=ps.getResultSet();
                    while(rs.next()){
                        User aux = new User(rs.getInt("id"),rs.getString("name"),rs.getString("password"));
                        result.add(aux);
                    }
                    rs.close();
                }
                ps.close();
            }catch (SQLException e){
                Log.warningLogging(e+"");
                result=null;
            }
        }
        return result;
    }

    /**
     * Método que busca todos los Posts que tiene un User
     * @return la lista de Posts
     */
    public List<Post> getPosts(){
        if(super.getPosts()==null){
            setPosts(PostDAO.getPostOfUser(this));
        }
        return super.getPosts();
    }

    /**
     *Método que añade un Post a la lista de Posts de un User
     * @param p post que se va a añadir a la lista de Posts
     */
    public void addPosts(Post p){
        boolean result=false;
        p.setOwner(this);
        PostDAO pDAO = new PostDAO(p);
        if(p.getId()==-1){
            pDAO.insert();
        }else{
            pDAO.update();
        }
        super.addPosts(pDAO);
    }

    /**
     * Método que busca todos los Followers que tiene un User
     * @return la lista de Followers
     */
    public List<User> getFollowers(){
        if(super.getFollowers()==null){
            setFollowers((User) this.getFollowOfUser(this));
        }
        return super.getFollowers();
    }

    /**
     * Método que añade un Follower a la lista de Followers de un User
     *
     * @param u follower que se va a añadir a la lista de Followers
     * @return
     */
    public void addFollowers(User u){
        boolean result=false;
        u.setFollowers(this);
        UserDAO uDAO = new UserDAO(u);
        if(u.getId()==-1){
            uDAO.insert();
        }else {
            uDAO.update();
        }
    }

        /**
         * Método que busca todos los Followereds que tiene un User
         * @return la lista de Followereds
         */
        public List<User> getFollowereds(){
            if(super.getFollowereds()==null){
                setFollowereds((User) this.getFollowOfUser(this));
            }
            return super.getFollowereds();
        }

        /**
         *Método que añade un Followered a la lista de Followereds de un User
         * @param u followered que se va a añadir a la lista de Followereds
         */
        public void addFollowereds(User u) {
            boolean result = false;
            u.setFollowereds(this);
            UserDAO uDAO = new UserDAO(u);
            if (u.getId() == -1) {
                uDAO.insert();
            } else {
                uDAO.update();
            }
        }

    /**
     * Método que edita los campos de la tabla User en la base de datos
     * @return 1 o 0 si los ha editado o no
     */
    @Override
    public int update() {
        int i=-1;
        if(id!=-1){
            Connection miCon = DBConnection.getConnect();
            if(miCon!=null){
                PreparedStatement ps;
                try {
                    ps = miCon.prepareStatement(UPDATE);
                    ps.setInt(1, this.id);
                    ps.setString(2, this.name);
                    ps.setString(3, this.password);
                    ps.executeUpdate();
                    ps.close();
                    i=1;
                } catch (SQLException e) {
                    Log.warningLogging(e+"");
                    i=0;
                }
            }
        }
        return i;
    }

    /**
     * Método que borra la tabla User de la base de datos
     * @return 1 o 0 si la ha borrado o no
     */
    @Override
    public int delete() {
        int i=-1;
        if(id!=-1){
            Connection miCon = DBConnection.getConnect();
            if(miCon!=null){
                PreparedStatement ps;
                try {
                    ps=miCon.prepareStatement(DELETE);
                    ps.setInt(1, this.id);
                    if(ps.executeUpdate()==1){
                        this.id=-1;
                    }
                    ps.close();
                    i=1;
                }catch(SQLException e) {
                    Log.warningLogging(e+"");
                    i=0;
                }
            }
        }
        return i;
    }

}
