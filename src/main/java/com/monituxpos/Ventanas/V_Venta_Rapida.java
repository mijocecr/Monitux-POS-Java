/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.monituxpos.Clases.Miniatura_Producto;
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Producto;
import com.monituxpos.Clases.ProductoTopVR;
import com.monituxpos.Clases.Util;
import jakarta.persistence.EntityManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Venta_Rapida extends javax.swing.JPanel {

    
      public int Secuencial_Usuario=V_Menu_Principal.getSecuencial_Usuario();
      public int Secuencial_Cliente;
    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    public int Secuencial;
    
    
           double subTotal = 0.0;
double total = 0.0;
double otrosCargos = 0.0;
double impuesto = 0.0;
double descuento = 0.0;
    
       public static final Map<String, Miniatura_Producto> listaDeItems = new HashMap<>();
    
       
       private Webcam webcam;

private Timer escaneoTimer;


//********************

//
//private void iniciarEscaner() {
//    jTextField1.setText(""); // Limpiar campo
//
//    webcam = Webcam.getDefault();
//    if (webcam == null) {
//        JOptionPane.showMessageDialog(this, "No se detectó ninguna cámara.");
//        return;
//    }
//
//    webcam.setViewSize(WebcamResolution.VGA.getSize());
//    webcam.open();
//
//    escaneoTimer = new Timer(200, e -> escanearFrame());
//    escaneoTimer.start();
//}
private void escanearFrame() {
    if (webcam != null && webcam.isOpen()) {
        BufferedImage imagenOriginal = webcam.getImage();
        if (imagenOriginal != null) {
            int ancho = jLabel4.getWidth();
            int alto = jLabel4.getHeight();

            if (ancho > 0 && alto > 0) {
                Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                jLabel4.setIcon(new ImageIcon(imagenEscalada));
            }

            try {
                LuminanceSource source = new BufferedImageLuminanceSource(imagenOriginal);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);

                if (result != null) {
                    jTextField1.setText(result.getText());
                    jTextField1.requestFocusInWindow();
                    Ejecutar_BC(); // Se ejecuta cada vez que se detecta un código
                }
            } catch (NotFoundException ex) {
                // No se detectó código, continuar escaneando
            }
        }
    }
}

private void iniciarEscaner() {
    jTextField1.setText(""); // Limpiar campo

    webcam = Webcam.getDefault();
    if (webcam == null) {
        JOptionPane.showMessageDialog(this, "No se detectó ninguna cámara.");
        return;
    }

    webcam.setViewSize(WebcamResolution.VGA.getSize());
    webcam.open();

    escaneoTimer = new Timer(200, e -> escanearFrame());
    escaneoTimer.start();
}


private void cerrarCamara() {
    if (escaneoTimer != null) {
        escaneoTimer.stop();
        escaneoTimer = null;
    }
    if (webcam != null && webcam.isOpen()) {
        webcam.close();
        webcam = null;
    }
    jLabel4.setIcon(null);
}


