package com.rodrigo.socialmedia.domain.usuario.editar;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EditarUsuarioDTO(
        @Size(max = 16) @Pattern(regexp = "\\w+\\S")
        String apelido,
        String nome,
        String sobrenome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataDeNascimento,
        String cidade,
        String estado,
        String pais,
        String telefone
) {
}
