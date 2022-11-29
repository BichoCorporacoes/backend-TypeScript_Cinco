package com.atividade.tecnica.servico;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Credenciais;

@Service
public class ClienteSelecionadorEmail implements Selecionador<Cliente, String>{
	
	@Override
	public Cliente selecionar(List<Cliente> objetos, String identificador) {
		Cliente usuario = null;
		for (Cliente objeto : objetos) {
			Credenciais credencial = objeto.getCredenciais();
			String nomeUsuario = credencial.getEmail();
			if (nomeUsuario.trim().equals(identificador.trim())) {
				usuario = objeto;
				break;
			}
		}
		return usuario;
	}
}
