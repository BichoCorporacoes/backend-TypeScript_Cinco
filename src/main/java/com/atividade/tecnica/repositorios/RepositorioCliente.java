package com.atividade.tecnica.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividade.tecnica.entidades.Cliente;

public interface RepositorioCliente extends JpaRepository<Cliente, Long>{

}
