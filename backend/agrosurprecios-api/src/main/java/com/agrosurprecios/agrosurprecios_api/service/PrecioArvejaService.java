package com.agrosurprecios.agrosurprecios_api.service;

import com.agrosurprecios.agrosurprecios_api.repository.PrecioArvejaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import  com.agrosurprecios.agrosurprecios_api.domain.PrecioArveja;

@Service
public class PrecioArvejaService {


    private final PrecioArvejaRepository repository;

    public PrecioArvejaService(PrecioArvejaRepository repository) {
        this.repository = repository;
    }

    public PrecioArveja guardarPrecio(Double precioBulto) {

        if (precioBulto == null || precioBulto <= 0) {
            throw new IllegalArgumentException("El precio por bulto debe ser mayor a cero");
        }

        PrecioArveja precio = new PrecioArveja(
                LocalDateTime.now(),
                precioBulto,
                "Ipiales"
        );

        return repository.save(precio);
    }

    public List<PrecioArveja> listar() {
        return repository.findAllByOrderByFechaDesc();
    }

    public PrecioArveja ultimoPrecio() {
        List<PrecioArveja> lista = repository.findAllByOrderByFechaDesc();
        return lista.isEmpty() ? null : lista.get(0);
    }
}
