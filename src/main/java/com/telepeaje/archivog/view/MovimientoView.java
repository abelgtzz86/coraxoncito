/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telepeaje.archivog.view;

import com.telepeaje.archivog.bean.Movimiento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author AbelGTZ
 */
@ManagedBean
@ViewScoped
public class MovimientoView {
    
    private List<Movimiento> abonos;
    private Date fecha;
    private double total;
    private int cuenta;
    private int banco;
    
    private double saldoAtenrior;
    private String concepto;
    private double importe;

    /**
     * Creates a new instance of MovimientoView
     */
    public MovimientoView() {
    }
    
    @PostConstruct
    public void init(){
        abonos = new ArrayList<Movimiento>();
        abonos.add(new Movimiento("dep. clientes", 16638248.49));
        abonos.add(new Movimiento("REGRESO DE INVERSION", 57334053.2));
        abonos.add(new Movimiento("INTERES DE LA INVERSION",  31135.57));
        abonos.add(new Movimiento("TRASPASO CTA 6524",  5759132.83));
        
        abonos.add(new Movimiento("INVERSION", -83793217.36));
        abonos.add(new Movimiento("TRASPASO CTA 6524 ", 0.0));
        
        fecha = new Date();
        
        saldoAtenrior =4050647.27;
    }
    
    public void addMov(){
        abonos.add(new Movimiento(concepto,importe));
    }

    public List<Movimiento> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<Movimiento> abonos) {
        this.abonos = abonos;
    }

    public double getTotal() {
        total = saldoAtenrior;
        for (Movimiento abono : abonos) {
            total = total+abono.getSaldo();
        }
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getSaldoAtenrior() {
        return saldoAtenrior;
    }

    public void setSaldoAtenrior(double saldoAtenrior) {
        this.saldoAtenrior = saldoAtenrior;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }

    public int getBanco() {
        return banco;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }
    
}
