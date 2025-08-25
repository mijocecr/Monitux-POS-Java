package com.monituxpos.Clases;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int secuencial;

    private String nombre;
    private String descripcion;

    @Lob
    private byte[] imagen;

    @Column(name = "secuencial_empresa", nullable = false)
    private int secuencialEmpresa;

    // Getters y setters

    public int getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.secuencial = secuencial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getSecuencialEmpresa() {
        return secuencialEmpresa;
    }

    public void setSecuencialEmpresa(int secuencialEmpresa) {
        this.secuencialEmpresa = secuencialEmpresa;
    }
}
