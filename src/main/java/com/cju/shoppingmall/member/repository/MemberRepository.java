package com.cju.shoppingmall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cju.shoppingmall.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);
    Optional<Member> findByUsername(String loginId);
}
