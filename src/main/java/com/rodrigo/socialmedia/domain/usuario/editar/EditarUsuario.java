package com.rodrigo.socialmedia.domain.usuario.editar;

import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarIdade;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeApelidoJaExiste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditarUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidarSeApelidoJaExiste validarSeApelidoJaExiste;

    @Autowired
    private ValidarIdade validarIdade;

    public void execute(Long id, EditarUsuarioDTO dto) {
        validarIdade.validar(dto.dataDeNascimento());
        validarSeApelidoJaExiste.validar(dto.apelido());
        usuarioRepository.getReferenceById(id).atualizarDados(dto);
    }
}
