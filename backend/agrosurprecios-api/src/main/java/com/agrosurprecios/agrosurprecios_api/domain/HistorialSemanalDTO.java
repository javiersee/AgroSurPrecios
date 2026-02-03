package com.agrosurprecios.agrosurprecios_api.domain;

import java.util.List;

public class HistorialSemanalDTO {
    private List<SemanaPromedio> semanas; // Ejemplo: {semana: "2026-W05", promedio: 197500}
    private Double promedioGeneralSemanas;

    public HistorialSemanalDTO(List<SemanaPromedio> semanas, Double promedioGeneralSemanas) {
        this.semanas = semanas;
        this.promedioGeneralSemanas = promedioGeneralSemanas;
    }

    public List<SemanaPromedio> getSemanas() {
        return semanas;
    }

    public void setSemanas(List<SemanaPromedio> semanas) {
        this.semanas = semanas;
    }

    public Double getPromedioGeneralSemanas() {
        return promedioGeneralSemanas;
    }

    public void setPromedioGeneralSemanas(Double promedioGeneralSemanas) {
        this.promedioGeneralSemanas = promedioGeneralSemanas;
    }
}
