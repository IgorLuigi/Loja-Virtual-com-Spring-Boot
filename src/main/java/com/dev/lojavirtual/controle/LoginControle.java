package com.dev.lojavirtual.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.lojavirtual.modelos.Cidade;
import com.dev.lojavirtual.repositorios.CidadeRepositorio;
import com.dev.lojavirtual.repositorios.EstadoRepositorio;

import java.util.Optional;

import javax.validation.Valid;


	
@Controller
public class LoginControle {
	
	
	@GetMapping("/login")
	public ModelAndView cadastrar(Cidade cidade) {
		ModelAndView mv = new ModelAndView("/login");
		return mv;  
	}
	
	
}
