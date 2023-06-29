package com.ezen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ezen.Service.LectorsService;
import com.ezen.Service.UserService;
import com.ezen.model.Lectors;
import com.ezen.model.LectorsDTO;

@Controller
public class LectureController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	LectorsService lectorsService;

	@GetMapping("/upgradeLector")
	public String upgradeLecture() {
		return "/lecture/joinLector";
	}
	
// 가입 승인을 위해 어드민 활성화 후 수정 예정
	@PostMapping("/joinLectorProc")
	public String joinLectorProc(Lectors lectors, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username);
        lectors.setUser_id(user_id);
        
        //lectorsService.getSave(lectors);
        
		return "/user/myPage";
	}
	
}

