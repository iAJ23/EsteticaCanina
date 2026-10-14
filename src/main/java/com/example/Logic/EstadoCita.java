package com.example.Logic;

public enum EstadoCita {
    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    EN_SERVICIO("En servicio"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada");

    private final String etiqueta;
    EstadoCita(String etiqueta) { this.etiqueta = etiqueta; }
    public String getEtiqueta() { return etiqueta; }
}
