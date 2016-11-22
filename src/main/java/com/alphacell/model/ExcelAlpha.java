/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alphacell.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "excel_alpha")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExcelAlpha.findAll", query = "SELECT e FROM ExcelAlpha e"),
    @NamedQuery(name = "ExcelAlpha.findByImei", query = "SELECT e FROM ExcelAlpha e WHERE e.imei = :imei"),
    @NamedQuery(name = "ExcelAlpha.findByFechaFinanciera", query = "SELECT e FROM ExcelAlpha e WHERE e.fechaFinanciera = :fechaFinanciera"),
    @NamedQuery(name = "ExcelAlpha.findByCantidad", query = "SELECT e FROM ExcelAlpha e WHERE e.cantidad = :cantidad"),
    @NamedQuery(name = "ExcelAlpha.findByAlmacen", query = "SELECT e FROM ExcelAlpha e WHERE e.almacen = :almacen"),
    @NamedQuery(name = "ExcelAlpha.findByCodigoArticulo", query = "SELECT e FROM ExcelAlpha e WHERE e.codigoArticulo = :codigoArticulo"),
    @NamedQuery(name = "ExcelAlpha.findBySitio", query = "SELECT e FROM ExcelAlpha e WHERE e.sitio = :sitio")})
public class ExcelAlpha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "imei")
    private String imei;
    @Column(name = "fecha_financiera")
    @Temporal(TemporalType.DATE)
    private Date fechaFinanciera;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Size(max = 80)
    @Column(name = "almacen")
    private String almacen;
    @Size(max = 50)
    @Column(name = "codigo_articulo")
    private String codigoArticulo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 10)
    @Column(name = "sitio")
    private String sitio;

    public ExcelAlpha() {
    }

    public ExcelAlpha(ExcelAlphaXLS excelAlphaXLS) {
        this.imei = excelAlphaXLS.getImei();
        this.fechaFinanciera = excelAlphaXLS.getFechaFinanciera();
        this.cantidad = excelAlphaXLS.getCantidad();
        this.almacen = excelAlphaXLS.getAlmacen();
        this.codigoArticulo = excelAlphaXLS.getCodigoArticulo();
        this.nombre =excelAlphaXLS.getNombre();
        this.sitio = excelAlphaXLS.getSitio();
    }

    public ExcelAlpha(String imei) {
        this.imei = imei;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imei != null ? imei.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExcelAlpha)) {
            return false;
        }
        ExcelAlpha other = (ExcelAlpha) object;
        if ((this.imei == null && other.imei != null) || (this.imei != null && !this.imei.equals(other.imei))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.alphacell.model.ExcelAlpha[ imei=" + imei + " ]";
    }
    
}
