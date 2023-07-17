package com.ezen.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer>{
	
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> lecturePageList(Pageable Pagealbe, LocalTime nowTime);
	
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE (lec.lecture_theme LIKE '%'|| :theme_1 ||'%') "
			+ "AND lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime))"
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_1value(Pageable pageable, LocalTime nowTime, String theme_1);

	@Query("SELECT lec FROM Lecture lec " +
		       "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') " +
		       "OR (lec.lecture_theme LIKE '%'|| :theme_2 ||'%')) " +
		       "AND lec.fullCheck = 'X' " +
		       "AND (lec.lecture_date > TRUNC(SYSDATE) " +
		       "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) " +
		       "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_2values(Pageable pageable, LocalTime nowTime, String theme_1, String theme_2);

	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_2 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_3 ||'%'))"
			+ "AND lec.fullCheck = 'X'"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime))"
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_3values(Pageable pageable, LocalTime nowTime, String theme_1, String theme_2, String theme_3);

	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_2 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_3 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_4 ||'%'))"
			+ "AND lec.fullCheck = 'X'"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_4values(Pageable pageable, LocalTime nowTime, String theme_1, String theme_2, String theme_3, String theme_4);

	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_2 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_3 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_4 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_5 ||'%'))"
			+ "AND lec.fullCheck = 'X'"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_5values(Pageable pageable, LocalTime nowTime, String theme_1, String theme_2, String theme_3, String theme_4, String theme_5);

	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_2 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_3 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_4 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_5 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_6 ||'%'))"
			+ "AND lec.fullCheck = 'X'"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_6values(Pageable pageable, LocalTime nowTime, String theme_1,  String theme_2, String theme_3, String theme_4, String theme_5,
												String theme_6);

	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_2 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_3 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_4 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_5 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_6 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_7 ||'%'))"
			+ "AND lec.fullCheck = 'X'"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_7values(Pageable pageable, LocalTime nowTime, String theme_1, String theme_2, String theme_3, String theme_4, String theme_5,
												String theme_6, String theme_7);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_2 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_3 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_4 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_5 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_6 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_7 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_8 ||'%'))"
			+ "AND lec.fullCheck = 'X'"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_8values(Pageable pageable, LocalTime nowTime, String theme_1, String theme_2, String theme_3, String theme_4, String theme_5,
												String theme_6, String theme_7, String theme_8);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE ((lec.lecture_theme LIKE '%'|| :theme_1 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_2 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_3 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_4 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_5 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_6 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_7 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_8 ||'%') OR "
			+ "(lec.lecture_theme LIKE '%'|| :theme_9 ||'%'))"
			+ "AND lec.fullCheck = 'X'"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> themeSearchPageList_9values(Pageable pageable, LocalTime nowTime, String theme_1, String theme_2, String theme_3, String theme_4, String theme_5,
												String theme_6, String theme_7, String theme_8, String theme_9);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE (lec.lecture_title LIKE '%'|| :keyword ||'%') "
			+ "AND lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> searchLectureByTitleLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE (lec.lecture_place LIKE '%'|| :keyword ||'%') "
			+ "AND lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> searchLectureByPlaceLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	
	@Query("SELECT lec FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	List<Lecture> findLectureListByLecture_num(int lecture_num);
	
	@Query("SELECT lec.user_num FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	String findUser_numByLecture_num(int lecture_num);
	
	@Query("SELECT lec.maxPersonnel FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	int findMaxPersonnelByLecture_num(int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.user_num = :user_num WHERE lec.lecture_num = :lecture_num")
	void lectureReservationSetUser(String user_num, int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.fullCheck = 'O' WHERE lec.lecture_num = :lecture_num")
	void reservationFullCheckUpdate(int lecture_num);
	
	@Query("SELECT lec.maxPersonnelCheckNum FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	int findMaxPersonnelCheckNumByLecture_num(int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.maxPersonnelCheckNum = :maxPersonnelUpdateNum WHERE lec.lecture_num = :lecture_num")
	void maxPersonnelCheckNumUpdateByLecture_num(int maxPersonnelUpdateNum, int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.rentalCheck = 'X' WHERE lec.lecture_num = :lecture_num")
	void rentalCheckUpdateXByLecture_num(int lecture_num);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.fullCheck = 'X' "
			+ "AND lec.lecture_title LIKE '%'|| :keyword ||'%' "
			+ "AND lec.lecture_date = TRUNC(SYSDATE) "
			+ "AND lec.lecture_start_time >= :nowTime "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findTimeDeadlinePageListByTitleLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.fullCheck = 'X' "
			+ "AND lec.lecture_place LIKE '%'|| :keyword ||'%' "
			+ "AND lec.lecture_date = TRUNC(SYSDATE) "
			+ "AND lec.lecture_start_time >= :nowTime "
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findTimeDeadlinePageListByPlaceLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE (lec.maxPersonnel - lec.maxPersonnelCheckNum = 1)"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "AND (lec.lecture_title LIKE '%'|| :keyword ||'%')"
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findReservationDeadlinePageListByTitleLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);

	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE (lec.maxPersonnel - lec.maxPersonnelCheckNum = 1)"
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "AND lec.lecture_place LIKE '%'|| :keyword ||'%'"
			+ "ORDER BY lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findReservationDeadlinePageListByPlaceLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	@Query("SELECT lec FROM Lecture lec " 
			+ "INNER JOIN Lectors l "
			+ "ON lec.lector_num = l.lector_num "
			+ "WHERE lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "AND lec.lecture_title LIKE '%'||:keyword||'%'"
			+ "ORDER BY l.review_score DESC, lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findHighScorePageListByTitleLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword); 
	
	@Query("SELECT lec FROM Lecture lec " 
			+ "INNER JOIN Lectors l "
			+ "ON lec.lector_num = l.lector_num "
			+ "WHERE lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "AND lec.lecture_place LIKE '%'||:keyword||'%'"
			+ "ORDER BY l.review_score DESC, lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findHighScorePageListByPlaceLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "AND lec.lecture_title LIKE '%'||:keyword||'%' "
			+ "ORDER BY lec.price ASC, lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findLowPricePageListByTitleLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.fullCheck = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "AND lec.lecture_place LIKE '%'||:keyword||'%' "
			+ "ORDER BY lec.price ASC, lec.membership ASC, lec.lecture_date ASC")
	Page<Lecture> findLowPricePageListByPlaceLikeKeyword(Pageable pageable, LocalTime nowTime, String keyword);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.membership = 'O' WHERE lec.lector_num = :lector_num")
	void membershipUpdate(int lector_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.membership = 'X' WHERE lec.lector_num = :lector_num")
	void membershipEnd(int lector_num);
	
	@Query("SELECT lec FROM Lecture lec WHERE lec.lector_num = :lector_num ORDER BY lec.lecture_date DESC")
	Page<Lecture> findAllLectureListByLector_num(Pageable pageable, int lector_num);
	
	@Query("SELECT lec.lecture_num FROM Lecture lec WHERE lec.rentalCheck = :rentalCheck")
	int findLecture_numByRentalCheck(String rentalCheck);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.lecture_place = 'X' WHERE lec.lecture_num = :lecture_num")
	void updateLecture_placeByLecture_num(int lecture_num);
	
	@Transactional
	@Modifying
	@Query("UPDATE Lecture lec SET "
			+ "lec.lecture_title = :lecture_title, "
			+ "lec.lecture_introduce = :lecture_introduce, "
			+ "lec.lecture_place = :lecture_place, "
			+ "lec.maxPersonnel = :maxPersonnel, "
			+ "lec.price = :price, "
			+ "lec.lecture_date = :lecture_date, "
			+ "lec.lecture_start_time = :lecture_start_time, "
			+ "lec.lecture_end_time = :lecture_end_time, "
			+ "lec.rentalCheck = :rentalCheck "
			+ "WHERE lec.lecture_num = :lecture_num")
	void updateAllLectureByLecture_num(String lecture_title, String lecture_introduce, String lecture_place, 
										int maxPersonnel, double price, 
										LocalDate lecture_date, LocalTime lecture_start_time, LocalTime lecture_end_time,
										String rentalCheck, int lecture_num);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.lecture_place = 'X' "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime))")
	List<Lecture> findLecture_placeIsX(LocalTime nowTime);
	
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.lecture_date < TRUNC(SYSDATE) "
			+ "AND lec.lector_num = :lector_num "
			+ "ORDER BY lec.lecture_date DESC")
	Page<Lecture> findEndLectureByLector_num(Pageable pageable, int lector_num);
	
	@Query("SELECT lec.reviewWriteCheck FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	String findReviewWriteCheckByLecture_num(int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.reviewWriteCheck = :user_id WHERE lec.lecture_num = :lecture_num")
	void updateReviewWriteCheckByUser_id(String user_id, int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.review_score = :review_score WHERE lec.lecture_num = :lecture_num")
	void updateReview_scoreByLecture_num(double review_score, int lecture_num);
	
	@Query("SELECT lec.review_score FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	double findReview_scoreByLecture_num(int lecture_num);
	
	@Query("SELECT lec.all_review_score FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	double findAll_review_scoreByLecture_num(int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.all_review_score = :all_review_score WHERE lec.lecture_num = :lecture_num")
	void all_review_scoreUpdateByLecture_num(double all_review_score, int lecture_num);
	
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.lector_num = :lector_num "
			+ "AND (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.lecture_date ASC")
	Page<Lecture> findNotEndLectureListByLector_num(Pageable pageable, int lector_num, LocalTime nowTime);
	
	@Query("SELECT lec.price FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	double findPriceByLecture_num(int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.getTokenCheck = 'O' WHERE lec.lecture_num = :lecture_num")
	void updateGetTokenCheckByLecture_num(int lecture_num);
	
	
	
	// 어드민 기능
	@Query("SELECT lec FROM Lecture lec ORDER BY lec.lecture_date DESC")
	Page<Lecture> findAllLectureList(Pageable pageable);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE (lec.lecture_date > TRUNC(SYSDATE) "
			+ "OR (lec.lecture_date = TRUNC(SYSDATE) AND lec.lecture_start_time >= :nowTime)) "
			+ "ORDER BY lec.lecture_date ASC")
	Page<Lecture> findNotEndLectureList(Pageable pageable, LocalTime nowTime);
	
	@Query("SELECT lec FROM Lecture lec "
			+ "WHERE lec.lecture_date < TRUNC(SYSDATE) "
			+ "ORDER BY lec.lecture_date DESC")
	Page<Lecture> findEndLectureList(Pageable pageable);
	
	@Query("SELECT lec FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	Lecture findLectureTypeByLecture_num(int lecture_num);
	
	@Query("SELECT lec.complainCount FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	int findComplainCountByLecture_num(int lecture_num);
	
	@Modifying
	@Query("UPDATE Lecture lec SET lec.complainCount = :complainCount WHERE lec.lecture_num = :lecture_num")
	void updateComplainCountByLecture_num(int complainCount, int lecture_num);
	
	@Query("SELECT lec.lector_num FROM Lecture lec WHERE lec.lecture_num = :lecture_num")
	int findLector_numByLecture_num(int lecture_num);
	
	
	
	
}
