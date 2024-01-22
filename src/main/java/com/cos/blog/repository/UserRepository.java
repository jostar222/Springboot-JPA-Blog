package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// DAO(Data Access Object
// 자동으로 bean 등록이 된다.
// @Repository // 생략이 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> { // User 테이블 관리, User 테이블의 Primary key는 integer다

}
