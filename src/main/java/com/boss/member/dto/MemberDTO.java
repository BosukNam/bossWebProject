package com.boss.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="tblmber")
public class MemberDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "mb_id")
    private String memberId;
    @Column(name = "mb_pw")
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
