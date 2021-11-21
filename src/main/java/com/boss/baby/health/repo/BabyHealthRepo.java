package com.boss.baby.health.repo;

import com.boss.baby.health.dto.BabyHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "babyHealths", path = "babyHealth")
public interface BabyHealthRepo extends JpaRepository<BabyHealth, Integer> {
    List<BabyHealth> findAllByMemberIdIgnoreCaseContaining(String memberId);
}
