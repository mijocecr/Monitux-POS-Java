/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Encriptador;
import com.monituxpos.Clases.Usuario;
import com.monituxpos.Clases.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Usuario extends javax.swing.JFrame {

    public int Secuencial_Usuario=1;//Cambiar esto
    public int Secuencial_Empresa=1;//Cambiar esto
    public int Secuencial;
    
    
    private byte[] imagen;

public byte[] getImagen() {
    return imagen;
}

public void setImagen(byte[] imagen) {
    this.imagen = imagen;
}

    
    /**
     * Creates new form V_Usuario
     */
    public V_Usuario() {
        initComponents();
    }

   
    private void cargarDatos() {
    // Modelo no editable
    DefaultTableModel model = new DefaultTableModel(
        new String[] { "S", "Codigo", "Nombre", "Acceso", "Activo", "Password", "Imagen" }, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tableUsuarios.setModel(model);
    tableUsuarios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Scroll horizontal

    // Estilo visual profesional
    tableUsuarios.setRowHeight(24);
    tableUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    tableUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    tableUsuarios.setGridColor(Color.LIGHT_GRAY);

    // Efecto zebra + centrado
    tableUsuarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            c.setBackground(isSelected ? table.getSelectionBackground() :
                (row % 2 == 0 ? Color.WHITE : new Color(224, 224, 224)));
            return c;
        }
    });

    // Ocultar columnas sensibles
    for (int i = 5; i <= 6; i++) {
        tableUsuarios.getColumnModel().getColumn(i).setMinWidth(0);
        tableUsuarios.getColumnModel().getColumn(i).setMaxWidth(0);
        tableUsuarios.getColumnModel().getColumn(i).setWidth(0);
    }

    // Cargar datos desde JPA
    EntityManager em = Persistence.createEntityManagerFactory("MonituxPU").createEntityManager();
    try {
        List<Usuario> usuarios = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.Secuencial_Empresa = :empresa", Usuario.class)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Usuario u : usuarios) {
            model.addRow(new Object[] {
                u.getSecuencial(), u.getCodigo(), u.getNombre(),
                u.getAcceso(), u.isActivo() ? "Sí" : "No",
                u.getPassword(), u.getImagen()
            });
        }

        if (tableUsuarios.getRowCount() > 0) {
            tableUsuarios.setRowSelectionInterval(0, 0);
        }

        // Ajustar ancho de columnas automáticamente
        TableColumnModel columnModel = tableUsuarios.getColumnModel();
        for (int col = 0; col < tableUsuarios.getColumnCount(); col++) {
            int ancho = 75;
            for (int fila = 0; fila < tableUsuarios.getRowCount(); fila++) {
                TableCellRenderer renderer = tableUsuarios.getCellRenderer(fila, col);
                Component comp = tableUsuarios.prepareRenderer(renderer, fila, col);
                ancho = Math.max(comp.getPreferredSize().width + 10, ancho);
            }
            columnModel.getColumn(col).setPreferredWidth(ancho);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage());
    } finally {
        em.close();
    }
}

    
    
