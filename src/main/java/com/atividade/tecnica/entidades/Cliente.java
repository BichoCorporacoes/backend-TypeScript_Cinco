package com.atividade.tecnica.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column
	private String nome;
	
	@Column
	private String nomeSocial;
	
	@Column
	private Boolean Titular;
	
	@OneToMany
	private Set<Documento> Documentos = new HashSet<>();
	
	@OneToMany
	private Set<Telefone> Telefones = new HashSet<>();;
	
	@OneToMany
	private Set<Cliente> Dependente = new HashSet<>();;
	
	@OneToOne
	private Acomodacao Acomodacao;
	
	@OneToOne
	private Endereco Endereco;
	

}
