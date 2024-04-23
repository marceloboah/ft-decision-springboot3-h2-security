package br.com.firstdecisionapp.firstdecisionapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/api/v1/login",
            "/api/v1/validate/token/oauth2",//url que usaremos para fazer login
            "/h2/query.do",
            "/h2/stylesheet.css",
            "/h2/login.jsp",
            "/swagger-ui/*",
            "/swagger-ui/index.html",
            "/h2/background.gif"


    };

}
