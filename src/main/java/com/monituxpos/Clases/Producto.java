package com.monituxpos.Clases;


import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int secuencial;

    @Column(name = "secuencial_proveedor")
    private int secuencialProveedor;

    private String codigo;
    private String descripcion;

    @Column(nullable = false)
    private double cantidad = 0;

    @Column(name = "precio_costo", nullable = false)
    private double precioCosto = 0;

    @Column(name = "precio_venta", nullable = false)
    private double precioVenta = 0;

    private String marca;
    private String codigoBarra;
    private String codigoFabricante;

    @Lob
    private byte[] imagen;

    @Column(name = "fecha_caducidad")
    private String fechaCaducidad;

    private String tipo = "Producto";

    @Column(name = "secuencial_categoria")
    private int secuencialCategoria;

    private boolean expira = false;

    @Column(name = "existencia_minima")
    private double existenciaMinima;

    @Column(name = "secuencial_empresa", nullable = false)
    private int secuencialEmpresa;

    // Constructores

    public Producto() {}

    public Producto(int secuencial, int secuencialProveedor, String codigo, String descripcion,
                    double cantidad, double precioCosto, double precioVenta, String marca,
                    String codigoBarra, String codigoFabricante, byte[] imagen,
                    int secuencialCategoria, String fechaCaducidad, boolean expira,
                    String tipo, int secuencialEmpresa) {

        this.secuencial = secuencial;
        this.secuencialProveedor = secuencialProveedor;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioCosto = precioCosto;
        this.precioVenta = precioVenta;
        this.marca = marca;
        this.codigoBarra = codigoBarra;
        this.codigoFabricante = codigoFabricante;
        this.imagen = imagen;
        this.secuencialCategoria = secuencialCategoria;
        this.fechaCaducidad = fechaCaducidad;
        this.expira = expira;
        this.tipo = tipo;
        this.secuencialEmpresa = secuencialEmpresa;
    }

    // Métodos auxiliares

    public void setProducto(Producto producto) {
        this.secuencial = producto.secuencial;
        this.secuencialProveedor = producto.secuencialProveedor;
        this.codigo = producto.codigo;
        this.descripcion = producto.descripcion;
        this.cantidad = producto.cantidad;
        this.precioCosto = producto.precioCosto;
        this.precioVenta = producto.precioVenta;
        this.marca = producto.marca;
        this.codigoBarra = producto.codigoBarra;
        this.codigoFabricante = producto.codigoFabricante;
        this.imagen = producto.imagen;
        this.secuencialCategoria = producto.secuencialCategoria;
        this.fechaCaducidad = producto.fechaCaducidad;
        this.expira = producto.expira;
        this.tipo = producto.tipo;
        this.existenciaMinima = producto.existenciaMinima;
        this.secuencialEmpresa = producto.secuencialEmpresa;
    }

    public Producto getProducto() {
        return new Producto(
                this.secuencial,
                this.secuencialProveedor,
                this.codigo,
                this.descripcion,
                this.cantidad,
                this.precioCosto,
                this.precioVenta,
                this.marca,
                this.codigoBarra,
                this.codigoFabricante,
                this.imagen,
                this.secuencialCategoria,
                this.fechaCaducidad,
                this.expira,
                this.tipo,
                this.secuencialEmpresa
        );
    }

    // Getters y setters (puedo generarlos si los necesitas)
}
