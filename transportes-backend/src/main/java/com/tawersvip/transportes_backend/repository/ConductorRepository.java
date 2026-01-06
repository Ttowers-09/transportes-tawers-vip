package com.tawersvip.transportes_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tawersvip.transportes_backend.domain.Conductor;

public interface ConductorRepository extends JpaRepository<Conductor, String> {
    Conductor findByNumeroCedula(String numeroCedula);
    List<Conductor> findByNombre(String nombre);
}
