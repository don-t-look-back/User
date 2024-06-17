package com.example.dlbuser.auth.jwt;

import com.example.dlbuser.common.exceptions.BaseException;
import com.example.dlbuser.common.response.BaseResponseStatus;
import com.example.dlbuser.domains.auth.model.response.TokenResponse;
import com.example.dlbuser.domains.member.entity.Member;
import com.example.dlbuser.domains.member.repository.MemberRepository;
import com.example.dlbuser.domains.memberToken.entity.MemberToken;
import com.example.dlbuser.domains.memberToken.entity.constant.JwtTokenType;
import com.example.dlbuser.domains.memberToken.repository.MemberTokenRepository;
import com.example.dlbuser.utils.DateTimeUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final MemberTokenRepository memberTokenRepository;
    private final MemberRepository memberRepository;

    private final String MEMBER_ID = "member_id";
//    @Value("spring.jwt.secret")
//    private String secretKey;


    private static String tokenSecret = "visioncountingserversecretkeysecretsecretsecretsecretvisioncountingvisionvisioncountingcounting";

    private String accessTokenExpirationTime = "31536000000";

    private String refreshTokenExpirationTime = "31536000000";

    public static Long getMemberId(String token) {
        try {
            Key key = Keys.hmacShaKeyFor("visioncountingserversecretkeysecretsecretsecretsecretvisioncountingvisionvisioncountingcounting".getBytes());

//            final Claims claims = Jwts.parser().setSigningKey(key)
//                    .parseClaimsJws(token).getBody();
//            return Long.parseLong(claims.getAudience());
            final Claims claims = parseJwt(token);
            return Long.parseLong(claims.getAudience());

        } catch (Exception e) {
            log.warn("토큰 변환 중 에러 발생: {}", token);
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Access 토큰 파싱
     * @param jwt
     * @return
     */
    public static Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(jwt)
                .getBody();

        return claims;
    }

    public TokenResponse createTokenDto(Member member) {

        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(member.getId(), accessTokenExpireTime);
        String refreshToken = createRefreshToken(member.getId(), refreshTokenExpireTime);

        final MemberToken memberToken = MemberToken.create(accessToken, refreshToken, DateTimeUtils.convertToLocalDateTime(refreshTokenExpireTime), member.getId());
        memberTokenRepository.save(memberToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpireDate(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .memberId(member.getId())
               // .memberRole(member.getMemberRole().toString())
                .build();
    }

    private String createRefreshToken(Long memberId, Date expirationTime) {
        return Jwts.builder()
                .setSubject(JwtTokenType.REFRESH.name())
                .setAudience(Long.toString(memberId))
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public String createAccessToken(Long memberId, Date expirationTime) {
        return Jwts.builder()
                .setSubject(JwtTokenType.ACCESS.name())
                .setAudience(Long.toString(memberId))
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Optional<Long> resolveMemberId(String token) {

        log.info("resolving ::::::::::::::::::::::::::::" + token);
        final Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
            log.info("claims :::::::::::::::::::::::" + claims);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Jwt 서명입니다. token: {}", token, e);
            return Optional.empty();
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다. token: {}", token, e);
            return Optional.empty();
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다. token: {}", token, e);
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다. token: {}", token, e);
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(claims.getSubject())
                    .map(Long::parseLong);
        } catch (NumberFormatException e) {
            log.error("잘못된 토큰입니다. 'sub' claim 이 Long 타입이어야합니다. token: {}", token);
            return Optional.empty();
        }
    }


    /**
     * access token 만료 시간 생성
     * @return
     */
    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    /**
     * refresh token 만료 시간 생성
     * @return
     */
    private Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    /**
     * 토큰 파싱 후 property 변환
     * @param token
     * @return
     */
    public Claims getTokenClaims(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw e;
        }
        return claims;
    }

    /**
     * 토큰 유효 검사
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            MemberToken memberToken = memberTokenRepository.findByAccessToken(token);
            if(memberToken.getTokenExpirationTime().isBefore(LocalDateTime.now())){
                throw new BaseException(BaseResponseStatus.EXPIRED_JWT);
            }
            return true;
        } catch (JwtException e) {  // 토큰 변조
            log.warn("토큰 변조 감지: {}", token);
            e.printStackTrace();
        } catch (Exception e) {
            log.warn("토큰 검증 에러 발생: {}", token);
            e.printStackTrace();
            throw e;
        }

        return false;
    }

    public boolean isTokenExpired(LocalDateTime now, LocalDateTime tokenExpiredTime) {
        if (now.isAfter(tokenExpiredTime)) { // 토큰 만료된 경우
            return true;
        }
        return false;
    }

    public boolean isTokenExpired(Date now, Date tokenExpiredTime) {
        if (now.after(tokenExpiredTime)) { // 토큰 만료된 경우
            return true;
        }
        return false;
    }

}
