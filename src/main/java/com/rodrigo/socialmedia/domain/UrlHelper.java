package com.rodrigo.socialmedia.domain;

import org.springframework.beans.factory.annotation.Value;

public class UrlHelper {

    @Value("${baseurl:http://localhost:8080/}")
    public static String baseurl;

    public static String getUrlDeConfirmacaoDeCadastro(String token) {
        return baseurl.concat(token);
    }
}
