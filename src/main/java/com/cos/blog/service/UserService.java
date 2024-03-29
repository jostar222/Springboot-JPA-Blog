package com.cos.blog.service;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//서비스가 필요한 이유: 트랜잭션관리, 서비스의 의미!!
//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{ //orElseGet -> Username으로 찾았는데 없으면 빈 객체를 만들어라.
                return new User();
        });
        return user;
    }

    @Transactional
    public int 회원가입(User user) {
        String rawPassword = user.getPassword(); //1234원문
        String encPassword = encoder.encode(rawPassword); //해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);

        try {
            userRepository.save(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserService:회원가입():"+e.getMessage());
        }
        return -1;
    }

    @Transactional
    public void 회원수정(User user) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        // select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
        User persistence = userRepository.findById(user.getId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("회원수정 실패:아이디를 찾을 수 없습니다.");
                });

        //Validate 체크
        if(persistence.getOauth() == null || persistence.getOauth().equals("")){
            String rawPassword = user.getPassword(); //원문
            String encPassword = encoder.encode(rawPassword); //해쉬
            persistence.setPassword(encPassword);
            persistence.setEmail(user.getEmail());
        }

        //회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 된다.
        //영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.

    }





    // 아래는 전통적인 방식의 로그인
    /*
    @Transactional(readOnly = true) //Select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
    */
}
