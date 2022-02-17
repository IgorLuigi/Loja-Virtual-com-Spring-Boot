package com.dev.lojavirtual.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.lojavirtual.repositorios.CategoriaRepositorio;
import com.dev.lojavirtual.repositorios.FuncionarioRepositorio;
import com.dev.lojavirtual.repositorios.MarcaRepositorio;
import com.dev.lojavirtual.repositorios.ProdutoRepositorio;

@Controller
public class PrincipalControle {
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
	private MarcaRepositorio marcaRepositorio;
	
	@Autowired 
	private CategoriaRepositorio categoriaRepositorio;
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	
	@GetMapping("/administrativo")
	public ModelAndView acessarPrincipal() {
		ModelAndView mv = new ModelAndView("administrativo/home");
		mv.addObject("totalProdutos", produtoRepositorio.count());
		mv.addObject("totalMarcas", marcaRepositorio.count());
        mv.addObject("totalCategorias", categoriaRepositorio.count());
        mv.addObject("totalFuncionarios", funcionarioRepositorio.count());
		return mv;
	}
	
}