/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Cliente;
import com.monituxpos.Clases.Compra;
import com.monituxpos.Clases.Compra_Detalle;
import com.monituxpos.Clases.Miniatura_Producto;
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Proveedor;
import com.monituxpos.Clases.Util;
import com.monituxpos.Clases.Venta;
import com.monituxpos.Clases.Venta_Detalle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Compras_Ventas extends javax.swing.JPanel {
public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
public int Secuencial_Venta;
public int Secuencial_Compra;

public int Secuencial_Proveedor;

public int Secuencial_Cliente;

  public  String cliente_seleccionado;
  public  String proveedor_seleccionado;



public static Map<String, Double> lista = new HashMap<>();

    /**
     * Creates new form V_Compras_Ventas
     */
   
    public V_Compras_Ventas() {
    initComponents();

    // Cargar combos
    Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);
    Util.llenarComboProveedor(jComboBox2, Secuencial_Empresa);

    // Configurar tablas
    configurarTablaVenta(jTable1);
    configurarTablaDetalle(jTable2);
    configurarTablaCompra(jTable3);
    configurarTablaDetalle(jTable4);

    // Limpiar tabla de detalles de venta
    ((DefaultTableModel) jTable2.getModel()).setRowCount(0);

    // Filtrar ventas por cliente seleccionado
    String seleccionado = jComboBox1.getSelectedItem().toString();
    int secuencialCliente = Integer.parseInt(seleccionado.split("-")[0].trim());
    filtrarVenta(secuencialCliente, jTable1, jTable2);

    // Limpiar tabla de detalles de compra
    ((DefaultTableModel) jTable4.getModel()).setRowCount(0);

    // Filtrar compras por proveedor seleccionado (CORREGIDO)
    String seleccionadoc = jComboBox2.getSelectedItem().toString();
    int secuencialProveedor = Integer.parseInt(seleccionadoc.split("-")[0].trim());
    filtrarCompra(secuencialProveedor, jTable3, jTable4);

    // Eventos de selección en tabla de ventas
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int fila = jTable1.getSelectedRow();
            if (fila != -1) {
                Object valor = jTable1.getValueAt(fila, 0);
                if (valor != null) {
                    Secuencial_Venta = Integer.parseInt(valor.toString());
                    filtrarDetalleVenta(Secuencial_Venta, jTable2);
                }
            }
        }
    });

    jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            int fila = jTable1.getSelectedRow();
            if (fila != -1) {
                Object valor = jTable1.getValueAt(fila, 0);
                if (valor != null) {
                    try {
                        Secuencial_Venta = Integer.parseInt(valor.toString());
                        filtrarDetalleVenta(Secuencial_Venta, jTable2);
                    } catch (NumberFormatException ex) {
                        System.err.println("Error al convertir el valor a entero: " + valor);
                    }
                }
            }
        }
    });

    // Eventos de selección en tabla de compras
    jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int fila = jTable3.getSelectedRow();
            if (fila != -1) {
                Object valor = jTable3.getValueAt(fila, 0);
                if (valor != null) {
                    Secuencial_Compra = Integer.parseInt(valor.toString());
                    filtrarDetalleCompra(Secuencial_Compra, jTable4);
                }
            }
        }
    });

    jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            int fila = jTable3.getSelectedRow();
            if (fila != -1) {
                Object valor = jTable3.getValueAt(fila, 0);
                if (valor != null) {
                    try {
                        Secuencial_Compra = Integer.parseInt(valor.toString());
                        filtrarDetalleCompra(Secuencial_Compra, jTable4);
                    } catch (NumberFormatException ex) {
                        System.err.println("Error al convertir el valor a entero: " + valor);
                    }
                }
            }
        }
    });
    
    
      if ("Vendedor".equals(V_Menu_Principal.getAcceso_Usuario())) {
    jButton3.setVisible(false);
        jButton6.setVisible(false);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel1.setBackground(new java.awt.Color(35, 32, 45));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 768));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 168, 107));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ventas");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, -1, 30));

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
        jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox1PropertyChange(evt);
            }
        });
        jPanel2.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 190, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Cliente:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 50, 20));

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
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 100, -1));

        jLabel6.setForeground(new java.awt.Color(102, 0, 204));
        jLabel6.setText("Telefono:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 60, 20));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Facturas:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, -1, -1));

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 380, 160));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Detalle:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable2);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 380, 180));

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, -1, -1));

        jButton2.setText("Enviar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 480, -1, -1));

        jButton3.setText("Modificar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 480, 90, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 400, 520));

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Compras");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, -1, -1));
        jLabel3.getAccessibleContext().setAccessibleName("");
        jLabel3.getAccessibleContext().setAccessibleDescription("");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Facturas:");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable3);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 380, 160));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Detalle:");
        jPanel4.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, -1, -1));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable4);

        jPanel4.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 380, 180));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Proveedor:");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 70, 30));

        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel4.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 190, -1));

        jLabel12.setForeground(new java.awt.Color(51, 255, 51));
        jLabel12.setText("Telefono:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 60, 20));

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel4.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 100, -1));

        jButton4.setText("Imprimir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, -1, -1));

        jButton5.setText("Enviar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 480, -1, -1));

        jButton6.setText("Modificar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 480, 90, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 394, 520));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gestión de Facturas");
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 50));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("🔔 Atención: Modificar una factura es una acción delicada. Los cambios realizados no se pueden deshacer. Proceda con precaución.");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, 771, 20));

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 830, 620));
    }// </editor-fold>//GEN-END:initComponents

    
    
   public void filtrarDetalleVenta(int Secuencial_Venta, JTable tablaDetalle) {
    if (tablaDetalle.getColumnCount() == 0) {
        configurarTablaDetalle(tablaDetalle); // Método externo que define columnas
    }

    DefaultTableModel modelo = (DefaultTableModel) tablaDetalle.getModel();
    modelo.setRowCount(0);

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Venta_Detalle> detalles = em.createQuery(
            "SELECT d FROM Venta_Detalle d WHERE d.Secuencial_Factura = :venta AND d.Secuencial_Empresa = :empresa",
            Venta_Detalle.class)
            .setParameter("venta", Secuencial_Venta)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Venta_Detalle item : detalles) {
            modelo.addRow(new Object[] {
                item.getSecuencial(),
                item.getCodigo(),
                item.getDescripcion(),
                item.getCantidad(),
                item.getPrecio(),
                redondear(item.getTotal()),
                item.getSecuencial_Producto(),
                item.getTipo()
            });
        }

        tablaDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        System.out.println("✅ Detalles de venta filtrados correctamente para factura: " + Secuencial_Venta);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "❌ Error al filtrar detalles de venta: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    
       private double redondear(Double valor) {
    return Math.round(valor * 100.0) / 100.0;
}

    
      public void filtrarDetalleCompra(int secuencialCompra, JTable tablaDetalle) {
    if (tablaDetalle.getColumnCount() == 0) {
        configurarTablaDetalle(tablaDetalle); // Método externo que define columnas
    }

    DefaultTableModel modelo = (DefaultTableModel) tablaDetalle.getModel();
    modelo.setRowCount(0);

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Compra_Detalle> detalles = em.createQuery(
            "SELECT d FROM Compra_Detalle d WHERE d.Secuencial_Factura = :compra AND d.Secuencial_Empresa = :empresa",
            Compra_Detalle.class)
            .setParameter("compra", secuencialCompra)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Compra_Detalle item : detalles) {
            modelo.addRow(new Object[] {
                item.getSecuencial(),
                item.getCodigo(),
                item.getDescripcion(),
                item.getCantidad(),
                item.getPrecio(),
                redondear(item.getTotal()),
                item.getSecuencial_Producto(),
                item.getTipo()
            });
        }

        tablaDetalle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        System.out.println("✅ Detalles de compra filtrados correctamente para factura: " + secuencialCompra);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "❌ Error al filtrar detalles de compra: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

       
    
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String telefono = jTextField1.getText().trim();

        if (telefono.isEmpty()) {
            Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);
        } else {
            Util.llenarComboClientePorTelefono(jComboBox1, telefono, Secuencial_Empresa);
        }
    }
    }//GEN-LAST:event_jTextField1KeyReleased

     public void configurarTablaDetalle(JTable tabla) {
    // Habilitar la tabla
    tabla.setEnabled(true);

    // Centrar contenido de las celdas
    DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
    centrado.setHorizontalAlignment(SwingConstants.CENTER);
    tabla.setDefaultRenderer(Object.class, centrado);

    // Definir columnas
    String[] columnas = {
        "S", "Codigo", "Descripción", "Cantidad", "Precio",
        "Total", "SP", "Tipo"
    };

    DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Tabla de solo lectura
        }
    };

    tabla.setModel(modelo);

    // Ajustes visuales
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

     public void cargar_Datos_Venta(){
     
         cargarDatosVenta(jTable1);
         DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
         modelo.setRowCount(0);

         
     }
     
     
      public void cargar_Datos_Compra(){
     
         cargarDatosCompra(jTable3);
         DefaultTableModel modelo = (DefaultTableModel) jTable4.getModel();
         modelo.setRowCount(0);

         
     }
    
    
      public void cargarDatosVenta(JTable tabla) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Venta> ventas = em.createQuery(
            "SELECT v FROM Venta v WHERE v.Secuencial_Empresa = :empresa AND v.Secuencial_Cliente = :cliente",
            Venta.class)
            .setParameter("empresa", Secuencial_Empresa)
            .setParameter("cliente", Secuencial_Cliente)
            .getResultList();

        for (Venta venta : ventas) {
            modelo.addRow(new Object[] {
                venta.getSecuencial(),
                venta.getFecha(),
                venta.getTotal(),
                venta.getGran_Total(),
                venta.getSecuencial_Cliente()
            });
        }

        System.out.println("✅ Ventas cargadas correctamente para cliente: " + Secuencial_Cliente);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "❌ Error al cargar ventas: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

      
      
   public void cargarDatosCompra(JTable tabla) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Compra> compras = em.createQuery(
            "SELECT c FROM Compra c WHERE c.Secuencial_Empresa = :empresa AND c.Secuencial_Proveedor = :proveedor", Compra.class)
            .setParameter("empresa", Secuencial_Empresa)
            .setParameter("proveedor", Secuencial_Proveedor)
            .getResultList();

        for (Compra compra : compras) {
            modelo.addRow(new Object[] {
                compra.getSecuencial(),
                compra.getFecha(),
                compra.getTotal(),
                compra.getGran_Total(),
                compra.getSecuencial_Proveedor()
            });
        }

        System.out.println("✅ Compras cargadas correctamente para proveedor: " + Secuencial_Proveedor);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "❌ Error al cargar compras: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    public void filtrarVenta(int secuencialCliente, JTable tablaVenta, JTable tablaDetalle) {
    // Verificar y configurar columnas si no existen
    if (tablaVenta.getColumnCount() == 0) {
        configurarTablaVenta(tablaVenta); // Método externo que define columnas
    }

    DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
    modelo.setRowCount(0);

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Venta> ventas = em.createQuery(
            "SELECT v FROM Venta v WHERE v.Secuencial_Cliente = :cliente AND v.Secuencial_Empresa = :empresa",
            Venta.class)
            .setParameter("cliente", secuencialCliente)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Venta venta : ventas) {
            modelo.addRow(new Object[] {
                venta.getSecuencial(),
                venta.getFecha(),
                venta.getTotal(),
                venta.getGran_Total(),
                venta.getSecuencial_Cliente()
            });
        }

        if (modelo.getRowCount() == 0) {
            ((DefaultTableModel) tablaDetalle.getModel()).setRowCount(0);
        }

        tablaVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        System.out.println("✅ Ventas filtradas correctamente para cliente: " + secuencialCliente);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "❌ Error al filtrar ventas: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    
    
    public void filtrarCompra(int secuencialProveedor, JTable tablaCompra, JTable tablaDetalle) {
    if (tablaCompra.getColumnCount() == 0) {
        configurarTablaCompra(tablaCompra); // Método externo que define columnas
    }

    DefaultTableModel modelo = (DefaultTableModel) tablaCompra.getModel();
    modelo.setRowCount(0);

    EntityManager em = MonituxDBContext.getEntityManager();

    try {
        List<Compra> compras = em.createQuery(
            "SELECT c FROM Compra c WHERE c.Secuencial_Proveedor = :proveedor AND c.Secuencial_Empresa = :empresa",
            Compra.class)
            .setParameter("proveedor", secuencialProveedor)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Compra compra : compras) {
            modelo.addRow(new Object[] {
                compra.getSecuencial(),
                compra.getFecha(),
                compra.getTotal(),
                compra.getGran_Total(),
                compra.getSecuencial_Proveedor()
            });
        }

        if (modelo.getRowCount() == 0) {
            ((DefaultTableModel) tablaDetalle.getModel()).setRowCount(0);
        }

        tablaCompra.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        System.out.println("✅ Compras filtradas correctamente para proveedor: " + secuencialProveedor);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "❌ Error al filtrar compras: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
 
    
    public void configurarTablaVenta(JTable tabla) {
    // Centrar contenido de las celdas
    DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
    centrado.setHorizontalAlignment(SwingConstants.CENTER);
    tabla.setDefaultRenderer(Object.class, centrado);

    // Definir columnas para ventas
    String[] columnas = { "S", "Fecha", "Total", "Gran Total", "SC" };
    DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Tabla de solo lectura
        }
    };

    tabla.setModel(modelo);

    // Ajustes visuales
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

    
    
    public void configurarTablaCompra(JTable tabla) {
    // Centrar contenido de las celdas
    DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
    centrado.setHorizontalAlignment(SwingConstants.CENTER);
    tabla.setDefaultRenderer(Object.class, centrado);

    // Definir columnas para compras
    String[] columnas = { "S", "Fecha", "Total", "Gran Total", "SP" };
    DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Tabla de solo lectura
        }
    };

    tabla.setModel(modelo);

    // Ajustes visuales
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

    
    
    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased


         String telefono=jTextField2.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        Util.llenarComboProveedorPorTelefono(jComboBox2, telefono, Secuencial_Empresa);

        if (jTextField2.getText().trim().isEmpty()) {
            Util.llenarComboProveedor(jComboBox2, Secuencial_Empresa);
        }
    }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

