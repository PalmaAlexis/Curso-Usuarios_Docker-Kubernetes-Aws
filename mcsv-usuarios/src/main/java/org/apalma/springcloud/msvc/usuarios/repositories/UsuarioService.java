package org.apalma.springcloud.msvc.usuarios.repositories;
import org.apalma.springcloud.msvc.usuarios.model.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> allUsuarios();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    void delete(Long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAllByIds(Iterable<Long> ids);
}
