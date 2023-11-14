package com.handwoong.rainbowletter.config.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.handwoong.rainbowletter.exception.ErrorCode;
import com.handwoong.rainbowletter.exception.RainbowLetterException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private static final String AUTH_CLAIM_KEY = "roles";
    private static final String USERNAME = "username";
    private static final String AUTHORITY = "authority";

    private final SecretKey key;

    public JwtTokenProvider(@Value("${jwt.secret}") final String secretKey) {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponse generateToken(final GrantType grantType, final Authentication authentication) {
        final String authority = getAuthority(authentication);
        final String token = createToken(authority, authentication);
        return TokenResponse.of(grantType, token);
    }

    private String getAuthority(final Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findAny()
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.UN_AUTHORIZE));
    }

    private String createToken(final String authority, final Authentication authentication) {
        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTH_CLAIM_KEY, authority)
                .signWith(key)
                .compact();
    }

    public Authentication parseToken(final String token) {
        validateToken(token);
        final Map<String, String> payload = parsePayload(token);
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(payload.get(AUTHORITY)));
        final User principal = new User(payload.get(USERNAME), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private void validateToken(final String token) {
        try {
            parseClaims(token);
        } catch (final IllegalArgumentException | JwtException exception) {
            throw new RainbowLetterException(ErrorCode.IN_VALID_TOKEN);
        }
    }

    private Map<String, String> parsePayload(final String token) {
        final Map<String, String> payload = new HashMap<>();
        final Claims claims = parseClaims(token);
        payload.put(USERNAME, claims.getSubject());
        payload.put(AUTHORITY, claims.get(AUTH_CLAIM_KEY).toString());
        return payload;
    }

    private Claims parseClaims(final String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
