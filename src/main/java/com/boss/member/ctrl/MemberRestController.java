package com.boss.member.ctrl;

import com.boss.member.dto.MemberDTO;
import com.boss.member.service.MemberService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public HttpEntity<CollectionModel<MemberDTO>> getAllMembers() {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @GetMapping("/member/{id}")
    public HttpEntity<MemberDTO> one(@PathVariable String id) {
        Optional<MemberDTO> memberDTO = memberService.getMember(id);
        if(memberDTO.isPresent()) {
            return new ResponseEntity<>(memberDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
