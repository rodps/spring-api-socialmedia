package com.rodrigo.socialmedia.domain.usuario.mudarSenha;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MudarSenhaDTO(@NotBlank String senhaAtual, @NotBlank @Size(min = 6) String novaSenha) {
}
