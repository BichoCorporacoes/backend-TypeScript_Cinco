package com.atividade.tecnica.entidades;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.atividade.tecnica.enumeracao.TipoDocumento;

import lombok.Data;

@Data
@Entity
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column(nullable = false)
	private TipoDocumento tipo;
	
	@Column(nullable = false)
	private Date dataEmissao;
	
	@Column(unique = true, nullable = false)
	private String numero;
	
}
