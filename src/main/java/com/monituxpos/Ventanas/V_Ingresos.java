/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Ingreso;
import com.monituxpos.Clases.Usuario;
import com.monituxpos.Clases.Util;
import com.monituxpos.Clases.Venta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Ingresos extends javax.swing.JPanel {

    
    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    public int Secuencial_Usuario=V_Menu_Principal.getSecuencial_Usuario();
    
    public int Secuencial;
    /**
     * Creates new form V_Ingresos
     */
    public V_Ingresos() {
        initComponents();
        configurarTablaIngresos(jTable1);
        cargarDatos();
        

        
    }
    
  
    public void cargarDatos() {
    double totalIngresos = 0;
    double totalOtros = 0;

    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
    modelo.setRowCount(0);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        List<Ingreso> ingresos = em.createQuery(
            "SELECT i FROM Ingreso i " +
            "LEFT JOIN FETCH i.usuario " +
            "LEFT JOIN FETCH i.venta " +
            "WHERE i.Secuencial_Empresa = :empresa " +
            "ORDER BY i.Fecha DESC", Ingreso.class)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Ingreso i : ingresos) {
            String facturaAsociada = (i.getVenta() != null) ? "Factura No. " + i.getVenta().getSecuencial() : "0";

            modelo.addRow(new Object[] {
                i.getSecuencial(),
                i.getUsuario() != null ? i.getUsuario().getNombre() : "Desconocido",
                i.getFecha(), // ← ya es String, no se formatea
                i.getTipo_Ingreso(),
                i.getTotal(),
                i.getDescripcion()
            });

            if ("0".equals(facturaAsociada)) {
                totalOtros += i.getTotal();
            } else {
                totalIngresos += i.getTotal();
            }
        }

        jLabel8.setText(String.format("%.2f", totalOtros));
        jLabel7.setText(String.format("%.2f", totalIngresos));

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        em.close();
        emf.close();
    }
}

    
public void cargarDatosFecha(LocalDate fechaInicio, LocalDate fechaFin) {
    double totalIngresos = 0;
    double totalOtros = 0;

    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
    modelo.setRowCount(0);

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        // Convertir rango a LocalDateTime
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        // Formato de fecha con hora (como está en la base)
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));


        // Consulta con JOINs
        List<Ingreso> ingresos = em.createQuery(
            "SELECT i FROM Ingreso i " +
            "LEFT JOIN FETCH i.usuario " +
            "LEFT JOIN FETCH i.venta " +
            "WHERE i.Secuencial_Empresa = :empresa " +
            "ORDER BY i.Fecha DESC", Ingreso.class)
            .setParameter("empresa", Secuencial_Empresa)
            .getResultList();

        for (Ingreso i : ingresos) {
            LocalDateTime fechaConvertida;
            try {
                fechaConvertida = LocalDateTime.parse(i.getFecha().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha mal formateada: " + i.getFecha());
                continue;
            }

            if (!fechaConvertida.isBefore(inicio) && !fechaConvertida.isAfter(fin)) {
                String facturaAsociada = (i.getVenta() != null) ? "Factura No. " + i.getVenta().getSecuencial() : "0";

                modelo.addRow(new Object[] {
                    i.getSecuencial(),
                    i.getUsuario() != null ? i.getUsuario().getNombre() : "Desconocido",
                    i.getFecha(),
                    i.getTipo_Ingreso(),
                    i.getTotal(),
                    i.getDescripcion()
                });

                if ("0".equals(facturaAsociada)) {
                    totalOtros += i.getTotal();
                } else {
                    totalIngresos += i.getTotal();
                }
            }
        }

        jLabel8.setText(String.format("%.2f", totalOtros));
        jLabel7.setText(String.format("%.2f", totalIngresos));

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar datos por fecha: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        em.close();
        emf.close();
    }
}

    
   public void configurarTablaIngresos(JTable tabla) {
    // Definir columnas
    String[] columnas = { "S", "Usuario", "Fecha", "Tipo", "Total", "Descripción" };
    DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Tabla de solo lectura
        }
    };

    tabla.setModel(modelo);

    // Centrar contenido de las celdas
    DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
    centrado.setHorizontalAlignment(SwingConstants.CENTER);
    tabla.setDefaultRenderer(Object.class, centrado);

    // Ajustes visuales
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabla.setEnabled(true); // Si quieres desactivarla, usa false
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        datePicker2 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Gestión de Ingresos");
        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 0));
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(35, 32, 45));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/page_add.png"))); // NOI18N
        jButton1.setText("<html><b>Nuevo</b><br><i>Ingreso</i></html>");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/delete1.png"))); // NOI18N
        jButton2.setText("<html><b>Eliminar</b><br><i>Ingreso</i></html>");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/page_excel.png"))); // NOI18N
        jButton3.setText("<html><b>Exportar</b><br><i>Excel</i></html>");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(35, 32, 45));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setText("Desde:");
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Hasta:");
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Todos los ingresos en un rango de fechas.");
        jLabel4.setForeground(new java.awt.Color(255, 255, 51));

        jButton4.setText("Consultar");
        jButton4.setBackground(new java.awt.Color(128, 255, 128));
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setText("Ingresos Totales:");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Otros Ingresos:");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("0");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 255, 0));

        jLabel8.setText("0");
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));

        jLabel9.setText("<html>No se puede eliminar ningun ingreso asociado a una factura o a un abono de cuentas por cobrar. La funcion \"Eliminar Ingreso\" solo puede aplicarse a registros manuales de ingresos.</html>");
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/arrow_refresh.png"))); // NOI18N
        jLabel10.setToolTipText("Refrescar Datos.");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(35, 35, 35)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        
        
        cargarDatosFecha(datePicker1.getDate(),datePicker2.getDate());
        
        


      
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        
cargarDatos();
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed


        // Inicialización de SQLite (si aplica, depende de tu configuración)
