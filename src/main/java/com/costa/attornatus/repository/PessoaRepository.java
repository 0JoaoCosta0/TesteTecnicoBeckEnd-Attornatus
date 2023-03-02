package com.costa.attornatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.costa.attornatus.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
