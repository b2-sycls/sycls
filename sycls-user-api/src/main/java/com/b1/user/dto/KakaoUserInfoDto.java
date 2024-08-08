package com.b1.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoUserInfoDto {

    private Long id;
    private String nickname;
    private String email;
    @Setter
    private boolean newUser;

    public static KakaoUserInfoDto of(Long id, String nickname, String email) {
        return KakaoUserInfoDto.builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .build();
    }
}