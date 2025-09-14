/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Abonos_Compras")
public class Abono_Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    @Column(name = "Secuencial_CTAP")
    private int Secuencial_CTAP;

    @Column(name = "Secuencial_Usuario")
    private int Secuencial_Usuario;

    @Column(name = "Secuencial_Proveedor")
    private int Secuencial_Proveedor;

    @Column(name = "Fecha")
    private String Fecha;

    @Column(name = "Monto")
    private double Monto = 0;

    @Column(name = "Secuencial_Empresa")
    private int Secuencial_Empresa;

    public Abono_Compra() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.Fecha = LocalDateTime.now().format(formatter);
    }

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public int getSecuencial_CTAP() {
        return Secuencial_CTAP;
    }

    public void setSecuencial_CTAP(int Secuencial_CTAP) {
        this.Secuencial_CTAP = Secuencial_CTAP;
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int Secuencial_Usuario) {
        this.Secuencial_Usuario = Secuencial_Usuario;
    }

    public int getSecuencial_Proveedor() {
        return Secuencial_Proveedor;
    }

    public void setSecuencial_Proveedor(int Secuencial_Proveedor) {
        this.Secuencial_Proveedor = Secuencial_Proveedor;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public double getMonto() {
        return Monto;
    }

    public void setMonto(double Monto) {
        this.Monto = Monto;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int Secuencial_Empresa) {
        this.Secuencial_Empresa = Secuencial_Empresa;
    }

    
    
    
    
    
    
}
