package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.rodrigo.socialmedia.domain.ValidacaoException;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.ValidarIdadeAoCadastrarUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@DisplayName("Validar idade ao cadastrar Usuario")
class ValidarIdadeAoCadastrarUsuarioTest {

    private ValidarIdadeAoCadastrarUsuario validarIdadeAoCadastrarUsuario = new ValidarIdadeAoCadastrarUsuario();

    @Test
    @DisplayName("Deveria lançar ValidacaoException se o Usuario tiver idade menor do que 18 anos")
    public void deveriaLancarExcecaoSeOUsuarioForMenorDoQue18Anos() {
        //arrange
        CadastrarUsuarioDTO dto = new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email",
                "senha",
                LocalDate.now().minusYears(17),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );

        //act + assert
        Assertions.assertThrows(ValidacaoException.class, () -> validarIdadeAoCadastrarUsuario.validar(dto));
    }

    @Test
    @DisplayName("Não deveria lançar exceção se o Usuario tiver idade maior ou igual a 18 anos")
    public void naoDeveriaLancarExcecaoSeOUsuarioForMaiorDoQue18Anos() {
        //arrange
        CadastrarUsuarioDTO dtoIgualA18 = new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email",
                "senha",
                LocalDate.now().minusYears(18),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );

        CadastrarUsuarioDTO dtoMaiorQue18 = new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email",
                "senha",
                LocalDate.now().minusYears(19),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );

        //act + assert
        Assertions.assertDoesNotThrow(() -> validarIdadeAoCadastrarUsuario.validar(dtoIgualA18));
        Assertions.assertDoesNotThrow(() -> validarIdadeAoCadastrarUsuario.validar(dtoMaiorQue18));
    }
}