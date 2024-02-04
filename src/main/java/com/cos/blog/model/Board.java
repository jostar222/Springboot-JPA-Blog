package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")// 대용량 데이터, @Lob만 주면 tinytext로 나옴
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.

    private int count; // 조회수

    // Many = Board, User = One -> 한 명의 유저는 여러 개의 게시글을 쓸 수 있다.
    // ManyToOne 기본 fetch 전략이 EAGER (즉시 로딩)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    // One = Board, Many = Reply -> 하나의 게시글은 여러 개의 댓글을 가질 수 있다.
    // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니야) DB에 컬럼을 만들지 마세요.
    // board를 select할 때 join문을 통해서 값을 얻기위해 필요한 것이다.
    // OneToMany 기본 fetch 전략이 LAZY(지연 로딩)
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 게시글 상세에서 댓글이 바로 보이므로 EAGER 사용
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}
