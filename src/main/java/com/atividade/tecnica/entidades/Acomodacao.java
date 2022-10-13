package com.atividade.tecnica.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.atividade.tecnica.enumeracao.TipoAcomodacao;

import lombok.Data;

@Data
@Entity
public class Acomodacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column
    private TipoAcomodacao nomeAcomadacao;
	
	@Column
	private Integer camaSolteiro;
	
	@Column
	private Integer camaCasal;
	
	@Column
	private Integer suite;
	
	@Column
	private Boolean climatizacao;
	
	@Column
	private Integer garagem;
	
	
}
