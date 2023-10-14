package com.rodrigo.socialmedia.domain.usuario;

import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void deveriaAlterarOValorDoEstaValidadoParaTrue() {
        //arrange
        Usuario usuario = new Usuario(dadosUsuario(), "senha");
        usuarioRepository.save(usuario);

        //act
        int rows = usuarioRepository.setEstaValidadoTrue(usuario.getEmail());

        em.refresh(usuario);

        //assert
        assertEquals(1, rows);
        assertEquals(true, usuario.isEstaValidado());
    }

    private CadastrarUsuarioDTO dadosUsuario() {
        return new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email@gmail.com",
                "senha",
                LocalDate.now(),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );
    }
}