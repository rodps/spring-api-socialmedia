package com.rodrigo.socialmedia.domain.usuario.autenticacao;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.infra.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuario {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public TokenDTO execute(AutenticarUsuarioDTO dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = authenticationManager.authenticate(token);

        var jwtToken = jwtService.encode((Usuario) authentication.getPrincipal(), 2);
        return new TokenDTO(jwtToken);
    }
}
