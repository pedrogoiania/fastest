package br.com.pedrogyn.fastest.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadProjetoController {
	
	@RequestMapping(value="/api/uploadProjeto", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadProjeto(@RequestParam("file") MultipartFile file) throws IOException {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		
		File convertFile = new File(s + "/projetos/" + file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		System.out.println(fout);
		fout.write(file.getBytes());
		fout.close();
		
		String realFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
		byte[] buffer = new byte[1024];

	    try{
		    	//create output directory is not exists
	    		String folderName = s + "/projetos/" + realFileName;
		    	File folder = new File(folderName);
		    	System.out.println("folder: " + folder);
		    	if(!folder.exists()){
		    		folder.mkdir();
		    	}
	
		    	FileInputStream fis = new FileInputStream(s + "/projetos/" + file.getOriginalFilename());
		    	System.out.println("fis: " + s + "/projetos/" + file.getOriginalFilename());
		    	
		    	//get the zip file content
		    	ZipInputStream zis = new ZipInputStream(fis);
		    	System.out.println("zis: " + zis);
		    		
		    	//get the zipped file list entry
		    	ZipEntry ze = zis.getNextEntry();
		    	System.out.println("ze: " + ze);
		    	
		    	while(ze!=null){
		    		String fileName = ze.getName();
		    		Boolean nomeIgual = false;
		    		if(fileName.equals( realFileName + "/")) {
		    			nomeIgual = true;
		    		} else {
		    			nomeIgual = false;
		    		}
		    		
		    		if(nomeIgual == false) {
		    			
		    			if(!(fileName.contains("__MACOSX"))) {
			    			System.out.println("zeName: " + ze.getName());
		     	   		File newFile = new File(s + "/projetos" + File.separator + fileName);
			     	   	//System.out.println("file unzip : "+ newFile.getAbsoluteFile());
		
		     	   		if(newFile.isDirectory()) {
		     	   			System.out.println(newFile.getName() + " is Diretorio ");
		     	   		} else {
		     	   			System.out.println(newFile.getName() + " is arquivo ");
		     	   			//create all non exists folders
			             	//else you will hit FileNotFoundException for compressed folder
			     	   		System.out.println("parent: " + newFile.getParent());
			     	   		
			     	   		File pathParent = new File(newFile.getParent());
			     	   		
			     	   		if(pathParent.exists()) {
			     	   			pathParent.delete();
			     	   			pathParent.mkdir();
			     	   		}

			             	FileOutputStream fos = new FileOutputStream(newFile);
			
			             	int len;
			             	while ((len = zis.read(buffer)) > 0) {
			            	 		fos.write(buffer, 0, len);
			             	}
			
			             	fos.close();
		     	   		}
		     	   		
		             	
			    		} 
		    			
		    		}
		    		ze = zis.getNextEntry();
	     	}

	        zis.closeEntry();
	    		zis.close();

		    	System.out.println("Done");
	
		    }catch(IOException ex){
		       ex.printStackTrace();
		    }
		
		
	    convertFile.delete();
		return new ResponseEntity<>("O projeto " + realFileName +" foi enviado com sucesso!!!", HttpStatus.OK);
	}
	
}
