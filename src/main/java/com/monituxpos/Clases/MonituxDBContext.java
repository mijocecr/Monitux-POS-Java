/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;


//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import org.hibernate.jpa.HibernatePersistenceProvider;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MonituxDBContext {
//
//    private static EntityManagerFactory emf;
//    private static EntityManager em;
//
//    public static void init(DBProvider provider, String connectionString, String user, String password) {
//        try {
//            System.out.println("🔧 Inicializando JPA con " + provider + "...");
//
//            Map<String, Object> props = new HashMap<>();
//            props.put("jakarta.persistence.jdbc.url", connectionString);
//            props.put("jakarta.persistence.jdbc.user", user);
//            props.put("jakarta.persistence.jdbc.password", password);
//            props.put("hibernate.hbm2ddl.auto", "update");
//            props.put("hibernate.schema_generation.create_source", "metadata");
//            props.put("hibernate.schema_generation.drop_source", "metadata");
//            props.put("hibernate.show_sql", "true");
//props.put("hibernate.globally_quoted_identifiers", "true");
//
//            switch (provider) {
//                case MYSQL:
//                    props.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
//                    props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//                    break;
//                case POSTGRESQL:
//                    props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
//                    props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//                    break;
//                case SQLSERVER:
//                    props.put("jakarta.persistence.jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                    props.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
//                    break;
//                default:
//                    throw new IllegalArgumentException("Proveedor no soportado: " + provider);
//            }
//
//            props.put("jakarta.persistence.mappingClasses", List.of(
//                com.monituxpos.Clases.Usuario.class,
//                com.monituxpos.Clases.Venta.class,
//                com.monituxpos.Clases.Ingreso.class
//            ));
//
//            //emf = new HibernatePersistenceProvider().createEntityManagerFactory(null, props);
//            emf = new HibernatePersistenceProvider().createEntityManagerFactory("MonituxPU", props);
//
//
//            if (emf == null) {
//                throw new IllegalStateException("❌ EntityManagerFactory no se pudo crear. Verifica configuración.");
//            }
//
//            em = emf.createEntityManager();
//            System.out.println("✅ JPA inicializado correctamente.");
//        } catch (Exception ex) {
//            System.err.println("❌ Error al inicializar JPA: " + ex.getMessage());
//            ex.printStackTrace();
//        }
//    }
//
//    public static EntityManager getEntityManager() {
//        if (em == null || !em.isOpen()) {
//            System.out.println("⚠️ EntityManager estaba cerrado. Reabriendo...");
//            em = emf.createEntityManager();
//        }
//        return em;
//    }
//
//    public static void close() {
//        if (em != null && em.isOpen()) em.close();
//        if (emf != null && emf.isOpen()) emf.close();
//    }
//}





import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonituxDBContext {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void init(DBProvider provider, String connectionString, String user, String password) {
        try {
            System.out.println("🔧 Inicializando JPA con " + provider + "...");

            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", connectionString);
            props.put("jakarta.persistence.jdbc.user", user);
            props.put("jakarta.persistence.jdbc.password", password);
            props.put("jakarta.persistence.jdbc.driver", getDriver(provider));
            props.put("hibernate.dialect", getDialect(provider));
            props.put("hibernate.hbm2ddl.auto", "update");
            props.put("hibernate.globally_quoted_identifiers", "true");
            props.put("hibernate.show_sql", "true");

            // Opcional: solo para SQL Server
            if (provider == DBProvider.SQLSERVER) {
                props.put("hibernate.connection.autocommit", "true");
            }

            props.put("jakarta.persistence.mappingClasses", List.of(
                com.monituxpos.Clases.Usuario.class,
                com.monituxpos.Clases.Venta.class,
                com.monituxpos.Clases.Ingreso.class
            ));

            // Validación defensiva del driver
            try {
                Class.forName(props.get("jakarta.persistence.jdbc.driver").toString());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("❌ Driver JDBC no encontrado: " + e.getMessage());
            }

            emf = new HibernatePersistenceProvider().createEntityManagerFactory("MonituxPU", props);
            if (emf == null || !emf.isOpen()) {
                throw new IllegalStateException("❌ EntityManagerFactory no se pudo abrir. Verifica configuración.");
            }

            em = emf.createEntityManager();
            System.out.println("✅ JPA inicializado correctamente.");
        } catch (Exception ex) {
            System.err.println("❌ Error al inicializar JPA: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            System.out.println("⚠️ EntityManager estaba cerrado. Reabriendo...");
            em = emf.createEntityManager();
        }
        return em;
    }

    public static void close() {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }

    private static String getDriver(DBProvider provider) {
        return switch (provider) {
            case MYSQL -> "com.mysql.cj.jdbc.Driver";
            case POSTGRESQL -> "org.postgresql.Driver";
            case SQLSERVER -> "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            default -> throw new IllegalArgumentException("Proveedor no soportado: " + provider);
        };
    }

    private static String getDialect(DBProvider provider) {
        return switch (provider) {
            case MYSQL -> "org.hibernate.dialect.MySQLDialect";
            case POSTGRESQL -> "org.hibernate.dialect.PostgreSQLDialect";
            case SQLSERVER -> "org.hibernate.dialect.SQLServerDialect";
            default -> throw new IllegalArgumentException("Proveedor no soportado: " + provider);
        };
    }
}
