package com.tawersvip.transportes_backend.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Conductor {

    private String nombre;
    private String numeroCedula;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;

    private String categorialicencia;
    private LocalDate fechaVencimientoLicencia;

    private LocalDate fechaInicioContrato;
    private LocalDate fechaPlanillaDeSeguridadSocial;

    public Conductor(String nombre, String numeroCedula, String telefono, String direccion, LocalDate fechaNacimiento) {

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

    public void registrarLicencia(String categoriaLicencia, LocalDate fechaVencimiento) {
        if (categoriaLicencia == null || categoriaLicencia.isBlank()) {
            throw new IllegalArgumentException("La categoría de la licencia es obligatoria");
        }

        if (fechaVencimiento == null || !fechaVencimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La licencia debe tener una fecha de vencimiento válida");
        }

        this.categorialicencia = categoriaLicencia;
        this.fechaVencimientoLicencia = fechaVencimiento;
    }

    public void actualizarTelefono(String nuevoTelefono) {
        validarTelefono(nuevoTelefono);
        this.telefono = nuevoTelefono;
    }

    public void actualizarDireccion(String nuevaDireccion) {
        validarDireccion(nuevaDireccion);
        this.direccion = nuevaDireccion;
    }

    public void actualizarFechaVencimientoLicencia(LocalDate nuevaFechaVencimiento) {
        this.fechaVencimientoLicencia = nuevaFechaVencimiento;
    }

    public void registrarInicioContrato(LocalDate fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }

    public void registrarPlanillaSeguridadSocial(LocalDate fechaPlanilla) {
        if (fechaPlanilla == null) {
            throw new IllegalArgumentException("La fecha de planilla es obligatoria");
        }

        LocalDate hoy = LocalDate.now();

        if (fechaPlanilla.isAfter(hoy)) {
            throw new IllegalArgumentException("La planilla no puede registrarse con fecha futura");
        }

        if (fechaPlanilla.getMonth() != hoy.getMonth() || fechaPlanilla.getYear() != hoy.getYear()) {
            throw new IllegalArgumentException("La planilla debe corresponder al mes actual");
        }

        this.fechaPlanillaDeSeguridadSocial = fechaPlanilla;
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

    public boolean isConductorActivo(LocalDate fechaActual){
        return validarEstado(fechaActual).isEmpty();
    }

    public List<String> validarEstado (LocalDate fechaActual){
        if(fechaActual == null){
            throw new IllegalArgumentException("La fecha actual es obligatoria");
        }

        List <String> errores = new ArrayList<>();

        if (fechaVencimientoLicencia == null || fechaVencimientoLicencia.isBefore(fechaActual)) {
            errores.add("La licencia se encuentra Vencida o no ha sido registrada");
        }

        if (fechaInicioContrato == null || fechaInicioContrato.isAfter(fechaActual)) {
            errores.add("El contrato no ha sido registrado o iniciado");
        }

        if (fechaPlanillaDeSeguridadSocial == null ||
                fechaPlanillaDeSeguridadSocial.getMonth() != fechaActual.getMonth() ||
                fechaPlanillaDeSeguridadSocial.getYear() != fechaActual.getYear()) {
            errores.add("La planilla de seguridad social no ha sido registrada para el mes actual");
        }
        return errores;
    }
        


}
