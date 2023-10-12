package com.rodrigo.socialmedia.controller;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastrarUsuario cadastrarUsuario;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarUsuarioDTO dto, UriComponentsBuilder uriBuilder) {
        try {
            Usuario usuario = cadastrarUsuario.execute(dto);
            var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