//    private void cargarDatos() {
//    // Modelo no editable
//    DefaultTableModel model = new DefaultTableModel(
//        new String[] { "S", "Codigo", "Nombre", "Acceso", "Activo", "Password", "Imagen" }, 0
//    ) {
//        @Override
//        public boolean isCellEditable(int row, int column) {
//            return false;
//        }
//    };
//    tableUsuarios.setModel(model);
//
//    // Efecto zebra + centrado
//    tableUsuarios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value,
//                boolean isSelected, boolean hasFocus, int row, int column) {
//
//            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            setHorizontalAlignment(SwingConstants.CENTER);
//            c.setBackground(isSelected ? table.getSelectionBackground() :
//                (row % 2 == 0 ? Color.WHITE : new Color(224, 224, 224)));
//            return c;
//        }
//    });
//
//    // Ocultar columnas sensibles
//    for (int i = 5; i <= 6; i++) {
//        tableUsuarios.getColumnModel().getColumn(i).setMinWidth(0);
//        tableUsuarios.getColumnModel().getColumn(i).setMaxWidth(0);
//        tableUsuarios.getColumnModel().getColumn(i).setWidth(0);
//    }
//
//    // Cargar datos desde JPA
//    EntityManager em = Persistence.createEntityManagerFactory("MonituxPU").createEntityManager();
//    try {
//        List<Usuario> usuarios = em.createQuery(
//            "SELECT u FROM Usuario u WHERE u.secuencial_empresa = :empresa", Usuario.class)
//            .setParameter("empresa", 1)
//            .getResultList();
//
//        for (Usuario u : usuarios) {
//            model.addRow(new Object[] {
//                u.getSecuencial(), u.getCodigo(), u.getNombre(),
//                u.getAcceso(), u.isActivo() ? "Sí" : "No",
//                u.getPassword(), u.getImagen()
//            });
//        }
//
//        if (tableUsuarios.getRowCount() > 0) {
//            tableUsuarios.setRowSelectionInterval(0, 0);
//        }
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage());
//    } finally {
//        em.close();
//    }
//}
//
//    
    
    
    
    
   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        checkBoxActivo = new javax.swing.JCheckBox();
        txt_Codigo = new javax.swing.JTextField();
        txt_Nombre = new javax.swing.JTextField();
        comboBoxAcceso = new javax.swing.JComboBox<>();
        txt_Password = new javax.swing.JPasswordField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUsuarios = new javax.swing.JTable();
        labelImagen = new javax.swing.JLabel();
        labelImagen1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Menu_Nuevo = new javax.swing.JMenuItem();
        Menu_Guardar = new javax.swing.JMenuItem();
        Menu_Eliminar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Menu_Salir = new javax.swing.JMenuItem();

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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Usuarios");
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(35, 32, 40));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Codigo:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Nombre:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Password:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Acceso:");

        checkBoxActivo.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxActivo.setText("Activo");

        tableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableUsuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUsuariosMouseClicked(evt);
            }
        });
        tableUsuarios.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tableUsuariosPropertyChange(evt);
            }
        });
        tableUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableUsuariosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableUsuarios);

        labelImagen.setToolTipText("Click para escoger imagen.");
        labelImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelImagenMouseClicked(evt);
            }
        });

        labelImagen1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/camera.png"))); // NOI18N
        labelImagen1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelImagen1MouseClicked(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(35, 32, 40));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Buscar por:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_Password)
                                    .addComponent(txt_Nombre)
                                    .addComponent(txt_Codigo)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 5, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(checkBoxActivo)
                                        .addGap(110, 110, 110))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(comboBoxAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(labelImagen1)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 362, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboBoxAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkBoxActivo))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))))
                    .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Opciones");

        Menu_Nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/page_white.png"))); // NOI18N
        Menu_Nuevo.setText("Nuevo");
        Menu_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_NuevoActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Nuevo);

        Menu_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disk.png"))); // NOI18N
        Menu_Guardar.setText("Guardar");
        Menu_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_GuardarActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Guardar);

        Menu_Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exclamation.png"))); // NOI18N
        Menu_Eliminar.setText("Eliminar");
        Menu_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_EliminarActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Eliminar);
        jMenu1.add(jSeparator1);

        Menu_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/door_out.png"))); // NOI18N
        Menu_Salir.setText("Salir");
        Menu_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Menu_SalirActionPerformed(evt);
            }
        });
        jMenu1.add(Menu_Salir);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
       
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.black);
        
        // Establece el título del formulario
setTitle("Monitux-POS v." + "");//V_Menu_Principal.VER);

// Asigna el secuencial del usuario


//// Oculta o muestra el botón de eliminar según el rol
//if (!"Administrador".equals(V_Menu_Principal.Acceso_Usuario)) {
//    Menu_Eliminar.setVisible(false);
//} else {
//    Menu_Eliminar.setVisible(true);
//}

// Limpia la tabla
DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();
model.setRowCount(0);
this.setLocationRelativeTo(null);
// Carga los datos
cargarDatos();
primera_carga();

// Centra el contenido de las celdas
DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
for (int i = 0; i < tableUsuarios.getColumnCount(); i++) {
    tableUsuarios.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
}

// Llena el comboBox1
comboBoxAcceso.addItem("Vendedor");
comboBoxAcceso.addItem("Administrador");

comboBoxAcceso.setSelectedIndex(0);

