package com.example.Persistence;

import com.example.Logic.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuarioIgnoreCase(String nombreUsuario);
    boolean existsByNombreUsuarioIgnoreCase(String nombreUsuario);
}
