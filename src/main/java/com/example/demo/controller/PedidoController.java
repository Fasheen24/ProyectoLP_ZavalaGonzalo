package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.PedidoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PedidoController {
	 	@Autowired
	    private PedidoService pedidoService;

	    @GetMapping("/guardar_factura")
	    public String guardarFactura(HttpSession session) {
	        pedidoService.guardarFactura(session);
	        return "redirect:/menu_producto";
	    }

}
