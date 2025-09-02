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


@Table(name = "Ingresos")
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Secuencial;

    private Integer Secuencial_Factura = 0;
    private int Secuencial_Usuario = 0;
    private String Fecha = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
    private String Tipo_Ingreso = "";
    private String Descripcion = "";
    private double Total = 0.0;

    private int Secuencial_Empresa;

    // Getters y setters
    public int getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(int secuencial) {
        Secuencial = secuencial;
    }

    public Integer getSecuencial_Factura() {
        return Secuencial_Factura;
    }

    public void setSecuencial_Factura(Integer secuencial_Factura) {
        Secuencial_Factura = secuencial_Factura;
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

    public String getTipo_Ingreso() {
        return Tipo_Ingreso;
    }

    public void setTipo_Ingreso(String tipo_Ingreso) {
        Tipo_Ingreso = tipo_Ingreso;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public int getSecuencial_Empresa() {
        return Secuencial_Empresa;
    }

    public void setSecuencial_Empresa(int secuencial_Empresa) {
        Secuencial_Empresa = secuencial_Empresa;
    }
}
