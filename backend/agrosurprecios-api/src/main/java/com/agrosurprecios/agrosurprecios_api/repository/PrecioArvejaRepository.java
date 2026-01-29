package com.agrosurprecios.agrosurprecios_api.repository;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioArveja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface PrecioArvejaRepository extends JpaRepository<PrecioArveja, Long> {
    List<PrecioArveja> findAllByOrderByFechaDesc();
    List<PrecioArveja> findByFechaBetween (LocalDateTime inicio, LocalDateTime fin);
}
