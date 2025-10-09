/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;


import jakarta.persistence.*;

@Entity
@Table(name = "Comentarios")
public class Comentario {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int Secuencial;


    private int Secuencial_Factura_C;
    private int Secuencial_Factura_V;
    private int Secuencial_Producto;
    private int Secuencial_Cotizacion;
    private int Secuencial_Orden;

    private String Contenido;

    private int Secuencial_Empresa;

    public Comentario() {
        // Constructor por defecto
    }

    // Getters y Setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.Secuencial = secuencial;
    }

    public int getSecuencial_Factura_C() {
        return Secuencial_Factura_C;
    }

    public void setSecuencial_Factura_C(int secuencial_Factura_C) {
        this.Secuencial_Factura_C = secuencial_Factura_C;
    }

    public int getSecuencial_Factura_V() {
        return Secuencial_Factura_V;
    }

    public void setSecuencial_Factura_V(int secuencial_Factura_V) {
        this.Secuencial_Factura_V = secuencial_Factura_V;
    }

    public int getSecuencial_Producto() {
        return Secuencial_Producto;
    }

    public void setSecuencial_Producto(int secuencial_Producto) {
        this.Secuencial_Producto = secuencial_Producto;
    }

    public int getSecuencial_Cotizacion() {
        return Secuencial_Cotizacion;
    }

    public void setSecuencial_Cotizacion(int secuencial_Cotizacion) {
        this.Secuencial_Cotizacion = secuencial_Cotizacion;
    }

    public int getSecuencial_Orden() {
        return Secuencial_Orden;
    }

    public void setSecuencial_Orden(int secuencial_Orden) {
        this.Secuencial_Orden = secuencial_Orden;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String contenido) {
        this.Contenido = contenido;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int secuencial_Empresa) {
        this.Secuencial_Empresa = secuencial_Empresa;
    }
}
