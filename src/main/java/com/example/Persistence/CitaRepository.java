package com.example.Persistence;

import com.example.Logic.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findAllByOrderByFechaAscHoraAsc();

    @Query("select case when count(c) > 0 then true else false end from Cita c where c.mascota.num_cliente = :mascotaId")
    boolean existePorMascota(@Param("mascotaId") Long mascotaId);
}
