package com.portfolio.arithmetic.calculator.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.portfolio.arithmetic.calculator.core.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JWTService {

    @Value("auth.secret-key")
    private String secret;

    @Value("auth.issuer")
    private String issuer;


    private final static long tokenDuration = 24 * 60 * 60 * 1000L;

    public String generateToken(User user) {
        final Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenDuration))
                .withClaim("userId", user.getId())
                .sign(algorithm);
    }

    public Optional<Long> getUserId(String authorizationHeader) {
        final String token = getToken(authorizationHeader);
        final Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            Claim claim = decodedJWT.getClaim("userId");

            if (claim.isMissing()) {
                return Optional.empty();
            }

            return Optional.of(claim.asLong());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

    private String getToken(final String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return null;
        }

        if (authorizationHeader.startsWith("Bearer")) {
            return authorizationHeader.substring(7);
        }

        return authorizationHeader;
    }
}
