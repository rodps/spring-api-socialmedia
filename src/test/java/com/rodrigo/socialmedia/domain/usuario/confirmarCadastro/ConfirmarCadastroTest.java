package com.rodrigo.socialmedia.domain.usuario.confirmarCadastro;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.infra.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmarCadastroTest {

    @InjectMocks
    private ConfirmarCadastro confirmarCadastro;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Test
    public void deveriaLancarExcecaoSeVerificacaoDoTokenFalhar() {
        String token = "anyToken";
        doThrow(RuntimeException.class).when(jwtService).decode(token);

        assertThrows(RuntimeException.class, () -> confirmarCadastro.execute(token));
    }

    @Test
    public void deveriaAlterarUsuarioParaValidadoNoBancoDeDados() {
        //arrange
        String token = "anyToken";
        String decodedEmail = "decodedEmail";
        doReturn(decodedEmail).when(jwtService).decode(token);

        //act
        confirmarCadastro.execute(token);

        //assert
        Mockito.verify(usuarioRepository).setEstaValidadoTrue(decodedEmail);
    }

}