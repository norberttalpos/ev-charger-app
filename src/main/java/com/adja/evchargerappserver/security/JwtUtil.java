package com.adja.evchargerappserver.security;

import com.adja.evchargerappserver.security.configs.SecurityConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;

public class JwtUtil {

    public static DecodedJWT getDecodedJWT(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(SecurityConfig.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

    public static String getUsernameFromJwt(String authorizationHeader) {
        return getDecodedJWT(authorizationHeader).getSubject();
    }

    public static String createAccessToken(String username, List<String> roles, String requestUrl) {
        Algorithm algorithm = Algorithm.HMAC256(SecurityConfig.getSecretKey().getBytes());

         return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + getAccessTokenExpiryTime()))
                .withIssuer(requestUrl)
                .withClaim("roles", roles)
                .sign(algorithm);
    }

    public static String createRefreshToken(String username, String requestUrl) {
        Algorithm algorithm = Algorithm.HMAC256(SecurityConfig.getSecretKey().getBytes());

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + getRefreshTokenExpiryTime()))
                .withIssuer(requestUrl)
                .sign(algorithm);
    }

    public static long getAccessTokenExpiryTime() {
        return 60 * 60 * 1000;
    }

    public static long getRefreshTokenExpiryTime() {
        return 24 * 60 * 60 * 1000;
    }
}
