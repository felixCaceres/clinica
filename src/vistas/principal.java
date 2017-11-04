/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import modelo.Agenda;
import modelo.FichaMedica;
import modelo.Paciente;
import modelo.Usuario;

/**
 *
 * @author MEC Habilitar las siguientes funciones habilitar campos: tiene que
 * fijarse que pestaña esta seleccionada y habilitar todos los campos de esa
 * pestaña deshabilitar campos: tiene que fijarse que pestaña esta seleccionada
 * y deshabilitar todos los campos de esa pestaña terminar de implementar el
 * btnSave para todas las pestañas terminar de implementar el btnNuevo para
 * todas las pestañas ==================================================== falta
 * un ejemplo de como editar un objeto y luego implementar en todas las pestañas
 *
 */
public class principal extends javax.swing.JFrame {

    DefaultTableModel model;

    Integer TAB_PACIENTE = 1;
    Integer TAB_FICHA_MEDICA = 2;
    Integer TAB_SEGUIMIENTO = 3;
    Integer TAB_ESTUDIOS = 4;
    Integer TAB_AGENDA = 5;
    Integer TAB_USUARIOS = 6;
    
    //Listener para el menu
    ActionListener listenerMenu;

    /**
     * Creates new form principal
     */
    public principal() {
        initComponents();

        iniciarComponentes();

        jTabbedPane1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("Tab: " + jTabbedPane1.getSelectedIndex());
                if (jTabbedPane1.getSelectedIndex() == 5) {
                    //Entonces se puede realizar la consulta para traer los usuarios
                    // y cargar los datos de usuario

                    usuarioList = em.createQuery("From Usuario").getResultList();

                    String[] titulos = {"Id", "Nombre", "Password"};
                    model = new DefaultTableModel(null, titulos);

                    //Mostrar los datos en la tabla
                    for (Usuario usuario : usuarioList) {
                        String registro[] = new String[3];
                        registro[0] = usuario.getId().toString();
                        registro[1] = usuario.getUsuario();
                        registro[2] = usuario.getPassword();
                        model.addRow(registro);
                    }

                    tablaUsuario.setModel(model);

                }
//                    Estoy seleccionado el tab fichaMedica
                if (jTabbedPane1.getSelectedIndex() == TAB_PACIENTE) {
                    if (tablaPaciente.getSelectedRow() < 0) {
                        return;
                    }
                    paciente = getSelectedPaciente();
                    cargarPaciente();

                }

                if (jTabbedPane1.getSelectedIndex() == TAB_FICHA_MEDICA) {
                    if (tablaPaciente.getSelectedRow() < 0) {
                        return;
                    }
                    paciente = getSelectedPaciente();
                    cargarFichaMedicaAVista();

                    //
                }
                //nuevo if con el id de la pestaña agenda
                //verificar si hay seleccionado un paciente
                if (jTabbedPane1.getSelectedIndex() == TAB_AGENDA) {
                    if (tablaPaciente.getSelectedRow() < 0) {
                        return;
                    }
                    paciente = getSelectedPaciente();
                    //Cargar los datos para la agenda
                    cargarAgenda(paciente);
                }
            }

        });

    }

    private void cargarVistaAFichaMedica() {
        
        fichaMedica.setApp(jtAPP.getText());
        fichaMedica.setCirugiaprevia(jtCirugia.getText());
        fichaMedica.setTransfucion(jcTransfuciones.isSelected());
        fichaMedica.setAlergias(cbTieneAlergia.isSelected());
        fichaMedica.setDescalergia(jtAlergias.getText());
        fichaMedica.setAntecefamiliar(jtAntecedente.getText());
        fichaMedica.setMotivoconsulta(jtMotivoconsulta.getText());
        fichaMedica.setAntecenfernedad(jtAea.getText());
        //Agregado por Marcelo
        fichaMedica.setEfpa(Boolean.toString(jcPresion.isSelected()));
        fichaMedica.setEffc(Boolean.toString(jcFrecuencia.isSelected()));
        fichaMedica.setEfsat(Boolean.toString(jcSat.isSelected()));
        fichaMedica.setEftemp(Boolean.toString(jcTemperatura.isSelected()));
        fichaMedica.setEstudiosolicitado(jtEstudios.getText());
        fichaMedica.setImprediagnostico(jtImpresion.getText());
        fichaMedica.setTratamiento(jtTratamiento.getText());
        
    }

    /**
     * De acuerdo al paciente seleccionado cargar los datos del mismo en la
     * ficha medica realizar las consultas necesarias en la bbdd
     *
     * @param id
     */
    FichaMedica fichaMedica;

    private void cargarFichaMedicaAVista() {
        //To change body of generated methods, choose Tools | Templates.
        System.out.println("Ficha Medica loadFichaMedica> ");
        //cuando es nuevo y se guarda la ficha tambien, falta refrescar
        em.refresh(paciente);
        if (paciente.getFichaMedicas().isEmpty()) {
            System.out.println("Crear nueva FichaMedica y agregar Datos al Paciente");
            fichaMedica = new FichaMedica();
            fichaMedica.setPaciente(paciente);
            //Esto no se lo que hace
            paciente.getEstudioses().add(fichaMedica);
        } else {
            
            Iterator it = paciente.getFichaMedicas().iterator();
            while (it.hasNext()) {
                System.out.println("While cargar fichaMedica");
                fichaMedica = (FichaMedica) it.next();
                //Como la ficha medica solo "Debe haber una cargamos esa"
                //y salimos del bucle;
                break;
            }
            

        }
        
        jtAPP.setText                            (fichaMedica.getApp()) ;
        jtCirugia.setText                        (fichaMedica.getCirugiaprevia());
        jtAlergias.setText                       (fichaMedica.getDescalergia());
        jtAntecedente.setText                    (fichaMedica.getAntecefamiliar());
        jtMotivoconsulta.setText                 (fichaMedica.getMotivoconsulta()) ;
        jtAea.setText                            ( fichaMedica.getAntecenfernedad() ) ;
        jtEstudios.setText                       (fichaMedica.getEstudiosolicitado());
        jtImpresion.setText                      (fichaMedica.getImprediagnostico());
        jtTratamiento.setText                    (fichaMedica.getTratamiento());
        jtExamen.setText                         (fichaMedica.getExamenfisico());
        
        //Agregado por Marcelo
        //Es un problema los valores por defecto, ya que los campos asociados a 
        //algunas variables boolenas son del Tipo String entonces deben estar 
        //inicializado
        try {
            
            jcTransfuciones.setSelected             (fichaMedica.getTransfucion());
            cbTieneAlergia.setSelected              (fichaMedica.getAlergias()) ;
            jcPresion.setSelected                   (Boolean.parseBoolean(fichaMedica.getEfpa()));
            jcFrecuencia.setSelected                (Boolean.parseBoolean( fichaMedica.getEffc()));
            jcSat.setSelected                       (Boolean.parseBoolean( fichaMedica.getEfsat()));
            jcTemperatura.setSelected               (Boolean.parseBoolean( fichaMedica.getEftemp()));
        } catch (Exception e) {
            System.out.println("sucedio un error: "+ e.getMessage());
        }
        
        //Invalidar la vista para que re renderize
        jtAlergias.setVisible(cbTieneAlergia.isSelected());
        panelFichaMedica.revalidate();
        panelFichaMedica.repaint();

    }

    /**
     *
     */
    Agenda agenda;

    private void cargarAgenda(Paciente pac) {
        agenda = new Agenda();
        agenda.setPaciente(pac);
        ;

        agenda.setFecha(new Date());

        //Vamos a usar la libreria que combierte entre fechas
        //agenda.setHora();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpConsulta = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPaciente = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnVerPac = new javax.swing.JButton();
        btnAgendar = new javax.swing.JButton();
        jpPaciente = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfDocumento = new javax.swing.JTextField();
        tfNombre = new javax.swing.JTextField();
        tfApellido = new javax.swing.JTextField();
        tfEdad = new javax.swing.JTextField();
        tfTel = new javax.swing.JTextField();
        tfCel = new javax.swing.JTextField();
        calFechaNacimiento = new com.toedter.calendar.JDateChooser();
        panelFichaMedica = new javax.swing.JPanel();
        jtAlergias = new javax.swing.JTextField();
        cbTieneAlergia = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jtAPP = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtCirugia = new javax.swing.JTextField();
        jcTransfuciones = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtAntecedente = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jtMotivoconsulta = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtAea = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jcPresion = new javax.swing.JCheckBox();
        jcFrecuencia = new javax.swing.JCheckBox();
        jcSat = new javax.swing.JCheckBox();
        jcTemperatura = new javax.swing.JCheckBox();
        jtExamen = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtEstudios = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtImpresion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtTratamiento = new javax.swing.JTextArea();
        jPSeguimiento = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtSeguimiento = new javax.swing.JTextArea();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jpEstudios = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tfEstudiosAnexos = new javax.swing.JTextArea();
        jpAgenda = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jtPaciente = new javax.swing.JTextField();
        calendarfecha = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jcHora = new javax.swing.JComboBox();
        jpUsuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuario = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPacienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaPaciente);

        jLabel1.setText("Ingrese el nombre o Docuemnto:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        btnVerPac.setText("ver");
        btnVerPac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerPacActionPerformed(evt);
            }
        });

        btnAgendar.setText("Programar consulta");
        btnAgendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpConsultaLayout = new javax.swing.GroupLayout(jpConsulta);
        jpConsulta.setLayout(jpConsultaLayout);
        jpConsultaLayout.setHorizontalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpConsultaLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpConsultaLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpConsultaLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnVerPac))
                            .addComponent(btnAgendar))
                        .addGap(43, 43, 43))))
        );
        jpConsultaLayout.setVerticalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpConsultaLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgendar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVerPac)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Consulta de Pacientes", jpConsulta);

        jLabel3.setText("Documento");

        jLabel4.setText("Nombre");

        jLabel5.setText("Apellido");

        jLabel6.setText("Fecha de Nacimiento");

        jLabel7.setText("Edad");

        jLabel8.setText("Telefono");

        jLabel9.setText("Celular");

        tfDocumento.setEditable(false);

        tfNombre.setEditable(false);

        tfApellido.setEditable(false);

        tfEdad.setEditable(false);
        tfEdad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfEdadFocusGained(evt);
            }
        });

        tfTel.setEditable(false);

        tfCel.setEditable(false);

        calFechaNacimiento.setEnabled(false);
        calFechaNacimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                calFechaNacimientoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jpPacienteLayout = new javax.swing.GroupLayout(jpPaciente);
        jpPaciente.setLayout(jpPacienteLayout);
        jpPacienteLayout.setHorizontalGroup(
            jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel3)
                .addGap(16, 16, 16)
                .addComponent(tfDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel4)
                .addGap(23, 23, 23)
                .addComponent(tfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel5)
                .addGap(23, 23, 23)
                .addComponent(tfApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addGap(21, 21, 21)
                .addComponent(calFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel7)
                .addGap(26, 26, 26)
                .addComponent(tfEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel8)
                .addGap(28, 28, 28)
                .addComponent(tfTel, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel9)
                .addGap(27, 27, 27)
                .addComponent(tfCel, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpPacienteLayout.setVerticalGroup(
            jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPacienteLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPacienteLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3))
                    .addComponent(tfDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(tfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(tfApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(calFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(tfEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(tfTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpPacienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(tfCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Pacientes", jpPaciente);

        jtAlergias.setEditable(false);

        cbTieneAlergia.setText("Tiene Alergias?");
        cbTieneAlergia.setEnabled(false);
        cbTieneAlergia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTieneAlergiaActionPerformed(evt);
            }
        });

        jLabel2.setText("APP");

        jtAPP.setEditable(false);

        jLabel10.setText("Cirugías Previas:");

        jtCirugia.setEditable(false);

        jcTransfuciones.setText("Tiene Transfuciones?");
        jcTransfuciones.setEnabled(false);

        jLabel11.setText("Antecedente Familiar:");

        jtAntecedente.setEditable(false);
        jtAntecedente.setColumns(20);
        jtAntecedente.setRows(5);
        jScrollPane3.setViewportView(jtAntecedente);

        jLabel12.setText("Motivo de la Consulta:");

        jtMotivoconsulta.setEditable(false);

        jLabel13.setText("A.E.A.:");

        jtAea.setEditable(false);

        jLabel14.setText("Exámen Físico:");

        jcPresion.setText("P.A.");
        jcPresion.setEnabled(false);

        jcFrecuencia.setText("F.C.");
        jcFrecuencia.setEnabled(false);

        jcSat.setText("Sat.");
        jcSat.setEnabled(false);

        jcTemperatura.setText("Temp.");
        jcTemperatura.setEnabled(false);

        jtExamen.setEditable(false);

        jLabel15.setText("Estudios Solicitados:");

        jtEstudios.setEditable(false);

        jLabel16.setText("Impresión Diagnostica:");

        jtImpresion.setEditable(false);

        jLabel17.setText("Tratamiento:");

        jtTratamiento.setEditable(false);
        jtTratamiento.setColumns(20);
        jtTratamiento.setRows(5);
        jScrollPane4.setViewportView(jtTratamiento);

        javax.swing.GroupLayout panelFichaMedicaLayout = new javax.swing.GroupLayout(panelFichaMedica);
        panelFichaMedica.setLayout(panelFichaMedicaLayout);
        panelFichaMedicaLayout.setHorizontalGroup(
            panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jtAPP, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(20, 20, 20)
                        .addComponent(jtCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jcTransfuciones)
                        .addGap(23, 23, 23)
                        .addComponent(cbTieneAlergia)
                        .addGap(13, 13, 13)
                        .addComponent(jtAlergias, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(43, 43, 43)
                        .addComponent(jtMotivoconsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(114, 114, 114)
                        .addComponent(jtAea, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(39, 39, 39)
                        .addComponent(jcPresion)
                        .addGap(13, 13, 13)
                        .addComponent(jcFrecuencia)
                        .addGap(3, 3, 3)
                        .addComponent(jcSat)
                        .addGap(5, 5, 5)
                        .addComponent(jcTemperatura)
                        .addGap(15, 15, 15)
                        .addComponent(jtExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(23, 23, 23)
                        .addComponent(jtEstudios, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(11, 11, 11)
                        .addComponent(jtImpresion, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(48, 48, 48)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        panelFichaMedicaLayout.setVerticalGroup(
            panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtAPP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jtCirugia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcTransfuciones)
                    .addComponent(cbTieneAlergia)
                    .addComponent(jtAlergias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jtMotivoconsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jtAea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFichaMedicaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel14))
                    .addComponent(jcPresion)
                    .addComponent(jcFrecuencia)
                    .addComponent(jcSat)
                    .addComponent(jcTemperatura)
                    .addComponent(jtExamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jtEstudios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jtImpresion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelFichaMedicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Ficha Medica", panelFichaMedica);

        jLabel18.setText("Seguimiento:");

        btnAdd.setText("Agregar");

        jtSeguimiento.setColumns(20);
        jtSeguimiento.setRows(5);
        jScrollPane7.setViewportView(jtSeguimiento);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTable1);

        javax.swing.GroupLayout jPSeguimientoLayout = new javax.swing.GroupLayout(jPSeguimiento);
        jPSeguimiento.setLayout(jPSeguimientoLayout);
        jPSeguimientoLayout.setHorizontalGroup(
            jPSeguimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSeguimientoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSeguimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPSeguimientoLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd)
                        .addContainerGap(45, Short.MAX_VALUE))
                    .addComponent(jScrollPane8)))
        );
        jPSeguimientoLayout.setVerticalGroup(
            jPSeguimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSeguimientoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSeguimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd)
                    .addComponent(jLabel18)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Seguimiento", jPSeguimiento);

        jLabel19.setText("Estudios Anexos:");

        tfEstudiosAnexos.setEditable(false);
        tfEstudiosAnexos.setColumns(20);
        tfEstudiosAnexos.setRows(5);
        jScrollPane6.setViewportView(tfEstudiosAnexos);

        javax.swing.GroupLayout jpEstudiosLayout = new javax.swing.GroupLayout(jpEstudios);
        jpEstudios.setLayout(jpEstudiosLayout);
        jpEstudiosLayout.setHorizontalGroup(
            jpEstudiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEstudiosLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel19)
                .addGap(14, 14, 14)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpEstudiosLayout.setVerticalGroup(
            jpEstudiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEstudiosLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel19))
            .addGroup(jpEstudiosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Estudios", jpEstudios);

        jLabel20.setText("Paciente:");

        jLabel21.setText("Programar Fecha de Consulta:");

        jLabel22.setText("Hora:");

        jcHora.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jpAgendaLayout = new javax.swing.GroupLayout(jpAgenda);
        jpAgenda.setLayout(jpAgendaLayout);
        jpAgendaLayout.setHorizontalGroup(
            jpAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAgendaLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel20)
                .addGap(37, 37, 37)
                .addComponent(jtPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpAgendaLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel21)
                .addGap(24, 24, 24)
                .addComponent(calendarfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpAgendaLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel22)
                .addGap(13, 13, 13)
                .addComponent(jcHora, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpAgendaLayout.setVerticalGroup(
            jpAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAgendaLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jpAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpAgendaLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel20))
                    .addComponent(jtPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jpAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(calendarfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpAgendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jcHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Agenda", jpAgenda);

        tablaUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaUsuario);

        javax.swing.GroupLayout jpUsuariosLayout = new javax.swing.GroupLayout(jpUsuarios);
        jpUsuarios.setLayout(jpUsuariosLayout);
        jpUsuariosLayout.setHorizontalGroup(
            jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpUsuariosLayout.setVerticalGroup(
            jpUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usuarios", jpUsuarios);

        btnSave.setText("GUARDAR");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jMenu1.setText("Archivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Utilidades");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnEditar)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar)
                .addGap(18, 18, 18)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTieneAlergiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTieneAlergiaActionPerformed
        // TODO add your handling code here:

        jtAlergias.setVisible(cbTieneAlergia.isSelected());
        panelFichaMedica.revalidate();
        panelFichaMedica.repaint();
    }//GEN-LAST:event_cbTieneAlergiaActionPerformed

    private void calFechaNacimientoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_calFechaNacimientoFocusLost
        // TODO add your handling code here:
        
        tfEdad.setText(calcularEdad());
    }//GEN-LAST:event_calFechaNacimientoFocusLost

    private void btnVerPacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerPacActionPerformed
        // TODO add your handling code here:

        if (tablaPaciente.getSelectedRow() < 0) {
            return;
        }

        paciente = getSelectedPaciente();

        System.out.println("Obteniendo paciente: " + paciente);

        cargarPaciente();

        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnVerPacActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:

        cargarDatosPacientes();
    }//GEN-LAST:event_txtNombreActionPerformed

    private void tablaPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPacienteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaPacienteMouseClicked

    private void btnAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarActionPerformed
        // TODO add your handling code here:

        if (tablaPaciente.getSelectedRow() < 0) {
            return;
        }
        jTabbedPane1.setSelectedIndex(TAB_AGENDA);
    }//GEN-LAST:event_btnAgendarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() == TAB_FICHA_MEDICA) {
            System.out.println("btnGuardarFichaMedica> ");
            if (paciente == null || paciente.getId() == null) {
                showMensaje("Paciente no seleccionado","Seleccione paciente!!!");
                return;
            }
            cargarVistaAFichaMedica();
            em.getTransaction().begin();
            em.persist(fichaMedica);
            em.getTransaction().commit();
            //para que pueda cargar los datos             
            //Luego de guardar la ficha refrescamos la tabla
            cargarDatosPacientes();
            

        }
        if (jTabbedPane1.getSelectedIndex() == TAB_AGENDA) {
            System.out.println("btnGuardarAGENDA> ");
            // cargarVistaAGENDA();
            em.getTransaction().begin();
            em.persist(agenda);
            em.getTransaction().commit();

        }
        
        //Cuando es paciente y se preciona guardar no hace falta limpiar los 
        //campos
        if (jTabbedPane1.getSelectedIndex() == TAB_PACIENTE) {
            System.out.println("btnGuardarPACIENTE> ");
            cargarVistaAPaciente();
            em.getTransaction().begin();
            em.persist(paciente);
            em.getTransaction().commit();
            cargarDatosPacientes();

        }
        if (jTabbedPane1.getSelectedIndex() == TAB_SEGUIMIENTO) {
            System.out.println("btnGuardarSEGUIMIENTO> ");
            //cargarVistaSeguimiento();
            em.getTransaction().begin();
            em.persist(agenda);
            em.getTransaction().commit();

        }
        if (jTabbedPane1.getSelectedIndex() == TAB_ESTUDIOS) {
            System.out.println("btnGuardarESTUDIOS> ");
            //cargarVistaEstudios();
            em.getTransaction().begin();
            em.persist(agenda);
            em.getTransaction().commit();

        }
        limpiarCampos();
        habilitarCampos(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        habilitarCampos(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        habilitarCampos(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:

        int n = showMensaje("Salir del sistema", "Desea abandonar el sistema?");
        if (n == 0) {
            System.exit(0);
        }
        calcularEdad();

    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        habilitarCampos(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tfEdadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfEdadFocusGained
        // TODO add your handling code here:
        tfEdad.setText(calcularEdad());
    }//GEN-LAST:event_tfEdadFocusGained
    /**
     * Busca un paciente en la tabla Pacinetes, de acuerdo a lo que esta
     * seleccionado en la grilla si nada no esta seleccionado retorna null
     *
     * @return
     */
    private Paciente getSelectedPaciente() {
        Paciente result = null;
        if (tablaPaciente.getSelectedRow() < 0) {
            return result;
        }

        int row = tablaPaciente.getSelectedRow();
        String idStr = (String) tablaPaciente.getValueAt(row, 0);
        System.out.println("Buscando este ID: " + idStr);
        try {
            Integer id = Integer.parseInt(idStr);
            result = em.find(Paciente.class, id);
        } catch (NumberFormatException nfe) {
            System.out.println("Error al convertir de string a entero: " + idStr);
        }

        return result;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new principal().setVisible(true);
            }
        });
    }

    EntityManager em;
    List<Usuario> usuarioList;
    Usuario user;

    private Paciente paciente;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAgendar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnVerPac;
    private com.toedter.calendar.JDateChooser calFechaNacimiento;
    private com.toedter.calendar.JDateChooser calendarfecha;
    private javax.swing.JCheckBox cbTieneAlergia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPSeguimiento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox jcFrecuencia;
    private javax.swing.JComboBox jcHora;
    private javax.swing.JCheckBox jcPresion;
    private javax.swing.JCheckBox jcSat;
    private javax.swing.JCheckBox jcTemperatura;
    private javax.swing.JCheckBox jcTransfuciones;
    private javax.swing.JPanel jpAgenda;
    private javax.swing.JPanel jpConsulta;
    private javax.swing.JPanel jpEstudios;
    private javax.swing.JPanel jpPaciente;
    private javax.swing.JPanel jpUsuarios;
    private javax.swing.JTextField jtAPP;
    private javax.swing.JTextField jtAea;
    private javax.swing.JTextField jtAlergias;
    private javax.swing.JTextArea jtAntecedente;
    private javax.swing.JTextField jtCirugia;
    private javax.swing.JTextField jtEstudios;
    private javax.swing.JTextField jtExamen;
    private javax.swing.JTextField jtImpresion;
    private javax.swing.JTextField jtMotivoconsulta;
    private javax.swing.JTextField jtPaciente;
    private javax.swing.JTextArea jtSeguimiento;
    private javax.swing.JTextArea jtTratamiento;
    private javax.swing.JPanel panelFichaMedica;
    private javax.swing.JTable tablaPaciente;
    private javax.swing.JTable tablaUsuario;
    private javax.swing.JTextField tfApellido;
    private javax.swing.JTextField tfCel;
    private javax.swing.JTextField tfDocumento;
    private javax.swing.JTextField tfEdad;
    private javax.swing.JTextArea tfEstudiosAnexos;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfTel;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables

    private void iniciarComponentes() {

        em = HibernateUtil.getSessionFactory().createEntityManager();
        paciente = new Paciente();
        fichaMedica = new FichaMedica();
        jtAlergias.setVisible(false);
        cargarDatosPacientes();
        listenerMenu = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ejecutarMenu(ae);
            }

            
        };
        tablaPaciente.setComponentPopupMenu(new MenuTablaPaciente(listenerMenu));
    }
    
    //Para Implementar los menus de las tablas las acciones que queres que sucedan
    private void ejecutarMenu(ActionEvent ae) {
        
        Paciente modificar = getSelectedPaciente();
        
        if (ae.getActionCommand().equals(MenuTablaPaciente.MenuPacientes.Nuevo.name())){
            
        }
        if (ae.getActionCommand().equals(MenuTablaPaciente.MenuPacientes.Editar.name())){
            
        }
        if (ae.getActionCommand().equals(MenuTablaPaciente.MenuPacientes.FichaMedica.name())){
            jTabbedPane1.setSelectedIndex(TAB_FICHA_MEDICA);
        }
        if (ae.getActionCommand().equals(MenuTablaPaciente.MenuPacientes.Seguimiento.name())){
            
        }
        /*
        Para borrar se necesita verificar si tiene relaciones en otras tablas
        por defecto vamos a borrar, luego hay que verificar si devemos bloquear o no ciertas 
        acciones
        */
        if (ae.getActionCommand().equals(MenuTablaPaciente.MenuPacientes.Borrar.name())){
            if (modificar ==null) {
                return;
            }
            if (!modificar.getFichaMedicas().isEmpty()) {
                //tiene fichas medicas, entonces borro las fichas
                deleteAll( modificar.getFichaMedicas().iterator());
            }
            if (!modificar.getSeguimientos().isEmpty()) {
                //Tiene seguimientos entonces debo borrar
                deleteAll( modificar.getSeguimientos().iterator());
            
            }
            if (!modificar.getEstudioses().isEmpty()) {
                //Tiene estudios entonces debo borrar
                deleteAll( modificar.getEstudioses().iterator());
            }
            em.getTransaction().begin();
            em.remove(modificar);
            em.getTransaction().commit();
            //se borro todo entonces recargar la tabla
            cargarDatosPacientes();
        }
    }

    private void limpiarCampos() {
        if (jTabbedPane1.getSelectedIndex() == TAB_PACIENTE) {
            tfDocumento.setText("");
            tfNombre.setText("");
            tfApellido.setText("");
            calFechaNacimiento.setDate(null);
            tfEdad.setText("");
            tfTel.setText("");
            tfCel.setText("");
            paciente = new Paciente();
            fichaMedica = new FichaMedica();
            //tfDocument
        }
        //Para las fichas medicas no hace falta limpiar los campos pues
        //solo tiene una ficha medica
        if (jTabbedPane1.getSelectedIndex() == TAB_FICHA_MEDICA) {
//            jtAPP.setText("");
//            jtCirugia.setText("");
//            jcTransfuciones.setSelected(false);
//            cbTieneAlergia.setSelected(false);
//            jtAlergias.setText("");
//            jtAntecedente.setText("");
//            jtMotivoconsulta.setText("");
//            jtAea.setText("");
//            jcPresion.setSelected(false);
//            jcFrecuencia.setSelected(false);
//            jcSat.setSelected(false);
//            jcTemperatura.setSelected(false);
//            jtExamen.setText("");
//            jtEstudios.setText("");
//            jtImpresion.setText("");
//            jtTratamiento.setText("");
        }
        if (jTabbedPane1.getSelectedIndex() == TAB_SEGUIMIENTO) {
            jtSeguimiento.setText("");
        }
        if (jTabbedPane1.getSelectedIndex() == TAB_ESTUDIOS) {
            tfEstudiosAnexos.setText("");
        }
        if (jTabbedPane1.getSelectedIndex() == TAB_AGENDA) {
            jtPaciente.setText("");
            calendarfecha.setDate(new Date());
            jcHora.setSelectedIndex(0);
        }

    }

    private void cargarPaciente() {
        tfDocumento.setText(paciente.getDocumento().toString());
        tfNombre.setText(paciente.getNombre());
        tfApellido.setText(paciente.getApellido());
        calFechaNacimiento.setDate(paciente.getFechanac());
        //Hacer calculo de edad
        tfEdad.setText(calcularEdad());
        tfTel.setText(paciente.getTel());
        tfCel.setText(paciente.getCel());
    }

    
    /**
     * cargar datos pacientes a la grilla
     * 
     * Falta mejorar este codigo, por ahora lo dejamos asi
     */
    List<Paciente> pacientesList;
    
    /**
     * Cargar la lista de pacientes para actualizar los datos de la tabla
     * una vez realizada la consulta carga todos los datos a la tabla de 
     * pacientes
     */
    private void cargarDatosPacientes() {

        if (txtNombre.getText().length() == 0) {
            pacientesList = em.createQuery("From Paciente").getResultList();
        } else {

            Integer searchDoc = 0;
            try {
                searchDoc = Integer.parseInt(txtNombre.getText());
            } catch (NumberFormatException nfe) {
                System.out.println("Error al convertir a numero");
            }

            pacientesList = em.createQuery("From Paciente p where p.nombre LIKE :searchText OR p.documento LIKE :searchDoc")
                    .setParameter("searchText", '%' + txtNombre.getText() + '%')
                    .setParameter("searchDoc", searchDoc)
                    .getResultList();

        }
        String[] titulos = {"Id", "Documento", "Nombre", "Apellido"};
        DefaultTableModel model = new DefaultTableModel(null, titulos);
        for (Paciente paciente : pacientesList) {
            String registro[] = new String[4];
            registro[0] = paciente.getId().toString();
            registro[1] = paciente.getDocumento() != null ? paciente.getDocumento().toString() : "S/D";
            registro[2] = paciente.getNombre();
            registro[3] = paciente.getApellido();
            model.addRow(registro);
        }
        tablaPaciente.setModel(model);

    }

    private void cargarVistaAPaciente() {
        Integer doc = Integer.parseInt(tfDocumento.getText());

        paciente.setDocumento(doc);
        paciente.setNombre(tfNombre.getText());
        paciente.setApellido(tfApellido.getText());
        
        paciente.setFechanac(calFechaNacimiento.getDate());
        paciente.setTel(tfTel.getText());
        paciente.setCel(tfCel.getText());

    }

    private void habilitarCampos(boolean estado) {
        if (jTabbedPane1.getSelectedIndex() == TAB_PACIENTE) {
            tfDocumento.setEditable(estado);
            tfNombre.setEditable(estado);
            tfApellido.setEditable(estado);
            calFechaNacimiento.setEnabled(estado);
            //Este campo no es editable
//            tfEdad.setEditable(estado);
            tfTel.setEditable(estado);
            tfCel.setEditable(estado);

        }
        /*
        *Primero habilitamos todos los que son textField
        *Luego todos los que son checkbox
        */
         if (jTabbedPane1.getSelectedIndex() == TAB_FICHA_MEDICA) {
            jtAPP.setEditable(estado);
            jtCirugia.setEditable(estado);
            jtAlergias.setEditable(estado);
            jtAntecedente.setEditable(estado);
            jtMotivoconsulta.setEditable(estado);
            jtAea.setEditable(estado);
            jtExamen.setEditable(estado);
            jtEstudios.setEditable(estado);
            jtTratamiento.setEditable(estado);
            jtImpresion.setEditable(estado);
            
            //Estaba mal, falta habilitar no setear el valor
            jcTransfuciones.setEnabled(estado);
            cbTieneAlergia.setEnabled(estado);
            jcPresion.setEnabled(estado);
            jcFrecuencia.setEnabled(estado);
            jcSat.setEnabled(estado);
            jcTemperatura.setEnabled(estado);
         }
         if (jTabbedPane1.getSelectedIndex() == TAB_SEGUIMIENTO) {
            jtSeguimiento.setEditable(estado);
        }
        if (jTabbedPane1.getSelectedIndex() == TAB_ESTUDIOS) {
            tfEstudiosAnexos.setEditable(estado);
        }
        if (jTabbedPane1.getSelectedIndex() == TAB_AGENDA) {
            jtPaciente.setEditable(estado);
            calendarfecha.setDate(new Date());
            jcHora.setSelectedIndex(0);
        }
    }
    
    /**
     * Metodo para calcular la edad
     */
    

    private String calcularEdad() {
        
        LocalDate ahora = LocalDate.now();
        LocalDate fechaSeleccionada ;
        long edad  = 0;
        ZoneId zoneId = ZoneId.systemDefault();
        fechaSeleccionada = calFechaNacimiento.getCalendar().
                toInstant().atZone(zoneId).toLocalDate();
        
        edad = ChronoUnit.YEARS.between(fechaSeleccionada , ahora);
        return edad + " años";
        
    }

    private int showMensaje(String titulo, String mensaje) {
        Object[] options = {"Aceptar",
            "Cancelar"};
        int n = JOptionPane.showOptionDialog(new Frame(),
                mensaje,
                titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return n;
    }
    
    private void deleteAll(Iterator it){
        while (it.hasNext()) {
            em.getTransaction().begin();
            em.remove(it.next());
            em.getTransaction().commit();
        }
    }

}
