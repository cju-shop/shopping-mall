package com.cju.shoppingmall.member.service;

import org.springframework.stereotype.Service;

import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;

    public MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public Member join(Member member) {
        return repository.save(member);
    }
}
