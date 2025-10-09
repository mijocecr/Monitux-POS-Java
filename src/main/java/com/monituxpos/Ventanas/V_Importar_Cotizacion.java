/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Cotizacion;
import com.monituxpos.Clases.Cotizacion_Detalle;
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Orden;
import com.monituxpos.Clases.SelectorCantidad;
import com.monituxpos.Clases.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
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
public class V_Importar_Cotizacion extends javax.swing.JFrame {
    
    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(V_Importar_Cotizacion.class.getName());

    
    public int secuencial;

public static Map<String, Double> lista = new HashMap<>();

public static String clienteSeleccionado;

    

public Runnable onImportarCotizacion;



public Runnable onAceptar; // El callback





    /**
     * Creates new form V_Importar_Cotizacion
     */
    public V_Importar_Cotizacion() {
        initComponents();
        
        
        
        
        
        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            Object valor = jTable1.getValueAt(fila, 0); // 0 es la columna "Secuencial"
            if (valor != null) {
                secuencial = Integer.parseInt(valor.toString());
                filtrarDetalle(secuencial,jTable2);
            }
        }
    }
});
        
        
        
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
    @Override
    public void keyReleased(java.awt.event.KeyEvent evt) {
        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            Object valor = jTable1.getValueAt(fila, 0); // Columna "Secuencial"
            if (valor != null) {
                try {
                    secuencial = Integer.parseInt(valor.toString());
                    filtrarDetalle(secuencial, jTable2);
                } catch (NumberFormatException ex) {
                    System.err.println("Error al convertir el valor a entero: " + valor);
                }
            }
        }
    }
});


        
        
    }
    
    
    
    
    

    public void configurarTablaCotizacion(JTable tabla) {
    // Centrar contenido de las celdas
    DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
    centrado.setHorizontalAlignment(SwingConstants.CENTER);
    tabla.setDefaultRenderer(Object.class, centrado);

    // Definir columnas
    String[] columnas = { "S", "Fecha", "Total", "Gran Total", "SC" };
    DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Hacer la tabla de solo lectura
        }
    };

    tabla.setModel(modelo);

    // Ajustes visuales
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}
    
    
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

  public void cargarDatosOrden(JTable tabla) {
    // Limpiar la tabla
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0);

    EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();

        List<Orden> ordenes = em.createQuery(
            "SELECT o FROM Orden o WHERE o.Secuencial_Empresa = :empresa", Orden.class)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Orden item : ordenes) {
            modelo.addRow(new Object[] {
                item.getSecuencial(),
                item.getFecha(),
                item.getTotal(),
                item.getGran_Total(),
                item.getSecuencial_Proveedor()
            });
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al cargar órdenes de compra: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}


    public void filtrarCotizacion(int secuencialCliente, JTable tablaCotizacion, JTable tablaDetalle) {
    // Verificar y configurar columnas si no existen
    if (tablaCotizacion.getColumnCount() == 0) {
        configurarTablaCotizacion(tablaCotizacion);
    }

    // Limpiar tabla
    DefaultTableModel modelo = (DefaultTableModel) tablaCotizacion.getModel();
    modelo.setRowCount(0);

    EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();

        List<Cotizacion> cotizaciones = em.createQuery(
            "SELECT c FROM Cotizacion c WHERE c.Secuencial_Cliente = :cliente AND c.Secuencial_Empresa = :empresa",
            Cotizacion.class)
            .setParameter("cliente", secuencialCliente)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Cotizacion item : cotizaciones) {
            modelo.addRow(new Object[] {
                item.getSecuencial(),
                item.getFecha(),
                item.getTotal(),
                item.getGran_Total(),
                item.getSecuencial_Cliente()
            });
        }

        // Si no hay resultados, limpiar tabla de detalles
        if (modelo.getRowCount() == 0) {
            ((DefaultTableModel) tablaDetalle.getModel()).setRowCount(0);
        }

        // Selección de fila completa
        tablaCotizacion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al filtrar cotizaciones: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

    
    
    public void filtrarDetalle(int secuencialCotizacion, JTable tablaDetalle) {
    // Verificar y configurar columnas si no existen
    if (tablaDetalle.getColumnCount() == 0) {
        configurarTablaDetalle(tablaDetalle);
    }

    // Limpiar tabla
    DefaultTableModel modelo = (DefaultTableModel) tablaDetalle.getModel();
    modelo.setRowCount(0);

    EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();

        List<Cotizacion_Detalle> detalles = em.createQuery(
            "SELECT d FROM Cotizacion_Detalle d WHERE d.Secuencial_Cotizacion = :cotizacion AND d.Secuencial_Empresa = :empresa",
            Cotizacion_Detalle.class)
            .setParameter("cotizacion", secuencialCotizacion)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Cotizacion_Detalle item : detalles) {
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

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al filtrar detalles: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

    private double redondear(Double valor) {
    return Math.round(valor * 100.0) / 100.0;
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
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(35, 32, 45));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cliente:");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Cotizaciones:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Detalle:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Importar Cotizacion");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(51, 255, 51));
        jLabel6.setText("Telefono:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(182, 182, 182))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(21, 21, 21))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1))
                        .addContainerGap())))
        );

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cotizaciones");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        configurarTablaCotizacion(jTable1);
configurarTablaDetalle(jTable2);
 
         this.getContentPane().setBackground(Color.black);
        
        setTitle("Monitux-POS v." + "");//V_Menu_Principal.VER);

Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);

