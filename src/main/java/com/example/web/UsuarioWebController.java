package com.example.web;

import com.example.Logic.Rol;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioWebController {
    private final UsuarioAdminService service;

    public UsuarioWebController(UsuarioAdminService service) { this.service = service; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", service.listar());
        model.addAttribute("form", new UsuarioForm());
        model.addAttribute("roles", Rol.values());
        return "usuarios";
    }

    @PostMapping("/usuarios")
    public String crear(@ModelAttribute("form") UsuarioForm form, RedirectAttributes flash) {
        try {
            service.crear(form);
            flash.addFlashAttribute("mensaje", "Usuario creado.");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/{id}/rol")
    public String cambiarRol(@PathVariable Long id, @RequestParam Rol rol, RedirectAttributes flash) {
        return accion(flash, () -> service.cambiarRol(id, rol));
    }

    @PostMapping("/usuarios/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, RedirectAttributes flash) {
        return accion(flash, () -> service.alternarEstado(id));
    }

    @PostMapping("/usuarios/{id}/password")
    public String cambiarPassword(@PathVariable Long id, @RequestParam String password, RedirectAttributes flash) {
        return accion(flash, () -> service.cambiarPassword(id, password));
    }

    private String accion(RedirectAttributes flash, Runnable operacion) {
        try {
            operacion.run();
            flash.addFlashAttribute("mensaje", "Cambios guardados.");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuarios";
    }
}
