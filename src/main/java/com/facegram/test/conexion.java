package com.facegram.test;

import com.facegram.model.DAO.UserDAO;
import com.facegram.model.dataobject.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class conexion {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MySQL");
        EntityManager manager = emf.createEntityManager();
        //manager = DBConnection.getConnect().createEntityManager();
        List<User> result = manager.createQuery("FROM user").getResultList();
        manager.close();
    }
}
