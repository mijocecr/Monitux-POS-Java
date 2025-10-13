/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "abonos_ventas")
public class Abono_Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    @Column(name = "Secuencial_CTAC")
    private int Secuencial_CTAC;

    @Column(name = "Secuencial_Usuario")
    private int Secuencial_Usuario;

    @Column(name = "Secuencial_Cliente")
    private int Secuencial_Cliente;

    @Column(name = "Fecha")
    private String Fecha;

    @Column(name = "Monto")
    private double Monto = 0;

    @Column(name = "Secuencial_Empresa")
    private int Secuencial_Empresa;

    public Abono_Venta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.Fecha = LocalDateTime.now().format(formatter);
    }

    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int Secuencial) {
        this.Secuencial = Secuencial;
    }

    public int getSecuencial_CTAC() {
        return Secuencial_CTAC;
    }

    public void setSecuencial_CTAC(int Secuencial_CTAC) {
        this.Secuencial_CTAC = Secuencial_CTAC;
    }

    public int getSecuencial_Usuario() {
        return Secuencial_Usuario;
    }

    public void setSecuencial_Usuario(int Secuencial_Usuario) {
        this.Secuencial_Usuario = Secuencial_Usuario;
    }

    public int getSecuencial_Cliente() {
        return Secuencial_Cliente;
    }

    public void setSecuencial_Cliente(int Secuencial_Cliente) {
        this.Secuencial_Cliente = Secuencial_Cliente;
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
