package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> Java(다른언어 포함) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성이 된다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
// @DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 즉, 오라클이면 시퀀스, MySQL이면 auto_increment
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 30, unique = true) // 유니크키로 중복방지, 옵션 추가 후 테이블 재생성
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 123456 => 해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email; // myEmail (PhysicalNamingStrategyStandardImpl), my_email(CamelCaseToUnderscoresNamingStrategy) //

    // @ColumnDefault("'user'") // ''로 문자라는걸 알려줘야한다. default값 안 들어가게 주석 처리
    // RoleType으로 type 강제,
    @Enumerated(EnumType.STRING) // DB는 RoleType이라는게 없다. 알려줘야함
    private RoleType role; // Enum을 쓰는게 좋다.(도메인(영역, 범위)을 정할 수 있음) // admin, user, manager (String으로 하게 되면 실수로 managerrr로 들어갈 수도 있으므로)

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
}
