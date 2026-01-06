package com.tawersvip.transportes_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tawersvip.transportes_backend.domain.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    boolean existsByPlaca(String placa);

    Vehicle findByPlaca(String placa);
}
