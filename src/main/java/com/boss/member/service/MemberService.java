package com.boss.member.service;

import com.boss.member.dto.MemberDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface MemberService extends UserDetailsService {
    CollectionModel<MemberDTO> getAllMembers();
    CollectionModel<MemberDTO> getAllMembers(String name);
    CollectionModel<MemberDTO> getSignUpMembers();
    CollectionModel<MemberDTO> getSignOutMembers();
    Optional<MemberDTO> getMember(Integer seq);
    Optional<MemberDTO> getMember(String memberId);
    long getMemberCount();
    long getSignUpMemberCount();
    long getSignOutMemberCount();
    void updateMember(MemberDTO memberDTO);
    void signUpMember(MemberDTO memberDTO);
    void signOutMember(MemberDTO memberDTO);
}
