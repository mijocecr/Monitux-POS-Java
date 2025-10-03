/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

/**
 *
 * @author Miguel Cerrato
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidadorLicencia {

    private static final String GOOGLE_SHEET_ID = "1Y7dfYNYySdANwfjfvEFgWFap54QA-lJ6KoBabjuiHIs";
    private static final String GID = "0";

    private boolean esValido = false;
    private String nombreCliente = "";
    private LocalDate fechaExpiracion;
    private String estado = "INDEFINIDO";

    public boolean validar(String codigoIngresado) {
        try {
            String url = "https://docs.google.com/spreadsheets/d/" + GOOGLE_SHEET_ID +
                         "/export?format=csv&gid=" + GID;

            String csv = descargarTexto(url);
            BufferedReader reader = new BufferedReader(new StringReader(csv));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");

                if (campos.length >= 4) {
                    String codigo = campos[0].trim();
                    String nombre = campos[1].trim();
                    String fecha = campos[2].trim();
                    String estadoCampo = campos[3].trim().toUpperCase();

                    LocalDate expira = parseFecha(fecha);

                    if (codigo.equalsIgnoreCase(codigoIngresado) && expira != null) {
                        this.nombreCliente = nombre;
                        this.fechaExpiracion = expira;
                        this.estado = estadoCampo;

                        if ("ACTIVO".equals(estadoCampo) && !expira.isBefore(LocalDate.now())) {
                            this.esValido = true;
                        }

                        break;
                    }
                }
            }
        } catch (Exception ex) {
            this.estado = "ERROR: " + ex.getMessage();
        }

        return esValido;
    }

    private String descargarTexto(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append("\n");
            }

            return content.toString();
        }
    }

    private LocalDate parseFecha(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return null;
        }
    }

    // Getters para acceder a los datos después de validar
    public boolean isEsValido() {
        return esValido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public String getEstado() {
        return estado;
    }
}
