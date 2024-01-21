package com.cos.blog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Timestamp;

// ORM -> Java(다른언어 포함) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 즉, 오라클이면 시퀀스, MySQL이면 auto_increment
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 30)
    private String usernmae; // 아이디

    @Column(nullable = false, length = 100) // 123456 => 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email; // myEmail (PhysicalNamingStrategyStandardImpl), my_email(CamelCaseToUnderscoresNamingStrategy) //

    @ColumnDefault("'user'") // ''로 문자라는걸 알려줘야한다.
    private String role; // Enum을 쓰는게 좋다.(도메인(영역, 범위)을 정할 수 있음) // admin, user, manager (String으로 하게 되면 실수로 managerrr로 들어갈 수도 있으므로)

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
}
