package com.atividade.tecnica.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastroCliente(@RequestBody Cliente obj) {
		clienteServico.insert(obj);
		return new ResponseEntity<>("Cliente Cadastrado com sucesso!", HttpStatus.CREATED);
	}

	@PutMapping("/cadastro/{clienteID}/telefone")
	public ResponseEntity<?> cadastroClienteTelefone(@PathVariable Long clienteID, @RequestBody Telefone telefone) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			clienteServico.insertTelefoneCliente(cliente, telefone);
			return new ResponseEntity<>("Telefone Cadastro com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cadastro/{clienteID}/documento")
	public ResponseEntity<?> cadastroClienteDocumento(@PathVariable Long clienteID, @RequestBody Documento documento) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		List<Documento> docs = documentoServico.findDocs();
		if (cliente != null) {
			for (Documento findDocs : docs) {
				if (documento.getNumero().equals(findDocs.getNumero())) {
					return new ResponseEntity<>("Documento Já existe", HttpStatus.CONFLICT);
				}
			}
			clienteServico.insertDocumentoCliente(cliente, documento);
			return new ResponseEntity<>("Documento cadastrado com sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cadastro/{clienteID}/endereco")
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

	@PutMapping("/cadastro/{clienteID}/quarto")
	public ResponseEntity<?> cadastroClienteAcomodacao(@PathVariable Long clienteID, @RequestBody Acomodacao acomodacao) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			clienteServico.insertAcomodacaoCliente(acomodacao.getID(), cliente);
			return new ResponseEntity<>("Reserva feita com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/cadastro/{clienteID}/dependente")
	public ResponseEntity<?> cadastroClienteDependente(@PathVariable Long clienteID, @RequestBody Cliente dependente) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			clienteServico.depedenteCadastro(dependente);
			clienteServico.insertEnderecoCliente(dependente, cliente.getEndereco());
			clienteServico.insertDependente(cliente, dependente);
			return new ResponseEntity<>("Dependente Cadastro com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
		}
	}

}
