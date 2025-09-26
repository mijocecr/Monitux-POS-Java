package com.monituxpos.Clases;

import jakarta.persistence.*;

@Entity
@Table(name = "Kardex")
public class Kardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Secuencial")
    private int secuencial;

    @Column(name = "Fecha")
    private String fecha; // formato dd/MM/yyyy

    @Column(name = "Secuencial_Producto", insertable = false, updatable = false)
    private int secuencialProducto;

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
    private double costoTotal;

    @Column(name = "Venta")
    private double venta;

    @Column(name = "Venta_Total")
    private double ventaTotal;

    @Column(name = "Saldo")
    private double saldo;

    @Column(name = "Movimiento")
    private String movimiento;

    @Column(name = "Secuencial_Empresa")
    private int secuencialEmpresa;

    // Getters y setters
    public int getSecuencial() { return secuencial; }
    public void setSecuencial(int secuencial) { this.secuencial = secuencial; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public int getSecuencialProducto() { return secuencialProducto; }
    public void setSecuencialProducto(int secuencialProducto) { this.secuencialProducto = secuencialProducto; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }

    public double getVenta() { return venta; }
    public void setVenta(double venta) { this.venta = venta; }

    public double getVentaTotal() { return ventaTotal; }
    public void setVentaTotal(double ventaTotal) { this.ventaTotal = ventaTotal; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getMovimiento() { return movimiento; }
    public void setMovimiento(String movimiento) { this.movimiento = movimiento; }

    public int getSecuencialEmpresa() { return secuencialEmpresa; }
    public void setSecuencialEmpresa(int secuencialEmpresa) { this.secuencialEmpresa = secuencialEmpresa; }
}
