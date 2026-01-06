package com.tawersvip.transportes_backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tawersvip.transportes_backend.domain.Factura;
import com.tawersvip.transportes_backend.domain.TipoGasto;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByVehicle_Placa(String placa);
    List<Factura> findByTipoGasto(TipoGasto tipoGasto);
    List<Factura> findByConductor_NumeroCedula(String numeroCedula);
    List<Factura> findByConductor_Nombre(String nombre);
    List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
