/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telepeaje.archivog.excel.service;

import java.util.List;

/**
 *
 * @author AbelGTZ
 */
public interface ICatalogoService {
    
    List<String> getAll();
    List<String> getAmount(String tag,String fecha);
    
}
