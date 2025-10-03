/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;

public class GestorLicencia {

    private static final String URL_HOJA = "https://docs.google.com/spreadsheets/d/1Y7dfYNYySdANwfjfvEFgWFap54QA-lJ6KoBabjuiHIs/export?format=csv&gid=0";
    private static final String URL_ACTIVACION = "https://script.google.com/macros/s/AKfycbw4q6q0yvqEo8SisdTMt95xOuU797RaEWKw9v6-zFqRamtPmPErvIwrzQfj-EwqDh8CiA/exec";

    private final Preferences prefs = Preferences.userRoot().node("Monitux_POS");

    public boolean validarYActivarLicencia(String codigo) {
        if (prefs.getBoolean("LicenciaValida", false)) {
            return true;
        }

        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        try {
            String csv = descargarTexto(URL_HOJA);
            BufferedReader reader = new BufferedReader(new StringReader(csv));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");

                if (campos.length >= 5) {
                    String cod = campos[0].trim();
                    String nombre = campos[1].trim();
                    String fechaStr = campos[2].trim();
                    String estado = campos[3].trim().toUpperCase();
                    String usada = campos[4].trim().toUpperCase();

                    LocalDate expira = parseFecha(fechaStr);

                    if (cod.equalsIgnoreCase(codigo) &&
                        "ACTIVO".equals(estado) &&
                        expira != null &&
                        !expira.isBefore(LocalDate.now()) &&
                        !"SI".equals(usada)) {

                        String urlActivacionFinal = URL_ACTIVACION + "?codigo=" + codigo;
                        String respuesta = descargarTexto(urlActivacionFinal);

                        if ("ACTIVADA".equalsIgnoreCase(respuesta.trim())) {
                            prefs.putBoolean("LicenciaValida", true);
                            prefs.put("NombreCliente", nombre);
                            prefs.put("FechaExpiracion", expira.toString());
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Silenciar o loguear si lo deseas
        }

        return false;
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
}
