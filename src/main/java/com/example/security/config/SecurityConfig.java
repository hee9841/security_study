package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //정보 저장, 검증 할때 해시로 암호화 시켜서 함
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //csrf disable
        //세션 방식에서는 세션이 항상 고정되어서 csrf 공격을 필수적으로 방어해줘어야함
        //jwt방식은 세션을 stateless 상태로 관리해서 csrf 공격를 방어하지 않아도 되어서 disable 상태로 둘꺼임
        //from 로그인, http basic 인증 방식 disable -> jwt 방식으로 사용할거라서
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);

        //경로별 인가 작업
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login", "/", "/join").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
            );



        //세션은 서버의 메모리에 유저 정보를 젖아해 동일한 브라우저에 오는 요청을 기억할 수 있음
        //따라서 로그인을 했다면 매번 요청마다 인증 과정 없이 서션에서 기억하고 있기 때문에 특정한 작업을 진행할 수 있음
        // 서버에 저장되는 세션은 브라우저로 발급해준 JSESSION이라는 쿠키를 통해 특정할 수 있음
        //이 떄 세션을 STATELESS로 관리할 경우 서버측 메모리에 저장하지 않기 때문에 동일한 브라우저에서 요청하더라도
        //매번 새로운 사용자로 특정하게 됨

        //세션을 stateless 상태로 설정
        //요청이 서버에 도착한 뒤 부터 나가기 까지만 SecurityContextHolder가 관리하는 SecurityContext에
        // Authentication 객체로 들어감
        // 엄밀히 정의하면 세션은 아니지만, 세션 비슷한 느낌??
        http.sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
