package incidentes.dao;

import model.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtivoDAO extends JpaRepository<Ativo, Integer> {
    List<Ativo> findByUsuarioIdOrderByIdDesc(int usuarioId);
}