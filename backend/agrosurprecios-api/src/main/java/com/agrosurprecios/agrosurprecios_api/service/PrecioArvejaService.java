package com.agrosurprecios.agrosurprecios_api.service;

import com.agrosurprecios.agrosurprecios_api.domain.HistorialSemanalDTO;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioPeriodoDTO;
import com.agrosurprecios.agrosurprecios_api.domain.SemanaPromedio;
import com.agrosurprecios.agrosurprecios_api.repository.PrecioArvejaRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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
    public List<PrecioArveja> obtenerPorDia(LocalDate fecha) {

        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23,59,59);

        return repository.findByFechaBetween(inicio, fin);
    }
    public Double obtenerPromedioTotal() {
        Double promedio = repository.findAveragePrecioBultoTotal();
        // Si no hay registros, devolvemos 0.0 en lugar de null
        return (promedio != null) ? promedio : 0.0;
    }

    public PrecioPeriodoDTO obtenerDatosHoy() {
        List<PrecioArveja> registros = repository.findByFechaHoy();
        Double promedio = repository.findPromedioHoy();
        // Si no hay datos, el promedio será null, lo manejamos como 0.0
        return new PrecioPeriodoDTO(registros, promedio != null ? promedio : 0.0);
    }

    public PrecioPeriodoDTO obtenerDatosSemana() {
        LocalDateTime ahora = LocalDateTime.now();
        // Obtener el lunes de esta semana a las 00:00:00
        LocalDateTime inicioSemana = ahora.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0).withMinute(0).withSecond(0);
        // Hasta el momento actual
        LocalDateTime finSemana = ahora;

        List<PrecioArveja> registros = repository.findByRangoFechas(inicioSemana, finSemana);
        Double promedio = repository.findPromedioByRango(inicioSemana, finSemana);

        return new PrecioPeriodoDTO(registros, promedio != null ? promedio : 0.0);
    }

    public HistorialSemanalDTO obtenerHistorialSemanas() {
        List<SemanaPromedio> promediosSemanales = repository.findPromediosAgrupadosPorSemana();


        Double promedioGeneral = promediosSemanales.stream()
                .mapToDouble(SemanaPromedio::getPromedio)
                .average()
                .orElse(0.0);

        return new HistorialSemanalDTO(promediosSemanales, promedioGeneral);
    }

    public HistorialSemanalDTO obtenerHistorialMeses() {
        List<SemanaPromedio> promediosMensuales = repository.findPromediosAgrupadosPorMes();

        Double promedioGeneral = promediosMensuales.stream()
                .mapToDouble(SemanaPromedio::getPromedio)
                .average()
                .orElse(0.0);

        return new HistorialSemanalDTO(promediosMensuales, promedioGeneral);
    }

    public HistorialSemanalDTO obtenerHistorialAnios() {
        List<SemanaPromedio> promediosAnuales = repository.findPromediosAgrupadosPorAnio();

        Double promedioGeneral = promediosAnuales.stream()
                .mapToDouble(SemanaPromedio::getPromedio)
                .average()
                .orElse(0.0);

        return new HistorialSemanalDTO(promediosAnuales, promedioGeneral);
    }
}
