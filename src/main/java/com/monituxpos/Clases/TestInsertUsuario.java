/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import jakarta.persistence.EntityManager;

public class TestInsertUsuario {
    public static void main(String[] args) {
        MonituxDBContext.init(
            DBProvider.H2,
            "jdbc:h2:file:./Resources/Database/H2-DB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
            "sa",
            ""
        );

        try {
            EntityManager em = MonituxDBContext.getEntityManager();
            em.getTransaction().begin();

            Usuario usuario = new Usuario();
            usuario.setCodigo("USR001");
            usuario.setNombre("Miguel Cerrato");
            usuario.setPassword("clave123");
            usuario.setAcceso("ADMIN");
            usuario.setActivo(true);
            usuario.setSecuencial_Empresa(1);
            usuario.setImagen(null); // o cargar desde archivo si quieres

            em.persist(usuario);
            em.getTransaction().commit();

            Usuario resultado = em.find(Usuario.class, usuario.getSecuencial());
            System.out.println("✅ Usuario insertado: " + resultado.getNombre());
        } catch (Exception e) {
            System.err.println("❌ Error en la prueba: " + e.getMessage());
            e.printStackTrace();
        } finally {
            MonituxDBContext.close();
        }
    }
}
