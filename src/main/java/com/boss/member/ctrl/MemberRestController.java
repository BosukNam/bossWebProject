package com.boss.member.ctrl;

import com.boss.member.dto.MemberDTO;
import com.boss.member.service.MemberService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public HttpEntity<CollectionModel<MemberDTO>> getAllMembers() {
        List<MemberDTO> memberList = memberService.getAllMembers();
        memberList.stream().map(memberDTO -> setSelfRel(memberDTO));
        Link link = linkTo(methodOn(MemberRestController.class).getAllMembers()).withRel("allMembers");
        CollectionModel<MemberDTO> result = CollectionModel.of(memberList, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/member/{id}")
    public HttpEntity<MemberDTO> one(@PathVariable String id) {
        Optional<MemberDTO> memberDTO = memberService.getMember(id);
        if(memberDTO.isPresent()) {
            MemberDTO member = setSelfRel(memberDTO.get());
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private MemberDTO setSelfRel(MemberDTO memberDTO) {
        return memberDTO.add(linkTo(methodOn(MemberRestController.class).one(memberDTO.getMemberId())).withSelfRel());
    }

}
