package com.rodrigo.socialmedia.domain.usuario.validacoes;

import com.rodrigo.socialmedia.domain.errors.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ValidarIdade {

    public void validar(LocalDate dataDeNascimento) {
        LocalDate dataAtual = LocalDate.now();
        Long idade = ChronoUnit.YEARS.between(dataDeNascimento, dataAtual);
        if (idade < 18) {
            throw new ValidacaoException("Idade inferior a 18 anos");
        }
    }
}
