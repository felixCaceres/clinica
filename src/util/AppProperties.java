/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.time.ZoneId;

/**
 *
 * @author marcelo
 */
public class AppProperties {
    public static String FECHA_DEFAULT_FORMAT = "yyyy-MM-dd";
    public static String YEAR_FORMAT = "yy";
    public static String MSG_USER_NOT_SELECTED = "Seleccione un paciente por favor" ;
    public static String TITLE_USER_NOT_SELECTED = "Paciente no seleccionado" ;
    public static String TITLE_SEGUIMIENTO_NO_SET = "Ingrese Seguimiento" ;
    public static String MSG_SEGUIMIENTO_NO_SET = "Ingrese Seguimiento por favor" ;
    public static String TITLE_ESTUDIOSANEXOS_NO_SET = "Ingrese un Estudio";
    public static String MSG_ESTUDIOSANEXOS_NO_SET = "Ingrese un Estudio por favor" ;
    
    public static String TITLE_ALERT_BORRAR = "Borrar Realmente?" ;
    public static String MSG_ALERT_BORRAR = "Desea Borrar Realmente?" ;
    
    public static String[] HORARIOS_LIBRES = new String[]{"08:00", "09:00", "10:00", "11:00",
        "12:00", "14:00", "15:00", "16:00",
        "17:00", "18:00"
    };
    public static String FORMATO_HORA = "HH:mm";
    public static String PROPIEDAD_FECHA = "date";
    public static int OPCION_BORRRAR = 0;
    
    public static String[] CLINICAS = new String []{"CLINICA TAJY", "SANATORIO RIO"};
}
