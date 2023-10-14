package com.rodrigo.socialmedia.domain.usuario.confirmarCadastro;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.infra.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmarCadastro {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public void execute(String token) {
        String email = jwtService.decode(token);
        usuarioRepository.setEstaValidadoTrue(email);
    }
}
