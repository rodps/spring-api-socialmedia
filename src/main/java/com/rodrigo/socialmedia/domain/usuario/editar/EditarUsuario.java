package com.rodrigo.socialmedia.domain.usuario.editar;

import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditarUsuario {

    @Autowired
    UsuarioRepository usuarioRepository;

    public void execute(Long id, EditarUsuarioDTO dto) {
        usuarioRepository.getReferenceById(id).atualizarDados(dto);
    }
}
