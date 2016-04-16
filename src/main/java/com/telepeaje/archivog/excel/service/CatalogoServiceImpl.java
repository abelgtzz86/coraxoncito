/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telepeaje.archivog.excel.service;

import com.telepeaje.archivog.excel.repository.ICatalogo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "catalogoServiceImpl")
@Transactional(readOnly = true)
public class CatalogoServiceImpl implements ICatalogoService {

    @Autowired
    private ICatalogo catalgoImpl;

    @Override
    public List<String> getAll() {
        FileInputStream file = null;
        File fOut = null;
        FileOutputStream out = null;
        HSSFWorkbook workbook = null;
        try {
            file = new FileInputStream(new File("C:\\Users\\AbelGTZ\\Documents\\g02D57022.67762.005\\g02D57022.67762.005.xls"));
            workbook = new HSSFWorkbook(file);
            
            fOut = new File("C:\\Users\\AbelGTZ\\Documents\\g02D57022.67762.005\\gdos.xls");
            out = new FileOutputStream(fOut);
            /*
             * Obtenemos la primera pestaÃ±a a la que se quiera procesar indicando el indice.
             * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator
             * que nos permite recorrer cada una de las filas que contiene.
             */

            HSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            Row row;
            int n = 0;

            HSSFCellStyle style = workbook.createCellStyle();

            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.RED.index);
            // Recorremos todas las filas para mostrar el contenido de cada celda
            while (rowIterator.hasNext()) {

                row = rowIterator.next();

                // Obtenemos el iterator que permite recorres todas las celdas de una fila
                Iterator<Cell> cellIterator = row.cellIterator();

                Cell cImporte = row.getCell(12);

                printCell(row.getCell(5));
                printCell(row.getCell(7));
                printCell(row.getCell(8));
                printCell(cImporte);

                String tag = row.getCell(5).getStringCellValue();
                String fecha = row.getCell(7).getStringCellValue().concat(row.getCell(8).getStringCellValue());
                String importe = row.getCell(12).getStringCellValue();

                if (n > 0) {
                    List<String> amunt = catalgoImpl.catalogo(tag, fecha);

                    System.out.println("amount size= " + amunt.size());

                    Double amountD = new Double(amunt.get(0));
                    Double importeD = new Double(importe);
                    Cell cell = row.createCell(19);

                    if (!importeD.equals(amountD)) {
                        cell.setCellValue("Error!!!");
                        cell.setCellStyle(style);

                        cImporte.setCellStyle(style);
                    } else {
                        cell.setCellValue(amountD);
                    }

                }
                n++;
                System.out.println("fila = "+n);
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
        return catalgoImpl.catalogo("example", "");
    }

    public ICatalogo getCatalgoImpl() {
        return catalgoImpl;
    }
    
    @Override
    public List<String> getAmount(String tag,String fecha){
        return catalgoImpl.catalogo(tag, fecha);
    }

    public void setCatalgoImpl(ICatalogo catalgoImpl) {
        this.catalgoImpl = catalgoImpl;
    }

    public void printCell(Cell celda) {
        // Dependiendo del formato de la celda el valor se debe mostrar como String, Fecha, boolean, entero...
        switch (celda.getCellType()) {

            case Cell.CELL_TYPE_NUMERIC:

                if (HSSFDateUtil.isCellDateFormatted(celda)) {
                    System.out.println(celda.getDateCellValue());
                } else {
                    System.out.println(celda.getNumericCellValue());
                }
                System.out.println(celda.getNumericCellValue());
                break;

            case Cell.CELL_TYPE_STRING:
                System.out.println(celda.getStringCellValue());
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                System.out.println(celda.getBooleanCellValue());
                break;

        }
    }

}
