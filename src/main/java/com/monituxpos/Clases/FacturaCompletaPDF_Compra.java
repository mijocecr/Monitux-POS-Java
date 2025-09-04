/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;
import java.text.DecimalFormat;
import java.util.List;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.monituxpos.Ventanas.V_Menu_Principal;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 *
 * @author Miguel Cerrato
 */
public class FacturaCompletaPDF_Compra {

    public int Secuencial;
    public String Proveedor;
    public String TipoCompra;
    public String MetodoPago;
    public String Fecha;
    public List<Item_Factura> Items;

    public double OtrosCargos;
    public double ISV;
    public double Descuento;

    public byte[] GeneratePdfToBytes() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, stream);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font boldFont = new Font(Font.HELVETICA, 11, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11);

            DecimalFormat df = new DecimalFormat("#,##0.00");

            // ENCABEZADO
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new int[]{3, 1});
            headerTable.setSpacingAfter(10f);

            PdfPCell empresaCell = new PdfPCell();
            empresaCell.setBorder(Rectangle.NO_BORDER);
            empresaCell.addElement(new Paragraph(V_Menu_Principal.getNombre_Empresa(), titleFont));
            empresaCell.addElement(new Paragraph(V_Menu_Principal.getDireccion_Empresa(), normalFont));
            empresaCell.addElement(new Paragraph(V_Menu_Principal.getTelefono_Empresa() + " | " + V_Menu_Principal.getEmail(), normalFont));
            headerTable.addCell(empresaCell);

            PdfPCell facturaCell = new PdfPCell();
            facturaCell.setBorder(Rectangle.NO_BORDER);
            facturaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            facturaCell.addElement(new Paragraph("Factura N°: " + Secuencial, normalFont));
            facturaCell.addElement(new Paragraph("Fecha: " + Fecha, normalFont));
            headerTable.addCell(facturaCell);

            document.add(headerTable);
            document.add(new LineSeparator());

            // DATOS PROVEEDOR
            Paragraph proveedorInfo = new Paragraph();
            proveedorInfo.setSpacingBefore(10f);
            proveedorInfo.setSpacingAfter(10f);
            proveedorInfo.add(new Phrase("Proveedor: " + Proveedor + "\n", normalFont));
            proveedorInfo.add(new Phrase("Tipo de compra: " + TipoCompra + "\n", normalFont));
            proveedorInfo.add(new Phrase("Método de pago: " + MetodoPago + "\n", normalFont));
            document.add(proveedorInfo);

            document.add(new LineSeparator());

            // TABLA DE ITEMS
            PdfPTable itemTable = new PdfPTable(5);
            itemTable.setWidthPercentage(100);
            itemTable.setWidths(new int[]{1, 3, 1, 1, 1});
            itemTable.setSpacingBefore(10f);
            itemTable.setSpacingAfter(10f);

            String[] headers = {"Código", "Descripción", "Cantidad", "Precio", "Total"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                itemTable.addCell(cell);
            }

            double subtotal = 0.0;
            for (Item_Factura item : Items) {
                itemTable.addCell(new PdfPCell(new Phrase(item.getCodigo(), normalFont)));
                itemTable.addCell(new PdfPCell(new Phrase(item.getDescripcion(), normalFont)));

                PdfPCell cantidadCell = new PdfPCell(new Phrase(String.valueOf(item.getCantidad()), normalFont));
                cantidadCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                itemTable.addCell(cantidadCell);

                PdfPCell precioCell = new PdfPCell(new Phrase(df.format(item.getPrecio()), normalFont));
                precioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                itemTable.addCell(precioCell);

                PdfPCell totalCell = new PdfPCell(new Phrase(df.format(item.getTotal()), normalFont));
                totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                itemTable.addCell(totalCell);

                subtotal += item.getTotal();
            }

            document.add(itemTable);

            // TOTALES
            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(40);
            totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalTable.setSpacingBefore(10f);

            String[][] totalRows = {
                {"Subtotal", df.format(subtotal)},
                {"ISV", df.format(ISV)},
                {"Otros cargos", df.format(OtrosCargos)},
                {"Descuento", "-" + df.format(Descuento)},
                {"Total", df.format(subtotal + ISV + OtrosCargos - Descuento)}
            };

            for (String[] row : totalRows) {
                PdfPCell label = new PdfPCell(new Phrase(row[0], boldFont));
                label.setBorder(Rectangle.NO_BORDER);
                PdfPCell value = new PdfPCell(new Phrase(row[1], boldFont));
                value.setBorder(Rectangle.NO_BORDER);
                value.setHorizontalAlignment(Element.ALIGN_RIGHT);
                totalTable.addCell(label);
                totalTable.addCell(value);
            }

            document.add(totalTable);

            // PIE DE PÁGINA
            Paragraph footer = new Paragraph("Gracias por su atención", new Font(Font.HELVETICA, 10));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20f);
            document.add(footer);

            document.close();
            return stream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters y Setters

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.Secuencial = secuencial;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        this.Proveedor = proveedor;
    }

    public String getTipoCompra() {
        return TipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.TipoCompra = tipoCompra;
    }

    public String getMetodoPago() {
        return MetodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.MetodoPago = metodoPago;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public List<Item_Factura> getItems() {
        return Items;
    }

    public void setItems(List<Item_Factura> items) {
        this.Items = items;
    }

    public double getOtrosCargos() {
        return OtrosCargos;
    }

    public void setOtrosCargos(double otrosCargos) {
        this.OtrosCargos = otrosCargos;
    }

    public double getISV() {
        return ISV;
    }

    public void setISV(double ISV) {
        this.ISV = ISV;
    }

    public double getDescuento() {
        return Descuento;
    }

    public void setDescuento(double descuento) {
        this.Descuento = descuento;
    }
}
