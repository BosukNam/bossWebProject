package com.boss.member.service;

import com.boss.member.ctrl.MemberRestController;
import com.boss.member.dto.MemberDTO;
import com.boss.member.repo.MemberRepo;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service("memberService")
public class MemberServiceImpl extends DefaultOAuth2UserService implements MemberService {
    private final MemberRepo memberRepo;

    public MemberServiceImpl(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public CollectionModel<MemberDTO> getAllMembers() {
        return setWithRelMembers(setSelfRel(memberRepo.findAll()), "allMembers");
    }

    @Override
    public CollectionModel<MemberDTO> getAllMembers(String name) {
        return setWithRelMembers(setSelfRel(memberRepo.findAllByMemberNameIgnoreCaseContaining(name)),
                "allMembersByName");
    }

    @Override
    public CollectionModel<MemberDTO> getSignUpMembers() {
        return setWithRelMembers(setSelfRel(memberRepo.findAllByIsSignOutIsLessThan(1)), "signUpMembers");
    }

    @Override
    public CollectionModel<MemberDTO> getSignOutMembers() {
        return setWithRelMembers(setSelfRel(memberRepo.findAllByIsSignOutIsGreaterThan(0)), "signOutMembers");
    }

    @Override
    public Optional<MemberDTO> getMember(Integer seq) {
        return setSelfRel(memberRepo.findById(seq));
    }

    @Override
    public Optional<MemberDTO> getMember(String memberId) {
        return setSelfRel(memberRepo.findByMemberId(memberId));
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
    public void updateMember(MemberDTO memberDTO) {
        memberRepo.saveAndFlush(memberDTO);
    }

    @Override
    public void signUpMember(MemberDTO memberDTO) {
        memberRepo.saveAndFlush(memberDTO);
    }

    @Override
    public void signOutMember(MemberDTO memberDTO) {
        memberRepo.saveAndFlush(memberDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberDTO> memberEntityWrapper = memberRepo.findByMemberId(username);
        MemberDTO memberEntity = memberEntityWrapper.orElse(null);
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

    private MemberDTO setSelfRel(MemberDTO memberDTO) {
        return memberDTO.add(linkTo(methodOn(MemberRestController.class).one(memberDTO.getMemberId())).withSelfRel());
    }

    private Optional<MemberDTO> setSelfRel(Optional<MemberDTO> memberDTO) {
        if(memberDTO.isPresent()) {
            memberDTO.get().add(linkTo(methodOn(MemberRestController.class).one(memberDTO.get().getMemberId())).withSelfRel());
            return memberDTO;
        }
        else
            return null;
    }

    private List<MemberDTO> setSelfRel(List<MemberDTO> members) {
        members.stream().forEach(memberDTO -> setSelfRel(memberDTO));
        return members;
    }

    private CollectionModel<MemberDTO> setWithRelMembers(List<MemberDTO> members, String withRel) {
        Link link = linkTo(methodOn(MemberRestController.class).getAllMembers()).withRel(withRel);
        return CollectionModel.of(members, link);
    }

}
