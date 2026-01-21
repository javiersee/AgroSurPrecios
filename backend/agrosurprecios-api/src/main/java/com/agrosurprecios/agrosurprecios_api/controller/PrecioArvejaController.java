package com.agrosurprecios.agrosurprecios_api.controller;

import com.agrosurprecios.agrosurprecios_api.service.PrecioArvejaService;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioArveja;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/precios")
@CrossOrigin
public class PrecioArvejaController {
    private final PrecioArvejaService service;
    public PrecioArvejaController (PrecioArvejaService service){
        this.service = service;
    }
    @PostMapping
    public PrecioArveja guardar(@RequestBody Map<String, Double> body) {
        return service.guardarPrecio(body.get("precioBulto"));
    }

    @GetMapping("/ultimo")
    public PrecioArveja ultimo() {
        return service.ultimoPrecio();
    }

    @GetMapping
    public List<PrecioArveja> listar() {
        return service.listar();
    }
}
