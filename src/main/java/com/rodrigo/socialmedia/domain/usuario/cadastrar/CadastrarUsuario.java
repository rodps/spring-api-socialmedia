package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarIdade;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeApelidoJaExiste;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeEmailJaExiste;
import com.rodrigo.socialmedia.infra.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    public Usuario execute(CadastrarUsuarioDTO dto) throws MessagingException {
        validarIdade.validar(dto.dataDeNascimento());
        validarSeApelidoJaExiste.validar(dto.apelido());
        validarSeEmailJaExiste.validar(dto.email());

        String hashedPassword = passwordEncoder.encode(dto.senha());
        Usuario usuario = new Usuario(dto, hashedPassword);
        usuarioRepository.save(usuario);

        emailService.enviarEmailDeConfirmacaoDeCadastro(dto.email());
        return usuario;
    }
}
