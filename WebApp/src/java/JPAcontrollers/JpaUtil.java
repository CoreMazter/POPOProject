/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPAcontrollers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author manie
 */
public class JpaUtil {
    private static final EntityManagerFactory emf;
    static{
        try{
            emf=Persistence.createEntityManagerFactory("WebAppPU");
        }catch(Throwable t){
            System.out.println("Error al iniciar el EntityManagerFactory"+t);
            t.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }
    
    public static EntityManagerFactory getEntityMaganerFactory(){
        return emf;
    }
}
