package com.alphacell.service;

import com.alphacell.model.ExcelAlpha;
import com.alphacell.repository.ExcelAlphaRepository;
import com.alphacell.util.jpa.Transacional;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by luis on 22/11/16.
 */
public class RegistroExcelAlph implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ExcelAlphaRepository excelAlphaRepository;

    @Transacional
    public void salvar(ExcelAlpha excelAlpha) {
        excelAlphaRepository.guardar(excelAlpha);
    }


    @Transacional
    public void salvarSP(ExcelAlpha excelAlpha) {
        excelAlphaRepository.guardarSP(excelAlpha);
    }

    @Transacional
    public void excluir(ExcelAlpha excelAlpha) {
        excelAlphaRepository.remover(excelAlpha);
    }

}
