package org.apalma.springcloud.msvc.usuarios.controllers;
import org.apalma.springcloud.msvc.usuarios.model.entity.Usuario;
import org.apalma.springcloud.msvc.usuarios.repositories.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> allUsuarios(){
        return service.allUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.findById(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Usuario usuario, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        if(service.findByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Error: ","Email ya existe"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }



    @PutMapping("/{id}")
    public  ResponseEntity<?> edit(@RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        //VALIDANDO ANNOTATIONS
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Usuario> usuarioOptional = service.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario original= usuarioOptional.get();
            //VALIDADO EMAIL
            if(usuario.getEmail().equalsIgnoreCase(original.getEmail())  || service.findByEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("Error: ","Email ya existe"));
            }
            original.setName(usuario.getName());
            original.setEmail(usuario.getEmail());
            original.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(original));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.findById(id);
        if(usuarioOptional.isPresent()){
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errors= new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), "Error: "+error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
