package com.example.dlbuser.common.config.interceptor;

import com.example.dlbuser.auth.jwt.JwtProvider;
import com.example.dlbuser.common.exceptions.BaseException;
import com.example.dlbuser.common.response.BaseResponseStatus;
import com.example.dlbuser.domains.member.entity.Member;
import com.example.dlbuser.domains.member.entity.constant.ApprovedState;
import com.example.dlbuser.domains.member.repository.MemberRepository;
import com.example.dlbuser.domains.memberToken.entity.MemberToken;
import com.example.dlbuser.domains.memberToken.entity.constant.JwtTokenType;
import com.example.dlbuser.domains.memberToken.entity.constant.RemainingTokenTime;
import com.example.dlbuser.domains.memberToken.repository.MemberTokenRepository;
// import com.querydsl.core.util.StringUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthInterceptor implements HandlerInterceptor {


    private final JwtProvider tokenProvider;
    private final MemberTokenRepository memberTokenRepository;
    private final MemberRepository memberRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행: {}", requestURI);

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(memberTokenRepository.findByAccessToken(authorizationHeader).getTokenExpirationTime().isBefore(LocalDateTime.now())){
            throw new BaseException(BaseResponseStatus.EXPIRED_JWT);
        }

//        if (StringUtils.isNullOrEmpty(authorizationHeader)) {
//            sendErrorToResponse(response, HttpStatus.UNAUTHORIZED, "토큰 정보가 없습니다.");
//            return false;
//        }

        final String token = authorizationHeader.replace("Bearer", "").trim();
        log.info("token: {}", token);

        if (!tokenProvider.validateToken(token)) {
            sendErrorToResponse(response, HttpStatus.FORBIDDEN, "잘못된 토큰 정보입니다.");
            return false;
        }

        Claims tokenClaims = tokenProvider.getTokenClaims(token);

        final String tokenType = tokenClaims.getSubject(); // ACCESS or REFRESH
        final Long memberId = Long.parseLong(tokenClaims.getAudience());



        if (JwtTokenType.ACCESS.name().equals(tokenType)) {
            Date expiration = tokenClaims.getExpiration();
            //LocalDateTime expirationTime = expiration.

            // access token 만료
            if (tokenProvider.isTokenExpired(new Date(), expiration)) {
                sendErrorToResponse(response, HttpStatus.UNAUTHORIZED, "Access token이 만료되었습니다.");
                return false;
            }

        } else if (JwtTokenType.REFRESH.name().equals(tokenType)) {
            MemberToken memberToken = memberTokenRepository.findByRefreshToken(token)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_JWT));

            final LocalDateTime refreshTokenExpirationTime = memberToken.getTokenExpirationTime();

            //refresh token 만료 전이면 access token 재발급 및 Authorization Header 세팅
            if (!tokenProvider.isTokenExpired(LocalDateTime.now(), refreshTokenExpirationTime)) {
                Date accessTokenExpireTime = tokenProvider.createAccessTokenExpireTime();
                String accessToken = tokenProvider.createAccessToken(memberId, accessTokenExpireTime);
                response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);

                memberToken.updateRefreshTokenExpireTime(LocalDateTime.now(), RemainingTokenTime.HOURS_72); // 리프레시 토큰 만료 시간 갱신

            } else if (tokenProvider.isTokenExpired(LocalDateTime.now(), refreshTokenExpirationTime)) { // refresh token 만료 됐을 경우
                sendErrorToResponse(response, HttpStatus.UNAUTHORIZED, "Refresh Token이 만료되었습니다.");
                return false;
            }
        }
        return true;
    }

    private void sendErrorToResponse(HttpServletResponse response,HttpStatus httpStatus, String errorMessage) throws IOException {
        log.warn(errorMessage);
        response.sendError(httpStatus.value(), errorMessage);
    }

}
