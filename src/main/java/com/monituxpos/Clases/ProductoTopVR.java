/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

/**
 *
 * @author Miguel Cerrato
 */
public class ProductoTopVR {
    private int Secuencial_Producto;
    private int Secuencial_Empresa;
    private String descripcion;
    private String codigo;
    private double totalVendido;
    private double venta; // Hay que ajustar esto para incluir el Secuencial de la Empresa

    // Constructor
    public ProductoTopVR(int secuencialProducto, String descripcion, String codigo, double totalVendido, double venta,int secuencialEmpresa) {
        this.Secuencial_Producto = secuencialProducto;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.totalVendido = totalVendido;
        this.venta = venta;
        this.Secuencial_Empresa=secuencialEmpresa;
    }

    // Getters y Setters
    public int getSecuencialProducto() {
        return Secuencial_Producto;
    }

    public void setSecuencialProducto(int secuencialProducto) {
        this.Secuencial_Producto = secuencialProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public double getVenta() {
        return venta;
    }

    public void setVenta(double venta) {
        this.venta = venta;
    }
}
