package com.monituxpos.Ventanas;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class V_Captura_Imagen extends JDialog {

    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private JButton btnCapturar;
    private JLabel labelTitulo;
    public static BufferedImage imagenCapturada;
    private int secuencial;
    private String titulo;

    public V_Captura_Imagen(int secuencial, String titulo) {
        this.secuencial = secuencial;
        this.titulo = titulo;

        // Limpiar imagen anterior
        imagenCapturada = null;

        setTitle(titulo);
        setModal(true);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Título
        labelTitulo = new JLabel(titulo, SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(labelTitulo, BorderLayout.NORTH);

        // Inicializar cámara
        webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setFillArea(true);
            add(webcamPanel, BorderLayout.CENTER);

            webcam.open();
        } else {
            JOptionPane.showMessageDialog(this, "No se detectó ninguna cámara.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Botón de captura
        btnCapturar = new JButton("Capturar Imagen");
        btnCapturar.addActionListener(e -> {
            BufferedImage original = webcam.getImage();
            if (original != null) {
                BufferedImage clonada = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = clonada.createGraphics();
                g2d.drawImage(original, 0, 0, null);
                g2d.dispose();
                imagenCapturada = clonada;
            }
            cerrarCamara();
            dispose();
        });
        add(btnCapturar, BorderLayout.SOUTH);

        // Cierre seguro de cámara
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarCamara();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                cerrarCamara();
            }
        });
    }

    public static BufferedImage getImagen() {
        return imagenCapturada;
    }

    private void cerrarCamara() {
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }
}
