package org.apalma.springcloud.msvc.cursos.controllers;

import jakarta.validation.Valid;
import org.apalma.springcloud.msvc.cursos.model.entity.Curso;
import org.apalma.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> allCourses() {
        return ResponseEntity.ok(service.allCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if(optionalCurso.isPresent()){
            return ResponseEntity.ok(optionalCurso.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Curso curso, BindingResult result){


        if(result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Curso curso, BindingResult result,@PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }

        Optional<Curso> optionalCurso = service.findById(id);
        if(optionalCurso.isPresent()){
            Curso original= optionalCurso.get();
            original.setName(curso.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(original));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Curso> optionalCurso = service.findById(id);
        if(optionalCurso.isPresent()){
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores= new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "Error: "+error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
