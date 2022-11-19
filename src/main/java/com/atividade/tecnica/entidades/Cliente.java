package com.atividade.tecnica.entidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.atividade.tecnica.enumeracao.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class Cliente{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column
	private String nome;
	
	@Column
	private String nomeSocial;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Credenciais credenciais;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Roles> nivel_de_acesso  = new ArrayList<>();;
	
	@Column
	private boolean Titular;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Documento> Documentos = new ArrayList<>();
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Telefone> Telefones = new HashSet<>();
	
	@JsonIgnoreProperties(value = {"dependente" , "acomodacao", "endereco", "nivel_de_acesso"})
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Cliente> Dependente = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Acomodacao Acomodacao;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco Endereco = null;
	

}
