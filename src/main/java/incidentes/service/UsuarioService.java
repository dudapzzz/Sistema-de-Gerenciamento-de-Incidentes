package incidentes.service;

import incidentes.dao.UsuarioRepository;
import incidentes.model.Usuario;
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
    public List<Usuario> getUsuarios() {
        return this.repository.findAll();
    }

    // Buscar por UUID (Converte a String recebida da Web)
    @Transactional(readOnly = true)
    public Usuario getUsuarioUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return this.repository.findByUuid(uuidFormatado)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado para o UUID: " + uuid));
    }

    @Transactional
    public Usuario inserir(Usuario usuario) {
        if (usuario.getUuid() == null) {
            usuario.setUuid(UUID.randomUUID());
        }
        usuario.setAtivo(true);
        return this.repository.save(usuario);
    }

    // Atualizar usando o UUID contido no objeto
    @Transactional
    public Usuario atualizarUUID(Usuario usuario) {
        if (usuario.getUuid() == null) {
            throw new IllegalArgumentException("UUID é obrigatório para atualização");
        }
        Usuario existente = this.repository.findByUuid(usuario.getUuid())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        existente.setNome(usuario.getNome());
        existente.setEmail(usuario.getEmail());
        existente.setSenha(usuario.getSenha());

        return this.repository.save(existente);
    }

    // Deletar por UUID
    @Transactional
    public void deletarUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        if (!this.repository.existsByUuid(uuidFormatado)) {
            throw new NoSuchElementException("Usuário não encontrado para o UUID: " + uuid);
        }
        this.repository.deleteByUuid(uuidFormatado);
    }

    // Autenticação / Login
    public Usuario autenticar(String email, String senha) {
        return this.repository.findByEmailAndSenha(email, senha)
                .orElse(null);
    }
}
