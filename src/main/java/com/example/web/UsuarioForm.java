package com.example.web;

import com.example.Logic.Rol;

public class UsuarioForm {
    private String nombreUsuario;
    private String password;
    private Rol rol = Rol.CONSULTA;

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
