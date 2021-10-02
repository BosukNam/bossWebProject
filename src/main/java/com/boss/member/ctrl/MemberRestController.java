package com.boss.member.ctrl;

import com.boss.member.service.MemberService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

}
