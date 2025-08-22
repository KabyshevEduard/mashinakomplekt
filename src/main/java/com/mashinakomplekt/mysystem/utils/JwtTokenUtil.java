package com.mashinakomplekt.mysystem.utils;

import com.mashinakomplekt.mysystem.models.User;
import com.mashinakomplekt.mysystem.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration lifetime;

    private final UserService userService;

    public String generateToken(UserDetails userDetails) {
        //User info in jwt
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put("roles", roles);

        //Action time of jwt
        Date now = new Date();
        Date expiration = new Date(now.getTime() + lifetime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public User checkUser(String token) throws InvalidParameterException {
        String username = null;
        try {
            username = getUsername(token);
        } catch (Exception e) {
            String errMessage = "JWT токен не корректен, либо срок использования истек";
            throw new InvalidParameterException(errMessage);
        }
        Optional<User> userOp = userService.findByUsername(username);
        if (!userOp.isPresent()) {
            String errMesssage = "Такого пользователя не существует";
            throw new InvalidParameterException(errMesssage);
        }

        return userOp.get();
    }

    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getClaimsFromToken(token).get("roles",  List.class);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
