/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coraxoncito.view;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class TestView {
    
    private Date aniversario;
    private Date cumple;
    private int numColor;
    private int quieres;
    private int boda;
    private int puntos;
    
    public void puntos(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        Date oct = new Date(1986, 10, 7);
        Date dic = new Date(2013, 12, 23);
        if(aniversario.equals(dic)){
            puntos+=20;
        }
        if(cumple.equals(oct)){
            puntos +=20;
        }
        if(numColor==2){
            puntos += 20;
        }
        if(quieres==100){
            puntos += 20;
        }
        if(boda==1){
            puntos += 20;
        }
        context.addMessage(null, new FacesMessage("Total de puntos", puntos+""));
        context.addMessage(null, new FacesMessage("Sorry", "No me puedes ganar por que yo te quiero al infinito y mas alla"));
    }

    public Date getAniversario() {
        return aniversario;
    }

    public void setAniversario(Date aniversario) {
        this.aniversario = aniversario;
    }

    public Date getCumple() {
        return cumple;
    }

    public void setCumple(Date cumple) {
        this.cumple = cumple;
    }

    public int getNumColor() {
        return numColor;
    }

    public void setNumColor(int numColor) {
        this.numColor = numColor;
    }

    public int getQuieres() {
        return quieres;
    }

    public void setQuieres(int quieres) {
        this.quieres = quieres;
    }

    public int getBoda() {
        return boda;
    }

    public void setBoda(int boda) {
        this.boda = boda;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
    
}
