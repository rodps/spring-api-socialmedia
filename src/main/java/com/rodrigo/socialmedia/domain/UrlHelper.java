package com.rodrigo.socialmedia.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UrlHelper {

    @Value("${baseurl:http://localhost:8080/}")
    private String baseurl;

    public String urlLogin() {
        return baseurl.concat("login");
    }
}
