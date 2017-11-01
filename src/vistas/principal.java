/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
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
                    cargarFichaMedica(paciente.getId());

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
        fichaMedica.setDescalergia(jtAlergias.getText());
        fichaMedica.setAlergias(cbTieneAlergia.isSelected());
    }

    /**
     * De acuerdo al paciente seleccionado cargar los datos del mismo en la
     * ficha medica realizar las consultas necesarias en la bbdd
     *
     * @param id
     */
    FichaMedica fichaMedica;

    private void cargarFichaMedica(Integer id) {
        //To change body of generated methods, choose Tools | Templates.
        System.out.println("Ficha Medica loadFichaMedica> ");
        if (paciente.getFichaMedicas().isEmpty()) {
            System.out.println("Crear nueva FichaMedica y agregar Datos al Paciente");
            fichaMedica = new FichaMedica();
            fichaMedica.setPaciente(paciente);
            paciente.getEstudioses().add(fichaMedica);
        } else {
            Iterator it = paciente.getFichaMedicas().iterator();
            while (it.hasNext()) {
                System.out.println("While cargar fichaMedica");
                fichaMedica = (FichaMedica) it.next();
            }
            System.out.println("Ya tiene ficha medica");

        }

        if (fichaMedica.getAlergias() != null) {

            cbTieneAlergia.setSelected(fichaMedica.getAlergias());
            jtAlergias.setVisible(fichaMedica.getAlergias());
            System.out.println("fichaMedica.getAlergias> " + fichaMedica.getAlergias());
        } else {
            jtAlergias.setVisible(false);
            cbTieneAlergia.setSelected(false);
        }
        jtAlergias.setText(fichaMedica.getDescalergia());

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
        btnNuevoPaciente = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        calFechaNacimiento = new com.toedter.calendar.JDateChooser();
        panelFichaMedica = new javax.swing.JPanel();
        jtAlergias = new javax.swing.JTextField();
        cbTieneAlergia = new javax.swing.JCheckBox();
        btnGuardarFichaMedica = new javax.swing.JButton();
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
        jbGuardar = new javax.swing.JButton();
        jbCancelarfichamedica = new javax.swing.JButton();
        jbSalirfichamedica = new javax.swing.JButton();
        jbEditarfichamedica = new javax.swing.JButton();
        jPSeguimiento = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtSeguimiento = new javax.swing.JTextArea();
        jbGuardarseguimiento = new javax.swing.JButton();
        jbCancelarseguimiento = new javax.swing.JButton();
        jbSalirseguimiento = new javax.swing.JButton();
        jbEditarseguimiento = new javax.swing.JButton();
        jpEstudios = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tfEstudiosAnexos = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jbCancelarestudios = new javax.swing.JButton();
        jbSalirestudios = new javax.swing.JButton();
        jbEditarestudios = new javax.swing.JButton();
        jpAgenda = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jtPaciente = new javax.swing.JTextField();
        calendarfecha = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnguardar = new javax.swing.JButton();
        jbCancelaragenda = new javax.swing.JButton();
        jbEditaragenda = new javax.swing.JButton();
        jbSaliragenda = new javax.swing.JButton();
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

        jpPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Documento");
        jpPaciente.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        jLabel4.setText("Nombre");
        jpPaciente.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, -1, -1));

        jLabel5.setText("Apellido");
        jpPaciente.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, -1, -1));

        jLabel6.setText("Fecha de Nacimiento");
        jpPaciente.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel7.setText("Edad");
        jpPaciente.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, -1));

        jLabel8.setText("Telefono");
        jpPaciente.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, -1, -1));

        jLabel9.setText("Celular");
        jpPaciente.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, -1, -1));
        jpPaciente.add(tfDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 230, -1));
        jpPaciente.add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 230, -1));
        jpPaciente.add(tfApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 220, -1));
        jpPaciente.add(tfEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 80, -1));
        jpPaciente.add(tfTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 240, -1));
        jpPaciente.add(tfCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 240, -1));

        btnNuevoPaciente.setText("Nuevo");
        btnNuevoPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPacienteActionPerformed(evt);
            }
        });
        jpPaciente.add(btnNuevoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jpPaciente.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, -1, -1));

        jButton3.setText("Cancelar");
        jpPaciente.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, -1, -1));

        jButton4.setText("Salir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jpPaciente.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, -1, -1));

        calFechaNacimiento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                calFechaNacimientoFocusLost(evt);
            }
        });
        jpPaciente.add(calFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, -1, -1));

        jTabbedPane1.addTab("Pacientes", jpPaciente);

        panelFichaMedica.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelFichaMedica.add(jtAlergias, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 204, -1));

        cbTieneAlergia.setText("Tiene Alergias?");
        cbTieneAlergia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTieneAlergiaActionPerformed(evt);
            }
        });
        panelFichaMedica.add(cbTieneAlergia, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        btnGuardarFichaMedica.setText("Guardar");
        btnGuardarFichaMedica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarFichaMedicaActionPerformed(evt);
            }
        });
        panelFichaMedica.add(btnGuardarFichaMedica, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        jLabel2.setText("APP");
        panelFichaMedica.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 70, 20));
        panelFichaMedica.add(jtAPP, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 20, 90, -1));

        jLabel10.setText("Cirugías Previas:");
        panelFichaMedica.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));
        panelFichaMedica.add(jtCirugia, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 110, -1));

        jcTransfuciones.setText("Tiene Transfuciones?");
        jcTransfuciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcTransfucionesActionPerformed(evt);
            }
        });
        panelFichaMedica.add(jcTransfuciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jLabel11.setText("Antecedente Familiar:");
        panelFichaMedica.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jtAntecedente.setColumns(20);
        jtAntecedente.setRows(5);
        jScrollPane3.setViewportView(jtAntecedente);

        panelFichaMedica.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 330, 30));

        jLabel12.setText("Motivo de la Consulta:");
        panelFichaMedica.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));
        panelFichaMedica.add(jtMotivoconsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 260, -1));

        jLabel13.setText("A.E.A.:");
        panelFichaMedica.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));
        panelFichaMedica.add(jtAea, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 320, -1));

        jLabel14.setText("Exámen Físico:");
        panelFichaMedica.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        jcPresion.setText("P.A.");
        panelFichaMedica.add(jcPresion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, -1, -1));

        jcFrecuencia.setText("F.C.");
        panelFichaMedica.add(jcFrecuencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, -1, -1));

        jcSat.setText("Sat.");
        panelFichaMedica.add(jcSat, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, -1, -1));

        jcTemperatura.setText("Temp.");
        panelFichaMedica.add(jcTemperatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, -1, -1));
        panelFichaMedica.add(jtExamen, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 130, -1));

        jLabel15.setText("Estudios Solicitados:");
        panelFichaMedica.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));
        panelFichaMedica.add(jtEstudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 240, -1));

        jLabel16.setText("Impresión Diagnostica:");
        panelFichaMedica.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));
        panelFichaMedica.add(jtImpresion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 240, -1));

        jLabel17.setText("Tratamiento:");
        panelFichaMedica.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jtTratamiento.setColumns(20);
        jtTratamiento.setRows(5);
        jScrollPane4.setViewportView(jtTratamiento);

        panelFichaMedica.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 300, 340, 30));

        jbGuardar.setText("Guardar");
        panelFichaMedica.add(jbGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, -1, -1));

        jbCancelarfichamedica.setText("Cancelar");
        panelFichaMedica.add(jbCancelarfichamedica, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, -1, -1));

        jbSalirfichamedica.setText("Salir");
        jbSalirfichamedica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirfichamedicaActionPerformed(evt);
            }
        });
        panelFichaMedica.add(jbSalirfichamedica, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, -1, -1));

        jbEditarfichamedica.setText("Editar");
        panelFichaMedica.add(jbEditarfichamedica, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 350, -1, -1));

        jTabbedPane1.addTab("Ficha Medica", panelFichaMedica);

        jPSeguimiento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setText("Seguimiento:");
        jPSeguimiento.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 51, -1, -1));

        jtSeguimiento.setColumns(20);
        jtSeguimiento.setRows(5);
        jScrollPane5.setViewportView(jtSeguimiento);

        jPSeguimiento.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 350, 60));

        jbGuardarseguimiento.setText("Guardar");
        jPSeguimiento.add(jbGuardarseguimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, -1, -1));

        jbCancelarseguimiento.setText("Cancelar");
        jPSeguimiento.add(jbCancelarseguimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, -1, -1));

        jbSalirseguimiento.setText("Salir");
        jPSeguimiento.add(jbSalirseguimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, -1, -1));

        jbEditarseguimiento.setText("Editar");
        jPSeguimiento.add(jbEditarseguimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, -1, -1));

        jTabbedPane1.addTab("Seguimiento", jPSeguimiento);

        jpEstudios.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setText("Estudios Anexos:");
        jpEstudios.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 49, -1, -1));

        tfEstudiosAnexos.setColumns(20);
        tfEstudiosAnexos.setRows(5);
        jScrollPane6.setViewportView(tfEstudiosAnexos);

        jpEstudios.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 360, -1));

        jButton6.setText("Guardar");
        jpEstudios.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, -1, -1));

        jbCancelarestudios.setText("Cancelar");
        jpEstudios.add(jbCancelarestudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, -1, -1));

        jbSalirestudios.setText("Salir");
        jpEstudios.add(jbSalirestudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, -1, -1));

        jbEditarestudios.setText("Editar");
        jpEstudios.add(jbEditarestudios, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, -1, -1));

        jTabbedPane1.addTab("Estudios", jpEstudios);

        jpAgenda.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setText("Paciente:");
        jpAgenda.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 45, -1, -1));
        jpAgenda.add(jtPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 170, -1));
        jpAgenda.add(calendarfecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, -1));

        jLabel21.setText("Programar Fecha de Consulta:");
        jpAgenda.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel22.setText("Hora:");
        jpAgenda.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        btnguardar.setText("Guardar");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        jpAgenda.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        jbCancelaragenda.setText("Cancelar");
        jpAgenda.add(jbCancelaragenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, -1, -1));

        jbEditaragenda.setText("Editar");
        jpAgenda.add(jbEditaragenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, -1, -1));

        jbSaliragenda.setText("Salir");
        jpAgenda.add(jbSaliragenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, -1, -1));

        jcHora.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jpAgenda.add(jcHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 140, -1));

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

        btnCancelar.setText("CANCELAR");

        btnSalir.setText("SALIR");

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

    private void jbSalirfichamedicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirfichamedicaActionPerformed

    }//GEN-LAST:event_jbSalirfichamedicaActionPerformed

    private void btnGuardarFichaMedicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarFichaMedicaActionPerformed
        // TODO add your handling code here:
        System.out.println("btnGuardarFichaMedica> ");
        cargarVistaAFichaMedica();
        em.getTransaction().begin();
        em.persist(fichaMedica);
        em.getTransaction().commit();
    }//GEN-LAST:event_btnGuardarFichaMedicaActionPerformed

    private void cbTieneAlergiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTieneAlergiaActionPerformed
        // TODO add your handling code here:

        jtAlergias.setVisible(cbTieneAlergia.isSelected());
        panelFichaMedica.invalidate();
    }//GEN-LAST:event_cbTieneAlergiaActionPerformed

    private void calFechaNacimientoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_calFechaNacimientoFocusLost
        // TODO add your handling code here:
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechadenacimiento = LocalDate.parse(calFechaNacimiento.getDateFormatString(), date);
        LocalDate fechaactual = LocalDate.now();

        Period periodo = Period.between(fechadenacimiento, fechaactual);

        String resultado = ("Tienes: " + periodo.getYears() + "Años ");
    }//GEN-LAST:event_calFechaNacimientoFocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnNuevoPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPacienteActionPerformed
        // TODO add your handling code here:

        //Cuando este el boton se presiona guardar los datos de usuario en la bbdd
        //Mostrar errores en caso que los hubieran
        //On este metodo se pasa de un String a un Entero, puede generar un error
        //        cuando el string no es una representacion de un numero
        //Primero guardar todos los TEXTFIELD en usuario
        em.getTransaction().begin();
        em.persist(paciente);
        em.getTransaction().commit();

        //Limpiar los campos
        limpiarCampos();

        cargarDatosPacientes();

        //guardar el usuario con el entityManager
    }//GEN-LAST:event_btnNuevoPacienteActionPerformed

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarActionPerformed
        // TODO add your handling code here:

        if (tablaPaciente.getSelectedRow() < 0) {
            return;
        }

        paciente = getSelectedPaciente();

        System.out.println("Obteniendo paciente para agendar: " + paciente);

        cargarAgenda(paciente);

        jTabbedPane1.setSelectedIndex(TAB_AGENDA);
    }//GEN-LAST:event_btnAgendarActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        // TODO add your handling code here:
        agenda.setPaciente(paciente);
