package com.alphacell.service;

import com.alphacell.model.Principal;
import com.alphacell.repository.PrincipalRepository;
import com.alphacell.util.jpa.Transacional;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by luis on 27/10/16.
 */
public class RegistroPrincipal  implements Serializable{

    private static final long serialVersionUID = 1L;


    @Inject
    private PrincipalRepository principalRepository;

    @Transacional
    public void salvar(Principal principal) {
        principalRepository.guardar(principal);
    }

    @Transacional
    public void excluir(Principal principal) {
        principalRepository.remover(principal);
    }

    @Transacional
    public String actualizarSP() {
      return  principalRepository.actualizarTablaPrincipalSP();
    }

}
