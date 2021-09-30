package com.boss.member.repo;

import com.boss.member.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberRepo extends JpaRepository<Member, Integer> {
    List<Member> findAllByMemberNameIgnoreCaseContaining(String memberName);
    List<Member> findAllByIsSignOutIsLessThan(int isSignOut);
    List<Member> findAllByIsSignOutIsGreaterThan(int isSignOut);
    Optional<Member> findByMemberId(String memberId);
    long countAllByIsSignOutIsLessThan(int isSignOut);
    long countAllByIsSignOutIsGreaterThan(int isSignOut);
}
