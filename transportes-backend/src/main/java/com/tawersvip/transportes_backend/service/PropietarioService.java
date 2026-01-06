package com.tawersvip.transportes_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tawersvip.transportes_backend.domain.Propietario;
import com.tawersvip.transportes_backend.repository.PropietarioRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class PropietarioService {
    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public List<Propietario> findByNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacio.");
        }
        return propietarioRepository.findByNombre(nombre);
    }

    public Propietario findByNumeroCedula(String numeroCedula) {
        if (numeroCedula == null || numeroCedula.isBlank()) {
            throw new IllegalArgumentException("El numero de cedula no puede ser nulo o vacio.");
        }
        return propietarioRepository.findByNumeroCedula(numeroCedula);
    }
}
