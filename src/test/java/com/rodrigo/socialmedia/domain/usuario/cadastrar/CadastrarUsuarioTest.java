package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarIdade;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeApelidoJaExiste;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeEmailJaExiste;
import com.rodrigo.socialmedia.infra.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Cadastrar Usuario")
class CadastrarUsuarioTest {

    @InjectMocks
    private CadastrarUsuario cadastrarUsuario;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Mock
    private ValidarIdade validarIdade;

    @Mock
    private ValidarSeApelidoJaExiste validarSeApelidoJaExiste;

    @Mock
    private ValidarSeEmailJaExiste validarSeEmailJaExiste;

    @Mock
    private EmailService emailService;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void deveriaLancarExcecaoSeOUsuarioForMenorDoQue18Anos() throws JsonProcessingException {
        //arrange
        String json = """
                { "dataDeNascimento": "11/11/1999" }
            """;
        CadastrarUsuarioDTO dto = objectMapper.readValue(json, CadastrarUsuarioDTO.class);
        doThrow(ValidacaoException.class).when(validarIdade).validar(dto.dataDeNascimento());

        //assert
        Assertions.assertThrows(ValidacaoException.class, () -> cadastrarUsuario.execute(dto));
    }

    @Test
    public void deveriaLancarExcecaoSeOApelidoJaExiste() throws JsonProcessingException {
        //arrange
        String json = """
                { "apelido": "apelidoExistente" }
            """;
        CadastrarUsuarioDTO dto = objectMapper.readValue(json, CadastrarUsuarioDTO.class);
        doThrow(ValidacaoException.class).when(validarSeApelidoJaExiste).validar(dto.apelido());

        //assert
        Assertions.assertThrows(ValidacaoException.class, () -> cadastrarUsuario.execute(dto));
    }

    @Test
    public void deveriaLancarExcecaoSeOEmailJaExiste() throws JsonProcessingException {
        //arrange
        String json = """
                { "email": "emailExistente" }
            """;
        CadastrarUsuarioDTO dto = objectMapper.readValue(json, CadastrarUsuarioDTO.class);
        doThrow(ValidacaoException.class).when(validarSeEmailJaExiste).validar(any());

        //assert
        Assertions.assertThrows(ValidacaoException.class, () -> cadastrarUsuario.execute(dto));
    }

    @Test
    public void deveriaSalvarOUsuarioComOsParametrosCorretos() throws MessagingException {
        //arrange
        CadastrarUsuarioDTO dto = new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email",
                "senha",
                LocalDate.now().minusDays(1),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );
        String hashedPassword = "anyHashedPassword";
        given(passwordEncoder.encode(dto.senha())).willReturn(hashedPassword);

        //act
        cadastrarUsuario.execute(dto);

        //assert
        then(usuarioRepository).should().save(usuarioCaptor.capture());
        Assertions.assertEquals(usuarioCaptor.getValue().getApelido(), dto.apelido());
        Assertions.assertEquals(usuarioCaptor.getValue().getNome(), dto.nome());
        Assertions.assertEquals(usuarioCaptor.getValue().getSobrenome(), dto.sobrenome());
        Assertions.assertEquals(usuarioCaptor.getValue().getEmail(), dto.email());
        Assertions.assertEquals(usuarioCaptor.getValue().getSenha(), hashedPassword);
        Assertions.assertEquals(usuarioCaptor.getValue().getCidade(), dto.cidade());
        Assertions.assertEquals(usuarioCaptor.getValue().getEstado(), dto.estado());
        Assertions.assertEquals(usuarioCaptor.getValue().getPais(), dto.pais());
        Assertions.assertEquals(usuarioCaptor.getValue().getTelefone(), dto.telefone());
        Assertions.assertEquals(usuarioCaptor.getValue().getDataDeNascimento(), dto.dataDeNascimento());
    }

    @Test
    public void deveriaEnviarEmailDeConfirmacao() throws MessagingException {
        //arrange
        CadastrarUsuarioDTO dto = new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email",
                "senha",
                LocalDate.now(),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );

        //act
        cadastrarUsuario.execute(dto);

        //assert
        verify(usuarioRepository).save(usuarioCaptor.capture());
        verify(emailService).enviarEmailDeConfirmacaoDeCadastro(usuarioCaptor.getValue());
    }

}