//*********************


    
    /**
     * Creates new form V_Venta_Rapida
     */
 public V_Venta_Rapida() {
    initComponents();
listaDeItems.clear();
    jTextField1.requestFocusInWindow();
    jTextField1.selectAll();

    configurarTabla(jTable1);
    jComboBox1.setEditable(false);
    Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);

    jComboBox2.addItem("Codigo");
    jComboBox2.addItem("Codigo_Barra");
    jComboBox2.addItem("Codigo_Fabricante");

    // Esperar a que todo esté visible antes de agregar botones
    SwingUtilities.invokeLater(() -> {
        mostrarTopProductosEnPanel(jPanel3);
    });
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        icono_carga = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        lblISV = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(35, 32, 45));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(51, 255, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(35, 32, 45));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel3.setBackground(new java.awt.Color(35, 32, 45));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Opciones Rapidas");

        jButton4.setText("Importar Cotizacion");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Cambiar Modo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(35, 32, 45));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setAutoscrolls(true);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jPanel4.setBackground(new java.awt.Color(35, 32, 45));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(35, 32, 45));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Venta Rapida");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(35, 32, 45));
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Usar Escaner");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18))
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(35, 32, 45));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Seleccion de Cliente");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        icono_carga.setBackground(new java.awt.Color(35, 32, 45));
        icono_carga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gifs/spinner1-r.gif"))); // NOI18N
        icono_carga.setVisible(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(icono_carga)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(icono_carga, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel6.setBackground(new java.awt.Color(35, 32, 45));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete1.png"))); // NOI18N
        jButton1.setText("<html>Quitar<br><i>Item<i/></br></html>");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/database_refresh.png"))); // NOI18N
        jButton2.setText("<html>Reset<br><i>Factura<i/></br></html>");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

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
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTable1MouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(35, 32, 45));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel5.setBackground(new java.awt.Color(35, 32, 45));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Buscar Por:");

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, 162, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(35, 32, 45));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sub Total:");

        jCheckBox2.setBackground(new java.awt.Color(35, 32, 45));
        jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("ISV");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total:");

        lblSubTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubTotal.setForeground(new java.awt.Color(255, 255, 0));
        lblSubTotal.setText("0");

        lblISV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblISV.setForeground(new java.awt.Color(255, 255, 0));
        lblISV.setText("0");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 0));
        lblTotal.setText("0");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jCheckBox2)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblISV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblSubTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(lblISV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTotal))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(35, 32, 45));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jButton3.setBackground(new java.awt.Color(35, 32, 45));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 255, 0));
        jButton3.setText("<html>Generar<br>Venta</br></html>");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 477, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(16, 16, 16))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel10, jPanel6, jPanel8, jPanel9});

    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

            restaurarFocoEscaner();

    if (jCheckBox1.isSelected()) {
         jLabel4.setVisible(true);
        icono_carga.setVisible(true);
        jComboBox2.setSelectedItem("Codigo_Barra");
        jComboBox2.setEnabled(false);
        iniciarEscaner();
    } else {
        cerrarCamara();
        jComboBox2.setEnabled(true);
        jComboBox2.setSelectedItem("Codigo");
        jLabel4.setVisible(false);
        icono_carga.setVisible(false);
        
    }
    
   

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked

   



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        JPanel panel= new JPanel();
        
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
panel.setPreferredSize(new Dimension(300, 400));
panel.setBorder(BorderFactory.createLineBorder(Color.RED));

JButton test = new JButton("Test");
test.setPreferredSize(new Dimension(155, 60));
test.setBackground(Color.RED);
test.setForeground(Color.WHITE);
panel.add(test);

panel.revalidate();
panel.repaint();

        
//        
//             JButton test = new JButton("Test");
//jPanel3.add(test);
//jPanel3.revalidate();
//jPanel3.repaint();
//jPanel3.setBorder(BorderFactory.createLineBorder(Color.RED));


        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed


        Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);
        
        jComboBox1.setSelectedIndex(-1); // Limpiar la selección del cliente
        
        jComboBox1.setEnabled(true);


icono_carga.setVisible(false); // Equivalente a pictureBox2.Visible = false

DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
modelo.setRowCount(0); // Limpiar la tabla

subTotal = 0.0;
total = 0.0;
impuesto = 0.0;

lblSubTotal.setText(String.format("%.2f", subTotal));
lblISV.setText(String.format("%.2f", impuesto));
lblTotal.setText(String.format("%.2f", total));

jTextField1.setText(""); // Limpiar campo

configurarTabla(jTable1); // Tu método equivalente a Configurar_DataGridView()

jCheckBox2.setSelected(false); // Desmarcar checkbox de ISV

restaurarFocoEscaner(); // Método personalizado

listaDeItems.clear();
    jLabel3.setText("Opciones Rapidas");
    jButton4.setEnabled(true);


        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int filaSeleccionada = jTable1.getSelectedRow();
