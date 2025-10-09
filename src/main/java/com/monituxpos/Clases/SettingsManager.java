/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

/**
 *
 * @author Miguel Cerrato
 */

import java.util.prefs.Preferences;

public class SettingsManager {
    private static final Preferences prefs = Preferences.userRoot().node("Monitux_POS");

    // Guardar valores
    public static void guardarLicencia(String codigo, String nombreCliente, String fechaExpiracion) {
        prefs.put("Codigo_Licencia", codigo);
        prefs.put("NombreCliente", nombreCliente);
        prefs.put("FechaExpiracion", fechaExpiracion);
        prefs.putBoolean("LicenciaValida", true);
        prefs.putBoolean("CodigoUsado", true);
    }

    // Leer valores
    public static String obtenerCodigo() {
        return prefs.get("Codigo_Licencia", "");
    }

    public static String obtenerNombreCliente() {
        return prefs.get("NombreCliente", "");
    }

    public static String obtenerFechaExpiracion() {
        return prefs.get("FechaExpiracion", "");
    }

    public static boolean esLicenciaValida() {
        return prefs.getBoolean("LicenciaValida", false);
    }

    public static boolean fueCodigoUsado() {
        return prefs.getBoolean("CodigoUsado", false);
    }

    // Resetear valores (opcional)
    public static void resetearLicencia() {
        prefs.remove("Codigo_Licencia");
        prefs.remove("NombreCliente");
        prefs.remove("FechaExpiracion");
        prefs.putBoolean("LicenciaValida", false);
        prefs.putBoolean("CodigoUsado", false);
    }
}