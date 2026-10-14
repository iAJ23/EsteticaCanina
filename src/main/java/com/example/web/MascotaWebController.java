package com.example.web;

import com.example.Logic.Mascota;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

@Controller
public class MascotaWebController {
    private final MascotaService service;

    public MascotaWebController(MascotaService service) { this.service = service; }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalMascotas", service.listar().size());
        return "inicio";
    }

    @GetMapping("/mascotas")
    public String listar(Model model, Authentication authentication) {
        model.addAttribute("mascotas", service.listar());
        model.addAttribute("nombreUsuario", authentication.getName());
        return "mascotas/lista";
    }

    @GetMapping("/mascotas/nueva")
    public String nueva(Model model) {
        model.addAttribute("form", new MascotaForm());
        model.addAttribute("titulo", "Registrar mascota");
        model.addAttribute("accion", "/mascotas");
        return "mascotas/formulario";
    }

    @PostMapping("/mascotas")
    public String crear(@ModelAttribute("form") MascotaForm form, RedirectAttributes flash) {
        service.guardar(form, null);
        flash.addFlashAttribute("mensaje", "Mascota registrada correctamente.");
        return "redirect:/mascotas";
    }

    @GetMapping("/mascotas/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Mascota mascota = service.obtener(id);
        model.addAttribute("form", service.formulario(mascota));
        model.addAttribute("titulo", "Editar mascota");
        model.addAttribute("accion", "/mascotas/" + id);
        return "mascotas/formulario";
    }

    @PostMapping("/mascotas/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("form") MascotaForm form, RedirectAttributes flash) {
        service.guardar(form, id);
        flash.addFlashAttribute("mensaje", "Cambios guardados correctamente.");
        return "redirect:/mascotas";
    }

    @PostMapping("/mascotas/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            service.eliminar(id);
            flash.addFlashAttribute("mensaje", "Mascota eliminada correctamente.");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/mascotas";
    }
}
