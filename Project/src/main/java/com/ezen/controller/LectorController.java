package com.ezen.controller;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.Service.ExchangeOrderService;
import com.ezen.Service.LectorsService;
import com.ezen.Service.LectureOrderService;
import com.ezen.Service.LectureService;
import com.ezen.Service.MembershipService;
import com.ezen.Service.PracticeRoomRentalOrderService;
import com.ezen.Service.PracticeRoomRentalService;
import com.ezen.Service.ReviewService;
import com.ezen.Service.UserReviewService;
import com.ezen.Service.UserService;
import com.ezen.model.ExchangeOrder;
import com.ezen.model.Lectors;
import com.ezen.model.Lecture;
import com.ezen.model.Membership;
import com.ezen.model.MembershipDTO;
import com.ezen.model.PracticeRoomRental;
import com.ezen.model.PracticeRoomRentalOrder;
import com.ezen.model.Review;
import com.ezen.model.UserReview;
import com.ezen.model.Users;
@Controller
public class LectorController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	LectorsService lectorsService;
	
	@Autowired
	MembershipService membershipService;
	
	@Autowired
	PracticeRoomRentalService practiceRoomRentalService;
	
	@Autowired
	PracticeRoomRentalOrderService practiceRoomRentalOrderService;
	
	@Autowired
	LectureService lectureService;
	
	@Autowired
	LectureOrderService lectureOrderService;
	
	@Autowired
	UserReviewService userReviewService;
	
	@Autowired
	ExchangeOrderService exchangeOrderService;
	
	@Autowired
	ReviewService reviewService;
	
	
	
	@GetMapping("/upgradeLector")
	public String upgradeLector() {
		return "/lector/joinLector";
	}
	
	
	@PostMapping("/joinLectorProc")
	public String joinLectorProc(@RequestParam(value="paymentMethod", defaultValue="")String paymentMethod,
									Lectors lectors, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username);
        lectors.setUser_id(user_id);
        
        if(lectors.getMembership().equals("O")) { // 가입할 때 멤버쉽을 가입했을 경우,
        	
        	Membership membership = new Membership();
            
            membership.setUser_id(user_id);
        	membership.setMembership_Type(lectors.getMembership_Type());
        	membership.setPaymentMethod(paymentMethod);
        	
        	membershipService.insertMembership(membership);
        	
        	lectors.setMembershipJoinDate();
        	
        	if(lectors.getMembership_Type().equals("1month")) {
        		lectors.setMembershipEndDate(1);
        		
        	} else if(lectors.getMembership_Type().equals("3month")) {
        		lectors.setMembershipEndDate(3);
        		
        	} else if(lectors.getMembership_Type().equals("6month")) {
        		lectors.setMembershipEndDate(6);
        		
        	} else {
        		lectors.setMembershipEndDate(12);
        	}
        }
       
        lectorsService.getSave(lectors);
        
		return "/user/myPage";
	}
	
	@GetMapping("/lectorPage")
	public String lectorPage() {
		return "/lector/lectorPage";
	}
	
	@GetMapping("/lectorMypage")
	public String lectorMypage(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        List<Lectors> lectorList = lectorsService.getLectorList(lector_num); 
        
        model.addAttribute("lectorList", lectorList);
		
		return "/lector/lectorMypage";
	}
	
	@GetMapping("/updateCareer")
	public String updateCareer(@RequestParam("career")String career, 
								Model model) {
		model.addAttribute("career", career);
        
		return "/lector/updateCareer";
	}
	
	@PostMapping("/updateCareerProc")
	public ResponseEntity<Void> updateCareerProc(@RequestParam("careerValue")String careerValue) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        lectorsService.updateCareerByLector_num(careerValue, lector_num);
        
        return ResponseEntity.ok().build();
	}
	
	@GetMapping("insertLecture_room_address")
	public String updateLecture_room_address() {
		return "/lector/insertLecture_room_address";
	}
	
	@GetMapping("/updateLecture_room_address")
	public String updateLecture_room_address(@RequestParam("lecture_room_address")String lecture_room_address_value,
												Model model) {
		model.addAttribute("lecture_room_address_value", lecture_room_address_value);
		
		return "/lector/updateLecture_room_address";
	}
	
	@PostMapping("/insertLecture_room_address_Proc")
	public ResponseEntity<Void> insertLecture_room_address_Proc(@RequestParam("lecture_room_address_value")String lecture_room_address_value) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        lectorsService.updateLecture_room_addressByLector_num(lecture_room_address_value, lector_num);
        
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping("/updatePrefer_personnel")
	public String updatePrefer_personnel() {
		return "/lector/updatePrefer_personnel";
	}
	
	@PostMapping("/updatePrefer_personnelProc")
 	public ResponseEntity<Void> updatePrefer_personnelProc(@RequestParam("prefer_personnel_value")String prefer_personnel_value) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        lectorsService.updatePrefer_personnelByLector_num(prefer_personnel_value, lector_num);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/membership_join")
	public String membership_join() {
		return "/lector/membership_join";
	}
	
	@PostMapping("/membership_joinProc")
	public ResponseEntity<Void> membership_joinProc(Lectors lectors, Membership membership, MembershipDTO dto) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        lectors.setLector_num(lector_num);
		lectors.setMembership_Type(dto.getMembership_Type());
		lectors.setMembershipJoinDate();
        
    	if(dto.getMembership_Type().equals("1month")) {
    		lectors.setMembershipEndDate(1);
    		
    	} else if(dto.getMembership_Type().equals("3month")) {
    		lectors.setMembershipEndDate(3);
    		
    	} else if(dto.getMembership_Type().equals("6month")) {
    		lectors.setMembershipEndDate(6);
    		
    	} else {
    		lectors.setMembershipEndDate(12);
    	}
        
    	membership.setUser_id(user_id);
    	membership.setMembership_Type(dto.getMembership_Type());
    	membership.setPaymentMethod(dto.getPaymentMethod());
    	
    	
    	membershipService.insertMembership(membership);
        lectorsService.membershipJoin(lectors);
		
        // 멤버쉽 가입 전에 등록했던 글에 대해서 멤버쉽 등록해주는 기능
        lectureService.membershipUpdate(lector_num);
        
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/renewalMembership")
	public String renewalMembership(@RequestParam("membershipEndDate")String membershipEndDate,
										Model model) {
		model.addAttribute("membershipEndDate", membershipEndDate);
		
		return "/lector/renewalMembership";
	}
	
	@PostMapping("/renewalMembershipProc")
	public ResponseEntity<Void> renewalMembershipProc(Lectors lectors, Membership membership, MembershipDTO dto) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        Timestamp membershipEndDate = lectorsService.getFindMembershipEndDateByLector_num(lector_num);
        
        lectors.setLector_num(lector_num);
        lectors.setMembership_Type(dto.getMembership_Type());
        
    	if(dto.getMembership_Type().equals("1month")) {
    		lectors.renewalMembershipEndDate(1, membershipEndDate);
    		
    	} else if(dto.getMembership_Type().equals("3month")) {
    		lectors.renewalMembershipEndDate(3, membershipEndDate);
    		
    	} else if(dto.getMembership_Type().equals("6month")) {
    		lectors.renewalMembershipEndDate(6, membershipEndDate);
    		
    	} else {
    		lectors.renewalMembershipEndDate(12, membershipEndDate);
    	}
		
    	membership.setUser_id(user_id);
    	membership.setMembership_Type(dto.getMembership_Type());
    	membership.setPaymentMethod(dto.getPaymentMethod());
    	
    	membershipService.insertMembership(membership);
        lectorsService.renewalMembership(lectors);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/courseLecture")
	public String courseLecture(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        List<Lectors> lectorList = lectorsService.getLectorList(lector_num);
        
        model.addAttribute("lectorList", lectorList);
        
		return "/lector/courseLecture";
	}
	
	@GetMapping("/otherPlaceInput")
	public String otherPlaceInput() {
		return "/lector/otherPlaceInput";
	}
	
	@GetMapping("/otherPlace")
	public String otherPlace(Pageable pageable, Model model) {
		
		Page<PracticeRoomRental> practiceRoomPageList =  practiceRoomRentalService.findAllPracticeRoom(pageable);

		int nowPage = practiceRoomPageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, practiceRoomPageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("practiceRoomPageList", practiceRoomPageList);
		
		return "/lector/otherPlace";
	}
	
	@GetMapping("/searchPracticeRoom")
	public String searchPracticeRoom(Pageable pageable, Model model,
									 @RequestParam("keyword")String keyword) {
		Page<PracticeRoomRental> practiceRoomPageList =  practiceRoomRentalService.findPracticeRoomByKeword(pageable, keyword);
		
		int nowPage = practiceRoomPageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, practiceRoomPageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("practiceRoomPageList", practiceRoomPageList);
		model.addAttribute("searchKeyword", keyword); // 검색 키워드를 모델에 추가
		
		return "/lector/otherPlace";
	}
	
	@GetMapping("/setPracticeRoom_address") // 연습실을 렌탈할 경우 처리
	public String setPracticeRoom_address(PracticeRoomRental practiceRoomRental,
											Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
		List<PracticeRoomRental> practiceRoomList = practiceRoomRentalService.findPracticeRoomListByPracticeRoom_id(practiceRoomRental.getPracticeRoom_id());
		double token = userService.getFindTokenByUsername(username);
		
		model.addAttribute("practiceRoomList", practiceRoomList);
		model.addAttribute("token", token);
		
		return "/lector/practiceRoomRentalOrder";
	}
	
	@PostMapping("/practiceRoomRentalOrderProc")
	public String practiceRoomRentalOrderProc(PracticeRoomRentalOrder prro, Model model) {
		
		String practiceRoom_address = practiceRoomRentalService.findPracticeRoom_addressByPracticeRoom_id(prro.getPracticeRoom_id());
		int practiceRoom_id = prro.getPracticeRoom_id();
		String rentalStartTime = prro.getRentalStartTime();
		LocalDate rentalDate = prro.getRentalDate();
		
		model.addAttribute("practiceRoom_address", practiceRoom_address);
		model.addAttribute("practiceRoom_id", practiceRoom_id);
		model.addAttribute("rentalStartTime", rentalStartTime);
		model.addAttribute("rentalDate", rentalDate);
		
		return "/lector/otherPlaceChoose";
	}
	
	
	@PostMapping("/courseLectureProc")
	public String courseLectureProc(@RequestParam(value="rentalCheck", defaultValue = "")String rentalCheck,
									@RequestParam(value="practiceRoom_id", defaultValue = "")String practiceRoom_id,
									@RequestParam(value="rentalStartTime", defaultValue = "")String rentalStartTime, 
									Lecture lecture) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        int checkNum = lectorsService.checkMembershipByLector_num(lector_num);
        
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
            prro.setLector_num(lector_num);
            prro.setRentalDate(lecture.getLecture_date().toString());
            prro.setRentalStartTime(rentalStartTime);
            prro.setRentalEndTime(rentalEndTime);
            
            practiceRoomRentalOrderService.getSave(prro);
            int practiceRoomOrder_id = 
            		practiceRoomRentalOrderService
            		.findPracticeRoomOrder_idByLector_numAndRentalDateAndRentalStartTime(lector_num, lecture.getLecture_date(), rentalStartTime);
            
            // lecture의 rentalCheck에  렌탈 오더 주문 번호 저장해줄 생각임
            String String_practiceRoomOrder_id = String.valueOf(practiceRoomOrder_id);
            
            lecture.setRentalCheck(String_practiceRoomOrder_id); // 연습실 렌탈 여부 확인을 위해
		} 
        lecture.setLector_num(lector_num);
 	    
 	    
        if(checkNum > 0) { // 멤버쉽 가입자일 경우,
     	    lecture.setMembership("O");
     	    
        } else {
     	    lecture.setMembership("X");
        }
		
        lectureService.getSave(lecture);
 	
		return "/lector/lectorPage";
	}
	
	@GetMapping("/lectureManagement")
	public String lectureManagement(Pageable pageable, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        Page<Lecture> lecturePageList = lectureService.findAllLectureListByLector_num(pageable, lector_num);
        
        int nowPage = lecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, lecturePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
        model.addAttribute("lecturePageList", lecturePageList);
		
		return "/lector/lectureManagement";
	}
	
	@GetMapping("/notEndLectureListView")
	public String notEndLectureListView(Pageable pageable, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        Page<Lecture> lecturePageList = lectureService.findNotEndLectureListByLector_num(pageable, lector_num);
        
        int nowPage = lecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, lecturePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
        model.addAttribute("lecturePageList", lecturePageList);
		
		return "/lector/lectureManagement";
	}
	
	@GetMapping("/endLectureListView")
	public String endLectureListView(Pageable pageable, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        Page<Lecture> lecturePageList = lectureService.findEndLectureByLector_num(pageable, lector_num);
        
        int nowPage = lecturePageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, lecturePageList.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
        model.addAttribute("lecturePageList", lecturePageList);
		
		return "/lector/endLectureListView";
	}
	
	
