package com.alphacell.controller;

import com.alphacell.model.Principal;
import com.alphacell.repository.PrincipalRepository;
import com.alphacell.service.RegistroPrincipal;
import com.alphacell.util.file.ExcelHelper;
import com.alphacell.util.file.UploadHelper;


import java.util.*;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String excelFile="";
	private Part part;
    List<Principal> tablePrincipal;


    @Inject
    private PrincipalRepository principalRepository;

    @Inject
    private RegistroPrincipal registroPrincipal;


    public void processupload(){


        try {
            UploadHelper uh= new UploadHelper();

            this.excelFile=uh.processUpload(this.part);
            String basePath= FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/assets/"+this.excelFile);

            ExcelHelper excelHelper= new ExcelHelper(basePath);

            this.tablePrincipal= excelHelper.readData(Principal.class.getName());

            for(Principal item: this.tablePrincipal){
                registroPrincipal.salvar(item);
            }


        } catch (Exception e) {

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
}