// Hace la tabla de solo lectura
//tableUsuarios.setEnabled(false);

// Llena el comboBox2
jComboBox2.addItem("Codigo");
jComboBox2.addItem("Nombre");
jComboBox2.setSelectedIndex(0);

// Selecciona la primera fila si hay datos
if (tableUsuarios.getRowCount() > 0) {
    tableUsuarios.setRowSelectionInterval(0, 0);
    tableUsuarios.setColumnSelectionInterval(0, 0);

    // Simula el evento CellClick
   // tableUsuarios_CellClick(0, 0); // <- llama tu propio método
   //Ojo Aqui
}





// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed

       
        

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased


         Filtrar(jComboBox2.getSelectedItem().toString(), jTextField3.getText()); // Llama al método Filtrar con el valor del TextBox
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyReleased

    private void tableUsuariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableUsuariosKeyReleased
        // TODO add your handling code here:
        
        
    try {
        int rowIndex = tableUsuarios.getSelectedRow();
        if (rowIndex < 0) return; // No hay fila seleccionada

        DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();

        // Asignar valores si existen
        Object secuencialObj = model.getValueAt(rowIndex, 0); // Columna "S"
        if (secuencialObj != null) {
            this.Secuencial = Integer.parseInt(secuencialObj.toString());
        }

        txt_Codigo.setText(getCellValue(model, rowIndex, 1)); // "Código"
        txt_Nombre.setText(getCellValue(model, rowIndex, 2)); // "Nombre"

        String password = getCellValue(model, rowIndex, 5); // "Password"
        if (password != null && !password.isEmpty()) {
            txt_Password.setText(Encriptador.desencriptar(password));
        }

        String acceso = getCellValue(model, rowIndex, 3); // "Acceso"
        if (acceso != null) {
            for (int i = 0; i < comboBoxAcceso.getItemCount(); i++) {
                Object item = comboBoxAcceso.getItemAt(i);
                if (item != null && item.toString().contains(acceso)) {
                    comboBoxAcceso.setSelectedIndex(i);
                    break;
                }
            }
        }

        String activo = getCellValue(model, rowIndex, 4); // "Activo"
        checkBoxActivo.setSelected("Sí".equalsIgnoreCase(activo));

        // Cargar imagen desde la base de datos
        try (EntityManager em = Persistence.createEntityManagerFactory("MonituxPU").createEntityManager()) {
            Usuario usuario = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.Secuencial = :secuencial AND u.Secuencial_Empresa = :empresa", Usuario.class)
                .setParameter("secuencial", this.Secuencial)
                .setParameter("empresa", Secuencial_Empresa)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (usuario != null && usuario.getImagen() != null && usuario.getImagen().length > 0) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(usuario.getImagen())) {
                    BufferedImage img = ImageIO.read(bis);
                    labelImagen.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    labelImagen.setIcon(null);
                }
            } else {
                labelImagen.setIcon(null);
            }
        }

    } catch (Exception ex) {
        labelImagen.setIcon(null);
        ex.printStackTrace(); // Para depuración
    }
}

    
    private void Cargar_Datos_Fila_Actual(){
    
              
        
    try {
        int rowIndex = tableUsuarios.getSelectedRow();
        if (rowIndex < 0) return; // No hay fila seleccionada

        DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();

        // Asignar valores si existen
        Object secuencialObj = model.getValueAt(rowIndex, 0); // Columna "S"
        if (secuencialObj != null) {
            this.Secuencial = Integer.parseInt(secuencialObj.toString());
        }

        txt_Codigo.setText(getCellValue(model, rowIndex, 1)); // "Código"
        txt_Nombre.setText(getCellValue(model, rowIndex, 2)); // "Nombre"

        String password = getCellValue(model, rowIndex, 5); // "Password"
        if (password != null && !password.isEmpty()) {
            txt_Password.setText(Encriptador.desencriptar(password));
        }

        String acceso = getCellValue(model, rowIndex, 3); // "Acceso"
        if (acceso != null) {
            for (int i = 0; i < comboBoxAcceso.getItemCount(); i++) {
                Object item = comboBoxAcceso.getItemAt(i);
                if (item != null && item.toString().contains(acceso)) {
                    comboBoxAcceso.setSelectedIndex(i);
                    break;
                }
            }
        }

        String activo = getCellValue(model, rowIndex, 4); // "Activo"
        checkBoxActivo.setSelected("Sí".equalsIgnoreCase(activo));

        // Cargar imagen desde la base de datos
        try (EntityManager em = Persistence.createEntityManagerFactory("MonituxPU").createEntityManager()) {
            Usuario usuario = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.Secuencial = :secuencial AND u.Secuencial_Empresa = :empresa", Usuario.class)
                .setParameter("secuencial", this.Secuencial)
                .setParameter("empresa", Secuencial_Empresa)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (usuario != null && usuario.getImagen() != null && usuario.getImagen().length > 0) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(usuario.getImagen())) {
                    BufferedImage img = ImageIO.read(bis);
                    labelImagen.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    labelImagen.setIcon(null);
                }
            } else {
                labelImagen.setIcon(null);
            }
        }

    } catch (Exception ex) {
        labelImagen.setIcon(null);
        ex.printStackTrace(); // Para depuración
    }
    
    }
    
    
  // Método auxiliar para obtener valor seguro de celda
