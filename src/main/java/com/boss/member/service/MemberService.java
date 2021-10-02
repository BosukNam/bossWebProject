package com.boss.member.service;

import com.boss.member.dto.Member;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface MemberService extends UserDetailsService {
    Optional<Member> getMember(Integer seq);
    Optional<Member> getMember(String memberId);
    List<Member> getAllMembers();
    List<Member> getAllMembers(String name);
    List<Member> getSignUpMembers();
    List<Member> getSignOutMembers();
    long getMemberCount();
    long getSignUpMemberCount();
    long getSignOutMemberCount();
    void updateMember(Member member);
    void signUpMember(Member member);
    void signOutMember(Member member);
}
