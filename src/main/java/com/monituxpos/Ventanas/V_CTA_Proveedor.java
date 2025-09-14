/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Cuentas_Cobrar;
import com.monituxpos.Clases.Cuentas_Pagar;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.Color;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_CTA_Proveedor extends javax.swing.JFrame {
    public V_Proveedor form;
    public int Secuencial_Proveedor;
    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    public String Nombre;
    public int Secuencial;
    public double Gran_Total;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(V_CTA_Proveedor.class.getName());

    /**
     * Creates new form V_CTA_Cliente
     */
    public V_CTA_Proveedor() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.black);
        configurarTabla(jTable1);
    }

    
     public V_CTA_Proveedor(int secuencialProveedor,String nombre,V_Proveedor x) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.black);
        Secuencial_Proveedor=secuencialProveedor;
        Nombre=nombre;
        configurarTabla(jTable1);
        form=x;
    }
    
    
     
   public void configurarTabla(JTable tabla) {
    // Centrar el contenido de las celdas
    DefaultTableCellRenderer centroRenderer = new DefaultTableCellRenderer();
    centroRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    tabla.setDefaultRenderer(Object.class, centroRenderer);

    // Crear modelo no editable
    DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Todas las celdas son no editables
        }
    };

    // Asignar columnas
    modelo.addColumn("S");         // Secuencial
    modelo.addColumn("Emitida");   // Fecha
    modelo.addColumn("Vence");     // Fecha_Vencimiento
    modelo.addColumn("Total");     // Gran_Total
    modelo.addColumn("Saldo");     // Saldo
    modelo.addColumn("Pagado");    // Pagado
    tabla.setModel(modelo);

    // Colorear columnas específicas directamente
    TableColumnModel columnas = tabla.getColumnModel();

    DefaultTableCellRenderer colorTotal = new DefaultTableCellRenderer();
    colorTotal.setForeground(new Color(0, 192, 192));
    colorTotal.setHorizontalAlignment(SwingConstants.CENTER);
    columnas.getColumn(3).setCellRenderer(colorTotal);

    DefaultTableCellRenderer colorSaldo = new DefaultTableCellRenderer();
    colorSaldo.setForeground(Color.RED);
    colorSaldo.setHorizontalAlignment(SwingConstants.CENTER);
    columnas.getColumn(4).setCellRenderer(colorSaldo);

    DefaultTableCellRenderer colorPagado = new DefaultTableCellRenderer();
    colorPagado.setForeground(new Color(0, 100, 0)); // DarkGreen
    colorPagado.setHorizontalAlignment(SwingConstants.CENTER);
    columnas.getColumn(5).setCellRenderer(colorPagado);

    // Ajustes adicionales
    tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

     
   public void cargarDatosCTAS() {
    double totalFacturas = 0;
    double saldoPendiente = 0;

    jLabel4.setText("0");
    jLabel5.setText("0");

    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
    modelo.setRowCount(0); // Limpiar tabla

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager em = emf.createEntityManager();

    try {
        List<Cuentas_Pagar> cuentas = em.createQuery(
            "SELECT c FROM Cuentas_Pagar c WHERE c.Secuencial_Empresa = :empresa AND c.Secuencial_Proveedor = :proveedor",
            Cuentas_Pagar.class)
            .setParameter("empresa", Secuencial_Empresa)
            .setParameter("proveedor", Secuencial_Proveedor)
            .getResultList();

        for (Cuentas_Pagar item : cuentas) {
            saldoPendiente += item.getSaldo();
            totalFacturas += item.getTotal();

            modelo.addRow(new Object[]{
                item.getSecuencial(),
                item.getFecha(),
                item.getFecha_Vencimiento(),
                item.getGran_Total(),
                item.getSaldo(),
                item.getPagado()
            });
        }

        jLabel4.setText(String.format("%.2f", saldoPendiente));
        jLabel5.setText(String.format("%.2f", totalFacturas));

        // Marcar facturas vencidas con saldo
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            Object fechaObj = jTable1.getValueAt(i, 2); // Fecha_Vencimiento
            Object saldoObj = jTable1.getValueAt(i, 4); // Saldo

            try {
                LocalDate fechaVenc = LocalDate.parse(fechaObj.toString());
                double saldo = Double.parseDouble(saldoObj.toString());

                if (fechaVenc.isBefore(LocalDate.now()) && saldo > 0) {
                    for (int j = 0; j < jTable1.getColumnCount(); j++) {
                        jTable1.getCellRenderer(i, j).getTableCellRendererComponent(
                            jTable1, jTable1.getValueAt(i, j), false, false, i, j
                        ).setBackground(new Color(255, 228, 225)); // MistyRose
                    }
                }
            } catch (Exception e) {
                // Ignorar errores de formato
            }
        }
    } finally {
        em.close();
        emf.close();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Estado de Cuenta");
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(35, 32, 45));

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

        jPanel2.setBackground(new java.awt.Color(35, 32, 45));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Saldo Actual");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total en Facturas Credito");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("0");
        jLabel4.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 192, 192));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("0");
        jLabel5.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(49, 49, 49))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cargando Proveedor....");

        jButton1.setBackground(new java.awt.Color(102, 255, 0));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Ver CTA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
 setTitle("Monitux-POS v." + "");//V_Menu_Principal.VER);
jLabel6.setText(Nombre);
cargarDatosCTAS();
        

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        
        try {
    int filaSeleccionada = jTable1.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una fila primero.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    Gran_Total = Double.parseDouble(model.getValueAt(filaSeleccionada, 3).toString()); // "Total"
    Secuencial = Integer.parseInt(model.getValueAt(filaSeleccionada, 0).toString());   // "S"
    Nombre = jLabel6.getText(); // O donde tengas el nombre del cliente

    if (Secuencial_Proveedor == 0) return;

    V_Abono_Proveedor vAbonoProveedor = new V_Abono_Proveedor(Secuencial, Secuencial_Proveedor, Nombre, Gran_Total,this);
    vAbonoProveedor.setCliente_Nombre(Nombre);
    vAbonoProveedor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
    vAbonoProveedor.setVisible(true);

} catch (Exception ex) {
    ex.printStackTrace(); // Para depurar
}

   

    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing


    if (form!=null){
form.toFront();
form.requestFocus();
    
    }                

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

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
        java.awt.EventQueue.invokeLater(() -> new V_CTA_Proveedor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
