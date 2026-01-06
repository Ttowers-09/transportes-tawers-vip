package com.tawersvip.transportes_backend.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "fuecs")
@Entity
@Getter
public class Fuec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String contratante;

    @Column(nullable = false)
    private String lugarOrigen;

    @Column(nullable = false)
    private String lugarDestino;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    private String pasajeros;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_placa", nullable = false)
    private Vehicle vehicle;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "fuec_conductores", joinColumns = @JoinColumn(name = "fuec_id"), inverseJoinColumns = @JoinColumn(name = "conductor_cedula"))
    private Set<Conductor> conductores = new HashSet<>();

    protected Fuec() {
    }

    public Fuec(String contratante, String lugarOrigen, String lugarDestino, LocalDate fechaInicio,LocalDate fechaFin, String pasajeros, Vehicle vehicle, Set<Conductor> conductores) {
        
        validarContratante(contratante);
        validarLugarOrigen(lugarOrigen);
        validarLugarDestino(lugarDestino);
        validarFechas(fechaInicio, fechaFin);
        validarVehicle(vehicle);
        validarConductores(conductores);
        this.pasajeros = pasajeros;
    }

    public void validarContratante(String contratante) {
        if (contratante == null || contratante.isBlank()) {
            throw new IllegalArgumentException("El contratante no puede ser nulo o estar vacío.");
        }
        this.contratante = contratante;
    }

    public void validarLugarOrigen(String lugarOrigen) {
        if (lugarOrigen == null || lugarOrigen.isBlank()) {
            throw new IllegalArgumentException("El lugar de origen no puede ser nulo o estar vacío.");
        }
        this.lugarOrigen = lugarOrigen;
    }

    public void validarLugarDestino(String lugarDestino) {
        if (lugarDestino == null || lugarDestino.isBlank()) {
            throw new IllegalArgumentException("El lugar de destino no puede ser nulo o estar vacío.");
        }
        this.lugarDestino = lugarDestino;
    }

    public void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas y la fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    private void validarVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("El Fuec debe pertenecer a un vehículo");
        }
        this.vehicle = vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    private void validarConductores(Set<Conductor> conductores) {
        if (conductores == null || conductores.isEmpty()) {
            throw new IllegalArgumentException("El Fuec debe contener al menos un conductor");
        }
        this.conductores = conductores;
    }

    public void agregarConductor(Conductor conductor) {
        if (conductor == null) {
            throw new IllegalArgumentException("El conductor no puede ser nulo");
        }
        this.conductores.add(conductor);
    }

    public void removerConductor(Conductor conductor) {
        this.conductores.remove(conductor);
    }

}
