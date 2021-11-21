package com.boss.baby.health.service;

import com.boss.baby.health.dto.BabyHealth;
import com.boss.baby.health.repo.BabyHealthRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("babyHealthService")
public class BabyHealthServiceImpl implements BabyHealthService {
    private final BabyHealthRepo babyHealthRepo;

    public BabyHealthServiceImpl(BabyHealthRepo babyHealthRepo) {
        this.babyHealthRepo = babyHealthRepo;
    }

    @Override
    public List<BabyHealth> getAllBabyHealths() {
        return babyHealthRepo.findAll();
    }
}
