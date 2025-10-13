package com.monituxpos.Clases;

import jakarta.persistence.*;

@Entity
@Table(name = "kardex")
public class Kardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Secuencial")
    private int secuencial;

    @Column(name = "Fecha")
    private String fecha; // formato dd/MM/yyyy

    @Column(name = "Secuencial_Producto", insertable = false, updatable = false)
    private int Secuencial_Producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Secuencial_Producto")
    private Producto producto;

    @Column(name = "Descripcion")
    private String descripcion;

    @Column(name = "Cantidad")
    private double cantidad;

    @Column(name = "Costo")
    private double costo;

    @Column(name = "Costo_Total")
    private double Costo_Total;

    @Column(name = "Venta")
    private double venta;

    @Column(name = "Venta_Total")
    private double Venta_Total;

    @Column(name = "Saldo")
    private double saldo;

    @Column(name = "Movimiento")
    private String movimiento;

    @Column(name = "Secuencial_Empresa")
    private int Secuencial_Empresa;

    // Getters y setters
    public int getSecuencial() { return secuencial; }
    public void setSecuencial(int secuencial) { this.secuencial = secuencial; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public int getSecuencial_Producto() { return Secuencial_Producto; }
    public void setSecuencial_Producto(int Secuencial_Producto) { this.Secuencial_Producto = Secuencial_Producto; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public double getCosto_Total() { return Costo_Total; }
    public void setCosto_Total(double Costo_Total) { this.Costo_Total = Costo_Total; }

    public double getVenta() { return venta; }
    public void setVenta(double venta) { this.venta = venta; }

    public double getVenta_Total() { return Venta_Total; }
    public void setVenta_Total(double Venta_Total) { this.Venta_Total = Venta_Total; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getMovimiento() { return movimiento; }
    public void setMovimiento(String movimiento) { this.movimiento = movimiento; }

    public int getSecuencial_Empresa() { return Secuencial_Empresa; }
    public void setSecuencial_Empresa(int Secuencial_Empresa) { this.Secuencial_Empresa = Secuencial_Empresa; }
}
