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
class ValidarSeEmailJaExisteTest {

    @InjectMocks
    private ValidarSeEmailJaExiste validarSeEmailJaExiste;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveriaLancarExceptionSeEmailJaExiste() {
        //arrange
        String email = "emailQualquer";
        when(usuarioRepository.findByEmail(email)).thenReturn(new Usuario());

        //assert
        assertThrows(ValidacaoException.class, () -> validarSeEmailJaExiste.validar(email));
    }

    @Test
    public void naoDeveriaLancarExceptionSeEmailNaoExiste() {
        //arrange
        String email = "emailQualquer";
        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        //assert
        assertDoesNotThrow(() -> validarSeEmailJaExiste.validar(email));
    }
}