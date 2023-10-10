package com.rodrigo.socialmedia.domain.usuario;

import com.rodrigo.socialmedia.domain.post.Post;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
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
        this.apelido = dto.apelido();
        this.nome = dto.nome();
        this.sobrenome = dto.sobrenome();
        this.email = dto.email();
        this.senha = hashedPassword;
        this.dataDeNascimento = dto.dataDeNascimento();
        this.cidade = dto.cidade();
        this.pais = dto.pais();
        this.estado = dto.estado();
        this.telefone = dto.telefone();
        this.estaValidado = false;
    }
}
