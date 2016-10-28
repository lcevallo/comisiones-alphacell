package com.alphacell.util.file;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

public class UploadHelper {
	
	
	public UploadHelper() {
		
	}
	private final int limit_max_size = 10240000;
	private final String limit_type_file="xls|xlsx";
	private final String path_to="resources/assets/";
	
	
	public String processUpload(Part fileUpload)
	{
		String fileSaveData="";
		try {
			if(fileUpload.getSize()>0)
			{
				String submittedFileName=getFilename(fileUpload);
				
				if(checkFileType(submittedFileName))
				{
					
					if(fileUpload.getSize()>this.limit_max_size){
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Archivo demasiado Grande! ",""));
					}else{
						
						String currentFilename= submittedFileName;
						String extension = currentFilename.substring(currentFilename.lastIndexOf("."),currentFilename.length());
						Long nameRadom= Calendar.getInstance().getTimeInMillis();
						String newfilename=nameRadom+extension;
						fileSaveData=newfilename;

						ExternalContext ec= FacesContext.getCurrentInstance().getExternalContext();

						String path= Rute.getPathDefinida(ec.getRealPath("/"));
						String realPath=path + File.separator + "src"+ File.separator + "main" + File.separator + "webapp" + File.separator +"resources"+ File.separator+"assets" + File.separator;
						String fileSavePath= realPath;

									try {


										InputStream in= fileUpload.getInputStream();


										File fileToCreate= new File(fileSavePath,newfilename);
										File folder= new File(fileSavePath);
										if(!folder.exists())
										{
											folder.mkdirs();
										}


                                        this.save(in,fileToCreate);
                                  		fileSaveData = newfilename;



									} catch (IOException e) {
										fileSaveData="";
									}	
						
						}//fin del if fileUpload.getSize()
					
				}// fin del if checkFileType(submittedFileName
				else
				{
					fileSaveData="";
				}
					
				
			}//fin del fileUpload.getSize()>0
			
			
		} catch (Exception e) {
			fileSaveData="";
		}
		
		return fileSaveData;
	}//fin del metodo processUpload


    public void save (InputStream inputStream, File file) throws IOException
    {
        OutputStream output= new FileOutputStream(file);

        IOUtils.copy(inputStream,output);
    }


	private String getFilename(Part part)
	{
		for(String cd: part.getHeader("content-disposition").split(";")){
			if(cd.trim().startsWith("filename")){
				String filename= cd.substring(cd.indexOf('=')+1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/')+1).substring(filename.lastIndexOf('\\')+1);
			} 

		}
        return null;
	}
	
	private boolean checkFileType(String fileName)
	{
		if(fileName.length()>0){
			String[] parts = fileName.split("\\.");
            if(parts.length>0){
                    String extention =parts[parts.length-1];
                return this.limit_type_file.contains(extention);
            }
			
		}
		return false;
	}
	
	

}
