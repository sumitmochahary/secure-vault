package com.securevault.app.service;

import com.securevault.app.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @PostConstruct
    public void init() throws Exception {
        privateKey = loadPrivateKey();
        publicKey = loadPublicKey();
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(privateKey,Jwts.SIG.RS256)
                .compact();
    }

    public String extractEmail(String token){
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, User user){
        String email = extractEmail(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = parseClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Helpers to load PEM-encoded keys

    private PrivateKey loadPrivateKey() throws Exception {
        String key = readKey("keys/rsa-private.pem")
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replaceAll("\\s","");

        byte[] bytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private PublicKey loadPublicKey() throws Exception {
        String key = readKey("keys/rsa-public.pem")
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replaceAll("\\s","");

        byte[] bytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private String readKey(String path) throws Exception{
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if(is == null){
            throw new IllegalArgumentException("Key file not found: "+path);
        }
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

}
