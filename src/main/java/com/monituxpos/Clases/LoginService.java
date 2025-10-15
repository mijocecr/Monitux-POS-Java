/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;



public class LoginService {

    public Optional<Usuario> validarUsuario(String codigo, String clavePlano, int secuencialEmpresa) {
        EntityManager em = null;

        try {
            System.out.println("=== Diagnóstico de Login ===");
            System.out.println("Código ingresado: [" + codigo + "]");
            System.out.println("Clave en plano: [" + clavePlano + "]");

            String claveEncriptada = Encriptador.encriptar(clavePlano);
            System.out.println("Clave encriptada generada: [" + claveEncriptada + "]");
            System.out.println("Secuencial empresa: [" + secuencialEmpresa + "]");

            em = MonituxDBContext.getEntityManager().getEntityManagerFactory().createEntityManager();

            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.Codigo = :codigo AND u.Password = :clave AND u.Secuencial_Empresa = :empresa AND u.Activo = true",
                Usuario.class
            );
            query.setParameter("codigo", codigo);
            query.setParameter("clave", claveEncriptada);
            query.setParameter("empresa", secuencialEmpresa);
            query.setMaxResults(1);

            List<Usuario> resultados = query.getResultList();
            System.out.println("Cantidad de resultados: " + resultados.size());

            if (!resultados.isEmpty()) {
                System.out.println("Login exitoso: usuario encontrado.");
                return Optional.of(resultados.get(0));
            } else {
                System.out.println("Login fallido: no se encontró coincidencia.");
                return Optional.empty();
            }

        } catch (Exception ex) {
            System.out.println("Error durante la validación:");
            ex.printStackTrace();
            return Optional.empty();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
