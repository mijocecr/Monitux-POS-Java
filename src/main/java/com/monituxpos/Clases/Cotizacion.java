/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;


import jakarta.persistence.*;





@Entity
@Table(name = "Cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Secuencial")
    private int Secuencial;

    @Column(name = "Secuencial_Cliente")
    private int Secuencial_Cliente = 0;

    @Column(name = "Secuencial_Usuario")
    private int Secuencial_Usuario = 0;

    @Column(name = "Fecha")
    private String Fecha;

    @Column(name = "Total")
    private Double Total = 0.0;

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

    public Cotizacion() {
        // Constructor por defecto
    }

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
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

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
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
