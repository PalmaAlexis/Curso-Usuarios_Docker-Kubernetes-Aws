package org.apalma.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.apalma.springcloud.msvc.cursos.model.entity.Curso;
import org.apalma.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> allCourses() {
        return ResponseEntity.ok(service.allCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isPresent()) {
            return ResponseEntity.ok(optionalCurso.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isPresent()) {
            Curso original = optionalCurso.get();
            original.setName(curso.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(original));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody Usuario usuario, @PathVariable Long courseId) {
        Optional<Usuario> optionalJoin = null;
        try {
            optionalJoin = service.createUser(usuario, courseId);
        } catch (FeignException e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("Error: ", "error en la comunicación: " + e.getMessage()));
        }

        if (optionalJoin.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalJoin.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody Usuario usuario, @PathVariable Long courseId) {
        Optional<Usuario> optionalJoin = null;
        try {
            optionalJoin = service.assignUser(usuario, courseId);
        } catch (FeignException e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("Error: ", "error en la comunicación: " + e.getMessage()));
        }

        if (optionalJoin.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalJoin.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/unassign-user/{courseId}")
    public ResponseEntity<?> unassignUser(@RequestBody Usuario usuario, @PathVariable Long courseId) {
        Optional<Usuario> optionalJoin = null;
        try {
            optionalJoin = service.unassignUser(usuario, courseId);
        } catch (FeignException e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("Error: ", "error en la comunicación: " + e.getMessage()));
        }
        if (optionalJoin.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users-by-course/{idCourse}")
    public ResponseEntity<?> allUsersByCourse(@PathVariable Long idCourse) {
        try{
            Optional<Curso> curso = service.allUsersByCourse(idCourse);
            if(curso.isPresent()){
                return ResponseEntity.ok().body(curso);
            }
            return ResponseEntity.notFound().build();
        }catch (FeignException e){
            return ResponseEntity.internalServerError().body(Collections.singletonMap("Error: ","error de comunicación: "+e.getMessage()));
        }
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "Error: " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