//
//         
//        DefaultTableModel modelo1 = (DefaultTableModel) jTable1.getModel();
//        modelo1.setRowCount(0);
//        
//        
//        DefaultTableModel modelo2 = (DefaultTableModel) jTable2.getModel();
//        modelo2.setRowCount(0);
//        
//        cliente_seleccionado = jComboBox1.getSelectedItem().toString();
//int secuencialCliente = Integer.parseInt(cliente_seleccionado.split("-")[0].trim());
//filtrarVenta(secuencialCliente, jTable1, jTable2);
//Secuencial_Cliente=Integer.parseInt(cliente_seleccionado.split("-")[0].trim());
//
//        
 Object seleccionado = jComboBox1.getSelectedItem();
    if (seleccionado == null) {
        return; // Evita el error si no hay selección
    }

    String cliente_seleccionado = seleccionado.toString();
    int secuencialCliente = Integer.parseInt(cliente_seleccionado.split("-")[0].trim());

    DefaultTableModel modelo1 = (DefaultTableModel) jTable1.getModel();
    modelo1.setRowCount(0);

    DefaultTableModel modelo2 = (DefaultTableModel) jTable2.getModel();
    modelo2.setRowCount(0);

    filtrarVenta(secuencialCliente, jTable1, jTable2);
    Secuencial_Cliente = secuencialCliente;

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked


      //  Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1PropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

 try {
        // Validar selección del cliente
        if (jComboBox1.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Por favor, selecciona un cliente antes de continuar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String clienteTexto = jComboBox1.getSelectedItem().toString();
        int indice = clienteTexto.indexOf("- ");
        if (indice == -1) {
            JOptionPane.showMessageDialog(null,
                "El formato del cliente no es válido.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreCliente = clienteTexto.substring(indice + 2).trim();

        EntityManager em = MonituxDBContext.getEntityManager();

        Venta venta = em.createQuery(
            "SELECT v FROM Venta v WHERE v.Secuencial_Empresa = :empresa AND v.Secuencial = :secuencial",
            Venta.class)
            .setParameter("empresa", V_Menu_Principal.Secuencial_Empresa)
            .setParameter("secuencial", Secuencial_Venta)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (venta == null) {
            JOptionPane.showMessageDialog(null,
                "No se encontró la factura en la base de datos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        byte[] documento = venta.getDocumento();
        if (documento == null || documento.length == 0) {
            JOptionPane.showMessageDialog(null,
                "La factura no tiene documento PDF asociado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(documento);
        visor.setTitulo("Factura de Venta No. " + venta.getSecuencial());
        visor.mostrar();

        System.out.println("✅ Documento PDF de la venta cargado correctamente.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,
            "Se produjo un error inesperado:\n" + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

        
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

  EntityManager em = null;

    try {
        // Validar cliente
        Object selectedItem = jComboBox1.getSelectedItem();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(null,
                "Por favor, selecciona un cliente válido antes de continuar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String clienteTexto = selectedItem.toString().trim();
        int indice = clienteTexto.indexOf("- ");
        if (clienteTexto.isEmpty() || indice == -1) {
            JOptionPane.showMessageDialog(null,
                "Por favor, selecciona un cliente válido antes de continuar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreCliente = clienteTexto.substring(indice + 2).trim();

        em = MonituxDBContext.getEntityManager();

        Venta venta = em.createQuery(
            "SELECT v FROM Venta v WHERE v.Secuencial_Empresa = :empresa AND v.Secuencial = :secuencial",
            Venta.class)
            .setParameter("empresa", V_Menu_Principal.Secuencial_Empresa)
            .setParameter("secuencial", Secuencial_Venta)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (venta == null) {
            JOptionPane.showMessageDialog(null,
                "No se encontró la factura en la base de datos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        byte[] documento = venta.getDocumento();
        if (documento == null || documento.length == 0) {
            JOptionPane.showMessageDialog(null,
                "La factura no tiene documento PDF asociado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente destinatarioCliente = em.find(Cliente.class, Secuencial_Cliente);
        if (destinatarioCliente == null) {
            JOptionPane.showMessageDialog(null,
                "No se encontró el cliente en la base de datos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String destinatario = destinatarioCliente.getEmail();
        if (destinatario == null || destinatario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "El cliente no tiene un correo electrónico registrado.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Util.EnviarCorreoConPdfBytes(
            "monitux.pos@gmail.com",
            destinatario,
            V_Menu_Principal.Nombre_Empresa + " - Comprobante",
            "Gracias por su compra. Adjunto tiene su comprobante.",
            documento,
            "smtp.gmail.com",
            587,
            "monitux.pos",
            "ffeg qqnx zaij otmb"
        );

        JOptionPane.showMessageDialog(null,
            "Se envió la factura correctamente.",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,
            "Se produjo un error inesperado:\n" + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

         Object seleccionadoObj = jComboBox2.getSelectedItem();
    if (seleccionadoObj == null) {
        return; // Evita error si no hay selección
    }

    String seleccionado = seleccionadoObj.toString();
    int secuencialProveedor = Integer.parseInt(seleccionado.split("-")[0].trim());

    DefaultTableModel modelo1 = (DefaultTableModel) jTable3.getModel();
    modelo1.setRowCount(0);

    DefaultTableModel modelo2 = (DefaultTableModel) jTable4.getModel();
    modelo2.setRowCount(0);

    filtrarCompra(secuencialProveedor, jTable3, jTable4);
    Secuencial_Proveedor = secuencialProveedor;
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

try {
        // Validar selección del proveedor
        if (jComboBox2.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null,
                "Por favor, selecciona un proveedor antes de continuar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String proveedorTexto = jComboBox2.getSelectedItem().toString();
        int indice = proveedorTexto.indexOf("- ");
        if (indice == -1) {
            JOptionPane.showMessageDialog(null,
                "El formato del proveedor no es válido.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreProveedor = proveedorTexto.substring(indice + 2).trim();

        EntityManager em = MonituxDBContext.getEntityManager();

        Compra compra = em.createQuery(
            "SELECT c FROM Compra c WHERE c.Secuencial_Empresa = :empresa AND c.Secuencial = :secuencial",
            Compra.class)
            .setParameter("empresa", V_Menu_Principal.Secuencial_Empresa)
            .setParameter("secuencial", Secuencial_Compra)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (compra == null) {
            JOptionPane.showMessageDialog(null,
                "No se encontró la factura de compra en la base de datos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        byte[] documento = compra.getDocumento();
        if (documento == null || documento.length == 0) {
            JOptionPane.showMessageDialog(null,
                "La factura de compra no tiene documento PDF asociado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(documento);
        visor.setTitulo("Factura de Compra No. " + compra.getSecuencial());
        visor.mostrar();

        System.out.println("✅ Documento PDF de la compra cargado correctamente.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,
            "Se produjo un error inesperado:\n" + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
        
        
   

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

   EntityManager em = null;

    try {
        // Validar proveedor
        Object selectedItem = jComboBox2.getSelectedItem();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(null,
                "Por favor, selecciona un proveedor válido antes de continuar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String proveedorTexto = selectedItem.toString().trim();
        int indice = proveedorTexto.indexOf("- ");
        if (proveedorTexto.isEmpty() || indice == -1) {
            JOptionPane.showMessageDialog(null,
                "Por favor, selecciona un proveedor válido antes de continuar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreProveedor = proveedorTexto.substring(indice + 2).trim();

        em = MonituxDBContext.getEntityManager();

        Compra compra = em.createQuery(
            "SELECT c FROM Compra c WHERE c.Secuencial_Empresa = :empresa AND c.Secuencial = :secuencial",
            Compra.class)
            .setParameter("empresa", V_Menu_Principal.Secuencial_Empresa)
            .setParameter("secuencial", Secuencial_Compra)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (compra == null) {
            JOptionPane.showMessageDialog(null,
                "No se encontró la factura de compra en la base de datos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        byte[] documento = compra.getDocumento();
        if (documento == null || documento.length == 0) {
            JOptionPane.showMessageDialog(null,
                "La factura de compra no tiene documento PDF asociado.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Proveedor destinatarioProveedor = em.find(Proveedor.class, compra.getSecuencial_Proveedor());
        if (destinatarioProveedor == null) {
            JOptionPane.showMessageDialog(null,
                "No se encontró el proveedor en la base de datos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String destinatario = destinatarioProveedor.getEmail();
        if (destinatario == null || destinatario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "El proveedor no tiene un correo electrónico registrado.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Util.EnviarCorreoConPdfBytes(
            "monitux.pos@gmail.com",
            destinatario,
            V_Menu_Principal.Nombre_Empresa + " - Comprobante",
            "Gracias por su gestión. Adjunto tiene el comprobante de compra.",
            documento,
            "smtp.gmail.com",
            587,
            "monitux.pos",
            "ffeg qqnx zaij otmb"
        );

        JOptionPane.showMessageDialog(null,
            "Se envió la factura de compra correctamente.",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,
            "Se produjo un error inesperado:\n" + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

lista.clear();
    V_Editar_Factura_Venta.listaDeItems.clear();

    if (jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "No hay factura seleccionada para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Object clienteObj = jComboBox1.getSelectedItem();
    if (clienteObj == null) {
        JOptionPane.showMessageDialog(null, "Seleccione un cliente válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    String clienteSeleccionado = clienteObj.toString();

    lista.clear();

    int colCodigo = -1;
    int colCantidad = -1;
    for (int c = 0; c < jTable2.getColumnCount(); c++) {
        String colName = jTable2.getColumnName(c);
        if (colName.equalsIgnoreCase("Codigo")) colCodigo = c;
        if (colName.equalsIgnoreCase("Cantidad")) colCantidad = c;
    }

    if (colCodigo == -1 || colCantidad == -1) {
        JOptionPane.showMessageDialog(null, "Las columnas 'Codigo' o 'Cantidad' no existen en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    for (int i = 0; i < jTable2.getRowCount(); i++) {
        Object codigoObj = jTable2.getValueAt(i, colCodigo);
        Object cantidadObj = jTable2.getValueAt(i, colCantidad);

        if (codigoObj != null && cantidadObj != null) {
            String codigo = codigoObj.toString().trim();
            try {
                double cantidad = Double.parseDouble(cantidadObj.toString().trim());
                if (!lista.containsKey(codigo)) {
                    lista.put(codigo, cantidad);
                }
            } catch (NumberFormatException e) {
                System.err.println("Cantidad inválida en fila " + i + ": " + cantidadObj);
            }
        }
    }

    if (Secuencial_Venta == 0) {
        JOptionPane.showMessageDialog(null, "Seleccione Factura", "Monitux-POS", JOptionPane.WARNING_MESSAGE);
        return;
    }

    V_Editar_Factura_Venta vEditarFacturaVenta = new V_Editar_Factura_Venta(this);
    vEditarFacturaVenta.setSecuencial_Cliente(Secuencial_Cliente);
    vEditarFacturaVenta.setSecuencial(Secuencial_Venta);

    EntityManager em = MonituxDBContext.getEntityManager();
    vEditarFacturaVenta.importarFactura(V_Compras_Ventas.lista, clienteSeleccionado, em);
    vEditarFacturaVenta.setVisible(true);

    String clienteTexto = jComboBox1.getSelectedItem().toString();
    try {
        int clienteId = Integer.parseInt(clienteTexto.split("-")[0].trim());
        filtrarVenta(clienteId, jTable1, jTable2);
        filtrarDetalleVenta(Secuencial_Venta, jTable2);
        System.out.println("✅ Edición de factura de venta iniciada correctamente.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Formato de cliente inválido.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    Secuencial_Venta=0;
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed


//************************
lista.clear();
    V_Editar_Factura_Venta.listaDeItems.clear();

    if (jTable3.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "No hay factura seleccionada para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Object proveedorObj = jComboBox2.getSelectedItem();
    if (proveedorObj == null) {
        JOptionPane.showMessageDialog(null, "Seleccione un proveedor válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    proveedor_seleccionado = proveedorObj.toString();

    lista.clear();

    int colCodigo = -1;
    int colCantidad = -1;
    for (int c = 0; c < jTable4.getColumnCount(); c++) {
        String colName = jTable4.getColumnName(c);
        if (colName.equalsIgnoreCase("Codigo")) colCodigo = c;
        if (colName.equalsIgnoreCase("Cantidad")) colCantidad = c;
    }

    if (colCodigo == -1 || colCantidad == -1) {
        JOptionPane.showMessageDialog(null, "Las columnas 'Codigo' o 'Cantidad' no existen en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    for (int i = 0; i < jTable4.getRowCount(); i++) {
        Object codigoObj = jTable4.getValueAt(i, colCodigo);
        Object cantidadObj = jTable4.getValueAt(i, colCantidad);

        if (codigoObj != null && cantidadObj != null) {
            String codigo = codigoObj.toString().trim();
            try {
                double cantidad = Double.parseDouble(cantidadObj.toString().trim());
                if (!lista.containsKey(codigo)) {
                    lista.put(codigo, cantidad);
                }
            } catch (NumberFormatException e) {
                System.err.println("Cantidad inválida en fila " + i + ": " + cantidadObj);
            }
        }
    }

    if (Secuencial_Compra == 0) {
        JOptionPane.showMessageDialog(null, "Seleccione Factura", "Monitux-POS", JOptionPane.WARNING_MESSAGE);
        return;
    }

    V_Editar_Factura_Compra vEditarFacturaCompra = new V_Editar_Factura_Compra(this);
    vEditarFacturaCompra.setSecuencial_Proveedor(Secuencial_Proveedor);
    vEditarFacturaCompra.setSecuencial(Secuencial_Compra);

    EntityManager em = MonituxDBContext.getEntityManager();
    vEditarFacturaCompra.importarFactura(V_Compras_Ventas.lista, proveedor_seleccionado, em);
    vEditarFacturaCompra.setVisible(true);

    String proveedorTexto = jComboBox2.getSelectedItem().toString();
    try {
        int proveedorId = Integer.parseInt(proveedorTexto.split("-")[0].trim());
        filtrarCompra(proveedorId, jTable3, jTable4);
        filtrarDetalleCompra(Secuencial_Compra, jTable4);
        System.out.println("✅ Edición de factura iniciada correctamente.");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Formato de proveedor inválido.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    Secuencial_Compra=0;
//************************

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
