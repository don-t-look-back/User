package com.example.dlbuser.domains.memberToken.repository;

import com.example.dlbuser.domains.memberToken.entity.MemberToken;

import java.util.List;

public interface MemberTokenCustomRepository {

    List<MemberToken> findByMemberId(Long memberId);
}
