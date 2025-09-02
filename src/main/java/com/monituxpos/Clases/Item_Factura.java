/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

/**
 *
 * @author Miguel Cerrato
 */


public class Item_Factura {

    private int Secuencial;
    private String Codigo;
    private String Descripcion;
    private int Cantidad;
    private double Precio;

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        Secuencial = secuencial;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public double getTotal() {
        return Math.round(Cantidad * Precio * 100.0) / 100.0;
    }
}
