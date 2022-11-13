package com.atividade.tecnica.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CredenciaisDto  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
}
