package com.atividade.tecnica.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade.tecnica.entidades.Documento;
import com.atividade.tecnica.repositorios.RepositorioDocumento;

@Service
public class DocumentoServico {
	@Autowired
	private RepositorioDocumento repositorioDocumento;
	
	public List<Documento> findDocs() {
		return repositorioDocumento.findAll();
	}
}
