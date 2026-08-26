package com.monituxpos.Clases;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

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
            currentUser = user;
            currentPassword = password;

            // ============================================
            // RUTA PORTABLE PARA H2 (JAR / APPIMAGE)
            // ============================================
            if (provider == DBProvider.H2) {

                Path dbFolder = Paths.get(System.getProperty("user.dir"), "Database");
                dbFolder.toFile().mkdirs();

                System.out.println("📁 Carpeta de base de datos (JAR/AppImage): " + dbFolder.toAbsolutePath());

                Path dbPath = dbFolder.resolve("H2-DB");

                connectionUrl = dbPath.toAbsolutePath().normalize().toString().replace("\\", "/");
            } else {
                connectionUrl = connectionString;
            }

            // Registrar driver JDBC
            try {
                switch (provider) {
                    case SQLSERVER -> DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                    case MYSQL -> DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                    case POSTGRESQL -> DriverManager.registerDriver(new org.postgresql.Driver());
                    case H2 -> DriverManager.registerDriver(new org.h2.Driver());
                    default -> throw new IllegalArgumentException("Proveedor no soportado: " + provider);
                }
                System.out.println("✅ Driver registrado manualmente: " + getDriver(provider));
            } catch (SQLException e) {
                throw new IllegalStateException("❌ Error al registrar el driver JDBC: " + getDriver(provider), e);
            }

            Map<String, Object> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", getJdbcConnectionString());
            props.put("jakarta.persistence.jdbc.user", user);
            props.put("jakarta.persistence.jdbc.password", password);
            props.put("jakarta.persistence.jdbc.driver", getDriver(provider));
            props.put("hibernate.connection.driver_class", getDriver(provider));
            props.put("hibernate.dialect", getDialect(provider));
            props.put("hibernate.hbm2ddl.auto", "none");
            props.put("hibernate.globally_quoted_identifiers", "true");
            props.put("hibernate.show_sql", "true");
            props.put("hibernate.archive.autodetection", "class");
            props.put("hibernate.jdbc.lob.non_contextual_creation", "true");
            props.put("hibernate.format_sql", "true");
            props.put("hibernate.use_sql_comments", "true");

            if (provider == DBProvider.SQLSERVER) {
                props.put("hibernate.connection.autocommit", "true");
            }

            var registry = new StandardServiceRegistryBuilder()
                    .applySettings(props)
                    .build();

            var sources = new MetadataSources(registry);
            registerEntities(sources);

            emf = sources.buildMetadata().buildSessionFactory();

            if (emf == null || !emf.isOpen()) {
                throw new IllegalStateException("❌ EntityManagerFactory no se pudo abrir.");
            }

            em = emf.createEntityManager();

            // Validación de archivo H2
            if (provider == DBProvider.H2) {
                File dbFile = Paths.get(connectionUrl + ".mv.db").toFile();
                if (!dbFile.exists()) {
                    System.out.println("ℹ️ La base de datos será creada automáticamente por H2.");
                }
            }

            System.out.println("✅ JPA inicializado correctamente.");
        } catch (Exception ex) {
            System.err.println("❌ Error al inicializar JPA: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void ensureEntityManagerFactoryReady() {
        if (emf == null || !emf.isOpen()) {
            System.out.println("🔄 Reconstruyendo EntityManagerFactory...");
            init(currentProvider, connectionUrl, currentUser, currentPassword);
        }
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
    }

    public static EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            System.out.println("⚠️ EntityManager estaba cerrado. Reabriendo...");
            em = emf.createEntityManager();
        }
        return em;
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
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

    private static void registerEntities(MetadataSources sources) {
        Reflections reflections = new Reflections("com.monituxpos");
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        if (entities.isEmpty()) {
            throw new IllegalStateException("❌ No se encontraron entidades anotadas en el paquete com.monituxpos.");
        }
        for (Class<?> entityClass : entities) {
            sources.addAnnotatedClass(entityClass);
            System.out.println("📦 Entidad registrada: " + entityClass.getName());
        }
    }
}
