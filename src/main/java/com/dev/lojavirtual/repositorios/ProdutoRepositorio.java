package com.dev.lojavirtual.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.lojavirtual.modelos.Categoria;
import com.dev.lojavirtual.modelos.Marca;
import com.dev.lojavirtual.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long>{
	@Query
	("from Produto p where p.descricao like %?1% ")
    List<Produto> findByDescricao(String descricao);

    List<Produto> findByCategoria(Categoria categoria);

    List<Produto> findByMarca(Marca marca);
}
