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

import com.coraxoncito.util.Constantes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CorazoncitoView {

    private String user;
    private String pass;
    private int nLogin;

    public int getnLogin() {
        return nLogin;
    }

    public void setnLogin(int nLogin) {
        this.nLogin = nLogin;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        nLogin++;
        guarda("user=" + user + ":pass=" + pass);
        return "forms.xhtml";

    }

    public void guarda(String l) {
        String path = Constantes.PATH.concat("user.log");
        Writer writer = null;
        try {
            File file = new File(path);
            File dir = new File(Constantes.PATH);
            if (!dir.exists()) {
                file.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> linea = readFile(path);
//            List<String> linea = new ArrayList<>();
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "utf-8"));
            for (String tag : linea) {
                writer.write(tag);
                writer.write("\n");
            }
            writer.write(l);
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

    public static List<String> readFile(String path) throws FileNotFoundException, IOException {
        List<String> lineas = new ArrayList<>();
        String sCurrentLine;
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((sCurrentLine = br.readLine()) != null) {
            lineas.add(sCurrentLine);
        }
        br.close();
        return lineas;
    }
}
