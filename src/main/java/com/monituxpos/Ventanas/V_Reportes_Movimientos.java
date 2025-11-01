/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.monituxpos.Ventanas;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.monituxpos.Clases.Actividad;

import com.monituxpos.Clases.Util;
import com.monituxpos.Clases.Cliente;
import com.monituxpos.Clases.Compra;
import com.monituxpos.Clases.Cuentas_Cobrar;
import com.monituxpos.Clases.Egreso;
import com.monituxpos.Clases.Ingreso;
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Proveedor;
import com.monituxpos.Clases.Usuario;
import com.monituxpos.Clases.Venta;
import jakarta.persistence.EntityManager;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Reportes_Movimientos extends javax.swing.JPanel {

    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    
    
    /**
     * Creates new form V_Reportes_Movimientos
     */
    public V_Reportes_Movimientos() {
        initComponents();
        

        Util.llenarComboUsuario(jComboBox1, Secuencial_Empresa);
                
    }

    
    
    
    //******************************************
  
 public void RPT_Ingresos_Generales(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));

        List<Map<String, Object>> ingresos = new ArrayList<>();

        // Función local para convertir fechas
        Function<Object, LocalDateTime> convertirFecha = raw -> {
            if (raw == null) return null;
            String texto = raw.toString().trim()
                .replace("a. m.", "AM").replace("p. m.", "PM")
                .replace("a.m.", "AM").replace("p.m.", "PM");
            try { return LocalDateTime.parse(texto, formatter); } catch (Exception e) { return null; }
        };

        // VENTAS CONTADO
        List<Object[]> ventas = em.createQuery(
            "SELECT v.Fecha, v.Secuencial, v.Gran_Total, v.Secuencial_Cliente " +
            "FROM Venta v WHERE v.Secuencial_Empresa = :empresa AND v.Gran_Total > 0 AND v.Forma_Pago = 'Contado'",
            Object[].class).setParameter("empresa", secuencialEmpresa).getResultList();

        for (Object[] v : ventas) {
            LocalDateTime fecha = convertirFecha.apply(v[0]);
            if (fecha == null || fecha.isBefore(inicio) || fecha.isAfter(fin)) continue;

            Cliente c = em.find(Cliente.class, (int) v[3]);
            String nombre = (c != null && c.getNombre() != null && !c.getNombre().isBlank()) ? c.getNombre() : "Cliente #" + v[3];

            ingresos.add(Map.of(
                "Fecha", fecha,
                "Cliente", nombre,
                "Tipo", "Venta - Contado",
                "Descripcion", "Factura #" + v[1],
                "Monto", (double) v[2]
            ));
        }

        // COBROS CXC
        List<Object[]> cobros = em.createQuery(
            "SELECT c.Fecha, c.Secuencial_Factura, c.Total, c.Saldo, c.Secuencial_Cliente " +
            "FROM Cuentas_Cobrar c WHERE c.Secuencial_Empresa = :empresa AND c.Total > 0 AND c.Saldo < c.Total",
            Object[].class).setParameter("empresa", secuencialEmpresa).getResultList();

        for (Object[] c : cobros) {
            LocalDateTime fecha = convertirFecha.apply(c[0]);
            if (fecha == null || fecha.isBefore(inicio) || fecha.isAfter(fin)) continue;

            Cliente cl = em.find(Cliente.class, (int) c[4]);
            String nombre = (cl != null && cl.getNombre() != null && !cl.getNombre().isBlank()) ? cl.getNombre() : "Cliente #" + c[4];
            double monto = (double) c[2] - (double) c[3];

            ingresos.add(Map.of(
                "Fecha", fecha,
                "Cliente", nombre,
                "Tipo", "Cobro CXC",
                "Descripcion", "Factura #" + c[1],
                "Monto", monto
            ));
        }

        // INGRESOS MANUALES
        List<Object[]> ingresosManuales = em.createQuery(
            "SELECT i.Fecha, i.Secuencial_Factura, i.Tipo_Ingreso, i.Descripcion, i.Total " +
            "FROM Ingreso i WHERE i.Secuencial_Empresa = :empresa AND i.Total > 0",
            Object[].class).setParameter("empresa", secuencialEmpresa).getResultList();

        for (Object[] i : ingresosManuales) {
            LocalDateTime fecha = convertirFecha.apply(i[0]);
            if (fecha == null || fecha.isBefore(inicio) || fecha.isAfter(fin)) continue;

            String nombreCliente = "Cliente N/D";
            if (i[1] != null) {
                int secFactura = (int) i[1];
                Cuentas_Cobrar cx = em.createQuery(
                    "SELECT c FROM Cuentas_Cobrar c WHERE c.Secuencial_Factura = :factura",
                    Cuentas_Cobrar.class).setParameter("factura", secFactura).setMaxResults(1)
                    .getResultStream().findFirst().orElse(null);

                if (cx != null) {
                    Cliente cl = em.find(Cliente.class, cx.getSecuencial_Cliente());
                    nombreCliente = (cl != null && cl.getNombre() != null && !cl.getNombre().isBlank()) ? cl.getNombre() : "Cliente #" + cx.getSecuencial_Cliente();
                } else {
                    Venta v = em.find(Venta.class, secFactura);
                    if (v != null) {
                        Cliente cl = em.find(Cliente.class, v.getSecuencial_Cliente());
                        nombreCliente = (cl != null && cl.getNombre() != null && !cl.getNombre().isBlank()) ? cl.getNombre() : "Cliente #" + v.getSecuencial_Cliente();
                    }
                }
            }

            ingresos.add(Map.of(
                "Fecha", fecha,
                "Cliente", nombreCliente,
                "Tipo", "Ingreso - " + i[2],
                "Descripcion", i[3],
                "Monto", (double) i[4]
            ));
        }

        ingresos.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));
        Map<String, List<Map<String, Object>>> agrupado = ingresos.stream()
            .collect(Collectors.groupingBy(r -> r.get("Cliente").toString(), LinkedHashMap::new, Collectors.toList()));

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9);
        Font fontBold = new Font(Font.HELVETICA, 9, Font.BOLD);

        document.add(new Paragraph("📊 Reporte de Ingresos", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 3f, 2f, 2f });

        double totalGeneral = 0.0;

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupado.entrySet()) {
            String cliente = grupo.getKey();
            List<Map<String, Object>> lista = grupo.getValue();
            double totalCliente = lista.stream().mapToDouble(r -> (double) r.get("Monto")).sum();
            totalGeneral += totalCliente;

            PdfPCell celdaGrupo = new PdfPCell(new Phrase("👤 Cliente: " + cliente, fontBold));
            celdaGrupo.setColspan(4);
            celdaGrupo.setBackgroundColor(Color.LIGHT_GRAY);
            celdaGrupo.setPadding(5f);
            tabla.addCell(celdaGrupo);

            for (Map<String, Object> r : lista) {
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
                tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            }

            tabla.addCell(new Phrase("🧮 Total del cliente:", fontBold));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(String.format("%,.2f", totalCliente), fontBold));
        }

        PdfPCell totalLabel = new PdfPCell(new Phrase("💰 TOTAL GENERAL DE INGRESOS:", fontBold));
        totalLabel.setColspan(3);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(6f);
        tabla.addCell(totalLabel);

        PdfPCell totalMonto = new PdfPCell(new Phrase(String.format("%,.2f", totalGeneral), fontBold));
       
                totalMonto.setBackgroundColor(Color.LIGHT_GRAY);
        totalMonto.setPadding(4f);
        tabla.addCell(totalMonto);

        document.add(tabla);
        document.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        // Mostrar visor
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de ingresos generales");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

