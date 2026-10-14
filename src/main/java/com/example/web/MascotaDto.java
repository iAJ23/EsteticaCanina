package com.example.web;

import com.example.Logic.Mascota;

public record MascotaDto(Long id, String nombre, String raza, String color, String alergias,
                         String atencionEspecial, String observaciones,
                         Integer idDueno, String nombreDueno, String celularDueno) {
    public static MascotaDto desde(Mascota mascota) {
        var dueno = mascota.getUnDueno();
        return new MascotaDto(mascota.getNum_cliente(), mascota.getNombre(), mascota.getRaza(), mascota.getColor(),
                mascota.getAlergico(), mascota.getAtencion_especial(), mascota.getObservaciones(),
                dueno == null ? null : (int) dueno.getId_dueno(),
                dueno == null ? null : dueno.getNombre(), dueno == null ? null : dueno.getCelDuenio());
    }
}
