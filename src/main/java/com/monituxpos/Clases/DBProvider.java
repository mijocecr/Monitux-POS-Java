package com.monituxpos.Clases;

/**
 * Enum que representa los proveedores de base de datos soportados.
 * Puedes extenderlo fácilmente si decides añadir más motores en el futuro.
 */
public enum DBProvider {
    MYSQL,
    POSTGRESQL,
    SQLSERVER,
    H2
}
