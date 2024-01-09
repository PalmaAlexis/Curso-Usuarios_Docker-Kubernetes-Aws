package org.apalma.springcloud.msvc.cursos.repositories;

import org.apalma.springcloud.msvc.cursos.model.entity.Curso;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CursoRepository extends CrudRepository<Curso, Long> {
}
