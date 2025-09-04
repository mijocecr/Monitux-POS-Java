/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monituxpos.Clases;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Egresos")
public class Egreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    private Integer Secuencial_Factura = 0;
    private int Secuencial_Usuario = 0;

    private String Fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private String Tipo_Egreso = "";
    private String Descripcion = "";
    private double Total = 0.0;

    private int Secuencial_Empresa;

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

    public String getTipo_Egreso() {
        return Tipo_Egreso;
    }

    public void setTipo_Egreso(String Tipo_Egreso) {
        this.Tipo_Egreso = Tipo_Egreso;
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
