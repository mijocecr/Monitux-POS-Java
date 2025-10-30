/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

/**
 *
 * @author Miguel Cerrato
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TarjetaDashboard {

    private JPanel panel;
    private String titulo;

    private JLabel labelTitulo;
    private JLabel labelValor;
    private JLabel labelVariacion;
    private JLabel picIcono;

    public TarjetaDashboard(String titulo, String valorPrincipal, String variacion, Image icono, Color fondo, Point ubicacion) {
        this.titulo = titulo;

        panel = new JPanel();
        panel.setLayout(null); // Posicionamiento absoluto
        panel.setSize(200, 80);
        panel.setBackground(fondo);
        panel.setLocation(ubicacion);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panel.setOpaque(true);

        labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setBounds(10, 6, 180, 20);

        labelValor = new JLabel(valorPrincipal);
        labelValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelValor.setForeground(Color.WHITE);
        labelValor.setBounds(10, 28, 180, 20);

        labelVariacion = new JLabel(variacion);
        labelVariacion.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        labelVariacion.setForeground(Color.LIGHT_GRAY);
        labelVariacion.setBounds(10, 50, 180, 20);

        picIcono = new JLabel(new ImageIcon(icono));
        picIcono.setBounds(160, 30, 28, 28);
        picIcono.setOpaque(false);

        panel.add(labelTitulo);
        panel.add(labelValor);
        panel.add(labelVariacion);
        panel.add(picIcono);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getTitulo() {
        return titulo;
    }

    public JLabel getLabelTitulo() {
        return labelTitulo;
    }

    public JLabel getLabelValor() {
        return labelValor;
    }

    public JLabel getLabelVariacion() {
        return labelVariacion;
    }

    public void asignarClickComun(MouseAdapter eventoClick) {
        panel.addMouseListener(eventoClick);
        for (Component comp : panel.getComponents()) {
            comp.addMouseListener(eventoClick);
        }
    }
}