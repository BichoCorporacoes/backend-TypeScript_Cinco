package com.atividade.tecnica.servico;

import java.util.List;

import org.springframework.stereotype.Component;

import com.atividade.tecnica.entidades.Cliente;

@Component
public class ClienteSelecionadorEmail {
	
	public Cliente select(List<Cliente> usuarios, String email) {
		Cliente selecionado = null;
		for (Cliente usuario : usuarios) {
			if (usuario.getCredenciais().getEmail().equals(email)) {
				selecionado = usuario;
			}
		}
		return selecionado;
	}
}
