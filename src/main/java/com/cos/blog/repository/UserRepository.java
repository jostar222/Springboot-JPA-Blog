package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// DAO(Data Access Object
// 자동으로 bean 등록이 된다.
// @Repository // 생략이 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> { // User 테이블 관리, User 테이블의 Primary key는 integer다
    //SELECT * FROM user WHERE username = ?
    Optional<User> findByUsername(String username);

}

    /*
    아래 2가지 방법이 가능
    JPA Naming 쿼리 전략
    SELECT * FROM user WHERE username = ?1 AND password = ?2; (1:username, 2:password)
    아래 함수명처럼 입력 시 위 쿼리가 실행된다.
     */

    //1번
    //User findByUsernameAndPassword(String username, String password);

    //2번
    /*
    @Query(value = "SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
    User login(String username, String passoword);
     */
