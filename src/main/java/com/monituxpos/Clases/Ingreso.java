
package com.monituxpos.Clases;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Ingresos")
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    @ManyToOne
    @JoinColumn(name = "Secuencial_Usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Secuencial_Factura", insertable = false, updatable = false)
    private Venta venta;

 @Column(name = "Secuencial_Factura", nullable = true)
private Integer Secuencial_Factura;


    @Column(name = "Secuencial_Usuario")
    private int Secuencial_Usuario = 0;

    @Column(name = "Fecha")
    private String Fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    @Column(name = "Tipo_Ingreso")
    private String Tipo_Ingreso = "";

    @Column(name = "Descripcion")
    private String Descripcion = "";

    @Column(name = "Total")
    private double Total = 0.0;

    @Column(name = "Secuencial_Empresa")
    private int Secuencial_Empresa;

    // Getters y setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public Integer getSecuencial_Factura() {
        return Secuencial_Factura;
    }

    public void setSecuencial_Factura(Integer Secuencial_Factura) {
        this.Secuencial_Factura = Secuencial_Factura;
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int Secuencial_Usuario) {
        this.Secuencial_Usuario = Secuencial_Usuario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String getTipo_Ingreso() {
        return Tipo_Ingreso;
    }

    public void setTipo_Ingreso(String Tipo_Ingreso) {
        this.Tipo_Ingreso = Tipo_Ingreso;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }
}
