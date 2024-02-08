package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration //빈등록(IoC관리)
//@EnableWebSecurity //시큐리티 필터가 등록이 된다. 설정을 SecurityConfig 하면 된다.
//@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig {

//    private PrincipalDetailService principalDetailService;


    @Bean //IoC가 된다. return 객체를 스프링이 관리
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    //시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
    // 해당 password가 어떤 것으로 해쉬가 되어 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
    //스프링 시큐리티 5.7.1 버전부터
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception { //Authwired 버그(정상작동함)
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 최신 버전(2.7)으로 시큐리티 필터 변경
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. csrf 비활성화
        http.csrf().disable();

        // 2. 인증 주소 설정
        http.authorizeRequests(
                authorize -> authorize.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**").permitAll()
                        .anyRequest().authenticated()
        );

        // 3. 로그인 처리 프로세스 설정
        http.formLogin(f -> f.loginPage("/auth/loginForm")
                .loginProcessingUrl("/auth/loginProc")
                .defaultSuccessUrl("/")
        );

        return http.build();
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }*/



    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable() //CSRF 토큰 비활성화 (테스트 시 걸어두는 게 좋음)
        .authorizeRequests()
            .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
            .permitAll()
            .anyRequest()
            .authenticated()
        .and()
            .formLogin()
            .loginPage("/auth/loginForm")
            .loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
            .defaultSuccessUrl("/") //로그인 성공 시 "/" 로 이동
//            .failureUrl("/fail") //로그인 실패 시 "/fail" 로 이동
        ;
    }*/
}
