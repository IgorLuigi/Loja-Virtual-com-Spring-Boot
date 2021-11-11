package com.dev.lojavirtual.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.lojavirtual.modelos.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long>{
	@Query
	("from Categoria c where c.nomeCategoria like %?1% ")
    List<Categoria> findByNome(String nomeCategoria);
}
