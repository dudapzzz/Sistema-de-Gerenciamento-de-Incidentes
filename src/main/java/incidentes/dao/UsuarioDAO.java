package incidentes.dao;

import model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}