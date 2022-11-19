package com.atividade.tecnica.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Documento;
import com.atividade.tecnica.enumeracao.TipoDocumento;
import com.atividade.tecnica.servico.ClienteServico;
import com.atividade.tecnica.servico.DocumentoServico;


@CrossOrigin
@RestController
@RequestMapping("/docs")
public class DocumentosControles {
	
	@Autowired
	private ClienteServico clienteServico;
	@Autowired
	private DocumentoServico documentoServico;
	
	@GetMapping("/teste")
	public ResponseEntity<?> teste(){
		return new ResponseEntity<>("Olá", HttpStatus.OK);
	}
	
	@PutMapping("/cadastro-documentos/{clienteID}")
	public ResponseEntity<?> cadastroClienteDocumento(@PathVariable Long clienteID, @RequestBody List<Documento> documento) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		List<Documento> docs = documentoServico.findDocs();
		if (cliente != null) {
			List<Long> listaIds = new ArrayList<>();
			boolean documentoExistente = false;
			String erroLog = "Os seguintes documentos já estão cadastrados: ";
			for (Documento findDocs : docs) {
				for(Documento docsBody: documento) {
					if (docsBody.getNumero().equals(findDocs.getNumero())) {
						documentoExistente = true;
						erroLog += clienteServico.getDocByNumero(docsBody.getNumero()).getNumero() + " ";
					}
					listaIds.add(findDocs.getID());
				}
			}
			if(documentoExistente == true) {
				return new ResponseEntity<>(erroLog, HttpStatus.CONFLICT);
			}else {
				clienteServico.insertDocumentoCliente(cliente, documento);
				return new ResponseEntity<>("Documento Cadastrado com sucesso", HttpStatus.CREATED);
			}	
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/atualizar-documento/{id}")
	public ResponseEntity<?> atualizarDocumento(@PathVariable Long id, @RequestBody Documento atualizacao ){
		atualizacao.setID(id);
		atualizacao = clienteServico.updateDocs(atualizacao);
		return new ResponseEntity<>("Documento atulaizado com sucesso", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/cliente-documento/deletar/{id}")
	public ResponseEntity<?> deletarDocumento(@PathVariable Long id, @RequestBody Cliente docs){
		Cliente cliente = clienteServico.findId(id);
		List<Long> ids = new ArrayList<>();
		for(Documento doc : docs.getDocumentos())
			ids.add(doc.getID());
		System.out.println(ids);
		List<Documento> documentos = clienteServico.fromListIdsDocumentos(ids);
		cliente.getDocumentos().removeAll(documentos);
		clienteServico.insert(cliente);
		return new ResponseEntity<>("Documento removidos do pacote com sucesso", HttpStatus.ACCEPTED);	
	}
}
