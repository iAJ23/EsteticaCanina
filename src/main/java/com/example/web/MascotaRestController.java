package com.example.web;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaRestController {
    private final MascotaService service;

    public MascotaRestController(MascotaService service) { this.service = service; }

    @GetMapping
    public List<MascotaDto> listar() { return service.listar().stream().map(MascotaDto::desde).toList(); }

    @GetMapping("/{id}")
    public MascotaDto obtener(@PathVariable Long id) { return MascotaDto.desde(service.obtener(id)); }

    @PostMapping
    public MascotaDto crear(@RequestBody MascotaForm form) { return MascotaDto.desde(service.guardar(form, null)); }

    @PutMapping("/{id}")
    public MascotaDto actualizar(@PathVariable Long id, @RequestBody MascotaForm form) {
        return MascotaDto.desde(service.guardar(form, id));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
