//package com.example.dlbuser.domains.member.repository;
//
//import com.example.dlbuser.domains.member.entity.Member;
//// import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
//
//@Repository
//public class MemberCustomRepositoryImpl implements MemberCustomRepository{
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
////    @Override
////    public List<Member> findByNameAndId(String search) {
////
////        List<Member> members = new ArrayList<>();
////
////        JPAQueryFactory qf = new JPAQueryFactory(entityManager);
////        List<Member> query = qf.select(member)
////                .from(member)
////                .where(member.memberId.contains(search).or(member.memberName.contains(search)))
////                .fetch();
////        return Optional.ofNullable(query).orElse(members);
////    }
//}
