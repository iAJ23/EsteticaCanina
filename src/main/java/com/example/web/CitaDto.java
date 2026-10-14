package com.example.web;

import com.example.Logic.Cita;
import com.example.Logic.EstadoCita;
import java.time.LocalDate;
import java.time.LocalTime;

public record CitaDto(Long id, LocalDate fecha, LocalTime hora, String servicio, EstadoCita estado,
                      String notas, Long mascotaId, String nombreMascota, String nombreDueno) {
    public static CitaDto desde(Cita cita) {
        var mascota = cita.getMascota();
        return new CitaDto(cita.getId(), cita.getFecha(), cita.getHora(), cita.getServicio(), cita.getEstado(),
                cita.getNotas(), mascota.getNum_cliente(), mascota.getNombre(),
                mascota.getUnDueno() == null ? null : mascota.getUnDueno().getNombre());
    }
}