// No es necesario en JPA/Hibernate, pero si usas SQLite directamente:
// SQLiteConfig config = new SQLiteConfig();
// config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);

int confirmResult = JOptionPane.showConfirmDialog(
    null,
    "¿Está seguro de eliminar el ingreso seleccionado?",
    "Confirmar Eliminación",
    JOptionPane.YES_NO_OPTION
);

if (confirmResult != JOptionPane.YES_OPTION) {
    return;
}

EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
EntityManager em = emf.createEntityManager();

try {
    Ingreso ingreso = em.createQuery(
        "SELECT i FROM Ingreso i WHERE i.Secuencial = :secuencial " +
        "AND (i.Secuencial_Factura = 0 OR i.Secuencial_Factura IS NULL) " +
        "AND i.Secuencial_Empresa = :empresa", Ingreso.class)
        .setParameter("secuencial", this.Secuencial)
        .setParameter("empresa", this.Secuencial_Empresa)
        .setMaxResults(1)
        .getResultStream()
        .findFirst()
        .orElse(null);

    if (ingreso != null) {
        em.getTransaction().begin();
        em.remove(ingreso);

        Util.registrarActividad(
            this.Secuencial_Usuario,
            "Eliminó ingreso sin factura asociada. Monto: " + ingreso.getTotal() +
            " | Tipo: " + ingreso.getTipo_Ingreso() +
            " | Fecha: " + ingreso.getFecha(),
            this.Secuencial_Empresa
        );

        em.getTransaction().commit();

        JOptionPane.showMessageDialog(null,
            "Ingreso sin factura asociada eliminado correctamente.",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

        cargarDatos(); // Método que refresca la tabla
    } else {
        JOptionPane.showMessageDialog(null,
            "No es posible eliminar el ingreso seleccionado.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
} catch (Exception e) {
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }
    JOptionPane.showMessageDialog(null,
        "Error al eliminar ingreso: " + e.getMessage(),
        "Error",
        JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
} finally {
    em.close();
    emf.close();
}



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        
int viewRowIndex = jTable1.getSelectedRow();

    if (viewRowIndex >= 0) { // Asegura que no sea el encabezado
        int modelRowIndex = jTable1.convertRowIndexToModel(viewRowIndex);
        int columnIndex = jTable1.getColumnModel().getColumnIndex("S");

        TableModel model = jTable1.getModel();
        Object value = model.getValueAt(modelRowIndex, columnIndex);

        if (value != null) {
            Secuencial = Integer.parseInt(value.toString());
            
        }
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased

int viewRowIndex = jTable1.getSelectedRow();

    if (viewRowIndex >= 0) { // Asegura que no sea el encabezado
        int modelRowIndex = jTable1.convertRowIndexToModel(viewRowIndex);
        int columnIndex = jTable1.getColumnModel().getColumnIndex("S");

        TableModel model = jTable1.getModel();
        Object value = model.getValueAt(modelRowIndex, columnIndex);

        if (value != null) {
            Secuencial = Integer.parseInt(value.toString());
           
        }
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed


        V_Ingresos_Egresos vIngresosEgresos = new V_Ingresos_Egresos(this);
vIngresosEgresos.isEgreso = false; // Indica que es un ingreso
vIngresosEgresos.Secuencial_Empresa = this.Secuencial_Empresa;
vIngresosEgresos.Secuencial_Usuario = this.Secuencial_Usuario;

vIngresosEgresos.setVisible(true); // Muestra la ventana

//Cargar_Datos(); // Refresca los datos después del cierre



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed


            Util.exportarJTableAExcel(jTable1, "Ingresos", "Ingresos");

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private com.github.lgooddatepicker.components.DatePicker datePicker2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
