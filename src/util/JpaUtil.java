/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author MEC
 */
public class JpaUtil {
    private static final EntityManagerFactory entityManagerFactory;
        static {
            try {
                entityManagerFactory = 
                        Persistence.createEntityManagerFactory("SwingHibernatePU");
            }catch (Throwable ex){
                System.err.println("Initial EntityManagerFactory crated failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
public static EntityManagerFactory getEntityManagerFactory(){
    return entityManagerFactory;
}
}
