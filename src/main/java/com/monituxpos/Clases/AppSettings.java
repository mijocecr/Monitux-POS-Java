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

public class AppSettings {
    private static final Preferences prefs = Preferences.userRoot().node("Monitux_POS");

    
    public static void set_Licencia(String codigo_licencia, String cliente, String fecha_vencimiento) {
        prefs.put("CODIGO_LICENCIA", codigo_licencia);
        prefs.put("CLIENTE", cliente);
        prefs.put("FECHA_VENCIMIENTO", fecha_vencimiento);
        
   }
    
    public static void set_Conexion (String db_provider,String db_connection){
    
        prefs.put("DB_PROVIDER",db_provider);
        prefs.put("DB_CONNECTION",db_connection);

    }
    
      public static void set_Primer_Arranque (boolean primer_arranque){
    
        prefs.putBoolean("PRIMER_ARRANQUE",primer_arranque);


    }
      
       public static void set_Usuario_Creado (boolean usuario_creado){
    
        prefs.putBoolean("USUARIO_CREADO",usuario_creado);

    }
       
              public static void set_Empresa_Creada (boolean empresa_creada){
    
        prefs.putBoolean("EMPRESA_CREADA",empresa_creada);

    }

              public static void set_Licencia_Valida (boolean licencia_valida){
    
        prefs.putBoolean("LICENCIA_VALIDA",licencia_valida);

    }
    
    
    
    
     
    public static String getDB_Provider() {
        return prefs.get("DB_PROVIDER", "");
    }
    
    public static String getDB_Connection() {
        return prefs.get("DB_CONNECTION", "");
    }
    

    
    public static String getCodigo_Licencia() {
        return prefs.get("CODIGO_LICENCIA", "");
    }

    public static String getCliente() {
        return prefs.get("CLIENTE", "");
    }

    public static String getFecha_Vencimiento() {
        return prefs.get("FECHA_VENCIMIENTO", "");
    }

    public static boolean getLicencia_Valida() {
        return prefs.getBoolean("LICENCIA_VALIDA", false);
    }

     public static boolean getPrimer_Arranque() {
        return prefs.getBoolean("PRIMER_ARRANQUE", false);
    }
     
     public static boolean getEmpresa_Creada() {
        return prefs.getBoolean("EMPRESA_CREADA", false);
    }

     public static boolean getUsuario_Creado() {
        return prefs.getBoolean("USUARIO_CREADO", false);
    }

     public static void setCredenciales(String usuario, String password) {
    prefs.put("DB_USUARIO", usuario);
    prefs.put("DB_PASSWORD", password);
}

public static String getUsuario() {
    return prefs.get("DB_USUARIO", "");
}

public static String getPassword() {
    return prefs.get("DB_PASSWORD", "");
}

     
   

//    // Resetear valores (opcional)
//    public static void resetearLicencia() {
//        prefs.remove("Codigo_Licencia");
//        prefs.remove("NombreCliente");
//        prefs.remove("FechaExpiracion");
//        prefs.putBoolean("LicenciaValida", false);
//        prefs.putBoolean("CodigoUsado", false);
//    }
}