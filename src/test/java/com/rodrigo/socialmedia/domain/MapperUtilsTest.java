package com.rodrigo.socialmedia.domain;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.editar.EditarUsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MapperUtilsTest {

    MapperUtils mapperUtils = new MapperUtils();

    @Test
    public void deveAtualizarCorretamente() {
        CadastrarUsuarioDTO cadastrarDto = new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email",
                "senha",
                LocalDate.now(),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(cadastrarDto, usuario);

        EditarUsuarioDTO editarDto = new EditarUsuarioDTO(
                "apelidoEditado",
                null,
                null,
                LocalDate.now().minusYears(1),
                null,
                "estadoEditado",
                null,
                "telefoneAtualizado"
        );
        mapperUtils.atualizarDados(editarDto, usuario);

        assertEquals(usuario.getApelido(), editarDto.apelido());
        assertEquals(usuario.getNome(), cadastrarDto.nome());
        assertEquals(usuario.getSobrenome(), cadastrarDto.sobrenome());
        assertEquals(usuario.getDataDeNascimento(), editarDto.dataDeNascimento());
        assertEquals(usuario.getCidade(), cadastrarDto.cidade());
        assertEquals(usuario.getEstado(), editarDto.estado());
        assertEquals(usuario.getPais(), cadastrarDto.pais());
        assertEquals(usuario.getTelefone(), editarDto.telefone());
    }



}