//        agenda.setFecha(tfFecha.getDate());
        //convertir el combo a hora
//        SimpleDateFormat formatoDeFecha = new SimpleDateFormat(FORMATO_HORA);
//        Date horaSeleccionada;
//        try{
//            horaSeleccionada = formatoDeFecha.parse(cbxHora.getSelectedItem().toString());
//            agenda.setHora(horaSeleccionada);
//        } catch (ParseException ex) {
//            Logger.getLogger(AgendaView.class.getName()).log(Level.SEVERE, null, ex);
//        }
        em.getTransaction().begin();
        em.persist(agenda);
        em.getTransaction().commit();
        //produce que se sincronicen los datos con la bbdd

        //limpiar los datos
        agenda = new Agenda();
//        updateFecha();

    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() == TAB_FICHA_MEDICA) {
            System.out.println("btnGuardarFichaMedica> ");
            cargarVistaAFichaMedica();
            em.getTransaction().begin();
            em.persist(fichaMedica);
            em.getTransaction().commit();

        }
        if (jTabbedPane1.getSelectedIndex() == TAB_AGENDA) {
            System.out.println("btnGuardarAGENDA> ");
            // cargarVistaAGENDA();
            em.getTransaction().begin();
            em.persist(agenda);
            em.getTransaction().commit();

        }
        if (jTabbedPane1.getSelectedIndex() == TAB_PACIENTE) {
            System.out.println("btnGuardarPACIENTE> ");
            cargarVistaAPaciente();
            em.getTransaction().begin();
            em.persist(paciente);
            em.getTransaction().commit();

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
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void jcTransfucionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcTransfucionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcTransfucionesActionPerformed
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
    private javax.swing.JButton btnAgendar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardarFichaMedica;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevoPaciente;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnVerPac;
    private javax.swing.JButton btnguardar;
    private com.toedter.calendar.JDateChooser calFechaNacimiento;
    private com.toedter.calendar.JDateChooser calendarfecha;
    private javax.swing.JCheckBox cbTieneAlergia;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbCancelaragenda;
    private javax.swing.JButton jbCancelarestudios;
    private javax.swing.JButton jbCancelarfichamedica;
    private javax.swing.JButton jbCancelarseguimiento;
    private javax.swing.JButton jbEditaragenda;
    private javax.swing.JButton jbEditarestudios;
    private javax.swing.JButton jbEditarfichamedica;
    private javax.swing.JButton jbEditarseguimiento;
    private javax.swing.JButton jbGuardar;
    private javax.swing.JButton jbGuardarseguimiento;
    private javax.swing.JButton jbSaliragenda;
    private javax.swing.JButton jbSalirestudios;
    private javax.swing.JButton jbSalirfichamedica;
    private javax.swing.JButton jbSalirseguimiento;
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
        jtAlergias.setVisible(false);
        cargarDatosPacientes();
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
            //tfDocument
        }
        if (jTabbedPane1.getSelectedIndex() == TAB_FICHA_MEDICA) {
            jtAPP.setText("");
            jtCirugia.setText("");
            jcTransfuciones.setSelected(false);
            cbTieneAlergia.setSelected(false);
            jtAlergias.setText("");
            jtAntecedente.setText("");
            jtMotivoconsulta.setText("");
            jtAea.setText("");
            jcPresion.setSelected(false);
            jcFrecuencia.setSelected(false);
            jcSat.setSelected(false);
            jcTemperatura.setSelected(false);
            jtExamen.setText("");
            jtEstudios.setText("");
            jtImpresion.setText("");
            jtTratamiento.setText("");
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
        tfEdad.setText("");
        tfTel.setText(paciente.getTel());
        tfCel.setText(paciente.getCel());
    }

    /**
     * Verifica un string que cumpla con el formato "dd/MM/yyyy"
     *
     * @param aVerificar
     * @return un date si la fecha es correcta , null en caso contrario
     */
    private Date verificarFecha(String aVerificar) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date fecha;

        try {

            fecha = format.parse(aVerificar);

        } catch (ParseException e) {
            System.out.println(e.toString());
            fecha = null;
        }
        return fecha;
    }

    /**
     * cargar datos pacientes a la grilla
     */
    List<Paciente> pacientesList;

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
        //Aqui es donde se chequea la fecha

        //Date fecha = verificarFecha(tfFechaNacimiento.getText());
        //Verificamos del JCalendar
        paciente.setFechanac(calFechaNacimiento.getDate());
        paciente.setTel(tfTel.getText());
        paciente.setCel(tfCel.getText());

    }

}
