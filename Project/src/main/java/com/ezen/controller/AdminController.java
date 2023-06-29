package com.ezen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.Service.LectorsService;
import com.ezen.Service.UserService;
import com.ezen.model.Lectors;
import com.ezen.model.LectorsDTO;
import com.ezen.model.Role;
import com.ezen.model.Users;

@Controller
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	LectorsService lectorsService;

	// 테스트용
	@GetMapping("/test")
	public String testTest(Model model) {

		return "/admin/test";
	}

	@GetMapping("/adminMain")
	public String adminMain() {
		return "/admin/adminMain";
	}

	@GetMapping("/userDetail")
	public String userDetail(@RequestParam("username") String username, Model model) {

		List<Users> userList = userService.getUserlist(username);
		model.addAttribute("userList", userList);

		return "/admin/userDetail";
	}

	@PostMapping("/giftToken")
	public String giftToken(@RequestParam("username") String username, @RequestParam("number") String number,
			Model model) {

		double token = Double.parseDouble(number);
		double oldToken = userService.getFindTokenByUsername(username);
		double beforeToken = oldToken + token;

		userService.getUpdateToken(username, beforeToken);

		List<Users> allUserlist = userService.getAllUserlist();
		model.addAttribute("allUserlist", allUserlist);

		return "redirect:/allUserView";
	}

	@PostMapping("/userKick")
	public String userKick(@RequestParam("user_id") String user_id, @RequestParam("username") String username) {

		int id = Integer.parseInt(user_id);
		userService.deleteById(id);

		return "/admin/adminMain";
	}

	@GetMapping("/lectorsPermission")
	public String lectorsPermission(Pageable pageable, Model model) {

		Page<Lectors> notPermissionLectorList = lectorsService.getNotPermissionLectorsList(pageable);
		
		int nowPage = notPermissionLectorList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, notPermissionLectorList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("notPermissionLectorList", notPermissionLectorList);

		return "/admin/lectorsPermission";
	}

	@GetMapping("/lectorDetail")
	public String lectorDetail(@RequestParam("lector_num") String lectorNum, Model model) {

		int lector_num = Integer.parseInt(lectorNum);
		List<Lectors> lectorDetailList = lectorsService.getFindNotPermissionLectorsListByLectorNum(lector_num);

		model.addAttribute("lectorDetailList", lectorDetailList);

		return "/admin/lectorDetail";
	}

	@PostMapping("/permissionDecision")
	public String permissionDecision(@RequestParam("permissionDecision") String permissionDecision, LectorsDTO dto,
										Pageable pageable, Model model) {

		if (permissionDecision.equals("O")) { // 승인
			userService.getUpdateRole(dto.getUser_id());
			lectorsService.updateAdmin_Permission(dto.getLector_num());

		} else {
			lectorsService.deleteLectorBylector_num(dto.getLector_num());
		}

		Page<Lectors> notPermissionLectorList = lectorsService.getNotPermissionLectorsList(pageable);
		
		int nowPage = notPermissionLectorList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, notPermissionLectorList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("notPermissionLectorList", notPermissionLectorList);

		return "/admin/lectorsPermission";
	}

	// 페이징 처리
	@GetMapping("/allUserView")
	public String allUserView(Pageable pageable, Model model) {

		// 타입을 Users로 지정해서 자동으로 db정보가 입력됨
		Page<Users> pageList = userService.getPagingUserlist(pageable);

		int nowPage = pageList.getPageable().getPageNumber() + 1;
		// Page가 0부터 시작해서 +1 해줌

		int startPage = Math.max(nowPage - 4, 1);
		// [6] [7] [8] [9] [10](현재 페이지) [11] [12] [13] [14] [15]
		// 현재 페이지 기준으로 뒤에 4개를 출력
		// 뒤에 1은 시작 페이지가 1보다 작으면 시작페이지를 1로 설정

		int endPage = Math.min(nowPage + 5, pageList.getTotalPages());
		// [6] [7] [8] [9] [10](현재 페이지) [11] [12] [13] [14] [15]
		// 현재 페이지 기준으로 앞으로 5개를 출력

		// pageList.getTotalPages()는 전체 페이지 수를 반환함
		// 만약 전체 페이지가 15까지 있고, nowPage + 5가 20이라면, 20까지 출력하지않고, 15(전체 페이지)를 출력

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		model.addAttribute("pageList", pageList);

		return "/admin/allUserView";
	}

	// 검색 기능
	@GetMapping("/userSearch")
	public String userSearch(@RequestParam(value = "roleCheck")String role, 
							 @RequestParam("searchOption") String searchOption,
							 @RequestParam("keyword") String keyword, 
							 Pageable pageable, Model model) {
		
		if (role != "") {

			if (searchOption.equals("id")) {
				Page<Users> pageList = userService.searchUserByIdAndRole(keyword, role, pageable);

				int nowPage = pageList.getPageable().getPageNumber() + 1;
				int startPage = Math.max(nowPage - 4, 1);
				int endPage = Math.min(nowPage + 5, pageList.getTotalPages());
				
				model.addAttribute("roleCheck", role);
				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage);
				model.addAttribute("pageList", pageList);
				
				return "/admin/searchUserView";

			} else if (searchOption.equals("username")) {
				Page<Users> pageList = userService.searchUserByUsernameAndRole(keyword, role, pageable);

				int nowPage = pageList.getPageable().getPageNumber() + 1;
				int startPage = Math.max(nowPage - 4, 1);
				int endPage = Math.min(nowPage + 5, pageList.getTotalPages());
				
				model.addAttribute("roleCheck", role);
				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage);
				model.addAttribute("pageList", pageList);

				return "/admin/searchUserView";

			} else {
				Page<Users> pageList = userService.searchUserByNameAndRole(keyword, role, pageable);

				int nowPage = pageList.getPageable().getPageNumber() + 1;
				int startPage = Math.max(nowPage - 4, 1);
				int endPage = Math.min(nowPage + 5, pageList.getTotalPages());
				
				model.addAttribute("roleCheck", role);
				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage);
				model.addAttribute("pageList", pageList);

				return "/admin/searchUserView";
			}

		} else { // role 체크

			if (searchOption.equals("id")) {
				Page<Users> pageList = userService.searchUserById(keyword, pageable);

				int nowPage = pageList.getPageable().getPageNumber() + 1;
				int startPage = Math.max(nowPage - 4, 1);
				int endPage = Math.min(nowPage + 5, pageList.getTotalPages());
				
				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage);
				model.addAttribute("pageList", pageList);

				return "/admin/searchUserView";

			} else if (searchOption.equals("username")) {
				Page<Users> pageList = userService.searchUserByUsername(keyword, pageable);

				int nowPage = pageList.getPageable().getPageNumber() + 1;
				int startPage = Math.max(nowPage - 4, 1);
				int endPage = Math.min(nowPage + 5, pageList.getTotalPages());

				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage);
				model.addAttribute("pageList", pageList);

				return "/admin/searchUserView";

			} else {
				Page<Users> pageList = userService.searchUserByName(keyword, pageable);

				int nowPage = pageList.getPageable().getPageNumber() + 1;
				int startPage = Math.max(nowPage - 4, 1);
				int endPage = Math.min(nowPage + 5, pageList.getTotalPages());

				model.addAttribute("searchOption", searchOption);
				model.addAttribute("keyword", keyword);
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("startPage", startPage);
				model.addAttribute("endPage", endPage);
				model.addAttribute("pageList", pageList);

				return "/admin/searchUserView";
			}
		}
	}
	
	
	
	

}
