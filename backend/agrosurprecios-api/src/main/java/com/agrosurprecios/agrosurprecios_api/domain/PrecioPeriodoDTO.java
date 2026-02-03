package com.agrosurprecios.agrosurprecios_api.domain;

import java.util.List;

public class PrecioPeriodoDTO {
    private List<PrecioArveja> registros;
    private Double promedio;
    public PrecioPeriodoDTO(List<PrecioArveja> registros, Double promedio) {
        this.registros = registros;
        this.promedio = promedio;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public List<PrecioArveja> getRegistros() {
        return registros;
    }

    public void setRegistros(List<PrecioArveja> registros) {
        this.registros = registros;
    }
}
