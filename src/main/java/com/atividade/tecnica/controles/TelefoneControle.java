package com.atividade.tecnica.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Telefone;
import com.atividade.tecnica.servico.ClienteServico;

@CrossOrigin
@RestController
@RequestMapping("/tell")
public class TelefoneControle {
	@Autowired
	private ClienteServico clienteServico;
	
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
}
