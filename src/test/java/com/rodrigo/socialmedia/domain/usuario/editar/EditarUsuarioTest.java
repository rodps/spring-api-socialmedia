package com.rodrigo.socialmedia.domain.usuario.editar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rodrigo.socialmedia.domain.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarIdade;
import com.rodrigo.socialmedia.domain.usuario.validacoes.ValidarSeApelidoJaExiste;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditarUsuarioTest {

    @InjectMocks
    private EditarUsuario editarUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @Mock
    private ValidarIdade validarIdade;

    @Mock
    private ValidarSeApelidoJaExiste validarSeApelidoJaExiste;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void deveriaLancarExcecaoSeValidarIdadeFalhar() throws JsonProcessingException {
        //arrange
        String json = """
                { "dataDeNascimento": "11/11/1999" }
            """;
        EditarUsuarioDTO dto = objectMapper.readValue(json, EditarUsuarioDTO.class);
        doThrow(ValidacaoException.class).when(validarIdade).validar(dto.dataDeNascimento());

        //act + assert
        Assertions.assertThrows(ValidacaoException.class, () -> editarUsuario.execute(1l, dto));
    }

    @Test
    public void deveriaLancarExcecaoSeValidarApelidoFalhar() throws JsonProcessingException {
        //arrange
        String json = """
                { "apelido": "apelidoQualquer" }
            """;
        EditarUsuarioDTO dto = objectMapper.readValue(json, EditarUsuarioDTO.class);
        doThrow(ValidacaoException.class).when(validarSeApelidoJaExiste).validar(dto.apelido());

        //act + assert
        Assertions.assertThrows(ValidacaoException.class, () -> editarUsuario.execute(1l, dto));
    }

    @Test
    public void deveriaAtualizarOsDadosComOsParametrosCorretos() {
        //arrange
        EditarUsuarioDTO dto = new EditarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                LocalDate.now().minusYears(17),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );
        Long id = 1l;
        Usuario usuario = mock(Usuario.class);
        given(usuarioRepository.getReferenceById(id)).willReturn(usuario);

        //act
        editarUsuario.execute(id, dto);

        //assert
        then(usuario).should().atualizarDados(dto);
    }

    @Test
    public void deveriaLancarExcecaoSeORepositorioLancarExcecao() {
        //arrange
        EditarUsuarioDTO dto = new EditarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                LocalDate.now(),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );
        given(usuarioRepository.getReferenceById(any())).willThrow();

        //assert
        Assertions.assertThrows(RuntimeException.class, () -> editarUsuario.execute(1l, dto));
    }
}