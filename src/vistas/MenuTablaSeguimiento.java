/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author marcelo
 */
public class MenuTablaSeguimiento extends JPopupMenu{
    JMenuItem editar;
    JMenuItem borrar;
    public enum MenuSeguimiento {Editar, Borrar};
    public MenuTablaSeguimiento(ActionListener al) {
        editar = new JMenuItem(MenuSeguimiento.Editar.name());
        borrar = new JMenuItem(MenuSeguimiento.Borrar.name());
        
        editar.addActionListener(al);
        borrar.addActionListener(al);
        
        this.add(editar);
        this.addSeparator();
        this.add(borrar);
    }
    
    
    
}
