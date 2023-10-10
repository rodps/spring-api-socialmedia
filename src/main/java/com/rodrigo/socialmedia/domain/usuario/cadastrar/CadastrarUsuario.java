package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastrarUsuario {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    List<CadastrarUsuarioValidator> validatorList;

    public void execute(CadastrarUsuarioDTO dto) {
        validatorList.forEach(v -> v.validar(dto));
        String hashedPassword = passwordEncoder.encode(dto.senha());
        Usuario usuario = new Usuario(dto, hashedPassword);
        usuarioRepository.save(usuario);
    }
}
