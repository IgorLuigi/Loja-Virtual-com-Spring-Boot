package com.dev.lojavirtual.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dev.lojavirtual.modelos.Cliente;
import com.dev.lojavirtual.modelos.Compra;
import com.dev.lojavirtual.modelos.ItensCompra;
import com.dev.lojavirtual.modelos.Produto;
import com.dev.lojavirtual.repositorios.ClienteRepositorio;
import com.dev.lojavirtual.repositorios.CompraRepositorio;
import com.dev.lojavirtual.repositorios.EstadoRepositorio;
import com.dev.lojavirtual.repositorios.ImagemRepositorio;
import com.dev.lojavirtual.repositorios.ItensCompraRepositorio;
import com.dev.lojavirtual.repositorios.ProdutoRepositorio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;

@Controller
@RequestMapping("/relatorio")
public class RelatorioControle {
	
	@Autowired
	private DataSource dataSource;

	@GetMapping("/marcas")
    public void relatorioFuncionarios(HttpServletResponse servletResponse) throws JRException, SQLException, IOException {
        relatorio(servletResponse, "Marcas.jasper", "relatorioMarcas.pdf");
    }

    @GetMapping("/produtos")
    public void relatorioProdutos(HttpServletResponse servletResponse) throws JRException, SQLException, IOException {
        relatorio(servletResponse, "Produtos.jasper", "relatorioProdutos.pdf");
    }
    
    public void relatorio(HttpServletResponse servletResponse, String jasperName, String relatorioNome) throws JRException, SQLException, IOException {
        InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/" + jasperName);

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource.getConnection());

        servletResponse.setContentType("application/pdf");
        servletResponse.setHeader("Content-Disposition", "attachment;filename=" + relatorioNome);

        OutputStream outputStream = servletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
}
