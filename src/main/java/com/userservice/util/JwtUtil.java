package com.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String SECRET_KEY = "1231d23e2e23";

    // Use email instead of username
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);  // Subject is the email
    }

    public String extractUserType(String token) {
        return (String) extractAllClaims(token).get("userType");
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate JWT token using email instead of username
    public String generateToken(String email, String userType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userType", userType);  // Add userType (role) to JWT claims
        return createToken(claims, email);  // Set email as the subject of the token
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)  // Subject is the email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Token valid for 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate token using email
    public Boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);  // Extract email from token
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }
}
