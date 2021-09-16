package com.boss.member.service;

import com.boss.member.dto.MemberDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface MemberService extends UserDetailsService {
    List<MemberDTO> getAllMembers();
    List<MemberDTO> getAllMembers(String name);
    List<MemberDTO> getSignUpMembers();
    List<MemberDTO> getSignOutMembers();
    Optional<MemberDTO> getMember(Integer seq);
    Optional<MemberDTO> getMember(String memberId);
    long getMemberCount();
    long getSignUpMemberCount();
    long getSignOutMemberCount();
    void updateMember(MemberDTO memberDTO);
    void signUpMember(MemberDTO memberDTO);
    void signOutMember(MemberDTO memberDTO);
}
