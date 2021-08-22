package com.dev.lojavirtual.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.lojavirtual.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{

}
