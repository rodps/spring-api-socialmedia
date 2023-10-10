package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CadastrarUsuarioDTO(
        @NotBlank @Max(32)
        String apelido,
        @NotBlank
        String nome,
        @NotBlank
        String sobrenome,
        @NotBlank @Email
        String email,
        @NotBlank
        String senha,
        @NotNull
        LocalDate dataDeNascimento,
        @NotBlank
        String cidade,
        @NotBlank
        String estado,
        @NotBlank
        String pais,
        String telefone
) {
}
