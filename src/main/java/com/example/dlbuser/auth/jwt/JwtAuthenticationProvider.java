package com.example.dlbuser.auth.jwt;

import com.example.dlbuser.domains.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        log.info("authenticate 메서드 실행 !!!!!!!!!!!!!!!!!!!");
//        log.info("authenticate :::::::::::: " + authentication.getPrincipal());
//
//        if (!supports(authentication.getClass())) {
//            return null;
//        }
//
//        String token = authentication.getPrincipal().toString();
//
//        Long memberId = jwtProvider.resolveMemberId(token)
//                .orElseThrow(() -> new BadCredentialsException("Validation failed"));
//
//
//        Member member = memberService.findById(memberId).orElseThrow(() -> {
//            throw new BaseException(BaseResponseStatus.NOT_FIND_USER);
//        });
//
//        return new PreAuthenticatedAuthenticationToken(
//                member.getMemberId(),
//                null,
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//        );
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication));
    }
}