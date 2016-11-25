package com.alphacell.controller;

import com.alphacell.model.ExcelAlpha;
import com.alphacell.model.ExcelAlphaXLS;
import com.alphacell.model.Principal;
import com.alphacell.model.PrincipalXLS;
import com.alphacell.repository.ExcelAlphaRepository;
import com.alphacell.repository.PrincipalRepository;
import com.alphacell.service.RegistroExcelAlph;
import com.alphacell.service.RegistroPrincipal;
import com.alphacell.util.file.ExcelHelper;
import com.alphacell.util.file.Rute;
import com.alphacell.util.file.UploadHelper;
import com.alphacell.util.jsf.FacesMessages;
import com.alphacell.util.reporte.FormatoExcelPoi;
import org.primefaces.context.RequestContext;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by luis on 27/10/16.
 */

@Named
@ViewScoped
public class UploadBean2  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String excelFile="";
	private Part part;
    private Part partExcel;
    private List<PrincipalXLS> tablePrincipalXLS;
    private List<Principal> tablePrincipal;
    private List<Principal> tablePrincipalFiltered;

    private Principal principalSelected;

    private List<ExcelAlphaXLS> tableExcelAlphXLS;
    private List<ExcelAlpha> tableExcelAlph;
    private List<ExcelAlpha> tableExcelFilteredAlph;


    @Inject
    private PrincipalRepository principalRepository;

    @Inject
    private RegistroPrincipal registroPrincipal;



    @Inject
    private ExcelAlphaRepository excelAlphaRepository;

    @Inject
    private RegistroExcelAlph registroExcelAlph;


    @Inject
    private FacesMessages messages;

    public void processuploadCliente(){
        try {
            UploadHelper uh= new UploadHelper();

            this.excelFile=uh.processUpload(this.part);
            ExternalContext ec= FacesContext.getCurrentInstance().getExternalContext();

            String path= Rute.getPathDefinida(ec.getRealPath("/"));
            String realPath=path + File.separator + "src"+ File.separator + "main" + File.separator + "webapp" + File.separator +"resources"+ File.separator+"assets" + File.separator;

            String basePath= realPath+this.excelFile;

            ExcelHelper excelHelper= new ExcelHelper(basePath);

            this.tablePrincipalXLS= excelHelper.readData("com.alphacell.model.",PrincipalXLS.class.getName());


            tablePrincipalXLS.forEach(principalXLS -> {
                        Principal pInsert= new Principal(principalXLS);
                        this.registroPrincipal.salvar(pInsert);

                    }
            );

            messages.info("Ahora debe de cargar el excel que le pasa Isabel");

            RequestContext.getCurrentInstance().update("form-principal:display");


        } catch (Exception e) {

            this.tablePrincipalXLS=null;
            this.tablePrincipal=null;
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void processuploadAlph(){

        try {

            UploadHelper uh = new UploadHelper();

            this.excelFile = uh.processUpload(this.partExcel);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            String path = Rute.getPathDefinida(ec.getRealPath("/"));
            String realPath = path + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "resources" + File.separator + "assets" + File.separator;

            String basePath = realPath + this.excelFile;

            ExcelHelper excelHelper = new ExcelHelper(basePath);


            this.tableExcelAlphXLS = excelHelper.readData2("com.alphacell.model.", ExcelAlphaXLS.class.getName());
            this.tableExcelAlph = new ArrayList<>();




            tableExcelAlphXLS.stream().
                    filter(excelAlphaXLS->excelAlphaXLS.getImei()!=null).
                    forEach(excelAlphaXLS -> {
                        ExcelAlpha excelAlpha= new ExcelAlpha(excelAlphaXLS);
                        this.registroExcelAlph.salvar(excelAlpha);
                    });




            /*
            tableExcelAlphXLS.forEach(excelAlphaXLS -> {
                        ExcelAlpha excelAlpha= new ExcelAlpha(excelAlphaXLS);
                        this.registroExcelAlph.salvar(excelAlpha);
                    }
            );
            */

            this.llenarTablaPrincipal();



            RequestContext.getCurrentInstance().update(
                    Arrays.asList("form-principal:display","form-principal:table-principal"));


        }

        catch (Exception e) {

            this.tableExcelAlph=null;
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }



    public String  actualizarPrincipal()
    {
        return this.registroPrincipal.actualizarSP();


    }

    public void llenarTablaPrincipal()
    {

        String mensaje= this.actualizarPrincipal();

        this.tablePrincipal= null;
        this.tablePrincipal= new ArrayList<>();
        this.tablePrincipal= this.principalRepository.getAllNoEliminados();

        messages.info(mensaje);

    }


    public void postProcessXLS(Object document) {

        HashSet omitirColumnas = new HashSet();
        //add elements to HashSet object
        omitirColumnas.add(new Integer("0"));
        omitirColumnas.add(new Integer("1"));
        omitirColumnas.add(new Integer("2"));
        omitirColumnas.add(new Integer("4"));
        omitirColumnas.add(new Integer("5"));
        omitirColumnas.add(new Integer("6"));
        omitirColumnas.add(new Integer("8"));
        omitirColumnas.add(new Integer("9"));
        omitirColumnas.add(new Integer("11"));
        omitirColumnas.add(new Integer("12"));
        omitirColumnas.add(new Integer("14"));
        omitirColumnas.add(new Integer("15"));
        omitirColumnas.add(new Integer("17"));
        omitirColumnas.add(new Integer("18"));
        omitirColumnas.add(new Integer("20"));
        omitirColumnas.add(new Integer("21"));

        FormatoExcelPoi.formatearArchivoExcel(document,omitirColumnas);

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


    public List<ExcelAlpha> getTableExcelAlph() {
        return tableExcelAlph;
    }

    public void setTableExcelAlph(List<ExcelAlpha> tableExcelAlph) {
        this.tableExcelAlph = tableExcelAlph;
    }

    public List<ExcelAlpha> getTableExcelFilteredAlph() {
        return tableExcelFilteredAlph;
    }

    public void setTableExcelFilteredAlph(List<ExcelAlpha> tableExcelFilteredAlph) {
        this.tableExcelFilteredAlph = tableExcelFilteredAlph;
    }

    public Part getPartExcel() {
        return partExcel;
    }

    public void setPartExcel(Part partExcel) {
        this.partExcel = partExcel;
    }

    public List<ExcelAlphaXLS> getTableExcelAlphXLS() {
        return tableExcelAlphXLS;
    }

    public void setTableExcelAlphXLS(List<ExcelAlphaXLS> tableExcelAlphXLS) {
        this.tableExcelAlphXLS = tableExcelAlphXLS;
    }
}
