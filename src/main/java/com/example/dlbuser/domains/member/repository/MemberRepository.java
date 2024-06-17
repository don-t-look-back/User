package com.example.dlbuser.domains.member.repository;

import com.example.dlbuser.domains.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Member findByMemberId(String memberId);

    /// Optional<Member> findById(String id);

    //Optional<Member> findByMemberId(String memberId);

   // boolean existsByMemberId(String userId);

}
