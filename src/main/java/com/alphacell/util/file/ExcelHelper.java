package com.alphacell.util.file;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;


/**
 * Created by luis on 27/10/16.
 */
public class ExcelHelper {

    private List<String> fieldNames = new ArrayList<>();
    private Workbook workbook = null;
    private String workbookName = "";


    public ExcelHelper(String workbookName) {
        this.workbookName = workbookName;
        initialize();
    }

    private void initialize() {
        setWorkbook(new HSSFWorkbook());
    }

    public void closeWorkSheet() {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(getWorkbookName());
            getWorkbook().write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean setupFieldsForClass(Class<?> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fieldNames.add(fields[i].getName());
        }
        return true;
    }


    private Sheet getSheetWithName(String paquete,String name) {
        Sheet sheet = null;
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            if (name.compareTo(paquete+workbook.getSheetName(i)) == 0) {
                sheet = workbook.getSheetAt(i);
                break;
            }

        }
        return sheet;
    }

    private void initializeForRead() throws InvalidFormatException, IOException {
        InputStream inp = new FileInputStream(getWorkbookName());
        workbook = WorkbookFactory.create(inp);
    }


    /***
     *
     * @param paquete Es el nombre del paquete del que pertenece la clase
     * @param classname Es el nombre de la clase que debe de estar en el nombre del sheet
     * @param <T>
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> readData(String paquete,String classname) throws Exception {
        this.initializeForRead();
        Sheet sheet = getSheetWithName(paquete,classname);
       // sheet.getPhysicalNumberOfRows();
        Class clazz = Class.forName(paquete+workbook.getSheetName(0));
        setupFieldsForClass(clazz);
        List<T> result = new ArrayList<T>();
        Row row;
        for (int rowCount = 1; rowCount < sheet.getLastRowNum()+1; rowCount++) {
            T one = (T) clazz.newInstance();
            row = sheet.getRow(rowCount);
            int colCount = 0;
            result.add(one);
            for (Cell cell : row) {
                int type = cell.getCellType();
                String fieldName = fieldNames.get(colCount++);
                Method method = constructMethod(clazz, fieldName);
                if (type == Cell.CELL_TYPE_STRING) {
                    String value = cell.getStringCellValue();
                    Object[] values = new Object[1];
                    values[0] = value;
                    method.invoke(one, values);
                } else if (type == Cell.CELL_TYPE_NUMERIC) {
                    Double num = cell.getNumericCellValue();
                    Class<?> returnType = getGetterReturnClass(clazz, fieldName);
                    if (returnType == int.class || returnType == Integer.class) {
                        method.invoke(one, num.intValue());
                    } else if (returnType == double.class || returnType == Double.class) {
                        method.invoke(one, num);
                    } else if (returnType == float.class || returnType == Float.class) {
                        method.invoke(one, num.floatValue());
                    } else if (returnType == long.class || returnType == Long.class) {
                        method.invoke(one, num.longValue());
                    } else if (returnType == short.class || returnType == Short.class) {
                        method.invoke(one, num.shortValue());
                    } else if (returnType == BigDecimal.class) {
                        method.invoke(one, BigDecimal.valueOf(num));
                    }else if (returnType == Date.class) {

                        Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        method.invoke(one, date);
                    }

                } else if (type == Cell.CELL_TYPE_BOOLEAN) {
                    boolean num = cell.getBooleanCellValue();
                    Object[] values = new Object[1];
                    values[0] = num;
                    method.invoke(one, values);
                }
            }
        }
        return result;
    }//fin del metodo readData


    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> readData2(String paquete,String classname) throws Exception {


        try(
        InputStream inp = new FileInputStream(getWorkbookName());

        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(inp);            // InputStream or File for XLSX file (required)
        ){

            //Sheet sheet = getSheetWithName(paquete,classname);
            List<T> result = new ArrayList<T>();
            for(Sheet sheet:workbook) {
                Class clazz = Class.forName(paquete + workbook.getSheetName(0));
                setupFieldsForClass(clazz);
                for (Row r : sheet) {

                    int colCount = 0;
                    //Ya que la primera fila son los titulos
                    if(r.getRowNum()>0)
                    {
                        T one = (T) clazz.newInstance();
                        result.add(one);
                        for (Cell cell : r) {

                            int type = cell.getCellType();
                            String fieldName = fieldNames.get(colCount++);
                            Method method = constructMethod(clazz, fieldName);
                            if (type == Cell.CELL_TYPE_STRING) {
                                String value = cell.getStringCellValue();
                                Object[] values = new Object[1];
                                values[0] = value;
                                method.invoke(one, values);
                            } else if (type == Cell.CELL_TYPE_NUMERIC) {
                                Double num = cell.getNumericCellValue();
                                Class<?> returnType = getGetterReturnClass(clazz, fieldName);
                                if (returnType == int.class || returnType == Integer.class) {
                                    method.invoke(one, num.intValue());
                                } else if (returnType == double.class || returnType == Double.class) {
                                    method.invoke(one, num);
                                } else if (returnType == float.class || returnType == Float.class) {
                                    method.invoke(one, num.floatValue());
                                } else if (returnType == long.class || returnType == Long.class) {
                                    method.invoke(one, num.longValue());
                                } else if (returnType == short.class || returnType == Short.class) {
                                    method.invoke(one, num.shortValue());
                                } else if (returnType == BigDecimal.class) {
                                    method.invoke(one, BigDecimal.valueOf(num));
                                } else if (returnType == Date.class) {

                                    Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                                    method.invoke(one, date);
                                }

                            } else if (type == Cell.CELL_TYPE_BOOLEAN) {
                                boolean num = cell.getBooleanCellValue();
                                Object[] values = new Object[1];
                                values[0] = num;
                                method.invoke(one, values);
                            }

                        } //fin del for Cell


                    }// fin del if

                }// fin del for ROw


            }//fin del For Sheet
            return result;
        }

    }



    private Class<?> getGetterReturnClass(Class<?> clazz, String fieldName) {
        String methodName = "get" + capitalize(fieldName);
        String methodIsName = "is" + capitalize(fieldName);
        Class<?> returnType = null;

        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName) || method.getName().equals(methodIsName)) {
                returnType = method.getReturnType();
                break;
            }

        }//fin del for
        return returnType;
    }//fin del metodo getGetterReturnClass


    @SuppressWarnings({"unchecked", "rawtypes"})
    private Method constructMethod(Class clazz, String fieldName) throws SecurityException, NoSuchMethodException {
        Class<?> fieldClass = this.getGetterReturnClass(clazz, fieldName);
        return clazz.getMethod("set" + capitalize(fieldName), fieldClass);
    }

    public <T> void writeData(List<T> data) throws Exception {
        try {
            Sheet sheet = getWorkbook().createSheet(data.get(0).getClass().getName());
            setupFieldsForClass(data.get(0).getClass());
            int rowCount = 0;
            int columnCount = 0;
            Row row = sheet.createRow(rowCount++);
            for (String fieldName : fieldNames) {
                Cell cel = row.createCell(columnCount++);
                cel.setCellValue(fieldName);
            }
            Class<? extends Object> classz = data.get(0).getClass();
            for (T t : data) {
                row = sheet.createRow(rowCount++);
                columnCount = 0;
                for (String fieldName : fieldNames) {
                    Cell cel = row.createCell(columnCount);
                    Method method = hasMethod(classz, fieldName)
                            ? classz.getMethod("get" + capitalize(fieldName))
                            : classz.getMethod("is" + capitalize(fieldName));

                    Object value = method.invoke(t, (Object[]) null);
                    if (value != null) {
                        if (value instanceof String) {
                            cel.setCellValue((String) value);
                        } else if (value instanceof Long) {
                            cel.setCellValue((Long) value);
                        } else if (value instanceof Integer) {
                            cel.setCellValue((Integer) value);
                        } else if (value instanceof Double) {
                            cel.setCellValue((Double) value);
                        }  else if (value instanceof Date) {
                            cel.setCellValue((Date) value);
                            CellStyle styleDate = workbook.createCellStyle();
                            DataFormat dataFormatDate = workbook.createDataFormat();
                            styleDate.setDataFormat(dataFormatDate.getFormat("d/m/yy"));
                            cel.setCellStyle(styleDate);
                        } else if (value instanceof Boolean) {
                            cel.setCellValue((Boolean) value);
                        }
                    }
                    columnCount++;
                }//fin del for fieldName

            }//fin del for

            //Autofit
            for (int i = 0; i < fieldNames.size(); i++)
                sheet.autoSizeColumn(i);

            FileOutputStream out = new FileOutputStream(new File(workbookName));
            workbook.write(out);
            out.close();
            workbook.close();


        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }//fin del metodo writeData


    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean hasMethod(Class claszz, String fieldName) {

        try {
            claszz.getMethod("get" + capitalize(fieldName));
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }//fin del metodo hasMethod

    public String capitalize(String string) {
        String capital = string.substring(0, 1).toUpperCase();
        return capital + string.substring(1);
    }


    public String getWorkbookName() {
        return workbookName;
    }

    public void setWorkbookName(String workbookName) {
        this.workbookName = workbookName;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }
}
