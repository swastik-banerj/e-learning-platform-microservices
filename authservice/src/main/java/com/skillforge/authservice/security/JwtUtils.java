package com.skillforge.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    private final String jwtSecret = "YS1zdHJpbmctc2VjcmV0LWF0bGVhc3QtMjU2LWJpdHMtbG9uZw==";
    private final int jwtExpirationsMs = 172800000;


    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }

    public String generateToken(String userId, String role){
        return Jwts.builder()
                .subject(userId)
                .claim("roles", List.of(role))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationsMs))
                .signWith(key())
                .compact();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));
    }

    public boolean validateJwtToken(String jwtToken){

        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken);
        } catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public String getUserIdFromToken(String jwt){
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload().getSubject();
    }

    public Claims getAllClaims(String jwt){
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload();
    }


}
