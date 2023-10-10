package com.rodrigo.socialmedia.domain.usuario.editar;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EditarUsuarioDTO(
        @Max(16) @Pattern(regexp = "[^\s]")
        String apelido,
        String nome,
        String sobrenome,
        LocalDate dataDeNascimento,
        String cidade,
        String estado,
        String pais,
        String telefone
) {
}
