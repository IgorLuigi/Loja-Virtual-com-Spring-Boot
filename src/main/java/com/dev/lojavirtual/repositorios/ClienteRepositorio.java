package com.dev.lojavirtual.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.lojavirtual.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long>{

}
