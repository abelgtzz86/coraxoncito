/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coraxoncito.view;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.utils.URIBuilder;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author AbelGTZ
 */
@ManagedBean(name = "qrView")
@ViewScoped
public class QRView {

    private StreamedContent sc;

    private String name;
    private String lastName;
    private String number;
    private String url;

    @PostConstruct
    public void init() {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            name = req.getParameter("name");
            lastName = req.getParameter("lastName");
            number = req.getParameter("number");
            generateUrl();
        } catch (URISyntaxException ex) {
            Logger.getLogger(QRView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateUrl() throws URISyntaxException {
        url = "";
        URIBuilder b = new URIBuilder("http://coraxoncito.com");
        b.setPath("/mobile/qr.xhtml");
        b.addParameter("name", name);
        b.addParameter("lastName", lastName);
        b.addParameter("number", number + "");

        url = b.build().toString();

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public StreamedContent getSc() {
        return sc;
    }

    public void setSc(StreamedContent sc) {
        this.sc = sc;
    }

}