public void RPT_Egresos_Generales(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));

        List<Map<String, Object>> egresos = new ArrayList<>();

        Function<Object, LocalDateTime> convertirFecha = raw -> {
            if (raw == null) return null;
            String texto = raw.toString().trim()
                .replace("a. m.", "AM").replace("p. m.", "PM")
                .replace("a.m.", "AM").replace("p.m.", "PM");
            try { return LocalDateTime.parse(texto, formatter); } catch (Exception e) { return null; }
        };

        List<Object[]> registros = em.createQuery(
            "SELECT e.Fecha, e.Secuencial_Factura, e.Tipo_Egreso, e.Descripcion, e.Total " +
            "FROM Egreso e WHERE e.Secuencial_Empresa = :empresa AND e.Total > 0",
            Object[].class).setParameter("empresa", secuencialEmpresa).getResultList();

        for (Object[] e : registros) {
            LocalDateTime fecha = convertirFecha.apply(e[0]);
            if (fecha == null || fecha.isBefore(inicio) || fecha.isAfter(fin)) continue;

            String proveedor = "Proveedor N/D";
            if (e[1] != null) {
                int secFactura = (int) e[1];
                Compra compra = em.find(Compra.class, secFactura);
                if (compra != null) {
                    Proveedor p = em.find(Proveedor.class, compra.getSecuencial_Proveedor());
                    proveedor = (p != null && p.getNombre() != null && !p.getNombre().isBlank()) ? p.getNombre() : "Proveedor #" + compra.getSecuencial_Proveedor();
                }
            }

            egresos.add(Map.of(
                "Fecha", fecha,
                "Proveedor", proveedor,
                "Tipo", "Egreso - " + e[2],
                "Descripcion", e[3],
                "Monto", (double) e[4]
            ));
        }

        egresos.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));
        Map<String, List<Map<String, Object>>> agrupado = egresos.stream()
            .collect(Collectors.groupingBy(r -> r.get("Proveedor").toString(), LinkedHashMap::new, Collectors.toList()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9);
        Font fontBold = new Font(Font.HELVETICA, 9, Font.BOLD);

        document.add(new Paragraph("📉 Reporte de Egresos", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 3f, 2f, 2f });

        double totalGeneral = 0.0;

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupado.entrySet()) {
            String proveedor = grupo.getKey();
            List<Map<String, Object>> lista = grupo.getValue();
            double totalProveedor = lista.stream().mapToDouble(r -> (double) r.get("Monto")).sum();
            totalGeneral += totalProveedor;

            PdfPCell celdaGrupo = new PdfPCell(new Phrase("🏢 Proveedor: " + proveedor, fontBold));
            celdaGrupo.setColspan(4);
            celdaGrupo.setBackgroundColor(Color.LIGHT_GRAY);
            celdaGrupo.setPadding(5f);
            tabla.addCell(celdaGrupo);

            for (Map<String, Object> r : lista) {
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
                tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            }

            tabla.addCell(new Phrase("🧮 Total del proveedor:", fontBold));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(String.format("%,.2f", totalProveedor), fontBold));
        }

        PdfPCell totalLabel = new PdfPCell(new Phrase("💸 TOTAL GENERAL DE EGRESOS:", fontBold));
        totalLabel.setColspan(3);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(6f);
        tabla.addCell(totalLabel);

        PdfPCell totalMonto = new PdfPCell(new Phrase(String.format("%,.2f", totalGeneral), fontBold));
        totalMonto.setBackgroundColor(Color.LIGHT_GRAY);
        totalMonto.setPadding(4f);
        tabla.addCell(totalMonto);

        document.add(tabla);
        document.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de egresos generales");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}



