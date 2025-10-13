/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity



@Table(name = "ventas_detalles")
public class Venta_Detalle {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int Secuencial;


    private int Secuencial_Factura = 0;
    private int Secuencial_Cliente = 0;
    private int Secuencial_Producto = 0;
    private int Secuencial_Usuario = 0;

    private String Fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private String Codigo = ""; // Código del producto
    private String Descripcion = "";

    private double Cantidad = 0.0;
    private double Precio = 0.0;
    private double Total = 0.0;

    private String Tipo; // Puede ser Producto o Servicio

    private int Secuencial_Empresa = 0;

    public Venta_Detalle() {
        // Constructor por defecto
    }

    // Getters y Setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.Secuencial = secuencial;
    }

    public int getSecuencial_Factura() {
        return Secuencial_Factura;
    }

    public void setSecuencial_Factura(int secuencial_Factura) {
        this.Secuencial_Factura = secuencial_Factura;
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

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int secuencial_Usuario) {
        this.Secuencial_Usuario = secuencial_Usuario;
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

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        this.Total = total;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int secuencial_Empresa) {
        this.Secuencial_Empresa = secuencial_Empresa;
    }
}
