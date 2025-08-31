/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

import javax.swing.*;
import java.awt.*;
//
//public class SelectorCantidad extends JPanel {
//
//    private JLabel labelCodigo;
//    public JSpinner spinnerCantidad;
//    private JCheckBox checkBoxSeleccionado;
//
//    public SelectorCantidad(String codigoProducto) {
//        setLayout(new BorderLayout());
//        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//        setBackground(Color.WHITE);
//
//        // 🔤 Código del producto
//        labelCodigo = new JLabel(codigoProducto);
//        labelCodigo.setFont(new Font("Arial", Font.BOLD, 14));
//        labelCodigo.setHorizontalAlignment(SwingConstants.CENTER);
//
//        // 🔢 Spinner para cantidad
//        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.0, 1000.0, 1);
//        spinnerCantidad = new JSpinner(model);
//        spinnerCantidad.setPreferredSize(new Dimension(100, 30));
//
//        // ✅ Checkbox de selección (opcional)
//        checkBoxSeleccionado = new JCheckBox("Quitar");
//        checkBoxSeleccionado.setSelected(false);
//
//        // 🧩 Panel interno
//        JPanel panelCentro = new JPanel(new GridLayout(2, 1));
//        panelCentro.setBackground(Color.WHITE);
//        panelCentro.add(spinnerCantidad);
//        panelCentro.add(checkBoxSeleccionado);
//
//        add(labelCodigo, BorderLayout.NORTH);
//        add(panelCentro, BorderLayout.CENTER);
//    }
//
//    // 🔧 Métodos de acceso
//    public String getCodigo() {
//        return labelCodigo.getText();
//    }
//
//    public double getCantidadSeleccionada() {
//        return ((Number) spinnerCantidad.getValue()).doubleValue();
//    }
//
//    public boolean isSeleccionado() {
//        return checkBoxSeleccionado.isSelected();
//    }
//
//    public void setCantidad(double cantidad) {
//        spinnerCantidad.setValue(cantidad);
//    }
//
//    public void setSeleccionado(boolean seleccionado) {
//        checkBoxSeleccionado.setSelected(seleccionado);
//    }
//}

public class SelectorCantidad extends JPanel {

    private JLabel labelCodigo;
    public JSpinner spinnerCantidad;
    private JCheckBox checkBoxSeleccionado;

    public SelectorCantidad(String codigoProducto) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 60)); // 🔽 Altura reducida

        // 🔤 Código del producto
        labelCodigo = new JLabel(codigoProducto);
        labelCodigo.setFont(new Font("Arial", Font.BOLD, 12));
        labelCodigo.setHorizontalAlignment(SwingConstants.CENTER);
        labelCodigo.setPreferredSize(new Dimension(100, 20)); // 🔽 Más compacto

        // 🔢 Spinner para cantidad
        SpinnerNumberModel model = new SpinnerNumberModel(1.0, 0.0, 1000.0, 1);
        spinnerCantidad = new JSpinner(model);
        spinnerCantidad.setPreferredSize(new Dimension(70, 25));

        // ✅ Checkbox de selección
        checkBoxSeleccionado = new JCheckBox("Quitar");
        checkBoxSeleccionado.setFont(new Font("Arial", Font.PLAIN, 12));
        checkBoxSeleccionado.setPreferredSize(new Dimension(80, 25));

        // 🧩 Panel horizontal para spinner + checkbox
        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setPreferredSize(new Dimension(100, 30));
        panelCentro.add(spinnerCantidad);
        panelCentro.add(checkBoxSeleccionado);

        add(labelCodigo, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
    }

    // 🔧 Métodos de acceso
    public String getCodigo() {
        return labelCodigo.getText();
    }

    public double getCantidadSeleccionada() {
        return ((Number) spinnerCantidad.getValue()).doubleValue();
    }

    public boolean isSeleccionado() {
        return checkBoxSeleccionado.isSelected();
    }

    public void setCantidad(double cantidad) {
        spinnerCantidad.setValue(cantidad);
    }

    public void setSeleccionado(boolean seleccionado) {
        checkBoxSeleccionado.setSelected(seleccionado);
    }
}