if (filaSeleccionada != -1) {
    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

    // Obtener el código del producto desde la columna correspondiente
    Object codigoObj = modelo.getValueAt(filaSeleccionada, 1); // columna 1 = Código
    if (codigoObj != null) {
        String codigo = codigoObj.toString();
        listaDeItems.remove(codigo); // Eliminar del mapa
    }

    modelo.removeRow(filaSeleccionada); // Eliminar de la tabla
}

actualizarTotal();
restaurarFocoEscaner();


        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved


        restaurarFocoEscaner();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTable1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseEntered

restaurarFocoEscaner();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseEntered

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        restaurarFocoEscaner();
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered

        restaurarFocoEscaner();
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

      if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String texto = jTextField1.getText().trim();
            if (!texto.isEmpty()) {
                Ejecutar_BC();
                jTextField1.setText("");
            }
        }
        
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed


        icono_carga.setVisible(true);

    V_Importar_Cotizacion dialogo = new V_Importar_Cotizacion();
    dialogo.onAceptar = () -> {
        importarCotizacion(dialogo.getLista(), dialogo.getClienteSeleccionado());

        actualizarTotal(); // ✅ Ahora se ejecuta después de importar

        icono_carga.setVisible(false);
        jButton4.setEnabled(false);
    };

    dialogo.setVisible(true); // No bloquea si el diálogo no es modal
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

        Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

V_Factura_Venta destino = new V_Factura_Venta();
destino.setVisible(true); // Primero mostrar
V_Menu_Principal.abrirVentana(destino); // Registrar en navegación

