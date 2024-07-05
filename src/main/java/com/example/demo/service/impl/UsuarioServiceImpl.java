package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UsuarioEntity;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import com.example.demo.utils.Utilitarios;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void crearUsuario(UsuarioEntity usuarioEntity, Model model) {
		
		String passwordHash = Utilitarios.extraerHash(usuarioEntity.getPassword());
				usuarioEntity.setPassword(passwordHash);
				
				usuarioRepository.save(usuarioEntity);
				
				model.addAttribute("registroCorrecto", "Registro Correcto");
				model.addAttribute("usuario", new UsuarioEntity());
		
	}

	@Override
	public boolean validarUsuario(UsuarioEntity usuarioEntity, HttpSession session) {
		UsuarioEntity usuarioEncontradoPorcCorreo = 
				usuarioRepository.findByCorreo(usuarioEntity.getCorreo());
		
		if(usuarioEncontradoPorcCorreo == null) {
			return false;
		}
		if(!Utilitarios.checkPassword(usuarioEntity.getPassword(), 
				usuarioEncontradoPorcCorreo.getPassword())) {
			return false;
		}
		session.setAttribute("usuario", usuarioEncontradoPorcCorreo.getCorreo());
		
		return true;
	}

	@Override
	public UsuarioEntity buscarUsuarioPorCorreo(String correo) {
		return usuarioRepository.findByCorreo(correo);
	}

}
