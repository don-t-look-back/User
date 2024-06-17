package com.example.dlbuser.resolver;

import com.example.dlbuser.auth.jwt.JwtProvider;
import com.example.dlbuser.common.exceptions.BaseException;
import com.example.dlbuser.common.response.BaseResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class RequestMemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    //private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final boolean hasMemberAnnotation = parameter.hasParameterAnnotation(RequestMemberId.class);
        final boolean hasLong = Long.class.isAssignableFrom(parameter.getParameterType());

        return hasMemberAnnotation && hasLong;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null){
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }
//        //MemberToken memberToken = JwtProvider.getToken(token);
//        if(memberToken.getTokenExpirationTime().isBefore(LocalDateTime.now())){
//            throw new BaseException(BaseResponseStatus.EXPIRED_JWT);
//        }
        return JwtProvider.getMemberId(token);
    }
}
