package com.tawersvip.transportes_backend.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "facturas")
@Getter
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fechaEmision;

    @Column(nullable = false)
    private int valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaGastoOperativo categoria;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "nombre_conductor")
    private String nombreDelConductor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGasto tipoGasto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_placa", nullable = false)
    private Vehicle vehicle;

    protected Factura() {
    }

    public Factura(LocalDate fechaEmision, int valor, CategoriaGastoOperativo categoria, String descripcion, String nombreDelConductor, TipoGasto tipoGasto, Vehicle vehicle) {
        validarFechaFactura(fechaEmision);
        validarValorFactura(valor);
        validarCategoriaGastoOperativo(categoria);
        validarConductor(nombreDelConductor);
        validarTipoDeGasto(tipoGasto);
        validarVehicle(vehicle);
        this.descripcion = descripcion;
    }

    public void validarFechaFactura(LocalDate fechaEmision) {
        if (fechaEmision == null || fechaEmision.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de emision no puede ser nula o posterior a la fecha actual");
        }
        this.fechaEmision = fechaEmision;
    }

    public void validarValorFactura(int valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor de la factura no puede ser negativo");
        }
        this.valor = valor;
    }

    public void validarCategoriaGastoOperativo(CategoriaGastoOperativo categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria de gasto operativo no puede ser nula");
        }
        this.categoria = categoria;
    }

    public void validarConductor(String nombreDelConductor) {
        if (tipoGasto == TipoGasto.OPERATIVO_CONDUCTOR) {
            if (nombreDelConductor == null || nombreDelConductor.isEmpty()) {
                throw new IllegalArgumentException("El nombre del conductor no puede ser nulo o vacío");
            }
        }
        this.nombreDelConductor = nombreDelConductor;
    }

    private void validarVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("La factura debe pertenecer a un vehículo");
        }
        this.vehicle = vehicle;
    }

    private void validarTipoDeGasto(TipoGasto tipoGasto) {
        if (tipoGasto == null) {
            throw new IllegalArgumentException("El tipo de gasto es obligatorio");
        }
        this.tipoGasto = tipoGasto;
    }

    public String getNombreDelConductor() {
        return nombreDelConductor;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}
