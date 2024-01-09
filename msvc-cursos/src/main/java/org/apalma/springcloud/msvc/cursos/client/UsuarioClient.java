package org.apalma.springcloud.msvc.cursos.client;

import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.apalma.springcloud.msvc.cursos.model.entity.Curso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "mcsv-usuarios", url = "localhost:8001")
public interface UsuarioClient {
    @GetMapping("/{id}")
    Usuario findUserById(@PathVariable Long id);

    @PostMapping
    Usuario save(Usuario usuario);

    @GetMapping("/all-users-by-ids")
    List<Usuario> findByAllByIds(@RequestParam Iterable<Long> ids);
}
