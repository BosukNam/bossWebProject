package com.boss.baby.health.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;

@Data
@Entity(name="tblbabyHealth")
@Relation(collectionRelation = "babyHealths", itemRelation = "babyHealth")
public class BabyHealth extends RepresentationModel<BabyHealth> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "mb_id")
    private String memberId;
    @Column(name = "rec_date")
    private String recordDate;
}