private String getCellValue(DefaultTableModel model, int row, int col) {
    Object value = model.getValueAt(row, col);
    return value != null ? value.toString() : "";
        
    }//GEN-LAST:event_tableUsuariosKeyReleased


 private void primera_carga(){
 
     
         
          try {
        int rowIndex = 0;
        

        DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();

        // Asignar valores si existen
        Object secuencialObj = model.getValueAt(rowIndex, 0); // Columna "S"
        if (secuencialObj != null) {
            this.Secuencial = Integer.parseInt(secuencialObj.toString());
        }

        txt_Codigo.setText(getCellValue(model, rowIndex, 1)); // "Código"
        txt_Nombre.setText(getCellValue(model, rowIndex, 2)); // "Nombre"

        String password = getCellValue(model, rowIndex, 5); // "Password"
        if (password != null && !password.isEmpty()) {
            txt_Password.setText(Encriptador.desencriptar(password));
        }

        String acceso = getCellValue(model, rowIndex, 3); // "Acceso"
        if (acceso != null) {
            for (int i = 0; i < comboBoxAcceso.getItemCount(); i++) {
                Object item = comboBoxAcceso.getItemAt(i);
                if (item != null && item.toString().contains(acceso)) {
                    comboBoxAcceso.setSelectedIndex(i);
                    break;
                }
            }
        }

        String activo = getCellValue(model, rowIndex, 4); // "Activo"
        checkBoxActivo.setSelected("Sí".equalsIgnoreCase(activo));

        // Cargar imagen desde la base de datos
        try (EntityManager em = Persistence.createEntityManagerFactory("MonituxPU").createEntityManager()) {
            Usuario usuario = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.Secuencial = :secuencial AND u.Secuencial_Empresa = :empresa", Usuario.class)
                .setParameter("secuencial", this.Secuencial)
                .setParameter("empresa", Secuencial_Empresa)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (usuario != null && usuario.getImagen() != null && usuario.getImagen().length > 0) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(usuario.getImagen())) {
                    BufferedImage img = ImageIO.read(bis);
                    labelImagen.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    labelImagen.setIcon(null);
                }
            } else {
                labelImagen.setIcon(null);
            }
        }

    } catch (Exception ex) {
        labelImagen.setIcon(null);
        ex.printStackTrace(); // Para depuración
    }
        
        
 
 
 }




    private void tableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUsuariosMouseClicked

        
        
          try {
        int rowIndex = tableUsuarios.getSelectedRow();
        if (rowIndex < 0) return; // No hay fila seleccionada

        DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();

        // Asignar valores si existen
        Object secuencialObj = model.getValueAt(rowIndex, 0); // Columna "S"
        if (secuencialObj != null) {
            this.Secuencial = Integer.parseInt(secuencialObj.toString());
        }

        txt_Codigo.setText(getCellValue(model, rowIndex, 1)); // "Código"
        txt_Nombre.setText(getCellValue(model, rowIndex, 2)); // "Nombre"

        String password = getCellValue(model, rowIndex, 5); // "Password"
        if (password != null && !password.isEmpty()) {
            txt_Password.setText(Encriptador.desencriptar(password));
        }

        String acceso = getCellValue(model, rowIndex, 3); // "Acceso"
        if (acceso != null) {
            for (int i = 0; i < comboBoxAcceso.getItemCount(); i++) {
                Object item = comboBoxAcceso.getItemAt(i);
                if (item != null && item.toString().contains(acceso)) {
                    comboBoxAcceso.setSelectedIndex(i);
                    break;
                }
            }
        }

        String activo = getCellValue(model, rowIndex, 4); // "Activo"
        checkBoxActivo.setSelected("Sí".equalsIgnoreCase(activo));

        // Cargar imagen desde la base de datos
        try (EntityManager em = Persistence.createEntityManagerFactory("MonituxPU").createEntityManager()) {
            Usuario usuario = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.Secuencial = :secuencial AND u.Secuencial_Empresa = :empresa", Usuario.class)
                .setParameter("secuencial", this.Secuencial)
                .setParameter("empresa", Secuencial_Empresa)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (usuario != null && usuario.getImagen() != null && usuario.getImagen().length > 0) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(usuario.getImagen())) {
                    BufferedImage img = ImageIO.read(bis);
                    labelImagen.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    labelImagen.setIcon(null);
                }
            } else {
                labelImagen.setIcon(null);
            }
        }

    } catch (Exception ex) {
        labelImagen.setIcon(null);
        ex.printStackTrace(); // Para depuración
    }
        
        

        // TODO add your handling code here:
    }//GEN-LAST:event_tableUsuariosMouseClicked

    private void labelImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelImagenMouseClicked

