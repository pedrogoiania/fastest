package br.com.pedrogyn.fastest.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class ConsultaDiretorioService {
	
	public String[] consultarDiretorioLocal() {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
//		System.out.println("Current relative path is: " + s);
		
		File file = new File(s + "/projetos");
		String[] str = file.list();
		for(String string:str) {
			System.out.println(string);
		}
		
		return str;
	}
	
	public String[] consultarProjeto(String nomeDoProjeto) {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
//		System.out.println("Current relative path is: " + s);
		
		
		
		File file = new File(s + "/projetos/" + nomeDoProjeto);
		
		String[] str = file.list();
		System.out.println("Pasta do projeto: " + str);
		
		if(str == null) {
			str = new String[1];
			str[0]="Esse projeto não existe";
		} else {
			for(String string:str) {
				System.out.println(string);
			}
		}
		return str;
	}
	
}
