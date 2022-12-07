package com.facegram.utils.badWords;

import com.facegram.log.Log;
import com.facegram.utils.message.ErrorMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BadWords {

    /**
     * Atributos de clase
     */
    private static BadWords _newInstance;
    private String uri = "jdbc:mysql://localhost/badwords";
    private static Connection conn = null;
    private static List<String> badWords = new ArrayList<>();

    /**
     * Metodo para realizar la conexion
     */
    private BadWords() {
        try {
            conn= DriverManager.getConnection(uri,"root","");
            getQuery();
        } catch (SQLException e) {
            new ErrorMessage("No se pudo crear la conexion").showMessage();
            Log.warningLogging(e+"");
            conn=null;
        }
    }

    /**
     * Metodo que realiza la instancia de la clase
     * @return Devuelve el objeto con inicializado
     */
    public static Connection getConnect() {
        if(_newInstance==null) {
            _newInstance=new BadWords();
        }
        return conn;
    }

    /**
     * Realiza la consulta y rellena el List
     */
    private void getQuery() {
        String word="";
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT palabra FROM word";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                word=(rs.getString("palabra"));
                badWords.add(word);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            Log.warningLogging(e+"");
        }
    }

    /**
     * Comprueba si una palabra del List se encuentra en la cadena
     * @param text Cadena a recorrer
     * @return True o false si existe o no
     */
    public static boolean isFound(String text) {
        if (text != null) {
            for(String word : badWords) {
                if (text.indexOf(word)!=-1){
                    return true;
                }
            }
        }
        return false;
    }
}


