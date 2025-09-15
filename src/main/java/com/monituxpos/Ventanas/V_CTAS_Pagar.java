/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.monituxpos.Clases.Cuentas_Cobrar;
import com.monituxpos.Clases.Cuentas_Pagar;
import com.monituxpos.Clases.Egreso;
import com.monituxpos.Clases.Util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Statement;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Miguel Cerrato
 */
public class V_CTAS_Pagar extends javax.swing.JPanel {

    public int Secuencial_Empresa = V_Menu_Principal.getSecuencial_Empresa();
    public int Secuencial_Usuario= V_Menu_Principal.getSecuencial_Usuario();;
    public int Secuencial;
        public int Secuencial_Proveedor;
    /**
     * Creates new form V_Ingresos
     */
    public V_CTAS_Pagar() {
        initComponents();
       inicializarModeloTabla();
        Util.llenarComboProveedor(jComboBox1, Secuencial_Empresa);
      Cargar_Datos_CTAS_Pagar(Secuencial_Empresa);
      
      
      
        
    }

    
    
    
    public void Cargar_Datos_CTAS_Pagar(int secuencial_empresa) {
    double total_facturas = 0;
    double saldo_pendiente = 0;

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Limpiar tabla

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager context = emf.createEntityManager();

    try {
        DateTimeFormatter vencFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Cuentas_Pagar> resultado = context.createQuery(
            "SELECT cp FROM Cuentas_Pagar cp JOIN cp.Compra c JOIN cp.Proveedor p " +
            "WHERE cp.Saldo > 0 AND cp.Secuencial_Empresa = :secuencial_empresa",
            Cuentas_Pagar.class
        ).setParameter("secuencial_empresa", secuencial_empresa).getResultList();

        for (Cuentas_Pagar item : resultado) {
            boolean vencida = false;
            try {
                LocalDate venc = LocalDate.parse(item.getFecha_Vencimiento().trim(), vencFormatter);
                BigDecimal saldo = BigDecimal.valueOf(item.getSaldo());
                vencida = venc.isBefore(LocalDate.now()) && saldo.compareTo(BigDecimal.ZERO) > 0;
            } catch (Exception e) {
                System.out.println("Error al parsear fecha de vencimiento: " + item.getFecha_Vencimiento());
            }

            model.addRow(new Object[] {
                String.valueOf(item.getSecuencial()),
                item.getCompra().getSecuencial(),
                item.getProveedor().getNombre(),
                item.getFecha(),
                item.getFecha_Vencimiento(),
                String.valueOf(item.getGran_Total()),
                item.getSaldo(),
                item.getPagado(),
                item.getSecuencial_Proveedor(),
                vencida // columna oculta
            });

            total_facturas += item.getGran_Total();
            saldo_pendiente += item.getSaldo();
        }

        jLabel8.setText(String.format("%.2f", total_facturas));
        jLabel7.setText(String.format("%.2f", saldo_pendiente));

        // Ocultar columna "Vencida"
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setWidth(0);

        // Aplicar color a filas vencidas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Object vencidaFlag = table.getValueAt(row, table.getColumnCount() - 1);

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else if (vencidaFlag instanceof Boolean && (Boolean) vencidaFlag) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        for (int i = 0; i < jTable1.getColumnCount() - 1; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

    } finally {
        context.close();
        emf.close();
    }
}

    
    
    
    
    
//   
//    private void Cargar_Datos_CTAS(int secuencial_empresa) {
//    double total_facturas = 0;
//    double saldo_pendiente = 0;
//
//    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//    model.setRowCount(0); // Limpiar tabla
//
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
//    EntityManager context = emf.createEntityManager();
//
//    try {
//        List<Cuentas_Cobrar> resultado = context.createQuery(
//            "SELECT cc FROM Cuentas_Cobrar cc JOIN cc.Venta v JOIN cc.Cliente c WHERE cc.Saldo > 0 AND cc.Secuencial_Empresa = :secuencial_empresa",
//            Cuentas_Cobrar.class
//        ).setParameter("secuencial_empresa", secuencial_empresa).getResultList();
//
//        model.setRowCount(0);
//
//        for (Cuentas_Cobrar item : resultado) {
//            int rowIndex = model.getRowCount();
//            model.addRow(new Object[] {
//                String.valueOf(item.getSecuencial()),
//                item.getVenta().getSecuencial(),
//                item.getCliente().getNombre(),
//                item.getFecha().toString(),
//                item.getFecha_Vencimiento().toString(),
//                String.valueOf(item.getGran_Total()),
//                item.getSaldo(),
//                item.getPagado(),
//                item.getSecuencial_Cliente()
//            });
//
//            total_facturas += item.getGran_Total();
//            saldo_pendiente += item.getSaldo();
//
//            Object fechaVencObj = model.getValueAt(rowIndex, 4);
//            Object saldoObj = model.getValueAt(rowIndex, 6);
//
//            try {
//                LocalDate venc = LocalDate.parse(fechaVencObj.toString());
//                BigDecimal saldo = new BigDecimal(saldoObj.toString());
//
//                if (venc.isBefore(LocalDate.now()) && saldo.compareTo(BigDecimal.ZERO) > 0) {
//                    jTable1.setRowSelectionInterval(rowIndex, rowIndex);
//                    jTable1.setSelectionBackground(Color.PINK);
//                }
//            } catch (Exception e) {
//                // Ignorar errores de parseo
//            }
//        }
//
//        jLabel8.setText(String.format("%.2f", total_facturas));
//        jLabel7.setText(String.format("%.2f", saldo_pendiente));
//
//    } finally {
//        context.close();
//        emf.close();
//    }
//}
//
//  
    
    private void Cargar_Datos_CTAS_Proveedor(int secuencial_proveedor, int secuencial_empresa) {
    double total_facturas = 0;
    double saldo_pendiente = 0;

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Limpiar tabla

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager context = emf.createEntityManager();

    try {
        DateTimeFormatter vencFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Cuentas_Pagar> resultado = context.createQuery(
            "SELECT cp FROM Cuentas_Pagar cp JOIN cp.Compra c JOIN cp.Proveedor p " +
            "WHERE cp.Saldo > 0 AND cp.Secuencial_Proveedor = :secuencial_proveedor AND cp.Secuencial_Empresa = :secuencial_empresa",
            Cuentas_Pagar.class
        )
        .setParameter("secuencial_proveedor", secuencial_proveedor)
        .setParameter("secuencial_empresa", secuencial_empresa)
        .getResultList();

        for (Cuentas_Pagar item : resultado) {
            boolean vencida = false;
            try {
                LocalDate venc = LocalDate.parse(item.getFecha_Vencimiento().trim(), vencFormatter);
                BigDecimal saldo = BigDecimal.valueOf(item.getSaldo());
                vencida = venc.isBefore(LocalDate.now()) && saldo.compareTo(BigDecimal.ZERO) > 0;
            } catch (Exception e) {
                System.out.println("Error al parsear fecha de vencimiento: " + item.getFecha_Vencimiento());
            }

            model.addRow(new Object[] {
                String.valueOf(item.getSecuencial()),
                item.getCompra().getSecuencial(),
                item.getProveedor().getNombre(),
                item.getFecha(),
                item.getFecha_Vencimiento(),
                String.valueOf(item.getGran_Total()),
                item.getSaldo(),
                item.getPagado(),
                item.getSecuencial_Proveedor(),
                vencida // columna oculta
            });

            total_facturas += item.getGran_Total();
            saldo_pendiente += item.getSaldo();
        }

        jLabel8.setText(String.format("%.2f", total_facturas));
        jLabel7.setText(String.format("%.2f", saldo_pendiente));

        // Ocultar columna "Vencida"
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setWidth(0);

        // Aplicar color a filas vencidas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Object vencidaFlag = table.getValueAt(row, table.getColumnCount() - 1);

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else if (vencidaFlag instanceof Boolean && (Boolean) vencidaFlag) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        for (int i = 0; i < jTable1.getColumnCount() - 1; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

    } finally {
        context.close();
        emf.close();
    }
}

    
    
//    private void Cargar_Datos_CTAS_Cliente(int secuencial_cliente, int secuencial_empresa) {
//    double total_facturas = 0;
//    double saldo_pendiente = 0;
//
//    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//    model.setRowCount(0); // Limpiar tabla
//
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
//    EntityManager context = emf.createEntityManager();
//
//    try {
//        List<Cuentas_Cobrar> resultado = context.createQuery(
//            "SELECT cc FROM Cuentas_Cobrar cc JOIN cc.Venta v JOIN cc.Cliente c WHERE cc.Saldo > 0 AND cc.Secuencial_Cliente = :secuencial_cliente AND cc.Secuencial_Empresa = :secuencial_empresa",
//            Cuentas_Cobrar.class
//        )
//        .setParameter("secuencial_cliente", secuencial_cliente)
//        .setParameter("secuencial_empresa", secuencial_empresa)
//        .getResultList();
//
//        model.setRowCount(0);
//
//        for (Cuentas_Cobrar item : resultado) {
//            int rowIndex = model.getRowCount();
//            model.addRow(new Object[] {
//                String.valueOf(item.getSecuencial()),
//                item.getVenta().getSecuencial(),
//                item.getCliente().getNombre(),
//                item.getFecha().toString(),
//                item.getFecha_Vencimiento().toString(),
//                String.valueOf(item.getGran_Total()),
//                item.getSaldo(),
//                item.getPagado(),
//                item.getSecuencial_Cliente()
//            });
//
//            total_facturas += item.getGran_Total();
//            saldo_pendiente += item.getSaldo();
//
//            Object fechaVencObj = model.getValueAt(rowIndex, 4);
//            Object saldoObj = model.getValueAt(rowIndex, 6);
//
//            try {
//                LocalDate venc = LocalDate.parse(fechaVencObj.toString());
//                BigDecimal saldo = new BigDecimal(saldoObj.toString());
//
//                if (venc.isBefore(LocalDate.now()) && saldo.compareTo(BigDecimal.ZERO) > 0) {
//                    jTable1.setRowSelectionInterval(rowIndex, rowIndex);
//                    jTable1.setSelectionBackground(Color.PINK);
//                }
//            } catch (Exception e) {
//                // Ignorar errores de parseo
//            }
//        }
//
//        jLabel8.setText(String.format("%.2f", total_facturas));
//        jLabel7.setText(String.format("%.2f", saldo_pendiente));
//
//    } finally {
//        context.close();
//        emf.close();
//    }
//}

    
    
   public void Cargar_Datos_CTAS_Fecha(LocalDate fechaInicio, LocalDate fechaFin, int secuencial_empresa) {
    double total_facturas = 0;
    double saldo_pendiente = 0;

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Limpiar tabla

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager context = emf.createEntityManager();

    try {
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        DateTimeFormatter vencFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Cuentas_Pagar> resultado = context.createQuery(
            "SELECT cp FROM Cuentas_Pagar cp JOIN cp.Compra c JOIN cp.Proveedor p " +
            "WHERE cp.Saldo > 0 AND cp.Secuencial_Empresa = :secuencial_empresa " +
            "ORDER BY cp.Fecha DESC", Cuentas_Pagar.class)
            .setParameter("secuencial_empresa", secuencial_empresa)
            .getResultList();

        for (Cuentas_Pagar item : resultado) {
            LocalDateTime fechaConvertida;
            try {
                fechaConvertida = LocalDateTime.parse(item.getFecha().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha mal formateada: " + item.getFecha());
                continue;
            }

            if (!fechaConvertida.isBefore(inicio) && !fechaConvertida.isAfter(fin)) {
                boolean vencida = false;
                try {
                    LocalDate venc = LocalDate.parse(item.getFecha_Vencimiento().trim(), vencFormatter);
                    BigDecimal saldo = BigDecimal.valueOf(item.getSaldo());
                    vencida = venc.isBefore(LocalDate.now()) && saldo.compareTo(BigDecimal.ZERO) > 0;
                } catch (Exception e) {
                    System.out.println("Error al parsear fecha de vencimiento: " + item.getFecha_Vencimiento());
                }

                model.addRow(new Object[] {
                    item.getSecuencial(),
                    item.getCompra().getSecuencial(),
                    item.getProveedor().getNombre(),
                    item.getFecha(),
                    item.getFecha_Vencimiento(),
                    item.getGran_Total(),
                    item.getSaldo(),
                    item.getPagado(),
                    item.getSecuencial_Proveedor(),
                    vencida // columna oculta
                });

                total_facturas += item.getGran_Total();
                saldo_pendiente += item.getSaldo();
            }
        }

        jLabel8.setText(String.format("%.2f", total_facturas));
        jLabel7.setText(String.format("%.2f", saldo_pendiente));

        // Ocultar columna "Vencida"
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setWidth(0);

        // Aplicar color a filas vencidas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Object vencidaFlag = table.getValueAt(row, table.getColumnCount() - 1);

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else if (vencidaFlag instanceof Boolean && (Boolean) vencidaFlag) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        for (int i = 0; i < jTable1.getColumnCount() - 1; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        context.close();
        emf.close();
    }
}

    
//
//    public void Cargar_Datos_CTAS_Fecha(LocalDate fechaInicio, LocalDate fechaFin, int secuencial_empresa) {
//    double total_facturas = 0;
//    double saldo_pendiente = 0;
//
//    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//    model.setRowCount(0); // Limpiar tabla
//
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
//    EntityManager context = emf.createEntityManager();
//
//    try {
//        LocalDateTime inicio = fechaInicio.atStartOfDay();
//        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
//
//        List<Cuentas_Cobrar> resultado = context.createQuery(
//            "SELECT cc FROM Cuentas_Cobrar cc JOIN cc.Venta v JOIN cc.Cliente c " +
//            "WHERE cc.Saldo > 0 AND cc.Secuencial_Empresa = :secuencial_empresa " +
//            "ORDER BY cc.Fecha DESC", Cuentas_Cobrar.class)
//            .setParameter("secuencial_empresa", secuencial_empresa)
//            .getResultList();
//
//        for (Cuentas_Cobrar item : resultado) {
//            LocalDateTime fechaConvertida;
//            try {
//                fechaConvertida = LocalDateTime.parse(item.getFecha().trim(), formatter);
//            } catch (DateTimeParseException e) {
//                System.out.println("Fecha mal formateada: " + item.getFecha());
//                continue;
//            }
//
//            if (!fechaConvertida.isBefore(inicio) && !fechaConvertida.isAfter(fin)) {
//                int rowIndex = model.getRowCount();
//                model.addRow(new Object[] {
//                    item.getSecuencial(),
//                    item.getVenta().getSecuencial(),
//                    item.getCliente().getNombre(),
//                    item.getFecha(),
//                    item.getFecha_Vencimiento(),
//                    item.getGran_Total(),
//                    item.getSaldo(),
//                    item.getPagado(),
//                    item.getSecuencial_Cliente()
//                });
//
//                total_facturas += item.getGran_Total();
//                saldo_pendiente += item.getSaldo();
//
//                try {
//                    LocalDate venc = LocalDate.parse(item.getFecha_Vencimiento().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                    BigDecimal saldo = BigDecimal.valueOf(item.getSaldo());
//
//                    if (venc.isBefore(LocalDate.now()) && saldo.compareTo(BigDecimal.ZERO) > 0) {
//                        jTable1.setRowSelectionInterval(rowIndex, rowIndex);
//                        jTable1.setSelectionBackground(Color.pink);
//                    }
//                } catch (Exception e) {
//                    System.out.println("Error al parsear fecha de vencimiento: " + item.getFecha_Vencimiento());
//                }
//            }
//        }
//
//        jLabel8.setText(String.format("%.2f", total_facturas));
//        jLabel7.setText(String.format("%.2f", saldo_pendiente));
//
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        e.printStackTrace();
//    } finally {
//        context.close();
//        emf.close();
//    }
//}
//
//    
   
    
    private void inicializarModeloTabla() {
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    model.setColumnIdentifiers(new String[] {
        "S", "SF", "Nombre", "Fecha", "Fecha_Vencimiento",
        "Gran_Total", "Saldo", "Pagado", "SP", "Vencida"
    });

    jTable1.setModel(model);

    // Ocultar columna "Vencida"
    TableColumn vencidaCol = jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1);
    vencidaCol.setMinWidth(0);
    vencidaCol.setMaxWidth(0);
    vencidaCol.setWidth(0);

    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jTable1.setRowHeight(24);
    jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
    jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    jTable1.setGridColor(Color.LIGHT_GRAY);

    // Efecto zebra + centrado
    jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

    // 🔧 Autoredimensionar columna "Nombre"
    TableColumn nombreCol = jTable1.getColumnModel().getColumn(2); // Índice de "Nombre"
    int maxWidth = 100; // Ancho mínimo base

    for (int row = 0; row < jTable1.getRowCount(); row++) {
        TableCellRenderer renderer = jTable1.getCellRenderer(row, 2);
        Component comp = renderer.getTableCellRendererComponent(jTable1,
                jTable1.getValueAt(row, 2), false, false, row, 2);
        maxWidth = Math.max(comp.getPreferredSize().width + 10, maxWidth);
    }

    nombreCol.setPreferredWidth(maxWidth);
}

    
    
    
//    
//   private void inicializarModeloTabla() {
//    DefaultTableModel model = new DefaultTableModel() {
//        @Override
//        public boolean isCellEditable(int row, int column) {
//            return false; // Desactiva edición en todas las celdas
//        }
//    };
//
//    // Definir nombres de columnas (incluye columna oculta "Vencida")
//    model.setColumnIdentifiers(new String[] {
//        "S",
//        "SF",
//        "Nombre",
//        "Fecha",
//        "Fecha_Vencimiento",
//        "Gran_Total",
//        "Saldo",
//        "Pagado",
//        "SC",
//        "Vencida" // columna extra para marcar vencimiento
//    });
//
//    jTable1.setModel(model);
//
//    // Ocultar la columna "Vencida"
//    jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMinWidth(0);
//    jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMaxWidth(0);
//    jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setWidth(0);
//}
//
//    
    
//    
//private void inicializarModeloTabla() {
//    DefaultTableModel model = new DefaultTableModel() {
//        @Override
//        public boolean isCellEditable(int row, int column) {
//            return false; // Desactiva edición en todas las celdas
//        }
//    };
//
//    // Definir nombres de columnas
//    model.setColumnIdentifiers(new String[] {
//        "S",
//        "SF",
//        "Nombre",
//        "Fecha",
//        "Fecha_Vencimiento",
//        "Gran_Total",
//        "Saldo",
//        "Pagado",
//        "SC"
//    });
//
//    // Asignar el modelo a la tabla
//    jTable1.setModel(model);
//}

    
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
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 192, 192));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cuentas a Pagar");
        jLabel1.setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(35, 32, 45));

