package com.atividade.tecnica.controles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import com.atividade.tecnica.dto.TokenDto;
import com.atividade.tecnica.entidades.Acomodacao;
import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Endereco;
import com.atividade.tecnica.jwt.JsonWebTotenGerador;
import com.atividade.tecnica.servico.ClienteServico;
import com.atividade.tecnica.servico.LoginService;


@CrossOrigin
@RestController
@RequestMapping("/cliente")
public class ClienteControle {

	@Autowired
	private ClienteServico clienteServico;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JsonWebTotenGerador jwtAuth;

	
	// Teste

	
	// Cliente
	@PostMapping("/cadastro")
	public ResponseEntity<?> cadastroCliente(@RequestBody Cliente obj) {
		clienteServico.insert(obj);
		return new ResponseEntity<>( obj.getID(), HttpStatus.CREATED);
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
		List<Cliente> todos = clienteServico.findAll();
		Cliente existe = clienteServico.vereficarDuplicatas(todos, usuarioDto.getEmail().toString());
		if(existe != null ) {
			return new ResponseEntity<>("Funcionario com o email '" + usuarioDto.getEmail() + "' Já está cadastrado", HttpStatus.BAD_REQUEST);
		}else {
			Cliente usuario = usuarioDto.get();
			clienteServico.insert(usuario);
			return new ResponseEntity<>("User Criado", HttpStatus.ACCEPTED);
		}
	}
	@GetMapping("/cliente-titular/{id}")
	public ResponseEntity<?> GetTitularById(@PathVariable Long id){
		Cliente cliente = clienteServico.findId(id);
		return new ResponseEntity<>(cliente, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/clientes-titulares")
	public ResponseEntity<?> pegarTitulares(){
		List<Cliente> cliente = clienteServico.findAll();
		List<Cliente> clientes = new ArrayList<>();
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
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginService login){
		UsernamePasswordAuthenticationToken logging = login.convert();
		List<Cliente> usuarios = clienteServico.findAll();
		try {
		      Authentication authentication = authManager.authenticate(logging);
		      Cliente user = clienteServico.select(usuarios, authentication.getName());
		      String token = jwtAuth.createToken(authentication.getName());
				return new ResponseEntity<>(new TokenDto(token, "Bearer", user),HttpStatus.ACCEPTED);
			}catch(Exception e) {
				return new ResponseEntity<>("Usuario ou senha incorretos",HttpStatus.UNAUTHORIZED);
			}
	}
	
	// Dependente
	@PutMapping("/cadastro-dependente/{clienteID}")
	public ResponseEntity<?> cadastroClienteDependente(@PathVariable Long clienteID, @RequestBody Cliente dependente) {
		List<Cliente> clientes = clienteServico.findAll();
		Cliente cliente = clienteServico.selecionar(clientes, clienteID);
		if (cliente != null) {
			if(cliente.getEndereco() == null) {
				return new ResponseEntity<>("Cadastre o Endereço Primeiro", HttpStatus.CONFLICT);
			}else if(cliente.getAcomodacao() == null) {
				return new ResponseEntity<>("Cadastre a Acomodação Primeiro", HttpStatus.CONFLICT);
			}else {
				clienteServico.depedenteCadastro(dependente);
				clienteServico.insertEnderecoCliente(dependente, cliente.getEndereco());
				clienteServico.insertAcomodacaoCliente(cliente.getAcomodacao().getID(), dependente);
				clienteServico.insertDependente(cliente, dependente);
			}
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
	
	
	@GetMapping("/acomodacoes")
	public ResponseEntity<?> PegarAcomodacao() {
		try{
			return new ResponseEntity<>(clienteServico.getAllAcomodacao(), HttpStatus.OK);
		}catch(Exception e) {			
			return new ResponseEntity<>(e, HttpStatus.FORBIDDEN);
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
	
	
	// Documento
	
}
