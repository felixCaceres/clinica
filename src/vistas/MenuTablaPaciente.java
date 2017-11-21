/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import modelo.Estudios;

/**
 *
 * @author marcelo
 */
public class MenuTablaPaciente extends JPopupMenu{
    
    public enum MenuPacientes {Nuevo, Editar, Borrar, FichaMedica,Seguimiento, Estudios, Agendar };
    
    private final JMenuItem nuevo;
    private final JMenuItem editar;
    private final JMenuItem fichaMedica;
    private final JMenuItem seguimiento;
    private final JMenuItem agendar;
    private final JMenuItem estudios;
    private final JMenuItem borrar;

    public MenuTablaPaciente(ActionListener al) {
        this.nuevo = new JMenuItem(MenuPacientes.Nuevo.name());
        this.nuevo.addActionListener(al);
        this.editar = new JMenuItem(MenuPacientes.Editar.name());
        this.editar.addActionListener(al);
        this.borrar = new JMenuItem(MenuPacientes.Borrar.name());
        this.borrar.addActionListener(al);
        this.fichaMedica = new JMenuItem(MenuPacientes.FichaMedica.name());
        this.fichaMedica.addActionListener(al);
        this.seguimiento = new JMenuItem(MenuPacientes.Seguimiento.name());
        this.seguimiento.addActionListener(al);
        this.estudios = new JMenuItem(MenuPacientes.Estudios.name());
        this.estudios.addActionListener(al);
        this.agendar = new JMenuItem(MenuPacientes.Agendar.name());
        this.agendar.addActionListener(al);
        
        
        
        
        this.add(nuevo);
        this.add(editar);
        this.add(borrar);
        this.addSeparator();
        this.add(fichaMedica);
        this.add(seguimiento);
        this.add(estudios);
        this.add(agendar);
        
        
        
    }
    
    
    
}
