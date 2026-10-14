package com.example.web;

import com.example.Logic.Rol;
import com.example.Logic.Usuario;
import com.example.Persistence.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class UsuarioAdminService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;

    public UsuarioAdminService(UsuarioRepository usuarios, PasswordEncoder passwordEncoder) {
        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarios.findAll().stream().sorted(Comparator.comparing(Usuario::getNombreUsuario, String.CASE_INSENSITIVE_ORDER)).toList();
    }

    @Transactional
    public void crear(UsuarioForm form) {
        String username = form.getNombreUsuario() == null ? "" : form.getNombreUsuario().trim();
        if (!username.matches("[A-Za-z0-9._-]{3,80}")) throw new IllegalArgumentException("El usuario debe tener entre 3 y 80 caracteres (letras, números, punto, guion o guion bajo).");
        if (usuarios.existsByNombreUsuarioIgnoreCase(username)) throw new IllegalArgumentException("Ese nombre de usuario ya existe.");
        validarPassword(form.getPassword());
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(username);
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));
        usuario.setRol(form.getRol() == null ? Rol.CONSULTA : form.getRol());
        usuario.setActivo(true);
        usuarios.save(usuario);
    }

    @Transactional
    public void cambiarRol(Long id, Rol rol) {
        Usuario usuario = obtener(id);
        if (usuario.isActivo() && usuario.getRol() == Rol.ADMIN && rol != Rol.ADMIN) validarQueNoSeaElUltimoAdmin(usuario);
        usuario.setRol(rol);
        usuarios.save(usuario);
    }

    @Transactional
    public void alternarEstado(Long id) {
        Usuario usuario = obtener(id);
        if (usuario.isActivo() && usuario.getRol() == Rol.ADMIN) validarQueNoSeaElUltimoAdmin(usuario);
        usuario.setActivo(!usuario.isActivo());
        usuarios.save(usuario);
    }

    @Transactional
    public void cambiarPassword(Long id, String password) {
        validarPassword(password);
        Usuario usuario = obtener(id);
        usuario.setPassword(passwordEncoder.encode(password));
        usuarios.save(usuario);
    }

    private Usuario obtener(Long id) {
        return usuarios.findById(id).orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario."));
    }

    private void validarQueNoSeaElUltimoAdmin(Usuario admin) {
        long adminsActivos = usuarios.findAll().stream().filter(Usuario::isActivo).filter(u -> u.getRol() == Rol.ADMIN).count();
        if (adminsActivos <= 1) throw new IllegalArgumentException("Debe quedar al menos un administrador activo.");
    }

    private void validarPassword(String password) {
        if (password == null || password.length() < 12) throw new IllegalArgumentException("La contraseña debe tener al menos 12 caracteres.");
    }
}
