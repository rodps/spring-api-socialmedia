package com.rodrigo.socialmedia.domain.usuario.mudarSenha;

import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MudarSenhaTest {

    @InjectMocks
    private MudarSenha mudarSenha;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void deveriaAtualizarANovaSenha() {
        Long id = 1l;
        Usuario usuario = mock(Usuario.class);
        MudarSenhaDTO dto = new MudarSenhaDTO(
                "123",
                "321"
        );
        doReturn(usuario).when(usuarioRepository).getReferenceById(id);
        doReturn("hashedPassword").when(usuario).getSenha();
        doReturn("hashedPassword").when(passwordEncoder).encode(dto.senhaAtual());

        mudarSenha.execute(id, dto);

        verify(usuario).setSenha(dto.novaSenha());
    }

    @Test
    public void deveriaLancarExcecaoSeASenhaAtualForIncorreta() {
        Long id = 1l;
        Usuario usuario = mock(Usuario.class);
        MudarSenhaDTO dto = new MudarSenhaDTO(
                "123",
                "321"
        );
        doReturn(usuario).when(usuarioRepository).getReferenceById(id);
        doReturn("hashedPassword").when(usuario).getSenha();
        doReturn("hashedPassword2").when(passwordEncoder).encode(dto.senhaAtual());

        assertThrows(ValidacaoException.class, () -> mudarSenha.execute(id, dto));
    }
}