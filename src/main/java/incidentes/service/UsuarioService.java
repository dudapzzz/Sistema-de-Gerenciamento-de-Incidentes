package incidentes.service;

import incidentes.dao.UsuarioRepository;
import incidentes.model.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return this.repository.findAll();
    }

    // Buscar por UUID (Converte a String recebida da Web)
    @Transactional(readOnly = true)
    public Usuario buscarPorUuid(UUID uuid) {
        return this.repository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado para o UUID: " + uuid));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuario.setNome(usuario.getNome().trim());
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());

        if (usuario.getPermissao() == null || usuario.getPermissao().isBlank()){
            usuario.setPermissao("ROLE_USER");
        }else{
            usuario.setPermissao(usuario.getPermissao().trim().toUpperCase());
        }

        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        usuario.setAtivo(true);

        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizarPorUuid(UUID uuid, Usuario dados) {

        Usuario existente = buscarPorUuid(uuid);

        existente.setNome(dados.getNome().trim());
        existente.setEmail(dados.getEmail().trim().toLowerCase());

        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            existente.setSenha(dados.getSenha());
        }

        return repository.save(existente);
    }

    // Deletar por UUID
    @Transactional
    public void excluirPorUuid(UUID uuid) {
        repository.delete(buscarPorUuid(uuid));
    }

}
