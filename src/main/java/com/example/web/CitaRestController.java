package com.example.web;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaRestController {
    private final CitaService service;
    public CitaRestController(CitaService service) { this.service = service; }

    @GetMapping
    public List<CitaDto> listar() { return service.listar().stream().map(CitaDto::desde).toList(); }
    @GetMapping("/{id}")
    public CitaDto obtener(@PathVariable Long id) { return CitaDto.desde(service.obtener(id)); }
    @PostMapping
    public CitaDto crear(@RequestBody CitaForm form) { return CitaDto.desde(service.guardar(form, null)); }
    @PutMapping("/{id}")
    public CitaDto actualizar(@PathVariable Long id, @RequestBody CitaForm form) { return CitaDto.desde(service.guardar(form, id)); }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
