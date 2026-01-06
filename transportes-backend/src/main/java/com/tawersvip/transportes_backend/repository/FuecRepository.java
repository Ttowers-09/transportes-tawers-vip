package com.tawersvip.transportes_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tawersvip.transportes_backend.domain.Fuec;

public interface FuecRepository extends JpaRepository<Fuec, Long> {
    List<Fuec> findByVehicle_Placa(String placa);
    List<Fuec> findByLugarOrigen(String lugarOrigen);
    List<Fuec> findByVehicle_PlacaAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(String placa, LocalDate fechaFin, LocalDate fechaInicio);
    List<Fuec> findByConductores_NumeroCedula(String numeroCedula);

}
