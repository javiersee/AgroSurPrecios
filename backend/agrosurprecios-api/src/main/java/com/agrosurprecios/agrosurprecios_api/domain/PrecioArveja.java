package com.agrosurprecios.agrosurprecios_api.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "precios_arveja")
public class PrecioArveja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "precio_bulto", nullable = false)
    private Double precioBulto;

    @Column(nullable = false)
    private String mercado;

    protected PrecioArveja() {
        // constructor requerido por JPA
    }
    public PrecioArveja(LocalDate fecha, Double precioBulto, String mercado) {
        this.fecha = fecha;
        this.precioBulto = precioBulto;
        this.mercado = mercado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecioBulto() {
        return precioBulto;
    }

    public void setPrecioBulto(Double precioBulto) {
        this.precioBulto = precioBulto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }
}
