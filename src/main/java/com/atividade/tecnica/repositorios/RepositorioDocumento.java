package com.atividade.tecnica.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade.tecnica.entidades.Documento;

public interface RepositorioDocumento extends JpaRepository<Documento, Long>{
	Optional<Documento> findByNumero(String numero);
}
