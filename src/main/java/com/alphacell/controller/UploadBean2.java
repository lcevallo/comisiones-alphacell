package com.alphacell.controller;

import com.alphacell.model.Principal;
import com.alphacell.model.PrincipalXLS;
import com.alphacell.repository.PrincipalRepository;
import com.alphacell.service.RegistroPrincipal;
import com.alphacell.util.file.ExcelHelper;
import com.alphacell.util.file.Rute;
import com.alphacell.util.file.UploadHelper;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;


import java.io.File;
import java.util.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import java.io.Serializable;

/**
 * Created by luis on 27/10/16.
 */

@Named
@ViewScoped
public class UploadBean2  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String excelFile="";
	private Part part;
    private List<PrincipalXLS> tablePrincipalXLS;
    private List<Principal> tablePrincipal;
    private List<Principal> tablePrincipalFiltered;
    private Principal principalSelected;


    @Inject
    private PrincipalRepository principalRepository;

    @Inject
    private RegistroPrincipal registroPrincipal;


    public void processupload(){
        try {
            UploadHelper uh= new UploadHelper();

            this.excelFile=uh.processUpload(this.part);
            ExternalContext ec= FacesContext.getCurrentInstance().getExternalContext();

            String path= Rute.getPathDefinida(ec.getRealPath("/"));
            String realPath=path + File.separator + "src"+ File.separator + "main" + File.separator + "webapp" + File.separator +"resources"+ File.separator+"assets" + File.separator;

            String basePath= realPath+this.excelFile;

            ExcelHelper excelHelper= new ExcelHelper(basePath);

            this.tablePrincipalXLS= excelHelper.readData("com.alphacell.model.",PrincipalXLS.class.getName());
            this.tablePrincipal= new ArrayList<>();

            tablePrincipalXLS.forEach(principalXLS -> {
                                        Principal pInsert= new Principal(principalXLS);
                                        this.registroPrincipal.salvar(pInsert);
                                        this.tablePrincipal.add(new Principal(principalXLS));
                                                }
                                );

            RequestContext.getCurrentInstance().update(
                    Arrays.asList("form-principal:table-principal"));


        } catch (Exception e) {

            this.tablePrincipalXLS=null;
            this.tablePrincipal=null;
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }


    public String getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(String excelFile) {
        this.excelFile = excelFile;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public List<Principal> getTablePrincipal() {
        return tablePrincipal;
    }

    public void setTablePrincipal(List<Principal> tablePrincipal) {
        this.tablePrincipal = tablePrincipal;
    }

    public List<PrincipalXLS> getTablePrincipalXLS() {
        return tablePrincipalXLS;
    }

    public void setTablePrincipalXLS(List<PrincipalXLS> tablePrincipalXLS) {
        this.tablePrincipalXLS = tablePrincipalXLS;
    }

    public List<Principal> getTablePrincipalFiltered() {
        return tablePrincipalFiltered;
    }

    public void setTablePrincipalFiltered(List<Principal> tablePrincipalFiltered) {
        this.tablePrincipalFiltered = tablePrincipalFiltered;
    }

    public Principal getPrincipalSelected() {
        return principalSelected;
    }

    public void setPrincipalSelected(Principal principalSelected) {
        this.principalSelected = principalSelected;
    }
}
