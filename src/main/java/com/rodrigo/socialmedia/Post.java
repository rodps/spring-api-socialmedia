package com.rodrigo.socialmedia;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "posts")
@Getter
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mediaUrl;
    private String texto;
    private String dataDeCriacao;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;
}
