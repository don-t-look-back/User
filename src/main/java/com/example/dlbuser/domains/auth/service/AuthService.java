package com.example.dlbuser.domains.auth.service;

import com.example.dlbuser.auth.jwt.JwtProvider;
import com.example.dlbuser.common.exceptions.BaseException;
import com.example.dlbuser.common.response.BaseResponseStatus;
import com.example.dlbuser.domains.member.entity.Member;
import com.example.dlbuser.domains.member.repository.MemberRepository;
import com.example.dlbuser.domains.memberToken.entity.MemberToken;
import com.example.dlbuser.domains.memberToken.repository.MemberTokenRepository;
import com.example.dlbuser.domains.auth.model.request.LoginRequest;
import com.example.dlbuser.domains.auth.model.response.TokenResponse;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberTokenRepository memberTokenRepository;

    @Transactional
    public TokenResponse login(LoginRequest request) {

        Member member = memberRepository.findByMemberId(request.getMemberId());
//        if(!member.getApprovedState().equals(ApprovedState.APPROVED)) {
//            throw new BaseException(BaseResponseStatus.NOT_APPROVED_USER);
//        }
        verifyPassword(request.getMemberPassword(), member.getMemberPassword());

        return jwtProvider.createTokenDto(member);
    }

    private void verifyPassword(String requestPassword, String realPassword) {
        if (!passwordEncoder.matches(requestPassword, realPassword)) {
            throw new BaseException(BaseResponseStatus.INVALID_MEMBER_PASSWORD);
        }
    }

//    private Member checkMember(String memberId) {
//        return memberRepository.findByMemberId(memberId)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_MEMBER_NAME));
//    }

    public void logout(Long memberId) {

        List<MemberToken> tokens = memberTokenRepository.findByMemberId(memberId);

        for(MemberToken token : tokens){
            token.updateTokenExpireTime(LocalDateTime.now());

            Claims accessTokenClaims = jwtProvider.getTokenClaims(token.getAccessToken());
            Claims refreshTokenClaims = jwtProvider.getTokenClaims(token.getRefreshToken());
            accessTokenClaims.setExpiration(new Date());
            refreshTokenClaims.setExpiration(new Date());

            memberTokenRepository.save(token);
        }
    }

//    private String resolveAccessToken(Long memberId) {
//        return jwtProvider.createAccessToken(memberId);
//    }e
//
//    private String resolveRefreshToken(Long memberId) {
//        return jwtProvider.createRefreshToken(memberId);
//    }


}
