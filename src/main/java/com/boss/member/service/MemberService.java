package com.boss.member.service;

import com.boss.member.dto.Member;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface MemberService extends UserDetailsService {
    CollectionModel<Member> getAllMembers();
    CollectionModel<Member> getAllMembers(String name);
    CollectionModel<Member> getSignUpMembers();
    CollectionModel<Member> getSignOutMembers();
    Optional<Member> getMember(Integer seq);
    Optional<Member> getMember(String memberId);
    long getMemberCount();
    long getSignUpMemberCount();
    long getSignOutMemberCount();
    void updateMember(Member member);
    void signUpMember(Member member);
    void signOutMember(Member member);
}
