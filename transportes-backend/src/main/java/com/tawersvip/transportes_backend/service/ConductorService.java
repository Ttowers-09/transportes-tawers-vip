package com.tawersvip.transportes_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tawersvip.transportes_backend.domain.Conductor;
import com.tawersvip.transportes_backend.repository.ConductorRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ConductorService {
    private final ConductorRepository conductorRepository;

    public ConductorService(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    public Conductor findByNumeroCedula(String numeroCedula){
        if (!conductorRepository.existsById(numeroCedula)){
            throw new IllegalStateException("El conductor con numero de cedula " + numeroCedula + " no existe.");
        }
        return conductorRepository.findByNumeroCedula(numeroCedula);
    }

    public List<Conductor> findByNombre(String nombre){
        if( nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El nombre del conductor no puede ser nulo o vacio.");
        }
        return conductorRepository.findByNombre(nombre);
    }

}
