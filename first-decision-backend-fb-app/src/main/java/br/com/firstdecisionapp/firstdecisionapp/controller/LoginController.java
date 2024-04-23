package br.com.firstdecisionapp.firstdecisionapp.controller;

import br.com.firstdecisionapp.firstdecisionapp.dto.TokenJWT;
import br.com.firstdecisionapp.firstdecisionapp.model.User;
import br.com.firstdecisionapp.firstdecisionapp.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**Classe com Método que valida o token do Facebook vindo do cliente e gera um novo token jwt para a utilização das próximas chamadas de backend*/
@CrossOrigin("*")
@RestController
public class LoginController {

    @Autowired
    private br.com.firstdecisionapp.firstdecisionapp.service.LoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/validate/token/oauth2")
    public ResponseEntity<TokenJWT> logar(@RequestHeader("idUser") String idUser, @RequestHeader("tokenUser") String tokenUser, @RequestHeader("emailUser") String emailUser) throws IOException, InterruptedException, URISyntaxException {
        User user = new User();
        user.setEmail(emailUser);
        user.setPassword(idUser);
        TokenJWT tokenJWT = new TokenJWT();

        if (StringUtils.isBlank(idUser) && StringUtils.isBlank(tokenUser) && StringUtils.isBlank(emailUser)) {
            throw new RuntimeException("Erro! Parâmetros inválidos!");
        }
        String url = "https://graph.facebook.com/" + idUser + "/accounts?access_token=" + tokenUser;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            //userService.createUser(user);
            //Criar o token JWT aqui
            String tokenJWTBackend = loginService.gerarToken(user);
            tokenJWT.setTokenJWT(tokenJWTBackend);
            return new ResponseEntity<>(tokenJWT, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}