public void RPT_Pagos_Recibidos(LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        List<Map<String, Object>> pagos = new ArrayList<>();

        List<Ingreso> ingresosBase = em.createQuery(
            "SELECT i FROM Ingreso i WHERE i.Secuencial_Empresa = :empresa AND i.Total > 0 AND i.Tipo_Ingreso = 'Pago Recibido'",
            Ingreso.class).setParameter("empresa", empresa).getResultList();

        for (Ingreso i : ingresosBase) {
            LocalDateTime fecha;
            try {
                fecha = LocalDateTime.parse(i.getFecha().trim(), formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            pagos.add(Map.of(
                "Fecha", fecha,
                "Descripcion", i.getDescripcion(),
                "Monto", i.getTotal()
            ));
        }

        pagos.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("💵 Reporte de Pagos Recibidos", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 5f, 2f });

        double totalPagos = 0.0;

        for (Map<String, Object> r : pagos) {
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            totalPagos += (double) r.get("Monto");
        }

        PdfPCell totalLabel = new PdfPCell(new Phrase("🧾 TOTAL DE PAGOS RECIBIDOS:", fontBold));
        totalLabel.setColspan(2);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(6f);
        tabla.addCell(totalLabel);

        PdfPCell totalMonto = new PdfPCell(new Phrase(String.format("%,.2f", totalPagos), fontBold));
        totalMonto.setBackgroundColor(new Color(200, 255, 200)); // Verde claro
        totalMonto.setPadding(4f);
        tabla.addCell(totalMonto);

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        // Mostrar visor
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de pagos recibidos");
        visor.mostrar();

       

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

public void RPT_Pagos_Realizados(LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        List<Map<String, Object>> pagos = new ArrayList<>();

        List<Egreso> egresosBase = em.createQuery(
            "SELECT e FROM Egreso e WHERE e.Secuencial_Empresa = :empresa AND e.Total > 0 AND e.Tipo_Egreso = 'Pago Realizado'",
            Egreso.class).setParameter("empresa", empresa).getResultList();

        for (Egreso e : egresosBase) {
            LocalDateTime fecha;
            try {
                fecha = LocalDateTime.parse(e.getFecha().trim(), formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            pagos.add(Map.of(
                "Fecha", fecha,
                "Descripcion", e.getDescripcion(),
                "Monto", e.getTotal()
            ));
        }

        pagos.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("💸 Reporte de Pagos Realizados", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 5f, 2f });

        double totalPagos = 0.0;

        for (Map<String, Object> r : pagos) {
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            totalPagos += (double) r.get("Monto");
        }

        PdfPCell totalLabel = new PdfPCell(new Phrase("🧾 TOTAL DE PAGOS REALIZADOS:", fontBold));
        totalLabel.setColspan(2);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(6f);
        tabla.addCell(totalLabel);

        PdfPCell totalMonto = new PdfPCell(new Phrase(String.format("%,.2f", totalPagos), fontBold));
        totalMonto.setBackgroundColor(new Color(255, 230, 230)); // Rojo claro
        totalMonto.setPadding(4f);
        tabla.addCell(totalMonto);

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        // Mostrar visor
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de pagos realizados");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}


public void RPT_Ingresos_Manuales(LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));

        List<Map<String, Object>> ingresos = new ArrayList<>();

        List<Ingreso> ingresosBase = em.createQuery(
            "SELECT i FROM Ingreso i WHERE i.Secuencial_Empresa = :empresa AND i.Total > 0 AND i.Tipo_Ingreso = 'Ingreso Manual'",
            Ingreso.class).setParameter("empresa", empresa).getResultList();

        for (Ingreso i : ingresosBase) {
            LocalDateTime fecha;
            try {
                String texto = i.getFecha().trim().replace("a. m.", "AM").replace("p. m.", "PM");
                fecha = LocalDateTime.parse(texto, formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            ingresos.add(Map.of(
                "Fecha", fecha,
                "Descripcion", i.getDescripcion(),
                "Monto", i.getTotal()
            ));
        }

        ingresos.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("📝 Reporte de Ingresos Manuales", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 6f, 2f });

        double total = 0.0;

        for (Map<String, Object> r : ingresos) {
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            total += (double) r.get("Monto");
        }

        PdfPCell totalLabel = new PdfPCell(new Phrase("🧮 TOTAL DE INGRESOS MANUALES:", fontBold));
        totalLabel.setColspan(2);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(6f);
        tabla.addCell(totalLabel);

        PdfPCell totalMonto = new PdfPCell(new Phrase(String.format("%,.2f", total), fontBold));
        totalMonto.setBackgroundColor(new Color(200, 255, 200));
        totalMonto.setPadding(4f);
        tabla.addCell(totalMonto);

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de ingresos manuales");
        visor.mostrar();

       

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}


public void RPT_Egresos_Manuales(LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));

        List<Map<String, Object>> egresos = new ArrayList<>();

        List<Egreso> egresosBase = em.createQuery(
            "SELECT e FROM Egreso e WHERE e.Secuencial_Empresa = :empresa AND e.Total > 0 AND e.Tipo_Egreso = 'Egreso Manual'",
            Egreso.class).setParameter("empresa", empresa).getResultList();

        for (Egreso e : egresosBase) {
            LocalDateTime fecha;
            try {
                String texto = e.getFecha().trim().replace("a. m.", "AM").replace("p. m.", "PM");
                fecha = LocalDateTime.parse(texto, formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            egresos.add(Map.of(
                "Fecha", fecha,
                "Descripcion", e.getDescripcion(),
                "Monto", e.getTotal()
            ));
        }

        egresos.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("📤 Reporte de Egresos Manuales", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 6f, 2f });

        double total = 0.0;

        for (Map<String, Object> r : egresos) {
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            total += (double) r.get("Monto");
        }

        PdfPCell totalLabel = new PdfPCell(new Phrase("📉 TOTAL DE EGRESOS MANUALES:", fontBold));
        totalLabel.setColspan(2);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(6f);
        tabla.addCell(totalLabel);

        PdfPCell totalMonto = new PdfPCell(new Phrase(String.format("%,.2f", total), fontBold));
        totalMonto.setBackgroundColor(new Color(255, 230, 230)); // Rojo claro
        totalMonto.setPadding(4f);
        tabla.addCell(totalMonto);

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de egresos manuales");
        visor.mostrar();

      

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

public void RPT_Ingresos_Por_Usuario(LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));

        List<Ingreso> ingresosBase = em.createQuery(
            "SELECT i FROM Ingreso i WHERE i.Secuencial_Empresa = :empresa AND i.Total > 0",
            Ingreso.class).setParameter("empresa", empresa).getResultList();

        List<Map<String, Object>> ingresosFiltrados = new ArrayList<>();

        for (Ingreso i : ingresosBase) {
            LocalDateTime fecha;
            try {
                String texto = i.getFecha().trim().replace("a. m.", "AM").replace("p. m.", "PM");
                fecha = LocalDateTime.parse(texto, formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            Usuario usuario = em.find(Usuario.class, i.getSecuencial_Usuario());
            String nombreUsuario = (usuario != null && usuario.getNombre() != null && !usuario.getNombre().isBlank())
                ? usuario.getNombre()
                : "Usuario #" + i.getSecuencial_Usuario();

            ingresosFiltrados.add(Map.of(
                "Fecha", fecha,
                "Usuario", nombreUsuario,
                "Tipo", "Ingreso - " + i.getTipo_Ingreso(),
                "Descripcion", i.getDescripcion(),
                "Monto", (double) i.getTotal()
            ));
        }

        ingresosFiltrados.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        Map<String, List<Map<String, Object>>> agrupadoPorUsuario = ingresosFiltrados.stream()
            .collect(Collectors.groupingBy(r -> (String) r.get("Usuario")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("👥 Reporte de Ingresos por Usuario", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 4f, 2f, 2f });

        double totalGeneral = 0.0;

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupadoPorUsuario.entrySet()) {
            String usuario = grupo.getKey();
            List<Map<String, Object>> ingresos = grupo.getValue();
            double totalUsuario = ingresos.stream().mapToDouble(r -> (double) r.get("Monto")).sum();

            PdfPCell usuarioHeader = new PdfPCell(new Phrase("👤 Usuario: " + usuario, fontBold));
            usuarioHeader.setColspan(4);
            usuarioHeader.setBackgroundColor(new Color(220, 220, 220));
            usuarioHeader.setPadding(4f);
            tabla.addCell(usuarioHeader);

            for (Map<String, Object> r : ingresos) {
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
                tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            }

            tabla.addCell(new Phrase("🧮 Total del usuario:", fontBold));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(String.format("%,.2f", totalUsuario), fontBold));

            totalGeneral += totalUsuario;
        }

        tabla.addCell(new Phrase("💰 TOTAL GENERAL DE INGRESOS:", fontBold));
        tabla.addCell(new Phrase(""));
        tabla.addCell(new Phrase(""));
        PdfPCell totalFinal = new PdfPCell(new Phrase(String.format("%,.2f", totalGeneral), fontBold));
        totalFinal.setBackgroundColor(new Color(230, 230, 230));
        totalFinal.setPadding(4f);
        tabla.addCell(totalFinal);

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de ingresos por usuario");
        visor.mostrar();

       

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

public void RPT_Egresos_Por_Usuario(LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));

        List<Egreso> egresosBase = em.createQuery(
            "SELECT e FROM Egreso e WHERE e.Secuencial_Empresa = :empresa AND e.Total > 0",
            Egreso.class).setParameter("empresa", empresa).getResultList();

        List<Map<String, Object>> egresosFiltrados = new ArrayList<>();

        for (Egreso e : egresosBase) {
            LocalDateTime fecha;
            try {
                String texto = e.getFecha().trim().replace("a. m.", "AM").replace("p. m.", "PM");
                fecha = LocalDateTime.parse(texto, formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            Usuario usuario = em.find(Usuario.class, e.getSecuencial_Usuario());
            String nombreUsuario = (usuario != null && usuario.getNombre() != null && !usuario.getNombre().isBlank())
                ? usuario.getNombre()
                : "Usuario #" + e.getSecuencial_Usuario();

            egresosFiltrados.add(Map.of(
                "Fecha", fecha,
                "Usuario", nombreUsuario,
                "Tipo", "Egreso - " + e.getTipo_Egreso(),
                "Descripcion", e.getDescripcion(),
                "Monto", (double) e.getTotal()
            ));
        }

        egresosFiltrados.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        Map<String, List<Map<String, Object>>> agrupadoPorUsuario = egresosFiltrados.stream()
            .collect(Collectors.groupingBy(r -> (String) r.get("Usuario")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("📤 Reporte de Egresos por Usuario", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 4f, 2f, 2f });

        double totalGeneral = 0.0;

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupadoPorUsuario.entrySet()) {
            String usuario = grupo.getKey();
            List<Map<String, Object>> egresos = grupo.getValue();
            double totalUsuario = egresos.stream().mapToDouble(r -> (double) r.get("Monto")).sum();

            PdfPCell usuarioHeader = new PdfPCell(new Phrase("👤 Usuario: " + usuario, fontBold));
            usuarioHeader.setColspan(4);
            usuarioHeader.setBackgroundColor(new Color(220, 220, 220));
            usuarioHeader.setPadding(4f);
            tabla.addCell(usuarioHeader);

            for (Map<String, Object> r : egresos) {
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
                tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%,.2f", (double) r.get("Monto")), fontNormal));
            }

            tabla.addCell(new Phrase("🧾 Total del usuario:", fontBold));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(""));
            tabla.addCell(new Phrase(String.format("%,.2f", totalUsuario), fontBold));

            totalGeneral += totalUsuario;
        }

        tabla.addCell(new Phrase("💰 TOTAL GENERAL DE EGRESOS:", fontBold));
        tabla.addCell(new Phrase(""));
        tabla.addCell(new Phrase(""));
        PdfPCell totalFinal = new PdfPCell(new Phrase(String.format("%,.2f", totalGeneral), fontBold));
        totalFinal.setBackgroundColor(new Color(255, 230, 230));
        totalFinal.setPadding(4f);
        tabla.addCell(totalFinal);

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de egresos por usuario");
        visor.mostrar();

      

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

public void RPT_Actividades(LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        List<Actividad> actividadesBase = em.createQuery(
            "SELECT a FROM Actividad a WHERE a.Secuencial_Empresa = :empresa",
            Actividad.class).setParameter("empresa", empresa).getResultList();

        List<Map<String, Object>> actividadesFiltradas = new ArrayList<>();

        for (Actividad a : actividadesBase) {
            LocalDateTime fecha;
            try {
                fecha = LocalDateTime.parse(a.getFecha().trim(), formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            Usuario usuario = em.find(Usuario.class, a.getSecuencial_Usuario());
            String nombreUsuario = (usuario != null && usuario.getNombre() != null && !usuario.getNombre().isBlank())
                ? usuario.getNombre()
                : "Usuario #" + a.getSecuencial_Usuario();

            actividadesFiltradas.add(Map.of(
                "Fecha", fecha,
                "Usuario", nombreUsuario,
                "Descripcion", a.getDescripcion()
            ));
        }

        actividadesFiltradas.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        Map<String, List<Map<String, Object>>> agrupadoPorUsuario = actividadesFiltradas.stream()
            .collect(Collectors.groupingBy(r -> (String) r.get("Usuario")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("📋 Reporte de Actividades", fontHeader));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 6f });

        int totalGeneral = 0;

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupadoPorUsuario.entrySet()) {
            String usuario = grupo.getKey();
            List<Map<String, Object>> actividades = grupo.getValue();

            PdfPCell usuarioHeader = new PdfPCell(new Phrase("👤 Usuario: " + usuario, fontBold));
            usuarioHeader.setColspan(2);
            usuarioHeader.setBackgroundColor(new Color(220, 220, 220));
            usuarioHeader.setPadding(4f);
            tabla.addCell(usuarioHeader);

            for (Map<String, Object> r : actividades) {
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
                tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
            }

            tabla.addCell(new Phrase("🧾 Total actividades:", fontBold));
            tabla.addCell(new Phrase(String.valueOf(actividades.size()), fontBold));

            totalGeneral += actividades.size();
        }

        tabla.addCell(new Phrase("🔢 TOTAL GENERAL DE ACTIVIDADES:", fontBold));
        PdfPCell totalFinal = new PdfPCell(new Phrase(String.valueOf(totalGeneral), fontBold));
        totalFinal.setBackgroundColor(new Color(230, 230, 230));
        totalFinal.setPadding(4f);
        tabla.addCell(totalFinal);

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de actividades por usuario");
        visor.mostrar();

      

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}


public void RPT_Actividades_De_Usuario(LocalDate fechaInicio, LocalDate fechaFin, String seleccionComboUsuario) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        int empresa = V_Menu_Principal.getSecuencial_Empresa();
        int secuencialUsuario = Integer.parseInt(seleccionComboUsuario.split("-")[0].trim());
        LocalDateTime desde = fechaInicio.atStartOfDay();
        LocalDateTime hasta = fechaFin.plusDays(1).atStartOfDay().minusSeconds(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        List<Actividad> actividadesBase = em.createQuery(
            "SELECT a FROM Actividad a WHERE a.Secuencial_Empresa = :empresa AND a.Secuencial_Usuario = :usuario",
            Actividad.class)
            .setParameter("empresa", empresa)
            .setParameter("usuario", secuencialUsuario)
            .getResultList();

        List<Map<String, Object>> actividadesFiltradas = new ArrayList<>();

        for (Actividad a : actividadesBase) {
            LocalDateTime fecha;
            try {
                fecha = LocalDateTime.parse(a.getFecha().trim(), formatter);
            } catch (Exception ex) {
                continue;
            }

            if (fecha.isBefore(desde) || fecha.isAfter(hasta)) continue;

            Usuario usuario = em.find(Usuario.class, a.getSecuencial_Usuario());
            String nombreUsuario = (usuario != null && usuario.getNombre() != null && !usuario.getNombre().isBlank())
                ? usuario.getNombre()
                : "Usuario #" + a.getSecuencial_Usuario();

            actividadesFiltradas.add(Map.of(
                "Fecha", fecha,
                "Usuario", nombreUsuario,
                "Descripcion", a.getDescripcion()
            ));
        }

        actividadesFiltradas.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        String nombreUsuario = actividadesFiltradas.isEmpty()
            ? "Usuario #" + secuencialUsuario
            : (String) actividadesFiltradas.get(0).get("Usuario");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 20, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 10);
        Font fontBold = new Font(Font.HELVETICA, 10, Font.BOLD);

        document.add(new Paragraph("📒 Reporte de Actividades del Usuario", fontHeader));
        document.add(new Paragraph("Usuario: " + nombreUsuario, fontNormal));
        document.add(new Paragraph("Fechas: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
            " al " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[] { 2f, 6f });

        for (Map<String, Object> r : actividadesFiltradas) {
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
            tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
        }

        tabla.addCell(new Phrase("🔢 Total actividades:", fontBold));
        tabla.addCell(new Phrase(String.valueOf(actividadesFiltradas.size()), fontBold));

        document.add(tabla);
        document.add(new Paragraph("Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de actividades del usuario");
        visor.mostrar();

      

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}


//******************************************
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        datePicker2 = new com.github.lgooddatepicker.components.DatePicker();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setText("Desde:");
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Hasta:");
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel2.setBackground(new java.awt.Color(35, 32, 45));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Ingresos");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setText("Todos los Ingresos");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Pagos Recibidos");
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Ingresos Manuales");
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("Ingresos por Usuario");
        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Generar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Generar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Generar");
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
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton1))
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jButton4))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton3))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jButton2))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jButton1))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(35, 32, 45));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Egresos");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel13.setText("Egresos por Usuario");
        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));

        jLabel14.setText("Egresos Manuales");
        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));

        jLabel15.setText("Pagos Realizados");
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));

        jLabel16.setText("Todos los Egresos");
        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));

        jButton7.setText("Generar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Generar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Generar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Generar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addComponent(jButton8)
                    .addComponent(jButton7)
                    .addComponent(jButton10))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jButton7))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jButton8))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jButton9))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jButton10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(35, 32, 45));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Actividades");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("Todos las Actividades");
        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));

        jButton5.setText("Generar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel12.setText("Por Usuario:");
        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));

        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });

        jButton6.setText("Generar");
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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(3, 3, 3))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Todos los reportes requieren un rango de fechas.");
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Alien.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(22, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70))))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel2, jPanel3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed


        
        
        LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Ingresos_Generales(Secuencial_Empresa, fechaInicio, fechaFin);




        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed


          
        LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Egresos_Generales(Secuencial_Empresa, fechaInicio, fechaFin);



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed


             LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Pagos_Recibidos(fechaInicio, fechaFin);


        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed


        
             LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Pagos_Realizados(fechaInicio, fechaFin);


        



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed



               LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Ingresos_Manuales(fechaInicio, fechaFin);


        


        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed


        

               LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Egresos_Manuales(fechaInicio, fechaFin);


        




        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        

               LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Ingresos_Por_Usuario(fechaInicio, fechaFin);




        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed


        
               LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Egresos_Por_Usuario(fechaInicio, fechaFin);



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked

        Util.llenarComboUsuario(jComboBox1, Secuencial_Empresa);

        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed


        
        
               LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Actividades(fechaInicio, fechaFin);





        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed


        
               LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();
         String seleccionado=jComboBox1.getSelectedItem().toString();
         int secuencialUsuario = Integer.parseInt(seleccionado.split("-")[0].trim());
         
         RPT_Actividades_De_Usuario(fechaInicio,fechaFin, seleccionado);
         
         
        

        
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private com.github.lgooddatepicker.components.DatePicker datePicker2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
