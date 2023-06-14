package com.ezen.controller;

import java.util.List;  


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.Service.RechargeService;
import com.ezen.Service.UserService;
import com.ezen.model.Recharge;
import com.ezen.model.RechargeDTO;
import com.ezen.model.Users;

@Controller
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;
    
    @Autowired
    RechargeService rechargeService;
    
    

    
    @GetMapping("/checkTerms")
    public String checkTerms() {
		return "checkTerms";
    }
 
    @GetMapping("/join")
    public String join(Model model) {
        return "join";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    
    @GetMapping("/idCheck")
    @ResponseBody
    public String checkUsername(@RequestParam("id") String username) {
    	
    	int checkNum = userService.getSameIdCheck(username);
        
    	if (checkNum == 1) {
            return "duplicate";
            
        } else {
            return "available";
        }
    }

    
    @PostMapping("/joinProc")
    public String joinProc(Users users) {
    	
        String rawPassword = users.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        
        users.setPassword(encPassword);
        
        userService.getSave(users);
        
        return "redirect:/";
    }
    
    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model) {
    	
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        String role = userService.getFindRoleByUsername(username);
        
        model.addAttribute("role", role);
        model.addAttribute("username", username);
        
        return "/user/loginSuccess";
    }
    
    
    @GetMapping("myPage")
    public String myPage() {
    	return "/user/myPage";  // 단순 이동 경로 설정
    }
    
    @PostMapping("/tokenOrderList")
    public String tokenOrderList(Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);
    	List<Recharge> tokenOrderList = rechargeService.getTokenOrderList(user_id);
    	
    	model.addAttribute("tokenOrderList", tokenOrderList);
    	
		return "/user/tokenOrderList";
    }
    
    
    @PostMapping("myPageUpdate")
    public String myPageUpdate(Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	List<Users> userList = userService.getUserlist(username);
    	model.addAttribute("userList", userList);
    	
    	return "/user/myPageUpdate";
    }
    
    @PostMapping("passwordUpdate")
    public String passwordUpdate() {
    	return "/user/passwordUpdate";
    }
    
    
    @PostMapping("passwordUpdateProc")
    public String passwordUpdateProc(@RequestParam("oldPassword")String oldPassword, 
    								 @RequestParam("newPassword")String newPassword,
    								 Model model) {

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	String pass = userService.getUserpassword(username);
    	
    	boolean check = bCryptPasswordEncoder.matches(oldPassword, pass);
    	boolean sameCheck = bCryptPasswordEncoder.matches(newPassword, pass);
    	
    	String msg = "";
    	
    	if(check == true) { // 비밀번호가 일치할 경우 정상 작동
    		
    		// 비밀번호 암호화
            String encPassword = bCryptPasswordEncoder.encode(newPassword);
            userService.getUpdatePassword(username, encPassword);	
   
    		msg = "비밀번호가 정상적으로 수정되었습니다.";
    		model.addAttribute("msg", msg);
    		return "/user/myPage";
    		
    	} else if(sameCheck == true) { // 기존에 사용하던 비밀번호와 새로 사용할 비밀번호가 일치할 경우
    		msg = "기존에 사용하던 비밀번호는 사용하실 수 없습니다.";
    		model.addAttribute("msg", msg);
    		return "/user/passwordUpdate";
    		
    	} else { // 비밀번호가 일치하지않을 경우
    		msg = "비밀번호가 일치하지않습니다. 다시 확인해주십시오.";    
    		model.addAttribute("msg", msg);
    		return "/user/passwordUpdate";
    	}
    }
    
    
			   
    
    @PostMapping("recharge")
    public String recharge() {
    	return "/user/recharge";
    }
    
    
    @PostMapping("/rechargeProc")
    public String rechargeProc(Recharge recharge, RechargeDTO dto, Model model) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);
    	recharge.setUser_id(user_id);
    	
    	// 기존에 보유하고있던 토큰 조회
    	double beforeToken = userService.getFindTokenByUsername(username);
    	
    	// 토큰 갯수 합산
    	double afterToken = beforeToken + dto.getPay();
    	
    	rechargeService.getRechargeOrderSave(recharge);
    	userService.getUpdateToken(username, afterToken);
    	
    	model.addAttribute("username", username);
    	
    	return "/user/loginSuccess";
    }
    
    @GetMapping("/updateBy")
    public String updateBy(@RequestParam("paramName") String paramName, Model model) {
     
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	if(paramName.equals("name")) {
    		String beforeName = userService.getFindNameByUsername(username);
    		model.addAttribute("beforeName", beforeName);    		
    		return "/user/updateByName";
    		
    	} else if(paramName.equals("email")) {
    		String beforeEmail = userService.getFindEmailByUsername(username);
    		model.addAttribute("beforeEmail", beforeEmail); 
    		return "/user/updateByEmail";
    		
    	} else if(paramName.equals("phone")) {
    		String beforePhone = userService.getFindPhoneByUsername(username);
    		model.addAttribute("beforePhone", beforePhone); 
    		return "/user/updateByPhone";
    		
    	} else {
    		String beforePrefer_lecture = userService.getFindPrefer_lectureByUsername(username);
    		model.addAttribute("beforePrefer_lecture", beforePrefer_lecture); 
    		return "/user/updateByPrefer_lecture";
    	}
    }
    
    @PostMapping("/updateUserName")
    public String updateName(@RequestParam("afterName")String newName, Model model) {
    	
    	String msg = "";
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	userService.getUpdateName(newName, username);
    	msg = "이름을 정상적으로 변경했습니다.";
		model.addAttribute("msg", msg);
    	
    	return "/user/myPage";
    }
    
    @PostMapping("/updateUserEmail")
    public String updateEmail(@RequestParam("afterEmail")String newEmail, Model model) {
    	
    	String msg = "";
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	userService.getUpdateEmail(newEmail, username);
    	msg = "이메일을 정상적으로 변경했습니다.";
		model.addAttribute("msg", msg);
    	
    	return "/user/myPage";
    }
    
    @PostMapping("/updateUserPhone")
    public String updatePhone(@RequestParam("afterPhone")String newPhone, Model model) {
    	
    	String msg = "";
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	userService.getUpdatePhone(newPhone, username);
    	msg = "번호를 정상적으로 변경했습니다.";
		model.addAttribute("msg", msg);
    	
    	return "/user/myPage";
    }
    
    @PostMapping("/updateUserPrefer_lecture")
    public String updatePrefer_lecture(@RequestParam("prefer_lecture[]")String[] prefer_lecture, Model model) {
    	
    	String newPrefer_lecture = String.join(",", prefer_lecture);
    	
    	String msg = "";
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	userService.getUpdatePrefer_lecture(newPrefer_lecture, username);
    	msg = "선호 항목을 정상적으로 변경했습니다.";
		model.addAttribute("msg", msg);
    	
    	return "/user/myPage";
    }
    
    
    
}
