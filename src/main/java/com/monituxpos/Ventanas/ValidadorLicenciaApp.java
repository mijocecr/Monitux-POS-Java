/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Ventanas;

/**
 *
 * @author Miguel Cerrato
 */
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;

public class ValidadorLicenciaApp extends JFrame {
    private static final String URL_CSV = "https://docs.google.com/spreadsheets/d/1Y7dfYNYySdANwfjfvEFgWFap54QA-lJ6KoBabjuiHIs/export?format=csv&gid=0";
    private static final String URL_SCRIPT = "https://script.google.com/macros/s/AKfycbw4q6q0yvqEo8SisdTMt95xOuU797RaEWKw9v6-zFqRamtPmPErvIwrzQfj-EwqDh8CiA/exec";
    private static final Preferences prefs = Preferences.userRoot().node("Monitux_POS");

    private JTextField txtLicencia;
    private JLabel lblResultado, lblEstado, lblExpira;

    public ValidadorLicenciaApp() {
        setTitle("Validador de Licencia");
        setSize(400, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        txtLicencia = new JTextField();
        JButton btnValidar = new JButton("Validar Licencia");
        lblResultado = new JLabel("Resultado: ");
        lblEstado = new JLabel("Estado: ");
        lblExpira = new JLabel("Expira: ");

        add(new JLabel("Ingrese Código de Licencia:"));
        add(txtLicencia);
        add(btnValidar);
        add(lblResultado);
        add(lblEstado);
        add(lblExpira);

        btnValidar.addActionListener(e -> validarLicencia(txtLicencia.getText().trim()));

        // Mostrar datos si ya está activada
        if (prefs.getBoolean("LicenciaValida", false)) {
            lblResultado.setText("✅ Licencia ya activada: " + prefs.get("Codigo_Licencia", ""));
            lblEstado.setText("Estado: " + prefs.get("EstadoLicencia", ""));
            lblExpira.setText("Expira: " + prefs.get("FechaExpiracion", ""));
        }
    }

    private void validarLicencia(String licenciaIngresada) {
        if (licenciaIngresada.isEmpty()) {
            lblResultado.setText("⚠️ Ingrese un código");
            return;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest csvRequest = HttpRequest.newBuilder().uri(URI.create(URL_CSV)).build();
            HttpResponse<String> csvResponse = client.send(csvRequest, HttpResponse.BodyHandlers.ofString());

            BufferedReader reader = new BufferedReader(new StringReader(csvResponse.body()));
            String line;
            boolean encontrado = false;
            String estado = "";
            LocalDate fecha = null;
            boolean usada = false;

            reader.readLine(); // Saltar encabezado

            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (campos.length < 5) continue;

                String cod = campos[0].trim(); // Columna "Codigo_Licencia"
                if (!cod.equalsIgnoreCase(licenciaIngresada)) continue;

                estado = campos[2].trim().toUpperCase();
                String fechaStr = campos[3].trim();
                String usadaStr = campos[4].trim().toUpperCase();
                usada = usadaStr.equals("SI");

                try {
                    fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception ex) {
                    lblResultado.setText("⚠️ Fecha inválida");
                    return;
                }

                encontrado = true;
                break;
            }

            if (!encontrado) {
                lblResultado.setText("❌ Código no encontrado");
                return;
            }

            if (!estado.equals("ACTIVO")) {
                lblResultado.setText("❌ Licencia inactiva");
                return;
            }

            if (usada) {
                lblResultado.setText("❌ Licencia ya usada");
                return;
            }

            if (fecha.isBefore(LocalDate.now())) {
                lblResultado.setText("❌ Licencia vencida");
                return;
            }

            // Activación remota
            String urlFinal = URL_SCRIPT + "?codigo=" + licenciaIngresada;
            HttpRequest activacionRequest = HttpRequest.newBuilder().uri(URI.create(urlFinal)).build();
            HttpResponse<String> activacionResponse = client.send(activacionRequest, HttpResponse.BodyHandlers.ofString());

            if ("ACTIVADA".equalsIgnoreCase(activacionResponse.body().trim())) {
                prefs.put("Codigo_Licencia", licenciaIngresada);
                prefs.put("EstadoLicencia", estado);
                prefs.put("FechaExpiracion", fecha.toString());
                prefs.putBoolean("LicenciaValida", true);
                prefs.putBoolean("LicenciaUsada", true);

                lblResultado.setText("✅ Licencia activada: " + licenciaIngresada);
                lblEstado.setText("Estado: " + estado);
                lblExpira.setText("Expira: " + fecha);
            } else {
                lblResultado.setText("❌ Activación rechazada: " + activacionResponse.body());
            }

        } catch (Exception e) {
            lblResultado.setText("❌ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ValidadorLicenciaApp().setVisible(true));
    }
}
