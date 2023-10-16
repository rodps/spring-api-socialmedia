package com.rodrigo.socialmedia.controller;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.autenticacao.AutenticarUsuario;
import com.rodrigo.socialmedia.domain.usuario.autenticacao.AutenticarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.editar.EditarUsuario;
import com.rodrigo.socialmedia.domain.usuario.editar.EditarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.mudarSenha.MudarSenha;
import com.rodrigo.socialmedia.domain.usuario.mudarSenha.MudarSenhaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private CadastrarUsuario cadastrarUsuario;

    @Autowired
    private EditarUsuario editarUsuario;

    @Autowired
    private MudarSenha mudarSenha;

    @Autowired
    private AutenticarUsuario autenticarUsuario;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarUsuarioDTO dto, UriComponentsBuilder uriBuilder) {
        Usuario usuario = cadastrarUsuario.execute(dto);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity editar(@PathVariable("id") Long id, @RequestBody @Valid EditarUsuarioDTO dto) {
        editarUsuario.execute(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/mudarSenha")
    public ResponseEntity mudarSenha(@PathVariable("id") Long id, @RequestBody @Valid MudarSenhaDTO dto) {
        mudarSenha.execute(id, dto);
        return ResponseEntity.ok().build();
    }
}
