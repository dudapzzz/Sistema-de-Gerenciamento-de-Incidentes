package incidentes.service;

import incidentes.dao.AtivoRepository;
import incidentes.dao.UsuarioRepository;
import incidentes.model.Ativo;
import incidentes.model.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class AtivoService {

    private final AtivoRepository ativoRepository;
    private final UsuarioRepository usuarioRepository;

    public AtivoService(AtivoRepository ativoRepository, UsuarioRepository usuarioRepository) {
        this.ativoRepository = ativoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Ativo> listarTodos(){
        return ativoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Ativo> listarPorUsuarioUuid(UUID usuarioUuid) {
        validarUsuario(usuarioUuid);
        return ativoRepository.findByUsuario_UuidOrderByIdDesc(usuarioUuid);
    }

    @Transactional(readOnly = true)
    public Ativo buscarPorUuid(UUID uuid) {
        return ativoRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Ativo nao encontrado para o uuid: " +uuid));
    }

    @Transactional
    public Ativo salvar(Ativo ativo) {
        if (ativo.getUsuario() == null || ativo.getUsuario().getUuid() == null) {
            throw new IllegalArgumentException("O usuário do ativo é obrigatório.");
        }

        Usuario usuario = buscarUsuario(ativo.getUsuario().getUuid());
        ativo.setUsuario(usuario);
        normalizarCampos(ativo);

        return ativoRepository.save(ativo);
    }

    @Transactional
    public Ativo atualizarPorUuid(UUID uuid, Ativo dados) {
        Ativo existente = buscarPorUuid(uuid);

        existente.setNome(dados.getNome().trim());
        existente.setTipo(dados.getTipo());
        existente.setIpOuUrl(dados.getIpOuUrl());
        existente.setCriticidade(dados.getCriticidade());

        if (dados.getUsuario() != null && dados.getUsuario().getUuid() != null) {
            existente.setUsuario(buscarUsuario(dados.getUsuario().getUuid()));
        }

        normalizarCampos(existente);
        return ativoRepository.save(existente);
    }

    @Transactional
    public void excluirPorUuid(UUID uuid) {
        Ativo ativo = buscarPorUuid(uuid);
        ativoRepository.delete(ativo);
    }

    private Usuario buscarUsuario(UUID uuid) {
        return usuarioRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado: " + uuid)
                );
    }

    private void validarUsuario(UUID uuid) {
        if (!usuarioRepository.existsByUuid(uuid)) {
            throw new EntityNotFoundException("Usuário não encontrado: " + uuid);
        }
    }

    private void normalizarCampos(Ativo ativo) {
        if (ativo.getNome() != null) {
            ativo.setNome(ativo.getNome().trim());
        }
        if (ativo.getIpOuUrl() != null) {
            ativo.setIpOuUrl(ativo.getIpOuUrl().trim());
        }
    }
}


