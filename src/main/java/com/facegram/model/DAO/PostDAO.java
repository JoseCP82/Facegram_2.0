package com.facegram.model.DAO;

import com.facegram.connection.DBConnection;
import com.facegram.interfaces.IDAO;
import com.facegram.log.Log;
import com.facegram.model.dataobject.Post;
import com.facegram.model.dataobject.User;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDAO {

    /**
     * Inserta un nuevo post en la base de datos
     * @return True o false si la operación se realizó con éxito o no
     */
    public boolean insert() {
        boolean result = false;

        EntityManager manager = DBConnection.getConnect().createEntityManager();

        return result;
    }

    /**
     * Obtiene un post dado por su id
     * @param id Id del objeto a buscar
     * @return Post o null si no existe
     */

    public Post get(Integer id) {

        return this;
    }

    /**
     * Obtiene todos los post del usuario
     * @return Lista de post o null si no existe ninguno.
     */
    public List<Post> getAll() {
        List<Post> result = new ArrayList<Post>();
        User user = null;
        Connection conn = DBConnection.getConnect();
        if(conn != null) {
            PreparedStatement ps;
            try {
                ps = conn.prepareStatement(SELECTALL);
                if(ps.execute()) {
                    ResultSet rs = ps.getResultSet();
                    while(rs.next()) {
                        Post p = new Post(rs.getInt("id"),
                                rs.getString("text"),
                                rs.getDate("date"),
                                rs.getDate("edit_date"));
                        p.setOwner(new User(rs.getInt("id_user")));
                        result.add(p);
                    }
                    rs.close();
                }
                ps.close();
            } catch (SQLException e) {
                Log.warningLogging(e+"");
            }
        }
        return result;
    }

    /**
     * Obiene todos los post de un usuario
     * @param user Usuario del que queremos sus post
     * @return Lista de post
     */
    public static List<Post> getPostOfUser(User user){
        List<Post> result = new ArrayList<Post>();
        Connection conn = DBConnection.getConnect();
        if(conn != null) {
            PreparedStatement ps;
            try {
                ps = conn.prepareStatement(SELECTALLBYUSER);
                ps.setInt(1, user.getId());
                if(ps.execute()) {
                    ResultSet rs = ps.getResultSet();
                    while(rs.next()) {
                        Post post = new Post(rs.getInt("id"),
                                rs.getString("text"),
                                rs.getDate("date"),
                                rs.getDate("edit_date"));
                        result.add(post);
                    }
                    rs.close();
                }
                ps.close();
            } catch (SQLException e) {
                Log.warningLogging(e+"");
            }
        }
        return result;
    }

    /**
     * Actualiza el post
     * @return 1 o 0 si la opoeración se realizó con éxito o no
     */
    public int update() {
        int result = 0;
        if(id!=-1) {
            Connection conn = DBConnection.getConnect();
            if (conn != null) {
                try {
                    PreparedStatement ps = conn.prepareStatement(UPDATE);
                    ps.setObject(1, this.getEditDate());
                    ps.setString(2, this.getText());
                    ps.setInt(3, this.getId());
                    ps.executeUpdate();
                    ps.close();
                    result = 1;
                } catch (SQLException e) {
                    Log.warningLogging(e + "");
                    result = 0;
                }
            }
        }
        return result;
    }

    /**
     * Elimina un post
     * @return 1 o 0 si la opoeración se realizó con éxito o no
     */
    public int delete() {
        int result = 0;
        if(id!=-1) {
            Connection conn = DBConnection.getConnect();
            if(conn != null) {
                try {
                    PreparedStatement ps = conn.prepareStatement(DELETE);
                    ps.setInt(1, this.id);
                    if(ps.executeUpdate()==1) {
                        this.id=-1;
                    }
                    ps.close();
                    result = 1;
                } catch (SQLException e) {
                    Log.warningLogging(e+"");
                    result = 0;
                }
            }
        }
        return result;
    }
}