/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import com.lowagie.text.Document;
import com.monituxpos.Ventanas.V_Menu_Principal;
import java.awt.Color;
import java.util.List;


public class FacturaCompletaPDF_Venta {

    public int Secuencial;
    public String Cliente;
    public String TipoVenta;
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
        empresaCell.addElement(new Paragraph(V_Menu_Principal.getTelefono_Empresa()+" | "+V_Menu_Principal.getEmail(), normalFont));
        headerTable.addCell(empresaCell);

        PdfPCell facturaCell = new PdfPCell();
        facturaCell.setBorder(Rectangle.NO_BORDER);
        facturaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        facturaCell.addElement(new Paragraph("Factura N°: " + Secuencial, normalFont));
        facturaCell.addElement(new Paragraph("Fecha: " + Fecha, normalFont));
        headerTable.addCell(facturaCell);

        document.add(headerTable);
        document.add(new LineSeparator());

        // DATOS CLIENTE
        Paragraph clienteInfo = new Paragraph();
        clienteInfo.setSpacingBefore(10f);
        clienteInfo.setSpacingAfter(10f);
        clienteInfo.add(new Phrase("Cliente: " + Cliente + "\n", normalFont));
        clienteInfo.add(new Phrase("Tipo de venta: " + TipoVenta + "\n", normalFont));
        clienteInfo.add(new Phrase("Método de pago: " + MetodoPago + "\n", normalFont));
        document.add(clienteInfo);

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
        Paragraph footer = new Paragraph("Gracias por su compra", new Font(Font.HELVETICA, 10));
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

    
    
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public String getTipoVenta() {
        return TipoVenta;
    }

    public void setTipoVenta(String TipoVenta) {
        this.TipoVenta = TipoVenta;
    }

    public String getMetodoPago() {
        return MetodoPago;
    }

    public void setMetodoPago(String MetodoPago) {
        this.MetodoPago = MetodoPago;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public List<Item_Factura> getItems() {
        return Items;
    }

    public void setItems(List<Item_Factura> Items) {
        this.Items = Items;
    }

    public double getOtrosCargos() {
        return OtrosCargos;
    }

    public void setOtrosCargos(double OtrosCargos) {
        this.OtrosCargos = OtrosCargos;
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

    public void setDescuento(double Descuento) {
        this.Descuento = Descuento;
    }
    
    
    
}
