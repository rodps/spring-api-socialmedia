package com.rodrigo.socialmedia.domain.usuario;

import com.rodrigo.socialmedia.domain.MapperUtils;
import com.rodrigo.socialmedia.domain.post.Post;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.editar.EditarUsuarioDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Setter
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apelido;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private LocalDate dataDeNascimento;
    private String cidade;
    private String estado;
    private String pais;
    private String telefone;
    @OneToMany(mappedBy = "autor")
    private List<Post> posts = new ArrayList<>();

    public Usuario(CadastrarUsuarioDTO dto, String hashedPassword) {
        BeanUtils.copyProperties(dto, this);
        this.senha = hashedPassword;
    }

    public void atualizarDados(EditarUsuarioDTO dto) {
        MapperUtils.atualizarDados(dto, this);
        this.telefone = dto.telefone();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}