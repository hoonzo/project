package com.ezen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.Service.UserService;
import com.ezen.model.Users;

@Controller
public class AdminController {
	
	@Autowired
	UserService userService;
	
	
	
	@GetMapping("/adminMain")
	public String adminMain() {
		return "/admin/adminMain";
	}
	
	@PostMapping("/allUserView")
	public String allUserView(Model model) {
		
		List<Users> allUserlist = userService.getAllUserlist();
		model.addAttribute("allUserlist", allUserlist);
		
		return "/admin/allUserView";
	}
	
	@GetMapping("/userDetail")
	public String userDetail(@RequestParam("username")String username, Model model) {
		
		List<Users> userList = userService.getUserlist(username);
		model.addAttribute("userList", userList);
	
		return "/admin/userDetail";
	}
	
	
	@PostMapping("/giftToken")
	public String giftToken(@RequestParam("username") String username,
	                        @RequestParam("number") String number,
	                        Model model) {
		
		double token = Double.parseDouble(number);
	    double oldToken = userService.getFindTokenByUsername(username);
	    double beforeToken = oldToken + token;
	    
	    userService.getUpdateToken(username, beforeToken);

	    List<Users> allUserlist = userService.getAllUserlist();
	    model.addAttribute("allUserlist", allUserlist);

	    return "/admin/allUserView";
	}
	
	@PostMapping("/userKick")
	public String userKick(@RequestParam("user_id") String user_id,
						   @RequestParam("username") String username) {
			
		int id = Integer.parseInt(user_id);
		userService.deleteById(id);
		
		return "/admin/adminMain";
	}
	
	
	
	
	
	
	

}
