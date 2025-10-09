/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;
import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidadorLicenciaConsola {
    private static final String URL_CSV = "https://docs.google.com/spreadsheets/d/e/2PACX-1vSNT2-r-c8KZnxDsjyygeFMozXhsDou6BCIk0NOqjQGQo26HqGmcMUAzuyyiBSNtT7x-EScoIANm7s_/pub?gid=0&single=true&output=csv";
    private static final String URL_SCRIPT = "https://script.google.com/macros/s/AKfycbw4q6q0yvqEo8SisdTMt95xOuU797RaEWKw9v6-zFqRamtPmPErvIwrzQfj-EwqDh8CiA/exec";

    public static void main(String[] args) {
        String licenciaIngresada = "MJCC300725"; // Cambia este valor para probar otros códigos

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest csvRequest = HttpRequest.newBuilder().uri(URI.create(URL_CSV)).build();
            HttpResponse<String> csvResponse = client.send(csvRequest, HttpResponse.BodyHandlers.ofString());

            BufferedReader reader = new BufferedReader(new StringReader(csvResponse.body()));
            String line;
            boolean encontrado = false;

            reader.readLine(); // Saltar encabezado

            while ((line = reader.readLine()) != null) {
                System.out.println("Línea CSV: " + line);

                String[] campos = line.split(",");
                System.out.println("Campos detectados: " + campos.length);
                for (int i = 0; i < campos.length; i++) {
                    System.out.println("Campo[" + i + "]: '" + campos[i].trim() + "'");
                }

                if (campos.length < 5) continue;

                String codigo = campos[0].trim();
                String nombre = campos[1].trim();
                String fechaStr = campos[2].trim();
                String estado = campos[3].trim().toUpperCase();
                String usadaStr = campos[4].trim().toUpperCase();

                if (!codigo.equalsIgnoreCase(licenciaIngresada)) continue;

                encontrado = true;
                boolean usada = usadaStr.equals("SI");
                LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                System.out.println("✔ Código encontrado");
                System.out.println("Cliente: " + nombre);
                System.out.println("Estado: " + estado);
                System.out.println("Expira: " + fecha);
                System.out.println("Usada: " + usadaStr);

                if (!estado.equals("ACTIVO")) {
                    System.out.println("❌ Licencia inactiva");
                    return;
                }

                if (usada) {
                    System.out.println("❌ Licencia ya usada");
                    return;
                }

                if (fecha.isBefore(LocalDate.now())) {
                    System.out.println("❌ Licencia vencida");
                    return;
                }

                // Activación remota
                String urlFinal = URL_SCRIPT + "?codigo=" + licenciaIngresada;
                HttpRequest activacionRequest = HttpRequest.newBuilder().uri(URI.create(urlFinal)).build();
                HttpResponse<String> activacionResponse = client.send(activacionRequest, HttpResponse.BodyHandlers.ofString());

                System.out.println("Respuesta del script: " + activacionResponse.body());

                if ("ACTIVADA".equalsIgnoreCase(activacionResponse.body().trim())) {
                    System.out.println("✅ Licencia activada correctamente");
                } else {
                    System.out.println("❌ Activación rechazada por el servidor");
                }

                return;
            }

            if (!encontrado) {
                System.out.println("❌ Código no encontrado en la hoja");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
