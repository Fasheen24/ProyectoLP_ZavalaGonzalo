package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	
	@GetMapping("/menu_paciente")
	public String showMenu(HttpSession session, Model model) {
		 if (session.getAttribute("usuario") == null) {
	            return "redirect:/menu_paciente";
	        }

	        String correo = session.getAttribute("usuario").toString();
	        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCorreo(correo);
	        

	        List<PacienteEntity> pacientes = pacienteService.buscarTodosPacientes();
	        model.addAttribute("menupaciente", pacientes);
	        return "menupaciente";
	}
	

	
	@GetMapping("/registrar_paciente")
	public String showAgregarPaciente(Model model) {
		model.addAttribute("paciente",new PacienteEntity());
		return "registrarpaciente";
	}
	@PostMapping("/registrar_paciente")
	public String registrarPaciente(PacienteEntity pacienteEntity) {
		pacienteService.registrarPaciente(pacienteEntity);
		return "redirect:/menu_paciente";
	}
	
	@GetMapping("/editar_paciente/{id}")
	public String showEditarPaciente(@PathVariable("id") Long id, Model model ) {
		PacienteEntity pacientebusacrEntity = pacienteService.buscarPacienteporId(id);
		model.addAttribute("paciente", pacientebusacrEntity);
		return "editar_paciente";
	}
	@PostMapping("/editar_paciente")
	public String editarEmpleado( Model model, PacienteEntity pacienteEntity ) {
		pacienteService.editarPaciente(pacienteEntity);
		return "redirect:/menu_paciente";
	}
	
	@GetMapping("/buscar_paciente/{id}")
	public String buscarPorId(@PathVariable("id") Long id, Model model) {
		PacienteEntity pacienteencotradoEntity = pacienteService.buscarPacienteporId(id);
		model.addAttribute("paciente", pacienteencotradoEntity);
		return "buscarpaciente";
	}
	
	@GetMapping("/eliminar_paciente/{id}")
	public String eliminarProducto(@PathVariable("id") Long id, Model model) {
		pacienteService.eliminarPaciente(id);
        return "redirect:/menu_paciente";
    }

}
