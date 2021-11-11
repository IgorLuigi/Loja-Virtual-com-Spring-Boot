package com.dev.lojavirtual.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.lojavirtual.modelos.Categoria;
import com.dev.lojavirtual.modelos.Marca;

public interface MarcaRepositorio extends JpaRepository<Marca, Long>{
	@Query
	("from Marca c where c.nomeMarca like %?1% ")
    List<Marca> findByNome(String nomeMarca);
}