this.setLocationRelativeTo(null);

if (jComboBox1.getItemCount() > 0) {
    jComboBox1.setSelectedIndex(0); // Seleccionar el primer cliente por defecto
}

       
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

        
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        Util.llenarComboClientePorTelefono(jComboBox1, jTextField1.getText(), Secuencial_Empresa);

        if (jTextField1.getText().trim().isEmpty()) {
            Util.llenarComboCliente(jComboBox1, Secuencial_Empresa);
        }
    
         }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
  Object seleccionadoObj = jComboBox1.getSelectedItem();
    if (seleccionadoObj == null) {
        return; // Evita error si no hay selección
    }

    String seleccionado = seleccionadoObj.toString();
    int secuencialCliente = Integer.parseInt(seleccionado.split("-")[0].trim());

    DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
    modelo.setRowCount(0);

    filtrarCotizacion(secuencialCliente, jTable1, jTable2);
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    
    
    
    
    
    
    
    
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

     lista.clear();
    V_Factura_Venta.listaDeItems.clear();

    if (jTable1.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "No hay cotizaciones disponibles para importar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (jTable2.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Debe seleccionar una cotización.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int opt = JOptionPane.showConfirmDialog(
        null,
        "¿Desea importar la cotización seleccionada?\nAdvertencia: Esta se eliminará de los registros.",
        "Importar Cotización",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (opt != JOptionPane.YES_OPTION) return;

    clienteSeleccionado = jComboBox1.getSelectedItem().toString();
    lista.clear();

    DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
    for (int i = 0; i < modelo.getRowCount(); i++) {
        Object codigoObj = modelo.getValueAt(i, obtenerIndiceColumna(jTable2, "Codigo"));
        Object cantidadObj = modelo.getValueAt(i, obtenerIndiceColumna(jTable2, "Cantidad"));

        if (codigoObj != null && cantidadObj != null) {
            String codigo = codigoObj.toString();
            try {
                double cantidad = Double.parseDouble(cantidadObj.toString());
                if (!lista.containsKey(codigo)) {
                    lista.put(codigo, cantidad);
                }
            } catch (NumberFormatException ex) {
                // Ignorar si no es número válido
            }
        }
    }

    EntityManager em = null;

    try {
        em = MonituxDBContext.getEntityManager();

        Cotizacion cotizacion = em.createQuery(
            "SELECT c FROM Cotizacion c WHERE c.Secuencial = :secuencial AND c.Secuencial_Empresa = :empresa",
            Cotizacion.class)
            .setParameter("secuencial", this.secuencial)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultStream().findFirst().orElse(null);

        if (cotizacion != null) {
            em.getTransaction().begin();

            // Eliminar cabecera
            em.remove(cotizacion);

            // Eliminar detalles asociados
            List<Cotizacion_Detalle> detalles = em.createQuery(
                "SELECT d FROM Cotizacion_Detalle d WHERE d.Secuencial_Cotizacion = :cotizacion AND d.Secuencial_Empresa = :empresa",
                Cotizacion_Detalle.class)
                .setParameter("cotizacion", this.secuencial)
                .setParameter("empresa", Secuencial_Empresa)
                .getResultList();

            for (Cotizacion_Detalle detalle : detalles) {
                em.remove(detalle);
            }

            em.getTransaction().commit();
        }

        JOptionPane.showMessageDialog(null, "Cotización importada correctamente.", "Importación Exitosa", JOptionPane.INFORMATION_MESSAGE);

        if (onAceptar != null) {
            onAceptar.run(); // Ejecuta el callback
        }

        this.dispose();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al importar cotización: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
  
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed
  
    private int obtenerIndiceColumna(JTable tabla, String nombreColumna) {
    for (int i = 0; i < tabla.getColumnCount(); i++) {
        if (tabla.getColumnName(i).equals(nombreColumna)) {
            return i;
        }
    }
    return -1;
}

    public static Map<String, Double> getLista() {
        return lista;
    }

    public static void setLista(Map<String, Double> lista) {
        V_Importar_Cotizacion.lista = lista;
    }

    public static String getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public static void setClienteSeleccionado(String clienteSeleccionado) {
        V_Importar_Cotizacion.clienteSeleccionado = clienteSeleccionado;
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new V_Importar_Cotizacion().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
