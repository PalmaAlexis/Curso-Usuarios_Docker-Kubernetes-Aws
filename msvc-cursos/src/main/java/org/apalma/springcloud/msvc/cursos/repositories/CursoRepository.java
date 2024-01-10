package org.apalma.springcloud.msvc.cursos.repositories;

import org.apalma.springcloud.msvc.cursos.model.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CursoRepository extends CrudRepository<Curso, Long> {
    @Modifying
    @Query("DELETE FROM CursoUsuario cu WHERE cu.usuarioId=?1")
    void deleteCursoUsuario(Long idUser);
}
