package com.example.Logic;

import jakarta.persistence.*;

@Entity
public class Dueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_dueno;

    private String nombre;
    private String celDuenio;

    @OneToOne(mappedBy = "unDueno")
    private Mascota mascota;

    public Dueno(){

    }

    public Dueno(int id_dueno, String nombre, String celDuenio) {
        this.id_dueno = id_dueno;
        this.nombre = nombre;
        this.celDuenio = celDuenio;
    }

    public long getId_dueno() {
        return id_dueno;
    }

    public void setId_dueno(int id_dueno) {
        this.id_dueno = id_dueno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelDuenio() {
        return celDuenio;
    }

    public void setCelDuenio(String celDuenio) {
        this.celDuenio = celDuenio;
    }

    @Override
    public String toString() {
        return "Dueno{" +
                "id_dueno=" + id_dueno +
                ", nombre='" + nombre + '\'' +
                ", celDuenio='" + celDuenio + '\'' +
                '}';
    }
}
