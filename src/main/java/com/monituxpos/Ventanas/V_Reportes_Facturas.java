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
import com.monituxpos.Clases.MonituxDBContext;
import com.monituxpos.Clases.Util;
import jakarta.persistence.EntityManager;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Reportes_Facturas extends javax.swing.JPanel {

    private int Secuencial_Empresa= V_Menu_Principal.getSecuencial_Empresa();
    
    /**
     * Creates new form V_Reportes_Facturas
     */
    public V_Reportes_Facturas() {
        initComponents();
        
        Util.llenarComboCliente(jComboBox4, Secuencial_Empresa);
        Util.llenarComboProveedor(jComboBox5, Secuencial_Empresa);
        
        
    }

    
    
    
    
    
    //************************
    
   public void RPT_V_Tipo(int secuencialEmpresa, String tipoVenta, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Obtener registros filtrados
        List<Object[]> registros = em.createQuery(
            "SELECT v.Secuencial, v.Fecha, c.Nombre, v.Gran_Total " +
            "FROM Venta v JOIN Cliente c ON v.Secuencial_Cliente = c.Secuencial " +
            "WHERE v.Tipo = :tipo AND v.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("tipo", tipoVenta)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> datos = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Cliente", r[2]);
                fila.put("Total", r[3]);
                datos.add(fila);
            }
        }

        datos.sort(Comparator.comparing(d -> (LocalDateTime) d.get("Fecha")));
        double total = datos.stream().mapToDouble(d -> (double) d.get("Total")).sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("🧾 Reporte de Ventas por Tipo", fontHeader));
        doc.add(new Paragraph("Tipo: " + tipoVenta.toUpperCase(), fontNormal));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        String[] columnas = { "Factura", "Fecha", "Cliente", "Total"};
        for (String col : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
            celda.setBackgroundColor(Color.LIGHT_GRAY);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5f);
            tabla.addCell(celda);
        }

        for (Map<String, Object> r : datos) {
            tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Cliente").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
        }

        PdfPCell resumenLabel = new PdfPCell(new Phrase("🧮 TOTAL:", fontBold));
        resumenLabel.setColspan(3);
        resumenLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumenLabel.setPadding(6f);
        tabla.addCell(resumenLabel);

        PdfPCell resumenValor = new PdfPCell(new Phrase(String.format("%.2f ", total), fontBold));
        resumenValor.setBackgroundColor(Color.LIGHT_GRAY);
        resumenValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumenValor.setPadding(6f);
        tabla.addCell(resumenValor);

        doc.add(tabla);
        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        // Mostrar visor
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Ventas por tipo - " + tipoVenta);
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

   public void RPT_C_Tipo(int secuencialEmpresa, String tipoCompra, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Obtener registros filtrados
        List<Object[]> registros = em.createQuery(
            "SELECT c.Secuencial, c.Fecha, p.Nombre, c.Gran_Total " +
            "FROM Compra c JOIN Proveedor p ON c.Secuencial_Proveedor = p.Secuencial " +
            "WHERE c.Tipo = :tipo AND c.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("tipo", tipoCompra)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> datos = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Proveedor", r[2]);
                fila.put("Total", r[3]);
                datos.add(fila);
            }
        }

        datos.sort(Comparator.comparing(d -> (LocalDateTime) d.get("Fecha")));
        double total = datos.stream().mapToDouble(d -> (double) d.get("Total")).sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("📦 Reporte de Compras por Tipo", fontHeader));
        doc.add(new Paragraph("Tipo: " + tipoCompra.toUpperCase(), fontNormal));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        String[] columnas = { "Factura", "Fecha", "Proveedor", "Total"};
        for (String col : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
            celda.setBackgroundColor(Color.LIGHT_GRAY);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5f);
            tabla.addCell(celda);
        }

        for (Map<String, Object> r : datos) {
            tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Proveedor").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
        }

        PdfPCell resumenLabel = new PdfPCell(new Phrase("🧮 TOTAL:", fontBold));
        resumenLabel.setColspan(3);
        resumenLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumenLabel.setPadding(6f);
        tabla.addCell(resumenLabel);

        PdfPCell resumenValor = new PdfPCell(new Phrase(String.format("%.2f ", total), fontBold));
        resumenValor.setBackgroundColor(Color.LIGHT_GRAY);
        resumenValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumenValor.setPadding(6f);
        tabla.addCell(resumenValor);

        doc.add(tabla);
        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        // Mostrar visor
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Compras por tipo - " + tipoCompra);
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

   public void RPT_V_Cliente(int secuencialCliente, int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT v.Secuencial, v.Fecha, c.Nombre, v.Tipo, v.Gran_Total " +
            "FROM Venta v JOIN Cliente c ON v.Secuencial_Cliente = c.Secuencial " +
            "WHERE v.Secuencial_Cliente = :cliente AND v.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("cliente", secuencialCliente)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Cliente", r[2]);
                fila.put("Tipo", r[3]);
                fila.put("Total", r[4]);
                registrosFiltrados.add(fila);
            }
        }

        Map<String, List<Map<String, Object>>> agrupados = registrosFiltrados.stream()
            .collect(Collectors.groupingBy(r -> r.get("Cliente").toString()));

        double totalContado = registrosFiltrados.stream()
            .filter(r -> "Contado".equals(r.get("Tipo")))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalCredito = registrosFiltrados.stream()
            .filter(r -> "Credito".equals(r.get("Tipo")))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalGeneral = registrosFiltrados.stream()
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("🧾 Ventas por Cliente", fontHeader));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupados.entrySet()) {
            doc.add(new Paragraph("👤 Cliente: " + grupo.getKey(), fontBold));
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] columnas = { "Factura", "Fecha", "Tipo de Venta", "Total" };
            for (String col : columnas) {
                PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(5f);
                tabla.addCell(celda);
            }

            double totalCliente = 0;

            for (Map<String, Object> r : grupo.getValue()) {
                tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
                totalCliente += (double) r.get("Total");
            }

            doc.add(tabla);
            doc.add(new Paragraph("📦 Total vendido al cliente: " + String.format("%.2f", totalCliente), fontBold));
            doc.add(Chunk.NEWLINE);
        }

        doc.add(new Paragraph("📋 Ventas Totales", fontBold));
        doc.add(new Paragraph("💳 Total CRÉDITO: " + String.format("%.2f", totalCredito), fontNormal));
        doc.add(new Paragraph("💵 Total CONTADO: " + String.format("%.2f", totalContado), fontNormal));
        doc.add(new Paragraph("🧮 Total GENERAL: " + String.format("%.2f", totalGeneral), fontBold));
        doc.add(Chunk.NEWLINE);

        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Ventas por cliente - " + secuencialCliente);
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}
 
   public void RPT_C_Proveedor(int secuencialProveedor, int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT c.Secuencial, c.Fecha, p.Nombre, c.Tipo, c.Gran_Total " +
            "FROM Compra c JOIN Proveedor p ON c.Secuencial_Proveedor = p.Secuencial " +
            "WHERE c.Secuencial_Proveedor = :proveedor AND c.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("proveedor", secuencialProveedor)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Proveedor", r[2]);
                fila.put("Tipo", r[3]);
                fila.put("Total", r[4]);
                registrosFiltrados.add(fila);
            }
        }

        Map<String, List<Map<String, Object>>> agrupados = registrosFiltrados.stream()
            .collect(Collectors.groupingBy(r -> r.get("Proveedor").toString()));

        double totalContado = registrosFiltrados.stream()
            .filter(r -> "Contado".equals(r.get("Tipo")))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalCredito = registrosFiltrados.stream()
            .filter(r -> "Credito".equals(r.get("Tipo")))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalGeneral = registrosFiltrados.stream()
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("📦 Compras por Proveedor", fontHeader));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupados.entrySet()) {
            doc.add(new Paragraph("🏢 Proveedor: " + grupo.getKey(), fontBold));
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] columnas = { "Compra", "Fecha", "Tipo de Compra", "Total" };
            for (String col : columnas) {
                PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(5f);
                tabla.addCell(celda);
            }

            double totalProveedor = 0;

            for (Map<String, Object> r : grupo.getValue()) {
                tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
                totalProveedor += (double) r.get("Total");
            }

            doc.add(tabla);
            doc.add(new Paragraph("📦 Total comprado al proveedor: " + String.format("%.2f", totalProveedor), fontBold));
            doc.add(Chunk.NEWLINE);
        }

        doc.add(new Paragraph("📋 Compras Totales", fontBold));
        doc.add(new Paragraph("💳 Total CRÉDITO: " + String.format("%.2f", totalCredito), fontNormal));
        doc.add(new Paragraph("💵 Total CONTADO: " + String.format("%.2f", totalContado), fontNormal));
        doc.add(new Paragraph("🧮 Total GENERAL: " + String.format("%.2f", totalGeneral), fontBold));
        doc.add(Chunk.NEWLINE);

        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Compras por proveedor - " + secuencialProveedor);
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}


   public void RPT_C_Rango_T(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin, double montoMinimo, double montoMaximo) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT c.Secuencial, c.Fecha, p.Nombre, c.Tipo, c.Gran_Total " +
            "FROM Compra c JOIN Proveedor p ON c.Secuencial_Proveedor = p.Secuencial " +
            "WHERE c.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            double total = (double) r[4];

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin) &&
                total >= montoMinimo &&
                total <= montoMaximo) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Proveedor", r[2]);
                fila.put("Tipo", r[3]);
                fila.put("Total", total);
                registrosFiltrados.add(fila);
            }
        }

        registrosFiltrados.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));
        double totalFiltrado = registrosFiltrados.stream().mapToDouble(r -> (double) r.get("Total")).sum();

        double totalContado = registrosFiltrados.stream()
            .filter(r -> "Contado".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalCredito = registrosFiltrados.stream()
            .filter(r -> "Credito".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("📦 Compras por Rango de Monto", fontHeader));
        doc.add(new Paragraph("Monto entre: " + String.format("%.2f", montoMinimo) + " y " + String.format("%.2f", montoMaximo), fontNormal));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        String[] columnas = { "Compra", "Fecha", "Proveedor", "Tipo", "Total" };
        for (String col : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
            celda.setBackgroundColor(Color.LIGHT_GRAY);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5f);
            tabla.addCell(celda);
        }

        for (Map<String, Object> r : registrosFiltrados) {
            tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Proveedor").toString(), fontNormal));
            tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
        }

        doc.add(tabla);
        doc.add(new Paragraph("🧮 TOTAL DEL RANGO: " + String.format("%.2f", totalFiltrado), fontBold));
        doc.add(new Paragraph("💵 Total CONTADO: " + String.format("%.2f", totalContado), fontNormal));
        doc.add(new Paragraph("💳 Total CRÉDITO: " + String.format("%.2f", totalCredito), fontNormal));
        doc.add(Chunk.NEWLINE);
        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Compras por rango de monto");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

   
   public void RPT_V_Rango_T(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin, double montoMinimo, double montoMaximo) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT v.Secuencial, v.Fecha, c.Nombre, v.Tipo, v.Gran_Total " +
            "FROM Venta v JOIN Cliente c ON v.Secuencial_Cliente = c.Secuencial " +
            "WHERE v.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            double total = (double) r[4];

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin) &&
                total >= montoMinimo &&
                total <= montoMaximo) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Cliente", r[2]);
                fila.put("Tipo", r[3]);
                fila.put("Total", total);
                registrosFiltrados.add(fila);
            }
        }

        registrosFiltrados.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));
        double totalFiltrado = registrosFiltrados.stream().mapToDouble(r -> (double) r.get("Total")).sum();

        double totalContado = registrosFiltrados.stream()
            .filter(r -> "Contado".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalCredito = registrosFiltrados.stream()
            .filter(r -> "Credito".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("🧾 Ventas por Rango de Monto", fontHeader));
        doc.add(new Paragraph("Monto entre: " + String.format("%.2f", montoMinimo) + " y " + String.format("%.2f", montoMaximo), fontNormal));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        String[] columnas = { "Factura", "Fecha", "Cliente", "Tipo", "Total" };
        for (String col : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
            celda.setBackgroundColor(Color.LIGHT_GRAY);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5f);
            tabla.addCell(celda);
        }

        for (Map<String, Object> r : registrosFiltrados) {
            tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Cliente").toString(), fontNormal));
            tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
        }

        doc.add(tabla);
        doc.add(new Paragraph("🧮 TOTAL DEL RANGO: " + String.format("%.2f", totalFiltrado), fontBold));
        doc.add(new Paragraph("💵 Total CONTADO: " + String.format("%.2f", totalContado), fontNormal));
        doc.add(new Paragraph("💳 Total CRÉDITO: " + String.format("%.2f", totalCredito), fontNormal));
        doc.add(Chunk.NEWLINE);
        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Ventas por rango de monto");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

   
 public void RPT_P_Vendidos_Usuario(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT d.Codigo, d.Descripcion, d.Cantidad, d.Precio, v.Fecha, u.Nombre " +
            "FROM Venta_Detalle d " +
            "JOIN Venta v ON d.Secuencial_Factura = v.Secuencial " +
            "JOIN Usuario u ON d.Secuencial_Usuario = u.Secuencial " +
            "WHERE v.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[4]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                double cantidad = (r[2] instanceof Double) ? (double) r[2] : ((Number) r[2]).doubleValue();
                double precio = (r[3] instanceof Double) ? (double) r[3] : ((Number) r[3]).doubleValue();

                Map<String, Object> fila = new HashMap<>();
                fila.put("Usuario", r[5]);
                fila.put("Codigo", r[0]);
                fila.put("Descripcion", r[1]);
                fila.put("Cantidad", cantidad);
                fila.put("Precio", precio);
                fila.put("Fecha", fechaConvertida);
                registrosFiltrados.add(fila);
            }
        }

        Map<String, List<Map<String, Object>>> agrupadoPorUsuario = registrosFiltrados.stream()
            .collect(Collectors.groupingBy(r -> r.get("Usuario").toString(), LinkedHashMap::new, Collectors.toList()));

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("🧾 Productos Vendidos por Usuario", fontHeader));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupadoPorUsuario.entrySet()) {
            doc.add(new Paragraph("👤 Usuario: " + grupo.getKey(), fontBold));
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] columnas = { "Código", "Producto", "Cantidad", "Fecha", "Subtotal" };
            for (String col : columnas) {
                PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(5f);
                tabla.addCell(celda);
            }

            double totalUsuario = 0;

            for (Map<String, Object> r : grupo.getValue()) {
                double cantidad = (double) r.get("Cantidad");
                double precio = (double) r.get("Precio");
                double subtotal = cantidad * precio;

                tabla.addCell(new Phrase(r.get("Codigo").toString(), fontNormal));
                tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%.2f", cantidad), fontNormal));
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(String.format("%.2f", subtotal), fontNormal));

                totalUsuario += subtotal;
            }

            PdfPCell resumenLabel = new PdfPCell(new Phrase("🧮 Total vendido por usuario:", fontBold));
            resumenLabel.setColspan(4);
            resumenLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            resumenLabel.setPadding(6f);
            tabla.addCell(resumenLabel);

            PdfPCell resumenValor = new PdfPCell(new Phrase(String.format("%.2f", totalUsuario), fontBold));
            resumenValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
            resumenValor.setPadding(6f);
            tabla.addCell(resumenValor);

            doc.add(tabla);
        }

        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Productos vendidos por usuario");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

  
 public void RPT_P_Comprados_Usuario(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT d.Codigo, d.Descripcion, d.Cantidad, d.Precio, c.Fecha, u.Nombre " +
            "FROM Compra_Detalle d " +
            "JOIN Compra c ON d.Secuencial_Factura = c.Secuencial " +
            "JOIN Usuario u ON d.Secuencial_Usuario = u.Secuencial " +
            "WHERE c.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[4]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                double cantidad = (r[2] instanceof Double) ? (double) r[2] : ((Number) r[2]).doubleValue();
                double precioCosto = (r[3] instanceof Double) ? (double) r[3] : ((Number) r[3]).doubleValue();

                Map<String, Object> fila = new HashMap<>();
                fila.put("Usuario", r[5]);
                fila.put("Codigo", r[0]);
                fila.put("Descripcion", r[1]);
                fila.put("Cantidad", cantidad);
                fila.put("Precio_Costo", precioCosto);
                fila.put("Fecha", fechaConvertida);
                registrosFiltrados.add(fila);
            }
        }

        Map<String, List<Map<String, Object>>> agrupadoPorUsuario = registrosFiltrados.stream()
            .collect(Collectors.groupingBy(r -> r.get("Usuario").toString(), LinkedHashMap::new, Collectors.toList()));

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("📦 Productos Comprados por Usuario", fontHeader));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        for (Map.Entry<String, List<Map<String, Object>>> grupo : agrupadoPorUsuario.entrySet()) {
            doc.add(new Paragraph("👤 Usuario: " + grupo.getKey(), fontBold));
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);

            String[] columnas = { "Código", "Producto", "Cantidad", "Fecha", "Subtotal" };
            for (String col : columnas) {
                PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
                celda.setBackgroundColor(Color.LIGHT_GRAY);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setPadding(5f);
                tabla.addCell(celda);
            }

            double totalUsuario = 0;

            for (Map<String, Object> r : grupo.getValue()) {
                double cantidad = (double) r.get("Cantidad");
                double precioCosto = (double) r.get("Precio_Costo");
                double subtotal = cantidad * precioCosto;

                tabla.addCell(new Phrase(r.get("Codigo").toString(), fontNormal));
                tabla.addCell(new Phrase(r.get("Descripcion").toString(), fontNormal));
                tabla.addCell(new Phrase(String.format("%.2f", cantidad), fontNormal));
                tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
                tabla.addCell(new Phrase(String.format("%.2f", subtotal), fontNormal));

                totalUsuario += subtotal;
            }

            PdfPCell resumenLabel = new PdfPCell(new Phrase("🧮 Total comprado por usuario:", fontBold));
            resumenLabel.setColspan(4);
            resumenLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
            resumenLabel.setPadding(6f);
            tabla.addCell(resumenLabel);

            PdfPCell resumenValor = new PdfPCell(new Phrase(String.format("%.2f", totalUsuario), fontBold));
            resumenValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
            resumenValor.setPadding(6f);
            tabla.addCell(resumenValor);

            doc.add(tabla);
        }

        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Productos comprados por usuario");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

  
 
 public void RPT_Ventas_General(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT v.Secuencial, v.Fecha, c.Nombre, v.Tipo, v.Gran_Total " +
            "FROM Venta v JOIN Cliente c ON v.Secuencial_Cliente = c.Secuencial " +
            "WHERE v.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Cliente", r[2]);
                fila.put("Tipo", r[3]);
                fila.put("Total", r[4]);
                registrosFiltrados.add(fila);
            }
        }

        registrosFiltrados.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        double totalContado = registrosFiltrados.stream()
            .filter(r -> "Contado".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalCredito = registrosFiltrados.stream()
            .filter(r -> "Credito".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalGeneral = registrosFiltrados.stream()
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("🧾 Reporte General de Ventas", fontHeader));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        String[] columnas = { "Factura", "Fecha", "Cliente", "Tipo", "Total" };
        for (String col : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
            celda.setBackgroundColor(Color.LIGHT_GRAY);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5f);
            tabla.addCell(celda);
        }

        for (Map<String, Object> r : registrosFiltrados) {
            tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Cliente").toString(), fontNormal));
            tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
        }

        doc.add(tabla);
        doc.add(Chunk.NEWLINE);

        doc.add(new Paragraph("📋 Totales por Tipo de Venta", fontBold));
        doc.add(new Paragraph("💳 Total CRÉDITO: " + String.format("%.2f", totalCredito), fontNormal));
        doc.add(new Paragraph("💵 Total CONTADO: " + String.format("%.2f", totalContado), fontNormal));
        doc.add(new Paragraph("🧮 TOTAL GENERAL: " + String.format("%.2f", totalGeneral), fontBold));
        doc.add(Chunk.NEWLINE);

        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte general de ventas");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

 public void RPT_Compras_General(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        List<Object[]> registros = em.createQuery(
            "SELECT c.Secuencial, c.Fecha, p.Nombre, c.Tipo, c.Gran_Total " +
            "FROM Compra c JOIN Proveedor p ON c.Secuencial_Proveedor = p.Secuencial " +
            "WHERE c.Secuencial_Empresa = :empresa", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", new Locale("es", "ES"));
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Map<String, Object>> registrosFiltrados = new ArrayList<>();

        for (Object[] r : registros) {
            String fechaTexto = ((String) r[1]).trim()
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM");

            LocalDateTime fechaConvertida = null;
            try {
                fechaConvertida = LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException ignored) {}

            if (fechaConvertida != null &&
                !fechaConvertida.isBefore(inicio) &&
                !fechaConvertida.isAfter(fin)) {

                Map<String, Object> fila = new HashMap<>();
                fila.put("Secuencial", r[0]);
                fila.put("Fecha", fechaConvertida);
                fila.put("Proveedor", r[2]);
                fila.put("Tipo", r[3]);
                fila.put("Total", r[4]);
                registrosFiltrados.add(fila);
            }
        }

        registrosFiltrados.sort(Comparator.comparing(r -> (LocalDateTime) r.get("Fecha")));

        double totalContado = registrosFiltrados.stream()
            .filter(r -> "Contado".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalCredito = registrosFiltrados.stream()
            .filter(r -> "Credito".equalsIgnoreCase(r.get("Tipo").toString()))
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        double totalGeneral = registrosFiltrados.stream()
            .mapToDouble(r -> (double) r.get("Total"))
            .sum();

        // PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        doc.add(new Paragraph("📦 Reporte General de Compras", fontHeader));
        doc.add(new Paragraph("Periodo: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                              " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
        doc.add(new Paragraph("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
        doc.add(Chunk.NEWLINE);

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);

        String[] columnas = { "Compra", "Fecha", "Proveedor", "Tipo", "Total" };
        for (String col : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(col, fontBold));
            celda.setBackgroundColor(Color.LIGHT_GRAY);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setPadding(5f);
            tabla.addCell(celda);
        }

        for (Map<String, Object> r : registrosFiltrados) {
            tabla.addCell(new Phrase(r.get("Secuencial").toString(), fontNormal));
            tabla.addCell(new Phrase(((LocalDateTime) r.get("Fecha")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            tabla.addCell(new Phrase(r.get("Proveedor").toString(), fontNormal));
            tabla.addCell(new Phrase(r.get("Tipo").toString(), fontNormal));
            tabla.addCell(new Phrase(String.format("%.2f", r.get("Total")), fontNormal));
        }

        doc.add(tabla);
        doc.add(Chunk.NEWLINE);

        doc.add(new Paragraph("📋 Totales por Tipo de Compra", fontBold));
        doc.add(new Paragraph("💳 Total CRÉDITO: " + String.format("%.2f", totalCredito), fontNormal));
        doc.add(new Paragraph("💵 Total CONTADO: " + String.format("%.2f", totalContado), fontNormal));
        doc.add(new Paragraph("🧮 TOTAL GENERAL: " + String.format("%.2f", totalGeneral), fontBold));
        doc.add(Chunk.NEWLINE);

        doc.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        doc.close();

        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte general de compras");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

 
    //************************
    
    
    
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
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Desde:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Hasta:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(151, 151, 151)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 168, 107));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Ventas");

        jPanel6.setBackground(new java.awt.Color(35, 32, 45));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ventas por Tipo:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contado", "Credito" }));

        jButton3.setText("Generar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(35, 32, 45));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ventas por Cliente:");

        jButton5.setText("Generar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(35, 32, 45));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ventas por Rango de Montos:");

        jLabel9.setBackground(new java.awt.Color(35, 32, 45));
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("De:");

        jLabel10.setBackground(new java.awt.Color(35, 32, 45));
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("A:");

        jButton1.setText("Generar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(35, 32, 45));

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Unidades Vendidas por Usuario");

        jButton7.setText("Generar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jButton7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(35, 32, 45));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Todas las Ventas Registradas");

        jButton9.setText("Generar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jButton9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Todos los reportes requieren un rango de fechas.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Compras");

        jPanel7.setBackground(new java.awt.Color(35, 32, 45));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Compras por Tipo:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Contado", "Credito" }));

        jButton4.setText("Generar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(35, 32, 45));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Compras por Proveedor:");

        jButton6.setText("Generar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(35, 32, 45));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Compras por Rango de Montos:");

        jLabel12.setBackground(new java.awt.Color(35, 32, 45));
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("De:");

        jLabel15.setBackground(new java.awt.Color(35, 32, 45));
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("A:");

        jButton2.setText("Generar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(35, 32, 45));

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Unidades Compradas por Usuario");

        jButton8.setText("Generar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jButton8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(35, 32, 45));

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Todas las Compras Registradas");

        jButton10.setText("Generar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jButton10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

         try {
        int empresa = Secuencial_Empresa;

        String tipoVenta = jComboBox2.getSelectedItem().toString().trim();
         
        LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();

        RPT_V_Tipo(empresa, tipoVenta, fechaInicio, fechaFin);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al preparar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        
         
           try {
        int empresa = Secuencial_Empresa;

        String seleccionado=jComboBox4.getSelectedItem().toString();
         int secuencialCliente = Integer.parseInt(seleccionado.split("-")[0].trim());
      
         
        LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();

        RPT_V_Cliente(empresa, secuencialCliente, fechaInicio, fechaFin);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al preparar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    }
         
         
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

          
        LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();
         

         double minimo=Double.parseDouble(jTextField1.getText());
         double maximo=Double.parseDouble(jTextField2.getText());
    
        RPT_V_Rango_T(Secuencial_Empresa,fechaInicio,fechaFin,minimo,maximo);
        
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();
        
        RPT_P_Vendidos_Usuario(Secuencial_Empresa,fechaInicio,fechaFin);
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

         LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();
        
         RPT_Ventas_General(Secuencial_Empresa,fechaInicio,fechaFin);
         
         

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed


          try {
        int empresa = Secuencial_Empresa;

        String tipoVenta = jComboBox3.getSelectedItem().toString().trim();
         
        LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();

        RPT_C_Tipo(empresa, tipoVenta, fechaInicio, fechaFin);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al preparar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    }
        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed


         
           try {
        int empresa = Secuencial_Empresa;

        String seleccionado=jComboBox5.getSelectedItem().toString();
         int secuencialProveedor = Integer.parseInt(seleccionado.split("-")[0].trim());
      
         
        LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();

        RPT_C_Proveedor(empresa, secuencialProveedor, fechaInicio, fechaFin);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al preparar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    }
         
         



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed


           
        LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();
         

         double minimo=Double.parseDouble(jTextField3.getText());
         double maximo=Double.parseDouble(jTextField4.getText());
    
        RPT_C_Rango_T(Secuencial_Empresa,fechaInicio,fechaFin,minimo,maximo);
        
        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed


          LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();
        
        RPT_P_Comprados_Usuario(Secuencial_Empresa,fechaInicio,fechaFin);
        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed


         LocalDate fechaInicio = datePicker1.getDate();
         LocalDate fechaFin = datePicker2.getDate();
        
         RPT_Compras_General(Secuencial_Empresa,fechaInicio,fechaFin);
         

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed


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
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
