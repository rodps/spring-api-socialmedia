package com.rodrigo.socialmedia;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@EqualsAndHashCode
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private Date dataDeNascimento;
    private String cidade;
    private String estado;
    private String pais;
    private String telefone;
    private boolean estaValidado;
    @OneToMany(mappedBy = "autor")
    private List<Post> posts = new ArrayList<>();
}
