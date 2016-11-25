package com.alphacell.util.reporte;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashSet;

/**
 * Created by luis.cevallos on 27/4/2016.
 */
public class FormatoExcelPoi {

    public static void formatearArchivoExcel(Object document, HashSet<Integer> columnasNumero)
    {

        HSSFWorkbook book = (HSSFWorkbook) document;
        HSSFSheet sheet = book.getSheetAt(0);

        HSSFCellStyle intStyle = book.createCellStyle();
        intStyle.setDataFormat((short) 1);

        HSSFCellStyle decStyle = book.createCellStyle();
        decStyle.setDataFormat((short) 2);

        HSSFCellStyle dollarStyle = book.createCellStyle();
        dollarStyle.setDataFormat((short) 5);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; //Aqui me encargo de que no coja la fila de los nombres
            }

            for (Cell cell : row) {
                int numeroColumna = cell.getColumnIndex();
                 if (columnasNumero.contains(numeroColumna))
                        continue;

                String strVal = cell.getStringCellValue();

                if (strVal.isEmpty()|| strVal==null)
                    continue;


                cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

                try
                {
                         /*
                            if(InetAddress.getLocalHost().getHostName().equals("WSCEVALLOS"))
                            {
                                //TODO: ESTO FUNCIONA SOLO EN MI MAQUINA
                                strVal = strVal.replace(".", "");
                                strVal = strVal.replace(",", ".");
                            }
                            else{
                                //TODO: PARA EL SERVIDOR YA QUE EL SERVIDOR 1,089,544.6 coloca al reves las cosas
                                strVal = strVal.replace(",", "");
                            }
                            */
                    strVal = strVal.replace(".", "");
                    strVal = strVal.replace(",", ".");

                            if (strVal.indexOf('.') == -1) {
                                //integer
                                //numStyle.setDataFormat((short)1);
                                int intVal = Integer.valueOf(strVal);

                                cell.setCellStyle(intStyle);
                                cell.setCellValue(intVal);
                            } else {
                                //double
                                if (strVal.startsWith("$") || strVal.startsWith("-$")) {
                                    strVal = strVal.replace("$", StringUtils.EMPTY);
                                    //numStyle.setDataFormat((short)5);
                                    cell.setCellStyle(dollarStyle);
                                }

                                else {
                                    //numStyle.setDataFormat((short)2);
                                    cell.setCellStyle(decStyle);
                                }

                                double dblVal = Double.parseDouble(strVal);
                                //BigDecimal dblVal = BigDecimal.valueOf(strVal);
                                //cell.setCellValue( new BigDecimal(strVal).doubleValue());
                                cell.setCellValue(dblVal);
                            }

                }catch (Exception e)
                {
                    e.printStackTrace();

                }
            }
        }


    }
}
