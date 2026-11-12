package incidentes.dao;

import incidentes.model.Criticidade;
import incidentes.model.Incidente;
import incidentes.model.StatusIncidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    Optional<Incidente> findByUuid(UUID uuid);

    List<Incidente> findByUsuario_UuidOrderByCodigoDesc(UUID usuarioUuid);

    List<Incidente> findTop3ByUsuario_UuidOrderByCodigoDesc(UUID usuarioUuid);

    long countByStatusAndUsuario_Uuid(
            StatusIncidente status,
            UUID usuarioUuid
    );

    long countBySeveridadeAndUsuario_Uuid(
            Criticidade severidade,
            UUID usuarioUuid
    );

    @Query("""
            SELECT i.status, COUNT(i)
            FROM Incidente i
            WHERE i.usuario.uuid = :usuarioUuid
            GROUP BY i.status
            """)
    List<Object[]> getEstatisticasStatusRaw(
            @Param("usuarioUuid") UUID usuarioUuid
    );

    @Query("""
            SELECT i.severidade, COUNT(i)
            FROM Incidente i
            WHERE i.usuario.uuid = :usuarioUuid
            GROUP BY i.severidade
            """)
    List<Object[]> getEstatisticasSeveridadeRaw(
            @Param("usuarioUuid") UUID usuarioUuid
    );
}
