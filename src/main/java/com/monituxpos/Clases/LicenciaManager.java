package com.monituxpos.Clases;



import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;

public class LicenciaManager {
    private static final String URL_CSV = "https://docs.google.com/spreadsheets/d/1Y7dfYNYySdANwfjfvEFgWFap54QA-lJ6KoBabjuiHIs/export?format=csv&gid=0";
    private static final String URL_SCRIPT = "https://script.google.com/macros/s/AKfycbw4q6q0yvqEo8SisdTMt95xOuU797RaEWKw9v6-zFqRamtPmPErvIwrzQfj-EwqDh8CiA/exec";
    private static final Preferences prefs = Preferences.userRoot().node("Monitux_POS");

    public static boolean validarYActivar(String licenciaIngresada) {
        if (licenciaIngresada == null || licenciaIngresada.trim().isEmpty()) return false;
        if (prefs.getBoolean("LicenciaValida", false)) return true;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest csvRequest = HttpRequest.newBuilder().uri(URI.create(URL_CSV)).build();
            HttpResponse<String> csvResponse = client.send(csvRequest, HttpResponse.BodyHandlers.ofString());

            BufferedReader reader = new BufferedReader(new StringReader(csvResponse.body()));
            String line;
            boolean encontrado = false;
            String tipoLicencia = "";
            String estado = "";
            LocalDate fecha = null;
            boolean usada = false;

            // Saltar encabezado
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(",");
                if (campos.length < 5) continue;

                String cod = campos[1].trim(); // Columna "Licencia"
                if (!cod.equalsIgnoreCase(licenciaIngresada.trim())) continue;

                tipoLicencia = cod;
                estado = campos[2].trim().toUpperCase(); // Columna "Estado"
                String fechaStr = campos[3].trim();       // Columna "Fecha"
                String usadaStr = campos[4].trim().toUpperCase(); // Columna "Usada"
                usada = usadaStr.equals("SI");

                try {
                    fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception ex) {
                    System.out.println("⚠️ Fecha inválida: " + fechaStr);
                    return false;
                }

                encontrado = true;
                break;
            }

            if (!encontrado || !estado.equals("ACTIVO") || usada || fecha.isBefore(LocalDate.now())) {
                System.out.println("❌ Licencia inválida, vencida o ya usada.");
                return false;
            }

            // Activación remota
            String urlFinal = URL_SCRIPT + "?codigo=" + licenciaIngresada.trim();
            HttpRequest activacionRequest = HttpRequest.newBuilder().uri(URI.create(urlFinal)).build();
            HttpResponse<String> activacionResponse = client.send(activacionRequest, HttpResponse.BodyHandlers.ofString());

            if ("ACTIVADA".equalsIgnoreCase(activacionResponse.body().trim())) {
                guardarLicencia(licenciaIngresada.trim(), tipoLicencia, estado, fecha.toString(), true);
                return true;
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }

        return false;
    }

    private static void guardarLicencia(String codigo, String tipo, String estado, String fecha, boolean usada) {
        prefs.put("Codigo_Licencia", codigo);
        prefs.put("TipoLicencia", tipo);
        prefs.put("EstadoLicencia", estado);
        prefs.put("FechaExpiracion", fecha);
        prefs.putBoolean("LicenciaValida", true);
        prefs.putBoolean("LicenciaUsada", usada);
    }

    public static boolean esLicenciaValida() {
        return prefs.getBoolean("LicenciaValida", false);
    }

    public static String obtenerCodigo() {
        return prefs.get("Codigo_Licencia", "");
    }

    public static String obtenerTipoLicencia() {
        return prefs.get("TipoLicencia", "");
    }

    public static String obtenerEstado() {
        return prefs.get("EstadoLicencia", "");
    }

    public static String obtenerFechaExpiracion() {
        return prefs.get("FechaExpiracion", "");
    }

    public static boolean fueLicenciaUsada() {
        return prefs.getBoolean("LicenciaUsada", false);
    }

    public static void borrarLicencia() {
        prefs.remove("Codigo_Licencia");
        prefs.remove("TipoLicencia");
        prefs.remove("EstadoLicencia");
        prefs.remove("FechaExpiracion");
        prefs.putBoolean("LicenciaValida", false);
        prefs.putBoolean("LicenciaUsada", false);
    }
}
