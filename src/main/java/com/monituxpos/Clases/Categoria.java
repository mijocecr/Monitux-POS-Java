package com.monituxpos.Clases;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    private String Nombre;
    private String Descripcion;

   
@Column(name = "Imagen")
private byte[] Imagen;

    @Column(name = "Secuencial_Empresa", nullable = false)
    private int Secuencial_Empresa;

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] Imagen) {
        this.Imagen = Imagen;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }

   
}
