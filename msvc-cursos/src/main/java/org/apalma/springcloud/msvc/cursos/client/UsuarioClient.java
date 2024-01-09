package org.apalma.springcloud.msvc.cursos.client;

import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mcsv-usuarios", url = "localhost:8001")
public interface UsuarioClient {

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id);

    @PostMapping
    public Usuario save(Usuario usuario, BindingResult result);
}
