package com.atividade.tecnica.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade.tecnica.entidades.Acomodacao;
import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Documento;
import com.atividade.tecnica.entidades.Endereco;
import com.atividade.tecnica.entidades.Telefone;
import com.atividade.tecnica.repositorios.RepositorioAcomodacao;
import com.atividade.tecnica.repositorios.RepositorioCliente;
import com.atividade.tecnica.repositorios.RepositorioDocumento;
import com.atividade.tecnica.repositorios.RepositorioEndereco;
import com.atividade.tecnica.repositorios.RepositorioTelefone;

@Service
public class ClienteServico {

	@Autowired
	private RepositorioCliente repositorioCliente;
	@Autowired
	private RepositorioTelefone repositorioTelefone;
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	@Autowired
	private RepositorioDocumento repositorioDocumento;
	@Autowired
	private RepositorioAcomodacao repositorioAcomodacao;

	public void insert(Cliente cliente) {
		repositorioCliente.save(cliente);
	}

	public void depedenteCadastro(Cliente dependente) {
		Cliente newCliente = new Cliente();
		newCliente.setNome(dependente.getNome());
		newCliente.setNomeSocial(dependente.getNomeSocial());
		newCliente.setTitular(false);
		repositorioCliente.save(dependente);
	}

	public void insertDependente(Cliente cliente, Cliente dependente) {
		findId(cliente.getID());
		cliente.getDependente().add(dependente);
		repositorioCliente.save(cliente);
	}
	
	public void insertTelefoneCliente(Cliente cliente, Telefone telefone) {
		findId(cliente.getID());
		cliente.getTelefones().add(telefone);
		repositorioTelefone.save(telefone);
	}
	
	public void insertEnderecoCliente(Cliente cliente, Endereco endereco) {
		findId(cliente.getID());
		cliente.setEndereco(endereco);
		repositorioEndereco.save(endereco);		
	}
	
	public void insertDocumentoCliente(Cliente cliente, Documento documento) {
		findId(cliente.getID());
		cliente.getDocumentos().add(documento);
		repositorioDocumento.save(documento);
	}


	public Optional<Cliente> findId(Long id) {
		Optional<Cliente> find = repositorioCliente.findById(id);
		return find;
	}
	
	public List<Cliente> findAll() {
		return repositorioCliente.findAll();

	}
	
	public void insertAcomodacaoCliente(Long id, Cliente cliente){
		Optional<Acomodacao> find = repositorioAcomodacao.findById(id);
		cliente.setAcomodacao(find.get());
		repositorioCliente.save(cliente);
	}

	public Cliente selecionar(List<Cliente> clientes, Long id) {
		Cliente selecionado = null;
		for (Cliente cliente : clientes) {
			if (cliente.getID() == id) {
				selecionado = cliente;
			}
		}
		return selecionado;
	}
}
