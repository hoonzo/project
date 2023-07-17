package com.ezen.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.UserReview;

@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Integer>{
	
	
	@Query("SELECT ur FROM UserReview ur "
			+ "WHERE ur.lector_num = :lector_num "
			+ "AND ur.hide_review = 'X'")
	Page<UserReview> getWriteReviewPageList(Pageable pageable, int lector_num);
	
	@Query("SELECT ur FROM UserReview ur "
			+ "WHERE ur.lector_num = :lector_num "
			+ "AND ur.user_id = :user_id "
			+ "AND ur.hide_review = 'X'")
	List<UserReview> findUserReviewByLector_numAndUser_id(int lector_num, int user_id);
	
	@Query("SELECT ur FROM UserReview ur WHERE ur.userReivew_num = :userReivew_num")
	UserReview findUserReviewByUserReivew_num(int userReivew_num);
	
	@Modifying
	@Query("UPDATE UserReview ur SET ur.hide_review = 'O' WHERE ur.userReivew_num = :userReivew_num")
	void hide_reviewByUserReivew_num(int userReivew_num);
	
	@Query("SELECT ur FROM UserReview ur "
			+ "WHERE ur.user_id = :user_id "
			+ "AND ur.hide_review = 'X'")
	Page<UserReview> getReviewPageListByUser_id(Pageable pageable, int user_id);
	
	
	
}
