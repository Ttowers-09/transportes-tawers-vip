package com.tawersvip.transportes_backend.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Vehicle {
    private String placa;
    private String propietario;
    private int modelo;
    private String marca;

    private LocalDateTime fechaExpedicionSoat;
    private LocalDateTime fechaVencimientoSoat;

    private LocalDateTime fechaExpedicionTecnicomecanica;
    private LocalDateTime fechaVencimientoTecnicomecanica;

    private String aseguradoraTodoRiesgo;
    private LocalDateTime fechaExpedicionTodoRiesgo;
    private LocalDateTime fechaVencimientoTodoRiesgo;

    private LocalDateTime fechaExpedicionPreventiva;
    private LocalDateTime fechaVencimientoPreventiva;

    private LocalDateTime fechaVencimientoPolizaActual;
    private LocalDateTime fechaVencimientoPolizaContrActual;

    private String detallesEsteticos;

    private String empresaDeTransporte;

    private LocalDate fechaMatriculacion;

    // Realizamos el constructor y las validaciones del mismo
    public Vehicle(String placa, String propietario, int modelo, String marca) {
        validarPlaca(placa);
        validaPropietario(propietario);
        validaModelo(modelo);
        validamarca(marca);
    }

    private void validarPlaca(String placa) {
        if (placa == null || placa.length() < 6 || placa.length() > 8) {
            throw new IllegalArgumentException("La placa no es válida");
        }
        this.placa = placa;
    }

    private void validaPropietario(String propietario) {
        if (propietario == null || propietario.isEmpty()) {
            throw new IllegalArgumentException("El propietario no puede estar vacío");
        }
        this.propietario = propietario;
    }

    private void validaModelo(int modelo) {
        if (modelo <= 0) {
            throw new IllegalArgumentException("El modelo no puede ser menor o igual a cero");
        }
        this.modelo = modelo;
    }

    private void validamarca(String marca) {
        if (marca == null || marca.isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía");
        }
        this.marca = marca;
    }

    // metodos para registrar los documentos del vehiculo
    public void registrarSoat(LocalDateTime fechaExpedicion, LocalDateTime fechaVencimiento) {
        if (fechaExpedicion == null || fechaVencimiento == null || fechaVencimiento.isBefore(fechaExpedicion)) {
            throw new IllegalArgumentException("Fechas de SOAT no válidas");
        }
        this.fechaExpedicionSoat = fechaExpedicion;
        this.fechaVencimientoSoat = fechaVencimiento;
    }

    public void registrarTodoRiesgo(String aseguradora, LocalDateTime fechaExpedicion, LocalDateTime fechaVencimiento) {
        if (aseguradora == null || aseguradora.isEmpty() || fechaExpedicion == null || fechaVencimiento == null
                || fechaVencimiento.isBefore(fechaExpedicion)) {
            throw new IllegalArgumentException("Datos de seguro todo riesgo no válidos");
        }
        this.aseguradoraTodoRiesgo = aseguradora;
        this.fechaExpedicionTodoRiesgo = fechaExpedicion;
        this.fechaVencimientoTodoRiesgo = fechaVencimiento;
    }

    public void registrarPreventiva(LocalDateTime fechaExpedicion, LocalDateTime fechaVencimiento) {

        if (requierePreventiva(fechaMatriculacion)) {
            if (fechaExpedicion == null || fechaVencimiento == null || fechaVencimiento.isBefore(fechaExpedicion)) {
                throw new IllegalArgumentException("Fechas de revisión preventiva no válidas");
            } else {
                this.fechaExpedicionPreventiva = fechaExpedicion;
                this.fechaVencimientoPreventiva = fechaVencimiento;
            }
        } else {
            throw new IllegalArgumentException("El vehiculo esta muy nuevo para registrar la revisión preventiva");
        }
    }

    public void registrarPolizaActual(LocalDateTime fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("Fecha de vencimiento de póliza actual no válida");
        }
        this.fechaVencimientoPolizaActual = fechaVencimiento;
    }

    public void registrarTecnicomecanicaActual(LocalDateTime fechaVencimiento, LocalDateTime fechaExpedicion) {
        if (requiereTecnicomecanica(fechaMatriculacion)) {
            if (fechaExpedicion == null || fechaVencimiento == null || fechaVencimiento.isBefore(fechaExpedicion)) {
                throw new IllegalArgumentException("Fecha de vencimiento de revisión tecnicomecánica actual no válida");
            }
            this.fechaVencimientoTecnicomecanica = fechaVencimiento;
            this.fechaExpedicionTecnicomecanica = fechaExpedicion;
        } else {
            throw new IllegalArgumentException("El vehiculo esta muy nuevo para registrar la revisión tecnicomecánica");
        }
    }

    public void registrarPolizaContrActual(LocalDateTime fechaVencimiento) {
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("Fecha de vencimiento de póliza contratada actual no válida");
        }
        this.fechaVencimientoPolizaContrActual = fechaVencimiento;
    }

    public void agregarDetallesEsteticos(String detalles) {
        this.detallesEsteticos = detalles;
    }

    public void asignarEmpresaDeTransporte(String empresa) {
        this.empresaDeTransporte = empresa;
    }

    public void registrarFechaMatriculacion(LocalDate fechaMatriculacion) {
        if (fechaMatriculacion == null || fechaMatriculacion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de matriculación no válida");
        }
        this.fechaMatriculacion = fechaMatriculacion;
    }

    public boolean requiereTecnicomecanica(LocalDate fechaActual) {
        if (fechaMatriculacion == null) {
            throw new IllegalStateException("No se ha registrado la fecha de matriculación del vehículo");
        }
        return !fechaMatriculacion.plusYears(2).isAfter(fechaActual);
    }

    public boolean requierePreventiva(LocalDate fechaActual) {
        if (fechaMatriculacion == null) {
            throw new IllegalStateException("No se ha registrado la fecha de matriculación del vehículo");
        }
        return !fechaMatriculacion.plusMonths(2).isAfter(fechaActual);
    }

    public boolean isActivo(LocalDateTime fechaActual) {
        return validarEstado(fechaActual).isEmpty();
    }

    public List<String> validarEstado(LocalDateTime fechaActual) {
        if (fechaActual == null) {
            throw new IllegalArgumentException("La fecha actual es obligatoria");
        }

        List<String> errores = new ArrayList<>();

        if (fechaVencimientoSoat == null) {
            errores.add("El vehículo no tiene SOAT registrado");
        } else if (!fechaVencimientoSoat.isAfter(fechaActual)) {
            errores.add("El SOAT está vencido");
        }

        if (fechaVencimientoTodoRiesgo == null) {
            errores.add("El vehículo no tiene póliza todo riesgo registrada");
        } else if (!fechaVencimientoTodoRiesgo.isAfter(fechaActual)) {
            errores.add("La póliza todo riesgo está vencida");
        }

        if (requierePreventiva(fechaActual.toLocalDate())) {
            if (fechaVencimientoPreventiva == null) {
                errores.add("El vehículo requiere revisión preventiva y no está registrada");
            } else if (!fechaVencimientoPreventiva.isAfter(fechaActual)) {
                errores.add("La revisión preventiva está vencida");
            }
        }

        if (fechaVencimientoPolizaActual == null) {
            errores.add("El vehículo no tiene póliza actual registrada");
        } else if (!fechaVencimientoPolizaActual.isAfter(fechaActual)) {
            errores.add("La póliza actual está vencida");
        }

        if (fechaVencimientoPolizaContrActual == null) {
            errores.add("El vehículo no tiene póliza contratada actual registrada");
        } else if (!fechaVencimientoPolizaContrActual.isAfter(fechaActual)) {
            errores.add("La póliza contratada actual está vencida");
        }

        if (requiereTecnicomecanica(fechaActual.toLocalDate())) {
            if (fechaVencimientoTecnicomecanica == null) {
                errores.add("El vehículo requiere tecnicomecánica y no está registrada");
            } else if (!fechaVencimientoTecnicomecanica.isAfter(fechaActual)) {
                errores.add("La revisión tecnicomecánica está vencida");
            }
        }

        return errores;
    }
}
