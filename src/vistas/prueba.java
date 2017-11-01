/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.EntityManager;
import modelo.Paciente;
import modelo.Usuario;

/**
 *
 * @author MEC
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Paciente pac ;
        DateFormat dateForm ;
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date fecha;
        for (int i =0;i <10 ; i++){
            try {
                if (i%2 == 0) {
                    fecha = format.parse("2017/01/01");
                }
                else {
                    fecha = format.parse("01/13/2017");
                }
            }
            catch (ParseException e) {
                System.out.println(e.toString());
                fecha = new Date();
            }
            pac = new Paciente(i, "nombre"+i, "apellido"+i , fecha, "tel" +i, "cell"+i, null, null, null);
            
            em.getTransaction().begin();
            em.persist(pac);
            em.getTransaction().commit();
            
        }
        
        
        em.close();
        
        
    }
    
}
