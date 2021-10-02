package com.boss.common.login.ctrl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private RedisTemplate redisStringTemplate;

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("redisKeys", redisStringTemplate.keys("*"));
        return "welcome";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
