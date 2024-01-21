package com.cos.blog.test;

import lombok.*;

//lombok이 Getter Setter 생성
@Data
@NoArgsConstructor
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    // 빌더패턴: 파라미터 순서를 지키지 않아도 됨
    @Builder
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
