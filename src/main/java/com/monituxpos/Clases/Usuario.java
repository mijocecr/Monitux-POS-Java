package com.monituxpos.Clases;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int secuencial;

    @Column(nullable = false)
    private String codigo;

    private String nombre;

    @Column(nullable = false)
    private String password;

   @Lob
@Column(name = "imagen", columnDefinition = "LONGBLOB")
private byte[] imagen;


    @Column(nullable = false)
    private String acceso;

    @Column(nullable = false)
    private boolean activo;

    @Column(name = "secuencial_empresa", nullable = false)
    private int secuencial_empresa;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getSecuencialEmpresa() {
        return secuencial_empresa;
    }

    public void setSecuencialEmpresa(int secuencialEmpresa) {
        this.secuencial_empresa = secuencialEmpresa;
    }
}
