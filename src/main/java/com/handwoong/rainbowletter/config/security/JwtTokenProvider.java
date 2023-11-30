package com.handwoong.rainbowletter.config.security;

import static com.handwoong.rainbowletter.util.Constants.TEN_MINUTE_TO_MILLISECOND;
import static com.handwoong.rainbowletter.util.Constants.TWO_WEEK_TO_MILLISECOND;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.handwoong.rainbowletter.config.PropertiesConfig;
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
    private static final long ACCESS_EXPIRE_TIME = TWO_WEEK_TO_MILLISECOND;
    private static final long VERIFY_EXPIRE_TIME = TEN_MINUTE_TO_MILLISECOND;

    private final SecretKey key;

    public JwtTokenProvider(final PropertiesConfig propertiesConfig) {
        final byte[] keyBytes = Decoders.BASE64.decode(propertiesConfig.getSecretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponse generateToken(final GrantType grantType, final Authentication authentication) {
        final String authority = getAuthority(authentication);
        final String token = createToken(authentication.getName(), authority, generateExpireDate(ACCESS_EXPIRE_TIME));
        return TokenResponse.of(grantType, token);
    }

    public TokenResponse generateToken(final GrantType grantType, final String subject, final String claim) {
        final String token = createToken(subject, claim, generateExpireDate(VERIFY_EXPIRE_TIME));
        return TokenResponse.of(grantType, token);
    }

    private String getAuthority(final Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findAny()
                .orElseThrow(() -> new RainbowLetterException(ErrorCode.UN_AUTHORIZE));
    }

    private String createToken(final String subject, final String claim, final Date expireDate) {
        return Jwts.builder()
                .subject(subject)
                .claim(AUTH_CLAIM_KEY, claim)
                .signWith(key)
                .expiration(expireDate)
                .compact();
    }

    public Authentication parseToken(final String token) {
        validateToken(token);
        final Map<String, String> payload = parsePayload(token);
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(payload.get(AUTHORITY)));
        final User principal = new User(payload.get(USERNAME), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public String parseVerifyToken(final String token) {
        validateToken(token);
        final Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    private void validateToken(final String token) {
        try {
            parseClaims(token);
        } catch (final IllegalArgumentException | JwtException exception) {
            throw new RainbowLetterException(ErrorCode.INVALID_TOKEN);
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

    public Date generateExpireDate(final long expireTime) {
        return new Date(System.currentTimeMillis() + expireTime);
    }
}
