package com.monituxpos.Clases;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Kardex")
public class Kardex{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    private String Fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    @Column(name = "Secuencial_Producto")
    private int Secuencial_Producto;

    private String Descripcion;

    private double Cantidad = 0;
    private double Costo = 0;
    private double Costo_Total = 0;
    private double Venta = 0;
    private double Venta_Total = 0;
    private double Saldo = 0;

    private String Movimiento;

    @Column(name = "Secuencial_Empresa")
    private int Secuencial_Empresa;

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public int getSecuencial_Producto() {
        return Secuencial_Producto;
    }

    public void setSecuencial_Producto(int Secuencial_Producto) {
        this.Secuencial_Producto = Secuencial_Producto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getCosto() {
        return Costo;
    }

    public void setCosto(double Costo) {
        this.Costo = Costo;
    }

    public double getCosto_Total() {
        return Costo_Total;
    }

    public void setCosto_Total(double Costo_Total) {
        this.Costo_Total = Costo_Total;
    }

    public double getVenta() {
        return Venta;
    }

    public void setVenta(double venta) {
        this.Venta = venta;
    }

    public double getVenta_Total() {
        return Venta_Total;
    }

    public void setVenta_Total(double Venta_Total) {
        this.Venta_Total = Venta_Total;
    }

    public double getSaldo() {
        return Saldo;
    }

    public void setSaldo(double Saldo) {
        this.Saldo = Saldo;
    }

    public String getMovimiento() {
        return Movimiento;
    }

    public void setMovimiento(String Movimiento) {
        this.Movimiento = Movimiento;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }

  
}
