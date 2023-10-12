package com.rodrigo.socialmedia.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByApelido(String apelido);
    Usuario findByEmail(String email);
}
