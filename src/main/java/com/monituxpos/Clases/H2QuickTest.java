/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class H2QuickTest {
    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");

            String url = "jdbc:h2:file:C:/Users/Miguel Cerrato/Documents/NetBeansProjects/Monitux-POS-Java/Resources/Database/H2-DB-2;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
            Connection conn = DriverManager.getConnection(url, "sa", "");

            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS prueba (id INT PRIMARY KEY, nombre VARCHAR(255))");

            stmt.execute("INSERT INTO prueba VALUES (1, 'Miguel')");
            conn.close();

            System.out.println("✅ Base de datos H2 creada y tabla 'prueba' insertada.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