SwingUtilities.invokeLater(() -> {
    destino.recibirItems(new HashMap<>(this.listaDeItems)); // Ahora sí: contenedor ya renderizado
});

    
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    
    
    //*****************************
    
   public void importarCotizacion(Map<String, Double> lista, String cliente) {
    jComboBox1.setSelectedItem(cliente);
    jComboBox1.setEnabled(false);

    EntityManager em = null;
    DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel(); // ← Asumiendo jTable1 como destino

    try {
        em = MonituxDBContext.getEntityManager();

        for (Map.Entry<String, Double> itemC : lista.entrySet()) {
            String codigo = itemC.getKey();
            Double cantidadSeleccionada = itemC.getValue();

            if (cantidadSeleccionada == null || cantidadSeleccionada <= 0) {
                mostrarError("Cantidad inválida para el producto: " + codigo);
                continue;
            }

            List<Producto> productos = em.createQuery(
                "SELECT p FROM Producto p WHERE p.Codigo = :codigo AND p.Secuencial_Empresa = :empresa",
                Producto.class)
                .setParameter("codigo", codigo)
                .setParameter("empresa", Secuencial_Empresa)
                .getResultList();

            for (Producto producto : productos) {
                icono_carga.setVisible(true);

                agregarProductoATabla(producto, cantidadSeleccionada, modeloTabla);

                icono_carga.setVisible(false);
            }
        }

        //jButton6.doClick(); // Simula actualización visual

    } catch (Exception e) {
        mostrarError("Error al importar cotización: " + e.getMessage());
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

    
    
    //*****************************
    
    
    
    
    private void Ejecutar_BC(){
    
      
        String texto = jTextField1.getText().trim();

    if (!texto.isEmpty()) {
       
            filtrar(jComboBox2.getSelectedItem().toString(), texto, V_Menu_Principal.Secuencial_Empresa);
            actualizarTotal();
cerrarCamara();
           
            if (jCheckBox1.isSelected()) {
                iniciarEscaner(); // Reiniciar la cámara para escanear el siguiente código de barras
            }
        }
    
    
    
    }
    
    
    public void configurarTabla(JTable tabla) {
    // Modelo no editable
    DefaultTableModel modelo = new DefaultTableModel(
        new Object[][] {},
        new String[] { "S", "Codigo", "Descripcion", "Cantidad", "Precio", "Total" }
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Desactiva edición en todas las celdas
        }
    };

    tabla.setModel(modelo);

    // Centrar contenido de celdas
    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
    centro.setHorizontalAlignment(SwingConstants.CENTER);
    tabla.setDefaultRenderer(Object.class, centro);

    // Ajustar anchos de columna
    tabla.getColumnModel().getColumn(0).setPreferredWidth(28);   // Secuencial
    tabla.getColumnModel().getColumn(1).setPreferredWidth(100);  // Codigo
    tabla.getColumnModel().getColumn(2).setPreferredWidth(215);  // Descripcion
    tabla.getColumnModel().getColumn(3).setPreferredWidth(60);   // Cantidad
    tabla.getColumnModel().getColumn(4).setPreferredWidth(80);   // Precio
    tabla.getColumnModel().getColumn(5).setPreferredWidth(80);   // Total

    // Selección de fila completa
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabla.setRowSelectionAllowed(true);
    tabla.setColumnSelectionAllowed(false);
}

    
   public void filtrar(String campo, String valor, int secuencialEmpresa) {
    EntityManager em = MonituxDBContext.getEntityManager();
    DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();

    List<Producto> productos = em.createQuery(
        "SELECT p FROM Producto p WHERE p." + campo + " = :valor AND p.Secuencial_Empresa = :empresa", Producto.class)
        .setParameter("valor", valor)
        .setParameter("empresa", secuencialEmpresa)
        .getResultList();

    for (Producto producto : productos) {
        icono_carga.setVisible(true);

        String input = solicitarCantidad(producto.getCodigo());
        if (input == null || input.trim().isEmpty()) {
            mostrarError("No se ha ingresado una cantidad válida.");
            continue;
        }

        input = input.trim();
        if (!input.matches("^\\d+(\\.\\d+)?$")) {
            mostrarError("Solo se permiten números válidos (ej. 3 o 3.5).");
            continue;
        }

        double cantidad = Double.parseDouble(input);
        agregarProductoATabla(producto, cantidad, modeloTabla);
        icono_carga.setVisible(false);
    }
}

   
   private String solicitarCantidad(String codigoProducto) {
    return JOptionPane.showInputDialog(
        null,
        "Digite la cantidad en números de este producto que está agregando",
        "¿Cuántos: " + codigoProducto + "?",
        JOptionPane.QUESTION_MESSAGE
    );
}

private void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    if (jCheckBox1.isSelected()) {
        jTextField1.requestFocusInWindow();
    }
}

