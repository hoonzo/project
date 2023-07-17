package com.ezen.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	@Query("SELECT rev FROM Review rev "
			+ "WHERE rev.lecture_num = :lecture_num "
			+ "AND rev.user_id = :user_id "
			+ "AND rev.hide_review = 'X' ")
	List<Review> findMyReviewByLecture_numAndUser_id(int lecture_num, int user_id);
	
	@Query("SELECT rev.lector_review_score FROM Review rev "
			+ "WHERE rev.lector_num = :lector_num "
			+ "AND rev.user_id = :user_id")
	double findLector_review_scoreByLector_numAndUser_id(int lector_num, int user_id);
	
	@Query("SELECT rev FROM Review rev "
			+ "WHERE rev.lector_num = :lector_num "
			+ "AND rev.hide_review = 'X' ")
	Page<Review> findAllReviewByLector_num(Pageable pageable, int lector_num);
	
	@Query("SELECT rev FROM Review rev "
			+ "WHERE rev.lecture_num = :lecture_num "
			+ "AND rev.hide_review = 'X' ")
	List<Review> findReviewByLecture_num(int lecture_num);
	
	@Query("SELECT rev FROM Review rev "
			+ "WHERE rev.user_id = :user_id "
			+ "AND rev.hide_review = 'X' ")
	Page<Review> findReviewListByUser_id(Pageable pageable, int user_id);
	
	@Query("SELECT rev FROM Review rev WHERE rev.reivew_num = :reivew_num")
	Review findReviewByReivew_num(int reivew_num);
	
	@Modifying
	@Query("UPDATE Review rev SET rev.hide_review = 'O' WHERE rev.reivew_num = :reivew_num")
	void hide_review(int reivew_num);
	
}
