package com.example.demo.service;

import java.util.List;


import com.example.demo.entity.PacienteEntity;

import com.example.demo.repository.PacienteRepository;


public interface PacienteService {
	
	List<PacienteEntity>buscarTodosPacientes();
	PacienteEntity buscarPacienteporId(Long id);
	PacienteEntity registrarPaciente(PacienteEntity pacienteEntity);
	PacienteEntity editarPaciente(PacienteEntity pacienteEntity);
    void eliminarPaciente(Long  id);
    List<PacienteEntity> listarPacientes();

}
