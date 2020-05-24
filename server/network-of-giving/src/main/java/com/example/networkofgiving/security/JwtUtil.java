package com.example.networkofgiving.security;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.JwtAuthenticationResponse;
import com.example.networkofgiving.services.IUserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long expirationTime;

    @Autowired
    private IUserService userService;

    private final String JWT_PREFIX = "Bearer ";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public JwtAuthenticationResponse createTokenAuthenticationResponse(String subject) {

        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        Integer expiresInSeconds = (int)(expirationTime / 1000.0);
        return new JwtAuthenticationResponse(jwt, expiresInSeconds);
    }

    public Authentication getAuthenticationFromToken(String token) {
        String subject = getSubject(token);
        Long userId = Long.valueOf(subject);
        User user = this.userService.getUserById(userId);
        if (user == null) {
            return null;
        }
        AuthenticatedUserInfo auth = new AuthenticatedUserInfo(user);
        return new UsernamePasswordAuthenticationToken(auth, "", auth.getAuthorities());
    }

    public String getSubject(String token) {
        return this.getTokenClaims(token).getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith(JWT_PREFIX)) {
            return authorizationHeader.substring(JWT_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        Date expirationDate = null;
        try {
            expirationDate = this.getExpirationDate(token);
        } catch (JwtException e) {
            return false;
        }
        Date now = new Date();
        return expirationDate.after(now);
    }

    private Claims getTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date getExpirationDate(String token) {
        return this.getTokenClaims(token)
                .getExpiration();
    }
}
