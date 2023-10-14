package com.rodrigo.socialmedia.domain.usuario;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByApelido(String apelido);
    Usuario findByEmail(String email);

    @Modifying
    @Query("UPDATE Usuario u SET u.estaValidado = true WHERE u.email = :email")
    int setEstaValidadoTrue(@Param("email") String email);
}
