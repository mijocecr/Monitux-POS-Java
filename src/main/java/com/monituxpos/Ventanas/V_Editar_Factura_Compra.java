/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Compra;
import com.monituxpos.Clases.Compra_Detalle;
import com.monituxpos.Clases.Cuentas_Cobrar;
import com.monituxpos.Clases.Cuentas_Pagar;
import com.monituxpos.Clases.Egreso;
import com.monituxpos.Clases.FacturaCompletaPDF_Compra;
import com.monituxpos.Clases.FacturaCompletaPDF_Venta;
import com.monituxpos.Clases.Ingreso;
import com.monituxpos.Clases.Item_Factura;
import com.monituxpos.Clases.Kardex;
import com.monituxpos.Clases.Miniatura_Producto;
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Producto;
import com.monituxpos.Clases.SelectorCantidad;
import com.monituxpos.Clases.Util;
import com.monituxpos.Clases.Venta;
import com.monituxpos.Clases.Venta_Detalle;
import static com.monituxpos.Ventanas.V_Editar_Factura_Venta.listaDeItems;
import static com.monituxpos.Ventanas.V_Editar_Factura_Venta.selectoresCantidad;
import static com.monituxpos.Ventanas.V_Factura_Compra.listaDeItems;
import static com.monituxpos.Ventanas.V_Factura_Compra.selectoresCantidad;
import static com.monituxpos.Ventanas.V_Factura_Venta.listaDeItems;
import static com.monituxpos.Ventanas.V_Factura_Venta.selectoresCantidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.mail.FetchProfile.Item;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Editar_Factura_Compra extends javax.swing.JFrame {
    
    
     
      public int Secuencial_Usuario = V_Menu_Principal.getSecuencial_Usuario();
      public int Secuencial_Proveedor;
    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    public int Secuencial;
    public V_Compras_Ventas form;
    public EntityManager em;
           double subTotal = 0.0;
double total = 0.0;
double otrosCargos = 0.0;
double impuesto = 0.0;
double descuento = 0.0;
    
       public static final Map<String, Miniatura_Producto> listaDeItems = new HashMap<>();
       public static final Map<String, Miniatura_Producto> listaDeItemsEliminar = new HashMap<>();
       public static final Map<String, SelectorCantidad> selectoresCantidad = new HashMap<>();

   

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int Secuencial_Usuario) {
        this.Secuencial_Usuario = Secuencial_Usuario;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }

   

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    
    
    
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(V_Editar_Factura_Compra.class.getName());

    /**
     * Creates new form V_Editar_Factura_Venta
     */
    public V_Editar_Factura_Compra(V_Compras_Ventas x) {
       
        initComponents();

    em = MonituxDBContext.getEntityManager(); // ← Uso centralizado del contexto

    this.getContentPane().setBackground(Color.BLACK);

    form = x;
    Secuencial_Proveedor = form.Secuencial_Proveedor;

    System.out.println("✅ V_Editar_Factura_Compra inicializado con proveedor: " + Secuencial_Proveedor);
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        menu_contextual = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        comboProveedor = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbl_otrosCargos = new javax.swing.JTextField();
        lbl_impuesto = new javax.swing.JTextField();
        lbl_descuento = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_subTotal = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        icono_carga = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        contenedor = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        contenedor_selector = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(11, 8, 20));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Descripcion", "Marca", "Codigo_Barra" }));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buscar Por:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, 0, 175, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Imagen");

        jMenuItem1.setText("Local");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Web");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Hacer Foto");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        menu_contextual.add(jMenu1);

        jMenuItem7.setText("Comentario");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        menu_contextual.add(jMenuItem7);

        jMenu3.setText("Producto");

        jMenuItem4.setText("Agregar Unidades");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setText("Retirar Unidades");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);
        jMenu3.add(jSeparator1);

        jMenuItem6.setText("Ver Producto");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        menu_contextual.add(jMenu3);

        jMenuItem8.setText("Ampliar");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        menu_contextual.add(jMenuItem8);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Proveedor:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 16, -1, 30));

        comboProveedor.setEnabled(false);
        comboProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboProveedorMouseClicked(evt);
            }
        });
        comboProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProveedorActionPerformed(evt);
            }
        });
        jPanel2.add(comboProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 221, -1));

        jLabel7.setText("Tipo de Venta:");
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel8.setText("Metodo de Pago:");
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel9.setText("Se Vence:");
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, -1, -1));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contado", "Credito" }));
        jComboBox3.setEnabled(false);
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 93, -1));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta", "Otro", "Ninguno" }));
        jComboBox4.setEnabled(false);
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel2.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 93, -1));

        jLabel10.setText("Detalle:");
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio", "Total", "SP"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 400, 210));

        jButton7.setBackground(new java.awt.Color(11, 8, 20));
        jButton7.setForeground(new java.awt.Color(0, 204, 255));
        jButton7.setText("<html><b>Guardar</b><br>Cambios</html>");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 460, 72, 85));

        jLabel12.setText("Sub-Total:");
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, -1, -1));

        jLabel13.setText("Total:");
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, -1, -1));

        jLabel14.setText("Otros Cargos:");
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, -1, -1));

        jLabel15.setText("Descuento:");
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, -1, -1));

        jLabel16.setText("%");
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, -1, -1));

        lbl_otrosCargos.setBackground(new java.awt.Color(0, 204, 204));
        lbl_otrosCargos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lbl_otrosCargos.setBorder(null);
        lbl_otrosCargos.setForeground(Color.BLUE); // Cambia el texto a azul
        lbl_otrosCargos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl_otrosCargosActionPerformed(evt);
            }
        });
        lbl_otrosCargos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lbl_otrosCargosKeyReleased(evt);
            }
        });
        jPanel2.add(lbl_otrosCargos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 450, 80, -1));

        lbl_impuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lbl_impuesto.setBackground(new java.awt.Color(0, 204, 204));
        lbl_impuesto.setBorder(null);
        lbl_impuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl_impuestoActionPerformed(evt);
            }
        });
        lbl_impuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lbl_impuestoKeyReleased(evt);
            }
        });
        jPanel2.add(lbl_impuesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 490, 50, -1));

        lbl_descuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lbl_descuento.setBackground(new java.awt.Color(0, 204, 204));
        lbl_descuento.setBorder(null);
        lbl_descuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lbl_descuentoKeyReleased(evt);
            }
        });
        jPanel2.add(lbl_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 530, 50, -1));

        jLabel17.setText("Impuesto:");
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, -1, -1));

        jLabel18.setText("%");
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 530, -1, -1));

        lbl_subTotal.setText("0");
        lbl_subTotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_subTotal.setForeground(new java.awt.Color(255, 255, 0));
        jPanel2.add(lbl_subTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 150, -1));

        lbl_total.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(255, 255, 0));
        lbl_total.setText("0");
        jPanel2.add(lbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, 150, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 255, 0));
        jLabel6.setText("-- -- ----");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 160, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setText("Mecanica: Señale Producto -> Click -> Cantidad -> Actualizar Detalle");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 51));
        jLabel4.setText("Modificar Compra");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        icono_carga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gifs/spinner1-r.gif"))); // NOI18N
        icono_carga.setVisible(false);

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        jButton1.setBackground(new java.awt.Color(11, 8, 20));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/box_down.png"))); // NOI18N
        jButton1.setText("<html><b>Nuevo</b><br><i>Producto</i></html>");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(11, 8, 20));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Descripcion", "Marca", "Codigo_Barra" }));

        jTextField3.setToolTipText("<html>Para efectuar la busqueda debe presionar Enter. <br>El filtro se restablecera al presionar Enter si la casilla esta vacia.</br></html>");
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

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Buscar Por:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, 0, 175, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setForeground(new java.awt.Color(255, 153, 0));
        jLabel20.setText("<html>El impuesto y el descuento deben recalcularse al editar factura. (si aplica)</html>");
        jLabel20.setToolTipText("");

        jScrollPane1.setBackground(new java.awt.Color(35, 32, 40));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setHorizontalScrollBar(null);

        contenedor.setBackground(new java.awt.Color(35, 32, 45));
        contenedor.setAutoscrolls(true);
        contenedor.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                contenedorMouseMoved(evt);
            }
        });
        contenedor.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPane1.setViewportView(contenedor);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Productos en Lista:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("0");
        jLabel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel3MouseMoved(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(11, 8, 20));
        jButton4.setForeground(new java.awt.Color(255, 0, 51));
        jButton4.setText("<html><b>Eliminar</b><br>Factura</html>");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(11, 8, 20));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete1.png"))); // NOI18N
        jButton5.setText("<html><b>Quitar</b><br>Elemento</html>");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        contenedor_selector.setBackground(new java.awt.Color(35, 32, 45));
        jScrollPane3.setViewportView(contenedor_selector);

        jButton6.setBackground(new java.awt.Color(11, 8, 20));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/lightning_go.png"))); // NOI18N
        jButton6.setText("<html><b>Actualizar</b><br>Detalle</html>");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(8, 8, 8)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel20)
                .addGap(471, 471, 471))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jPanel3});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton5, jScrollPane3});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(icono_carga, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel11))
                    .addComponent(icono_carga, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged

      

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        
        
        
        
     ActualizarNumeros();   
        
Guardar_Cambios();


       
    }//GEN-LAST:event_jButton7ActionPerformed

   
  public void Guardar_Cambios() {
    EntityManager em = null;
    EntityTransaction tx = null;
    List<Object[]> movimientosPendientes = new ArrayList<>();
    List<String> actividadesPendientes = new ArrayList<>();
    try {
        em = MonituxDBContext.getEntityManager();
        if (em == null || !em.isOpen()) {
            throw new IllegalStateException("EntityManager no disponible.");
        }
        tx = em.getTransaction();
        tx.begin();
        Compra compra = em.find(Compra.class, Secuencial);
        if (compra == null || compra.getSecuencial_Empresa() != Secuencial_Empresa) {
            JOptionPane.showMessageDialog(null, "No se encontró la compra para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            tx.rollback();
            return;
        }
        compra.setSecuencial_Proveedor(Secuencial_Proveedor);
        compra.setSecuencial_Usuario(Secuencial_Usuario);
        compra.setTipo(jComboBox3.getSelectedItem() != null ? jComboBox3.getSelectedItem().toString() : "Sin tipo");
        compra.setForma_Pago(jComboBox4.getSelectedItem() != null ? jComboBox4.getSelectedItem().toString() : "Sin forma de pago");
        compra.setFecha(Util.fechaActualCompleta());
        compra.setTotal(Util.redondear(subTotal));
        compra.setGran_Total(Util.redondear(total));
        compra.setImpuesto(impuesto);
        compra.setOtros_Cargos(otrosCargos);
        compra.setDescuento(descuento);
        List<String> codigos = listaDeItems.values().stream()
            .map(pro -> pro.producto.getCodigo())
            .filter(Objects::nonNull)
            .distinct()
            .toList();
        List<Producto> productos = em.createQuery(
            "SELECT p FROM Producto p WHERE p.Codigo IN :codigos", Producto.class)
            .setParameter("codigos", codigos)
            .getResultList();
        Map<String, Producto> productosCache = productos.stream()
            .collect(Collectors.toMap(Producto::getCodigo, p -> p));
        for (Miniatura_Producto pro : listaDeItems.values()) {
            String codigo = pro.producto.getCodigo().trim();
            Producto productoBD = productosCache.get(codigo);
            if (productoBD == null) {
                System.out.println("❌ Producto no encontrado en BD para código: " + codigo);
                continue;
            }
            pro.producto.setPrecio_Costo(productoBD.getPrecio_Costo());
            pro.producto.setPrecio_Venta(productoBD.getPrecio_Venta());
            pro.producto.setCantidad(productoBD.getCantidad());
            double nuevaCantidad = pro.getCantidadSelecccion();
            Compra_Detalle detalle = em.createQuery(
                "SELECT d FROM Compra_Detalle d WHERE d.Secuencial_Factura = :compra AND d.Secuencial_Producto = :producto AND d.Secuencial_Empresa = :empresa",
                Compra_Detalle.class)
                .setParameter("compra", compra.getSecuencial())
                .setParameter("producto", productoBD.getSecuencial())
                .setParameter("empresa", compra.getSecuencial_Empresa())
                .getResultStream().findFirst().orElse(null);
            if (detalle != null) {
                if (!"Servicio".equalsIgnoreCase(pro.producto.getTipo())) {
                    double diferencia = nuevaCantidad - detalle.getCantidad();
                    if (diferencia != 0) {
                        String tipoMovimiento = diferencia > 0 ? "Entrada" : "Salida";
                        double cantidadMovimiento = Math.abs(diferencia);
                        movimientosPendientes.add(new Object[] {
                            productoBD.getSecuencial(),
                            productoBD.getCantidad(),
                            productoBD.getDescripcion(),
                            cantidadMovimiento,
                            productoBD.getPrecio_Costo(),
                            productoBD.getPrecio_Venta(),
                            tipoMovimiento,
                            Secuencial_Empresa
                        });
                        productoBD.setCantidad(productoBD.getCantidad() + diferencia);
                        em.merge(productoBD);

                        String accion = diferencia > 0 ? "Agregó" : "Quitó";
                        actividadesPendientes.add(
                            accion + " " + cantidadMovimiento + " unidades de " + productoBD.getCodigo() +
                            " en modificación de compra No. " + compra.getSecuencial()
                        );
                    }
                }
                detalle.setCantidad(nuevaCantidad);
                detalle.setPrecio(Util.redondear(productoBD.getPrecio_Costo()));
                detalle.setTotal(Util.redondear(nuevaCantidad * productoBD.getPrecio_Costo()));
                detalle.setDescripcion(productoBD.getDescripcion());
                detalle.setTipo(productoBD.getTipo());
                detalle.setFecha(compra.getFecha());
                detalle.setSecuencial_Usuario(compra.getSecuencial_Usuario());
                detalle.setSecuencial_Proveedor(compra.getSecuencial_Proveedor());
            } else {
                Compra_Detalle nuevo = new Compra_Detalle();
                nuevo.setSecuencial_Empresa(compra.getSecuencial_Empresa());
                nuevo.setSecuencial_Factura(compra.getSecuencial());
                nuevo.setSecuencial_Proveedor(compra.getSecuencial_Proveedor());
                nuevo.setSecuencial_Usuario(compra.getSecuencial_Usuario());
                nuevo.setFecha(compra.getFecha());
                nuevo.setSecuencial_Producto(productoBD.getSecuencial());
                nuevo.setCodigo(productoBD.getCodigo());
                nuevo.setDescripcion(productoBD.getDescripcion());
                nuevo.setCantidad(nuevaCantidad);
                nuevo.setPrecio(Util.redondear(productoBD.getPrecio_Costo()));
                nuevo.setTotal(Util.redondear(nuevaCantidad * productoBD.getPrecio_Costo()));
                nuevo.setTipo(productoBD.getTipo());
                em.persist(nuevo);
                actividadesPendientes.add(
                    "Agregó " + nuevaCantidad + " de " + productoBD.getCodigo() +
                    " a compra No. " + compra.getSecuencial()
                );
                if (!"Servicio".equalsIgnoreCase(productoBD.getTipo())) {
                    movimientosPendientes.add(new Object[] {
                        productoBD.getSecuencial(),
                        productoBD.getCantidad(),
                        productoBD.getDescripcion(),
                        nuevaCantidad,
                        productoBD.getPrecio_Costo(),
                        productoBD.getPrecio_Venta(),
                        "Entrada",
                        Secuencial_Empresa
                    });
                    productoBD.setCantidad(productoBD.getCantidad() + nuevaCantidad);
                    em.merge(productoBD);
                }
            }
            Cuentas_Pagar cuenta = em.createQuery(
                "SELECT c FROM Cuentas_Pagar c WHERE c.Secuencial_Factura = :compra AND c.Secuencial_Proveedor = :proveedor AND c.Secuencial_Empresa = :empresa",
                Cuentas_Pagar.class)
                .setParameter("compra", compra.getSecuencial())
                .setParameter("proveedor", compra.getSecuencial_Proveedor())
                .setParameter("empresa", compra.getSecuencial_Empresa())
                .getResultStream().findFirst().orElse(null);

            if (cuenta != null) {
                cuenta.setGran_Total(Util.redondear(compra.getGran_Total()));
                cuenta.setTotal(Util.redondear(compra.getTotal()));
                cuenta.setSaldo(Util.redondear(cuenta.getGran_Total() - cuenta.getPagado()));
            }
        }
        Egreso egreso = em.createQuery(
            "SELECT e FROM Egreso e WHERE e.Secuencial_Factura = :compra AND e.Secuencial_Empresa = :empresa",
            Egreso.class)
            .setParameter("compra", compra.getSecuencial())
            .setParameter("empresa", compra.getSecuencial_Empresa())
            .getResultStream().findFirst().orElse(null);
        if (egreso != null) {
            egreso.setTotal(compra.getGran_Total());
            egreso.setDescripcion("Actualización de egreso por compra No. " + compra.getSecuencial());
            egreso.setFecha(Util.fechaActualCompleta());
            egreso.setTipo_Egreso(compra.getTipo());
            egreso.setSecuencial_Usuario(compra.getSecuencial_Usuario());
            em.merge(egreso);
        }
        FacturaCompletaPDF_Compra factura = new FacturaCompletaPDF_Compra();
        factura.setSecuencial(compra.getSecuencial());
        factura.setProveedor(comboProveedor.getSelectedItem().toString().split("- ")[1].trim());
        factura.setTipoCompra(compra.getTipo());
        factura.setMetodoPago(compra.getForma_Pago());
        factura.setFecha(compra.getFecha());
        factura.setItems(ObtenerItemsDesdeGrid(jTable1));
        factura.setISV(compra.getImpuesto());
        factura.setOtrosCargos(compra.getOtros_Cargos());
        factura.setDescuento(compra.getDescuento());
        compra.setDocumento(factura.GeneratePdfToBytes());
        em.merge(compra);
        tx.commit();
        listaDeItems.clear();
        listaDeItemsEliminar.clear();
                JOptionPane.showMessageDialog(null, "Compra No. " + compra.getSecuencial() + " actualizada correctamente.", "Compras", JOptionPane.INFORMATION_MESSAGE);
        form.cargar_Datos_Compra();
        this.dispose();
    } catch (Exception ex) {
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        JOptionPane.showMessageDialog(null, "Error al actualizar compra:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
    // Ejecutar Kardex y actividad fuera del contexto transaccional
    for (Object[] mov : movimientosPendientes) {
        Util.registrarMovimientoKardex(
            (int) mov[0],
            (double) mov[1],
            (String) mov[2],
            (double) mov[3],
            (double) mov[4],
            (double) mov[5],
            (String) mov[6],
            (int) mov[7]
        );
    }
    for (String actividad : actividadesPendientes) {
        Util.registrarActividad(Secuencial_Usuario, actividad, Secuencial_Empresa);
    }
}

        
   //****************************************      
                
    
    
    
    
    
    
    public int getSecuencial_Proveedor() {
        return Secuencial_Proveedor;
    }

    public void setSecuencial_Proveedor(int Secuencial_Proveedor) {
        this.Secuencial_Proveedor = Secuencial_Proveedor;
    }

                
                
    
             
                
                
    
    private void lbl_otrosCargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl_otrosCargosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_otrosCargosActionPerformed

    
     public int obtenerIndiceColumna(JTable table, String nombreColumna) {
    for (int i = 0; i < table.getColumnCount(); i++) {
        if (table.getColumnName(i).equalsIgnoreCase(nombreColumna)) {
            return i;
        }
    }
    return -1; // No encontrada
}

    
     public List<Item_Factura> ObtenerItemsDesdeGrid(JTable table) {
    List<Item_Factura> lista = new ArrayList<>();

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    int rowCount = model.getRowCount();

    for (int i = 0; i < rowCount; i++) {
        // Puedes omitir filas vacías si lo deseas
        Object codigoObj = model.getValueAt(i, obtenerIndiceColumna(table, "Codigo"));
        Object descripcionObj = model.getValueAt(i, obtenerIndiceColumna(table, "Descripcion"));
        Object cantidadObj = model.getValueAt(i, obtenerIndiceColumna(table, "Cantidad"));
        Object precioObj = model.getValueAt(i, obtenerIndiceColumna(table, "Precio"));

       if (codigoObj != null && cantidadObj != null && precioObj != null) {
    try {
        Item_Factura item = new Item_Factura();
        item.setCodigo(codigoObj.toString());
        item.setDescripcion(descripcionObj != null ? descripcionObj.toString() : "");

        // Manejo seguro de cantidad (puede venir como "1.0")
        double cantidadDouble = Double.parseDouble(cantidadObj.toString());
        item.setCantidad((int) cantidadDouble); // redondeo truncado

        // Manejo seguro de precio
        double precio = Double.parseDouble(precioObj.toString());
        item.setPrecio(precio);

        lista.add(item);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Error al convertir cantidad o precio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    }

    return lista;
}

    
    private void lbl_otrosCargosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_otrosCargosKeyReleased

        if (lbl_otrosCargos.getText().trim().isEmpty()) {
            otrosCargos = 0.00;
        } else {
            try {
                otrosCargos = Double.parseDouble(lbl_otrosCargos.getText().trim());
            } catch (NumberFormatException e) {
                otrosCargos = 0.00; // Si hay error al convertir, se establece en 0.00
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_otrosCargosKeyReleased

    
//    
//    public void importarFactura(Map<String, Double> lista, String cliente, EntityManager entityManager) {
//    
////cargarOtrosDatos();
//
//Util.llenarComboCliente(comboCliente, Secuencial_Empresa);
//
//    comboCliente.setSelectedItem(cliente); // Seleccionar el cliente en el comboBox
//
//    List<Producto> productos;
//
//    for (Map.Entry<String, Double> itemC : lista.entrySet()) {
//        productos = entityManager
//            .createQuery("SELECT p FROM Producto p WHERE p.Codigo = :codigo AND p.Secuencial_Empresa = :empresa", Producto.class)
//            .setParameter("codigo", itemC.getKey())
//            .setParameter("empresa", Secuencial_Empresa)
//            .getResultList();
//
//        for (Producto item : productos) {
//            Miniatura_Producto miniaturaProducto = new Miniatura_Producto(item, true);
//
//            miniaturaProducto.setCantidad(item.getCantidad());
//            miniaturaProducto.setImagen(item.getImagen());
//            miniaturaProducto.setSecuencial_Empresa(Secuencial_Empresa);
//            miniaturaProducto.setSecuencial(item.getSecuencial());
//            miniaturaProducto.setCodigo(item.getCodigo());
//            miniaturaProducto.setMarca(item.getMarca());
//            miniaturaProducto.setDescripcion(item.getDescripcion());
//            miniaturaProducto.setPrecio_Venta(item.getPrecio_Venta());
//            miniaturaProducto.setPrecio_Costo(item.getPrecio_Costo());
//            miniaturaProducto.setExistencia_Minima(item.getExistencia_Minima());
//            miniaturaProducto.setCodigo_Barra(item.getCodigo_Barra());
//            miniaturaProducto.setCodigo_Fabricante(item.getCodigo_Fabricante());
//            miniaturaProducto.setSecuencial_Proveedor(item.getSecuencial_Proveedor());
//            miniaturaProducto.setSecuencial_Categoria(item.getSecuencial_Categoria());
//            miniaturaProducto.setSecuencial_Usuario(Secuencial_Usuario);
//            miniaturaProducto.setFecha_Caducidad(item.getFecha_Caducidad());
//            
//            miniaturaProducto.setExpira(item.isExpira());
//           
//            miniaturaProducto.setCantidadSelecccionItem(lista.getOrDefault(itemC.getKey(), 0.0).intValue());
//
//             //miniaturaProducto.setCantidadSelecccionItem(1);
//
//            miniaturaProducto.setTipo(item.getTipo());
//
//            SelectorCantidad selectorCantidad = new SelectorCantidad(item.getCodigo(), miniaturaProducto.getCantidadSelecccion());
//
//            if (listaDeItems.containsKey(miniaturaProducto.getCodigo())) {
//                listaDeItems.remove(miniaturaProducto.getCodigo());
//                listaDeItems.put(miniaturaProducto.getCodigo(), miniaturaProducto);
//
//                selectorCantidad.setCantidad(listaDeItems.get(selectorCantidad.getCodigo()).getCantidadSelecccion());
//            } else {
//                listaDeItems.put(miniaturaProducto.getCodigo(), miniaturaProducto);
//                contenedor_selector.add(selectorCantidad);
//            }
//        }
//    }
//
//    jLabel3.setText(String.valueOf(listaDeItems.size()));
//    jButton6.doClick();
//}
//
//    
//    
    
    public void importarFactura(Map<String, Double> lista, String proveedor, EntityManager entityManager) {
    try {
        Util.llenarComboProveedor(comboProveedor, Secuencial_Empresa);
        comboProveedor.setSelectedItem(proveedor);

        for (Map.Entry<String, Double> itemC : lista.entrySet()) {
            List<Producto> productos = entityManager
                .createQuery("SELECT p FROM Producto p WHERE p.Codigo = :codigo AND p.Secuencial_Empresa = :empresa", Producto.class)
                .setParameter("codigo", itemC.getKey())
                .setParameter("empresa", Secuencial_Empresa)
                .getResultList();

            for (Producto item : productos) {
                Miniatura_Producto miniaturaProducto = new Miniatura_Producto(item, true);
                // ... todas las asignaciones ...
                SelectorCantidad selectorCantidad = new SelectorCantidad(item.getCodigo(), miniaturaProducto.getCantidadSelecccion());

                if (listaDeItems.containsKey(miniaturaProducto.getCodigo())) {
                    listaDeItems.put(miniaturaProducto.getCodigo(), miniaturaProducto);
                    selectorCantidad.setCantidad(listaDeItems.get(selectorCantidad.getCodigo()).getCantidadSelecccion());
                } else {
                    listaDeItems.put(miniaturaProducto.getCodigo(), miniaturaProducto);
                    contenedor_selector.add(selectorCantidad);
                }
            }
        }

        jLabel3.setText(String.valueOf(listaDeItems.size()));
    } catch (Exception e) {
        e.printStackTrace();
    } 
    
    
}

    
    
    private void lbl_impuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl_impuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_impuestoActionPerformed

    private void lbl_impuestoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_impuestoKeyReleased

        if (lbl_impuesto.getText().trim().isEmpty()) {
            impuesto = 0.00;
        } else {
            try {
                impuesto = Double.parseDouble(lbl_impuesto.getText().trim());
                impuesto = (impuesto / 100) * subTotal; // Convertir el porcentaje a decimal
            } catch (NumberFormatException e) {
                impuesto = 0.00; // Si hay un error al convertir, se establece en 0.00
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_impuestoKeyReleased

    
     
 public void Quitar_Elemento() {
    List<Component> paraEliminar = new ArrayList<>();
    double totalRestado = 0.0;
    jButton7.setEnabled(false);

    EntityManager em = null;
    EntityTransaction tx = null;
    List<Object[]> movimientosPendientes = new ArrayList<>();
    List<String> actividadesPendientes = new ArrayList<>();

    try {
        em = MonituxDBContext.getEntityManager();
        if (em == null || !em.isOpen()) {
            throw new IllegalStateException("EntityManager no disponible.");
        }

        tx = em.getTransaction();
        tx.begin();

        for (Component comp : contenedor_selector.getComponents()) {
            if (!(comp instanceof SelectorCantidad selector)) continue;

            String codigo = selector.getCodigo();
            if (!selector.isSeleccionado() || !listaDeItems.containsKey(codigo)) {
                if (!listaDeItems.containsKey(codigo)) selector.setBackground(Color.RED);
                continue;
            }

            Miniatura_Producto original = listaDeItems.get(codigo);
            Miniatura_Producto copia = Util.clonarControl(original);
            copia.setCantidadSelecccion(original.getCantidadSelecccion());
            copia.setUnidadesAgregar(copia.getCantidadSelecccion());

            listaDeItemsEliminar.put(codigo, copia);
            listaDeItems.remove(codigo);
            paraEliminar.add(selector);

            double cantidad = copia.getCantidadSelecccion();
            double precio = copia.producto.getPrecio_Costo();
            totalRestado += cantidad * precio;

            List<Compra_Detalle> detalles = em.createQuery(
                "SELECT cd FROM Compra_Detalle cd WHERE cd.Codigo = :codigo AND cd.Secuencial_Factura = :compra AND cd.Secuencial_Empresa = :empresa",
                Compra_Detalle.class)
                .setParameter("codigo", codigo)
                .setParameter("compra", Secuencial)
                .setParameter("empresa", Secuencial_Empresa)
                .getResultList();

            if (!detalles.isEmpty()) {
                em.remove(detalles.get(0));
            }

            if (!"Servicio".equalsIgnoreCase(copia.producto.getTipo())) {
                Producto producto = em.find(Producto.class, copia.producto.getSecuencial());
                if (producto != null) {
                    double existenciaAnterior = producto.getCantidad();

                    movimientosPendientes.add(new Object[] {
                        producto.getSecuencial(),
                        existenciaAnterior,
                        producto.getDescripcion(),
                        cantidad,
                        producto.getPrecio_Costo(),
                        producto.getPrecio_Venta(),
                        "Salida",
                        Secuencial_Empresa
                    });

                    producto.setCantidad(existenciaAnterior - cantidad);
                    em.merge(producto);
                }
            }

            actividadesPendientes.add(
                "Eliminó el Item: " + codigo + " de la Compra No. " + Secuencial + "\n" +
                "Registrado a: " + precio + ", cantidad: " + cantidad + "\n" +
                "Total: " + (cantidad * precio)
            );
        }

        for (Component comp : paraEliminar) {
            contenedor_selector.remove(comp);
        }

        Compra compra = em.find(Compra.class, Secuencial);
        if (compra != null) {
            compra.setTotal(compra.getTotal() - totalRestado);
            compra.setGran_Total(compra.getGran_Total() - totalRestado);
            em.merge(compra);
        }

        List<Cuentas_Pagar> cuentas = em.createQuery(
            "SELECT c FROM Cuentas_Pagar c WHERE c.Secuencial_Factura = :compra AND c.Secuencial_Empresa = :empresa",
            Cuentas_Pagar.class)
            .setParameter("compra", Secuencial)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        if (!cuentas.isEmpty()) {
            Cuentas_Pagar cuenta = cuentas.get(0);
            cuenta.setTotal(cuenta.getTotal() - totalRestado);
            cuenta.setGran_Total(cuenta.getGran_Total() - totalRestado);
            cuenta.setSaldo(cuenta.getSaldo() - totalRestado);
            em.merge(cuenta);
        }

        tx.commit();

        jLabel3.setText(String.valueOf(listaDeItems.size()));
        contenedor_selector.revalidate();
        contenedor_selector.repaint();

        JOptionPane.showMessageDialog(null, "Se ha modificado la compra y el elemento se ha retirado correctamente.");

    } catch (Exception ex) {
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        JOptionPane.showMessageDialog(null, "Error al quitar el elemento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    // Ejecutar Kardex y actividad fuera del contexto principal
    for (Object[] mov : movimientosPendientes) {
        Util.registrarMovimientoKardex(
            (int) mov[0],
            (double) mov[1],
            (String) mov[2],
            (double) mov[3],
            (double) mov[4],
            (double) mov[5],
            (String) mov[6],
            (int) mov[7]
        );
    }

    for (String actividad : actividadesPendientes) {
        Util.registrarActividad(Secuencial_Usuario, actividad, Secuencial_Empresa);
    }
}

    
    
    private void lbl_descuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_descuentoKeyReleased

        if (lbl_descuento.getText().trim().isEmpty()) {
            descuento = 0.00;
        } else {
            try {
                descuento = Double.parseDouble(lbl_descuento.getText().trim());
                descuento = (descuento / 100) * subTotal; // Convertir el porcentaje a decimal
            } catch (NumberFormatException e) {
                descuento = 0.00; // Si hay un error al convertir, se establece en 0.00
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_descuentoKeyReleased

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseMoved

        jLabel3.setText(String.valueOf(listaDeItems.size()));
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseMoved

    
     
   
   
    
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

      icono_carga.setVisible(true);

    List<Object[]> movimientosPendientes = new ArrayList<>();
    List<String> actividadesPendientes = new ArrayList<>();

    try {
        int secuencialCompra = Secuencial;

        int confirmResult = JOptionPane.showConfirmDialog(
            null,
            "¿Está seguro de eliminar la Compra No. " + secuencialCompra + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmResult != JOptionPane.YES_OPTION) return;

        EntityManager em = MonituxDBContext.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Compra compra = em.find(Compra.class, secuencialCompra);
        if (compra == null) {
            JOptionPane.showMessageDialog(null, "Compra no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            tx.rollback();
            return;
        }

        Egreso egresoAsociado = em.createQuery(
            "SELECT e FROM Egreso e WHERE e.Secuencial_Factura = :factura AND e.Secuencial_Empresa = :empresa",
            Egreso.class)
            .setParameter("factura", secuencialCompra)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (egresoAsociado != null) {
            em.remove(egresoAsociado);
            actividadesPendientes.add("Se eliminó el egreso relacionado a la Compra No. " + secuencialCompra);
        }

        Cuentas_Pagar ctap = em.createQuery(
            "SELECT c FROM Cuentas_Pagar c WHERE c.Secuencial_Factura = :compra AND c.Secuencial_Proveedor = :proveedor AND c.Secuencial_Empresa = :empresa",
            Cuentas_Pagar.class)
            .setParameter("compra", secuencialCompra)
            .setParameter("proveedor", Secuencial_Proveedor)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (ctap != null) {
            em.remove(ctap);
        }

        List<Compra_Detalle> detalles = em.createQuery(
            "SELECT cd FROM Compra_Detalle cd WHERE cd.Secuencial_Factura = :factura",
            Compra_Detalle.class)
            .setParameter("factura", secuencialCompra)
            .getResultList();

        for (Compra_Detalle detalle : detalles) {
            Producto producto = em.createQuery(
                "SELECT p FROM Producto p WHERE p.Codigo = :codigo",
                Producto.class)
                .setParameter("codigo", detalle.getCodigo())
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (producto != null && !"Servicio".equals(producto.getTipo()) && detalle.getCantidad() != null) {
                double cantidadRetirada = detalle.getCantidad();

                movimientosPendientes.add(new Object[] {
                    producto.getSecuencial(),
                    producto.getCantidad(),
                    producto.getDescripcion(),
                    cantidadRetirada,
                    producto.getPrecio_Costo(),
                    producto.getPrecio_Venta(),
                    "Salida",
                    Secuencial_Empresa
                });

                producto.setCantidad(producto.getCantidad() - cantidadRetirada);
                em.merge(producto);
            }
        }

        detalles.forEach(em::remove);
        em.remove(compra);

        actividadesPendientes.add(
            "Eliminó la Compra No. " + secuencialCompra + " con " + detalles.size() +
            " productos. Registrada por un monto de " + compra.getGran_Total()
        );

        tx.commit();

        for (Object[] mov : movimientosPendientes) {
            Util.registrarMovimientoKardex(
                (int) mov[0],
                (double) mov[1],
                (String) mov[2],
                (double) mov[3],
                (double) mov[4],
                (double) mov[5],
                (String) mov[6],
                (int) mov[7]
            );
        }

        for (String actividad : actividadesPendientes) {
            Util.registrarActividad(Secuencial_Usuario, actividad, Secuencial_Empresa);
        }

        JOptionPane.showMessageDialog(
            null,
            "Compra No. " + secuencialCompra + " eliminada correctamente.",
            "Operación Exitosa",
            JOptionPane.INFORMATION_MESSAGE
        );

        this.dispose();
        form.cargar_Datos_Compra();

        System.out.println("✅ Compra eliminada correctamente: " + secuencialCompra);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(
            null,
            "Error al eliminar compra: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
        ex.printStackTrace();
    } finally {
        icono_carga.setVisible(false);
    }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

      
     
        Quitar_Elemento();
        
        
         Actualizar_Detalle();
         
         //cargarItems();
         
        
    }//GEN-LAST:event_jButton5ActionPerformed

    
    
    
    
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        Actualizar_Detalle();
       

    }//GEN-LAST:event_jButton6ActionPerformed

    
    
    
   
   
   
   
    
    
    
    //*****************************************
  
    //Esto es De Ayer No tocar de aqui para abajo
    public void Actualizar_Detalle(){
    
         //*********************************
jButton7.setEnabled(true);
        total = 0;
        subTotal = 0;
        //    impuesto = 0;
        //    descuento = 0;
        //    otrosCargos = 0;

        // Reiniciar visualmente
        DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"Codigo", "Descripcion", "Cantidad", "Precio", "Total", "SP"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Solo lectura
            }
        };

        jTable1.setModel(modeloTabla);
        jTable1.setRowHeight(24); // Altura de fila
        jTable1.setFont(new Font("Arial", Font.PLAIN, 13)); // Fuente legible
        jTable1.setGridColor(Color.LIGHT_GRAY); // Líneas suaves
        jTable1.setShowGrid(true); // Mostrar líneas

        lbl_subTotal.setText("0.00");
        lbl_total.setText("0.00");

        // Actualizar cantidades desde los selectores
        for (Component comp : contenedor_selector.getComponents()) {
            if (comp instanceof SelectorCantidad selector) {
                String codigo = selector.getCodigo();
                if (listaDeItems.containsKey(codigo)) {
                    Miniatura_Producto item = listaDeItems.get(codigo);
                    item.cantidadSelecccion = selector.getCantidadSeleccionada();
                }
            }
        }

        // Procesar ítems seleccionados
        for (String clave : listaDeItems.keySet()) {
            Miniatura_Producto item = listaDeItems.get(clave);

            if (item.cantidadSelecccion != 0) {
                double cantidad = item.cantidadSelecccion;
                double precio = item.producto.getPrecio_Costo();
                double totalItem = cantidad * precio;

                modeloTabla.addRow(new Object[] {
                    item.producto.getCodigo(),
                    item.producto.getDescripcion(),
                    cantidad,
                    precio,
                    totalItem,
                    item.producto.getSecuencial()
                });

                subTotal += totalItem;

            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "Revise la cantidad que desea agregar.\n -- " + item.producto.getCodigo() + " --\n" + item.producto.getDescripcion(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        // Actualizar totales
        ActualizarNumeros();

        //********************************
    
    }
    
//    
// public Object[] obtenerDatosVenta(EntityManager em, int secuencialEmpresa, int secuencialFactura) {
//    return em.createQuery(
//        "SELECT v.Tipo, c.Fecha_Vencimiento, v.Forma_Pago,v.Otros_Cargos " +
//        "FROM Cuentas_Cobrar c JOIN c.Venta v " +
//        "WHERE v.Secuencial_Empresa = :empresa AND v.Secuencial = :factura",
//        Object[].class
//    )
//    .setParameter("empresa", secuencialEmpresa)
//    .setParameter("factura", secuencialFactura)
//    .getResultStream()
//    .findFirst()
//    .orElse(null);
//}

 public Object[] obtenerDatosCompra(EntityManager em, int secuencialEmpresa, int secuencialCompra) {
    // Paso 1: Obtener los datos base desde Compra
    Object[] datosCompra = em.createQuery(
        "SELECT c.Tipo, c.Forma_Pago, c.Otros_Cargos " +
        "FROM Compra c " +
        "WHERE c.Secuencial_Empresa = :empresa AND c.Secuencial = :compra",
        Object[].class
    )
    .setParameter("empresa", secuencialEmpresa)
    .setParameter("compra", secuencialCompra)
    .getResultStream()
    .findFirst()
    .orElse(null);

    if (datosCompra == null) {
        return null; // No se encontró la compra
    }

    String tipo = (String) datosCompra[0];
    String formaPago = (String) datosCompra[1];
    Double otrosCargos = (Double) datosCompra[2];

    // Paso 2: Si es crédito, obtener la fecha de vencimiento desde Cuentas_Pagar
    String fechaVencimiento = null;
    if ("Credito".equalsIgnoreCase(tipo)) {
        fechaVencimiento = em.createQuery(
            "SELECT cp.Fecha_Vencimiento " +
            "FROM Cuentas_Pagar cp " +
            "WHERE cp.Secuencial_Empresa = :empresa AND cp.Secuencial = :compra",
            String.class
        )
        .setParameter("empresa", secuencialEmpresa)
        .setParameter("compra", secuencialCompra)
        .getResultStream()
        .findFirst()
        .orElse(null);
    }

    // Retornar todos los datos en un arreglo
    return new Object[] { tipo, fechaVencimiento, formaPago, otrosCargos };
}
 
    
    
 private void ActualizarNumeros() {
      jLabel3.setText(String.valueOf(listaDeItems.size()));
     total = subTotal + impuesto + otrosCargos - descuento;

    lbl_subTotal.setText(String.format("%.2f", subTotal));
    //lbl_impuesto.setText(String.format("%.2f", impuesto));
    lbl_otrosCargos.setText(String.format("%.2f", otrosCargos));
    //lbl_descuento.setText(String.format("%.2f", descuento));
    lbl_total.setText(String.format("%.2f", total));
}

    
 
 //*************
public void cargar_Items(int secuencialEmpresa, JPanel contenedor, JPanel contenedor_selector) {
    icono_carga.setVisible(true);

    contenedor.setLayout(new GridBagLayout());
    contenedor_selector.setLayout(new GridLayout(0, 1, 5, 5));
    contenedor.removeAll();

    EntityManager em = null;
    List<Producto> productos = new ArrayList<>();

    try {
        em = MonituxDBContext.getEntityManager();
        if (em == null || !em.isOpen()) {
            throw new IllegalStateException("EntityManager no disponible.");
        }

        productos = em.createQuery(
            "SELECT p FROM Producto p WHERE p.Secuencial_Empresa = :empresa", Producto.class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.NORTHWEST;

    int col = 0, row = 0;

    for (Producto producto : productos) {
        Miniatura_Producto miniatura = new Miniatura_Producto(producto, false);
        miniatura.setPreferredSize(new Dimension(120, 170));

        miniatura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SelectorCantidad selector = selectoresCantidad.computeIfAbsent(
                    producto.getCodigo(),
                    codigo -> new SelectorCantidad(codigo, 0)
                );

                if (!listaDeItems.containsKey(producto.getCodigo())) {
                    listaDeItems.put(producto.getCodigo(), miniatura);
                    if (!Arrays.asList(contenedor_selector.getComponents()).contains(selector)) {
                        contenedor_selector.add(selector);
                    }
                }

                contenedor_selector.revalidate();
                contenedor_selector.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        String comentario = miniatura.cargarComentario(); // Este método debe abrir su propio EM si lo necesita
        String descripcion = miniatura.producto.getDescripcion();
        miniatura.setToolTipText(
            comentario != null
                ? "<html><b>" + descripcion + "</b><br>" + comentario + "</html>"
                : "<html><b>" + descripcion + "</b><br></html>"
        );

        gbc.gridx = col;
        gbc.gridy = row;
        contenedor.add(miniatura, gbc);

        col++;
        if (col == 3) {
            col = 0;
            row++;
        }
    }

    contenedor.revalidate();
    contenedor.repaint();
    icono_carga.setVisible(false);
}

 
    public void cargarItems() {
    contenedor.removeAll();
    icono_carga.setVisible(true); // Mostrar ícono de carga

    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() {
            EntityManager em = MonituxDBContext.getEntityManager();
            try {
                cargar_Items(Secuencial_Empresa, contenedor, contenedor_selector);
                System.out.println("✅ Items cargados correctamente para empresa: " + Secuencial_Empresa);
            } catch (Exception ex) {
                System.err.println("❌ Error al cargar items: " + ex.getMessage());
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void done() {
            icono_carga.setVisible(false); // Ocultar ícono de carga
            revalidate();
            repaint();
        }
    };

    worker.execute(); // Ejecutar en segundo plano
}

    
    
    public void cargarItems_Filtrados( // Original
    int secuencialEmpresa,
    JComboBox<String> comboFiltro,
    JTextField campoValorFiltro,
    JPanel contenedor,
    JPanel contenedor_selector,
    EntityManager entityManager
) {
    icono_carga.setVisible(true);

    contenedor.setLayout(new GridLayout(0, 3, 5, 5));
    contenedor_selector.setLayout(new GridLayout(0, 1, 5, 5));
    contenedor.removeAll();

    String campoFiltro = (String) comboFiltro.getSelectedItem();
    String valorFiltro = campoValorFiltro.getText();

    boolean aplicarFiltro = campoFiltro != null && !campoFiltro.trim().isEmpty()
                         && valorFiltro != null && !valorFiltro.trim().isEmpty();

    String jpql = "SELECT p FROM Producto p WHERE p.Secuencial_Empresa = :empresa"
                + (aplicarFiltro ? " AND LOWER(p." + campoFiltro + ") LIKE :valorFiltro" : "");

    TypedQuery<Producto> query = entityManager.createQuery(jpql, Producto.class);
    query.setParameter("empresa", secuencialEmpresa);
    if (aplicarFiltro) {
        query.setParameter("valorFiltro", "%" + valorFiltro.toLowerCase() + "%");
    }

    for (Producto producto : query.getResultList()) {
        ImageIcon imagenIcon = (producto.getImagen() != null && producto.getImagen().length > 0)
            ? new ImageIcon(producto.getImagen())
            : new ImageIcon(getClass().getResource("/icons/no-image-icon-10.png"));


        Miniatura_Producto miniatura = new Miniatura_Producto(producto,true);

        miniatura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SelectorCantidad selector = selectoresCantidad.computeIfAbsent(
                    producto.getCodigo(),
                    codigo -> new SelectorCantidad(codigo, 0)
                );

                selector.setCantidad(0);

                if (!listaDeItems.containsKey(producto.getCodigo())) {
                    listaDeItems.put(producto.getCodigo(), miniatura);

                    if (!Arrays.asList(contenedor_selector.getComponents()).contains(selector)) {
                        contenedor_selector.add(selector);
                    }
                }

                contenedor_selector.revalidate();
                contenedor_selector.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        String comentario = miniatura.cargarComentario();
        String descripcion = miniatura.producto.getDescripcion();
        miniatura.setToolTipText("<html><b>" + descripcion + "</b><br>" + (comentario != null ? comentario : "") + "</html>");

        contenedor.add(miniatura);
    }

    contenedor.revalidate();
    contenedor.repaint();
    icono_carga.setVisible(false);
}
    
    
    
    public void cargarItemsFiltrados(
    int secuencialEmpresa,
    JComboBox<String> comboFiltro,
    JTextField campoValorFiltro,
    JPanel contenedor,
    JPanel contenedor_selector,
    EntityManager entityManager
) {
    icono_carga.setVisible(true);

    // Usamos GridBagLayout para evitar el ajuste automático de tamaño
    contenedor.setLayout(new GridBagLayout());
    contenedor_selector.setLayout(new GridLayout(0, 1, 5, 5));
    contenedor.removeAll();
    

    String campoFiltro = (String) comboFiltro.getSelectedItem();
    String valorFiltro = campoValorFiltro.getText();

    boolean aplicarFiltro = campoFiltro != null && !campoFiltro.trim().isEmpty()
                         && valorFiltro != null && !valorFiltro.trim().isEmpty();

    String jpql = "SELECT p FROM Producto p WHERE p.Secuencial_Empresa = :empresa"
                + (aplicarFiltro ? " AND LOWER(p." + campoFiltro + ") LIKE :valorFiltro" : "");

    TypedQuery<Producto> query = entityManager.createQuery(jpql, Producto.class);
    query.setParameter("empresa", secuencialEmpresa);
    if (aplicarFiltro) {
        query.setParameter("valorFiltro", "%" + valorFiltro.toLowerCase() + "%");
    }

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre miniaturas
    gbc.fill = GridBagConstraints.NONE; // No expandir
    gbc.anchor = GridBagConstraints.NORTHWEST;

    int col = 0, row = 0;

    for (Producto producto : query.getResultList()) {
        Miniatura_Producto miniatura = new Miniatura_Producto(producto, true);
        miniatura.setPreferredSize(new Dimension(120, 170)); // Tamaño fijo

        miniatura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SelectorCantidad selector = selectoresCantidad.computeIfAbsent(
                    producto.getCodigo(),
                    codigo -> new SelectorCantidad(codigo, 0)
                );

                selector.setCantidad(0);

                if (!listaDeItems.containsKey(producto.getCodigo())) {
                    listaDeItems.put(producto.getCodigo(), miniatura);

                    if (!Arrays.asList(contenedor_selector.getComponents()).contains(selector)) {
                        contenedor_selector.add(selector);
                    }
                }

                contenedor_selector.revalidate();
                contenedor_selector.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        String comentario = miniatura.cargarComentario();
        String descripcion = miniatura.producto.getDescripcion();
        miniatura.setToolTipText("<html><b>" + descripcion + "</b><br>" + (comentario != null ? comentario : "") + "</html>");

        // Posicionamiento en la cuadrícula
        gbc.gridx = col;
        gbc.gridy = row;
        contenedor.add(miniatura, gbc);

        col++;
        if (col == 3) {
            col = 0;
            row++;
        }
    }

    contenedor.revalidate();
    contenedor.repaint();
    icono_carga.setVisible(false);
}


    
    
 
 //*************
 
 
 
 
    
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        EntityManager em = MonituxDBContext.getEntityManager();

        try {
            cargarItemsFiltrados(Secuencial_Empresa, jComboBox2, jTextField1, contenedor, contenedor_selector, em);
            System.out.println("✅ Items filtrados correctamente para empresa: " + Secuencial_Empresa);
        } catch (Exception ex) {
            System.err.println("❌ Error al filtrar items: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        V_Producto form = new V_Producto();
            form.setOnProductoEditado(() -> cargarItems());
            form.setVisible(true);
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        EntityManager em = MonituxDBContext.getEntityManager();

        try {
            cargarItemsFiltrados(Secuencial_Empresa, jComboBox2, jTextField3, contenedor, contenedor_selector, em);
            System.out.println("✅ Items filtrados correctamente con texto: " + jTextField3.getText());
        } catch (Exception ex) {
            System.err.println("❌ Error al filtrar items: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3KeyReleased

    
   
   
    
 
    
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        Component invocador = menu_contextual.getInvoker();
        if (invocador instanceof Miniatura_Producto) {
            Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

            miniatura.actualizarImagenLocal();
            cargarItems();

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        Component invocador = menu_contextual.getInvoker();
        if (invocador instanceof Miniatura_Producto) {
            Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

            miniatura.actualizarImagenWeb();
            cargarItems();

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        
        Component invocador = menu_contextual.getInvoker();
if (invocador instanceof Miniatura_Producto) {
    Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

    new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            miniatura.actualizarImagenCamara(); // operación potencialmente pesada
            cargarItems(); // si también implica carga de datos, mejor aquí
            return null;
        }

        @Override
        protected void done() {
            // Si necesitas actualizar componentes visuales, hazlo aquí
            // Por ejemplo: panel.repaint(); o actualizar etiquetas
        }
    }.execute();
}

        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed

        Component invocador = menu_contextual.getInvoker();
        if (invocador instanceof Miniatura_Producto) {
            Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

            miniatura.Agregar_Comentario(miniatura.getComentario());

            cargarItems();

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

        Component invocador = menu_contextual.getInvoker();
        if (invocador instanceof Miniatura_Producto) {
            Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

            miniatura.actualizarProductoAgregarUnidades();

            cargarItems();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

        Component invocador = menu_contextual.getInvoker();
        if (invocador instanceof Miniatura_Producto) {
            Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

            miniatura.actualizarProductoRetirarUnidades();

            cargarItems();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        Component invocador = menu_contextual.getInvoker();
        if (invocador instanceof Miniatura_Producto) {
            Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

            V_Producto form = new V_Producto(false, miniatura.producto);
            form.setOnProductoEditado(() -> cargarItems());
            form.setVisible(true);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed

        Component invocador = menu_contextual.getInvoker();
        if (invocador instanceof Miniatura_Producto) {
            Miniatura_Producto miniatura = (Miniatura_Producto) invocador;

            byte[] datosImagen = miniatura.producto.getImagen(); // suponiendo que devuelve byte[]

            ImageIcon imagen = null;

            if (datosImagen != null && datosImagen.length > 0) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(datosImagen);
                    BufferedImage bufferedImage = ImageIO.read(bis);
                    imagen = new ImageIcon(bufferedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Puedes cargar una imagen por defecto si falla
                    imagen = new ImageIcon(getClass().getResource("/icons/no-image-icon-10.png"));
                }
            } else {
                // Imagen por defecto si no hay datos
                imagen = new ImageIcon(getClass().getResource("/icons/no-image-icon-10.png"));
            }

            try {
                V_Vista_Ampliada v_Vista_Ampliada = new V_Vista_Ampliada(miniatura.producto.getCodigo(),
                    miniatura.producto.getDescripcion(), imagen);
                v_Vista_Ampliada.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cargar la vista ampliada.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened


    Object[] resultado = obtenerDatosCompra(em, Secuencial_Empresa, Secuencial);


    
    if (resultado != null) {
    String tipo = (String) resultado[0];
    String fechaVencimientoStr = (String) resultado[1];
    String formaPago = (String) resultado[2];
    otrosCargos=(double) resultado [3];
    lbl_otrosCargos.setText(String.valueOf(otrosCargos));
    
    

    jComboBox3.setSelectedItem(tipo);
    jComboBox4.setSelectedItem(formaPago);
    jLabel6.setText(fechaVencimientoStr);
   
    System.out.println("Tipo: " + tipo);
    System.out.println("Fecha_Vencimiento: " + fechaVencimientoStr);
    System.out.println("Forma de pago: " + formaPago);
    System.out.println("Otros Gastos: " + otrosCargos);
    
} else {
    System.err.println("No se encontraron datos de venta para la factura " + Secuencial);
}

        
        
        
        
        this.setTitle("Factura No: " + Secuencial);



lbl_descuento.setText(String.format("%.2f", descuento));
lbl_impuesto.setText(String.format("%.2f", impuesto));
lbl_otrosCargos.setText(String.format("%.2f", otrosCargos));
lbl_subTotal.setText(String.format("%.2f", subTotal));
lbl_total.setText(String.format("%.2f", total));





//comboBox2.setSelectedIndex(0);
//comboBox3.setSelectedIndex(0);
//comboBox1.setSelectedIndex(0);

//configurarDataGridView();




cargarItemsDesdeLista(V_Compras_Ventas.lista,Secuencial_Empresa,contenedor,contenedor_selector);

ActualizarNumeros(); // Actualizar totales y visuales

        Actualizar_Detalle();
        
        this.setLocationRelativeTo(null);
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    
public void cargarItemsDesdeLista(Map<String, Double> lista, int secuencialEmpresa, JPanel contenedor, JPanel contenedor_selector) {
    icono_carga.setVisible(true);

    contenedor.setLayout(new GridBagLayout());
    contenedor_selector.setLayout(new GridLayout(0, 1, 5, 5));

    contenedor.removeAll();
    contenedor_selector.removeAll();
    listaDeItems.clear();
    selectoresCantidad.clear();

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.NORTHWEST;

    int col = 0, row = 0;

    for (Map.Entry<String, Double> itemC : lista.entrySet()) {
        EntityManager em = null;
        List<Producto> productos = new ArrayList<>();

        try {
            em = MonituxDBContext.getEntityManager();
            if (em == null || !em.isOpen()) {
                throw new IllegalStateException("EntityManager no disponible.");
            }

            productos = em.createQuery(
                "SELECT p FROM Producto p WHERE p.Codigo = :codigo AND p.Secuencial_Empresa = :empresa", Producto.class)
                .setParameter("codigo", itemC.getKey())
                .setParameter("empresa", secuencialEmpresa)
                .getResultList();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar producto: " + itemC.getKey() + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        for (Producto producto : productos) {
            Miniatura_Producto miniatura = new Miniatura_Producto(producto, true);
            miniatura.setCantidadSelecccionItem(itemC.getValue().intValue());
            miniatura.setPreferredSize(new Dimension(120, 170));

            SelectorCantidad selector = new SelectorCantidad(producto.getCodigo(), itemC.getValue().intValue());
            selectoresCantidad.put(producto.getCodigo(), selector);

            listaDeItems.put(producto.getCodigo(), miniatura);
            contenedor_selector.add(selector);

            String comentario = miniatura.cargarComentario(); // Este método debe abrir su propio EM internamente
            miniatura.setToolTipText(
                comentario != null
                    ? "<html><b>" + producto.getDescripcion() + "</b><br>" + comentario + "</html>"
                    : "<html><b>" + producto.getDescripcion() + "</b><br></html>"
            );

            miniatura.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        menu_contextual.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });

            gbc.gridx = col;
            gbc.gridy = row;
            contenedor.add(miniatura, gbc);

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }

    contenedor.revalidate();
    contenedor.repaint();
    contenedor_selector.revalidate();
    contenedor_selector.repaint();
    icono_carga.setVisible(false);
}
   
    
    private void contenedorMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contenedorMouseMoved
        // TODO add your handling code here:
        jLabel3.setText(String.valueOf(listaDeItems.size()));
    }//GEN-LAST:event_contenedorMouseMoved

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        form.cargar_Datos_Compra();
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void comboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboProveedorActionPerformed

    private void comboProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboProveedorMouseClicked

        // Util.llenarComboCliente(comboCliente,Secuencial_Empresa);

        // TODO add your handling code here:
    }//GEN-LAST:event_comboProveedorMouseClicked

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboProveedor;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel contenedor_selector;
    public javax.swing.JLabel icono_carga;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    public javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField lbl_descuento;
    private javax.swing.JTextField lbl_impuesto;
    private javax.swing.JTextField lbl_otrosCargos;
    private javax.swing.JLabel lbl_subTotal;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPopupMenu menu_contextual;
    // End of variables declaration//GEN-END:variables
}
