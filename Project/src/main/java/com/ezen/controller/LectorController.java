package com.ezen.controller;


import java.sql.Timestamp;
import java.time.LocalDate;
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

import com.ezen.Service.LectorsService;
import com.ezen.Service.LectureService;
import com.ezen.Service.MembershipService;
import com.ezen.Service.PracticeRoomRentalOrderService;
import com.ezen.Service.PracticeRoomRentalService;
import com.ezen.Service.UserService;
import com.ezen.model.Lectors;
import com.ezen.model.Lecture;
import com.ezen.model.LectureDTO;
import com.ezen.model.Membership;
import com.ezen.model.MembershipDTO;
import com.ezen.model.PracticeRoomRental;
import com.ezen.model.PracticeRoomRentalOrder;
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
	
	/////////////////////////////////////////////////////////////////////////////// 토큰 환전
	@GetMapping("/exchangeToken")
	public String exchangeToken(Model model) {
		
		
		return "/lector/exchangeToken";
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
	
	
	@GetMapping("/lectureManagementDetail")
	public String lectureManagementDetail(Lecture lecture, Model model) {
		
		List<Lecture> lectureDetailList = lectureService.getFindLectureListByLecture_num(lecture.getLecture_num());
		
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
		
		return "lectureManagement";
	}
	
	@GetMapping("/lectureDelete")
	public String lectureDelete(Lecture lecture) {
		
		lectureService.deleteById(lecture.getLecture_num());
		
		return "lectureManagement";
	}
	
	
	
	
}