private void agregarProductoATabla(Producto producto, double cantidad, DefaultTableModel modeloTabla) {
    Miniatura_Producto mini = listaDeItems.get(producto.getCodigo());

    if (mini == null) {
        mini = new Miniatura_Producto();
        mini.producto = new Producto(); // ← Prevención de NullPointerException
        mini.producto.setSecuencial(producto.getSecuencial());
        mini.producto.setCodigo(producto.getCodigo());
        mini.producto.setDescripcion(producto.getDescripcion());
        mini.producto.setPrecio_Venta(producto.getPrecio_Venta());
        mini.producto.setTipo(producto.getTipo());
        mini.setCantidadSelecccion(cantidad);
        mini.producto.setCantidad(cantidad);

        listaDeItems.put(producto.getCodigo(), mini);
    } else {
        mini.setCantidadSelecccion(mini.getCantidadSelecccion() + cantidad);
        mini.producto.setCantidad(mini.getCantidadSelecccion());

        // Eliminar fila anterior
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 1).equals(producto.getCodigo())) {
                modeloTabla.removeRow(i);
                break;
            }
        }
    }

    double precio = Math.round(producto.getPrecio_Venta() * 100.0) / 100.0;
    double total = Math.round(precio * mini.getCantidadSelecccion() * 100.0) / 100.0;

    modeloTabla.addRow(new Object[]{
        producto.getSecuencial(),
        producto.getCodigo(),
        producto.getDescripcion(),
        mini.getCantidadSelecccion(),
        precio,
        total
    });
}

   
   
    //*****************************
    
 public void mostrarTopProductosEnPanel(JPanel panel) {
    if (panel == null) return;

    panel.removeAll(); // Limpiar el panel

    // Contenedor interno con layout vertical
    JPanel contenedorVertical = new JPanel();
    contenedorVertical.setLayout(new BoxLayout(contenedorVertical, BoxLayout.Y_AXIS));
    contenedorVertical.setBackground(panel.getBackground()); // Mantener estilo
    contenedorVertical.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    List<ProductoTopVR> topProductos = Util.obtenerTopProductosVendidos(6);
    System.out.println("🔍 Productos top obtenidos: " + topProductos.size());

    if (topProductos == null || topProductos.isEmpty()) {
        JLabel vacio = new JLabel("No hay productos top vendidos.");
        vacio.setForeground(Color.GRAY);
        vacio.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        contenedorVertical.add(vacio);
    } else {
        for (ProductoTopVR producto : topProductos) {
            JButton btn = new JButton();
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // ancho flexible
            btn.setBackground(new Color(0,168,107));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            String texto = producto.getDescripcion().length() > 35
                ? producto.getDescripcion().substring(0, 35) + "..."
                : producto.getDescripcion();
            btn.setText("<html><center>" + texto + "<br><b>" + String.format("%.2f", producto.getVenta()) + "</b></center></html>");
            btn.putClientProperty("producto", producto);
            btn.setToolTipText(producto.getCodigo());

            btn.addActionListener(e -> {
                try {
                    icono_carga.setVisible(true);
                    ProductoTopVR prod = (ProductoTopVR) ((JButton) e.getSource()).getClientProperty("producto");

                    filtrar("Codigo", prod.getCodigo(), Secuencial_Empresa);

                    if (jCheckBox1.isSelected()) {
                        jTextField1.requestFocusInWindow();
                    }

                    actualizarTotal();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    icono_carga.setVisible(false);
                }
            });

            contenedorVertical.add(btn);
            contenedorVertical.add(Box.createVerticalStrut(4)); // espacio entre botones
        }
    }

    panel.setLayout(new BorderLayout()); // Asegura que el contenedor se expanda
    panel.add(contenedorVertical, BorderLayout.CENTER);

    restaurarFocoEscaner();
    panel.revalidate();
    panel.repaint();
}

    
    //****************************
    
    
    
    
    
    public void actualizarTotal() {
    double total = 0.0;
    double subtotal = 0.0;
    double isv = 0.0; // Reiniciar el ISV antes de calcular

    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();

    for (int i = 0; i < modelo.getRowCount(); i++) {
        Object valorCelda = modelo.getValueAt(i, 5); // Columna "Total" (índice 5)
        if (valorCelda != null) {
            try {
                double valor = Double.parseDouble(valorCelda.toString());
                subtotal += valor;
            } catch (NumberFormatException e) {
                // Ignorar valores no numéricos
            }
        }
    }

    if (jCheckBox2.isSelected()) {
        isv = subtotal * 0.15;//Cambiar esto a un Setting
    } else {
        isv = 0.0;
    }

    total = subtotal + isv;

    lblSubTotal.setText(String.format("%.2f", subtotal));
    lblISV.setText(String.format("%.2f", isv));
    lblTotal.setText(String.format("%.2f", total));

    restaurarFocoEscaner();
}

    
    public void restaurarFocoEscaner() {
    if (jCheckBox1.isSelected() && jTextField1.isVisible() && jTextField1.isEnabled()) {
        SwingUtilities.invokeLater(() -> {
            jTextField1.requestFocusInWindow();
            jTextField1.selectAll();
        });
    }
}

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel icono_carga;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblISV;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
}
