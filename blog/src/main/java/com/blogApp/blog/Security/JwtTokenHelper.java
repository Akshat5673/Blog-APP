package com.blogApp.blog.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;

    private static final String secret = "27f9f154fc29d04881972934f8a76e46411049bba57f328175eb711292166441";
    Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));


    // get username from jwt token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    // get expiration date from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T>T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any info from token we need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // check if token has expired
    public Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generating token for user
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    // while creating the token
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }



}
