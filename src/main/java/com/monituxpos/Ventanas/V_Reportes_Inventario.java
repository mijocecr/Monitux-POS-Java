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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Cerrato
 */
public class V_Reportes_Inventario extends javax.swing.JPanel {

    public int Secuencial_Empresa=V_Menu_Principal.getSecuencial_Empresa();
    
    /**
     * Creates new form V_Reportes_Inventario
     */
    public V_Reportes_Inventario() {
        initComponents();
        
        Util.llenarComboProveedor(jComboBox1, Secuencial_Empresa);
        Util.llenar_Combo_Categoria(jComboBox2, Secuencial_Empresa);
    }

    
    
    
    
  public void RPT_Productos_Registrados(int secuencialEmpresa) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Obtener productos con joins
        List<Object[]> productos = em.createQuery(
            "SELECT p.Codigo, p.Descripcion, p.Marca, p.Precio_Venta, p.Precio_Costo, p.Cantidad, " +
            "p.Existencia_Minima, c.Nombre, pr.Nombre " +
            "FROM Producto p " +
            "JOIN Proveedor pr ON p.Secuencial_Proveedor = pr.Secuencial " +
            "JOIN Categoria c ON p.Secuencial_Categoria = c.Secuencial " +
            "WHERE p.Secuencial_Empresa = :empresa " +
            "ORDER BY p.Codigo", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        // Generar PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontSmall  = new Font(Font.HELVETICA, 7, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        document.add(new Paragraph("📦 Reporte de Productos Registrados", fontHeader));
        document.add(new Paragraph("Total productos: " + productos.size(), fontNormal));
        document.add(new Paragraph("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        float[] widths = new float[] {
            1.5f, // Código
            3.5f, // Descripción
            2f,   // Marca
            2f,   // Precio Venta
            2f,   // Precio Costo
            1f,   // Cantidad
            1f,   // Stock Mínimo
            2f,   // Categoría
            2f    // Proveedor
        };
        table.setWidths(widths);

        String[] columnas = { "Código", "Descripción", "Marca", "Venta", "Costo", "Cantidad", "Stock Min.", "Categoría", "Proveedor" };
        for (String col : columnas) {
            PdfPCell cell = new PdfPCell(new Phrase(col, fontBold));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5f);
            table.addCell(cell);
        }

        for (Object[] fila : productos) {
            table.addCell(new Phrase(String.valueOf(fila[0]), fontNormal)); // Código
            table.addCell(new Phrase(String.valueOf(fila[1]), fontSmall));  // Descripción (más pequeña)
            table.addCell(new Phrase(String.valueOf(fila[2]), fontNormal)); // Marca
            table.addCell(new Phrase(String.format("%.2f", fila[3]), fontNormal)); // Precio Venta
            table.addCell(new Phrase(String.format("%.2f", fila[4]), fontNormal)); // Precio Costo
            table.addCell(new Phrase(String.valueOf(fila[5]), fontNormal)); // Cantidad
            table.addCell(new Phrase(String.valueOf(fila[6]), fontNormal)); // Stock Minimo
            table.addCell(new Phrase(String.valueOf(fila[7]), fontNormal)); // Categoría
            table.addCell(new Phrase(String.valueOf(fila[8]), fontNormal)); // Proveedor
        }

        document.add(table);
        document.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        // Mostrar visor directamente desde memoria
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de productos registrados");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

  public void RPT_Productos_Por_Marca(int secuencialEmpresa) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Obtener productos con joins
        List<Object[]> productos = em.createQuery(
            "SELECT p.Marca, p.Codigo, p.Descripcion, p.Precio_Venta, p.Precio_Costo, p.Cantidad, " +
            "p.Existencia_Minima, c.Nombre, pr.Nombre " +
            "FROM Producto p " +
            "JOIN Proveedor pr ON p.Secuencial_Proveedor = pr.Secuencial " +
            "JOIN Categoria c ON p.Secuencial_Categoria = c.Secuencial " +
            "WHERE p.Secuencial_Empresa = :empresa " +
            "ORDER BY p.Marca, p.Codigo", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        // Agrupar por marca
        Map<String, List<Object[]>> agrupadoPorMarca = productos.stream()
            .collect(Collectors.groupingBy(p -> (String) p[0], LinkedHashMap::new, Collectors.toList()));

        // Generar PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontSmall  = new Font(Font.HELVETICA, 7, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);
        Font fontMarca  = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(0, 70, 160));

        document.add(new Paragraph("🏷️ Reporte de Productos por Marca", fontHeader));
        document.add(new Paragraph("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), fontNormal));
        document.add(Chunk.NEWLINE);

        for (Map.Entry<String, List<Object[]>> grupo : agrupadoPorMarca.entrySet()) {
            Paragraph tituloMarca = new Paragraph("🔹 Marca: " + grupo.getKey(), fontMarca);
            tituloMarca.setSpacingAfter(2f); // Espacio reducido
            document.add(tituloMarca);

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setSpacingBefore(2f);
            table.setSpacingAfter(8f);
            table.setWidths(new float[] {
                1.5f, // Código
                3.5f, // Descripción
                1.5f, // Venta
                1.5f, // Costo
                1f,   // Cantidad
                1f,   // Stock Min
                2f,   // Categoría
                2f    // Proveedor
            });

            String[] columnas = { "Código", "Descripción", "Venta", "Costo", "Cantidad", "Stock Min.", "Categoría", "Proveedor" };
            for (String col : columnas) {
                PdfPCell cell = new PdfPCell(new Phrase(col, fontBold));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(4f);
                table.addCell(cell);
            }

            for (Object[] fila : grupo.getValue()) {
                table.addCell(new Phrase(String.valueOf(fila[1]), fontNormal)); // Código
                table.addCell(new Phrase(String.valueOf(fila[2]), fontSmall));  // Descripción
                table.addCell(new Phrase(String.format("%.2f", fila[3]), fontNormal)); // Venta
                table.addCell(new Phrase(String.format("%.2f", fila[4]), fontNormal)); // Costo
                table.addCell(new Phrase(String.valueOf(fila[5]), fontNormal)); // Cantidad
                table.addCell(new Phrase(String.valueOf(fila[6]), fontNormal)); // Stock Min
                table.addCell(new Phrase(String.valueOf(fila[7]), fontNormal)); // Categoría
                table.addCell(new Phrase(String.valueOf(fila[8]), fontNormal)); // Proveedor
            }

            document.add(table);
        }

        document.add(new Paragraph("Sistema Monitux-POS · Reporte agrupado por marca", fontNormal));
        document.close();

        // Mostrar visor directamente desde memoria
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de productos por marca");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}
  
  public void RPT_Productos_Por_Proveedor(int secuencialEmpresa, String secuencialProveedor, String proveedorNombre) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Obtener productos filtrados por proveedor y empresa
        List<Object[]> productos = em.createQuery(
            "SELECT p.Codigo, p.Descripcion, p.Marca, p.Precio_Venta, p.Precio_Costo, p.Cantidad, " +
            "p.Existencia_Minima, c.Nombre " +
            "FROM Producto p " +
            "JOIN Categoria c ON p.Secuencial_Categoria = c.Secuencial " +
            "WHERE p.Secuencial_Empresa = :empresa AND CAST(p.Secuencial_Proveedor AS string) = :proveedor " +
            "ORDER BY p.Codigo", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .setParameter("proveedor", secuencialProveedor.trim())
            .getResultList();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados para el proveedor: " + secuencialProveedor, "Monitux-POS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Generar PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontSmall  = new Font(Font.HELVETICA, 7, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        document.add(new Paragraph("📦 Reporte de Productos por Proveedor", fontHeader));
        document.add(new Paragraph("Proveedor: " + proveedorNombre, fontNormal));
        document.add(new Paragraph("Total productos: " + productos.size(), fontNormal));
        document.add(new Paragraph("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[] {
            1.5f, // Código
            3.5f, // Descripción
            2f,   // Marca
            1.5f, // Precio Venta
            1.5f, // Precio Costo
            1f,   // Cantidad
            1f,   // Stock Minimo
            2f    // Categoría
        });

        String[] columnas = { "Código", "Descripción", "Marca", "Venta", "Costo", "Cantidad", "Stock Min.", "Categoría" };
        for (String col : columnas) {
            PdfPCell cell = new PdfPCell(new Phrase(col, fontBold));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(4f);
            table.addCell(cell);
        }

        for (Object[] fila : productos) {
            table.addCell(new Phrase(String.valueOf(fila[0]), fontNormal)); // Código
            table.addCell(new Phrase(String.valueOf(fila[1]), fontSmall));  // Descripción
            table.addCell(new Phrase(String.valueOf(fila[2]), fontNormal)); // Marca
            table.addCell(new Phrase(String.format("%.2f", fila[3]), fontNormal)); // Precio Venta
            table.addCell(new Phrase(String.format("%.2f", fila[4]), fontNormal)); // Precio Costo
            table.addCell(new Phrase(String.valueOf(fila[5]), fontNormal)); // Cantidad
            table.addCell(new Phrase(String.valueOf(fila[6]), fontNormal)); // Stock Minimo
            table.addCell(new Phrase(String.valueOf(fila[7]), fontNormal)); // Categoría
        }

        document.add(table);
        document.add(new Paragraph("Sistema Monitux-POS · Reporte filtrado por proveedor", fontNormal));
        document.close();

        // Mostrar visor directamente desde memoria
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de productos por proveedor");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}
  
  public void RPT_Productos_Por_Categoria(int secuencialEmpresa, String secuencialCategoria, String categoriaNombre) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Obtener productos filtrados por categoría y empresa
        List<Object[]> productos = em.createQuery(
            "SELECT p.Codigo, p.Descripcion, p.Marca, p.Precio_Venta, p.Precio_Costo, p.Cantidad, " +
            "p.Existencia_Minima, pr.Nombre " +
            "FROM Producto p " +
            "JOIN Proveedor pr ON p.Secuencial_Proveedor = pr.Secuencial " +
            "WHERE p.Secuencial_Empresa = :empresa AND CAST(p.Secuencial_Categoria AS string) = :categoria " +
            "ORDER BY p.Codigo", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .setParameter("categoria", secuencialCategoria.trim())
            .getResultList();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados para la categoría: " + categoriaNombre, "Monitux-POS", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Generar PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontSmall  = new Font(Font.HELVETICA, 7, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        document.add(new Paragraph("📦 Reporte de Productos por Categoría", fontHeader));
        document.add(new Paragraph("Categoría: " + categoriaNombre, fontNormal));
        document.add(new Paragraph("Total productos: " + productos.size(), fontNormal));
        document.add(new Paragraph("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[] {
            1.5f, // Código
            3.5f, // Descripción
            2f,   // Marca
            1.5f, // Precio Venta
            1.5f, // Precio Costo
            1f,   // Cantidad
            1f,   // Stock Mínimo
            2f    // Proveedor
        });

        String[] columnas = { "Código", "Descripción", "Marca", "Venta", "Costo", "Cantidad", "Stock Min.", "Proveedor" };
        for (String col : columnas) {
            PdfPCell cell = new PdfPCell(new Phrase(col, fontBold));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(4f);
            table.addCell(cell);
        }

        for (Object[] fila : productos) {
            table.addCell(new Phrase(String.valueOf(fila[0]), fontNormal)); // Código
            table.addCell(new Phrase(String.valueOf(fila[1]), fontSmall));  // Descripción
            table.addCell(new Phrase(String.valueOf(fila[2]), fontNormal)); // Marca
            table.addCell(new Phrase(String.format("%.2f", fila[3]), fontNormal)); // Precio Venta
            table.addCell(new Phrase(String.format("%.2f", fila[4]), fontNormal)); // Precio Costo
            table.addCell(new Phrase(String.valueOf(fila[5]), fontNormal)); // Cantidad
            table.addCell(new Phrase(String.valueOf(fila[6]), fontNormal)); // Stock Mínimo
            table.addCell(new Phrase(String.valueOf(fila[7]), fontNormal)); // Proveedor
        }

        document.add(table);
        document.add(new Paragraph("Sistema Monitux-POS · Reporte filtrado por categoría", fontNormal));
        document.close();

        // Mostrar visor directamente desde memoria
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de productos por categoría");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

 public void RPT_Productos_Caducan(int secuencialEmpresa) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Obtener productos que caducan
        List<Object[]> productos = em.createQuery(
            "SELECT pr.Nombre, p.Codigo, p.Descripcion, p.Marca, p.Precio_Venta, p.Precio_Costo, p.Cantidad, " +
            "p.Existencia_Minima, p.Fecha_Caducidad, c.Nombre " +
            "FROM Producto p " +
            "JOIN Proveedor pr ON p.Secuencial_Proveedor = pr.Secuencial " +
            "JOIN Categoria c ON p.Secuencial_Categoria = c.Secuencial " +
            "WHERE p.Expira = true AND p.Fecha_Caducidad IS NOT NULL AND p.Secuencial_Empresa = :empresa " +
            "ORDER BY pr.Nombre, p.Fecha_Caducidad", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .getResultList();

        // Agrupar por proveedor
        Map<String, List<Object[]>> agrupadoPorProveedor = productos.stream()
            .collect(Collectors.groupingBy(p -> (String) p[0], LinkedHashMap::new, Collectors.toList()));

        // Generar PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader     = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal     = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontSmall      = new Font(Font.HELVETICA, 7, Font.NORMAL);
        Font fontBold       = new Font(Font.HELVETICA, 9, Font.BOLD);
        Font fontProveedor  = new Font(Font.HELVETICA, 11, Font.BOLD, new Color(0, 70, 160));

        document.add(new Paragraph("🧊 Reporte de Productos que Caducan", fontHeader));
        document.add(new Paragraph("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), fontNormal));
        document.add(Chunk.NEWLINE);

        for (Map.Entry<String, List<Object[]>> grupo : agrupadoPorProveedor.entrySet()) {
            Paragraph tituloProveedor = new Paragraph("🔹 Proveedor: " + grupo.getKey(), fontProveedor);
            tituloProveedor.setSpacingAfter(2f);
            document.add(tituloProveedor);

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            table.setSpacingBefore(2f);
            table.setSpacingAfter(8f);
            table.setWidths(new float[] {
                1.5f, // Código
                3.5f, // Descripción
                2f,   // Marca
                1.5f, // Venta
                1.5f, // Costo
                1f,   // Cantidad
                1f,   // Stock Min.
                1.5f, // Caducidad
                2f    // Categoría
            });

            String[] columnas = { "Código", "Descripción", "Marca", "Venta", "Costo", "Cantidad", "Stock Min.", "Caduca el", "Categoría" };
            for (String col : columnas) {
                PdfPCell cell = new PdfPCell(new Phrase(col, fontBold));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(4f);
                table.addCell(cell);
            }

            for (Object[] fila : grupo.getValue()) {
                table.addCell(new Phrase(String.valueOf(fila[1]), fontNormal)); // Código
                table.addCell(new Phrase(String.valueOf(fila[2]), fontSmall));  // Descripción
                table.addCell(new Phrase(String.valueOf(fila[3]), fontNormal)); // Marca
                table.addCell(new Phrase(String.format("%.2f", fila[4]), fontNormal)); // Venta
                table.addCell(new Phrase(String.format("%.2f", fila[5]), fontNormal)); // Costo
                table.addCell(new Phrase(String.valueOf(fila[6]), fontNormal)); // Cantidad
                table.addCell(new Phrase(String.valueOf(fila[7]), fontNormal)); // Stock Min

                // Parsear fecha de caducidad desde String "dd/MM/yyyy"
                String fechaFormateada;
                try {
                    String fechaRaw = String.valueOf(fila[8]).trim();
                    Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaRaw);
                    fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
                } catch (Exception e) {
                    fechaFormateada = "Fecha inválida";
                }
                table.addCell(new Phrase(fechaFormateada, fontNormal)); // Caducidad

                table.addCell(new Phrase(String.valueOf(fila[9]), fontNormal)); // Categoría
            }

            document.add(table);
        }

        document.add(new Paragraph("Sistema Monitux-POS · Reporte de productos caducables agrupado por proveedor", fontNormal));
        document.close();

        // Mostrar visor directamente desde memoria
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de productos que caducan");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
    }
}

 public void RPT_Productos_Mas_Vendidos(int secuencialEmpresa, LocalDate fechaInicio, LocalDate fechaFin) {
    EntityManager em = null;

    try {
        MonituxDBContext.ensureEntityManagerFactoryReady();
        em = MonituxDBContext.getEntityManager();

        // Formatear fechas como String para comparación lexicográfica
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaInicioStr = fechaInicio.format(formatter);
        String fechaFinStr = fechaFin.format(formatter);

        // Consulta sin funciones específicas, usando BETWEEN sobre texto
        List<Object[]> productos = em.createQuery(
            "SELECT vd.Codigo, vd.Descripcion, SUM(vd.Cantidad), SUM(vd.Total) " +
            "FROM Venta_Detalle vd " +
            "WHERE vd.Secuencial_Empresa = :empresa " +
            "AND vd.Fecha BETWEEN :inicio AND :fin " +
            "GROUP BY vd.Codigo, vd.Descripcion " +
            "ORDER BY SUM(vd.Cantidad) DESC", Object[].class)
            .setParameter("empresa", secuencialEmpresa)
            .setParameter("inicio", fechaInicioStr)
            .setParameter("fin", fechaFinStr)
            .getResultList();

        // Generar PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        document.open();

        Font fontHeader = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font fontNormal = new Font(Font.HELVETICA, 9, Font.NORMAL);
        Font fontSmall  = new Font(Font.HELVETICA, 7, Font.NORMAL);
        Font fontBold   = new Font(Font.HELVETICA, 9, Font.BOLD);

        document.add(new Paragraph("📊 Reporte de productos más vendidos", fontHeader));
        document.add(new Paragraph("Rango: " + fechaInicioStr + " a " + fechaFinStr, fontNormal));
        document.add(new Paragraph("Generado el: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()), fontNormal));
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[] {
            1.5f, // Código
            4f,   // Descripción
            1.5f, // Cantidad
            2f    // Total
        });

        String[] columnas = { "Código", "Descripción", "Cantidad", "Total" };
        for (String col : columnas) {
            PdfPCell cell = new PdfPCell(new Phrase(col, fontBold));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(4f);
            table.addCell(cell);
        }

        for (Object[] fila : productos) {
            table.addCell(new Phrase(String.valueOf(fila[0]), fontNormal)); // Código
            table.addCell(new Phrase(String.valueOf(fila[1]), fontSmall));  // Descripción
            table.addCell(new Phrase(String.format("%.0f", fila[2]), fontNormal)); // Cantidad
            table.addCell(new Phrase(String.format("%.2f", fila[3]), fontNormal)); // Total
        }

        document.add(table);
        document.add(new Paragraph("Sistema Monitux-POS · Reporte generado automáticamente", fontNormal));
        document.close();

        // Mostrar visor directamente desde memoria
        V_Visor_Factura visor = new V_Visor_Factura();
        visor.setDocumentoEnBytes(baos.toByteArray());
        visor.setTitulo("Reporte de productos más vendidos");
        visor.mostrar();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "❌ Error al generar el reporte:\n" + ex.getMessage(), "Monitux-POS", JOptionPane.ERROR_MESSAGE);
    } finally {
        if (em != null && em.isOpen()) em.close();
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker();
        jLabel4 = new javax.swing.JLabel();
        datePicker2 = new com.github.lgooddatepicker.components.DatePicker();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Inventario");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Todos los Productos");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Productos por Marca");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Por Proveedor");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Por Categoria");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Productos Perecederos");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 51));
        jLabel11.setText("Productos Mas Vendidos");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Lista de Precios de Venta");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Lista de Precios de Costo");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Servicios");

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

        jButton5.setText("Generar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 51));
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setText("Generar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Generar");

        jButton8.setText("Generar");

        jButton9.setText("Generar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, 196, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(jButton4)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel12)
                            .addGap(18, 18, 18)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton8))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton9))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(33, 33, 33)
                            .addComponent(jButton5)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButton1))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jButton2))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jButton5))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jButton6))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jButton7))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jButton8))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jButton9))
                .addGap(13, 13, 13))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Kardex");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Todos los Movimientos");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Entradas por Producto");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Salidas por Producto");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 0));
        jLabel18.setText("Todas las Entradas");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 0));
        jLabel19.setText("Todas las Salidas");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Baja Existencia");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Productos Agotados");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Productos Vencidos");

        jButton10.setText("Generar");

        jButton11.setText("Generar");

        jButton12.setText("Generar");

        jButton13.setBackground(new java.awt.Color(255, 255, 0));
        jButton13.setForeground(new java.awt.Color(0, 0, 0));
        jButton13.setText("Generar");

        jButton14.setBackground(new java.awt.Color(255, 255, 0));
        jButton14.setForeground(new java.awt.Color(0, 0, 0));
        jButton14.setText("Generar");

        jButton15.setText("Generar");

        jButton16.setText("Generar");

        jButton17.setText("Generar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12)
                    .addComponent(jButton11))
                .addGap(28, 28, 28))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jButton10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13)
                            .addComponent(jButton14)
                            .addComponent(jButton15)
                            .addComponent(jButton16)
                            .addComponent(jButton17))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField1, jTextField2});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jButton10))
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12))
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jButton13))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jButton14))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jButton15))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jButton16))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jButton17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton11, jTextField2});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton12, jTextField1});

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Desde:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hasta:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(datePicker2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Los reportes con distinción amarilla requieren un rango de fechas.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
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
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     

RPT_Productos_Registrados(Secuencial_Empresa);        

// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        
        RPT_Productos_Por_Marca(Secuencial_Empresa);
        

// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

     

         String seleccionado=jComboBox1.getSelectedItem().toString();
         int secuencialProveedor = Integer.parseInt(seleccionado.split("-")[0].trim());
         
         RPT_Productos_Por_Proveedor(Secuencial_Empresa,String.valueOf(secuencialProveedor),seleccionado);
         
        


        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed


        
        
         String seleccionado=jComboBox2.getSelectedItem().toString();
         int secuencialCategoria = Integer.parseInt(seleccionado.split("-")[0].trim());
         
         RPT_Productos_Por_Categoria(Secuencial_Empresa,String.valueOf(secuencialCategoria),seleccionado);
         
        
        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed


        RPT_Productos_Caducan(Secuencial_Empresa);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed


        LocalDate fechaInicio = datePicker1.getDate();
        LocalDate fechaFin = datePicker2.getDate();

// Validar fechas
if (fechaInicio == null || fechaFin == null) {
    JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
}



// Ejecutar reporte
RPT_Productos_Mas_Vendidos(Secuencial_Empresa, fechaInicio, fechaFin);


        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private com.github.lgooddatepicker.components.DatePicker datePicker2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
