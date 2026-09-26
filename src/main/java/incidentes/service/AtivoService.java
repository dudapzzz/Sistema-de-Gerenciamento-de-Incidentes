package incidentes.service;

import incidentes.dao.AtivoRepository;
import incidentes.dao.UsuarioRepository;
import incidentes.model.Ativo;
import incidentes.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class AtivoService {

    private final AtivoRepository dao;
    private final UsuarioRepository usuarioRepository;

    public AtivoService(AtivoRepository dao, UsuarioRepository usuarioRepository) {
        this.dao = dao;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Ativo> getAtivos() {
        return dao.findAll();
    }

    public List<Ativo> listarPorUsuarioUuid(String usuarioUuidStr) {
        UUID uuidFormatado = UUID.fromString(usuarioUuidStr);

        if (!usuarioRepository.existsByUuid(uuidFormatado)) {
            throw new NoSuchElementException("Usuário não encontrado para o UUID: " + usuarioUuidStr);
        }

        return dao.findByUsuarioUuidOrderByIdDesc(uuidFormatado);
    }
    public Ativo getAtivoByUuid(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return dao.findByUuid(uuid).orElseThrow(() -> new NoSuchElementException("Ativo nao encontrado para o uuid: " +uuidStr));
    }

    public Ativo saveAtivo(Ativo ativo) {
        if (ativo.getUsuario() != null && ativo.getUsuario().getUuid() != null) {
            Usuario usuario = usuarioRepository.findByUuid(ativo.getUsuario().getUuid())
                    .orElseThrow(() -> new NoSuchElementException("Usuário informado para o ativo não foi encontrado"));
            ativo.setUsuario(usuario);
        }

        if (ativo.getNome() != null) ativo.setNome(ativo.getNome().trim());
        if (ativo.getTipo() != null) ativo.setTipo(ativo.getTipo().trim());
        if (ativo.getIpOuUrl() != null) ativo.setIpOuUrl(ativo.getIpOuUrl().trim());

        return dao.save(ativo);
    }
    public Ativo updateAtivoByUuid(Ativo ativo) {
        if (ativo.getUuid() == null) {
            throw new IllegalArgumentException("UUID é obrigatório para atualização");
        }

        Ativo ativoExistente = getAtivoByUuid(ativo.getUuid().toString());
        ativo.setId(ativoExistente.getId());
        ativo.setUuid(ativoExistente.getUuid());
        if (ativo.getUsuario() == null) {
            ativo.setUsuario(ativoExistente.getUsuario());
        } else {
            Usuario usuario = usuarioRepository.findById(ativo.getUsuario().getCodigo())
                    .orElseThrow(() -> new NoSuchElementException("Usuário do ativo não encontrado"));
            ativo.setUsuario(usuario);
        }

        return dao.save(ativo);
    }

    public boolean excluirPorUuid(String uuidStr) {
        Ativo ativo = getAtivoByUuid(uuidStr);
        dao.delete(ativo);
        return true;
    }



}
