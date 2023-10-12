package com.rodrigo.socialmedia.domain.usuario.cadastrar;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CadastrarUsuarioDTO(
        @NotBlank @Size(max = 16) @Pattern(regexp = "\\w+\\S")
        String apelido,
        @NotBlank
        String nome,
        @NotBlank
        String sobrenome,
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 6)
        String senha,
        @NotNull @JsonFormat(pattern = "dd/MM/yyyy")
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
