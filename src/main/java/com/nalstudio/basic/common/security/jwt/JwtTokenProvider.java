package com.nalstudio.basic.common.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Json Web Token Provider
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final String JWTKey = "token....";//적용할 토큰 키

    public int getUserNo(String header) throws Exception {
        String token = header.substring(7);

        byte[] signingKey = getSigningKey();

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token);

        String subject = parsedToken.getBody().getSubject();

        int userNo = Integer.parseInt(subject);

        return userNo;
    }

    public String createToken(long userNo, String userId, List<String> roles) {
        byte[] signingKey = getSigningKey();
        String token = Jwts.builder()
//				.signWith(SignatureAlgorithm.HS512, signingKey)
//				.setHeaderParam("typ", SecurityConstants.TOKEN_PREFIX)
                .setHeader(createHeader())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .setClaims(createClaims(userNo, userId, roles))
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
//				.signWith(SignatureAlgorithm.HS512, createSigningKey(signingKey))
                .compact();

        return token;
    }

    private byte[] getSigningKey() {
        return JWTKey.getBytes();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS512");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    //ulink 형식으로 변경 필요 Member의 중요 정보 input
    private Map<String,Object> createClaims(long userNo, String userId, List<String> roles) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("uno", "" + userNo);
        claims.put("uid", userId);
        claims.put("rol", roles);
        return claims;
    }

    //토큰 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims =
                    Jwts.parser().setSigningKey(JWTKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(ExpiredJwtException ex) {
            log.error("Token Expired");
            return false;
        } catch (JwtException ex) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException ex) {
            log.error("Token is null");
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}
