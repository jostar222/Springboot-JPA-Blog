package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {
    @Id // Pirmary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne // Many = Reply, One = Board -> 하나의 글에 여러 개의 댓글을 달 수 있다.
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne // Many = Reply, One = user -> 하나의 유저가 여러 개의 댓글을 달 수 있다.
    @JoinColumn(name = "userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

}
