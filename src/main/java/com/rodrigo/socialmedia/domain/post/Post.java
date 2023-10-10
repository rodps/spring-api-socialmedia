package com.rodrigo.socialmedia.domain.post;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mediaUrl;
    private String texto;
    private LocalDate dataDeCriacao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;
}
