package com.atividade.tecnica.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade.tecnica.entidades.Endereco;

public interface RepositorioEndereco extends JpaRepository<Endereco, Long> {

}
