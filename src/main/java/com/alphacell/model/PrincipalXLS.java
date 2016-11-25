/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alphacell.model;

import javax.validation.constraints.NotNull;

/**
 *
 * @author luis
 */
public class PrincipalXLS {
    
    
    private short mes;
    private String ciudad;
    private String cadena;
    private String pos;
    private String region;
    private String marca;
    private String modelo;
    private String imei;

    public PrincipalXLS(short mes, String ciudad, String cadena, String pos, String region, String marca, String modelo, String imei) {
        this.mes = mes;
        this.ciudad = ciudad;
        this.cadena = cadena;
        this.pos = pos;
        this.region = region;
        this.marca = marca;
        this.modelo = modelo;
        this.imei = imei;
    }

    public PrincipalXLS() {
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
