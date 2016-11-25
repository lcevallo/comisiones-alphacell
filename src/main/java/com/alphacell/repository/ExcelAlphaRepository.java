package com.alphacell.repository;

import com.alphacell.model.ExcelAlpha;

import org.hibernate.exception.GenericJDBCException;
import org.hibernate.jpa.spi.StoredProcedureQueryParameterRegistration;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.Serializable;
import java.util.Date;

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
    
    public ExcelAlpha guardarSP (ExcelAlpha excelAlpha)
    {

        String salida="";
        try{


          
            StoredProcedureQuery query = this.manager.createStoredProcedureQuery("guardar_excel_alpha");

            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);

            ( (StoredProcedureQueryParameterRegistration) query.getParameter(2)).enablePassingNulls( true );
            ( (StoredProcedureQueryParameterRegistration) query.getParameter(3)).enablePassingNulls( true );
            ( (StoredProcedureQueryParameterRegistration) query.getParameter(4)).enablePassingNulls( true );
            ( (StoredProcedureQueryParameterRegistration) query.getParameter(5)).enablePassingNulls( true );
            ( (StoredProcedureQueryParameterRegistration) query.getParameter(6)).enablePassingNulls( true );
            ( (StoredProcedureQueryParameterRegistration) query.getParameter(7)).enablePassingNulls( true );

            query.setParameter(1, excelAlpha.getImei());
            query.setParameter(2, excelAlpha.getFechaFinanciera());
            query.setParameter(3, excelAlpha.getCantidad());
            query.setParameter(4, excelAlpha.getAlmacen());
            query.setParameter(5, (excelAlpha.getCodigoArticulo()!=null)?excelAlpha.getCodigoArticulo():null );
            query.setParameter(6, (excelAlpha.getNombre()!=null)?excelAlpha.getNombre():null);
            query.setParameter(7, excelAlpha.getSitio());

 /* Positional  Named Parameters
            query.registerStoredProcedureParameter("@imei", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("@fecha_financiera", Date.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("@cantidad", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("@almacen", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("@codigo_articulo", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("@nombre", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("@sitio", String.class, ParameterMode.IN);

            ( (StoredProcedureQueryParameterRegistration) query.getParameter( "@nombre" ) ).enablePassingNulls( true );

            query.setParameter("@imei", excelAlpha.getImei());
            query.setParameter("@fecha_financiera", excelAlpha.getFechaFinanciera());
            query.setParameter("@cantidad", excelAlpha.getCantidad());
            query.setParameter("@almacen", excelAlpha.getAlmacen());
            query.setParameter("@codigo_articulo", (excelAlpha.getCodigoArticulo()!=null)?excelAlpha.getCodigoArticulo():null );
            query.setParameter("@nombre", (excelAlpha.getNombre()!=null)?excelAlpha.getNombre():null);
            query.setParameter("@sitio", excelAlpha.getSitio());


              */

            //aqui salio
            if (query.execute())
            {
                salida = query.getSingleResult().toString();
                System.out.println(salida);
            }




        }
        catch (GenericJDBCException |SecurityException | IllegalStateException  e)
        {


            e.printStackTrace();
        }

       return  manager.find(ExcelAlpha.class,excelAlpha.getImei());

    }


}
