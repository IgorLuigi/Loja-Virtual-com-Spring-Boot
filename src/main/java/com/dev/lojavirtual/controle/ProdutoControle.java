package com.dev.lojavirtual.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dev.lojavirtual.modelos.Imagem;
import com.dev.lojavirtual.modelos.Produto;
import com.dev.lojavirtual.repositorios.ProdutoRepositorio;
import com.dev.lojavirtual.repositorios.CategoriaRepositorio;
import com.dev.lojavirtual.repositorios.ImagemRepositorio;
import com.dev.lojavirtual.repositorios.MarcaRepositorio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Controller
public class ProdutoControle {

	private static String caminhoImagem = "C:\\Users\\Jaguanhara Neto\\Documents\\imagensProjetoWEB";

	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
	private CategoriaRepositorio categoriaRepositorio;
	
	@Autowired
	private MarcaRepositorio marcaRepositorio;
	
	@Autowired
    private ImagemRepositorio imagemRepositorio;

	@GetMapping("/administrativo/produtos/cadastrar")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		mv.addObject("listaCategorias", categoriaRepositorio.findAll());
		mv.addObject("listaMarcas", marcaRepositorio.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}

	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		return cadastrar(produto.get());
	}

	@GetMapping("/administrativo/produtos/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		produtoRepositorio.delete(produto.get());
		return listar();
	}
	
	@ResponseBody
	@GetMapping("administrativo/produtos/mostrarImagem/{imagem}")
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagem + imagem);
		if(imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result,
			@RequestParam("file") List<MultipartFile> arquivo) {
		if (result.hasErrors()) {
			return cadastrar(produto);
		}
		
		try {
			if(!arquivo.isEmpty()) {
				List<Imagem> imagemList = new ArrayList<>();
				produtoRepositorio.saveAndFlush(produto);
				
				for(MultipartFile file : arquivo){
					Imagem imagem = new Imagem();
					
					byte[] bytes = file.getBytes();
					
					Path caminho = Paths.get(caminhoImagem+String.valueOf(produto.getId())+file.getOriginalFilename());
					Files.write(caminho, bytes);
					
					Imagem imagemComValores  = setValoresImagens(imagem, produto, file);
					imagemList.add(imagemRepositorio.saveAndFlush(imagemComValores));
					/*
					 * produto.setNomeImagem(String.valueOf(produto.getId())+arquivo.
					 * getOriginalFilename());
					 */	
				}
				produto.setNomeImagem(imagemList);
				produto = produtoRepositorio.saveAndFlush(produto);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		produtoRepositorio.saveAndFlush(produto);

		return cadastrar(new Produto());
	}

	private Imagem setValoresImagens(Imagem imagem, Produto produto, MultipartFile file) {
		imagem.setNomeImagem(String.valueOf(produto.getId() + file.getOriginalFilename()));
        imagem.setProduto(produto);
	    return imagem;
	}
}
