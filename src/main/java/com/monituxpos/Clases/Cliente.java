package com.monituxpos.Clases;


import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int secuencial;

    private String codigo;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;

    @Lob
    private byte[] imagen;

    private Boolean activo;

    @Column(name = "secuencial_empresa", nullable = false)
    private int secuencialEmpresa;

    // Getters y setters

    public int getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.secuencial = secuencial;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public int getSecuencialEmpresa() {
        return secuencialEmpresa;
    }

    public void setSecuencialEmpresa(int secuencialEmpresa) {
        this.secuencialEmpresa = secuencialEmpresa;
    }
}
