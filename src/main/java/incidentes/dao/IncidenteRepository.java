package incidentes.dao;

import incidentes.model.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Integer> {

    Optional<Incidente> findByUuid(UUID uuid);

    List<Incidente> findByUsuario_CodigoOrderByCodigoDesc(int usuarioId);
    List <Incidente> findTop3ByUsuario_CodigoOrderByCodigoDesc(int usuarioId);

    long countByStatusIgnoreCaseAndUsuario_Codigo(String status, int usuarioId);
    long countByRelevanciaIgnoreCaseAndUsuario_Codigo(String relevancia, int usuarioId);

    @Query("SELECT i.status, COUNT(i) FROM Incidente i WHERE i.usuario.codigo = :usuarioId GROUP BY i.status")
    List<Object[]> getEstatisticasStatusRaw(@Param("usuarioId") int usuarioId);

    @Query("SELECT i.relevancia, COUNT(i) FROM Incidente i WHERE i.usuario.codigo = :usuarioId GROUP BY i.relevancia")
    List<Object[]> getEstatisticasRelevanciaRaw(@Param("usuarioId") int usuarioId);
}
