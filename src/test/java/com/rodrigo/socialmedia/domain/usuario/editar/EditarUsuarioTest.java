package com.rodrigo.socialmedia.domain.usuario.editar;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EditarUsuarioTest {

    @InjectMocks
    private EditarUsuario editarUsuario;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Captor
    ArgumentCaptor<Long> idCaptor;

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

    @Mock
    Usuario usuarioMock;

    @Test
    public void deveriaChamarORepositorioComOIdCorreto() {
        //arrange
        Long id = 1l;
        BDDMockito.given(usuarioRepository.getReferenceById(idCaptor.capture())).willReturn(usuarioMock);

        //act
        editarUsuario.execute(id, dto);

        //assert
        Assertions.assertEquals(id, idCaptor.getValue());
    }

    @Test
    public void deveriaAtualizarOsDadosComOsParametrosCorretos() {
        //arrange
        BDDMockito.given(usuarioRepository.getReferenceById(BDDMockito.any())).willReturn(usuarioMock);

        //act
        editarUsuario.execute(1l, dto);

        //assert
        BDDMockito.then(usuarioMock).should().atualizarDados(dto);
    }

    @Test
    public void deveriaLancarExcecaoSeORepositorioLancarExcecao() {
        //arrange
        BDDMockito.given(usuarioRepository.getReferenceById(BDDMockito.any())).willThrow();

        //assert
        Assertions.assertThrows(RuntimeException.class, () -> editarUsuario.execute(1l, dto));
    }
}