        jPanel2.setBackground(new java.awt.Color(35, 32, 45));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setText("Desde:");
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Hasta:");
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Todos las CTAS a pagar en un rango de fechas.");
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
                    .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jLabel5.setText("Total a Pagar:");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Total Comprado a Credito:");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("0");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));

        jLabel8.setText("0");
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/arrow_refresh.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.setToolTipText("Refrescar Datos.");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel9.setText("<html>Se muestran solo las cuentas con saldo pendiente. En el archivo de cada cliente podra ver todas las cuentas asociadas al mismo. Indistintamente del saldo.</html>");
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(35, 32, 45));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel11.setText("Todas las cuentas a pagar para un proveedor especifico");
        jLabel11.setForeground(new java.awt.Color(255, 255, 51));

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox1PropertyChange(evt);
            }
        });

        jLabel12.setText("Lista de Proveedores");
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setToolTipText("");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        jLabel13.setText("<html>Cuentas Vencidas</html>");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.setForeground(new java.awt.Color(255, 0, 51));
        jLabel13.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel13AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
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
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addComponent(jLabel10))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
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

        jLabel1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

 
  Cargar_Datos_CTAS_Fecha(datePicker1.getDate(),datePicker2.getDate(),Secuencial_Empresa);

        
// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked

        Cargar_Datos_CTAS_Pagar(Secuencial_Empresa);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        
        tableMouseDoubleClick(evt, jTable1);
    

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

    private void jComboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox1PropertyChange


        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1PropertyChange

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

        

        String seleccionado = jComboBox1.getSelectedItem().toString();
 Secuencial_Proveedor = Integer.parseInt(seleccionado.split("-")[0].trim());

      Cargar_Datos_CTAS_Proveedor(Secuencial_Proveedor,Secuencial_Empresa);



        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered

        jLabel13.setText("<html><u>Cuentas Vencidas</u></html>");
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseEntered

    
 public void Cargar_Datos_CTAS_Vencidas(int secuencial_empresa) {
    double totalFacturas = 0;
    double saldoPendiente = 0;

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Limpiar tabla

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MonituxPU");
    EntityManager context = emf.createEntityManager();

    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Cuentas_Pagar> resultado = context.createQuery(
            "SELECT cp FROM Cuentas_Pagar cp JOIN cp.Compra c JOIN cp.Proveedor p " +
            "WHERE cp.Saldo > 0 AND cp.Secuencial_Empresa = :secuencial_empresa " +
            "ORDER BY cp.Fecha_Vencimiento ASC", Cuentas_Pagar.class)
            .setParameter("secuencial_empresa", secuencial_empresa)
            .getResultList();

        for (Cuentas_Pagar item : resultado) {
            LocalDate vencimiento;
            try {
                vencimiento = LocalDate.parse(item.getFecha_Vencimiento().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha de vencimiento mal formateada: " + item.getFecha_Vencimiento());
                continue;
            }

            if (vencimiento.isBefore(LocalDate.now()) && item.getSaldo() > 0) {
                boolean vencida = true;

                model.addRow(new Object[] {
                    String.valueOf(item.getSecuencial()),
                    item.getCompra().getSecuencial(),
                    item.getProveedor().getNombre(),
                    item.getFecha(),
                    item.getFecha_Vencimiento(),
                    String.valueOf(item.getGran_Total()),
                    item.getSaldo(),
                    item.getPagado(),
                    item.getSecuencial_Proveedor(),
                    vencida // columna oculta
                });

                totalFacturas += item.getGran_Total();
                saldoPendiente += item.getSaldo();
            }
        }

        jLabel8.setText(String.format("%.2f", totalFacturas));
        jLabel7.setText(String.format("%.2f", saldoPendiente));

        // Ocultar columna "Vencida"
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setWidth(0);

        // Aplicar renderer una sola vez, fuera del bucle
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                Object vencidaFlag = table.getValueAt(row, table.getColumnCount() - 1);

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else if (vencidaFlag instanceof Boolean && (Boolean) vencidaFlag) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        };

        for (int i = 0; i < jTable1.getColumnCount() - 1; i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al cargar datos vencidos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        context.close();
        emf.close();
    }
}

    
    
    private void tableMouseDoubleClick(MouseEvent evt, JTable table) {
    if (evt.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(evt)) {
        try {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener valores de la fila seleccionada
            Object granTotalObj = table.getValueAt(selectedRow, table.getColumn("Gran_Total").getModelIndex());
            Object secuencialObj = table.getValueAt(selectedRow, table.getColumn("S").getModelIndex());
            Object clienteObj = table.getValueAt(selectedRow, table.getColumn("Nombre").getModelIndex());
            Object secuencialProveedorObj = table.getValueAt(selectedRow, table.getColumn("SP").getModelIndex());

            double granTotal = 0;
            int secuencial = 0;
            String nombre = "";
            int secuencialProveedor = 0;

            if (granTotalObj != null) {
                granTotal = Double.parseDouble(granTotalObj.toString());
            }

            if (secuencialObj != null) {
                secuencial = Integer.parseInt(secuencialObj.toString());
            }

            if (clienteObj != null) {
                nombre = clienteObj.toString();
            }

            if (secuencialProveedorObj != null) {
                try {
                    secuencialProveedor = Integer.parseInt(secuencialProveedorObj.toString());
                } catch (NumberFormatException ex) {
                    secuencialProveedor = 0;
                }
            }

            // Crear y mostrar ventana de cliente
            V_Abono_Proveedor vAbonoProveedor = new V_Abono_Proveedor(secuencial, secuencialProveedor, nombre, granTotal,this);
            vAbonoProveedor.setCliente_Nombre(nombre);
            vAbonoProveedor.setVisible(true);
   

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Llamar método para cargar datos
      //  cargarDatosCTAS();
    }
}

    
    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited

jLabel13.setText("<html>Cuentas Vencidas</html>");
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseExited

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked


        Cargar_Datos_CTAS_Vencidas(Secuencial_Empresa);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel13AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel13AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13AncestorAdded

    
   
    
  
    
   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private com.github.lgooddatepicker.components.DatePicker datePicker2;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
