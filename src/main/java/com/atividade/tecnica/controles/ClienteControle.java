package com.atividade.tecnica.controles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atividade.tecnica.dto.ClienteRegisterDto;
import com.atividade.tecnica.entidades.Acomodacao;
import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Documento;
import com.atividade.tecnica.entidades.Endereco;
import com.atividade.tecnica.entidades.Telefone;
import com.atividade.tecnica.servico.ClienteServico;
import com.atividade.tecnica.servico.DocumentoServico;

@CrossOrigin
@RestController
@RequestMapping("/cliente")
public class ClienteControle {

	@Autowired
	private ClienteServico clienteServico;
	@Autowired
	private DocumentoServico documentoServico;

	
	// Cliente
	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastroCliente(@RequestBody Cliente obj) {
		clienteServico.insert(obj);
		return new ResponseEntity<>("Cliente Cadastrado com sucesso!", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/excluir/{clienteId}")
	public ResponseEntity<?> deleteCliente(@PathVariable Long clienteId) { 
		List<Cliente> clientes = clienteServico.findAll();                 
		Cliente cliente = clienteServico.selecionar(clientes, clienteId);
		if (cliente != null) {
			clienteServico.deletarCliente(clienteId);
			return new ResponseEntity<>("Cliente Deletado com sucesso", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/cadastrar-funcionario")
	public ResponseEntity<?> cadastrarCliente(@RequestBody ClienteRegisterDto usuarioDto) {
		Cliente usuario = usuarioDto.get();
		clienteServico.insert(usuario);
		return new ResponseEntity<>("User Criado", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/clientes-titulares")
	public ResponseEntity<?> pegarTitulares(){
		List<Cliente> cliente = clienteServico.findAll();
		Set<Cliente> clientes = new HashSet<>();
		if(clienteServico.findAll().isEmpty()) {
			return new ResponseEntity<>("Ainda não temos nenhum cliente cadastrado. por favor cadastre algum...", HttpStatus.BAD_GATEWAY);
		}else {
			for (Cliente titulares : cliente) {
				if(titulares.isTitular()) {
					clientes.add(titulares);
				}
			}
			return new ResponseEntity<>(clientes, HttpStatus.OK);
		}
	}
	// Dependente
	@PutMapping("/cadastro-dependente/{clienteID}")
	public ResponseEntity<?> cadastroClienteDependente(@PathVariable Long clienteID, @RequestBody Cliente dependente) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			clienteServico.depedenteCadastro(dependente);
			clienteServico.insertEnderecoCliente(dependente, cliente.getEndereco());
			clienteServico.insertAcomodacaoCliente(cliente.getAcomodacao().getID(), dependente);
			clienteServico.insertDependente(cliente, dependente);
			return new ResponseEntity<>("Dependente Cadastro com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/excluir-dependente/{clienteId}")
	public ResponseEntity<?> deleteClienteDependente(@PathVariable Long clienteId, @RequestBody Cliente dependente) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteId);
		if (cliente != null) {
			for (Cliente titulares : cliente.getDependente()) {
				if(dependente.getID() == titulares.getID()) {
					cliente.getDependente().remove(titulares);
					clienteServico.insert(cliente);
					clienteServico.deletarCliente(titulares.getID());
				}else {
					return new ResponseEntity<>("Dependente não encontrado", HttpStatus.BAD_REQUEST);
				}
			}
			return new ResponseEntity<>("Cliente Deletado com sucesso", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}

	
	// Acomodação
	@PutMapping("/cadastro-acomodacao/{clienteID}")
	public ResponseEntity<?> cadastroClienteAcomodacao(@PathVariable Long clienteID,
			@RequestBody Acomodacao acomodacao) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			clienteServico.insertAcomodacaoCliente(acomodacao.getID(), cliente);
			return new ResponseEntity<>("Reserva feita com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	
	// Endereco
	@PutMapping("/cadastro-endereco/{clienteID}")
	public ResponseEntity<?> cadastroClienteEndereco(@PathVariable Long clienteID, @RequestBody Endereco endereco) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			clienteServico.insertEnderecoCliente(cliente, endereco);
			return new ResponseEntity<>("Endereço Cadastro com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/atualizar-endereco/{id}")
	public ResponseEntity<?> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco atualizacao ){
		atualizacao.setID(id);
		atualizacao = clienteServico.updateEnd(atualizacao);
		return new ResponseEntity<>("Endereço atulaizado com sucesso", HttpStatus.CREATED);
	}
	
	// Telefone
	@PutMapping("/cadastro-telefone/{clienteID}")
	public ResponseEntity<?> cadastroClienteTelefone(@PathVariable Long clienteID, @RequestBody List<Telefone> telefone) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			for (@SuppressWarnings("unused") Telefone telefones : telefone) {
				clienteServico.insertTelefoneCliente(cliente, telefone);
			}
			return new ResponseEntity<>("Telefone Cadastro com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/atualizar-telefone/{id}")
	public ResponseEntity<?> atualizarDocumento(@PathVariable Long id, @RequestBody Telefone atualizacao ){
		atualizacao.setID(id);
		atualizacao = clienteServico.updateTell(atualizacao);
		return new ResponseEntity<>("Telefone atulaizado com sucesso", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/cliente-telefone/deletar/{id}")
	public ResponseEntity<?> deletarTelefone(@PathVariable Long id, @RequestBody Cliente tell){
		Cliente cliente = clienteServico.findId(id);
		List<Long> ids = new ArrayList<>();
		for(Telefone tells : tell.getTelefones())
			ids.add(tells.getID());
		System.out.println(ids);
		List<Telefone> telefones = clienteServico.fromListIdsTelefones(ids);
		cliente.getTelefones().removeAll(telefones);
		clienteServico.insert(cliente);
		return new ResponseEntity<>("Telefone removido com sucesso", HttpStatus.ACCEPTED);	
	}
	
	// Documento
	@PutMapping("/cadastro-documento/{clienteID}")
	public ResponseEntity<?> cadastroClienteDocumento(@PathVariable Long clienteID, @RequestBody List<Documento> documento) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		List<Documento> docs = documentoServico.findDocs();
		if (cliente != null) {
			for (Documento findDocs : docs) {
				for (Documento i : documento) {
					if (i.getNumero().equals(findDocs.getNumero())) {
						return new ResponseEntity<>("Documento Já existe", HttpStatus.CONFLICT);
					}
				}
			}
			clienteServico.insertDocumentoCliente(cliente, documento);
			return new ResponseEntity<>("Documento cadastrado com sucesso!", HttpStatus.CREATED);
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
