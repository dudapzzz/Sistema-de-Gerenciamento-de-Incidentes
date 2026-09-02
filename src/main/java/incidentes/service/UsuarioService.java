package incidentes.service;

import incidentes.dao.UsuarioRepository;
import incidentes.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario inserir(Usuario u) {
        if (u.getNome() == null || u.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório");
        }
        if (u.getEmail() == null || !u.getEmail().contains("@")) {
           throw new IllegalArgumentException("O e-mail é obrigatório");
        }
        if (u.getSenha() == null || u.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
        }

        u.setNome(u.getNome().trim());
        u.setEmail(u.getEmail().trim().toLowerCase());
        u.setAtivo(true);

        return repository.save(u);
    }

    public Usuario autenticar(String email, String senha){
        if(email == null || senha== null){
            throw new IllegalArgumentException("E-mail e senha são obrigatórios");
        }
        Optional<Usuario> usuarioOpt= repository.findByEmailAndSenha(email.trim().toLowerCase(),senha);
        return usuarioOpt.orElse(null);
    }
    public Usuario buscarPorId(int id){
        return repository.findById(id).orElse(null);
    }
}
