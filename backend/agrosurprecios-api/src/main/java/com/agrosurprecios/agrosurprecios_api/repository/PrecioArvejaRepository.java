package com.agrosurprecios.agrosurprecios_api.repository;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioArveja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
public interface PrecioArvejaRepository extends JpaRepository<PrecioArveja, Long> {
    Optional<PrecioArveja> findByFecha(LocalDate fecha);
}
