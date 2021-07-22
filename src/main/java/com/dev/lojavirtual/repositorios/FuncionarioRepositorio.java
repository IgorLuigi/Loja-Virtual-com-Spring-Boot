package com.dev.lojavirtual.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.lojavirtual.modelos.Funcionario;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long>{

}
