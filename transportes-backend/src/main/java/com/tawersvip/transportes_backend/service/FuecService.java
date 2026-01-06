package com.tawersvip.transportes_backend.service;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tawersvip.transportes_backend.domain.Conductor;
import com.tawersvip.transportes_backend.domain.Fuec;
import com.tawersvip.transportes_backend.domain.Vehicle;
import com.tawersvip.transportes_backend.repository.ConductorRepository;
import com.tawersvip.transportes_backend.repository.FuecRepository;
import com.tawersvip.transportes_backend.repository.VehicleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class FuecService {

    private final FuecRepository fuecRepository;
    private final VehicleRepository vehicleRepository;
    private final ConductorRepository conductorRepository;

    public FuecService(FuecRepository fuecRepository, VehicleRepository vehicleRepository, ConductorRepository conductorRepository) {
        this.fuecRepository = fuecRepository;
        this.vehicleRepository = vehicleRepository;
        this.conductorRepository = conductorRepository;
    }

    public Fuec crearFuec(String contratante, String lugarOrigen, String lugarDestino, LocalDate fechaInicio,LocalDate fechaFin, String pasajeros, Vehicle vehicle, Set<Conductor> conductores) {
        Vehicle vehiculo = vehicleRepository.findByPlaca(vehicle.getPlaca());
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo con placa " + vehicle.getPlaca() + " no existe.");
        }

        if (!vehiculo.isActivo(fechaInicio.atStartOfDay())) {
            throw new IllegalStateException("El vehículo no está activo para el FUEC");
        }

        if (conductores == null || conductores.isEmpty()) {
            throw new IllegalArgumentException("El Fuec debe contener al menos un conductor");
        }

        for (Conductor conductor : conductores) {
            Conductor conductorExistente = conductorRepository.findByNumeroCedula(conductor.getNumeroCedula());
            if (conductorExistente == null) {
                throw new IllegalArgumentException("El conductor con cédula " + conductor.getNumeroCedula() + " no existe.");
            }

            if (!conductorExistente.isConductorActivo(fechaInicio)) {
                throw new IllegalStateException("El conductor " + conductor.getNumeroCedula() + " no está activo");
            }
        }

        Fuec fuec = new Fuec(contratante, lugarOrigen, lugarDestino, fechaInicio, fechaFin, pasajeros, vehiculo, conductores);
        vehiculo.agregarFuec(fuec);

        return fuecRepository.save(fuec);
    }

}
