package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PacienteEntity;
import com.example.demo.repository.PacienteRepository;
import com.example.demo.service.PacienteService;

import jakarta.servlet.http.HttpSession;

@Service
public class PacienteServiceImpl implements PacienteService {
	@Autowired	
	private PacienteRepository pacienteRepository;

	   private HttpSession session;

	    public PacienteServiceImpl(HttpSession session) {
	        this.session = session;
	    }
	@Override
	 public List<PacienteEntity> buscarTodosPacientes() {
        return pacienteRepository.findAll();
    }

	@Override
	public PacienteEntity buscarPacienteporId(Long id) {
		return pacienteRepository.findById( id.longValue()).get();
	}

	@Override
	public PacienteEntity registrarPaciente(PacienteEntity pacienteEntity) {
		return pacienteRepository.save(pacienteEntity);
	}

	@Override
	public PacienteEntity editarPaciente(PacienteEntity pacienteEntity) {
		PacienteEntity pacientebuscadoEntity = buscarPacienteporId(pacienteEntity.getId());
		if(pacientebuscadoEntity != null) {
			pacientebuscadoEntity.setCorreo(pacienteEntity.getCorreo());
			pacientebuscadoEntity.setNombre(pacienteEntity.getNombre());
			pacientebuscadoEntity.setCelular(pacienteEntity.getCelular());
			pacientebuscadoEntity.setDni(pacienteEntity.getDni());
			
			return pacienteRepository.save(pacientebuscadoEntity);
		}
		return null;
	}

	@Override
	public void eliminarPaciente(Long  id) {
		pacienteRepository.deleteById(id);
		
	}

	 @Override
	    public List<PacienteEntity> listarPacientes() {
		 List<PacienteEntity> pacientes = (List<PacienteEntity>) session.getAttribute("pacientes");
	        if (pacientes == null) {
	            pacientes = new ArrayList<>();
	        }
	        return pacientes;
	        
	    }
	

	

}
