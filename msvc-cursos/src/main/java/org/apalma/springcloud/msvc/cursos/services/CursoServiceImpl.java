package org.apalma.springcloud.msvc.cursos.services;

import org.apalma.springcloud.msvc.cursos.client.UsuarioClient;
import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.apalma.springcloud.msvc.cursos.model.entity.Curso;
import org.apalma.springcloud.msvc.cursos.model.entity.CursoUsuario;
import org.apalma.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private CursoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> allCourses() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> addUser(Usuario usuario, Long idCourse) {
        Optional<Curso> optionalCurso = repository.findById(idCourse);
        if(optionalCurso.isPresent()){



        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> assingUser(Usuario usuario, Long idCourse) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> deleteUser(Usuario usuario, Long idCourse) {
        return Optional.empty();
    }
}
