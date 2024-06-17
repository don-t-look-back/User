package com.example.dlbuser.domains.memberToken.repository;

import com.example.dlbuser.domains.memberToken.entity.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberTokenRepository extends JpaRepository<MemberToken, Long>, MemberTokenCustomRepository{

    Optional<MemberToken> findByRefreshToken(String refreshToken);

    MemberToken findByAccessToken(String token);
}
