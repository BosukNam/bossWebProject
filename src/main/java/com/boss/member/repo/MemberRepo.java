package com.boss.member.repo;

import com.boss.member.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "members", path = "members")
public interface MemberRepo extends JpaRepository<Member, Integer> {
    List<Member> findAllByMemberNameIgnoreCaseContaining(String memberName);
    List<Member> findAllByIsSignOutIsLessThan(int isSignOut);
    List<Member> findAllByIsSignOutIsGreaterThan(int isSignOut);
    Optional<Member> findByMemberId(String memberId);
    long countAllByIsSignOutIsLessThan(int isSignOut);
    long countAllByIsSignOutIsGreaterThan(int isSignOut);
}
