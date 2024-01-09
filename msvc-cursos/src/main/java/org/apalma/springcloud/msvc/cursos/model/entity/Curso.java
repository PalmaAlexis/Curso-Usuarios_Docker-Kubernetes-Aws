package org.apalma.springcloud.msvc.cursos.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.apalma.springcloud.msvc.cursos.model.Usuario;
import org.springframework.data.annotation.Persistent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CursoUsuario> cursoUsuarios;


    @Transient
    private List<Usuario> usuarioList;

    public Curso() {
        cursoUsuarios= new ArrayList<>();
        usuarioList= new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public void setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public void addUsuarioList(CursoUsuario cursoUsuario){
        cursoUsuarios.add(cursoUsuario);
    }

    public void deleteUsuarioList(CursoUsuario cursoUsuario){
        cursoUsuarios.remove(cursoUsuario);
    }
}
