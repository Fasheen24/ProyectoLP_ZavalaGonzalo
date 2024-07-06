package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.ProductoService;




@Controller
public class MainController {
	

	 private final ProductoService productoService;

	    @Autowired
	    public MainController(ProductoService productoService) {
	        this.productoService = productoService;
	    }

	    @GetMapping("/menu_principal")
	    public String menuPrincipal(Model model) {
	        model.addAttribute("producto", productoService.buscarTodosProductos());
	        return "menuPrincipal";
	    }
    
}

