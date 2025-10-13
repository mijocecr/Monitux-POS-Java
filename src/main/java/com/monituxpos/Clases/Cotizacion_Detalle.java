/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import jakarta.persistence.*;

@Entity


@Table(name = "cotizaciones_detalles")
public class Cotizacion_Detalle {

  
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "Secuencial")
private int Secuencial;

    
    private int Secuencial_Cotizacion = 0;
    private int Secuencial_Usuario = 0;
    private int Secuencial_Cliente = 0;
    private int Secuencial_Producto = 0;

    private String Fecha;
    private String Codigo = ""; // Código del producto
    private String Descripcion;

    private Double Cantidad = 0.0;
    private Double Precio = 0.0;
    private String Tipo; // Puede ser Producto o Servicio
    private Double Total = 0.0;

    private int Secuencial_Empresa;

    public Cotizacion_Detalle() {
        // Constructor por defecto
    }

    // Getters y Setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.Secuencial = secuencial;
    }

    public int getSecuencial_Cotizacion() {
        return Secuencial_Cotizacion;
    }

    public void setSecuencial_Cotizacion(int secuencial_Cotizacion) {
        this.Secuencial_Cotizacion = secuencial_Cotizacion;
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int secuencial_Usuario) {
        this.Secuencial_Usuario = secuencial_Usuario;
    }

    public int getSecuencial_Cliente() {
        return Secuencial_Cliente;
    }

    public void setSecuencial_Cliente(int secuencial_Cliente) {
        this.Secuencial_Cliente = secuencial_Cliente;
    }

    public int getSecuencial_Producto() {
        return Secuencial_Producto;
    }

    public void setSecuencial_Producto(int secuencial_Producto) {
        this.Secuencial_Producto = secuencial_Producto;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        this.Codigo = codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public Double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.Cantidad = cantidad;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        this.Precio = precio;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        this.Total = total;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int secuencial_Empresa) {
        this.Secuencial_Empresa = secuencial_Empresa;
    }
}
