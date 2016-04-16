/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telepeaje.archivog.excel.repository;

import java.util.List;

/**
 *
 * @author AbelGTZ
 */
public interface ICatalogo {
    
    public List<String> catalogo(String tag,String fecha);
    
}
