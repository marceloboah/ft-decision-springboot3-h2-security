package br.com.firstdecisionapp.firstdecisionapp.controller;

import br.com.firstdecisionapp.firstdecisionapp.dto.UserDataOAuth2ForTokenJwt;
import br.com.firstdecisionapp.firstdecisionapp.model.User;
import br.com.firstdecisionapp.firstdecisionapp.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class GeneratorTokenTestController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid UserDataOAuth2ForTokenJwt dados){
        var token = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getUserId());
        var authentication = manager.authenticate(token);
        var tokenJWT = loginService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(tokenJWT);
    }
}