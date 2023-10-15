package com.rodrigo.socialmedia.domain.usuario.validacoes;

import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidarSeApelidoJaExisteTest {

    @InjectMocks
    private ValidarSeApelidoJaExiste validarSeApelidoJaExiste;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveriaLancarExceptionSeApelidoJaExiste() {
        //arrange
        String apelido = "apelidoQualquer";
        when(usuarioRepository.findByApelido(apelido)).thenReturn(new Usuario());

        //assert
        assertThrows(ValidacaoException.class, () -> validarSeApelidoJaExiste.validar(apelido));
    }

    @Test
    public void naoDeveriaLancarExceptionSeApelidoNaoExiste() {
        //arrange
        String apelido = "apelidoQualquer";
        when(usuarioRepository.findByApelido(apelido)).thenReturn(null);

        //assert
        assertDoesNotThrow(() -> validarSeApelidoJaExiste.validar(apelido));
    }
}