package com.atividade.tecnica.dto;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Credenciais;
import com.atividade.tecnica.enumeracao.Roles;

import lombok.Data;

@Data
public class ClienteRegisterDto implements DataTransferObject<Cliente> {
	private final BCryptPasswordEncoder encriptar = new BCryptPasswordEncoder();
	
	private String email;
	private String password;
	private List<Roles> nivel_de_acesso;

	@Override
	public Cliente get() {
		Cliente usuario = new Cliente();
		
		Credenciais credencial = new Credenciais();
		credencial.setEmail(email);
		credencial.setPassword(encriptar.encode(password));
		
		usuario.setCredenciais(credencial);
		usuario.setNivel_de_acesso(nivel_de_acesso);
		return usuario;
	}

}
