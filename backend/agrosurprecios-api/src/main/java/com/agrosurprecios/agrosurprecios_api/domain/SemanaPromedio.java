package com.agrosurprecios.agrosurprecios_api.domain;

public class SemanaPromedio {
    private String mercado;
    private Double promedio;



    public SemanaPromedio(String mercado, Double promedio) {
        this.mercado = mercado;
        this.promedio = promedio;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }
}
