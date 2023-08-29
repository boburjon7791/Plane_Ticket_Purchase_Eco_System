package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final String key="";
    public static String generateToken(@NonNull String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setIssuer("http://localhost:8080")
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    private static Key key(){
        byte[] bytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(bytes);
    }
    public static boolean isValid(HttpServletResponse res, @NonNull String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("claims = " + claims);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        }catch (Exception e){
            e.printStackTrace();
            res.setStatus(400);
            return false;
        }
    }
    public static String getEmail(HttpServletResponse res,@NonNull String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("claims = " + claims);
            return claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            res.setStatus(401);
            return null;
        }
    }
    public static String expireToken(@NonNull String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            claims.setExpiration(new Date(System.currentTimeMillis()-1));
            return Jwts.builder()
                    .signWith(key(),SignatureAlgorithm.HS256)
                    .setIssuer("http://localhost:8080")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()-1))
                    .setSubject(claims.getSubject())
                    .compact();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
