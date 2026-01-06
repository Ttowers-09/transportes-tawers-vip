package com.tawersvip.transportes_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tawersvip.transportes_backend.repository.VehicleRepository;
import com.tawersvip.transportes_backend.domain.Vehicle;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    public Vehicle registrarVehiculo (Vehicle vehicle){
        if (vehicleRepository.existsByPlaca(vehicle.getPlaca())) {
            throw new IllegalArgumentException("El vehículo con placa " + vehicle.getPlaca() + " ya está registrado.");
        }
        return vehicleRepository.save(vehicle);
    }

    public Vehicle obtenerVehiculoPorPlaca(String placa){
        if (!vehicleRepository.existsByPlaca(placa)){
            throw new IllegalStateException("El vehiculo con placa " + placa + " no existe.");
        }
        return vehicleRepository.findByPlaca(placa);
    }

    public List<Vehicle> obtenerTodosLosVehiculos(){
        return vehicleRepository.findAll();
    }
}
