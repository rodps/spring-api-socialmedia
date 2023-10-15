package com.rodrigo.socialmedia.domain.usuario.validacoes;

import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@DisplayName("Validar idade ao cadastrar Usuario")
class ValidarIdadeTest {

    private ValidarIdade validarIdade = new ValidarIdade();

    @Test
    @DisplayName("Deveria lançar ValidacaoException se o Usuario tiver idade menor do que 18 anos")
    public void deveriaLancarExcecaoSeOUsuarioForMenorDoQue18Anos() {
        //arrange
        LocalDate menorQue18 = LocalDate.now().minusYears(17);

        //act + assert
        Assertions.assertThrows(ValidacaoException.class, () -> validarIdade.validar(menorQue18));
    }

    @Test
    @DisplayName("Não deveria lançar exceção se o Usuario tiver idade maior ou igual a 18 anos")
    public void naoDeveriaLancarExcecaoSeOUsuarioForMaiorDoQue18Anos() {
        //arrange
        LocalDate idadeIgualA18 = LocalDate.now().minusYears(18);
        LocalDate idadeMaiorQue18 = LocalDate.now().minusYears(19);

        //act + assert
        Assertions.assertDoesNotThrow(() -> validarIdade.validar(idadeIgualA18));
        Assertions.assertDoesNotThrow(() -> validarIdade.validar(idadeMaiorQue18));
    }
}