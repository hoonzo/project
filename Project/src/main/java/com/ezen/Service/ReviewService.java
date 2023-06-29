package com.ezen.Service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.ReviewRepository;
import com.ezen.model.Review;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
	public void getSave(Review review) {
		reviewRepository.save(review);
	}

	public List<Review> findMyReviewByLecture_numAndUser_id(int lecture_num, int user_id) {
		return reviewRepository.findMyReviewByLecture_numAndUser_id(lecture_num, user_id);
	}
	
	public double findLector_review_scoreByLector_numAndUser_id(int lector_num, int user_id) {
		return reviewRepository.findLector_review_scoreByLector_numAndUser_id(lector_num, user_id);
	}
	
	public Page<Review> findAllReviewByLector_num(Pageable pageable, int lector_num) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "reivew_num"));
		return reviewRepository.findAllReviewByLector_num(pageRequest, lector_num);
	}
	
	public List<Review> findReviewByLecture_num(int lecture_num) {
		return reviewRepository.findReviewByLecture_num(lecture_num);
	}
	
	public Page<Review> findReviewListByUser_id(Pageable pageable, int user_id) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "reivew_num"));
		return reviewRepository.findReviewListByUser_id(pageRequest, user_id);
	}
	
	public Review findReviewByReivew_num(int reivew_num) {
		return reviewRepository.findReviewByReivew_num(reivew_num);
	}
	
	

}
