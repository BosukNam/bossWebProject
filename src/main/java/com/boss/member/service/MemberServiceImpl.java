package com.boss.member.service;

import com.boss.member.dto.Member;
import com.boss.member.repo.MemberRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("memberService")
public class MemberServiceImpl extends DefaultOAuth2UserService implements MemberService {
    private final MemberRepo memberRepo;

    public MemberServiceImpl(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepo.findAll();
    }

    @Override
    public List<Member> getAllMembers(String name) {
        return memberRepo.findAllByMemberNameIgnoreCaseContaining(name);
    }

    @Override
    public List<Member> getSignUpMembers() {
        return memberRepo.findAllByIsSignOutIsLessThan(1);
    }

    @Override
    public List<Member> getSignOutMembers() {
        return memberRepo.findAllByIsSignOutIsGreaterThan(0);
    }

    @Override
    public Optional<Member> getMember(Integer seq) {
        return memberRepo.findById(seq);
    }

    @Override
    public Optional<Member> getMember(String memberId) {
        return memberRepo.findByMemberId(memberId);
    }

    @Override
    public long getMemberCount() {
        return memberRepo.count();
    }

    @Override
    public long getSignUpMemberCount() {
        return memberRepo.countAllByIsSignOutIsLessThan(1);
    }

    @Override
    public long getSignOutMemberCount() {
        return memberRepo.countAllByIsSignOutIsGreaterThan(0);
    }

    @Override
    public void updateMember(Member member) {
        memberRepo.saveAndFlush(member);
    }

    @Override
    public void signUpMember(Member member) {
        memberRepo.saveAndFlush(member);
    }

    @Override
    public void signOutMember(Member member) {
        memberRepo.saveAndFlush(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberEntityWrapper = memberRepo.findByMemberId(username);
        Member memberEntity = memberEntityWrapper.orElse(null);
        if(memberEntity==null) throw new UsernameNotFoundException("Username is not founded!");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_BASIC_MEMBER"));
        return new User(memberEntity.getMemberId(), memberEntity.getMemberPw(), authorities);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(oAuth2UserRequest);
        Map<String, Object> attributes = user.getAttributes();
        Set<GrantedAuthority> authoritySet = new HashSet<>(user.getAuthorities());
        authoritySet.add(new SimpleGrantedAuthority("ROLE_OAUTH2_MEMBER"));
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        return new DefaultOAuth2User(authoritySet, attributes, userNameAttributeName);
    }

}
