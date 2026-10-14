package com.example.web;

import com.example.Logic.EstadoCita;
import com.example.Persistence.MascotaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CitaWebController {
    private final CitaService service;
    private final MascotaRepository mascotas;

    public CitaWebController(CitaService service, MascotaRepository mascotas) {
        this.service = service;
        this.mascotas = mascotas;
    }

    @GetMapping("/agenda")
    public String agenda(Model model) {
        model.addAttribute("citas", service.listar());
        return "agenda";
    }

    @GetMapping("/agenda/nueva")
    public String nueva(Model model) {
        model.addAttribute("form", new CitaForm());
        model.addAttribute("mascotas", mascotas.findAll());
        model.addAttribute("estados", EstadoCita.values());
        model.addAttribute("titulo", "Agendar cita");
        model.addAttribute("accion", "/agenda");
        return "cita-formulario";
    }

    @PostMapping("/agenda")
    public String crear(@ModelAttribute("form") CitaForm form, RedirectAttributes flash) {
        return guardar(form, null, flash);
    }

    @GetMapping("/agenda/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("form", service.formulario(service.obtener(id)));
        model.addAttribute("mascotas", mascotas.findAll());
        model.addAttribute("estados", EstadoCita.values());
        model.addAttribute("titulo", "Editar cita");
        model.addAttribute("accion", "/agenda/" + id);
        return "cita-formulario";
    }

    @PostMapping("/agenda/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("form") CitaForm form, RedirectAttributes flash) {
        return guardar(form, id, flash);
    }

    private String guardar(CitaForm form, Long id, RedirectAttributes flash) {
        try {
            service.guardar(form, id);
            flash.addFlashAttribute("mensaje", id == null ? "Cita agendada." : "Cita actualizada.");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:" + (id == null ? "/agenda/nueva" : "/agenda/" + id + "/editar");
        }
        return "redirect:/agenda";
    }

    @PostMapping("/agenda/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        service.eliminar(id);
        flash.addFlashAttribute("mensaje", "Cita eliminada.");
        return "redirect:/agenda";
    }
}
