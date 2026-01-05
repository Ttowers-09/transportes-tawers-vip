package com.tawersvip.transportes_backend.domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class AsignacionVehiculoConductor {
    private Vehicle vehiculo;
    private Conductor conductor;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public AsignacionVehiculoConductor(Vehicle vehiculo, Conductor conductor, LocalDate fechaInicio) {
        validarVehiculo(vehiculo);
        validarConductor(conductor);
        validarFechaInicio(fechaInicio);
    }

    public void validarVehiculo(Vehicle vehiculo){
        if(vehiculo == null ){
            throw new IllegalArgumentException("El vehículo debe ser obligatorio");
        }
        this.vehiculo = vehiculo;
    }

    public void validarConductor(Conductor conductor){
        if (conductor == null){
            throw new IllegalArgumentException("El conductor debe ser obligatorio");
        }
        this.conductor = conductor;
    }

    public void validarFechaInicio (LocalDate fechaInicio){
        if (fechaInicio == null || fechaInicio.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de inicio no es válida");
        }
        this.fechaInicio = fechaInicio;
    }

    public boolean relacionVehiculoConductorActiva(LocalDate fechaActual) {
        if (fechaActual == null){
            throw new IllegalArgumentException("La fecha actual es obligatoria");
        }

        if (fechaActual.isBefore(fechaInicio)) {
            return false;
        }

        return (fechaFin == null || !fechaFin.isAfter(fechaActual) || fechaFin.isEqual(fechaActual));
    }

    public void finalizarAsignacionConductorVehiculo(LocalDate fechaFinalizacion) {
        if (fechaFinalizacion == null || fechaFinalizacion.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de finalización no es válida");
        }
        this.fechaFin = fechaFinalizacion;
    }

    public List<String> validarEstadoAsignacion (LocalDate fechaActual){
        if (fechaActual == null){
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }

        List <String> errores = new ArrayList<>();

        if (!relacionVehiculoConductorActiva(fechaActual)){
            errores.add("La asignación entre el vehículo y el conductor ha finalizado");
        }

        if (!conductor.isConductorActivo(fechaActual)){
            errores.add("El conductor no se encuentra activo");
        }
        if (!vehiculo.isActivo(fechaActual.atStartOfDay())){
            errores.add("El vehículo no se encuentra activo");
        }
        return errores;
    }

}
