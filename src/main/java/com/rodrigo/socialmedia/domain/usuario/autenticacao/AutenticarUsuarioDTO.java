package com.rodrigo.socialmedia.domain.usuario.autenticacao;

import jakarta.validation.constraints.NotBlank;

public record AutenticarUsuarioDTO(@NotBlank String email, @NotBlank String senha) {
}
