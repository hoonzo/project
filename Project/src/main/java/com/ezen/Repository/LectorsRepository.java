package com.ezen.Repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.Lectors;

@Repository
public interface LectorsRepository extends JpaRepository<Lectors, Integer> {
	
	@Query("SELECT COUNT(*) FROM Lectors l WHERE l.user_id = :user_id")
	int checkLectorRegistration(int user_id);
	
	@Query("SELECT l FROM Lectors l WHERE l.admin_Permission = 'X' ORDER BY l.lector_num DESC")
	Page<Lectors> findNotPermissionLectorsList(Pageable pageable);
	
	@Query("SELECT l FROM Lectors l WHERE l.lector_num = :lector_num")
	List<Lectors> findNotPermissionLectorsListByLectorNum(int lector_num);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.admin_Permission = 'O' WHERE l.lector_num = :lector_num")
	void changeAdmin_Permission(int lector_num);
	
	@Query("SELECT l.lector_num FROM Lectors l WHERE l.user_id = :user_id")
	int findLector_numByUser_id(int user_id);
	
	@Query("SELECT l FROM Lectors l WHERE l.lector_num = :lector_num")
	List<Lectors> findAllByLector_num(int lector_num);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.career = :careerValue WHERE l.lector_num = :lector_num")
	void changeCareerByLector_num(String careerValue, int lector_num);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.lecture_room_address = :lecture_room_address_value, l.have_lecture_room = 'O' WHERE l.lector_num = :lector_num")
	void changeLecture_room_addressByLector_num(String lecture_room_address_value, int lector_num);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.prefer_personnel = :prefer_personnel_value WHERE l.lector_num = :lector_num")
	void changePrefer_personnelByLector_num(String prefer_personnel_value, int lector_num);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.membership_Type = :#{#lectors.membership_Type}, l.membership = 'O', "
	        + "l.membershipJoinDate = :#{#lectors.membershipJoinDate}, l.membershipEndDate = :#{#lectors.membershipEndDate} "
	        	+ "WHERE l.lector_num = :#{#lectors.lector_num}")
	void membershipJoin(Lectors lectors);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.membership_Type = :#{#lectors.membership_Type},"
	        + "l.membershipEndDate = :#{#lectors.membershipEndDate} "
	        	+ "WHERE l.lector_num = :#{#lectors.lector_num}")
	void renewalMembership(Lectors lectors);
	
	@Query("SELECT l.membershipEndDate FROM Lectors l WHERE l.lector_num = :lector_num")
	Timestamp findMembershipEndDateByLector_num(int lector_num);
	
	@Query("SELECT COUNT(*) FROM Lectors l WHERE l.membership = 'O' AND l.lector_num = :lector_num")
	int checkMembershipByLector_num(int lector_num);
	
	
	@Modifying
	@Query("UPDATE Lectors l SET l.membership = 'X' WHERE l.lector_num = :lector_num")
	void membershipEnd(int lector_num);
	
	@Query("SELECT l.review_score FROM Lectors l WHERE l.lector_num = :lector_num")
	double findReview_scoreByLector_num(int lector_num);
	
	@Query("SELECT l.reviewWriteCheck FROM Lectors l WHERE l.lector_num = :lector_num")
	String findRevieWriteCheckByLector_num(int lector_num);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.review_score = :review_score WHERE l.lector_num = :lector_num")
	void updateReview_scoreByLector_num(double review_score, int lector_num);

	@Modifying
	@Query("UPDATE Lectors l SET l.reviewWriteCheck = :user_id WHERE l.lector_num = :lector_num")
	void updateReviewWriteCheckByUser_id(String user_id, int lector_num);
	
	@Query("SELECT l.all_review_score FROM Lectors l WHERE l.lector_num = :lector_num")
	double findAll_review_scoreByLector_num(int lector_num);
	
	@Modifying
	@Query("UPDATE Lectors l SET l.all_review_score = :all_review_score WHERE l.lector_num = :lector_num")
	void all_review_scoreUpdateByLector_num(double all_review_score, int lector_num);
	
	@Query("SELECT l.user_id FROM Lectors l WHERE l.lector_num = :lector_num")
	int findUser_idByLector_num(int lector_num);
	
	
	
	
	
	
	
	
	
	
}
