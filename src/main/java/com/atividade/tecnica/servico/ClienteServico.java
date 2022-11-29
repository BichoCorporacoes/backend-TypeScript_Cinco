package com.atividade.tecnica.servico;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atividade.tecnica.entidades.Acomodacao;
import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Credenciais;
import com.atividade.tecnica.entidades.Documento;
import com.atividade.tecnica.entidades.Endereco;
import com.atividade.tecnica.entidades.Telefone;
import com.atividade.tecnica.repositorios.RepositorioAcomodacao;
import com.atividade.tecnica.repositorios.RepositorioCliente;
import com.atividade.tecnica.repositorios.RepositorioCredencial;
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
	@Autowired
	private RepositorioCredencial repositorioCredencial;

	
	// Clientes
	public void insert(Cliente cliente) {
		repositorioCliente.save(cliente);
	}
	
	public Cliente select(List<Cliente> usuarios, String email) {
		Cliente selecionado = null;
		for (Cliente usuario : usuarios) {
			if (usuario.getCredenciais().getEmail().equals(email)) {
				selecionado = usuario;
			}
		}
		return selecionado;
	}
	
	public Cliente vereficarDuplicatas(List<Cliente> objetos, String identificador) {
		Cliente usuario = null;
		for (Cliente objeto : objetos) {
			for(Credenciais existentes : repositorioCredencial.findAll()) {
				if(existentes.getEmail().equals(identificador)) {
					usuario = objeto;
					break;
				}
			}
		}
		return usuario;
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
	
	public void deletarCliente(Long id) {
		repositorioCliente.deleteById(id);
	}
	
	public List<Cliente> findAll() {
		return repositorioCliente.findAll();
	}
	
	public Cliente findId(Long id) {
		Optional<Cliente> find = repositorioCliente.findById(id);
		return find.orElseThrow(() -> new ObjectNotFoundException("Documento não encontrado"));
	}

	// Dependente
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
	public Cliente atualizarDepedentes(Cliente obj) {
		Cliente newObj = findId(obj.getID());
		updateData(newObj, obj);
		return repositorioCliente.save(newObj);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setNomeSocial(obj.getNomeSocial());
	}
	// Documentos
	public Documento getDocById(Long obj) {
		Optional<Documento> find =  repositorioDocumento.findById(obj);
		return find.orElseThrow(() -> new ObjectNotFoundException("Documento não encontrado"));
	}
	public Documento getDocByNumero(String obj) {
		Optional<Documento> find =  repositorioDocumento.findByNumero(obj);
		return find.orElseThrow(() -> new ObjectNotFoundException("Documento não encontrado"));
	}
	public List<Documento> fromListIdsDocumentos(List<Long> listId){
		List<Documento> obj = new ArrayList<>();
		for (Long longs : listId)
			obj.add(getDocById(longs));
		
		return obj;

	}
	public void insertDocumentoCliente(Cliente cliente, List<Documento> documento) {
		findId(cliente.getID());
		cliente.getDocumentos().addAll(documento);
		repositorioDocumento.saveAll(documento);
	}
	public Documento updateDocs(Documento documento) {
		Documento newOBJ = getDocById(documento.getID());
		UpdateDocumento(newOBJ, documento);
		return repositorioDocumento.save(newOBJ);
	}
	public void UpdateDocumento(Documento newObj, Documento obj) {
		newObj.setTipo(obj.getTipo());
		newObj.setNumero(obj.getNumero());
		newObj.setDataEmissao(obj.getDataEmissao());
	}
	public void deleteDocumento(Long id) {
		getDocById(id);
		repositorioDocumento.deleteById(id);
	}
	
	
	// Telefone
	public void insertTelefoneCliente(Cliente cliente, List<Telefone> telefone) {
		findId(cliente.getID());
		cliente.getTelefones().addAll(telefone);
		repositorioTelefone.saveAll(telefone);
	}
	public Telefone getTellById(Long obj) {
		Optional<Telefone> find =  repositorioTelefone.findById(obj);
		return find.orElseThrow(() -> new ObjectNotFoundException("Documento não encontrado"));
	}
	public Telefone updateTell(Telefone documento) {
		Telefone newOBJ = getTellById(documento.getID());
		UpdateTell(newOBJ, documento);
		return repositorioTelefone.save(newOBJ);
	}
	public void UpdateTell(Telefone newObj, Telefone obj) {
		newObj.setDdd(obj.getDdd());
		newObj.setNumero(obj.getNumero());
	}
	public void deleteTell(Long id) {
		getDocById(id);
		repositorioTelefone.deleteById(id);
	}
	public List<Telefone> fromListIdsTelefones(List<Long> listId){
		List<Telefone> obj = new ArrayList<>();
		for (Long longs : listId)
			obj.add(getTellById(longs));
		
		return obj;

	}
	
	
	// Endereco
	public void insertEnderecoCliente(Cliente cliente, Endereco endereco) {
		findId(cliente.getID());
		cliente.setEndereco(endereco);
		repositorioEndereco.save(endereco);		
	}
	
	public Endereco updateEnd(Endereco documento) {
		Endereco newOBJ = getEndById(documento.getID());
		UpdateEnd(newOBJ, documento);
		return repositorioEndereco.save(newOBJ);
	}
	public Endereco getEndById(Long obj) {
		Optional<Endereco> find =  repositorioEndereco.findById(obj);
		return find.orElseThrow(() -> new ObjectNotFoundException("Documento não encontrado"));
	}
	public void UpdateEnd(Endereco newObj, Endereco obj) {
		newObj.setBairro(obj.getBairro());
		newObj.setCep(obj.getCep());
		newObj.setCidade(obj.getCidade());
		newObj.setPais(obj.getPais());
		newObj.setRua(obj.getRua());
		newObj.setNumero(obj.getNumero());
	}
	
	
	// Acomodacao
	public void insertAcomodacaoCliente(Long id, Cliente cliente){
		Optional<Acomodacao> find = repositorioAcomodacao.findById(id);
		cliente.setAcomodacao(find.get());
		repositorioCliente.save(cliente);
	}
	public List<Acomodacao> getAllAcomodacao(){
		return repositorioAcomodacao.findAll();
	}
}
