/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telepeaje.archivog.bean;

import java.io.Serializable;

/**
 *
 * @author AbelGTZ
 */
public class Movimiento implements Serializable{
    
    private String concepto;
    private double saldo;

    public Movimiento(String concepto, double saldo) {
        this.concepto = concepto;
        this.saldo = saldo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    
}
