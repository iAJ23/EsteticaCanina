package com.example.web;

import com.example.Logic.Dueno;
import com.example.Logic.Mascota;
import com.example.Persistence.DuenoRepository;
import com.example.Persistence.CitaRepository;
import com.example.Persistence.MascotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MascotaService {
    private final MascotaRepository mascotas;
    private final DuenoRepository duenos;
    private final CitaRepository citas;

    public MascotaService(MascotaRepository mascotas, DuenoRepository duenos, CitaRepository citas) {
        this.mascotas = mascotas;
        this.duenos = duenos;
        this.citas = citas;
    }

    @Transactional(readOnly = true)
    public List<Mascota> listar() { return mascotas.findAll(); }

    @Transactional(readOnly = true)
    public Mascota obtener(Long id) {
        return mascotas.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe la mascota solicitada."));
    }

    @Transactional
    public Mascota guardar(MascotaForm form, Long id) {
        Mascota mascota = id == null ? new Mascota() : obtener(id);
        mascota.setNombre(form.getNombre().trim());
        mascota.setRaza(form.getRaza().trim());
        mascota.setColor(form.getColor().trim());
        mascota.setAlergico(form.getAlergico());
        mascota.setAtencion_especial(form.getAtencionEspecial());
        mascota.setObservaciones(form.getObservaciones());

        Dueno dueno = mascota.getUnDueno();
        if (dueno == null) dueno = new Dueno();
        dueno.setNombre(form.getNombreDueno().trim());
        dueno.setCelDuenio(form.getCelularDueno().trim());
        mascota.setUnDueno(duenos.save(dueno));
        return mascotas.save(mascota);
    }

    @Transactional
    public void eliminar(Long id) {
        Mascota mascota = obtener(id);
        if (citas.existePorMascota(id)) throw new IllegalArgumentException("No se puede eliminar: esta mascota tiene citas en la agenda.");
        Dueno dueno = mascota.getUnDueno();
        mascotas.delete(mascota);
        if (dueno != null) duenos.delete(dueno);
    }

    public MascotaForm formulario(Mascota mascota) {
        MascotaForm form = new MascotaForm();
        form.setNombre(mascota.getNombre());
        form.setRaza(mascota.getRaza());
        form.setColor(mascota.getColor());
        form.setAlergico(mascota.getAlergico());
        form.setAtencionEspecial(mascota.getAtencion_especial());
        form.setObservaciones(mascota.getObservaciones());
        if (mascota.getUnDueno() != null) {
            form.setNombreDueno(mascota.getUnDueno().getNombre());
            form.setCelularDueno(mascota.getUnDueno().getCelDuenio());
        }
        return form;
    }
}