/////////////////////////////////////////////////////////////////	
	@GetMapping("/lectureManagementDetail")
	public String lectureManagementDetail(Lecture lecture, Model model) {
		
		List<Lecture> lectureDetailList = lectureService.getFindLectureListByLecture_num(lecture.getLecture_num());
		
		
		System.out.println("              asdasdasdasdasdasdasdads                 " + lectureDetailList.get(0).toString());
		
		
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
		
		return "/lector/lectureManagementDetail";
	}
	
	@GetMapping("/myRentalList")
	public String myRentalList(Pageable pageable, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
		
		Page<PracticeRoomRentalOrder> practiceRoomOrderList = 
			practiceRoomRentalOrderService.myRentalListByLector_num(pageable, lector_num);
		
		int nowPage = practiceRoomOrderList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, practiceRoomOrderList.getTotalPages());
			
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("practiceRoomOrderList", practiceRoomOrderList);
		
		return "/lector/myRentalList";
	}
	
	@GetMapping("/rentalPracticeRoomDetail")
	public String rentalPracticeRoomDetail(PracticeRoomRentalOrder prro,
											Model model) {
		
		List<PracticeRoomRentalOrder> practiceRoomOrderList = 
				practiceRoomRentalOrderService.findPracticeRoomRentalListByPracticeRoomOrder_id(prro.getPracticeRoomOrder_id());
		
		List<PracticeRoomRental> practiceRoomDetail = 
				practiceRoomRentalService.findPracticeRoomListByPracticeRoom_id(prro.getPracticeRoom_id());
		
		model.addAttribute("practiceRoomOrderList", practiceRoomOrderList);
		model.addAttribute("practiceRoomDetail", practiceRoomDetail);
		
		return "/lector/myRentalListDetail";
	}
	
	@GetMapping("/cancelRentalPracticeRoom")
	public String cancelRentalPracticeRoom(PracticeRoomRental prr,
											PracticeRoomRentalOrder prro) {
		
		// prr price, prro practiceRoomOrder_id
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		
        double myToken = userService.getFindTokenByUsername(username);
		
        practiceRoomRentalOrderService.updateCancel_checkByPracticeRoomOrder_id(prro.getPracticeRoomOrder_id());
        userService.getUpdateToken(username, myToken + prr.getPracticeRoom_price());
        
        String String_practiceRoomOrder_id = String.valueOf(prro.getPracticeRoomOrder_id());
        int lecture_num = lectureService.findLecture_numByRentalCheck(String_practiceRoomOrder_id);
        
        lectureService.rentalCheckUpdateXByLecture_num(lecture_num); // lecture의 rentalCheck을 X로 변경
		lectureService.updateLecture_placeByLecture_num(lecture_num);
        
		return "/lector/lectorPage";
	}
	
	@Transactional
	@PostMapping("/lectureUpdateProc")
	public String lectureUpdateProc(@RequestParam(value="rentalCheck", defaultValue = "")String rentalCheck,
									@RequestParam(value="practiceRoom_id", defaultValue = "")String practiceRoom_id,
									@RequestParam(value="rentalStartTime", defaultValue = "")String rentalStartTime, 
									Lecture lecture) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
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
            prro.setLector_num(lector_num);
            prro.setRentalDate(lecture.getLecture_date().toString());
            prro.setRentalStartTime(rentalStartTime);
            prro.setRentalEndTime(rentalEndTime);
            
            practiceRoomRentalOrderService.getSave(prro);
            int practiceRoomOrder_id = 
            		practiceRoomRentalOrderService
            		.findPracticeRoomOrder_idByLector_numAndRentalDateAndRentalStartTime(lector_num, lecture.getLecture_date(), rentalStartTime);
            
            // lecture의 rentalCheck에  렌탈 오더 주문 번호 저장해줄 생각임
            String String_practiceRoomOrder_id = String.valueOf(practiceRoomOrder_id);
            
            lecture.setRentalCheck(String_practiceRoomOrder_id); // 연습실 렌탈 여부 확인을 위해
		}
        
        // JPA에서 단일 필드 수정의 경우, 알아서 DB에 저장까지 해주지만, 다중 필드를 수정할 경우, 별도로  save처리를 해줘야한단다..
        // 그래서 save로 처리한다.
        lectureService.getSave(lecture);
		
		return "redirect:/lectureManagement";
	}
	
	@GetMapping("/lectureDelete")
	public String lectureDelete(Lecture lecture) {
		
		lectureService.deleteById(lecture.getLecture_num());
		
		return "redirect:/lectureManagement";
	}
	
	@GetMapping("/userBan") 
	public String userBan(Users users, Lecture lecture, Model model) {
		
		List<Users> userDetailList = userService.getUserlist(users.getUsername());
		
		model.addAttribute("userDetailList", userDetailList);
		model.addAttribute("lecture_num", lecture.getLecture_num());
		
		return "/lector/userBan";
	}
	
	@GetMapping("/userBanProc")
	public String userBanProc(Users users, Lecture lecture, Model model) {
		
			int user_id = userService.getFindIdByUsername(users.getUsername());
			String String_user_id = String.valueOf(user_id);
		
			// 토큰 환불
			double price = lectureService.findPriceByLecture_num(lecture.getLecture_num());
			double user_token = userService.getFindTokenByUsername(users.getUsername());
			
		userService.getUpdateToken(users.getUsername(), user_token + price);
			
			// maxPersonnelCheckNum -1 해주기
			int old_maxPersonnelCheckNum = lectureService.findMaxPersonnelCheckNumByLecture_num(lecture.getLecture_num());
			
		lectureService.maxPersonnelCheckNumUpdateByLecture_num(old_maxPersonnelCheckNum - 1, lecture.getLecture_num());
		
			String old_user_num = lectureService.findUser_numByLecture_num(lecture.getLecture_num());
			
				String[] user_numArray = old_user_num.split(",");
				
					ArrayList<String> newUserNumArray = new ArrayList<String>();
					
					for(String str : user_numArray) {
						
						if(!str.equals(String_user_id)) {
							newUserNumArray.add(str);
						}
					}
					
						String[] new_user_numArray = newUserNumArray.toArray(new String[0]);
						String new_user_num = String.join(",", new_user_numArray);
		
		lectureService.lectureReservationSetUser(new_user_num, lecture.getLecture_num());
		
		// 강의 주문 취소
		lectureOrderService.CancelLectureByUser_id(user_id, lecture.getLecture_num());
		
		String message = "userBan";
		model.addAttribute("message", message);
		
		return "/lector/closePopup";
	}
	
	@GetMapping("/userReviewWrite")
	public String userReviewWrite(Users users, Lecture lecture, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUsername = authentication.getName();
        int myId = userService.getFindIdByUsername(myUsername); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(myId); // 유저 번호로 강사 번호 불러오기
		
		
		String reviewWriteCheckSign = "";
		
		// 이미 작성한 유저인지 체크
		String reviewWriteCheck = userService.findReviewWriteCheckByUsername(users.getUsername());
		
		String String_lector_num = String.valueOf(lector_num);
		
			if(reviewWriteCheck == null) {
				reviewWriteCheckSign = "X";
				
			} else {
				
				String[] revieWriteCheckArray = reviewWriteCheck.split(",");
				
				for (String lectorNums : revieWriteCheckArray) {
					
					if(lectorNums.equals(String_lector_num)) {
						reviewWriteCheckSign = "O";
						break;
					
					} else {
						reviewWriteCheckSign = "X";
					}
				}
			}
		
		model.addAttribute("reviewWriteCheckSign", reviewWriteCheckSign);
		model.addAttribute("username", users.getUsername());
		model.addAttribute("lecture_num", lecture.getLecture_num());
		
		return "/lector/userReviewWrite";
	}
	
	@PostMapping("/userReviewWriteProc")
	public String userReviewWriteProc(UserReview userReview, Users users, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUsername = authentication.getName();
        int myId = userService.getFindIdByUsername(myUsername); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(myId); // 유저 번호로 강사 번호 불러오기
		
		int user_id = userService.getFindIdByUsername(users.getUsername());
		Users newUsers = userService.findUsersByUser_id(user_id);
		
			String new_review_write_check = "";
			double new_all_review_score = 0;
			double new_manner_score = 0;
			
			// users.review_write_check 최신화
			String old_reviewWriteCheck = userService.findReviewWriteCheckByUsername(users.getUsername());
			
				if(old_reviewWriteCheck == null) { // 최초 입력일 경우,
					new_review_write_check = String.valueOf(lector_num);
					
				} else { // 기존에 입력한 값이 있을 경우,
					new_review_write_check = old_reviewWriteCheck + "," + String.valueOf(lector_num);
				}
				
			double old_all_review_score = userService.findAll_review_scoreByUser_id(user_id);
			
				if(old_all_review_score == 0) {
					new_all_review_score = userReview.getUser_review_score();
					new_manner_score = userReview.getUser_review_score();
					
				} else {
					
					String[] old_reviewWriteCheckArray = old_reviewWriteCheck.split(",");
					double old_reviewWriterlength = old_reviewWriteCheckArray.length + 1;
					
					new_all_review_score = old_all_review_score + userReview.getUser_review_score();
					
					new_manner_score = new_all_review_score / old_reviewWriterlength;
				}
		
		newUsers.setReviewWriteCheck(new_review_write_check);
		newUsers.setAll_review_score(new_all_review_score);
		newUsers.setManner_score(new_manner_score);
		
		userReview.setUser_id(user_id);
		userReview.setLector_num(lector_num);
		
		userService.getSave(newUsers);
		userReviewService.getSave(userReview); // 레포지토리에 만들어야함
		
		
		String message = "userReview";
		model.addAttribute("message", message);
		
		return "/lector/closePopup";
	}
	
	
	@GetMapping("/reviewManagement")
	public String reviewManagement(Pageable pageable, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUsername = authentication.getName();
        int myId = userService.getFindIdByUsername(myUsername); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(myId); // 유저 번호로 강사 번호 불러오기
        
        Page<UserReview> writeReviewPageList =  userReviewService.getWriteReviewPageList(pageable, lector_num);
        
        int nowPage = writeReviewPageList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, writeReviewPageList.getTotalPages());
			
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("writeReviewPageList", writeReviewPageList);
		
		return "/lector/writeReviewView";
	}
	
	@GetMapping("/writeReviewDetail")
	public String writeReviewDetail(UserReview userReview, Model model) {
		// user_id=6, lecture_num=27
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUsername = authentication.getName();
        int myId = userService.getFindIdByUsername(myUsername); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(myId); // 유저 번호로 강사 번호 불러오기
		
		List<Lecture> lectureList = lectureService.getFindLectureListByLecture_num(userReview.getLecture_num());
		List<UserReview> reviewList = userReviewService.findUserReviewByLector_numAndUser_id(lector_num, userReview.getUser_id());
		
		String username = userService.findUsernameById(reviewList.get(0).getUser_id());
		
		model.addAttribute("username", username);
		model.addAttribute("lectureList", lectureList);
		model.addAttribute("reviewList", reviewList);
		
		return "/lector/writeReviewDetail";
	}
	
	@GetMapping("/userReview_contentUpdate")
	public String review_contentUpdate(UserReview userReview, Model model) {
		
		model.addAttribute("userReivew_content", userReview.getUserReivew_content());
		
		return "/lector/userReview_contentUpdate";
	}
	
	@GetMapping("/user_review_scoreUpdate")
	public String user_review_scoreUpdate(UserReview userReview, Model model) {
		
		model.addAttribute("user_review_score", userReview.getUser_review_score());
		
		return "/lector/user_review_scoreUpdate";
	}
	
	@PostMapping("/writeReviewUpdateProc")
	public String writeReviewUpdateProc(UserReview userReview) {
		
		String username = userService.findUsernameById(userReview.getUser_id());
		
		// 기존에 저장되어 있던 값 불러오기
		UserReview old_review = userReviewService.findUserReviewByUserReivew_num(userReview.getUserReivew_num());
		
		// 수정된 내용이 있는지 체크
		if(old_review.getUser_review_score() != userReview.getUser_review_score()) {
				
				double old_all_review_score = userService.findAll_review_scoreByUser_id(userReview.getUser_id());
				double new_all_review_score = old_all_review_score - old_review.getUser_review_score()
																+ userReview.getUser_review_score();
			
			userService.updateAll_review_scoreByUser_id(new_all_review_score, userReview.getUser_id());
			
				// manner_score 업데이트
				String old_reviewWriteCheck = userService.findReviewWriteCheckByUsername(username);
				String [] lector_numArray = old_reviewWriteCheck.split(",");
				
				double newManner_score = new_all_review_score / lector_numArray.length;
			
			// manner_score 저장
			userService.updateManner_scoreByUser_id(newManner_score, userReview.getUser_id());
			
			old_review.setUser_review_score(userReview.getUser_review_score());
			
		}
		
		old_review.setUserReivew_content(userReview.getUserReivew_content());
		// 업데이트 가능 횟수 ++
		old_review.setUpdate_times(userReview.getUpdate_times() + 1);
		
		// UserReview 업데이트
		userReviewService.getSave(old_review);
		
		return "redirect:/reviewManagement";
	}
	
	@GetMapping("/userReviewDelete")
	public String reviewDelete(@RequestParam("beforeDeleteCheckDate")String beforeDeleteCheckDate,
								UserReview userReview) {
		
		//userReview_num
		UserReview old_review = userReviewService.findUserReviewByUserReivew_num(userReview.getUserReivew_num());
		
		String username = userService.findUsernameById(old_review.getUser_id());
		
		// 리뷰 작성 이후 일주일 경과 이전, 
		if(beforeDeleteCheckDate.equals("X")) {
			
				double old_all_review_score = userService.findAll_review_scoreByUser_id(old_review.getUser_id());
				double new_all_review_score = old_all_review_score - old_review.getUser_review_score();
				
				System.out.println("            new_all_review_score                   " + new_all_review_score);
			
			// all_review_score 저장
			userService.updateAll_review_scoreByUser_id(new_all_review_score, old_review.getUser_id());
			
				// review_score 업데이트
				String lector_nums = userService.findReviewWriteCheckByUsername(username);
				String[] lector_numsArray = lector_nums.split(",");
					
					ArrayList<String> newArray = new ArrayList<String>();
					
					String String_lector_num = Integer.toString(old_review.getLector_num());
					
						for(String str : lector_numsArray) {
							
							if(!str.equals(String_lector_num)) {
								newArray.add(str);
							}
						}
							
							String[] newLector_numArray = newArray.toArray(new String[0]);			
							String newReviewWriteCheck = String.join(",", newLector_numArray);
							
							System.out.println("            newReviewWriteCheck                   " + newReviewWriteCheck);
			
			// reviewWriteCheck 업데이트
			userService.updateReviewWriteCheckByUser_id(newReviewWriteCheck, old_review.getUser_id());
			
				// 리뷰 남긴 사람이 나 혼자일 경우, 최소값이 1이 되게 설정.
				double maxCheck = Math.max(lector_numsArray.length - 1, 1);
				double new_manner_score = new_all_review_score / maxCheck;		
				
				System.out.println("            new_manner_score                   " + new_manner_score);
			
			// manner_score 업데이트
			userService.updateManner_scoreByUser_id(new_manner_score, old_review.getUser_id());				
							
			userReviewService.hide_reviewByUserReivew_num(userReview.getUserReivew_num());		
			
		// beforeDeleteCheckDate == O이면, 리뷰에 반영 X
		} else {
			// 숨김 처리
			userReviewService.hide_reviewByUserReivew_num(userReview.getUserReivew_num());
		}
		
		return "redirect:/reviewManagement";
		
	}
	
	@GetMapping("/getToken")
	public String getToken(@RequestParam("lecture_fee")String lecture_fee,
							Lecture lecture) {
		
		System.out.println("            asdasdasdasads                    " + lecture.toString());
		
		int user_id = lectorsService.findUser_idByLector_num(lecture.getLector_num());
		String username = userService.findUsernameById(user_id);
		
		double old_token = userService.getFindTokenByUsername(username);
		double lectureFee = Double.parseDouble(lecture_fee);
		
		userService.getUpdateToken(username, old_token + lectureFee);
		
		// getToken을 "O"로 변경
		lectureService.updateGetTokenCheckByLecture_num(lecture.getLecture_num());
		
		return "redirect:/lectureManagement";
	}
	
	@GetMapping("/exchangeToken")
	public String exchangeToken(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
        
        int myToken = (int) userService.getFindTokenByUsername(username);
		int checkMembership = lectorsService.checkMembershipByLector_num(lector_num);
        
			String account = exchangeOrderService.findAccountByLector_num(lector_num);
			
				String accountMessage = "";
				
					if(account == null) {
						accountMessage = "X";
						
					} else {
						accountMessage = account;
					}
					
			String bank = exchangeOrderService.findBankByLector_num(lector_num);
				
				String bankMessage = "";
				
				if(bank == null) {
					bankMessage = "X";
					
				} else {
					bankMessage = bank;
				}
			
		System.out.println("         accountMessage                    " + accountMessage);
		System.out.println("         bankMessage                    " + bankMessage);
		
		model.addAttribute("myToken", myToken);
        model.addAttribute("checkMembership", checkMembership);
        
        model.addAttribute("accountMessage", accountMessage);
        model.addAttribute("bankMessage", bankMessage);
		
		return "/lector/exchangeToken";
	}
	
	@PostMapping("/exchangeTokenProc")
	public String exchangeTokenProc(ExchangeOrder eo) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
		
        double old_token = userService.getFindTokenByUsername(username);
        
        // 토큰 갯수 업데이트
        userService.getUpdateToken(username, old_token - eo.getRechange_amount());
        
        
		int checkMembership = lectorsService.checkMembershipByLector_num(lector_num);
		
			if(checkMembership > 0) {
				eo.setMembership("O");
			
			} else {
				eo.setMembership("X");
			}
			
		eo.setLector_num(lector_num);
		eo.ChangeWon();
		
		exchangeOrderService.getSave(eo);
		
		return "/lector/lectorPage";
	}
	
	@GetMapping("/exchangeTokenList")
	public String exchangeTokenList(Pageable pageable, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int user_id = userService.getFindIdByUsername(username); // 아이디로 유저 번호 불러오기
        int lector_num = lectorsService.getLector_num(user_id); // 유저 번호로 강사 번호 불러오기
		
        Page<ExchangeOrder> exchangeOrderList =  exchangeOrderService.findExchangePageListByLector_num(pageable, lector_num);
        
        int nowPage = exchangeOrderList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, exchangeOrderList.getTotalPages());
			
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("exchangeOrderList", exchangeOrderList);
		
		return "/lector/exchangeTokenList";
	}
	
		
	

	
	
}

