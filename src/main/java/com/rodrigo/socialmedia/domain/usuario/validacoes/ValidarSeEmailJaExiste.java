package com.rodrigo.socialmedia.domain.usuario.validacoes;

import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarSeEmailJaExiste {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(String email) {
        var usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            throw new ValidacaoException("Este email já está sendo utilizado por outro usuário.");
        }
    }
}
