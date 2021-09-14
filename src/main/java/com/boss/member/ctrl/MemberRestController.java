package com.boss.member.ctrl;

import com.boss.member.dto.MemberDTO;
import com.boss.member.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/all")
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers();
    }
}
