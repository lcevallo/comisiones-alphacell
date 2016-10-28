/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alphacell.model;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "principal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Principal.findAll", query = "SELECT p FROM Principal p"),
    @NamedQuery(name = "Principal.findByImei", query = "SELECT p FROM Principal p WHERE p.imei = :imei"),
    @NamedQuery(name = "Principal.findByMes", query = "SELECT p FROM Principal p WHERE p.mes = :mes"),
    @NamedQuery(name = "Principal.findByCiudad", query = "SELECT p FROM Principal p WHERE p.ciudad = :ciudad"),
    @NamedQuery(name = "Principal.findByCadena", query = "SELECT p FROM Principal p WHERE p.cadena = :cadena"),
    @NamedQuery(name = "Principal.findByPos", query = "SELECT p FROM Principal p WHERE p.pos = :pos"),
    @NamedQuery(name = "Principal.findByRegion", query = "SELECT p FROM Principal p WHERE p.region = :region"),
    @NamedQuery(name = "Principal.findByMarca", query = "SELECT p FROM Principal p WHERE p.marca = :marca"),
    @NamedQuery(name = "Principal.findByModelo", query = "SELECT p FROM Principal p WHERE p.modelo = :modelo"),
    @NamedQuery(name = "Principal.findByModeloAlph", query = "SELECT p FROM Principal p WHERE p.modeloAlph = :modeloAlph"),
    @NamedQuery(name = "Principal.findByImeiAlph", query = "SELECT p FROM Principal p WHERE p.imeiAlph = :imeiAlph")})
public class Principal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "imei")
    private String imei;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes")
    private short mes;
    @Size(max = 60)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 70)
    @Column(name = "cadena")
    private String cadena;
    @Size(max = 70)
    @Column(name = "pos")
    private String pos;
    @Size(max = 30)
    @Column(name = "region")
    private String region;
    @Size(max = 50)
    @Column(name = "marca")
    private String marca;
    @Size(max = 30)
    @Column(name = "modelo")
    private String modelo;
    
    @Size(max = 30)
    @Column(name = "modelo_alph",nullable = true)
    private String modeloAlph;
  
    @Size(max = 70)
    @Column(name = "imei_alph",nullable = true)
    private String imeiAlph;

    public Principal() {
    }

    public Principal(String imei) {
        this.imei = imei;
    }

    public Principal(String imei, short mes) {
        this.imei = imei;
        this.mes = mes;
    }

    public Principal(PrincipalXLS principalXLS) {
        this.imei = principalXLS.getImei();
        this.mes = principalXLS.getMes();
        this.ciudad = principalXLS.getCiudad();
        this.cadena = principalXLS.getCadena();
        this.pos = principalXLS.getPos();
        this.region = principalXLS.getRegion();
        this.marca = principalXLS.getMarca();
        this.modelo = principalXLS.getModelo();


    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public short getMes() {
        return mes;
    }

    public void setMes(short mes) {
        this.mes = mes;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getModeloAlph() {
        return modeloAlph;
    }

    public void setModeloAlph(String modeloAlph) {
        this.modeloAlph = modeloAlph;
    }

    public String getImeiAlph() {
        return imeiAlph;
    }

    public void setImeiAlph(String imeiAlph) {
        this.imeiAlph = imeiAlph;
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
        if (!(object instanceof Principal)) {
            return false;
        }
        Principal other = (Principal) object;
        if ((this.imei == null && other.imei != null) || (this.imei != null && !this.imei.equals(other.imei))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.alphacell.model.Principal[ imei=" + imei + " ]";
    }
    
}
