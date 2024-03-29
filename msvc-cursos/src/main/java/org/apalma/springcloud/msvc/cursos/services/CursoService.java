package org.apalma.springcloud.msvc.cursos.services;

import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.apalma.springcloud.msvc.cursos.model.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> allCourses();

    Optional<Curso> findById(Long id);

    Optional<Curso> allUsersByCourse(Long courseId);

    Curso save(Curso curso);

    void delete(Long id);

    void deleteCursoUsuario(Long userId);

    Optional<Usuario> createUser(Usuario usuario, Long courseId); //creating and assigning it
    //we can also implement one with userId and courseId for assign and un-assign
    Optional<Usuario> assignUser(Usuario usuario, Long courseId); //just assigning it
    Optional<Usuario> unassignUser(Usuario usuario, Long courseId); //unassigning it

}



