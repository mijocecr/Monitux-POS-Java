/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Ventanas;

/**
 *
 * @author Miguel Cerrato
 */



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.printing.PDFPageable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.awt.print.PrinterJob;




import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.printing.PDFPageable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.awt.print.PrinterJob;

public class V_Visor_Factura extends JFrame {

    private byte[] documentoEnBytes;
    private String titulo = "";
    private PDDocument document;
    private PDFRenderer renderer;
    private int paginaActual = 0;
    private double zoom = 1.0;
    private JLabel imagenLabel;
    private JLabel paginaInfo;
    private JScrollPane scrollPane;

    public void setDocumentoEnBytes(byte[] bytes) {
        this.documentoEnBytes = bytes;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public V_Visor_Factura() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    public void mostrar() {
        setTitle(titulo);

        try {
            if (documentoEnBytes == null || documentoEnBytes.length == 0) {
                JOptionPane.showMessageDialog(this, "El documento PDF está vacío o no se generó correctamente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            document = PDDocument.load(new ByteArrayInputStream(documentoEnBytes));
            renderer = new PDFRenderer(document);

            imagenLabel = new JLabel();
            scrollPane = new JScrollPane(imagenLabel);

            JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton anterior = new JButton("←");
            JButton siguiente = new JButton("→");
            JButton zoomIn = new JButton("Zoom +");
            JButton zoomOut = new JButton("Zoom -");
            JButton imprimir = new JButton("Imprimir");
            JButton guardar = new JButton("Guardar PDF");
            paginaInfo = new JLabel();

            anterior.addActionListener(e -> cambiarPagina(-1));
            siguiente.addActionListener(e -> cambiarPagina(1));
            zoomIn.addActionListener(e -> ajustarZoom(1.2));
            zoomOut.addActionListener(e -> ajustarZoom(0.8));
            imprimir.addActionListener(e -> imprimirDocumento());
            guardar.addActionListener(e -> guardarPDF());

            controles.add(anterior);
            controles.add(paginaInfo);
            controles.add(siguiente);
            controles.add(zoomOut);
            controles.add(zoomIn);
            controles.add(imprimir);
            controles.add(guardar);

            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            getContentPane().add(controles, BorderLayout.SOUTH);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    renderizarPagina();
                }
            });

            renderizarPagina();
            setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar el PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void renderizarPagina() {
        try {
            BufferedImage imagen = renderer.renderImageWithDPI(paginaActual, (int)(150 * zoom));
            imagenLabel.setIcon(new ImageIcon(imagen));
            actualizarPaginaInfo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al renderizar la página: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void cambiarPagina(int delta) {
        int totalPaginas = document.getNumberOfPages();
        int nuevaPagina = paginaActual + delta;
        if (nuevaPagina >= 0 && nuevaPagina < totalPaginas) {
            paginaActual = nuevaPagina;
            renderizarPagina();
        }
    }

    private void ajustarZoom(double factor) {
        zoom *= factor;
        renderizarPagina();
    }

    private void actualizarPaginaInfo() {
        paginaInfo.setText("Página " + (paginaActual + 1) + " / " + document.getNumberOfPages());
    }

    private void imprimirDocumento() {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            if (job.printDialog()) {
                job.print();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al imprimir el documento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void guardarPDF() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Guardar como...");
            chooser.setSelectedFile(new File("Factura.pdf"));
            int opcion = chooser.showSaveDialog(this);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                File destino = chooser.getSelectedFile();
                Files.write(destino.toPath(), documentoEnBytes);
                JOptionPane.showMessageDialog(this, "PDF guardado correctamente.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
