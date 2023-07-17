package com.ezen.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.ComplainLecture;


@Repository
public interface ComplainLectureRepository extends JpaRepository<ComplainLecture, Integer>{
	
	@Query("SELECT cl FROM ComplainLecture cl")
	Page<ComplainLecture> findComplainPageList(Pageable pageable);
	
	@Query("SELECT cl FROM ComplainLecture cl WHERE cl.complain_resolve = 'X'")
	Page<ComplainLecture> findUnresolvedComplainPageList(Pageable pageable);
	
	@Query("SELECT cl FROM ComplainLecture cl WHERE cl.complain_resolve <> 'X'")
	Page<ComplainLecture> findResolvedComplainPageList(Pageable pageable);
	
	
	@Query("SELECT count(*) FROM ComplainLecture cl "
			+ "WHERE cl.user_id = :user_id "
			+ "AND cl.lecture_num = :lecture_num")
	int findMyComplainByUser_idAndLecture_num(int user_id, int lecture_num);
	
	@Query("SELECT cl FROM ComplainLecture cl WHERE cl.complainLeture_num = :complainLeture_num")
	List<ComplainLecture> complainDetailListByComplainLeture_num(int complainLeture_num);
	
	@Query("SELECT cl FROM ComplainLecture cl WHERE cl.complainLeture_num = :complainLeture_num")
	ComplainLecture findComplainLectureTypeByComplainLeture_num(int complainLeture_num);
	
	
	
	@Query("SELECT cl FROM ComplainLecture cl WHERE cl.user_id = :user_id")
	Page<ComplainLecture> findComplainPageListByUser_id(Pageable pageable, int user_id);
	
	@Query("SELECT cl FROM ComplainLecture cl "
			+ "WHERE cl.user_id = :user_id "
			+ "AND cl.complain_resolve = 'X'")
	Page<ComplainLecture> findUnresolvedComplainPageListByUser_id(Pageable pageable, int user_id);
	
	@Query("SELECT cl FROM ComplainLecture cl "
			+ "WHERE cl.user_id = :user_id "
			+ "AND cl.complain_resolve <> 'X'")
	Page<ComplainLecture> findResolvedComplainPageListByUser_id(Pageable pageable, int user_id);
	
	
	
}
