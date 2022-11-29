package com.atividade.tecnica.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade.tecnica.entidades.Credenciais;

public interface RepositorioCredencial extends JpaRepository<Credenciais, Long> {

}
