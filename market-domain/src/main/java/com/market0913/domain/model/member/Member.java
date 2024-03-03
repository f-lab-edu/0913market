package com.market0913.domain.model.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "id", nullable = false, unique = true, columnDefinition = "varchar(30) COMMENT '회원 아이디'")
    private String id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(20) COMMENT '회원 타입'")
    private MemberType memberType;
}
