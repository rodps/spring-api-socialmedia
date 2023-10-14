package com.rodrigo.socialmedia.controller;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.confirmarCadastro.ConfirmarCadastro;
import com.rodrigo.socialmedia.domain.usuario.editar.EditarUsuario;
import com.rodrigo.socialmedia.domain.usuario.editar.EditarUsuarioDTO;
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
    private ConfirmarCadastro confirmarCadastro;

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarUsuarioDTO dto, UriComponentsBuilder uriBuilder) {
        Usuario usuario = cadastrarUsuario.execute(dto);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity editar(@RequestParam("id") Long id, @RequestBody @Valid EditarUsuarioDTO dto) {
        editarUsuario.execute(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirmarCadastro/{token}")
    public ResponseEntity confimarCadastro(@RequestParam("token") String token) {
        confirmarCadastro.execute(token);
        return ResponseEntity.ok().build();
    }
}
