package com.atividade.tecnica.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Credenciais;
import com.atividade.tecnica.repositorios.RepositorioCliente;

import java.util.List;

@Service
public class ClienteDeatisServiceImpl implements UserDetailsService{

	@Autowired
	private RepositorioCliente repositorio;
	
	@Autowired
	private ClienteSelecionadorEmail selecionador;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Cliente> usuarios = repositorio.findAll();
		Cliente usuario = selecionador.select(usuarios, username);
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		Credenciais credencial = usuario.getCredenciais();
		return new ClienteDetaisImpl(credencial.getEmail(), credencial.getPassword(), usuario.getNivel_de_acesso());
	}
	
}
