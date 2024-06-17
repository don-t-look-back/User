package com.example.dlbuser.domains.member.service;

import com.example.dlbuser.auth.jwt.JwtProvider;
import com.example.dlbuser.common.exceptions.BaseException;
import com.example.dlbuser.common.response.BaseResponseStatus;
import com.example.dlbuser.domains.auth.model.response.TokenResponse;
import com.example.dlbuser.domains.member.entity.Member;

import com.example.dlbuser.domains.member.model.request.*;
import com.example.dlbuser.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    // 회원가입
    @Transactional
    public TokenResponse signUp(SignUpRequest request) {

        confirmPasswordCheck(request.getConfirmPassword(), request.getMemberPassword());

        Member member = memberRepository.save(request.toEntity(passwordEncoder));
        return jwtProvider.createTokenDto(member);
    }

    private void confirmPasswordCheck(String confirmPasswordCheck, String password) {

        if (!confirmPasswordCheck.equals(password)) {
            throw new BaseException(BaseResponseStatus.MISMATCH_MEMBER_PASSWORD);
        }
    }



    // 고객 계정 삭제
    public void deleteCustomer(Long id) {

        Member member = findMemberOrThrow(id);
        memberRepository.deleteById(id);
    }

    private Member findMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> {
            throw new BaseException(BaseResponseStatus.NOT_FIND_USER);
        });
    }


    public Member findById(Long memberId) {

        return findMemberOrThrow(memberId);
    }

//    public List<Member> findByNameAndId(String search) {
//
//        if(search.equals("")){
//            return memberRepository.findAll().stream()
//                    .filter(m -> m.getDeletedAt() == null).toList();
//        } else{
//            return memberRepository.findByNameAndId(search);
//        }
//    }



    public void initializePassword(MemberIdsRequestDto req, Long memberId) {

        for(Long id : req.getIds()){
            Member member = findMemberOrThrow(id);
            member.updatePassword(passwordEncoder);
            memberRepository.save(member);
        }
    }


    public Boolean checkPassword(Long memberId, String password) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));
        return passwordEncoder.matches(password, member.getMemberPassword());
    }


    public void deleteMembers(MemberIdsRequestDto req, Long memberId) {

        // checkUserRole(findMemberOrThrow(memberId));
        for(Long id : req.getIds()){
            Member member = findMemberOrThrow(id);
            member.updateDeletedAt();
            memberRepository.save(member);
        }
    }


}
