/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import com.monituxpos.Clases.MonituxDBContext;


import jakarta.persistence.EntityManager;

public class TestPersistEmpresa {

    public static void main(String[] args) {
        // Inicializa el contexto con PostgreSQL
        MonituxDBContext.init(
            DBProvider.POSTGRESQL,
            "192.168.10.10:5432/monitux", // ajusta según tu configuración
            "miguel",
            "00511"
        );

        EntityManager em = MonituxDBContext.getEntityManager();

        try {
            em.getTransaction().begin();

            Empresa empresa = new Empresa();
            empresa.setNombre("Prueba Binaria");
            empresa.setDireccion("Dirección de prueba");
            empresa.setImagen(new byte[] {1, 2, 3, 4}); // imagen simulada

            em.persist(empresa);
            em.getTransaction().commit();

            System.out.println("✅ Empresa insertada con ID: " + empresa.getSecuencial());

        } catch (Exception e) {
            System.err.println("❌ Error al insertar empresa: " + e.getMessage());
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            MonituxDBContext.close();
        }
    }
}
