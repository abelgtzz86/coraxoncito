/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coraxoncito.view;

import com.coraxoncito.util.Constantes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import static com.coraxoncito.view.CorazoncitoView.readFile;

/**
 *
 * @author AbelGTZ
 */
@ManagedBean
@ViewScoped
public class QuestionView {

    private boolean muestraDatos;
    private Integer regalo;
    private String timeAniversarioMili;
    private String años;
    private String dias;

    private String fechaNovio;
    private String cuandoNovio;
    private String comoConocieron;
    private String hacerJuntos;

    @ManagedProperty(value = "#{invitacionView}")
    InvitacionView invitacionView;

    public InvitacionView getInvitacionView() {
        return invitacionView;
    }

    public void setInvitacionView(InvitacionView invitacionView) {
        this.invitacionView = invitacionView;
    }

    public String getFechaNovio() {
        return fechaNovio;
    }

    public void setFechaNovio(String fechaNovio) {
        this.fechaNovio = fechaNovio;
    }

    public String getCuandoNovio() {
        return cuandoNovio;
    }

    public void setCuandoNovio(String cuandoNovio) {
        this.cuandoNovio = cuandoNovio;
    }

    public String getComoConocieron() {
        return comoConocieron;
    }

    public void setComoConocieron(String comoConocieron) {
        this.comoConocieron = comoConocieron;
    }

    public String getHacerJuntos() {
        return hacerJuntos;
    }

    public void setHacerJuntos(String hacerJuntos) {
        this.hacerJuntos = hacerJuntos;
    }

    public boolean isMuestraDatos() {
        return muestraDatos;
    }

    public void setMuestraDatos(boolean muestraDatos) {
        this.muestraDatos = muestraDatos;
    }

    public Integer getRegalo() {
        return regalo;
    }

    public void setRegalo(Integer regalo) {
        this.regalo = regalo;
    }

    public String getTimeAniversarioMili() {
        return timeAniversarioMili;
    }

    public void setTimeAniversarioMili(String timeAniversarioMili) {
        this.timeAniversarioMili = timeAniversarioMili;
    }

    public String getAños() {
        return años;
    }

    public void setAños(String años) {
        this.años = años;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    @PostConstruct
    public void init() {
        muestraDatos = false;
        tiempoNovios();
    }

    public void evaluaRespuestas() throws Exception {
        muestraDatos = true;
        guarda(fechaNovio);
        guarda(cuandoNovio);
        guarda(comoConocieron);
        guarda(hacerJuntos);
        if(!"23 de diciembre".equals(fechaNovio)){
            FacesContext.getCurrentInstance().addMessage("the:cuestionarioForm:primera", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Nos hicimos novios el 23 de diciembre despues de un poquito mas de 6 meses de conocernos"));
        }if(!"Suegros".equals(comoConocieron)){
            FacesContext.getCurrentInstance().addMessage("the:cuestionarioForm:tercera", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Nuestros padres tienen muy buena amistad y nos presentaron"));
        }
        if (regalo == 1) {
            guarda("Si");
        } else {
            guarda("No");
            FacesContext.getCurrentInstance().addMessage("the:cuestionarioForm:regalo", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Si no es de liverpool por lo menos de sears o ya de perdis de tepito"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Si no es de liverpool por lo menos de sears", " o ya de perdis de tepito"));
        }
    }

    public void tiempoNovios() {
        try {
            Date aniversario = new Date();
            Date hoy = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            aniversario = sdf.parse("23/12/2013");
            long result = (hoy.getTime() - aniversario.getTime());

            DecimalFormat myFormatter = new DecimalFormat("###,###,###");

            timeAniversarioMili = myFormatter.format(result / 1000) + " Segundos";
            long year = ((long) 365 * 24) * 60 * 60 * 1000;
            int anios = (int) (result / year);
            Long diasM = (result - (anios * year));
            long dia = (long) 24 * 60 * 60 * 1000;
            años = anios + " años " + diasM / dia + " dias";
            dias = result / dia + " dias";
        } catch (ParseException ex) {
            Logger.getLogger(QuestionView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guarda(String l) {
        String id = invitacionView.getId();
        String name;
        if (id != null) {
            name = id;
        } else {
            name = "anonimo";
        }
        String filePath = Constantes.PATH.concat("respuestas.txt");
        Writer writer = null;
        try {
            File file = new File(filePath);
            File dir = new File(Constantes.PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> linea = readFile(filePath);
//            List<String> linea = new ArrayList<>();
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "utf-8"));
            for (String tag : linea) {
                writer.write(tag);
                writer.write("\n");
            }
            writer.write(name.concat(":").concat(l));
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
