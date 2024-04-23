package br.com.firstdecisionapp.firstdecisionapp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   // private final LoginService loginService;
    @Value("${enable.block.security}")
    private boolean enableBlockSecurity;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String emailUser = request.getHeader("emailUser");
        final String jwt;
        final String userEmail;
        /*System.out.println(authHeader);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ") || emailUser.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }*/
        String headerValue = request.getHeader("Authorization");
        if(enableBlockSecurity){//Property que habilita e desabilita o security para testes e debug
            // Verifica se o endpoint requer autenticação antes de processar a requisição
            if (checkIfEndpointIsNotPublic(request)) {

                var tokenJWT = recuperarToken(request);
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority >();
                GrantedAuthority grantedAuthority = new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "USER";
                    }
                };
                GrantedAuthority grantedAuthority2 = new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ADMIN";
                    }
                };

                authorities.add(grantedAuthority);
                authorities.add(grantedAuthority2);
                if (authHeader != null) {
                    var subject = getSubject(authHeader);
                    System.out.println(subject);
                    if(subject == emailUser){
                        var authentication = new UsernamePasswordAuthenticationToken("usuario", null,  authorities);

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else{
                        throw new BadCredentialsException("Token Inválido!");
                    }

                }else{
                    throw new BadCredentialsException("Token Inválido!");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        return !Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256("secret");
            return JWT.require(algoritmo)
                    .withIssuer("Ticket API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
