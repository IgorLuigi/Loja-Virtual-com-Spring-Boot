package com.dev.lojavirtual.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.lojavirtual.modelos.Categoria;
import com.dev.lojavirtual.repositorios.CategoriaRepositorio;

import java.util.Optional;

import javax.validation.Valid;

import com.dev.lojavirtual.validacaoCPF.*;
	
@Controller
public class CategoriaControle {
	
	@Autowired
	private CategoriaRepositorio categoriaRepositorio;
	
	@GetMapping("/administrativo/categorias/cadastrar")
	public ModelAndView cadastrar(Categoria categoria) {
		ModelAndView mv = new ModelAndView("administrativo/categorias/cadastro");
		mv.addObject("categoria", categoria);
		return mv;  
	}
	
	@GetMapping("/administrativo/categorias/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/categorias/lista");
		mv.addObject("listaCategorias", categoriaRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/categorias/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaRepositorio.findById(id);
		return cadastrar(categoria.get());
	}
	
	@GetMapping("/administrativo/categorias/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Categoria> categoria = categoriaRepositorio.findById(id);
		categoriaRepositorio.delete(categoria.get());
		return listar();
	}
	
	@PostMapping("/administrativo/categorias/salvar")
	public ModelAndView salvar(@Valid Categoria categoria, BindingResult result) {
		if(result.hasErrors()) {
			return cadastrar(categoria);
		}
		categoriaRepositorio.saveAndFlush(categoria);
		
		return cadastrar(new Categoria());
	}
}
