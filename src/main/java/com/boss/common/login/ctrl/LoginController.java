package com.boss.common.login.ctrl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    private final RedisTemplate redisStringTemplate;
    private final KafkaTemplate kafkaTemplate;

    public LoginController(RedisTemplate redisStringTemplate, KafkaTemplate kafkaTemplate) {
        this.redisStringTemplate = redisStringTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("redisKeys", redisStringTemplate.keys("*"));
        return "welcome";
    }

    @GetMapping("/login")
    public String loginPage() {
        kafkaTemplate.send("SAMPLE_TOPIC", "hello, kafka!");
        return "login";
    }
}
