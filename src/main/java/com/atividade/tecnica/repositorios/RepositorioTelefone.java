package com.atividade.tecnica.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade.tecnica.entidades.Telefone;

public interface RepositorioTelefone extends JpaRepository<Telefone, Long> {

}
