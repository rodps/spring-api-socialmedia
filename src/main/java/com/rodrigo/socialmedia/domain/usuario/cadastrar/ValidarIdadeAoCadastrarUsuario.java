package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.rodrigo.socialmedia.domain.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ValidarIdadeAoCadastrarUsuario implements CadastrarUsuarioValidator {

    public void validar(CadastrarUsuarioDTO dto) {
        LocalDate dataDeNascimento = dto.dataDeNascimento();
        LocalDate dataAtual = LocalDate.now();
        Long idade = ChronoUnit.YEARS.between(dataDeNascimento, dataAtual);
        if (idade < 18) {
            throw new ValidacaoException("Idade inferior a 18 anos");
        }
    }
}
