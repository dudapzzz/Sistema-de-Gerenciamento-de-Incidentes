package incidentes.dao;

import incidentes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    boolean existsByUuid(UUID uuid);

    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}

