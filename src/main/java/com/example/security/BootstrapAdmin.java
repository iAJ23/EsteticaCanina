package com.example.security;

import com.example.Logic.Rol;
import com.example.Logic.Usuario;
import com.example.Persistence.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootstrapAdmin implements ApplicationRunner {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String password;

    public BootstrapAdmin(UsuarioRepository usuarios, PasswordEncoder passwordEncoder,
                          @Value("${app.bootstrap-admin.username:admin}") String username,
                          @Value("${app.bootstrap-admin.password:}") String password) {
        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (usuarios.count() > 0) return;
        if (password == null || password.length() < 12) {
            throw new IllegalStateException("No hay usuarios registrados. Define APP_ADMIN_PASSWORD con al menos 12 caracteres para crear el primer administrador.");
        }
        Usuario admin = new Usuario();
        admin.setNombreUsuario(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRol(Rol.ADMIN);
        admin.setActivo(true);
        usuarios.save(admin);
    }
}
