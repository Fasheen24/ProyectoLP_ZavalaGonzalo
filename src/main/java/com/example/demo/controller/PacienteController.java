package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.PacienteEntity;
import com.example.demo.entity.UsuarioEntity;
import com.example.demo.service.PacienteService;
import com.example.demo.service.UsuarioService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class PacienteController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PacienteService pacienteService;
	@GetMapping("/menu")
	public String showMenu(HttpSession session, Model model) {
		 if (session.getAttribute("usuario") == null) {
	            return "redirect:/";
	        }

	        String correo = session.getAttribute("usuario").toString();
	        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);

	        List<PacienteEntity> pacientes = pacienteService.buscarTodosPacientes();
	        model.addAttribute("paciente", pacientes);
	        return "menu";
	}
	

	
	@GetMapping("/registrar_paciente")
	public String showAgregarPaciente(Model model) {
		model.addAttribute("paciente",new PacienteEntity());
		return "registrarpaciente";
	}
	@PostMapping("/registrar_paciente")
	public String registrarProducto(PacienteEntity pacienteEntity) {
		pacienteService.registrarPaciente(pacienteEntity);
		return "redirect:/menu";
	}
	

}
