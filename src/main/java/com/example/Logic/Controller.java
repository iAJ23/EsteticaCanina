package com.example.Logic;

import com.example.Persistence.DuenoDAO;
import com.example.Persistence.MascotaDAO;

import java.util.List;

public class Controller {
    private DuenoDAO duenoDAO;
    private MascotaDAO mascotaDAO;
    private String nombreDueno;

    public Controller() {
        this.duenoDAO = new DuenoDAO();
        this.mascotaDAO = new MascotaDAO();
    }


    public List<Mascota> getAllMascotas() {
        return mascotaDAO.findAll();
    }


    // Guardar tanto al dueño como a la mascota
    public void saveMascota(String nombre, String raza, String color, String observaciones, String alergias, String atencion, String celDueno, String nombreDueno) {
        this.nombreDueno = nombreDueno;

        // Crear una instancia de Dueno
        Dueno dueno = new Dueno();
        dueno.setNombre(nombreDueno);
        dueno.setCelDuenio(celDueno);

        // Guardar al dueño y obtener su ID generado
        duenoDAO.save(dueno);

        // Crear una instancia de Mascota
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setRaza(raza);
        mascota.setColor(color);
        mascota.setObservaciones(observaciones);
        mascota.setAlergico(alergias);
        mascota.setAtencion_especial(atencion);

        // al no establecer esta relacion, generaba un nombre dueno null asi como su id null
        mascota.setUnDueno(dueno); // Asociar el dueño con la mascota

        // Guardar la mascota
        mascotaDAO.save(mascota);
    }

    // Obtener una mascota por ID
    public Mascota getMascotaById(Long id) {
        return mascotaDAO.findById(id);
    }

    // Actualizar una mascota
    public void updateMascota(Mascota mascota) {
        mascotaDAO.update(mascota);
    }

    // Eliminar una mascota por ID
    public void deleteMascota(Long id) {
        mascotaDAO.delete(id);

    }

    public void updateMascota(Mascota masco, String nombre, String raza, String color, String observaciones, String alergias, String atencion,String nombreDueno,String celDuenio) {
        masco.setNombre(nombre);
        masco.setRaza(raza);
        masco.setColor(color);
        masco.setObservaciones(observaciones);
        masco.setAlergico(alergias);
        masco.setAtencion_especial(atencion);

        masco.getUnDueno().setNombre(nombreDueno);
        masco.getUnDueno().setCelDuenio(celDuenio);

        Dueno dueno = masco.getUnDueno();

        mascotaDAO.update(masco);
        duenoDAO.update(masco.getUnDueno());
    }

}
