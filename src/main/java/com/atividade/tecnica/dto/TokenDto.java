package com.atividade.tecnica.dto;

import com.atividade.tecnica.entidades.Cliente;

import lombok.Data;

@Data
public class TokenDto {
	private String token;
	private String type;
	private LoginDto user;

	public TokenDto(String token, String type, Cliente user) {
	    this.token = token;
	    this.type = type;

	    this.user = LoginDto.create(user);
	  }
}
