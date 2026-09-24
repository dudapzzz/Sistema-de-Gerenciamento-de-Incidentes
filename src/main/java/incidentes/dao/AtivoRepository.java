package incidentes.dao;

import incidentes.model.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Integer> {
    Optional<Ativo> findByUuid(UUID uuid);
    List<Ativo> findByUsuarioIdOrderByIdDesc(int usuarioId);
}