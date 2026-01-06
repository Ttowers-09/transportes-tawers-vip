package com.tawersvip.transportes_backend.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "propietarios")
@Getter
public class Propietario {

    @Column(nullable = false)
    private String nombre;


    @Id
    private String numeroCedula;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    public Propietario(String nombre, String numeroCedula, String telefono, String direccion, LocalDate fechaNacimiento) {
        validarNombre(nombre);
        validarNumeroCedula(numeroCedula);
        validarTelefono(telefono);
        validarDireccion(direccion);
        validarFechaNacimiento(fechaNacimiento);
    }

    public void validarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void validarNumeroCedula(String numeroCedula) {
        if (numeroCedula == null || numeroCedula.isEmpty()) {
            throw new IllegalArgumentException("El número de cédula no es válido");
        }
        this.numeroCedula = numeroCedula;
    }

    public void validarTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty() || telefono.length() > 10) {
            throw new IllegalArgumentException("El número de teléfono no es válido");
        }
        this.telefono = telefono;
    }

    public void validarDireccion(String direccion) {
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        this.direccion = direccion;
    }

    public void actualizarTelefono(String nuevoTelefono) {
        validarTelefono(nuevoTelefono);
        this.telefono = nuevoTelefono;
    }

    public void actualizarDireccion(String nuevaDireccion) {
        validarDireccion(nuevaDireccion);
        this.direccion = nuevaDireccion;
    }

    private void validarFechaNacimiento (LocalDate fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return java.time.Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

}
