package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.UsuarioRepository;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Spy
    private List<CadastrarUsuarioValidator> validatorList = new ArrayList<>();

    @Mock
    private CadastrarUsuarioValidator validator1;
    @Mock
    private CadastrarUsuarioValidator validator2;
    @Mock
    private CadastrarUsuarioValidator validator3;

    private CadastrarUsuarioDTO dto = new CadastrarUsuarioDTO(
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
    );;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Test
    @DisplayName("Deveria executar todas as validações")
    public void deveriaExecutarTodasAsValidacoes() {
        //arrange
        validatorList.add(validator1);
        validatorList.add(validator2);
        validatorList.add(validator3);

        //act
        cadastrarUsuario.execute(dto);

        //assert
        InOrder inorder = inOrder(validatorList, usuarioRepository);
        inorder.verify(validatorList).forEach(any());
        inorder.verify(usuarioRepository).save(any());
        then(validator1).should().validar(dto);
        then(validator2).should().validar(dto);
        then(validator3).should().validar(dto);
    }

    @Test
    @DisplayName("Deveria salvar o usuário com os parâmetros corretos")
    public void deveriaSalvarOUsuarioComOsParametrosCorretos() {
        //arrange
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
}