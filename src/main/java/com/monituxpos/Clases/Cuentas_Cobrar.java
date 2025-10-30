
   package com.monituxpos.Clases;

import com.monituxpos.Clases.Cliente;
import com.monituxpos.Clases.Venta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "cuentas_cobrar")
public class Cuentas_Cobrar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    @ManyToOne
    @JoinColumn(name = "Secuencial_Factura", insertable = false, updatable = false)
    private Venta Venta;

    @ManyToOne
    @JoinColumn(name = "Secuencial_Cliente", insertable = false, updatable = false)
    private Cliente Cliente;

    @Column(name = "Secuencial_Factura")
    private int Secuencial_Factura = 0;

    @Column(name = "Secuencial_Cliente")
    private int Secuencial_Cliente = 0;

    private int Secuencial_Usuario = 0;

    // ✅ Ahora se espera que estas fechas se asignen correctamente desde el servicio o controlador
    @Column(name = "Fecha")
    private String Fecha;

    @Column(name = "Fecha_Vencimiento")
    private String Fecha_Vencimiento;

    private Double Total = 0.0;
    private Double Saldo = 0.0;
    private Double Pagado = 0.0;
    private Double Otros_Cargos = 0.0;
    private Double Descuento = 0.0;
    private Double Impuesto = 0.0;
    private Double Gran_Total = 0.0;

    private int Secuencial_Empresa;

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public Venta getVenta() {
        return Venta;
    }

    public void setVenta(Venta Venta) {
        this.Venta = Venta;
    }

    public Cliente getCliente() {
        return Cliente;
    }

    public void setCliente(Cliente Cliente) {
        this.Cliente = Cliente;
    }

    public int getSecuencial_Factura() {
        return Secuencial_Factura;
    }

    public void setSecuencial_Factura(int Secuencial_Factura) {
        this.Secuencial_Factura = Secuencial_Factura;
    }

    public int getSecuencial_Cliente() {
        return Secuencial_Cliente;
    }

    public void setSecuencial_Cliente(int Secuencial_Cliente) {
        this.Secuencial_Cliente = Secuencial_Cliente;
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