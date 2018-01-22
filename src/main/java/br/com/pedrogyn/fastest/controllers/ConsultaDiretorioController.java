package br.com.pedrogyn.fastest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrogyn.fastest.services.ConsultaDiretorioService;

@RestController
@RequestMapping("/api/consultaDiretorio")
public class ConsultaDiretorioController {
	
	@Autowired
	private ConsultaDiretorioService consultaDiretorio;
	
	@GetMapping()
	public String[] consultaDiretorioLocal() {
		
		final String[] arquivos = this.consultaDiretorio.consultarDiretorioLocal();
		
		return arquivos;
	}
	
	@PostMapping(value = "/nomeDoProjeto")
	public String[] consultaProjeto(@RequestBody String nome) {
		
		String[] arquivos = this.consultaDiretorio.consultarProjeto(nome);
		
		System.out.println("Controller: " + arquivos.length);
		System.out.println("Nome: " + nome);
		
		if(arquivos.length == 0) {
			arquivos = new String[1];
			arquivos[0] = "Pasta do projeto está vazia";
		}
			
		return arquivos;
	}
}
