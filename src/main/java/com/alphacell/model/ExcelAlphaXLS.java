package com.alphacell.model;

import java.util.Date;

/**
 * Created by luis on 22/11/16.
 */
public class ExcelAlphaXLS {

    private Date fechaFinanciera;
    private Integer cantidad;
    private String almacen;
    private String imei;
    private String codigoArticulo;
    private String nombre;
    private String sitio;

    public ExcelAlphaXLS() {
    }

    public ExcelAlphaXLS(Date fechaFinanciera, Integer cantidad, String almacen, String imei, String codigoArticulo, String nombre, String sitio) {
        this.fechaFinanciera = fechaFinanciera;
        this.cantidad = cantidad;
        this.almacen = almacen;
        this.imei = imei;
        this.codigoArticulo = codigoArticulo;
        this.nombre = nombre;
        this.sitio = sitio;
    }

    public Date getFechaFinanciera() {
        return fechaFinanciera;
    }

    public void setFechaFinanciera(Date fechaFinanciera) {
        this.fechaFinanciera = fechaFinanciera;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }
}
