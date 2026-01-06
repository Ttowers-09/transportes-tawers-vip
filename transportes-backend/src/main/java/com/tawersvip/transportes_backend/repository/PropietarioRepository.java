package com.tawersvip.transportes_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tawersvip.transportes_backend.domain.Propietario;

public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

    List<Propietario> findByNombre(String nombre);
    Propietario findByNumeroCedula(String numeroCedula);
}
