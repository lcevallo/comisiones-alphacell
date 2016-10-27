package com.alphacell.repository;

import com.alphacell.model.Principal;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 27/10/16.
 */
public class PrincipalRepository implements Serializable{

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Principal getById(Integer id)
    {
        Principal principal=manager.find(Principal.class,id);
        return principal;
    }

    public List<Principal> getAllNoEliminados()
    {

        List<Principal> listaenviada=new ArrayList<Principal>();


        Query query= manager.createNamedQuery("Principal.findAll");

        listaenviada=query.getResultList();


        return listaenviada;
    }



    public Principal guardar(Principal principal) {
        return manager.merge(principal);
    }


    public void remover(Principal principal) {
        principal = getById(principal.getId());
        manager.remove(principal);
    }


}
