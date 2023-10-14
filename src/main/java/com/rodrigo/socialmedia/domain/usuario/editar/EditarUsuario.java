package com.rodrigo.socialmedia.domain.usuario.editar;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarIdade;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeApelidoJaExiste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditarUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidarSeApelidoJaExiste validarSeApelidoJaExiste;

    @Autowired
    private ValidarIdade validarIdade;

    public void execute(Long id, EditarUsuarioDTO dto) {
        if (dto.dataDeNascimento() != null) {
            validarIdade.validar(dto.dataDeNascimento());
        }
        if (dto.apelido() != null && !dto.apelido().isEmpty()) {
            validarSeApelidoJaExiste.validar(dto.apelido());
        }
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizarDados(dto);
        usuarioRepository.save(usuario);
    }
}
