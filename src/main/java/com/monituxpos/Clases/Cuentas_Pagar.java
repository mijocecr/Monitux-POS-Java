package com.monituxpos.Clases;

import com.monituxpos.Clases.Proveedor;
import com.monituxpos.Clases.Compra;
import jakarta.persistence.*;

@Entity
@Table(name = "cuentas_pagar")
public class Cuentas_Pagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    @ManyToOne
    @JoinColumn(name = "Secuencial_Factura", insertable = false, updatable = false)
    private Compra Compra;

    @ManyToOne
    @JoinColumn(name = "Secuencial_Proveedor", insertable = false, updatable = false)
    private Proveedor Proveedor;

    @Column(name = "Secuencial_Factura")
    private int Secuencial_Factura = 0;

    @Column(name = "Secuencial_Proveedor")
    private int Secuencial_Proveedor = 0;

    @Column(name = "Secuencial_Usuario")
    private int Secuencial_Usuario = 0;

    @Column(name = "Fecha")
    private String Fecha;

    @Column(name = "Fecha_Vencimiento")
    private String Fecha_Vencimiento;

    @Column(name = "Total")
    private Double Total = 0.0;

    @Column(name = "Saldo")
    private Double Saldo = 0.0;

    @Column(name = "Pagado")
    private Double Pagado = 0.0;

    @Column(name = "Otros_Cargos")
    private Double Otros_Cargos = 0.0;

    @Column(name = "Descuento")
    private Double Descuento = 0.0;

    @Column(name = "Impuesto")
    private Double Impuesto = 0.0;

    @Column(name = "Gran_Total")
    private Double Gran_Total = 0.0;

    @Column(name = "Secuencial_Empresa")
    private int Secuencial_Empresa;

    // Getters y setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public Compra getCompra() {
        return Compra;
    }

    public void setCompra(Compra Compra) {
        this.Compra = Compra;
    }

    public Proveedor getProveedor() {
        return Proveedor;
    }

    public void setProveedor(Proveedor Proveedor) {
        this.Proveedor = Proveedor;
    }

    public int getSecuencial_Factura() {
        return Secuencial_Factura;
    }

    public void setSecuencial_Factura(int Secuencial_Factura) {
        this.Secuencial_Factura = Secuencial_Factura;
    }

    public int getSecuencial_Proveedor() {
        return Secuencial_Proveedor;
    }

    public void setSecuencial_Proveedor(int Secuencial_Proveedor) {
        this.Secuencial_Proveedor = Secuencial_Proveedor;
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

    public String getFecha_Vencimiento() {
        return Fecha_Vencimiento;
    }

    public void setFecha_Vencimiento(String Fecha_Vencimiento) {
        this.Fecha_Vencimiento = Fecha_Vencimiento;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double Saldo) {
        this.Saldo = Saldo;
    }

    public Double getPagado() {
        return Pagado;
    }

    public void setPagado(Double Pagado) {
        this.Pagado = Pagado;
    }

    public Double getOtros_Cargos() {
        return Otros_Cargos;
    }

    public void setOtros_Cargos(Double Otros_Cargos) {
        this.Otros_Cargos = Otros_Cargos;
    }

    public Double getDescuento() {
        return Descuento;
    }

    public void setDescuento(Double Descuento) {
        this.Descuento = Descuento;
    }

    public Double getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(Double Impuesto) {
        this.Impuesto = Impuesto;
    }

    public Double getGran_Total() {
        return Gran_Total;
    }

    public void setGran_Total(Double Gran_Total) {
        this.Gran_Total = Gran_Total;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }
}
