package com.rodrigo.socialmedia.domain.usuario.mudarSenha;

import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MudarSenha {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(Long id, MudarSenhaDTO dto) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        String senhaAtual = usuario.getSenha();
        if (senhaAtual.equals(passwordEncoder.encode(dto.senhaAtual()))) {
            usuario.setSenha(dto.novaSenha());
        } else {
            throw new ValidacaoException("Senha atual incorreta");
        }
    }
}
