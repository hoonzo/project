package com.ezen.controller;

import java.sql.Timestamp;   
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;  


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.Service.LectorsService;
import com.ezen.Service.LectureOrderService;
import com.ezen.Service.LectureService;
import com.ezen.Service.RechargeService;
import com.ezen.Service.ReviewService;
import com.ezen.Service.UserService;
import com.ezen.model.Lecture;
import com.ezen.model.LectureOrder;
import com.ezen.model.Recharge;
import com.ezen.model.RechargeDTO;
import com.ezen.model.Users;
import com.ezen.model.Review;

@Controller
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;
    
    @Autowired
    RechargeService rechargeService;
    
    @Autowired
    LectorsService lectorsService;
    
    @Autowired
    LectureService lectureService;
    
    @Autowired
    LectureOrderService lectureOrderService;
    
    @Autowired
    ReviewService reviewService;
    
    

    
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
        
        int user_id = userService.getFindIdByUsername(username);        
        int checkLectorRegister = lectorsService.getCheckLectorRegistration(user_id);
        
        // 로그인 성공 시, 멤버쉽 종료 여부 체크
        if(checkLectorRegister > 0) {  // 만약 회원이 강사일 경우,
        	
        	int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        	int checkMembership = lectorsService.checkMembershipByLector_num(lector_num);
        	
        	// lectors의 membership이 O일 경우,
        	if(checkMembership > 0) {
        		
        		// MembershipEndDate 불러오기
        		Timestamp MembershipEndDate =  lectorsService.getFindMembershipEndDateByLector_num(lector_num);
     	        
     	        // Timestamp를 LocalDate로 변환
     	        LocalDate localDateMembershipEndDate = MembershipEndDate.toLocalDateTime().toLocalDate();
     	        
     	        boolean membershipIsEnd = localDateMembershipEndDate.equals(LocalDate.now());
     	        	
     	        	// 멤버쉽 종료일과 오늘 날짜가 일치할 경우
     	        	if(membershipIsEnd) {
     	        		lectorsService.membershipEnd(lector_num);
     	        		
     	        		//멤버쉽 종료되면 등록한 글들의 멤버쉽 여부도 변경하는 기능
     	        		lectureService.membershipEnd(lector_num);
     	        	}
        	}
        }

        String role = userService.getFindRoleByUsername(username);
        
        model.addAttribute("checkLectorRegister", checkLectorRegister);
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
    
    
			   
    
    @GetMapping("recharge")
    public String recharge() {
    	return "/user/recharge";
    }
    
    
    @GetMapping("/rechargeProc")
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

    
    @GetMapping("/lectureListView")
    public String lectureListView(Pageable pageable, Model model) {
    	
    	// lecture_place = 'X'인 값들을 불러온다.
    	List<Lecture> lecturePlaceIsXList = lectureService.findLecture_placeIsX();
    	
    	if(!lecturePlaceIsXList.isEmpty() ) { // X인 값이 있을 경우, 
    		
    		for(Lecture lec : lecturePlaceIsXList) {
    			
    			System.out.println("            체크                   " + lec.toString());
    			
    			LocalDate nowDate = LocalDate.now();
    			
    			// 강의 시작일 하루 전과 오늘의 날짜가 일치할 경우,
    			if(lec.getLecture_date().minusDays(1).isEqual(nowDate)) {
    				// 해당 강의 삭제 처리
    				lectureService.deleteById(lec.getLecture_num());
    			}
    		}
    	}
    	
    	Page<Lecture> lecturePageList = lectureService.getLecturePageList(pageable);
    	
    	int nowPage = lecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, lecturePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lecturePageList", lecturePageList);
    	
    	return "/user/lectureListView";
    }
    
    @GetMapping("/lectureThemeSelect")
    public String lectureThemeSelect() {
    	return "/user/lectureThemeSelect";
    }
    
    
	@GetMapping("/lectorThemeSelectProc")
	public String lectorThemeSelectProc(@RequestParam("lecture_theme") List<String> lectureThemes, 
											Pageable pageable, Model model) {
		
		int selectLectureThemesSize = 0;
		
		if(lectureThemes.contains("all")) {
			
			lectureThemes.remove(0); // 최초 전달된 all 을 지워주기위해서
			
			lectureThemes.add("piano");
			lectureThemes.add("guitar");
			lectureThemes.add("drum");
			lectureThemes.add("violin");
			lectureThemes.add("flute");
			lectureThemes.add("cello");
			lectureThemes.add("vocal");
			lectureThemes.add("dancing");
			lectureThemes.add("composition");
			selectLectureThemesSize = 9;
			
		} else {
			selectLectureThemesSize = lectureThemes.size();
			
		}
		
		Page<Lecture> searchLectureThemePageList = lectureService.getSearchThemePageList(pageable, selectLectureThemesSize, lectureThemes);
		
		
    	int nowPage = searchLectureThemePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, searchLectureThemePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lecturePageList", searchLectureThemePageList);
		
		model.addAttribute("lecture_theme", String.join(",", lectureThemes));
    	
    	return "/user/selectThemeLectureListView";
	}
	
	@GetMapping("/lectureDetail")
	public String lectureDetail(Lecture lecture, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	double myToken = userService.getFindTokenByUsername(username); // 결제 로직을 위해서
    	
    	int user_id = userService.getFindIdByUsername(username); // 내 아이디 전달을 위해
		
		int maxPersonnel = lectureService.findMaxPersonnelByLecture_num(lecture.getLecture_num()); // 강사가 설정한 최대 수강생 수
		
		String user_num = lectureService.findUser_numByLecture_num(lecture.getLecture_num());
		
		if(user_num == null) {
			List<Lecture> lectureDetailList = lectureService.getFindLectureListByLecture_num(lecture.getLecture_num());
			double review_score = lectorsService.findReview_scoreByLector_num(lecture.getLector_num());
			
			model.addAttribute("myToken", myToken);
			model.addAttribute("lectureDetailList", lectureDetailList);
			model.addAttribute("review_score", review_score);
			model.addAttribute("remainder", maxPersonnel);
			
			return "/user/lectureDetail";
			
		} else {
			String[] userNumArray = user_num.split(",");
	    	int ReservationPersonnel = userNumArray.length; // 현재 예약된 인원 수
	    	
			int remainder = maxPersonnel - ReservationPersonnel; // 잔여 수강 가능 수
			
			List<Lecture> lectureDetailList = lectureService.getFindLectureListByLecture_num(lecture.getLecture_num());
			double review_score = lectorsService.findReview_scoreByLector_num(lecture.getLector_num());
			
			model.addAttribute("myToken", myToken);
			model.addAttribute("lectureDetailList", lectureDetailList);
			model.addAttribute("review_score", review_score);
			model.addAttribute("remainder", remainder);
			
			model.addAttribute("user_id", user_id);
			model.addAttribute("userNumArray", userNumArray);
			
			return "/user/lectureDetail";
		}
	}
	
	@GetMapping("/lectorReview")
	public String lectorReview(Review review, Pageable pageable, Model model) {
		
		Page<Review> allReviewPageList = reviewService.findAllReviewByLector_num(pageable, review.getLector_num());
		
		String nullCheckMassege = "X";
		
		if(allReviewPageList.isEmpty()) {
			nullCheckMassege = "O";
				
		}
		
		int nowPage = allReviewPageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, allReviewPageList.getTotalPages());
		
		model.addAttribute("lector_num", review.getLector_num());
		model.addAttribute("nullCheckMassege", nullCheckMassege);
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		model.addAttribute("allReviewPageList", allReviewPageList);
		
		 return "/user/lectorReview"; 
	}
	
	
	
	@GetMapping("/lectureReservation")
	public String lectureReservation(Lecture lecture, LectureOrder lectureOrder) {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);
    	String user_num = lectureService.findUser_numByLecture_num(lecture.getLecture_num());
    	
    	int maxPersonnel = lectureService.findMaxPersonnelByLecture_num(lecture.getLecture_num()); // 강사 설정한 최대 인원
    	
    	if(user_num == null) { // 예약이 하나도 없을 경우,(최초 예약)
    		
    		if(maxPersonnel == 1) { // 개인 교습일 경우, 
    			lectureService.reservationFullCheckUpdate(lecture.getLecture_num());
    		} 
    		
    		lectureService.maxPersonnelCheckNumUpdateByLecture_num(1, lecture.getLecture_num());
    		lectureService.lectureReservationSetUser(Integer.toString(user_id), lecture.getLecture_num());
    		
    	} else { // user_num + "," + user_id 해줘야함.
    		
    		String[] userNumArray = user_num.split(",");
    		int ReservationPersonnel = userNumArray.length; // 현재 예약 인원
    		
    		lectureService.maxPersonnelCheckNumUpdateByLecture_num(ReservationPersonnel + 1, lecture.getLecture_num());
    		
    		// 현재 예약 인원 +1 이 강사 설정한 최대 인원이라면,
    		if(ReservationPersonnel + 1 == maxPersonnel) {
    			lectureService.reservationFullCheckUpdate(lecture.getLecture_num());
    		} 
    		
    		// 예약 처리
    		String user_nums = user_num + "," + Integer.toString(user_id);
    		lectureService.lectureReservationSetUser(user_nums, lecture.getLecture_num());
    		
    	}
    	// 강의 주문번호 생성
		lectureOrder.setUser_id(user_id);
		lectureOrder.setLecture_title(lecture.getLecture_title());
		lectureOrderService.lectureOrderSave(lectureOrder);
		
		// 결제
		double myToken = userService.getFindTokenByUsername(username);
    	double price = lecture.getPrice();
    	userService.getUpdateToken(username, myToken - price); 
		
		return "redirect:/lectureListView";
	}
	
	
	@GetMapping("/viewOptionSelect")
	public String viewOptionSelect(@RequestParam("viewOptionCheck")String viewOption,
									@RequestParam("searchOption")String searchOption,
									@RequestParam("keyword")String keyword,
									Pageable pageable, Model model) {
		
		Page<Lecture> searchingPageList = null;
		
		// 공백일 땐 ""로 전달된다.
		if(viewOption.equals("timeDeadline")) {
					
			if(searchOption.equals("title")) {
				// service의 title조회 메소드 호출
				searchingPageList = lectureService.findTimeDeadlinePageListByTitleLikeKeyword(pageable, keyword);
			
			} else {
				// service의 place조회 메소드 호출
				searchingPageList = lectureService.findTimeDeadlinePageListByPlaceLikeKeyword(pageable, keyword);
			} 
				
			
		} else if(viewOption.equals("reservationDeadline")) {
			
			if(searchOption.equals("title")) {
				searchingPageList = lectureService.findReservationDeadlinePageListByTitleLikeKeyword(pageable, keyword);
				
			} else {
				searchingPageList = lectureService.findReservationDeadlinePageListByPlaceLikeKeyword(pageable, keyword);
			}
			
		} else if(viewOption.equals("highScore")) {
			
			if(searchOption.equals("title")) {
				searchingPageList = lectureService.findHighScorePageListByTitleLikeKeyword(pageable, keyword);
				
			} else {
				searchingPageList = lectureService.findHighScorePageListByPlaceLikeKeyword(pageable, keyword);
			}
			
		} else if(viewOption.equals("lowPrice")) {
			
			if(searchOption.equals("title")) {
				searchingPageList = lectureService.findLowPricePageListByTitleLikeKeyword(pageable, keyword);
				
			} else {
				searchingPageList = lectureService.findLowPricePageListByPlaceLikeKeyword(pageable, keyword);
			}
			
		} else { // 전체 검색일 경우,
			
			if(searchOption.equals("title")) {
				searchingPageList = lectureService.searchLectureByTitleLikeKeyword(pageable, keyword);
				
			} else {
				searchingPageList = lectureService.searchLectureByPlaceLikeKeyword(pageable, keyword);
			}
		}
		
		int nowPage = searchingPageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, searchingPageList.getTotalPages());
		
		
		model.addAttribute("viewOption", viewOption);
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("keyword", keyword);
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lecturePageList", searchingPageList);
		
		return "/user/optionSearchView";
	}
	
	@GetMapping("/lectureOrderList")
	public String lectureOrderList(Model model, Pageable pageable) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);
		
    	Page<LectureOrder> lectureOrderList = lectureOrderService.findAllLectureOrderListByUser_id(pageable, user_id);
    	
    	int nowPage = lectureOrderList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, lectureOrderList.getTotalPages());
    	
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
    	model.addAttribute("lectureOrderList", lectureOrderList);
    	
		return "/user/lectureOrderListView";
	}

	@GetMapping("/lectureOrderDetail")
	public String lectureOrderDetail(LectureOrder lectureOrder, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);
    	String String_user_id = Integer.toString(user_id);
    	
		// lector
		String lector_reviewWriteCheckSign = "";
		
		String lector_revieWriteCheck = lectorsService.findRevieWriteCheckByLector_num(lectureOrder.getLector_num());
		
		if(lector_revieWriteCheck == null) {
			lector_reviewWriteCheckSign = "X";
			
		} else {
			
			String[] lector_revieWriteCheckArray = lector_revieWriteCheck.split(",");
			
			for (String userNums : lector_revieWriteCheckArray) {
				
				if(userNums.equals(String_user_id)) {
					lector_reviewWriteCheckSign = "O";
					break;
				
				} else {
					lector_reviewWriteCheckSign = "X";
				}
			}
		}
		
		// lecture
		String lecture_reviewWriteCheckSign = "";
		List<Review> myReviewList = null;
		
		String lecture_revieWriteCheck = lectureService.findReviewWriteCheckByLecture_num(lectureOrder.getLecture_num());
		
		if(lecture_revieWriteCheck == null) {
			lecture_reviewWriteCheckSign = "X";
			
		} else {
			String[] lecture_revieWriteCheckArray = lecture_revieWriteCheck.split(",");
			
			for (String userNums : lecture_revieWriteCheckArray) {
				
				if(userNums.equals(String_user_id)) {
					
					lecture_reviewWriteCheckSign = "O";
					myReviewList = reviewService.findMyReviewByLecture_numAndUser_id(lectureOrder.getLecture_num(), user_id);
					break;
					
				} else {
					lecture_reviewWriteCheckSign = "X";
				}
			}
		}
		
		// view에서 처리 예정.
		model.addAttribute("lector_reviewWriteCheckSign", lector_reviewWriteCheckSign);
		model.addAttribute("lecture_reviewWriteCheckSign", lecture_reviewWriteCheckSign);
		model.addAttribute("myReviewList", myReviewList);
		
		List<Lecture> lectureDetailList = lectureService.getFindLectureListByLecture_num(lectureOrder.getLecture_num());
		
		model.addAttribute("cancel_check", lectureOrder.getCancel_check());
		model.addAttribute("lectureDetailList", lectureDetailList);
		
		return "/user/lectureOrderDetailView";
	}
	
	@GetMapping("/cancelLecture")
	public String cancelLecture(Lecture lecture, LectureOrder lectureOrder) {
		// Lecture lecture에 lecture_num, price 가져옴
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	double myToken = userService.getFindTokenByUsername(username);
    	
    	int user_id = userService.getFindIdByUsername(username);
    		
    		String user_id_str = String.valueOf(user_id);
    	
	    	String user_num = lectureService.findUser_numByLecture_num(lecture.getLecture_num());
	    	
	    	String[] userNumArray = user_num.split(",");
	    	
	    	ArrayList<String> newArray = new ArrayList<>();
    	
    	if(userNumArray.length == 1) { // user_num에 값이 1개만 있을 경우, null 로 업데이트
    		
    		lectureService.lectureReservationSetUser(null, lecture.getLecture_num());
    		
    	} else { // 1개 이상일 경우, 해당 값만 삭제하고 다시 ,로 처리해줘야함.
    		
    		for(String num : userNumArray) {
    			 if (!num.equals(user_id_str)) {
    				 newArray.add(num);
    				 break;
    				 
    			 }
    		}
    		
    		String[] updatedUsernumArray = newArray.toArray(new String[0]);
        	String updatedUser_num = String.join(",", updatedUsernumArray);
        	
        	lectureService.lectureReservationSetUser(updatedUser_num, lecture.getLecture_num());
    	}
    	
    	lectureOrderService.CancelLectureByUser_id(user_id, lecture.getLecture_num());
    	userService.getUpdateToken(username, myToken + lecture.getPrice());
		
		return "redirect:/lectureOrderList";
	}
	
	@GetMapping("/writeReview")
	public String writeReview(@RequestParam("lector_reviewWriteCheckSign")String lector_reviewWriteCheckSign,
								Lecture lecture, Model model) {
		
		List<Lecture> lectureList = lectureService.getFindLectureListByLecture_num(lecture.getLecture_num());
		
		model.addAttribute("lectureList", lectureList);
		model.addAttribute("lector_reviewWriteCheckSign", lector_reviewWriteCheckSign);
		
		return "/user/writeReview";
	}
	
	
	@PostMapping("/writeReviewProc")
	public String writeReviewProc(Review review) {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);
    	
		String lecture_reviewWriteCheck = lectureService.findReviewWriteCheckByLecture_num(review.getLecture_num());
    	
    	// lecture
		String user_nums = "";
		double all_reviewScore = 0;
		double review_score = 0;
		
		// lecture Score
		if(lecture_reviewWriteCheck == null) { // 최초 리뷰 작성시, 
			
			all_reviewScore = review.getLecture_review_score();
			review_score = review.getLecture_review_score();
			user_nums = Integer.toString(user_id);
			
		} else { // ,를 추가해서 다시 저장
			
			String[] userNumArray = lecture_reviewWriteCheck.split(",");
			int writeReviewUsers = userNumArray.length + 1; // 기존에 리뷰를 작성한 유저들 수

			all_reviewScore = 
					lectureService.findAll_review_scoreByLecture_num(review.getLecture_num()) + review.getLecture_review_score();
			
			review_score = all_reviewScore / writeReviewUsers;
			
			user_nums = lecture_reviewWriteCheck + "," + Integer.toString(user_id);
			
		}
		
		// 합계 나누기용 all_review_score 저장
		lectureService.all_review_scoreUpdateByLecture_num(all_reviewScore, review.getLecture_num());
		
		// lecture에 review_score를 등록
		lectureService.updateReview_scoreByLecture_num(review_score, review.getLecture_num());
				
		// lecture에 reviewWriteCheck에 등록
		lectureService.updateReviewWriteCheckByUser_id(user_nums, review.getLecture_num());
		
		// 강사 리뷰 신규 등록
		if(review.getLector_review_score() != 0) { 
			
			// lector Score
			String lector_reviewWriteCheck = lectorsService.findRevieWriteCheckByLector_num(review.getLector_num());
			
			// lector
			String lector_user_nums = "";
			double lector_all_reviewScore = 0;
			double lector_review_score = 0;
			
			if(lector_reviewWriteCheck == null) {
				
				lector_all_reviewScore = review.getLector_review_score();
				lector_review_score = review.getLector_review_score();
				lector_user_nums = Integer.toString(user_id);
				
			} else {
				
				String[] lector_userNumArray = lector_reviewWriteCheck.split(",");
				
				// 중복 등록을 방지
				for(String userNums : lector_userNumArray) {
					
					if(userNums.equals(Integer.toString(user_id))) {
						lector_user_nums = lector_reviewWriteCheck;
						break;
					}
				}
				
 				double lector_writeReviewUsers = lector_userNumArray.length + 1; // 기존에 리뷰룰 작성한 유저들 + 본인
 				lector_all_reviewScore = 
						lectorsService.findAll_review_scoreByLector_num(review.getLector_num()) + review.getLector_review_score();
				
 				lector_review_score = lector_all_reviewScore  / lector_writeReviewUsers;
 				
				lector_user_nums = lector_reviewWriteCheck + "," + Integer.toString(user_id);
			}
			
			// 합계 나누기용 all_review_score 저장
			lectorsService.all_review_scoreUpdateByLector_num(lector_all_reviewScore, review.getLector_num());
			
			// lectors에 review_score를 등록
			lectorsService.updateReview_scoreByLector_num(lector_review_score, review.getLector_num());
			
			// lectors에 reviewWriteCheck에 등록
			lectorsService.updateReviewWriteCheckByUser_id(lector_user_nums, review.getLector_num());
		
		} else { // 기존에 강사에 대한 리뷰를 남겼을 경우,
			
			double beforeLector_review_score = 
					reviewService.findLector_review_scoreByLector_numAndUser_id(review.getLector_num(), user_id);
			
			// 기존에 등록했던 점수 등록
			review.setLector_review_score(beforeLector_review_score);
			
		}
		
		review.setUser_id(user_id);
		// 리뷰 저장 
		reviewService.getSave(review); 
		
		return "redirect:/lectureOrderList";
	}
	
	
	@GetMapping("/endLectureViewByLector_num")
	public String endLectureViewByLector_num(Lecture lecture, Pageable pageable, Model model) {
		// lector_num
		Page<Lecture> endLecturePageList =  lectureService.findEndLectureByLector_num(pageable, lecture.getLector_num());
		
		int nowPage = endLecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, endLecturePageList.getTotalPages());
    	
		model.addAttribute("lector_num", lecture.getLector_num());
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("endLecturePageList", endLecturePageList);
		
		return "/user/endLectureListView";
	}
	
