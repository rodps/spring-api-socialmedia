package com.rodrigo.socialmedia.controller;

import com.rodrigo.socialmedia.domain.usuario.autenticacao.AutenticarUsuario;
import com.rodrigo.socialmedia.domain.usuario.autenticacao.AutenticarUsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class AutenticacaoController {

    @Autowired
    private AutenticarUsuario autenticarUsuario;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticarUsuarioDTO dto) {
        var token = autenticarUsuario.execute(dto);
        return ResponseEntity.ok(token);
    }
}
