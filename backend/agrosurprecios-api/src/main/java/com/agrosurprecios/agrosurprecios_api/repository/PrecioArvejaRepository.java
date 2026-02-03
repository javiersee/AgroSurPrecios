package com.agrosurprecios.agrosurprecios_api.repository;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioArveja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrecioArvejaRepository extends JpaRepository<PrecioArveja, Long> {
    List<PrecioArveja> findAllByOrderByFechaDesc();
    List<PrecioArveja> findByFechaBetween (LocalDateTime inicio, LocalDateTime fin);
    // Método para el promedio
    @Query("SELECT AVG(p.precioBulto) FROM PrecioArveja p WHERE p.fecha BETWEEN :inicio AND :fin")
    Double findAveragePrecioBultoBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    @Query("SELECT AVG(p.precioBulto) FROM PrecioArveja p")
    Double findAveragePrecioBultoTotal();
}
