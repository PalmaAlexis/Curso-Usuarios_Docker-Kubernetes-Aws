package org.apalma.springcloud.msvc.cursos.services;

import org.apalma.springcloud.msvc.cursos.client.UsuarioClient;
import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.apalma.springcloud.msvc.cursos.model.entity.Curso;
import org.apalma.springcloud.msvc.cursos.model.entity.CursoUsuario;
import org.apalma.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    //@Transactional(readOnly = true)
    public Optional<Curso> allUsersByCourse(Long courseId) {
        Optional<Curso> optionalCurso = repository.findById(courseId);
        if(optionalCurso.isPresent()){
            Curso curso= optionalCurso.get();
            if(!curso.getCursoUsuarios().isEmpty()){ //avoiding one consult through mcsv by feign
                List<Long> ids= curso.getCursoUsuarios().stream().map( cu -> cu.getUsuarioId()).toList();
                List<Usuario> users= usuarioClient.findByAllByIds(ids);
                curso.setUsuarioList(users);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
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
    @Transactional
    public void deleteCursoUsuario(Long userId) {
        repository.deleteCursoUsuario(userId);
    }

    @Override
    @Transactional
    public Optional<Usuario> createUser(Usuario usuario, Long courseId) {
        Optional<Curso> optionalCurso = repository.findById(courseId);
        if (optionalCurso.isPresent()) {
            //creating user
            Usuario usuarioMsvc = usuarioClient.save(usuario);
            //creating join for assigning user to course
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            //assigning user to course
            Curso curso = optionalCurso.get();
            curso.addUsuarioList(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> assignUser(Usuario usuario, Long courseId) {
        Optional<Curso> optionalCurso = repository.findById(courseId);
        if (optionalCurso.isPresent()) {
            //finding user by id
            Usuario usuarioMcsv = usuarioClient.findUserById(usuario.getId());
            //crating join
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMcsv.getId());
            //creating course
            Curso curso = optionalCurso.get();
            //adding user to course
            curso.addUsuarioList(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMcsv);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> unassignUser(Usuario usuario, Long courseId) {
        Optional<Curso> optionalCurso = repository.findById(courseId);
        if (optionalCurso.isPresent()) {
            //finding user by id
            Usuario usuarioMcsv = usuarioClient.findUserById(usuario.getId());
            //creating join
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMcsv.getId());
            //creating course
            Curso curso = optionalCurso.get();
            //delete contains equals, and it gonna compare the ids
            curso.deleteUsuarioList(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMcsv);
        }
        return Optional.empty();
    }
}
