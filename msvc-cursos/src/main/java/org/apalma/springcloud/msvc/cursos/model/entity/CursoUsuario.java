package org.apalma.springcloud.msvc.cursos.model.entity;

import jakarta.persistence.*;

//join
@Entity
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    //comparing id
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof  CursoUsuario)){
            return false;
        }
        if(this==obj){
            return true;
        }
        CursoUsuario cursoUsuario= (CursoUsuario) obj;
        return null!=this.usuarioId && this.usuarioId.equals(cursoUsuario.usuarioId);
    }
}
