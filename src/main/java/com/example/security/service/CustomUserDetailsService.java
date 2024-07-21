package com.example.security.service;

import com.example.security.dto.CustomUserDetails;
import com.example.security.entity.Member;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member userInfo = userRepository.findByUsername(username);

        if(userInfo != null){
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userInfo);
        }

        return null;
    }
}
