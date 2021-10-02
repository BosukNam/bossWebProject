package com.boss.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;

@Data
@Entity(name="tblmber")
@Relation(collectionRelation = "members", itemRelation = "member")
public class Member extends RepresentationModel<Member> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "mb_id")
    private String memberId;
    @Column(name = "mb_pw")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String memberPw;
    @Column(name = "mb_name")
    private String memberName;
    @Column(name = "mb_email")
    private String memberEmail;
    @Column(name = "mb_addr")
    private String memberAddr;
    @Column(name = "mb_tel")
    private String memberTel;
    @Column(name = "mb_signout")
    private Boolean isSignOut;
}
