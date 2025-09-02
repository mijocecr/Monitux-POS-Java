/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity


@Table(name = "Cuentas_Cobrar")
public class Cuentas_Cobrar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    private int Secuencial_Factura = 0;
    private int Secuencial_Cliente = 0;
    private int Secuencial_Usuario = 0;

    private String Fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
    private String Fecha_Vencimiento;

    private Double Total = 0.0;
    private Double Saldo = 0.0;
    private Double Pagado = 0.0;
    private Double Otros_Cargos = 0.0;
    private Double Descuento = 0.0;
    private Double Impuesto = 0.0;
    private Double Gran_Total = 0.0;

    private int Secuencial_Empresa;

    // Getters y setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        Secuencial = secuencial;
    }

    public int getSecuencial_Factura() {
        return Secuencial_Factura;
    }

    public void setSecuencial_Factura(int secuencial_Factura) {
        Secuencial_Factura = secuencial_Factura;
    }

    public int getSecuencial_Cliente() {
        return Secuencial_Cliente;
    }

    public void setSecuencial_Cliente(int secuencial_Cliente) {
        Secuencial_Cliente = secuencial_Cliente;
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int secuencial_Usuario) {
        Secuencial_Usuario = secuencial_Usuario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getFecha_Vencimiento() {
        return Fecha_Vencimiento;
    }

    public void setFecha_Vencimiento(String fecha_Vencimiento) {
        Fecha_Vencimiento = fecha_Vencimiento;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

    public Double getPagado() {
        return Pagado;
    }

    public void setPagado(Double pagado) {
        Pagado = pagado;
    }

    public Double getOtros_Cargos() {
        return Otros_Cargos;
    }

    public void setOtros_Cargos(Double otros_Cargos) {
        Otros_Cargos = otros_Cargos;
    }

    public Double getDescuento() {
        return Descuento;
    }

    public void setDescuento(Double descuento) {
        Descuento = descuento;
    }

    public Double getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(Double impuesto) {
        Impuesto = impuesto;
    }

    public Double getGran_Total() {
        return Gran_Total;
    }

    public void setGran_Total(Double gran_Total) {
        Gran_Total = gran_Total;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int secuencial_Empresa) {
        Secuencial_Empresa = secuencial_Empresa;
    }
}
