package incidentes.dao;

import incidentes.model.Incidente;
import incidentes.model.NivelSeveridade;
import incidentes.model.StatusIncidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    Optional<Incidente> findByUuid(UUID uuid);

    List<Incidente> findByUsuarioUuidOrderByCodigoDesc(UUID usuarioId);
    List <Incidente> findTop3ByUsuarioUuidOrderByCodigoDesc(UUID usuarioId);

    long countByStatusAndUsuarioUuid(StatusIncidente status, UUID usuarioId);
    long countBySeveridadeAndUsuarioUuid(NivelSeveridade severidade, UUID usuarioId);

    @Query("SELECT i.status, COUNT(i) FROM Incidente i WHERE i.usuario.codigo = :usuarioId GROUP BY i.status")
    List<Object[]> getEstatisticasStatusRaw(@Param("usuarioUuid") UUID usuarioUuid);

    @Query("SELECT i.severidade, COUNT(i) FROM Incidente i WHERE i.usuario.uuid = :usuario.uuid GROUP BY i.severidade")
    List<Object[]> getEstatisticasSeveridadeRaw(@Param("usuarioUuid") int usuarioUuid);
}
