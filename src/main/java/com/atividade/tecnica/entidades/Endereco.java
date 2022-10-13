package com.atividade.tecnica.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column(nullable = false)
	private String Rua;
	
	@Column(nullable = false)
	private String Cep;
	
	@Column(nullable = false)
	private String Bairro;
	
	@Column(nullable = false)
	private String Pais;
	
	@Column(nullable = false)
	private String Cidade;
	
	@Column(nullable = false)
	private String Numer;
}
