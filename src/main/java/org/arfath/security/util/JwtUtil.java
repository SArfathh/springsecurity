package org.arfath.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

//    private final String SECRET = "MySuperSecretKeyThatIsLongEnoughForJwt123456789";
    private  String SECRET = "";

    public JwtUtil() throws NoSuchAlgorithmException {
        SecretKey sk = KeyGenerator.getInstance("HmacSHA256").generateKey();
        SECRET = Base64.getEncoder().encodeToString(sk.getEncoded());
    }


    public String generateToken(String username) {
        System.out.println(SECRET);
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60  * 60 * 1000))
                .signWith(getKey())
                .compact();

    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
    private Claims extractClaims(String token){

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private SecretKey getKey(){
        byte[] keyBytes = SECRET.getBytes(); // Get raw bytes of your secret
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

    }
}
