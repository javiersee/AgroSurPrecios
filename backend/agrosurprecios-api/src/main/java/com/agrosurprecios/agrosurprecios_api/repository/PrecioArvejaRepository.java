package com.agrosurprecios.agrosurprecios_api.repository;
import com.agrosurprecios.agrosurprecios_api.domain.PrecioArveja;
import com.agrosurprecios.agrosurprecios_api.domain.SemanaPromedio;
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

    // ✅ Arreglo para la lista de hoy
    @Query("SELECT p FROM PrecioArveja p WHERE CAST(p.fecha AS date) = CURRENT_DATE")
    List<PrecioArveja> findByFechaHoy();

    // ✅ Arreglo para el promedio de hoy
    @Query("SELECT AVG(p.precioBulto) FROM PrecioArveja p WHERE CAST(p.fecha AS date) = CURRENT_DATE")
    Double findPromedioHoy();

    // Registros en un rango de fechas (Sirve para semana, mes, año)
    @Query("SELECT p FROM PrecioArveja p WHERE p.fecha >= :inicio AND p.fecha <= :fin")
    List<PrecioArveja> findByRangoFechas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    // Promedio en un rango de fechas
    @Query("SELECT AVG(p.precioBulto) FROM PrecioArveja p WHERE p.fecha >= :inicio AND p.fecha <= :fin")
    Double findPromedioByRango(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    @Query(value = "SELECT to_char(fecha, 'IYYY-\"W\"IW') as semana, " +
            "AVG(precio_bulto) as promedio " +
            "FROM precios_arveja " + // ✅ DEBE SER: precios_arveja (plural y minúsculas)
            "GROUP BY semana " +
            "ORDER BY semana DESC", nativeQuery = true)
    List<SemanaPromedio> findPromediosAgrupadosPorSemana();

    @Query(value = "SELECT to_char(fecha, 'YYYY-MM') as mes, " +
            "AVG(precio_bulto) as promedio " +
            "FROM precios_arveja " +
            "GROUP BY mes " +
            "ORDER BY mes DESC", nativeQuery = true)
    List<SemanaPromedio> findPromediosAgrupadosPorMes();

    @Query(value = "SELECT to_char(fecha, 'YYYY') as anio, " +
            "AVG(precio_bulto) as promedio " +
            "FROM precios_arveja " +
            "GROUP BY anio " +
            "ORDER BY anio DESC", nativeQuery = true)
    List<SemanaPromedio> findPromediosAgrupadosPorAnio();
}