/*
        try {
    String imagenSeleccionada = Util.abrirDialogoSeleccionFilename();

    if (imagenSeleccionada != null && !imagenSeleccionada.trim().isEmpty()) {
        File archivoImagen = new File(imagenSeleccionada);

        if (archivoImagen.exists() && archivoImagen.isFile()) {
            // Cargar imagen para mostrar en el JLabel
            Image imagenCargada = Util.cargarImagenLocal(imagenSeleccionada);
            labelImagen.setIcon(new ImageIcon(imagenCargada));

            // Convertir a byte[] para guardar
            BufferedImage buffered = ImageIO.read(archivoImagen);
            if (buffered != null) {
                ByteArrayOutputStream ms = new ByteArrayOutputStream();
                ImageIO.write(buffered, "png", ms);
                imagen = ms.toByteArray();
            } else {
                imagen = null; // Imagen no válida
                System.err.println("Formato de imagen no soportado.");
            }
        } else {
            imagen = null;
            System.err.println("Archivo no encontrado o invalido: " + imagenSeleccionada);
        }
    }
} catch (IOException e) {
    imagen = null;
    e.printStackTrace(); // Para depuración
    // VMenuPrincipal.MSG.showMSG("No se pudo cargar la imagen seleccionada.", "Error");
}

     */




try {
    String imagenSeleccionada = Util.abrirDialogoSeleccionFilename();

    if (imagenSeleccionada != null && !imagenSeleccionada.trim().isEmpty()) {
        File archivoImagen = new File(imagenSeleccionada);

        if (archivoImagen.exists() && archivoImagen.isFile()) {
            // Cargar imagen como BufferedImage
            BufferedImage buffered = ImageIO.read(archivoImagen);

            if (buffered != null) {
                // Redimensionar al tamaño del JLabel
                int ancho = labelImagen.getWidth();
                int alto = labelImagen.getHeight();

                Image imagenEscalada = buffered.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

                // Mostrar imagen redimensionada en el JLabel
                labelImagen.setIcon(new ImageIcon(imagenEscalada));

                // Convertir imagen original (no escalada) a byte[] para guardar
                ByteArrayOutputStream ms = new ByteArrayOutputStream();
                ImageIO.write(buffered, "png", ms);
                imagen = ms.toByteArray();
            } else {
                imagen = null;
                System.err.println("Formato de imagen no soportado.");
            }
        } else {
            imagen = null;
            System.err.println("Archivo no encontrado o inválido: " + imagenSeleccionada);
        }
    }
} catch (IOException e) {
    imagen = null;
    e.printStackTrace();
    // VMenuPrincipal.MSG.showMSG("No se pudo cargar la imagen seleccionada.", "Error");
}




        // TODO add your handling code here:
    }//GEN-LAST:event_labelImagenMouseClicked

    private void Menu_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_NuevoActionPerformed

        Secuencial=0;
        txt_Codigo.setText("");
        txt_Nombre.setText("");
        txt_Password.setText("");
        checkBoxActivo.setSelected(true);
        labelImagen.setIcon(null);
        
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_NuevoActionPerformed

    private void Menu_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_GuardarActionPerformed


        //**********************************************************
         
        // Obtener imagen como byte[]
