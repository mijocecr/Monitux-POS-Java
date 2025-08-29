/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;


import jakarta.persistence.*;

@Entity
public class Cotizacion {

    @Id
    private int Secuencial;

    private int Secuencial_Cliente = 0;
    private int Secuencial_Usuario = 0;

    private String Fecha;

    private Double Total = 0.0;
    private Double Otros_Cargos = 0.0;
    private Double Descuento = 0.0;
    private Double Impuesto = 0.0;
    private Double Gran_Total = 0.0;

    private int Secuencial_Empresa;

    public Cotizacion() {
        // Constructor por defecto
    }

    // Getters y Setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.Secuencial = secuencial;
    }

    public int getSecuencial_Cliente() {
        return Secuencial_Cliente;
    }

    public void setSecuencial_Cliente(int secuencial_Cliente) {
        this.Secuencial_Cliente = secuencial_Cliente;
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int secuencial_Usuario) {
        this.Secuencial_Usuario = secuencial_Usuario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        this.Total = total;
    }

    public Double getOtros_Cargos() {
        return Otros_Cargos;
    }

    public void setOtros_Cargos(Double otros_Cargos) {
        this.Otros_Cargos = otros_Cargos;
    }

    public Double getDescuento() {
        return Descuento;
    }

    public void setDescuento(Double descuento) {
        this.Descuento = descuento;
    }

    public Double getImpuesto() {
        return Impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.Impuesto = impuesto;
    }

    public Double getGran_Total() {
        return Gran_Total;
    }

    public void setGran_Total(Double gran_Total) {
        this.Gran_Total = gran_Total;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int secuencial_Empresa) {
        this.Secuencial_Empresa = secuencial_Empresa;
    }
}
