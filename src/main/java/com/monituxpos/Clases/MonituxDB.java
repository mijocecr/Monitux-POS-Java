package com.monituxpos.Clases;


import jakarta.persistence.*;
import java.util.*;

public class MonituxDB {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void init(String proveedor, String conexion) {
        Map<String, String> props = new HashMap<>();

        switch (proveedor.toLowerCase()) {
            case "sqlite":
                props.put("jakarta.persistence.jdbc.url", "jdbc:sqlite:" + conexion);
                props.put("jakarta.persistence.jdbc.driver", "org.sqlite.JDBC");
                props.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
                break;
            case "mysql":
                props.put("jakarta.persistence.jdbc.url", conexion);
                props.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
                props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                break;
            case "sqlserver":
                props.put("jakarta.persistence.jdbc.url", conexion);
                props.put("jakarta.persistence.jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                props.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
                break;
            case "postgres":
                props.put("jakarta.persistence.jdbc.url", conexion);
                props.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
                props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                break;
            default:
                throw new UnsupportedOperationException("Proveedor no soportado: " + proveedor);
        }

        props.put("jakarta.persistence.jdbc.user", "usuario");
        props.put("jakarta.persistence.jdbc.password", "contraseña");
        props.put("hibernate.hbm2ddl.auto", "update");

        emf = Persistence.createEntityManagerFactory("MonituxPU", props);
        em = emf.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return em;
    }

    public static void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}