byte[] imagenBytes = null;
Icon icono = labelImagen.getIcon();
if (icono instanceof ImageIcon) {
    Image imagen = ((ImageIcon) icono).getImage();
    BufferedImage copia = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = copia.createGraphics();
    g2d.drawImage(imagen, 0, 0, null);
    g2d.dispose();

    imagenBytes = Util.comprimirImagen(copia, 100f); // Calidad ajustable
}

// Validaciones comunes
if (txt_Nombre.getText().isBlank()) {
    JOptionPane.showMessageDialog(null,"El nombre del usuario no puede estar vacío.");
    return;
}
if (txt_Codigo.getText().isBlank()) {
    JOptionPane.showMessageDialog(null,"El código de usuario no puede estar vacío.");
    return;
}
if (txt_Password.getText().isBlank()) {
    JOptionPane.showMessageDialog(null,"El password no puede estar vacío.");
    return;
}

EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
EntityManager em = emf.createEntityManager();

if (Secuencial != 0) {
    // MODO EDICIÓN
    Usuario usuario = em.find(Usuario.class, Secuencial);
    if (usuario != null) {
        em.getTransaction().begin();
        usuario.setSecuencial_Empresa(Secuencial_Empresa);
        usuario.setNombre(txt_Nombre.getText());
        usuario.setCodigo(txt_Codigo.getText());
        usuario.setPassword(Encriptador.encriptar(txt_Password.getText()));
        usuario.setAcceso(comboBoxAcceso.getSelectedItem() != null ? comboBoxAcceso.getSelectedItem().toString() : "Sin Tipo");
        usuario.setActivo(checkBoxActivo.isSelected());
        if (imagenBytes != null) {
            usuario.setImagen(imagenBytes);
        }
        em.getTransaction().commit();

        Util.registrarActividad(Secuencial_Usuario, "Ha modificado al usuario: " + usuario.getNombre(), Secuencial_Empresa);
       JOptionPane.showMessageDialog(null,"Usuario actualizado correctamente.");
    }
} else {
    // MODO CREACIÓN
    if (comboBoxAcceso.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null,"Debe seleccionar un acceso de usuario.");
        return;
    }

    Usuario usuario = new Usuario();
    usuario.setSecuencial_Empresa(Secuencial_Empresa);
    usuario.setNombre(txt_Nombre.getText());
    usuario.setCodigo(txt_Codigo.getText());
    usuario.setPassword(Encriptador.encriptar(txt_Password.getText()));
    usuario.setAcceso(comboBoxAcceso.getSelectedItem() != null ? comboBoxAcceso.getSelectedItem().toString() : "Sin Tipo");
    usuario.setActivo(true);
    usuario.setImagen(imagenBytes);

    try {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

       JOptionPane.showMessageDialog(null,"Usuario creado correctamente.");
        Util.registrarActividad(Secuencial_Usuario, "Ha creado al usuario: " + usuario.getNombre(), Secuencial_Empresa);
    } catch (Exception e) {
       JOptionPane.showMessageDialog(null,"Error al crear usuario: Ya existe o los datos proporcionados no son válidos.");
        em.getTransaction().rollback();
        return;
    }
}

em.close();
emf.close();


dispose();     // Cierra el formulario actual

        
        
        
          
        //**********************************************************


        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_GuardarActionPerformed

    private void Menu_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_EliminarActionPerformed


        
        int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);

