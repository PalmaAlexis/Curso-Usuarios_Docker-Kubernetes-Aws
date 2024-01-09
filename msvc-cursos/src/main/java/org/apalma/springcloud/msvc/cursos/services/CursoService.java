package org.apalma.springcloud.msvc.cursos.services;

import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.apalma.springcloud.msvc.cursos.model.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> allCourses();
    Optional<Curso> findById(Long id);
    Curso save(Curso curso);
    void delete(Long id);
    Optional<Usuario> addUser(Usuario usuario, Long idCourse); //add
    Optional<Usuario> assingUser(Usuario usuario, Long idCourse); //assing to a course
    Optional<Usuario> deleteUser(Usuario usuario, Long idCourse); //delete from a course
}
