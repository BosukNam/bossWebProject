package com.boss.baby.health.ctrl;

import com.boss.baby.health.service.BabyHealthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class BabyHealthController {
    private final BabyHealthService babyHealthService;

    public BabyHealthController(BabyHealthService babyHealthService) {
        this.babyHealthService = babyHealthService;
    }

    @GetMapping("/timetable")
    public String timetable() {
        return "index";
    }

}
