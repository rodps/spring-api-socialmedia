package com.rodrigo.socialmedia.domain.usuario.validacoes;

import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarSeApelidoJaExiste {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(String apelido) {
        var usuario = usuarioRepository.findByApelido(apelido);
        if (usuario != null) {
            throw new ValidacaoException("Este apelido já está sendo utilizado por outro usuário.");
        }
    }
}
