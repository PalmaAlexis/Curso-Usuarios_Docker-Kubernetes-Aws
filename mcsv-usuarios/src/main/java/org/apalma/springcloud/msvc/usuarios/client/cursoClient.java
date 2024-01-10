package org.apalma.springcloud.msvc.usuarios.client;
import org.apalma.springcloud.msvc.usuarios.model.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-cursos", url = "localhost:8002")
public interface cursoClient {

    @DeleteMapping("/delete-user/{userId}")
    public void deleteCursoUsuario(@PathVariable Long userId);
}
