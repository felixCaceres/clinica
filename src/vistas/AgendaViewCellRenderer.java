/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import framework.EntityTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import modelo.Agenda;
import util.AppProperties;


/**
 *
 * @author marcelo
 * Esta clase permitira cambiar el color de fondo de las celdas de acuerdo a si 
 * esta libre o ocupado
 */
public class AgendaViewCellRenderer extends DefaultTableCellRenderer{
    
    Font normal = new Font( "Arial",Font.PLAIN, 12 );
    Font negrita = new Font( "Arial",Font.BOLD, 14 );
    Font cursiva = new Font( "Times new roman",Font.ITALIC,12 );
    SimpleDateFormat f ;
    
    public AgendaViewCellRenderer() {
        f = new SimpleDateFormat(AppProperties.FORMATO_HORA);
       
    }
    
    

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        
        
        
        
        if (row  < 0 ) {
            return this;
        }
        EntityTableModel<Agenda> model = (EntityTableModel) jtable.getModel();
        jtable.setFont(negrita);
        jtable.getTableHeader().setFont(negrita);
        setOpaque(true);
        if (model.getItem(row).getId() == null) {
            setBackground(Color.green);
        }
        else {
            setBackground(Color.RED);
        }
        if( value instanceof Date) {
            value = f.format(value);
        }
        setForeground(Color.WHITE);
        super.getTableCellRendererComponent(jtable, value, isSelected, hasFocus, row, column);
        return this;
    }
    
    
}