if (res == JOptionPane.YES_OPTION) {
    try {
        Icon icono = labelImagen.getIcon();
        if (icono instanceof ImageIcon) {
            ((ImageIcon) icono).getImage().flush(); // Libera recursos
        }
        labelImagen.setIcon(null);
        imagen = null; // Variable de clase si la usas
    } catch (Exception e) {
        // Silenciar errores de liberación de imagen
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        Usuario usuario = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.secuencial = :id AND u.secuencial_empresa = :empresa", Usuario.class)
            .setParameter("id", this.Secuencial)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (usuario != null) {
            em.getTransaction().begin();
            em.remove(usuario);
            em.getTransaction().commit();

            Util.registrarActividad(Secuencial_Usuario, "Ha eliminado al usuario: " + usuario.getNombre(), Secuencial_Empresa);
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    } finally {
        em.close();
        emf.close();
    }
    
}




        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_EliminarActionPerformed

    private void Menu_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_SalirActionPerformed

        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_SalirActionPerformed

    private void labelImagen1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelImagen1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_labelImagen1MouseClicked

    private void tableUsuariosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tableUsuariosPropertyChange

    

        // TODO add your handling code here:
    }//GEN-LAST:event_tableUsuariosPropertyChange

    
    
    
// Método auxiliar para obtener el valor de celda como String



    
    private void Filtrar(String campo, String valor) {

        tableUsuarios.removeAll();
// Crear modelo si la tabla está vacía
    DefaultTableModel model = (DefaultTableModel) tableUsuarios.getModel();
    if (tableUsuarios.getColumnCount() == 0) {
        model.setColumnIdentifiers(new String[] { "S", "Codigo", "Nombre", "Acceso", "Activo" });

        // Ocultar columna Password
        TableColumn passwordCol = tableUsuarios.getColumnModel().getColumn(3);
        passwordCol.setMinWidth(0);
        passwordCol.setMaxWidth(0);
        passwordCol.setWidth(0);

        // Ajustar anchos
        tableUsuarios.getColumnModel().getColumn(0).setPreferredWidth(20);  // S
        tableUsuarios.getColumnModel().getColumn(1).setPreferredWidth(80);  // Código
        tableUsuarios.getColumnModel().getColumn(2).setPreferredWidth(80);  // Nombre
    }

    // Limpiar filas existentes
    model.setRowCount(0);

    // Conectar con JPA y filtrar usuarios
    EntityManager em = Persistence.createEntityManagerFactory("MonituxPU").createEntityManager();
    try {
       String jpql = "SELECT u FROM Usuario u WHERE u.Secuencial_Empresa = :empresa AND FUNCTION('REPLACE', u." + campo + ", ' ', '') LIKE :valor";

        List<Usuario> usuarios = em.createQuery(jpql, Usuario.class)
            .setParameter("empresa", Secuencial_Empresa)
            .setParameter("valor", "%" + valor.replace(" ", "") + "%")
            .getResultList();

//        for (Usuario u : usuarios) {
//            model.addRow(new Object[] {
//                u.getSecuencial(),
//                u.getCodigo(),
//                u.getNombre(),
//                u.getAcceso(),
//                u.isActivo() ? "Sí" : "No"
//            });

for (Usuario u : usuarios) {
            model.addRow(new Object[] {
                u.getSecuencial(), u.getCodigo(), u.getNombre(),
                u.getAcceso(), u.isActivo() ? "Sí" : "No",
                u.getPassword(), u.getImagen()
            });
        }


        

        tableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (tableUsuarios.getRowCount() > 0) {
            tableUsuarios.setRowSelectionInterval(0, 0);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al filtrar usuarios: " + e.getMessage());
        System.err.println(e.getMessage());
    } finally {
        em.close();
    }
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
            java.util.logging.Logger.getLogger(V_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(V_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(V_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(V_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new V_Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Menu_Eliminar;
    private javax.swing.JMenuItem Menu_Guardar;
    private javax.swing.JMenuItem Menu_Nuevo;
    private javax.swing.JMenuItem Menu_Salir;
    private javax.swing.JCheckBox checkBoxActivo;
    private javax.swing.JComboBox<String> comboBoxAcceso;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelImagen1;
    private javax.swing.JTable tableUsuarios;
    private javax.swing.JTextField txt_Codigo;
    private javax.swing.JTextField txt_Nombre;
    private javax.swing.JPasswordField txt_Password;
    // End of variables declaration//GEN-END:variables
}