///// 리뷰 작성된 내용 출력	
	@GetMapping("/endLectureDetail")
	public String endLectureDetail(Lecture lecture, Model model) {
		
		List<Lecture> endLectureList = lectureService.getFindLectureListByLecture_num(lecture.getLecture_num());
		List<Review> reviewList = reviewService.findReviewByLecture_num(lecture.getLecture_num());
		double review_score = lectorsService.findReview_scoreByLector_num(lecture.getLector_num());
		
		String nullCheckMessage = "X";
		
		if(reviewList.isEmpty()) {
			nullCheckMessage = "O";
		}
		
		model.addAttribute("nullCheckMessage", nullCheckMessage);
		
		model.addAttribute("endLectureList", endLectureList);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("review_score", review_score);
		
		return "/user/endLectureDetail";
	}
	
	
	@GetMapping("/myReviewList")
	public String writeReviewList(Pageable pageable, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);

    	Page<Review> myReviewList = reviewService.findReviewListByUser_id(pageable, user_id);
    	
    	int nowPage = myReviewList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, myReviewList.getTotalPages());
    	
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("myReviewList", myReviewList);

		return "/user/myReviewList";
	}
	
	
	@GetMapping("/myReviewDetail")
	public String myReviewDetail(Review review, Model model) {
		
		// lecture, lector num 받아옴
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	
    	int user_id = userService.getFindIdByUsername(username);
		
		List<Lecture> endLectureList = lectureService.getFindLectureListByLecture_num(review.getLecture_num());
		
		List<Review> myReviewList = reviewService.findMyReviewByLecture_numAndUser_id(review.getLecture_num(), user_id);
		double review_score = lectorsService.findReview_scoreByLector_num(review.getLector_num());
		
		model.addAttribute("endLectureList", endLectureList);
		
		model.addAttribute("myReviewList", myReviewList);
		model.addAttribute("review_score", review_score);
		
		return "/user/myReviewDetail";
	}
	
	@GetMapping("/review_contentUpdate")
	public String review_contentUpdate(Review review, Model model) {
		
		model.addAttribute("review_content", review.getReview_content());
		
		return "/user/review_contentUpdate";
	}
	
	@GetMapping("/lectureScoreUpdate")
	public String lectureScoreUpdate(Review review, Model model) {
		
		model.addAttribute("lecture_review_score", review.getLecture_review_score());
		
		return "/user/lectureScoreUpdate";
	}
	
	@GetMapping("/lectorScoreUpdate")
	public String lectorScoreUpdate(Review review, Model model) {
		
		model.addAttribute("lector_review_score", review.getLector_review_score());
		
		return "/user/lectorScoreUpdate";
	}
	
	@PostMapping("/myReviewUpdateProc")
	public String myReviewUpdateProc(Review review) {
		
		// review_num으로 이전 데이터를 가져와서 수정된 값이 있는지 비교
		Review old_review = reviewService.findReviewByReivew_num(review.getReivew_num());
		
		// 강의 점수를 수정했을 경우,
		if(old_review.getLecture_review_score() != review.getLecture_review_score()) {
			
			// lecture의 all_review_score에서  기존 점수를 빼고 새로 입력한 점수를 저장.
			double old_all_review_score = lectureService.findAll_review_scoreByLecture_num(review.getLecture_num());
			
			double new_all_review_score = old_all_review_score - old_review.getLecture_review_score() 
															+ review.getLecture_review_score();
			lectureService.all_review_scoreUpdateByLecture_num(new_all_review_score, review.getLecture_num());
			
			// 리뷰 업데이트 위한 객체 저장
			old_review.setLecture_review_score(review.getLecture_review_score());
		}
		
		// 강사 점수를 수정했을 경우,
		if(old_review.getLector_review_score() != review.getLector_review_score()) {
			
			double old_all_review_score = lectorsService.findAll_review_scoreByLector_num(review.getLector_num());
			
			double new_all_review_score = old_all_review_score - old_review.getLector_review_score()
															+ review.getLector_review_score();
			lectorsService.all_review_scoreUpdateByLector_num(new_all_review_score, review.getLector_num());
			
			old_review.setLector_review_score(review.getLector_review_score());
		}
		
		old_review.setReview_content(review.getReview_content());
		
		reviewService.getSave(old_review);
		
		return "redirect:/myReviewList";
	}
	
	
	
	
	
	
}
