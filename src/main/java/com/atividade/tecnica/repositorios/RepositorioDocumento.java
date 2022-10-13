package com.atividade.tecnica.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade.tecnica.entidades.Documento;

public interface RepositorioDocumento extends JpaRepository<Documento, Long>{

}
