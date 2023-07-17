package com.ezen.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.Service.ComplainLectureService;
import com.ezen.Service.LectorsService;
import com.ezen.Service.LectureService;
import com.ezen.Service.PracticeRoomRentalOrderService;
import com.ezen.Service.PracticeRoomRentalService;
import com.ezen.Service.ReviewService;
import com.ezen.Service.UserReviewService;
import com.ezen.Service.UserService;
import com.ezen.model.ComplainLecture;
import com.ezen.model.Lectors;
import com.ezen.model.LectorsDTO;
import com.ezen.model.Lecture;
import com.ezen.model.PracticeRoomRentalOrder;
import com.ezen.model.Review;
import com.ezen.model.Role;
import com.ezen.model.UserReview;
import com.ezen.model.Users;

@Controller
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	LectorsService lectorsService;
	
	@Autowired
	LectureService lectureService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	UserReviewService userReviewService;
	
	@Autowired
	PracticeRoomRentalService practiceRoomRentalService;
	
	@Autowired
	PracticeRoomRentalOrderService practiceRoomRentalOrderService;
	
	@Autowired
	ComplainLectureService complainLectureService;
	
	
	
	
	
	
	
	
	
	
	
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
	
	@GetMapping("/lectureManagementByAdmin")
	public String lectureManagementByAdmin(Pageable pageable, Model model) {
        
        Page<Lecture> lecturePageList = lectureService.findAllLectureList(pageable);
        
        int nowPage = lecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 4, lecturePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
        model.addAttribute("lecturePageList", lecturePageList);
		
		return "/admin/lectureManagementByAdmin";
	}
	
	@GetMapping("/notEndLectureListViewByAdmin")
	public String notEndLectureListViewByAdmin(Pageable pageable, Model model) {
		
        Page<Lecture> lecturePageList = lectureService.findNotEndLectureList(pageable);
        
        int nowPage = lecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 4, lecturePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
        model.addAttribute("lecturePageList", lecturePageList);
		
		return "/admin/lectureManagementByAdmin";
	}
	
	@GetMapping("/endLectureListViewByAdmin")
	public String endLectureListViewByAdmin(Pageable pageable, Model model) {
        
        Page<Lecture> lecturePageList = lectureService.findEndLectureList(pageable);
        
        int nowPage = lecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 4, lecturePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
        model.addAttribute("lecturePageList", lecturePageList);
		
		return "/admin/endLectureListViewByAdmin";
	}
	
	@GetMapping("/lectureManagementDetailByAdmin")
	public String lectureManagementDetailByAdmin(Lecture lecture, Model model) {
		
		List<Lecture> lectureDetailList = lectureService.getFindLectureListByLecture_num(lecture.getLecture_num());
		
		// 시간 비교 후 종료 강의인지 종료 이전 강의인지 체크
		LocalDate nowDate = LocalDate.now();
		LocalDate lectureDate = lectureDetailList.get(0).getLecture_date();
		
		int comparisonDate = nowDate.compareTo(lectureDate);
		
		String dateCheckMessage = ""; // "O"는 삭제 수정 가능
		String reservationCheckMessage = ""; // 강의 예약한 수강생 블랙 가능 여부
		
		if (comparisonDate == 0) { // 날짜가 동일할 때,
			LocalTime nowTime = LocalTime.now();
			LocalTime lectureStartTime = lectureDetailList.get(0).getLecture_start_time();
			
			int comparisonTime = nowTime.compareTo(lectureStartTime);
			
				if(comparisonTime < 0) { // nowTime이 lectureTime보다 이전인 경우
					
						reservationCheckMessage = "O";
						
						// 예약 인원 비교, 없을 경우 null로 나옴
						if(lectureDetailList.get(0).getUser_num() == null) {
							dateCheckMessage = "O";
							
						} else {
							dateCheckMessage = "X";
						}
					
				} else { // 강의 시작 시간 이후의 경우,
					dateCheckMessage = "X";
					reservationCheckMessage = "X";
				}
			
		} else if (comparisonDate < 0) { // nowDate가 lectureDate보다 이전인 경우
		    
			reservationCheckMessage = "O";
			
			// 예약 인원 비교, 없을 경우 null로 나옴
			if(lectureDetailList.get(0).getUser_num() == null) {
				dateCheckMessage = "O";
				
			} else {
				dateCheckMessage = "X";
			}
			
		} else { // nowDate가 lectureDate보다 이후인 경우
			
			dateCheckMessage = "X";
			reservationCheckMessage = "X";
		}
		
		List<Users> reservationUserList = new ArrayList<>();
		double lectureFee = 0;
		
		String user_nums = lectureDetailList.get(0).getUser_num();
		
			if(user_nums != null && !user_nums.isEmpty()) {
				
				String[] userNumArray = user_nums.split(",");
					
					lectureFee = userNumArray.length * lectureDetailList.get(0).getPrice();
				
				for(String users : userNumArray) {
					reservationUserList.add(userService.findManner_scoreByUser_id(Integer.parseInt(users)));
				}
			} 
		
		int lecture_fee = (int) lectureFee;
		
		List<Review> reviewList = reviewService.findReviewByLecture_num(lecture.getLecture_num());
		
			String nullCheckMessage = "X";
			
				if(reviewList.isEmpty()) {
					nullCheckMessage = "O";
				}
		
		// 리뷰 열람
		model.addAttribute("nullCheckMessage", nullCheckMessage);
		model.addAttribute("reviewList", reviewList);
		
		// 토큰 수령
		model.addAttribute("lecture_fee",  lecture_fee);
		model.addAttribute("reservationUserList", reservationUserList);
		
		// 삭제 및 수정
		model.addAttribute("dateCheckMessage", dateCheckMessage);
		model.addAttribute("reservationCheckMessage", reservationCheckMessage);
		
		// 강의 상세 내용
		model.addAttribute("lectureDetailList", lectureDetailList);
		
		return "/admin/lectureManagementDetailByAdmin";
	}
	
	@GetMapping("/userReviewView")
	public String userReviewView(Users users, Lecture lecture, Model model) {
		
		int user_id = userService.getFindIdByUsername(users.getUsername());
		
		List<UserReview> reviewList = 
				userReviewService.findUserReviewByLector_numAndUser_id(lecture.getLector_num(), user_id);
		
			String nullCheckMsg = "";
			String username = "";
			
				if(reviewList.isEmpty()) {
					nullCheckMsg = "O";
				
					} else {
						nullCheckMsg = "X";
						username = userService.findUsernameById(reviewList.get(0).getUser_id());
					}
				
			
		System.out.println("" + reviewList.get(0).toString());
				
		model.addAttribute("username", username);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("nullCheckMsg", nullCheckMsg);
		
		return "/admin/userReviewView";
	}
	
	@Transactional
	@PostMapping("/lectureUpdateByAdmin")
	public String lectureUpdateProc(@RequestParam(value="rentalCheck", defaultValue = "")String rentalCheck,
									@RequestParam(value="practiceRoom_id", defaultValue = "")String practiceRoom_id,
									@RequestParam(value="rentalStartTime", defaultValue = "")String rentalStartTime, 
									Lecture lecture) {
		
		Lecture old_lecture = lectureService.findLectureTypeByLecture_num(lecture.getLecture_num());
		
		int user_id = lectorsService.findUser_idByLector_num(old_lecture.getLector_num());
		String username = userService.findUsernameById(user_id);
		
	        if(rentalCheck.equals("O")) { 
	        	
		        	// 연습실 대여비 결제 코드
		            double nowToken = userService.getFindTokenByUsername(username);
		            double price = practiceRoomRentalService.findPracticeRoom_priceByPracticeRoom_id(Integer.parseInt(practiceRoom_id));
	            
	            userService.getUpdateToken(username, nowToken - price);
	            
		            // 연습실 렌탈 주문 생성 코드
		            // rentalEndTime은 자동 매핑이 안되서.. 손수 지정해줌
		            int endTime = Integer.parseInt(rentalStartTime) + 2;
		            String rentalEndTime = String.valueOf(endTime);
		            
		            PracticeRoomRentalOrder prro = new PracticeRoomRentalOrder();
	            
	            prro.setPracticeRoom_id(Integer.parseInt(practiceRoom_id));
	            prro.setLector_num(old_lecture.getLector_num());
	            prro.setRentalDate(lecture.getLecture_date().toString());
	            prro.setRentalStartTime(rentalStartTime);
	            prro.setRentalEndTime(rentalEndTime);
	            
	            practiceRoomRentalOrderService.getSave(prro);
		            int practiceRoomOrder_id = 
		            		practiceRoomRentalOrderService
		            		.findPracticeRoomOrder_idByLector_numAndRentalDateAndRentalStartTime
		            			(old_lecture.getLector_num(), old_lecture.getLecture_date(), rentalStartTime);
	            
	            // lecture의 rentalCheck에  렌탈 오더 주문 번호 저장해줄 생각임
	            String String_practiceRoomOrder_id = String.valueOf(practiceRoomOrder_id);
	            
	            lecture.setRentalCheck(String_practiceRoomOrder_id); // 연습실 렌탈 여부 확인을 위해
			}
        
        // JPA에서 단일 필드 수정의 경우, 알아서 DB에 저장까지 해주지만, 다중 필드를 수정할 경우, 별도로  save처리를 해줘야한단다..
        // 그래서 save로 처리한다.
        lectureService.getSave(lecture);
		
		return "redirect:/lectureManagementByAdmin";
	}
	
	@PostMapping("/lectureDeleteByAdmin")
	public String lectureDelete(Lecture lecture) {
		
		lectureService.deleteById(lecture.getLecture_num());
		
		return "redirect:/lectureManagementByAdmin";
	}
	
	@GetMapping("/complainManagement")
	public String complainManagement(Pageable pageable, Model model) {
		
		Page<ComplainLecture> allComplainPageList = complainLectureService.findComplainPageList(pageable);
		
		 	int nowPage = allComplainPageList.getPageable().getPageNumber() + 1;
			int startPage = Math.max(nowPage - 4, 1);
			int endPage = Math.min(nowPage + 4, allComplainPageList.getTotalPages());
			
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
	        model.addAttribute("allComplainPageList", allComplainPageList);
	        
		return "/admin/allComplainPageList";
	}
	
	@GetMapping("/unresolvedComplain")
	public String unresolvedComplain(Pageable pageable, Model model) {
		
		Page<ComplainLecture> unresolvedComplainPageList = complainLectureService.findUnresolvedComplainPageList(pageable);
		
		 	int nowPage = unresolvedComplainPageList.getPageable().getPageNumber() + 1;
			int startPage = Math.max(nowPage - 4, 1);
			int endPage = Math.min(nowPage + 4, unresolvedComplainPageList.getTotalPages());
			
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
	        model.addAttribute("allComplainPageList", unresolvedComplainPageList);
	        
		return "/admin/unresolvedComplain";
	}
	
	@GetMapping("/resolvedComplain")
	public String resolvedComplain(Pageable pageable, Model model) {
		
		Page<ComplainLecture> resolvedComplainPageList = complainLectureService.findResolvedComplainPageList(pageable);
		
		 	int nowPage = resolvedComplainPageList.getPageable().getPageNumber() + 1;
			int startPage = Math.max(nowPage - 4, 1);
			int endPage = Math.min(nowPage + 4, resolvedComplainPageList.getTotalPages());
			
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
	        model.addAttribute("allComplainPageList", resolvedComplainPageList);
	        
		return "/admin/resolvedComplain";
	}
	
	@GetMapping("/complainDetail")
	public String complainDetail(ComplainLecture cl, Model model) {
		
		// cl.getLecture_num();
		List<ComplainLecture> complainDetailList = 
				complainLectureService.complainDetailListByComplainLeture_num(cl.getComplainLeture_num());
		
		model.addAttribute("complainDetailList", complainDetailList);
		
		return "/admin/complainDetail";
	}
	
	@PostMapping("/complainResolveProc")
	public String complainResolveProc(@RequestParam("rightWrong")String rightWrong, 
										ComplainLecture cl) {
		
		ComplainLecture old_complainLecture = 
					complainLectureService.findComplainLectureTypeByComplainLeture_num(cl.getComplainLeture_num());
		
			old_complainLecture.setComplain_resolve("O");
			old_complainLecture.setProcessingResult(cl.getProcessingResult());
		
				if(rightWrong != "") { // 강사 잘못 없을 경우, 
					
					int old_complainCount = lectureService.
										findComplainCountByLecture_num(old_complainLecture.getLecture_num());
					
						// ComplainCount -1 해주기.
						lectureService.updateComplainCountByLecture_num(old_complainCount - 1, old_complainLecture.getLecture_num());
				} 
				
				complainLectureService.getSave(old_complainLecture);
				
		return "redirect:/complainManagement";
	}
	

}
