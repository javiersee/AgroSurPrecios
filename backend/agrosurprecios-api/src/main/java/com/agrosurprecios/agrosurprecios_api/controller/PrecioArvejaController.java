package com.agrosurprecios.agrosurprecios_api.controller;

import com.agrosurprecios.agrosurprecios_api.domain.HistorialSemanalDTO;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioPeriodoDTO;
import com.agrosurprecios.agrosurprecios_api.service.PrecioArvejaService;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioArveja;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/dia")
    public List<PrecioArveja> preciosPorDia(@RequestParam String fecha) {

        LocalDate localDate = LocalDate.parse(fecha);
        return service.obtenerPorDia(localDate);
    }
    @GetMapping("/promedioTotal")
    public Double promedioTotal() {
        return service.obtenerPromedioTotal();
    }
    @GetMapping("/hoy")
    public ResponseEntity<PrecioPeriodoDTO> getPreciosHoy() {
        PrecioPeriodoDTO datosHoy = service.obtenerDatosHoy();
        return ResponseEntity.ok(datosHoy);
    }

    @GetMapping("/semana")
    public ResponseEntity<PrecioPeriodoDTO> getPreciosSemana() {
        return ResponseEntity.ok(service.obtenerDatosSemana());
    }

    @GetMapping("/historialSemanal")
    public ResponseEntity<HistorialSemanalDTO> getHistorialSemanas() {
        // Llamamos al método de tu Service que acabas de crear
        HistorialSemanalDTO historial = service.obtenerHistorialSemanas();

        // Retornamos la respuesta con un estado 200 OK
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/mensual")
    public ResponseEntity<HistorialSemanalDTO> getHistorialMensual() {
        return ResponseEntity.ok(service.obtenerHistorialMeses());
    }

    @GetMapping("/anual")
    public ResponseEntity<HistorialSemanalDTO> getHistorialAnual() {
        return ResponseEntity.ok(service.obtenerHistorialAnios());
    }
}
