package com.rodrigo.socialmedia.controller;

import com.rodrigo.socialmedia.domain.usuario.Usuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuario;
import com.rodrigo.socialmedia.domain.usuario.cadastrar.CadastrarUsuarioDTO;
import com.rodrigo.socialmedia.domain.usuario.mudarSenha.MudarSenha;
import com.rodrigo.socialmedia.domain.usuario.mudarSenha.MudarSenhaDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CadastrarUsuario cadastrarUsuario;

    @MockBean
    private MudarSenha mudarSenha;

    @Autowired
    private JacksonTester<CadastrarUsuarioDTO> cadastrarUsuarioDTOJacksonTester;

    @Autowired
    private JacksonTester<MudarSenhaDTO> mudarSenhaDTOJacksonTester;

    @Test
    public void deveriaRetornarStatus400ParaCadastrar() throws Exception {
        var response = mockMvc.perform(post("/usuarios")).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveriaRetornarStatus201ParaCadastrar() throws Exception {
        CadastrarUsuarioDTO dto = new CadastrarUsuarioDTO(
                "apelido",
                "nome",
                "sobrenome",
                "email@email.com",
                "123456",
                LocalDate.now().minusYears(18),
                "cidade",
                "estado",
                "pais",
                "telefone"
        );
        var json = cadastrarUsuarioDTOJacksonTester.write(dto);
        doReturn(mock(Usuario.class)).when(cadastrarUsuario).execute(any());

        var response = mockMvc.perform(
                post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getJson())
        ).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void deveriaRetornarStatus400ParaMudarSenha() throws Exception {
        var response = mockMvc.perform(patch("/usuarios/{id}/mudarSenha", 1)).andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void deveriaRetornarStatus200ParaMudarSenha() throws Exception {
        MudarSenhaDTO dto = new MudarSenhaDTO(
                "senhaAtual",
                "novaSenha"
        );
        var json = mudarSenhaDTOJacksonTester.write(dto);

        var response = mockMvc.perform(
                patch("/usuarios/{id}/mudarSenha", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.getJson())
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}