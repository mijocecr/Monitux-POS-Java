package com.monituxpos.Clases;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MonituxDBContext {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static DBProvider currentProvider;
    private static String connectionUrl;
    private static String currentUser;
    private static String currentPassword;

    public static void init(DBProvider provider, String connectionString, String user, String password) {
        try {
            System.out.println("🔧 Inicializando JPA con " + provider + "...");

            currentProvider = provider;
            connectionUrl = connectionString;
            currentUser = user;
            currentPassword = password;

            // Carpeta portable para base de datos
            File dbFolder = new File(System.getProperty("user.dir") + File.separator + "Resources" + File.separator + "Database");
            if (provider == DBProvider.H2 && !dbFolder.exists()) {
                dbFolder.mkdirs();
                System.out.println("📁 Carpeta de base de datos creada en: " + dbFolder.getAbsolutePath());
            }

            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", connectionString);
            props.put("jakarta.persistence.jdbc.user", user);
            props.put("jakarta.persistence.jdbc.password", password);
            props.put("jakarta.persistence.jdbc.driver", getDriver(provider));
            props.put("hibernate.dialect", getDialect(provider));
            props.put("hibernate.hbm2ddl.auto", "create");
            props.put("hibernate.globally_quoted_identifiers", "true");
            props.put("hibernate.show_sql", "true");
            props.put("hibernate.archive.autodetection", "class");

            if (provider == DBProvider.SQLSERVER) {
                props.put("hibernate.connection.autocommit", "true");
            }

            Class.forName(props.get("jakarta.persistence.jdbc.driver").toString());

            emf = new HibernatePersistenceProvider().createEntityManagerFactory("MonituxPU", props);
            if (emf == null || !emf.isOpen()) {
                throw new IllegalStateException("❌ EntityManagerFactory no se pudo abrir.");
            }

            em = emf.createEntityManager();

            if (provider == DBProvider.H2) {
                File dbFile = new File(dbFolder, "H2-DB.mv.db");
                if (!dbFile.exists()) {
                    throw new IllegalStateException("❌ El archivo de base de datos no fue creado: " + dbFile.getAbsolutePath());
                }
            }

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
            case H2 -> "org.h2.Driver";
            default -> throw new IllegalArgumentException("Proveedor no soportado: " + provider);
        };
    }

    private static String getDialect(DBProvider provider) {
        return switch (provider) {
            case MYSQL -> "org.hibernate.dialect.MySQLDialect";
            case POSTGRESQL -> "org.hibernate.dialect.PostgreSQLDialect";
            case SQLSERVER -> "org.hibernate.dialect.SQLServerDialect";
            case H2 -> "org.hibernate.dialect.H2Dialect";
            default -> throw new IllegalArgumentException("Proveedor no soportado: " + provider);
        };
    }

    public static Map<String, String> getConnectionData() {
        Map<String, String> data = new HashMap<>();
        data.put("provider", currentProvider != null ? currentProvider.name() : "");
        data.put("url", connectionUrl != null ? connectionUrl : "");
        data.put("user", currentUser != null ? currentUser : "");
        data.put("password", currentPassword != null ? currentPassword : "");
        return data;
    }
    
    
    
   public static String getJdbcConnectionString() {
    if (currentProvider == null || connectionUrl == null) {
        throw new IllegalStateException("Proveedor o URL no inicializados.");
    }

    // Si la URL ya comienza con "jdbc:", se asume que está completa
    if (connectionUrl.trim().toLowerCase().startsWith("jdbc:")) {
        return connectionUrl;
    }

    return switch (currentProvider) {
        case MYSQL -> "jdbc:mysql://" + connectionUrl;
        case POSTGRESQL -> "jdbc:postgresql://" + connectionUrl;
        case SQLSERVER -> "jdbc:sqlserver://" + connectionUrl;
        case H2 -> "jdbc:h2:" + connectionUrl;
        default -> throw new IllegalArgumentException("Proveedor no soportado: " + currentProvider);
    };
}

    
}
