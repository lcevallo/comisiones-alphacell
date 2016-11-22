package com.alphacell.repository;

import com.alphacell.model.ExcelAlpha;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by luis on 22/11/16.
 */
public class ExcelAlphaRepository implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;


    public ExcelAlpha getByImei(String imei)
    {
        ExcelAlpha excelAlpha= manager.find(ExcelAlpha.class,imei);
        return excelAlpha;

    }

    public ExcelAlpha guardar(ExcelAlpha excelAlpha){
        return manager.merge(excelAlpha);
    }


    public void remover(ExcelAlpha excelAlpha){
        excelAlpha= getByImei(excelAlpha.getImei());
        manager.remove(excelAlpha);

    }

}
