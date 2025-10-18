/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.AppSettings;
import com.monituxpos.Clases.DBProvider;
import com.monituxpos.Clases.Empresa;
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.hibernate.Metamodel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Empresa extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(V_Empresa.class.getName());

    public int Secuencial;
    public int Secuencial_Usuario;
    public int suma=0;
    public String Pin="****";
    private byte[] imagen;
    public boolean isAdmin=false;
   
    /**
     * Creates new form V_Empresa
     */
    public V_Empresa() {
        
        initComponents();
        this.getContentPane().setBackground(Color.black);
        this.setLocationRelativeTo(null);
        

        if (AppSettings.getPrimer_Arranque()){
String proveedor = AppSettings.getDB_Provider();
String conexion = AppSettings.getDB_Connection();
String usuario = AppSettings.getUsuario();
String password = AppSettings.getPassword();

       
MonituxDBContext.init(
    DBProvider.valueOf(proveedor.toUpperCase()),
    conexion,
    usuario,
    password
);
        }  
        
        
        
    }

    
    private void primera_carga() {
    try {
        int rowIndex = 0;

        DefaultTableModel model = (DefaultTableModel) tableEmpresas.getModel();

        Object secuencialObj = model.getValueAt(rowIndex, 0); // Columna "S"
        if (secuencialObj != null) {
            this.Secuencial = Integer.parseInt(secuencialObj.toString());
        }

        txt_Nombre.setText(getCellValue(model, rowIndex, 1));     // "Nombre"
        txt_Direccion.setText(getCellValue(model, rowIndex, 2));  // "Dirección"
        txt_Telefono.setText(getCellValue(model, rowIndex, 3));   // "Teléfono"
        txt_Email.setText(getCellValue(model, rowIndex, 4));      // "Email"
        txt_ISV.setText(getCellValue(model, rowIndex, 6));        // "ISV"
        txt_RSS.setText(getCellValue(model, rowIndex, 9));        // "RSS"

        // Moneda: buscar por prefijo
        Object valorMonedaObj = model.getValueAt(rowIndex, 5); // "Moneda"
        if (valorMonedaObj != null) {
            String valorMoneda = valorMonedaObj.toString().trim();
            Object itemEncontrado = null;

            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                Object item = jComboBox1.getItemAt(i);
                if (item != null && item.toString().startsWith(valorMoneda)) {
                    itemEncontrado = item;
                    break;
                }
            }

            jComboBox1.setSelectedItem(itemEncontrado != null ? itemEncontrado : jComboBox1.getItemAt(0));
        }

        String activa = getCellValue(model, rowIndex, 7); // "Activa"
        checkBoxActivo.setSelected("Sí".equalsIgnoreCase(activa));

        // Cargar imagen desde la base de datos
        EntityManager em = null;
        try {
            em = MonituxDBContext.getEntityManager();

            Empresa empresa = em.createQuery(
                "SELECT e FROM Empresa e WHERE e.Secuencial = :secuencial", Empresa.class)
                .setParameter("secuencial", this.Secuencial)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (empresa != null && empresa.getImagen() != null && empresa.getImagen().length > 0) {
                try (ByteArrayInputStream bis = new ByteArrayInputStream(empresa.getImagen())) {
                    BufferedImage img = ImageIO.read(bis);
                    labelImagen.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    labelImagen.setIcon(null);
                }
            } else {
                labelImagen.setIcon(null);
            }

        } finally {
            // No hay recursos explícitos que cerrar aquí
        }

    } catch (Exception ex) {
        labelImagen.setIcon(null);
        ex.printStackTrace(); // Para depuración
    }
}

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_Nombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_Direccion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Telefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txt_ISV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_RSS = new javax.swing.JTextField();
        labelImagen = new javax.swing.JLabel();
        labelImagen1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTextField7 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableEmpresas = new javax.swing.JTable();
        checkBoxActivo = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Menu_Nuevo = new javax.swing.JMenuItem();
        Menu_Guardar = new javax.swing.JMenuItem();
        Menu_Eliminar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Menu_Salir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Empresas");
        jLabel1.setOpaque(true);
        jLabel1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jLabel1MouseWheelMoved(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(35, 32, 40));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Direccion:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Telefono:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Email:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Moneda:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "L - Lempira Hondureño", "$ - Dólar Estadounidense", "€ - Euro" }));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ISV:");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("%");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("RSS:");

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

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Buscar por:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tableEmpresas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableEmpresas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEmpresasMouseClicked(evt);
            }
        });
        tableEmpresas.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tableEmpresasPropertyChange(evt);
            }
        });
        tableEmpresas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableEmpresasKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableEmpresas);

        checkBoxActivo.setBackground(new java.awt.Color(35, 32, 40));
        checkBoxActivo.setForeground(new java.awt.Color(255, 255, 255));
        checkBoxActivo.setText("Activa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel2))
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_Direccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                .addComponent(txt_Nombre, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_RSS)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_ISV, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(checkBoxActivo))
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelImagen1)
                                .addGap(47, 47, 47))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox1, txt_Email, txt_Telefono});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_Telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(txt_ISV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                            .addComponent(checkBoxActivo)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(labelImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_RSS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Menu_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_NuevoActionPerformed

        Secuencial=0;
        txt_Nombre.setText("");
        txt_Direccion.setText("");
        txt_Telefono.setText("");
        txt_Email.setText("");
        //combo moneda
        txt_ISV.setText("0");
        txt_RSS.setText("");
        checkBoxActivo.setSelected(true);
        labelImagen.setIcon(null);

        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_NuevoActionPerformed

    private void Menu_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_GuardarActionPerformed

       
        
        byte[] imagenBytes = null;

    // Obtener imagen del label
    Icon icono = labelImagen.getIcon();
    if (icono instanceof ImageIcon) {
        Image imagen = ((ImageIcon) icono).getImage();
        BufferedImage copia = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = copia.createGraphics();
        g2d.drawImage(imagen, 0, 0, null);
        g2d.dispose();
        imagenBytes = Util.comprimirImagen(copia, 100f);

        System.out.println("🖼️ Imagen comprimida: " + imagenBytes.length + " bytes");
    }

    // Validaciones básicas
    if (txt_Nombre.getText().isBlank()) {
        JOptionPane.showMessageDialog(null, "El nombre de la empresa no puede estar vacío.");
        return;
    }
    if (txt_Direccion.getText().isBlank()) {
        JOptionPane.showMessageDialog(null, "La dirección no puede estar vacía.");
        return;
    }

    MonituxDBContext.ensureEntityManagerFactoryReady();
EntityManager em = MonituxDBContext.getEntityManager();


   // EntityManager em = MonituxDBContext.getEntityManager();
    if (em == null || !em.isOpen()) {
        JOptionPane.showMessageDialog(null, "❌ EntityManager no disponible.");
        return;
    }

    try {
        em.getTransaction().begin();

        if (Secuencial != 0) {
            // MODO EDICIÓN
            Empresa empresa = em.find(Empresa.class, Secuencial);
            if (empresa == null) {
                throw new IllegalStateException("Empresa no encontrada con ID: " + Secuencial);
            }

            empresa.setNombre(txt_Nombre.getText());
            empresa.setDireccion(txt_Direccion.getText());
            empresa.setTelefono(txt_Telefono.getText());
            empresa.setEmail(txt_Email.getText());
            empresa.setMoneda(jComboBox1.getSelectedItem().toString().split("-")[0].trim());
            if (!txt_ISV.getText().isEmpty()){
            empresa.setISV(new BigDecimal(txt_ISV.getText()));
            }
            empresa.setActiva(checkBoxActivo.isSelected());
            empresa.setRSS(txt_RSS.getText());
            empresa.setSecuencial_Usuario(Secuencial_Usuario);
            if (imagenBytes != null) {
                empresa.setImagen(imagenBytes);
            }

            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "Empresa actualizada correctamente.");
            Util.registrarActividad(Secuencial_Usuario, "Ha modificado la empresa: " + empresa.getNombre(), Secuencial);

        } else {
            // MODO CREACIÓN
            Empresa empresa = new Empresa();
            empresa.setNombre(txt_Nombre.getText());
            empresa.setDireccion(txt_Direccion.getText());
            empresa.setTelefono(txt_Telefono.getText());
            empresa.setEmail(txt_Email.getText());
            empresa.setMoneda(jComboBox1.getSelectedItem().toString().split("-")[0].trim());
            if (!txt_ISV.getText().isEmpty()){
            empresa.setISV(new BigDecimal(txt_ISV.getText()));
            }
            
            empresa.setActiva(true);
            empresa.setRSS(txt_RSS.getText());
            empresa.setSecuencial_Usuario(Secuencial_Usuario);
            //empresa.setImagen(imagenBytes);
empresa.setImagen(imagenBytes != null ? imagenBytes : new byte[0]);

            em.persist(empresa);
            em.getTransaction().commit();

            System.out.println("🆔 Empresa creada con ID: " + empresa.getSecuencial());
            JOptionPane.showMessageDialog(null, "Empresa creada correctamente.");
            Util.registrarActividad(Secuencial_Usuario, "Ha creado la empresa: " + empresa.getNombre(), empresa.getSecuencial());
            AppSettings.set_Empresa_Creada(true);

            if (AppSettings.getPrimer_Arranque()) {
                new V_Login().setVisible(true);
                AppSettings.set_Usuario_Creado(false);
            } else {
                this.dispose();
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "❌ Error al guardar empresa: " + e.getMessage());
        e.printStackTrace();
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
      
    }//GEN-LAST:event_Menu_GuardarActionPerformed

    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new V_Empresa().setVisible(true));
    }
    
    private void Menu_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_EliminarActionPerformed

      
        
        
        String respuesta = JOptionPane.showInputDialog(null, "Ingrese el Pin", "Usuario Administrador", JOptionPane.QUESTION_MESSAGE);
            if (respuesta != null) {
                if ("****".equals(respuesta) && "****".equals(Pin)) {
                    JOptionPane.showMessageDialog(null, "Debe generar un PIN para eliminar empresa.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (respuesta.equals(Pin) && !"****".equals(Pin)) {
                    
                    
                    //*******************************************
                    
                     //**************************
        
        
    int res = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta empresa?", "Confirmación", JOptionPane.YES_NO_OPTION);

    if (res == JOptionPane.YES_OPTION) {
        try {
            Icon icono = labelImagen.getIcon();
            if (icono instanceof ImageIcon) {
                ((ImageIcon) icono).getImage().flush(); // Libera recursos
            }
            labelImagen.setIcon(null);
            imagen = null;
        } catch (Exception e) {
            // Silenciar errores de liberación de imagen
        }

        EntityManager em = MonituxDBContext.getEntityManager();
        if (em == null || !em.isOpen()) {
            JOptionPane.showMessageDialog(null, "Error: El EntityManager está cerrado. No se puede continuar.");
            return;
        }

        try {
            Empresa empresa = em.createQuery(
                "SELECT e FROM Empresa e WHERE e.Secuencial = :id", Empresa.class)
                .setParameter("id", this.Secuencial)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (empresa != null) {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.remove(empresa);
                tx.commit();

                Util.registrarActividad(Secuencial_Usuario, "Ha eliminado la empresa: " + empresa.getNombre(), Secuencial);
                JOptionPane.showMessageDialog(null, "Empresa eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Empresa no encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            EntityTransaction tx = em.getTransaction();
            if (tx != null && tx.isActive()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    // Silenciar rollback fallido
                }
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar empresa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
        //**************************
                    
                    //*******************************************
                    
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Pin incorrecto. No se ha eliminado la empresa.", "Información", JOptionPane.INFORMATION_MESSAGE);
                   isAdmin=false;
                   jLabel1.setText("Empresas");
                   jLabel1.setForeground(Color.white);
                   Pin="****";
                   
                    return;
                }
            }
        
        
        
        
        
        
        
       

        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_EliminarActionPerformed

    private void Menu_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Menu_SalirActionPerformed

        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_Menu_SalirActionPerformed

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

    private void labelImagen1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelImagen1MouseClicked

        // Abrir ventana de captura
        V_Captura_Imagen ventanaCamara = new V_Captura_Imagen(Secuencial, "Capturando...");
        ventanaCamara.setModal(true);
        ventanaCamara.setVisible(true);

        // Obtener imagen capturada
        BufferedImage imagenCapturada = V_Captura_Imagen.getImagen();

        if (imagenCapturada != null) {
            // Liberar imagen anterior si existe
            if (labelImagen.getIcon() != null) {
                labelImagen.setIcon(null);
            }

            // Mostrar imagen redimensionada en JLabel
            Image imagenEscalada = imagenCapturada.getScaledInstance(
                labelImagen.getWidth(),
                labelImagen.getHeight(),
                Image.SCALE_SMOOTH
            );
            labelImagen.setIcon(new ImageIcon(imagenEscalada));

            // Convertir a byte[] (sin compresión personalizada)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(imagenCapturada, "png", baos); // Usa "jpg" si deseas compresión
                imagen = baos.toByteArray(); // Variable byte[] para guardar la imagen
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al procesar la imagen capturada.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "No se pudo capturar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_labelImagen1MouseClicked

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased

        
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String filtroSeleccionado = jComboBox2.getSelectedItem().toString();
        String valorFiltro = jTextField7.getText();

        Filtrar(filtroSeleccionado, valorFiltro); // Ejecuta solo al presionar Enter
    }
              
       
    }//GEN-LAST:event_jTextField7KeyReleased

    private void tableEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEmpresasMouseClicked

        
       try {
        int rowIndex = tableEmpresas.getSelectedRow();
        if (rowIndex < 0) return;

        DefaultTableModel model = (DefaultTableModel) tableEmpresas.getModel();

        Object secuencialObj = model.getValueAt(rowIndex, 0); // Columna "S"
        if (secuencialObj != null) {
            this.Secuencial = Integer.parseInt(secuencialObj.toString());
        }

        txt_Nombre.setText(getCellValue(model, rowIndex, 1));     // "Nombre"
        txt_Direccion.setText(getCellValue(model, rowIndex, 2));  // "Dirección"
        txt_Telefono.setText(getCellValue(model, rowIndex, 3));   // "Teléfono"
        txt_Email.setText(getCellValue(model, rowIndex, 4));      // "Email"
        txt_ISV.setText(getCellValue(model, rowIndex, 6));        // "ISV"
        txt_RSS.setText(getCellValue(model, rowIndex, 9));        // "RSS"

        // Moneda: buscar por prefijo
        Object valorCelda = model.getValueAt(rowIndex, 5); // "Moneda"
        if (valorCelda != null) {
            String valorMoneda = valorCelda.toString().trim();
            Object itemEncontrado = null;

            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                Object item = jComboBox1.getItemAt(i);
                if (item != null && item.toString().startsWith(valorMoneda)) {
                    itemEncontrado = item;
                    break;
                }
            }

            jComboBox1.setSelectedItem(itemEncontrado != null ? itemEncontrado : jComboBox1.getItemAt(0));
        }

        // Estado activo
        String activa = getCellValue(model, rowIndex, 7); // "Activa"
        checkBoxActivo.setSelected("Sí".equalsIgnoreCase(activa));

        // Cargar imagen desde base de datos
        EntityManager em = MonituxDBContext.getEntityManager();
        Empresa empresa = em.createQuery(
            "SELECT e FROM Empresa e WHERE e.Secuencial = :secuencial", Empresa.class)
            .setParameter("secuencial", this.Secuencial)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (empresa != null && empresa.getImagen() != null && empresa.getImagen().length > 0) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(empresa.getImagen())) {
                BufferedImage img = ImageIO.read(bis);
                labelImagen.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
                labelImagen.setIcon(null);
            }
        } else {
            labelImagen.setIcon(null);
        }

    } catch (Exception ex) {
        labelImagen.setIcon(null);
        ex.printStackTrace();
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_tableEmpresasMouseClicked

    private void tableEmpresasPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tableEmpresasPropertyChange

        // TODO add your handling code here:
    }//GEN-LAST:event_tableEmpresasPropertyChange

    private void tableEmpresasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableEmpresasKeyReleased

          try {
        int rowIndex = tableEmpresas.getSelectedRow();
        if (rowIndex < 0) return;

        DefaultTableModel model = (DefaultTableModel) tableEmpresas.getModel();

        Object secuencialObj = model.getValueAt(rowIndex, 0); // Columna "S"
        if (secuencialObj != null) {
            this.Secuencial = Integer.parseInt(secuencialObj.toString());
        }

        txt_Nombre.setText(getCellValue(model, rowIndex, 1));     // "Nombre"
        txt_Direccion.setText(getCellValue(model, rowIndex, 2));  // "Dirección"
        txt_Telefono.setText(getCellValue(model, rowIndex, 3));   // "Teléfono"
        txt_Email.setText(getCellValue(model, rowIndex, 4));      // "Email"
        txt_ISV.setText(getCellValue(model, rowIndex, 6));        // "ISV"
        txt_RSS.setText(getCellValue(model, rowIndex, 9));        // "RSS"

        // Moneda: buscar por prefijo
        Object valorCelda = model.getValueAt(rowIndex, 5); // "Moneda"
        if (valorCelda != null) {
            String valorMoneda = valorCelda.toString().trim();
            Object itemEncontrado = null;

            for (int i = 0; i < jComboBox1.getItemCount(); i++) {
                Object item = jComboBox1.getItemAt(i);
                if (item != null && item.toString().startsWith(valorMoneda)) {
                    itemEncontrado = item;
                    break;
                }
            }

            jComboBox1.setSelectedItem(itemEncontrado != null ? itemEncontrado : jComboBox1.getItemAt(0));
        }

        // Estado activo
        String activa = getCellValue(model, rowIndex, 7); // "Activa"
        checkBoxActivo.setSelected("Sí".equalsIgnoreCase(activa));

        // Cargar imagen desde base de datos
        EntityManager em = MonituxDBContext.getEntityManager();
        Empresa empresa = em.createQuery(
            "SELECT e FROM Empresa e WHERE e.Secuencial = :secuencial", Empresa.class)
            .setParameter("secuencial", this.Secuencial)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (empresa != null && empresa.getImagen() != null && empresa.getImagen().length > 0) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(empresa.getImagen())) {
                BufferedImage img = ImageIO.read(bis);
                labelImagen.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
                labelImagen.setIcon(null);
            }
        } else {
            labelImagen.setIcon(null);
        }

    } catch (Exception ex) {
        labelImagen.setIcon(null);
        ex.printStackTrace();
    }
  
       
        }

        private void Cargar_Datos_Fila_Actual() {
           
        }

        // Método auxiliar para obtener valor seguro de celda
        private String getCellValue(DefaultTableModel model, int row, int col) {
            Object value = model.getValueAt(row, col);
            return value != null ? value.toString() : "";

    }//GEN-LAST:event_tableEmpresasKeyReleased

        
        
        private void cargarEmpresas() {
    DefaultTableModel model = new DefaultTableModel(
        new String[] { "S", "Nombre", "Direccion", "Telefono", "Email", "Moneda", "ISV", "Activa", "Imagen", "RSS" }, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    tableEmpresas.setModel(model);
    tableEmpresas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tableEmpresas.setRowHeight(24);
    tableEmpresas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    tableEmpresas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    tableEmpresas.setGridColor(Color.LIGHT_GRAY);

    tableEmpresas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

    // Ocultar columnas sensibles (Imagen, RSS)
    for (int i : new int[] {8, 9}) {
        tableEmpresas.getColumnModel().getColumn(i).setMinWidth(0);
        tableEmpresas.getColumnModel().getColumn(i).setMaxWidth(0);
        tableEmpresas.getColumnModel().getColumn(i).setWidth(0);
    }

    EntityManager em = null;
    try {
        em = MonituxDBContext.getEntityManager();

        List<Empresa> empresas = em.createQuery("SELECT e FROM Empresa e", Empresa.class).getResultList();

        for (Empresa e : empresas) {
            model.addRow(new Object[] {
                e.getSecuencial(), e.getNombre(), e.getDireccion(), e.getTelefono(),
                e.getEmail(), e.getMoneda(), e.getISV(), e.isActiva() ? "Sí" : "No",
                e.getImagen(), e.getRSS()
            });
        }

        if (tableEmpresas.getRowCount() > 0) {
            tableEmpresas.setRowSelectionInterval(0, 0);
        }

        TableColumnModel columnModel = tableEmpresas.getColumnModel();
        for (int col = 0; col < tableEmpresas.getColumnCount(); col++) {
            int ancho = 75;
            for (int fila = 0; fila < tableEmpresas.getRowCount(); fila++) {
                TableCellRenderer renderer = tableEmpresas.getCellRenderer(fila, col);
                Component comp = tableEmpresas.prepareRenderer(renderer, fila, col);
                ancho = Math.max(comp.getPreferredSize().width + 10, ancho);
            }
            columnModel.getColumn(col).setPreferredWidth(ancho);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar empresas: " + e.getMessage());
        System.err.println("Error al cargar empresas: " + e.getMessage());
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

        
        private void Filtrar(String campo, String valor) {
    tableEmpresas.removeAll();

    DefaultTableModel model = (DefaultTableModel) tableEmpresas.getModel();
    if (tableEmpresas.getColumnCount() == 0) {
        model.setColumnIdentifiers(new String[] {
            "S", "Nombre", "Dirección", "Teléfono", "Email", "Moneda", "ISV", "Activa", "Imagen", "RSS"
        });

        // Ocultar columnas sensibles
        for (int i : new int[] {8, 9}) {
            TableColumn col = tableEmpresas.getColumnModel().getColumn(i);
            col.setMinWidth(0);
            col.setMaxWidth(0);
            col.setWidth(0);
        }

        // Ajustar anchos básicos
        tableEmpresas.getColumnModel().getColumn(0).setPreferredWidth(20);  // S
        tableEmpresas.getColumnModel().getColumn(1).setPreferredWidth(120); // Nombre
        tableEmpresas.getColumnModel().getColumn(2).setPreferredWidth(150); // Dirección
    }

    model.setRowCount(0);

    EntityManager em = null;
    try {
        em = MonituxDBContext.getEntityManager();

        String jpql = "SELECT e FROM Empresa e WHERE FUNCTION('REPLACE', e." + campo + ", ' ', '') LIKE :valor";

        List<Empresa> empresas = em.createQuery(jpql, Empresa.class)
            .setParameter("valor", "%" + valor.replace(" ", "") + "%")
            .getResultList();

        for (Empresa e : empresas) {
            model.addRow(new Object[] {
                e.getSecuencial(), e.getNombre(), e.getDireccion(), e.getTelefono(),
                e.getEmail(), e.getMoneda(), e.getISV(), e.isActiva() ? "Sí" : "No",
                e.getImagen(), e.getRSS()
            });
        }

        tableEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (tableEmpresas.getRowCount() > 0) {
            tableEmpresas.setRowSelectionInterval(0, 0);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al filtrar empresas: " + e.getMessage());
        System.err.println(e.getMessage());
    }
}

        
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

  jComboBox2.addItem("Nombre");
  jComboBox2.addItem("Telefono");
  jComboBox2.addItem("Email");
  
        
//****************************
        if (AppSettings.getPrimer_Arranque()==true){
        
           
        Menu_Eliminar.setVisible(false);
        
        }
        else{
        Secuencial=V_Menu_Principal.getSecuencial_Empresa();
        }
//*****************************        
        
        
cargarEmpresas();
primera_carga();
        

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jLabel1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jLabel1MouseWheelMoved

        
        
            
         if (isAdmin == false)
 {
     isAdmin = true; // Cambia el estado a administrador
     jLabel1.setForeground(Color.GREEN);// Cambia el color del texto a verde
 }
 else
 {
     isAdmin = false; // Cambia el estado a no administrador
     jLabel1.setForeground(Color.white);
 }

        
        
        
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseWheelMoved

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked


            
        if (isAdmin) {
    suma += 1; // Incrementa la suma de números aleatorios

    if (suma >= 10) {
        Random random = new Random();
        this.Pin = String.valueOf(1000 + random.nextInt(9000)) + "*"; // Genera número entre 1000 y 9999

        jLabel1.setForeground(Color.RED); // Cambia el color del texto a rojo
        isAdmin = false; // Cambia el estado a no administrador
        suma = 0; // Reinicia la suma
    }
}

        

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered


         if (Pin!="****"){
         jLabel1.setText("Pin: " + Pin); // Muestra el PIN generado al hacer clic en la etiqueta

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited

 jLabel1.setText("Empresas");
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseExited

    /**
     * @param args the command line arguments
     */
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Menu_Eliminar;
    private javax.swing.JMenuItem Menu_Guardar;
    private javax.swing.JMenuItem Menu_Nuevo;
    private javax.swing.JMenuItem Menu_Salir;
    private javax.swing.JCheckBox checkBoxActivo;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelImagen1;
    private javax.swing.JTable tableEmpresas;
    private javax.swing.JTextField txt_Direccion;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_ISV;
    private javax.swing.JTextField txt_Nombre;
    private javax.swing.JTextField txt_RSS;
    private javax.swing.JTextField txt_Telefono;
    // End of variables declaration//GEN-END:variables
}
