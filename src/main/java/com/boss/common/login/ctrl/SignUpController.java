package com.boss.common.login.ctrl;

import com.boss.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/signUp")
public class SignUpController {

    private final MemberService memberService;

    public SignUpController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping({"","/"})
    public String signUpPage() {
        return "signUp";
    }

    @GetMapping("/isUsernameExists/{username}")
    @ResponseBody
    public boolean isUsernameExists(@PathVariable String username) {
        return memberService.getMember(username).isPresent();
    }
}
