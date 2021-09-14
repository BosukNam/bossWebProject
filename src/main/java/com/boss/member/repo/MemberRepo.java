package com.boss.member.repo;

import com.boss.member.dto.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberRepo extends JpaRepository<MemberDTO, Integer> {
    List<MemberDTO> findAllByMemberNameIgnoreCaseContaining(String memberName);
    List<MemberDTO> findAllByIsSignOutIsLessThan(int isSignOut);
    List<MemberDTO> findAllByIsSignOutIsGreaterThan(int isSignOut);
    Optional<MemberDTO> findByMemberId(String memberId);
    long countAllByIsSignOutIsLessThan(int isSignOut);
    long countAllByIsSignOutIsGreaterThan(int isSignOut);
}
