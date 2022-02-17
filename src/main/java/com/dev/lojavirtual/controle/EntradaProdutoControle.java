package com.dev.lojavirtual.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.lojavirtual.modelos.EntradaItens;
import com.dev.lojavirtual.modelos.EntradaProduto;
import com.dev.lojavirtual.modelos.Estado;
import com.dev.lojavirtual.modelos.Produto;
import com.dev.lojavirtual.repositorios.EntradaItensRepositorio;
import com.dev.lojavirtual.repositorios.EntradaProdutoRepositorio;
import com.dev.lojavirtual.repositorios.FuncionarioRepositorio;
import com.dev.lojavirtual.repositorios.ProdutoRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


	
@Controller
public class EntradaProdutoControle {
	
	private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();
	
	@Autowired
	private EntradaProdutoRepositorio entradaProdutoRepositorio;
	
	@Autowired
	private EntradaItensRepositorio entradaItensRepositorio;
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@GetMapping("/administrativo/entrada/cadastrar")
	public ModelAndView cadastrar(EntradaProduto entrada, EntradaItens entradaItens) {
		ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
		mv.addObject("entrada", entrada);
		mv.addObject("listaEntradaItens", this.listaEntrada);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;  
	}
	
//	@GetMapping("/administrativo/entrada/listar")
//	public ModelAndView listar() {
//		ModelAndView mv = new ModelAndView("administrativo/entrada/lista");
//		mv.addObject("listaEstados", entradaProdutoRepositorio.findAll());
//		return mv;
//	}
//	
//	@GetMapping("/administrativo/entrada/editar/{id}")
//	public ModelAndView editar(@PathVariable("id") Long id) {
//		Optional<Estado> estado = entradaProdutoRepositorio.findById(id);
//		return cadastrar(estado.get());
//	}
	
//	@GetMapping("/administrativo/entrada/remover/{id}")
//	public ModelAndView remover(@PathVariable("id") Long id) {
//		Optional<Estado> estado = entradaProdutoRepositorio.findById(id);
//		entradaProdutoRepositorio.delete(estado.get());
//		return listar();
//	}
	
	@PostMapping("/administrativo/entrada/salvar")
	public ModelAndView salvar(String acao, EntradaProduto entrada, EntradaItens entradaItens) {
		if(acao.equals("itens")) {
			this.listaEntrada.add(entradaItens);
		}else if(acao.equals("salvar")) {
			entradaProdutoRepositorio.saveAndFlush(entrada);
			for(EntradaItens it:listaEntrada) {
				it.setEntrada(entrada);
				entradaItensRepositorio.saveAndFlush(it);
				Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque((int) (produto.getQuantidadeEstoque() + it.getQuantidade()));
				produto.setValorVenda(it.getValorVenda());
				produtoRepositorio.saveAndFlush(produto);
				this.listaEntrada = new ArrayList<>();
				
			}
			return cadastrar(new EntradaProduto(), new EntradaItens());
		}
		
		System.out.print(this.listaEntrada.size());
		
		return cadastrar(entrada, new EntradaItens());
	}
}
