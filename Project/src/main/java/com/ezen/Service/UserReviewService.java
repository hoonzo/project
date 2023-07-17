package com.ezen.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.UserReviewRepository;
import com.ezen.model.UserReview;

@Service
public class UserReviewService {
	
	@Autowired
	private UserReviewRepository userReviewRepository;
	
	
	public void getSave(UserReview userReview) {
		userReviewRepository.save(userReview);
	}
	
	public void deleteAllByUserReivew_num(int userReivew_num) {
		userReviewRepository.deleteById(userReivew_num);
	}
	
	public Page<UserReview> getWriteReviewPageList(Pageable pageable, int lector_num) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "userReivew_num"));
		return userReviewRepository.getWriteReviewPageList(pageRequest, lector_num);
	}
	
	public List<UserReview> findUserReviewByLector_numAndUser_id(int lector_num, int user_id) {
		return userReviewRepository.findUserReviewByLector_numAndUser_id(lector_num, user_id);
	}
	
	public UserReview findUserReviewByUserReivew_num(int userReivew_num) {
		return userReviewRepository.findUserReviewByUserReivew_num(userReivew_num);
	}
	
	public void hide_reviewByUserReivew_num(int userReivew_num) {
		userReviewRepository.hide_reviewByUserReivew_num(userReivew_num);
	}
	
	public Page<UserReview> getReviewPageListByUser_id(Pageable pageable, int user_id) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "userReivew_num"));
		return userReviewRepository.getReviewPageListByUser_id(pageRequest, user_id);
	}
	
	
}
