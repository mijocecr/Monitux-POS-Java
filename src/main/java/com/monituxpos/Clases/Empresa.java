/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

import jakarta.persistence.*; // O javax.persistence.*, según tu stack
import java.math.BigDecimal;



@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    private int Secuencial;

    private String Nombre = "";
    private String Direccion = "";
    private String Telefono = "";
    private String Email = "";
    private String Moneda = "";

    private BigDecimal ISV = BigDecimal.ZERO; // Manejo preciso de valores monetarios

    private boolean Activa = true;

    
    private byte[] Imagen; // Puede ser null por defecto

    private int Secuencial_Usuario;

    private String RSS = "https://www.tunota.com/rss/honduras-hoy.xml";

    // Getters y setters pueden generarse automáticamente

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

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public BigDecimal getISV() {
        return ISV;
    }

    public void setISV(BigDecimal ISV) {
        this.ISV = ISV;
    }

    public boolean isActiva() {
        return Activa;
    }

    public void setActiva(boolean Activa) {
        this.Activa = Activa;
    }

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] Imagen) {
        this.Imagen = Imagen;
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int Secuencial_Usuario) {
        this.Secuencial_Usuario = Secuencial_Usuario;
    }

    public String getRSS() {
        return RSS;
    }

    public void setRSS(String RSS) {
        this.RSS = RSS;
    }
    
    
    
}
