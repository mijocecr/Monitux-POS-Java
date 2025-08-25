package com.monituxpos.Clases;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int secuencial;

    @Column(name = "secuencial_usuario", nullable = false)
    private int secuencialUsuario;

    @Column(nullable = false)
    private String fecha;

    private String descripcion;

    @Column(name = "secuencial_empresa", nullable = false)
    private int secuencialEmpresa;

    // Constructor con inicialización de fecha
    public Actividad() {
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a"));
    }

    // Getters y setters

    public int getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.secuencial = secuencial;
    }

    public int getSecuencialUsuario() {
        return secuencialUsuario;
    }

    public void setSecuencialUsuario(int secuencialUsuario) {
        this.secuencialUsuario = secuencialUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getSecuencialEmpresa() {
        return secuencialEmpresa;
    }

    public void setSecuencialEmpresa(int secuencialEmpresa) {
        this.secuencialEmpresa = secuencialEmpresa;
    }
}
