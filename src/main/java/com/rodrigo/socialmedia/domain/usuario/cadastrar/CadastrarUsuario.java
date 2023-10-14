package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarIdade;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeApelidoJaExiste;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeEmailJaExiste;
import com.rodrigo.socialmedia.infra.EmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CadastrarUsuario {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidarIdade validarIdade;

    @Autowired
    private ValidarSeApelidoJaExiste validarSeApelidoJaExiste;

    @Autowired
    private ValidarSeEmailJaExiste validarSeEmailJaExiste;

    @Autowired
    private EmailService emailService;

    public Usuario execute(CadastrarUsuarioDTO dto) {
        validarIdade.validar(dto.dataDeNascimento());
        validarSeApelidoJaExiste.validar(dto.apelido());
        validarSeEmailJaExiste.validar(dto.email());

        String hashedPassword = passwordEncoder.encode(dto.senha());
        Usuario usuario = new Usuario(dto, hashedPassword);
        usuarioRepository.save(usuario);

        try {
            emailService.enviarEmailDeConfirmacaoDeCadastro(usuario);
        } catch (MessagingException e) {
            log.error("Ocorreu um erro ao enviar email: %s".formatted(e.getMessage()));
        }
        return usuario;
    }
}
