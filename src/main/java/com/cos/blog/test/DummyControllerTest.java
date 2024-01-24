package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

    @Autowired // DummyControllerTest가 메모리에 뜰 때 UserRepository도 메모리에 같이 뜬다. (의존성주입=DI(Dependency Injection)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {

        // 스프링부트 버전업 되면서 없는 id를 넣어도 예외가 발생하지 않음. 없을 시 그냥 무시함.
        try {
            userRepository.deleteById(id);
        } catch(Exception e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제되었습니다. id: " + id;
    }

    // save함수는 id를 전달하지 않으면 insert를 해주고
    // save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    // save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
    // email, password
    @Transactional // 함수 종료시에 자동 commit이 됨.
    @PutMapping("/dummy/user/{id}") // 경로 같아도 Put, Get 메서드 알아서 구분해준다.
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 => Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아줌
        System.out.println("id = " + id);
        System.out.println("password = " + requestUser.getPassword());
        System.out.println("email = " + requestUser.getEmail());

        // save() 사용 시 코드
        // user 객체를 받아와서 변경할 컬럼만 set해주고 save() 한다. 가져온 user 객체는 null 값이 없으므로 null이 들어가지 않음.

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // userRepository.save(user);

        // 더티 체킹? (찌꺼기 체킹?)
        /*
        여기에서 Dirty란 상태의 변화가 생긴 정도로 이해하시면 됩니다.
        즉, Dirty Checking이란 상태 변경 검사 입니다.
        JPA에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영해줍니다.
        이때 변화가 있다의 기준은 최초 조회 상태입니다.
        당연히 이런 상태 변경 검사의 대상은 영속성 컨텍스트가 관리하는 엔티티에만 적용됩니다.
           - detach된 엔티티 (준영속)
           - DB에 반영되기 전 처음 생성된 엔티티 (비영속)
        준영속/비영속 상태의 엔티티는 Dirty Checking 대상에 포함되지 않습니다.
        즉, 값을 변경해도 데이터베이스에 반영되지 않는다는 것이죠.
        */

       return user;
    }


    // http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 2건의 데이터를 리턴받아 볼 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }


    // {id} 주소로 파라미터를 전달 받을 수 있음.
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // user/4을 찾으면 내가 데이터베이스에서 못 찾아오게 되면 user가 null이 될 것 아냐?
        // 그럼 return null 이 리턴이 되니, 프로그램에 문제가 있지 않겠니?
        // Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해.

        // 람다식 작성법
        /*
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
        });
        return user;
        */


        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자가 없습니다.");
            }
        });
        // 요청: 웹브라우저
        // user 객체 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> JSON (Gson 라이브러리)
        // 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줌.
       return user;

    }

    // http://localhost:8000/blog/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) { // key=value (약속된 규칙)
        System.out.println("id = " + user.getId());
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());
        System.out.println("email = " + user.getEmail());
        System.out.println("role = " + user.getRole());
        System.out.println("createDate = " + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

}
