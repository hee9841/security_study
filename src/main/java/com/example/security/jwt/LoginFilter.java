package com.example.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * https://www.devyummi.com/page?id=668d55e4ceede2499082fc28
 */
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String pw = obtainPassword(request);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");

        //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
        //세번째 인자는 -> role값
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username, pw, null);

        //Authentication Manager 한테 인증르 받음
        //token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        System.out.println("success");
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("fail");
    }
}
