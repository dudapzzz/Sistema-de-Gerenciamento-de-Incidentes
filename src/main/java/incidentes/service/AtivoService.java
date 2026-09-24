package incidentes.service;

import incidentes.dao.AtivoRepository;
import incidentes.model.Ativo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class AtivoService {

    private final AtivoRepository dao;

    public AtivoService(AtivoRepository dao) {
        this.dao = dao;
    }

    public List<Ativo> getAtivos() {
        return dao.findAll();
    }

    public List<Ativo> listarPorUsuario(int usuarioId) {
        return dao.findByUsuarioIdOrderByIdDesc(usuarioId);
    }

    public Ativo getAtivoByUuid(String uuidStr) {
        UUID uuid = UUID.fromString(uuidStr);
        return dao.findByUuid(uuid).orElseThrow(() -> new NoSuchElementException("Ativo nao encontrado para o uuid: " +uuidStr));
    }

    public Ativo saveAtivo(Ativo ativo)
    {
        if(ativo.getNome() == null || ativo.getNome().trim().isEmpty()){
            throw new IllegalArgumentException("O nome do ativo é obrigatório");
        }
        if(ativo.getTipo() == null || ativo.getTipo().trim().isEmpty()){
            throw new IllegalArgumentException("o tipo do ativo é obrigatório");
        }
        if (ativo.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("O usuário do ativo é obrigatório");
        }

        ativo.setNome(ativo.getNome().trim());
        ativo.setTipo(ativo.getTipo().trim());

        if (ativo.getIpOuUrl() != null){
            ativo.setIpOuUrl(ativo.getIpOuUrl().trim());
        }

        return dao.save(ativo);
    }

    public Ativo updateAtivoByUuid(Ativo ativo) {
        if (ativo.getUuid() == null) {
            throw new IllegalArgumentException("UUID é obrigatório para atualização");
        }

        Ativo ativoExistente = getAtivoByUuid(ativo.getUuid().toString());
        ativo.setId(ativoExistente.getId());
        ativo.setUuid(ativoExistente.getUuid());

        return dao.save(ativo);
    }

    public boolean excluirPorUuid(String uuidStr) {
        Ativo ativo = getAtivoByUuid(uuidStr);
        dao.delete(ativo);
        return true;
    }



}
