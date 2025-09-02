/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Ventanas;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class VisorPDFBox extends JFrame {

    public VisorPDFBox(byte[] pdfBytes, String titulo) {
        setTitle(titulo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            if (pdfBytes == null || pdfBytes.length == 0) {
                throw new IllegalArgumentException("El PDF está vacío o no fue generado.");
            }

            // Diagnóstico: guardar temporalmente para inspección
            File tempPdf = File.createTempFile("debug_factura_", ".pdf");
            tempPdf.deleteOnExit();
            Files.write(tempPdf.toPath(), pdfBytes);
            System.out.println("PDF guardado en: " + tempPdf.getAbsolutePath());

            // Cargar desde archivo (más confiable que ByteArrayInputStream)
            PDDocument document = PDDocument.load(tempPdf);
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 150);

            JLabel label = new JLabel(new ImageIcon(image));
            JScrollPane scrollPane = new JScrollPane(label);
            getContentPane().add(scrollPane, BorderLayout.CENTER);

            document.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        setVisible(true);
    }
}
