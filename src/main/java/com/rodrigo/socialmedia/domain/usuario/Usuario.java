package com.rodrigo.socialmedia.domain.usuario;

import com.rodrigo.socialmedia.domain.MapperUtils;
import com.rodrigo.socialmedia.domain.post.Post;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.editar.EditarUsuarioDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Setter
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apelido;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private LocalDate dataDeNascimento;
    private String cidade;
    private String estado;
    private String pais;
    private String telefone;
    private boolean estaValidado;
    @OneToMany(mappedBy = "autor")
    private List<Post> posts = new ArrayList<>();

    public Usuario(CadastrarUsuarioDTO dto, String hashedPassword) {
        BeanUtils.copyProperties(dto, this);
        this.senha = hashedPassword;
        this.estaValidado = false;
    }

    public void atualizarDados(EditarUsuarioDTO dto) {
        MapperUtils.atualizarDados(dto, this);
        this.telefone = dto.telefone();
    }
}