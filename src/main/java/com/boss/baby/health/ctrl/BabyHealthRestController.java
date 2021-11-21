package com.boss.baby.health.ctrl;

import com.boss.baby.health.service.BabyHealthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BabyHealthRestController {
    private final BabyHealthService babyHealthService;

    public BabyHealthRestController(BabyHealthService babyHealthService) {
        this.babyHealthService = babyHealthService;
    }

}
