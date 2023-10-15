package com.rodrigo.socialmedia.infra;

import com.rodrigo.socialmedia.domain.UrlHelper;
import com.rodrigo.socialmedia.domain.usuario.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.from:teste@socialmedia.com}")
    private String from;

    @Autowired
    private UrlHelper urlHelper;

    public void enviarEmailSimples(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Async
    public void enviarEmailDeConfirmacaoDeCadastro(Usuario usuario) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        String urlDeLogin = urlHelper.urlLogin();
        String htmlMessage = """
                <h3>Confirmação de cadastro</h3>
                <p>Seja bem vindo ao projeto socialmedia! Para realizar o login, acesse o link abaixo:</p>
                <p>%s</p>
                """.formatted(urlDeLogin);
        helper.setText(htmlMessage, true);
        helper.setTo(usuario.getEmail());
        helper.setFrom(from);
        helper.setSubject("Confirme seu email");
        mailSender.send(message);
    }
}
