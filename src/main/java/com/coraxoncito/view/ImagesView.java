/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coraxoncito.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author abel
 */
@ManagedBean
@ViewScoped
public class ImagesView {

    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 0; i <= 12; i++) {
//            if (i >= 3) {
//                images.add("boda" + i + ".png");
//            } else {
                images.add("abby" + i + ".jpg");
//            }
        }
    }

    public List<String> getImages() {
        return images;
    }
}
