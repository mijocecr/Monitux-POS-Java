/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

public class Miniatura_Producto extends JPanel {

    public JCheckBox checkBoxSeleccionado;
    public Producto producto;
    public JLabel imagenLabel;
    public JLabel codigoLabel;
    public JLabel marcaLabel;
    public JLabel precioLabel;
    public JPanel infoPanel;
    public ImageIcon imagenRedimensionada;
    public double cantidadSelecccion;
    public int cantidadSelecccionItem;

    public JCheckBox getCheckBoxSeleccionado() {
        return checkBoxSeleccionado;
    }

    public void setCheckBoxSeleccionado(JCheckBox checkBoxSeleccionado) {
        this.checkBoxSeleccionado = checkBoxSeleccionado;
    }

    public JLabel getImagenLabel() {
        return imagenLabel;
    }

    public void setImagenLabel(JLabel imagenLabel) {
        this.imagenLabel = imagenLabel;
    }

    public JLabel getCodigoLabel() {
        return codigoLabel;
    }

    public void setCodigoLabel(JLabel codigoLabel) {
        this.codigoLabel = codigoLabel;
    }

    public JLabel getMarcaLabel() {
        return marcaLabel;
    }

    public void setMarcaLabel(JLabel marcaLabel) {
        this.marcaLabel = marcaLabel;
    }

    public JLabel getPrecioLabel() {
        return precioLabel;
    }

    public void setPrecioLabel(JLabel precioLabel) {
        this.precioLabel = precioLabel;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(JPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public ImageIcon getImagenRedimensionada() {
        return imagenRedimensionada;
    }

    public void setImagenRedimensionada(ImageIcon imagenRedimensionada) {
        this.imagenRedimensionada = imagenRedimensionada;
    }

    public double getCantidadSelecccion() {
        return cantidadSelecccion;
    }

    public void setCantidadSelecccion(double cantidadSelecccion) {
        this.cantidadSelecccion = cantidadSelecccion;
    }

    public Miniatura_Producto(Producto producto) {
        this.producto = producto;
        setLayout(new BorderLayout());

        int ancho = 120;
        int alto = 110;

        try {
            byte[] datosImagen = producto.getImagen();
            if (datosImagen != null && datosImagen.length > 0) {
                ByteArrayInputStream bis = new ByteArrayInputStream(datosImagen);
                BufferedImage bufferedImage = ImageIO.read(bis);
                Image imagenEscalada = bufferedImage.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                imagenRedimensionada = new ImageIcon(imagenEscalada);
            } else {
                imagenRedimensionada = new ImageIcon("C:\\Users\\Miguel Cerrato\\Documents\\NetBeansProjects\\Experimento\\src\\experimento\\test.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
            imagenRedimensionada = new ImageIcon(); // fallback
        }

        imagenLabel = new JLabel(imagenRedimensionada);
        
        codigoLabel = new JLabel(producto.getCodigo());
        marcaLabel = new JLabel("Marca: " + producto.getMarca());
        precioLabel = new JLabel("Precio: " + producto.getPrecio_Venta());
       // checkBoxSeleccionado = new JCheckBox("En lista");

        infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.add(codigoLabel);
        infoPanel.add(marcaLabel);
        infoPanel.add(precioLabel);
       // infoPanel.add(checkBoxSeleccionado);

        add(imagenLabel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        infoPanel.setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        actualizarColor(producto.getCantidad());
    }

  
    public void actualizarColor(double cantidadActual) {
    // Si el producto es de tipo "Servicio", fondo blanco
    if ("Servicio".equalsIgnoreCase(producto.getTipo())) {
        setBackground(Color.WHITE);
        repaint();
        return;
    }

    // Si no es servicio, aplicar lógica de existencia mínima
    double existenciaMinima = producto.getExistencia_Minima();

    if (cantidadActual < existenciaMinima) {
        setBackground(new Color(255, 102, 102)); // rojo suave
        infoPanel.setBackground(new Color(255, 102, 102));
    } else if (cantidadActual > existenciaMinima) {
        setBackground(new Color(144, 238, 144)); // verde claro
    
        infoPanel.setBackground(new Color(144, 238, 144));
        
    } else {
        setBackground(new Color(255, 255, 153)); // amarillo pastel
         infoPanel.setBackground(new Color(255, 255, 153));
    }

    repaint();
}

    

    public Producto getProducto() {
        return producto;
    }
}
