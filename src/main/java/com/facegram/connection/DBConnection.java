package com.facegram.connection;

import com.facegram.log.Log;
import com.facegram.utils.message.ErrorMessage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBConnection {

    /**
     * Atributos de clase
     */
    private static DBConnection _newInstance;
    private static EntityManagerFactory emf;

    /**
     * Metodo para realizar la conexion
     */
    private DBConnection() {
        emf = Persistence.createEntityManagerFactory("MySQL");
        if(emf==null) {
            new ErrorMessage("No se pudo establecer la conexión").showMessage();
            Log.warningLogging("No se pudo establecer la conexión");
        }
    }

    /**
     * Metodo que realiza la instancia de la clase
     * @return Devuelve el objeto con inicializado
     */
    public static EntityManagerFactory getConnect() {
        if(_newInstance==null) {
            _newInstance=new DBConnection();
        }
        return emf;
    }

    /**
     * Procedimiento el cual finaliza la conexión
     */
    public static void close() {
        if(emf != null) {
            emf.close();
        }
    }
}
