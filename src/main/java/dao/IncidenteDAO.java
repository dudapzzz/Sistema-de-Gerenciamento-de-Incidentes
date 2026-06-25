package dao;

import model.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenteDAO extends JpaRepository<Incidente, Integer> {
    List<Incidente> findByUsuarioIdOrderByCodigoDesc(int usuarioId);
    List <Incidente> findTop3ByUsuarioIdOrderByCodigoDesc(int usuarioId);

    long countByStatusIgnoreCaseAndUsuarioId(String status, int usuarioId);
    long countByRelevanciaIgnoreCaseAndUsuarioId(String relevancia, int usuarioId);

    @Query("SELECT i.status, COUNT(i) FROM Incidente i WHERE i.usuarioId = :usuarioId GROUP BY i.status")
    List<Object[]> getEstatisticasStatusRaw(@Param("usuarioId") int usuarioId);

    @Query("SELECT i.relevancia, COUNT(i) FROM Incidente i WHERE i.usuarioId = :usuarioId GROUP BY i.relevancia")
    List<Object[]> getEstatisticasRelevanciaRaw(@Param("usuarioId") int usuarioId);
}