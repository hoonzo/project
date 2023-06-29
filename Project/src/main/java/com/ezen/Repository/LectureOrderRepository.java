package com.ezen.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.LectureOrder;

@Repository
public interface LectureOrderRepository extends JpaRepository<LectureOrder, Integer> {
	
	@Query("SELECT ord FROM LectureOrder ord WHERE ord.user_id = :user_id")
	Page<LectureOrder> findAllLectureOrderListByUser_id(Pageable pageable, int user_id);
	
	@Modifying
	@Query("UPDATE LectureOrder ord SET ord.cancel_check = 'O' "
			+ "WHERE ord.user_id = :user_id "
			+ "AND ord.lecture_num = :lecture_num")
	void CancelLectureByUser_id(int user_id, int lecture_num);
	
	
	
}
