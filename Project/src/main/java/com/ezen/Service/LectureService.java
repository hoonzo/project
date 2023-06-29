package com.ezen.Service;

import java.time.LocalDate; 
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.LectureRepository;
import com.ezen.model.Lecture;

@Service
public class LectureService {
	
	@Autowired
	private LectureRepository lectureRepository;
	
	
	
	public void getSave(Lecture lecture) {
		lectureRepository.save(lecture);
	}
	
	public Page<Lecture> getLecturePageList(Pageable pageable) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.lecturePageList(pageRequest, nowTime);
	}

	
	public Page<Lecture> getSearchThemePageList(Pageable pageable, int themesIndex, List<String> lectureThemes) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		
		if(themesIndex == 1) {
			
			String theme_1 = lectureThemes.get(0);
			
			return lectureRepository.themeSearchPageList_1value(pageRequest, nowTime, theme_1);
			
		} else if(themesIndex == 2) {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			
			return lectureRepository.themeSearchPageList_2values(pageRequest, nowTime, theme_1, theme_2);
			
		} else if(themesIndex == 3) {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			String theme_3 = themes[2];
			
			return lectureRepository.themeSearchPageList_3values(pageRequest, nowTime, theme_1, theme_2, theme_3);
			
		} else if(themesIndex == 4) {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			String theme_3 = themes[2];
			String theme_4 = themes[3];
			
			return lectureRepository.themeSearchPageList_4values(pageRequest, nowTime, theme_1, theme_2, theme_3, theme_4);
			
		} else if(themesIndex == 5) {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			String theme_3 = themes[2];
			String theme_4 = themes[3];
			String theme_5 = themes[4];
			
			return lectureRepository.themeSearchPageList_5values(pageRequest, nowTime, theme_1, theme_2, theme_3, theme_4, theme_5);
			
		} else if(themesIndex == 6) {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			String theme_3 = themes[2];
			String theme_4 = themes[3];
			String theme_5 = themes[4];
			String theme_6 = themes[5];
			
			return lectureRepository.themeSearchPageList_6values(pageRequest, nowTime, theme_1, theme_2, theme_3, theme_4, theme_5, theme_6);
			
		} else if(themesIndex == 7) {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			String theme_3 = themes[2];
			String theme_4 = themes[3];
			String theme_5 = themes[4];
			String theme_6 = themes[5];
			String theme_7 = themes[6];
			
			return lectureRepository.themeSearchPageList_7values(pageRequest, nowTime, theme_1, theme_2, theme_3, theme_4, theme_5, 
																	theme_6, theme_7);
			
		} else if(themesIndex == 8) {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			String theme_3 = themes[2];
			String theme_4 = themes[3];
			String theme_5 = themes[4];
			String theme_6 = themes[5];
			String theme_7 = themes[6];
			String theme_8 = themes[7];
			
			return lectureRepository.themeSearchPageList_8values(pageRequest, nowTime, theme_1, theme_2, theme_3, theme_4, theme_5, 
																	theme_6, theme_7, theme_8);
			
		} else {
			
			String[] themes = new String[lectureThemes.size()];
			
			for(int i = 0; i < lectureThemes.size(); i++) {
				themes[i] = lectureThemes.get(i);
	    	}
			
			String theme_1 = themes[0];
			String theme_2 = themes[1];
			String theme_3 = themes[2];
			String theme_4 = themes[3];
			String theme_5 = themes[4];
			String theme_6 = themes[5];
			String theme_7 = themes[6];
			String theme_8 = themes[7];
			String theme_9 = themes[8];
			
			return lectureRepository.themeSearchPageList_9values(pageRequest, nowTime, theme_1, theme_2, theme_3, theme_4, theme_5, 
																	theme_6, theme_7, theme_8, theme_9);
		}
	}
	
	public Page<Lecture> searchLectureByTitleLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.searchLectureByTitleLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> searchLectureByPlaceLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.searchLectureByPlaceLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public List<Lecture> getFindLectureListByLecture_num(int lecture_num) {
		return lectureRepository.findLectureListByLecture_num(lecture_num);
	}
	
	public String findUser_numByLecture_num(int lecture_num) {
		return lectureRepository.findUser_numByLecture_num(lecture_num);
	}
	
	public int findMaxPersonnelByLecture_num(int lecture_num) {
		return lectureRepository.findMaxPersonnelByLecture_num(lecture_num);
	}
	
	@Transactional
	public void lectureReservationSetUser(String user_num, int lecture_num) {
		lectureRepository.lectureReservationSetUser(user_num, lecture_num);
	}
	
	@Transactional
	public void reservationFullCheckUpdate(int lecture_num) {
		lectureRepository.reservationFullCheckUpdate(lecture_num);
	}
	
	public int findMaxPersonnelCheckNumByLecture_num(int lecture_num) {
		return lectureRepository.findMaxPersonnelCheckNumByLecture_num(lecture_num);
	}
	
	@Transactional
	public void maxPersonnelCheckNumUpdateByLecture_num(int maxPersonnelUpdateNum, int lecture_num) {
		lectureRepository.maxPersonnelCheckNumUpdateByLecture_num(maxPersonnelUpdateNum, lecture_num);
	}
	
	public Page<Lecture> findTimeDeadlinePageListByTitleLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findTimeDeadlinePageListByTitleLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> findTimeDeadlinePageListByPlaceLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findTimeDeadlinePageListByPlaceLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> findReservationDeadlinePageListByTitleLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findReservationDeadlinePageListByTitleLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> findReservationDeadlinePageListByPlaceLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findReservationDeadlinePageListByPlaceLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> findHighScorePageListByTitleLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findHighScorePageListByTitleLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> findHighScorePageListByPlaceLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findHighScorePageListByPlaceLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> findLowPricePageListByTitleLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findLowPricePageListByTitleLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	public Page<Lecture> findLowPricePageListByPlaceLikeKeyword(Pageable pageable, String keyword) {
		
		LocalTime nowTime = LocalTime.now();
		
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findLowPricePageListByPlaceLikeKeyword(pageRequest, nowTime, keyword);
	}
	
	@Transactional
	public void membershipUpdate(int lector_num) {
		lectureRepository.membershipUpdate(lector_num);
	}
	
	@Transactional
	public void membershipEnd(int lector_num) {
		lectureRepository.membershipEnd(lector_num);
	}
	
	
	public Page<Lecture> findAllLectureListByLector_num(Pageable pageable, int lector_num) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findAllLectureListByLector_num(pageRequest, lector_num);
	}
	
	@Transactional
	public void rentalCheckUpdateXByLecture_num(int lecture_num) {
		lectureRepository.rentalCheckUpdateXByLecture_num(lecture_num);
	}

	public int findLecture_numByRentalCheck(String rentalCheck) {
		return lectureRepository.findLecture_numByRentalCheck(rentalCheck);
	}
	
	@Transactional
	public void updateLecture_placeByLecture_num(int lecture_num) {
		lectureRepository.updateLecture_placeByLecture_num(lecture_num);
	}
	
	@Transactional
	public void updateAllLectureByLecture_num(String lecture_title, String lecture_introduce, String lecture_place, 
										int maxPersonnel, double price, 
										LocalDate lecture_date, LocalTime lecture_start_time, LocalTime lecture_end_time,
										String rentalCheck, int lecture_num) {
		
		lectureRepository.updateAllLectureByLecture_num(lecture_title, lecture_introduce, lecture_place, maxPersonnel, price, 
														lecture_date, lecture_start_time, lecture_end_time, rentalCheck, lecture_num);
	}
	
	public void deleteById(int lecture_num) {
		lectureRepository.deleteById(lecture_num);
	}
	
	public List<Lecture> findLecture_placeIsX() {
		
		LocalTime nowTime = LocalTime.now();
		return lectureRepository.findLecture_placeIsX(nowTime);
	}
	
	public Page<Lecture> findEndLectureByLector_num(Pageable pageable, int lector_num) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(Sort.Direction.DESC, "lecture_num"));
		return lectureRepository.findEndLectureByLector_num(pageRequest, lector_num);
	}
	
	public String findReviewWriteCheckByLecture_num(int lecture_num) {
		return lectureRepository.findReviewWriteCheckByLecture_num(lecture_num);
	}
	
	@Transactional
	public void updateReviewWriteCheckByUser_id(String user_id, int lecture_num) {
		lectureRepository.updateReviewWriteCheckByUser_id(user_id, lecture_num);
	}
	
	@Transactional
	public void updateReview_scoreByLecture_num(double review_score, int lecture_num) {
		lectureRepository.updateReview_scoreByLecture_num(review_score, lecture_num);
	}
	
	public double findReview_scoreByLecture_num(int lecture_num) {
		return lectureRepository.findReview_scoreByLecture_num(lecture_num);
	}
	
	public double findAll_review_scoreByLecture_num(int lecture_num) {
		return lectureRepository.findAll_review_scoreByLecture_num(lecture_num);
	}
	
	@Transactional
	public void all_review_scoreUpdateByLecture_num(double all_review_score, int lecture_num) {
		lectureRepository.all_review_scoreUpdateByLecture_num(all_review_score, lecture_num);
	}
	
	
	
	
	
}
