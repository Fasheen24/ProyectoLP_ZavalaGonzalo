package com.example.demo.service;

import org.springframework.ui.Model;

import com.example.demo.entity.UsuarioEntity;

import jakarta.servlet.http.HttpSession;

public interface UsuarioService {
	void crearUsuario(UsuarioEntity usuarioEntity, Model model);
	boolean validarUsuario(UsuarioEntity usuarioEntity, HttpSession session);
	UsuarioEntity buscarUsuarioPorCorreo(String correo);

}
