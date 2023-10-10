package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CadastrarUsuarioDTO(
        @NotBlank @Max(16) @Pattern(regexp = "[^\s]")
        String apelido,
        @NotBlank
        String nome,
        @NotBlank
        String sobrenome,
        @NotBlank @Email
        String email,
        @NotBlank @Min(6)
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
