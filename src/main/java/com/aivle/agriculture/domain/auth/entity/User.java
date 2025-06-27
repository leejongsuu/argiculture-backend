package com.aivle.agriculture.domain.auth.entity;

import com.aivle.agriculture.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String provider; // 소셜 로그인 제공자
    private String providerId; // 소셜 로그인 제공자 ID


    @Builder
    public User(String name, String provider, String providerId) {
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
    }
}
