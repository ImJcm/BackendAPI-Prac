package com.example.backendapiprac.jwt;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "jwtUtil")
@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    private final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private Key key;

    /* DI(의존성 주입)이 이뤄진 후에 실행되는 메서드에 사용 */
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /* username으로 Jwt Token 생성 */
    public String createToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)   //사용자 식별값
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))   //만료시간
                        .setIssuedAt(date)  //발급일
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }


    /* 만들어진 Jwt Token -> Cookie로 생성 */
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            /* JWT Token 값에 공백문자를 -> %20으로 변경 */
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+","%20");

            /* token -> AUTHORIZATION_HEADER로 쿠기 생성 */
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            cookie.setPath("/");

            /* response에 cookie 추가 */
            res.addCookie(cookie);
        } catch(UnsupportedEncodingException e) {

        }
    }

    /* request로 부터 "Authorization" Cookie 받기 */
    public String getJwtFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /* 토큰검증 */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new JwtException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new JwtException("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new JwtException("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new JwtException("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
    }

    /* 토큰에서 사용자 정보 가져오기 */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /* Bearer 제거 */
    public String substringToken(String token) {
        if(StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        throw new NullPointerException("Not Found Token");
    }



}
