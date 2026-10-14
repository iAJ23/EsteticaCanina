package com.example.web;

import com.example.Logic.EstadoCita;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaForm {
    private LocalDate fecha;
    private LocalTime hora;
    private String servicio;
    private EstadoCita estado = EstadoCita.PENDIENTE;
    private String notas;
    private Long mascotaId;

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }
}
