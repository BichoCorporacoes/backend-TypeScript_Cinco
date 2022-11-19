package com.atividade.tecnica.dto;

import java.util.List;

import com.atividade.tecnica.entidades.Cliente;
import com.atividade.tecnica.entidades.Credenciais;
import com.atividade.tecnica.enumeracao.Roles;


import lombok.Data;

@Data
public class LoginDto {
	
	  private Credenciais credencial = new Credenciais();
	  private List<Roles> role;

	  public LoginDto(Cliente user) {
		this.credencial.setEmail(user.getCredenciais().getEmail());
		this.credencial.setPassword(user.getCredenciais().getPassword());
	    this.role = user.getNivel_de_acesso();
	  }

	  public static LoginDto create(Cliente user) {
	    return new LoginDto(user);
	  }

}
