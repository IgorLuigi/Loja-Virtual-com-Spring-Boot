package com.dev.lojavirtual.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import com.dev.lojavirtual.modelos.Fornecedor;
import com.dev.lojavirtual.repositorios.FornecedorRepositorio;

import java.util.Optional;

import javax.validation.Valid;


	
@Controller
public class FornecedorControle {
	
	@Autowired
	private FornecedorRepositorio fornecedorRepositorio;
	
	@GetMapping("/administrativo/fornecedores/cadastrar")
	public ModelAndView cadastrar(Fornecedor fornecedor) {
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/cadastro");
		mv.addObject("fornecedor", fornecedor);
		return mv;  
	}
	
	@GetMapping("/administrativo/fornecedores/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/lista");
		mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/fornecedores/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Fornecedor> fornecedor = fornecedorRepositorio.findById(id);
		return cadastrar(fornecedor.get());
	}
	
	@GetMapping("/administrativo/fornecedores/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Fornecedor> fornecedor = fornecedorRepositorio.findById(id);
		fornecedorRepositorio.delete(fornecedor.get());
		return listar();
	}
	
	@PostMapping("/administrativo/fornecedores/salvar")
	public ModelAndView salvar(@Valid Fornecedor fornecedor, BindingResult result) {
		if(result.hasErrors()) {
			return cadastrar(fornecedor);
		}
		fornecedorRepositorio.saveAndFlush(fornecedor);
		
		return cadastrar(new Fornecedor());
	}
}
