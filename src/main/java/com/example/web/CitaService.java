package com.example.web;

import com.example.Logic.Cita;
import com.example.Logic.EstadoCita;
import com.example.Persistence.CitaRepository;
import com.example.Persistence.MascotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaService {
    private final CitaRepository citas;
    private final MascotaRepository mascotas;

    public CitaService(CitaRepository citas, MascotaRepository mascotas) {
        this.citas = citas;
        this.mascotas = mascotas;
    }

    @Transactional(readOnly = true)
    public List<Cita> listar() { return citas.findAllByOrderByFechaAscHoraAsc(); }

    @Transactional(readOnly = true)
    public Cita obtener(Long id) {
        return citas.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontró la cita."));
    }

    @Transactional
    public Cita guardar(CitaForm form, Long id) {
        if (form.getFecha() == null || form.getHora() == null || form.getMascotaId() == null)
            throw new IllegalArgumentException("Selecciona fecha, hora y mascota.");
        if (form.getServicio() == null || form.getServicio().isBlank())
            throw new IllegalArgumentException("El servicio es obligatorio.");
        if (form.getServicio().trim().length() > 100)
            throw new IllegalArgumentException("El servicio no puede exceder 100 caracteres.");
        Cita cita = id == null ? new Cita() : obtener(id);
        var mascota = mascotas.findById(form.getMascotaId())
                .orElseThrow(() -> new IllegalArgumentException("La mascota seleccionada no existe."));
        cita.setFecha(form.getFecha());
        cita.setHora(form.getHora());
        cita.setServicio(form.getServicio().trim());
        cita.setEstado(form.getEstado() == null ? EstadoCita.PENDIENTE : form.getEstado());
        cita.setNotas(form.getNotas() == null ? "" : form.getNotas().trim());
        cita.setMascota(mascota);
        return citas.save(cita);
    }

    @Transactional
    public void eliminar(Long id) { citas.delete(obtener(id)); }

    public CitaForm formulario(Cita cita) {
        CitaForm form = new CitaForm();
        form.setFecha(cita.getFecha());
        form.setHora(cita.getHora());
        form.setServicio(cita.getServicio());
        form.setEstado(cita.getEstado());
        form.setNotas(cita.getNotas());
        form.setMascotaId(cita.getMascota().getNum_cliente());
        return form;
    }
}
