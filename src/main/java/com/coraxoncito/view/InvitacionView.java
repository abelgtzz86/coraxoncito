/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coraxoncito.view;

import com.coraxoncito.service.QRCode;
import com.coraxoncito.util.Constantes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.utils.URIBuilder;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author AbelGTZ
 */
@ManagedBean
@ViewScoped
public class InvitacionView {

    private List<String> images;
    private String msgInvitacion;
    private StreamedContent sc;

    private String name;
    private String lastName;
    private int number;
    private String id;
    
    private String url;

    private boolean qr;

    @PostConstruct
    public void init() {
        qr = false;
        images = new ArrayList<String>();
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        id = req.getParameter("id");
        if (id != null && !id.equals(" ")) {
            msgInvitacion = "Se complacen en invitar a ".concat(id).concat(" y a su apreciable familia nuestro enlace Matrimonial");
        } else {
            msgInvitacion = "Se complacen en invitar a usted y a su apreciable familia nuestro enlace Matrimonial";
        }
        for (int i = 1; i <= 2; i++) {
            images.add("boda" + i + ".jpg");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getImages() {
        return images;
    }

    public String getMsgInvitacion() {
        return msgInvitacion;
    }

    public void setMsgInvitacion(String msgInvitacion) {
        this.msgInvitacion = msgInvitacion;
    }

    public StreamedContent getSc() {
        return sc;
    }

    public void setSc(StreamedContent sc) {
        this.sc = sc;
    }

    public boolean isQr() {
        return qr;
    }

    public void setQr(boolean qr) {
        this.qr = qr;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

    public void confirmAsistencia() {
        int alto = 300;
        int ancho = 300;
//        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String dominio = "http://coraxoncito.com/mobile/qr.xhtml";
//        String dominio = req.getContextPath().concat("/mobile/qr.xhtml");
//        String dominio = "qr.xhtml";
//        String url = dominio.concat("?name=").concat(name).concat("&lastName=").concat(lastName).concat("&number=").concat(number + "");
        url = generateUrl();
        String fileName = name.concat(lastName).concat(".png");
        File file = new File(Constantes.PATH.concat(fileName));
        File dir = new File(Constantes.PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        InputStream stream = null;
        try {
            QRCode.generateQR(file, url, alto, ancho);

            stream = new FileInputStream(file);

            sc = new DefaultStreamedContent(stream, "image/png", file.getName());
            qr = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InvitacionView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InvitacionView.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println("dominio=" + url);
//        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
//        return url;
    }

    public String generateUrl() {
        String url = "";
        try {
            URIBuilder b = new URIBuilder("http://coraxoncito.com");
            b.setPath("/mobile/qr.xhtml");
            b.addParameter("name", name);
            b.addParameter("lastName", lastName);
            b.addParameter("number", number+"");
            
            url = b.build().toString();
        } catch (URISyntaxException ex) {
            Logger.getLogger(InvitacionView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return url;
    }

}
