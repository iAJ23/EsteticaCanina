package com.example.Logic;


import jakarta.persistence.*;

@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num_cliente;

    private String nombre;
    private String raza;
    private String color;
    private String alergico;
    private String atencion_especial;
    private String observaciones;

    @OneToOne
    private Dueno unDueno;

    public Mascota() {

    }

    public Mascota(Long num_cliente, String nombre, String raza, String color, String alergico, String atencion_especial, String observaciones, Dueno unDueno) {
        this.num_cliente = num_cliente;
        this.nombre = nombre;
        this.raza = raza;
        this.color = color;
        this.alergico = alergico;
        this.atencion_especial = atencion_especial;
        this.observaciones = observaciones;
        this.unDueno = unDueno;
    }

    public Long getNum_cliente() {
        return num_cliente;
    }

    public void setNum_cliente(Long num_cliente) {
        this.num_cliente = num_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlergico() {
        return alergico;
    }

    public void setAlergico(String alergico) {
        this.alergico = alergico;
    }

    public String getAtencion_especial() {
        return atencion_especial;
    }

    public void setAtencion_especial(String atencion_especial) {
        this.atencion_especial = atencion_especial;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Dueno getUnDueno() {
        return unDueno;
    }

    public void setUnDueno(Dueno unDueno) {
        this.unDueno = unDueno;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "num_cliente=" + num_cliente +
                ", nombre='" + nombre + '\'' +
                ", raza='" + raza + '\'' +
                ", color='" + color + '\'' +
                ", alergico='" + alergico + '\'' +
                ", atencion_especial='" + atencion_especial + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", unDueno=" + unDueno +
                '}';
    }
}