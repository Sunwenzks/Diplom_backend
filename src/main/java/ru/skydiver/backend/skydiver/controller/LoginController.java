package ru.skydiver.backend.skydiver.controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.openapitools.api.TokenApi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements TokenApi {

    private final JwtEncoder encoder;

    public LoginController(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<String> login() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Instant now = Instant.now();
        long expiry = 36000L;
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claims(stringObjectMap ->{
                    stringObjectMap.put("scope", scope);
                    stringObjectMap.put("Role", scope);
                })
                .build();
        return ResponseEntity.ok(
                this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }
}
