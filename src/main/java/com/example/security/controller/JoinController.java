package com.example.security.controller;

import com.example.security.dto.JoinDTO;
import com.example.security.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {
        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "ok";
    }
}
