package com.monituxpos.Clases;


import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    @Column(name = "Secuencial_Proveedor")
    private int Secuencial_Proveedor;

    private String Codigo;
    private String Descripcion;

    @Column(nullable = false)
    private double Cantidad = 0;

    @Column(name = "Precio_Costo", nullable = false)
    private double Precio_Costo = 0;

    @Column(name = "Precio_Venta", nullable = false)
    private double Precio_Venta = 0;

    private String Marca;
    private String Codigo_Barra;
    private String Codigo_Fabricante;

 
@Column(name = "Imagen")
private byte[] Imagen;

    @Column(name = "Fecha_Caducidad")
    private String Fecha_Caducidad;

    private String Tipo = "Producto";

    @Column(name = "Secuencial_Categoria")
    private int Secuencial_Categoria;

    private boolean Expira = false;

    @Column(name = "Existencia_Minima")
    private double Existencia_Minima;

    @Column(name = "Secuencial_Empresa", nullable = false)
    private int Secuencial_Empresa;

    // Constructores

    public Producto() {}

    public Producto(int secuencial, int secuencialProveedor, String codigo, String descripcion,
                    double cantidad, double precioCosto, double precioVenta, String marca,
                    String codigoBarra, String codigoFabricante, byte[] imagen,
                    int secuencialCategoria, String fechaCaducidad, boolean expira,
                    String tipo, int secuencialEmpresa) {

        this.Secuencial = secuencial;
        this.Secuencial_Proveedor = secuencialProveedor;
        this.Codigo = codigo;
        this.Descripcion = descripcion;
        this.Cantidad = cantidad;
        this.Precio_Costo = precioCosto;
        this.Precio_Venta = precioVenta;
        this.Marca = marca;
        this.Codigo_Barra = codigoBarra;
        this.Codigo_Fabricante = codigoFabricante;
        this.Imagen = imagen;
        this.Secuencial_Categoria = secuencialCategoria;
        this.Fecha_Caducidad = fechaCaducidad;
        this.Expira = expira;
        this.Tipo = tipo;
        this.Secuencial_Empresa = secuencialEmpresa;
    }

    // Métodos auxiliares

    public void setProducto(Producto producto) {
        this.Secuencial = producto.Secuencial;
        this.Secuencial_Proveedor = producto.Secuencial_Proveedor;
        this.Codigo = producto.Codigo;
        this.Descripcion = producto.Descripcion;
        this.Cantidad = producto.Cantidad;
        this.Precio_Costo = producto.Precio_Costo;
        this.Precio_Venta = producto.Precio_Venta;
        this.Marca = producto.Marca;
        this.Codigo_Barra = producto.Codigo_Barra;
        this.Codigo_Fabricante = producto.Codigo_Fabricante;
        this.Imagen = producto.Imagen;
        this.Secuencial_Categoria = producto.Secuencial_Categoria;
        this.Fecha_Caducidad = producto.Fecha_Caducidad;
        this.Expira = producto.Expira;
        this.Tipo = producto.Tipo;
        this.Existencia_Minima = producto.Existencia_Minima;
        this.Secuencial_Empresa = producto.Secuencial_Empresa;
    }

    public Producto getProducto() {
        return new Producto(
                this.Secuencial,
                this.Secuencial_Proveedor,
                this.Codigo,
                this.Descripcion,
                this.Cantidad,
                this.Precio_Costo,
                this.Precio_Venta,
                this.Marca,
                this.Codigo_Barra,
                this.Codigo_Fabricante,
                this.Imagen,
                this.Secuencial_Categoria,
                this.Fecha_Caducidad,
                this.Expira,
                this.Tipo,
                this.Secuencial_Empresa
        );
    }

    
    // Getters y setters (puedo generarlos si los necesitas)

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.Secuencial = secuencial;
    }

    public int getSecuencial_Proveedor() {
        return Secuencial_Proveedor;
    }

    public void setSecuencial_Proveedor(int Secuencial_Proveedor) {
        this.Secuencial_Proveedor = Secuencial_Proveedor;
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

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        this.Cantidad = cantidad;
    }

    public double getPrecio_Costo() {
        return Precio_Costo;
    }

    public void setPrecio_Costo(double Precio_Costo) {
        this.Precio_Costo = Precio_Costo;
    }

    public double getPrecio_Venta() {
        return Precio_Venta;
    }

    public void setPrecio_Venta(double Precio_Venta) {
        this.Precio_Venta = Precio_Venta;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        this.Marca = marca;
    }

    public String getCodigo_Barra() {
        return Codigo_Barra;
    }

    public void setCodigo_Barra(String Codigo_Barra) {
        this.Codigo_Barra = Codigo_Barra;
    }

    public String getCodigo_Fabricante() {
        return Codigo_Fabricante;
    }

    public void setCodigo_Fabricante(String Codigo_Fabricante) {
        this.Codigo_Fabricante = Codigo_Fabricante;
    }

    public byte[] getImagen() {
        return Imagen;
    }

    public void setImagen(byte[] imagen) {
        this.Imagen = imagen;
    }

    public String getFecha_Caducidad() {
        return Fecha_Caducidad;
    }

    public void setFecha_Caducidad(String Fecha_Caducidad) {
        this.Fecha_Caducidad = Fecha_Caducidad;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        this.Tipo = tipo;
    }

    public int getSecuencial_Categoria() {
        return Secuencial_Categoria;
    }

    public void setSecuencial_Categoria(int Secuencial_Categoria) {
        this.Secuencial_Categoria = Secuencial_Categoria;
    }

    public boolean isExpira() {
        return Expira;
    }

    public void setExpira(boolean expira) {
        this.Expira = expira;
    }

    public double getExistencia_Minima() {
        return Existencia_Minima;
    }

    public void setExistencia_Minima(double Existencia_Minima) {
        this.Existencia_Minima = Existencia_Minima;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }
    
    
    
    
}
