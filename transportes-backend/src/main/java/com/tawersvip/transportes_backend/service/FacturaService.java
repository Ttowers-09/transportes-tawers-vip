package com.tawersvip.transportes_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tawersvip.transportes_backend.domain.Conductor;
import com.tawersvip.transportes_backend.domain.Factura;
import com.tawersvip.transportes_backend.domain.TipoGasto;
import com.tawersvip.transportes_backend.domain.Vehicle;
import com.tawersvip.transportes_backend.repository.ConductorRepository;
import com.tawersvip.transportes_backend.repository.FacturaRepository;
import com.tawersvip.transportes_backend.repository.VehicleRepository;

@Transactional
@Service
public class FacturaService{
    private final FacturaRepository facturaRepository;
    private final VehicleRepository vehicleRepository;
    private final ConductorRepository conductorRepository;

    public FacturaService (FacturaRepository facturaRepository, VehicleRepository vehicleRepository, ConductorRepository conductorRepository) {
        this.facturaRepository = facturaRepository;
        this.vehicleRepository = vehicleRepository;
        this.conductorRepository = conductorRepository;
    }

    public Factura registrarFactura (String placa, Factura factura, String cedulaConductor){
        Vehicle vehicle = vehicleRepository.findByPlaca(placa);
        Conductor conductor = conductorRepository.findByNumeroCedula(cedulaConductor);
        
        if (!vehicleRepository.existsByPlaca(placa)){
            throw new IllegalStateException("El vehiculo con placa " + placa + " no existe.");
        }
        factura.setConductor(conductor);
        vehicle.agregarFactura(factura);

        return facturaRepository.save(factura);
    }

    public List<Factura> findByVehicle_Placa(String placa){
        if (!vehicleRepository.existsByPlaca(placa)){
            throw new IllegalStateException("El vehiculo con placa " + placa + " no existe.");
        }
        return facturaRepository.findByVehicle_Placa(placa);
    }

    public List<Factura> findByTipoGasto(TipoGasto tipoGasto){
        return facturaRepository.findByTipoGasto(tipoGasto);
    }
    public List<Factura> findByFechaEmisionBetween(LocalDate fechaInicio, LocalDate fechaFin){
        if (fechaInicio.isAfter(fechaFin)){
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        return facturaRepository.findByFechaEmisionBetween(fechaInicio, fechaFin);
    }

}