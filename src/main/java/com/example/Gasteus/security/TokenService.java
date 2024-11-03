//package com.example.Gasteus.security;
//
//import com.example.Gasteus.model.cliente.Cliente;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//@Service
//public class TokenService {
//
//    @Value("${gasteus.security.jwt.secret}")
//    private String secret;
//    public String gerarToken(Cliente cliente) {
//        try {
//            var algoritmo = Algorithm.HMAC256(secret);
//            return JWT.create()
//                    .withIssuer("API Gasteus")
//                    .withSubject(cliente.getCpf())
//                    .withExpiresAt(dataExpiracao())
//                    .sign(algoritmo);
//        } catch (JWTCreationException exception){
//            throw new RuntimeException("erro ao gerar token jwt", exception);
//        }
//    }
//
//    public String getSubject(String tokenJWT){
//        try {
//            var algoritmo = Algorithm.HMAC256(secret);
//            return JWT.require(algoritmo)
//                    .withIssuer("API Gasteus")
//                    .build()
//                    .verify(tokenJWT)
//                    .getSubject();
//        } catch (JWTVerificationException exception) {
//            throw new RuntimeException("Token JWT inválido ou expirado!");
//        }
//    }
//
//    private Instant dataExpiracao() {
//        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
//    }
//}

package com.example.Gasteus.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${gasteus.security.jwt.secret}")
    private String secret;

    public String gerarToken(UserDetails user) {  // Altere o parâmetro para UserDetails
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Gasteus")
                    .withSubject(user.getUsername())  // Usar getUsername() para o identificador único
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Gasteus")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
