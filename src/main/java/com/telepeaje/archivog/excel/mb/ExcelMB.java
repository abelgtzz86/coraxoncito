/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telepeaje.archivog.excel.mb;

import com.telepeaje.archivog.excel.service.ICatalogoService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author AbelGTZ
 */
@ManagedBean
@ViewScoped
public class ExcelMB implements Serializable {

    public static final String PATH = "D:/";
    private String hola;
    private UploadedFile file;
    private Integer nfilas;
    private boolean descarga;
    private StreamedContent fileOut;
    @ManagedProperty(value = "#{catalogoServiceImpl}")
    private ICatalogoService catalogoServiceImpl;

    private int ntag;
    private int nfecha;
    private int nhora;
    private int nimporte;

    private char ctag;
    private char cfecha;
    private char chora;
    private char cimporte;

    /**
     * Creates a new instance of ExcelMB
     */
    public ExcelMB() {
        hola = "";
    }

    @PostConstruct
    public void init() {
        nfilas = 0;

        ntag = 5;
        nfecha = 7;
        nhora = 8;
        nimporte = 12;
//        List<String> lst = catalogoServiceImpl.getAll();
//        for (String lst1 : lst) {
//            hola = hola.concat(lst1).concat("\n");
//        }
    }

    public void upload() throws IOException {
        if (file != null) {
            procesaFile(file.getInputstream(), file.getFileName());
            File outFile = new File(PATH.concat(file.getFileName()));
            if (outFile.exists()) {
                descarga = true;
                InputStream stream = new FileInputStream(outFile);
                fileOut = new DefaultStreamedContent(stream, "application/xls", "p".concat(file.getFileName()));
            }
        }
    }

    public void procesaFile(InputStream file, String fileName) {
        File fOut = null;
        FileOutputStream out = null;
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(file);

            fOut = new File(PATH.concat(fileName));
            out = new FileOutputStream(fOut);
            /*
             * Obtenemos la primera pestaÃ±a a la que se quiera procesar indicando el indice.
             * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
             * que nos permite recorrer cada una de las filas que contiene.
             */

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            Row row;

            HSSFCellStyle style = workbook.createCellStyle();

            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.RED.index);
            // Recorremos todas las filas para mostrar el contenido de cada celda
            while (rowIterator.hasNext()) {

                row = rowIterator.next();

                if (nfilas < 1) {
                    Iterator<Cell> cells = row.cellIterator();
                    int i = 0;
                    while (cells.hasNext()) {
                        if (row.getCell(i) != null) {
                            String titulo = row.getCell(i).getStringCellValue();
                            switch (titulo) {
                                case "Código de TAG":
                                    ntag = i;
                                    break;
                                case "Fecha de cruce":
                                    nfecha = i;
                                    break;
                                case "Hora de cruce":
                                    nhora = i;
                                    break;
                                case "Importe al 100%":
                                    nimporte = i;
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            break;
                        }
                        i++;
                    }

                } else {
                    Cell cImporte = row.getCell(nimporte);

                    String tag = row.getCell(ntag).getStringCellValue();
                    String fecha = row.getCell(nfecha).getStringCellValue().concat(row.getCell(nhora).getStringCellValue());
                    String importe = row.getCell(nimporte).getStringCellValue();

                    List<String> amunt = catalogoServiceImpl.getAmount(tag, fecha);

                    Double amountD = new Double(amunt.get(0));
                    Double importeD = new Double(importe);
                    Cell cell = row.createCell(19);

                    if (!importeD.equals(amountD)) {
                        cell.setCellValue(amountD);
                        cell.setCellStyle(style);

                        cImporte.setCellStyle(style);
                    } else {
                        cell.setCellValue(amountD);
                    }

                }
                nfilas++;
                System.out.println("filas =" + nfilas);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                workbook.write(out);
                out.close();
                // cerramos el libro excel
//                workbook.close();
                file.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getHola() {
        return hola;
    }

    public void setHola(String hola) {
        this.hola = hola;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public ICatalogoService getCatalogoServiceImpl() {
        return catalogoServiceImpl;
    }

    public void setCatalogoServiceImpl(ICatalogoService catalogoServiceImpl) {
        this.catalogoServiceImpl = catalogoServiceImpl;
    }

    public Integer getNfilas() {
        return nfilas;
    }

    public void setNfilas(Integer nfilas) {
        this.nfilas = nfilas;
    }

    public boolean isDescarga() {
        return descarga;
    }

    public void setDescarga(boolean descarga) {
        this.descarga = descarga;
    }

    public StreamedContent getFileOut() {
        return fileOut;
    }

    public void setFileOut(StreamedContent fileOut) {
        this.fileOut = fileOut;
    }

    public int getNtag() {
        return ntag;
    }

    public void setNtag(int ntag) {
        this.ntag = ntag;
    }

    public int getNfecha() {
        return nfecha;
    }

    public void setNfecha(int nfecha) {
        this.nfecha = nfecha;
    }

    public int getNhora() {
        return nhora;
    }

    public void setNhora(int nhora) {
        this.nhora = nhora;
    }

    public int getNimporte() {
        return nimporte;
    }

    public void setNimporte(int nimporte) {
        this.nimporte = nimporte;
    }

    public char getCtag() {
        return (char)('A'+ntag);
    }

    public void setCtag(char ctag) {
        this.ctag = ctag;
    }

    public char getCfecha() {
        return (char)('A'+nfecha);
    }

    public void setCfecha(char cfecha) {
        this.cfecha = cfecha;
    }

    public char getChora() {
        return (char)('A'+nhora);
    }

    public void setChora(char chora) {
        this.chora = chora;
    }

    public char getCimporte() {
        return (char)('A'+nimporte);
    }

    public void setCimporte(char cimporte) {
        this.cimporte = cimporte;
    }

}
