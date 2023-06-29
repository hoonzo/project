package com.ezen.Repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.PracticeRoomRental;

@Repository
public interface PracticeRoomRentalRepository extends JpaRepository<PracticeRoomRental, Integer>{
	
	@Query("SELECT prr FROM PracticeRoomRental prr")
	Page<PracticeRoomRental> findAllPracticeRoom(Pageable pageable);
	
	@Query("SELECT prr FROM PracticeRoomRental prr WHERE prr.practiceRoom_address LIKE '%'||:keyword||'%'")
	Page<PracticeRoomRental> findPracticeRoomByKeword(Pageable pageable, String keyword);
	
	@Query("SELECT prr FROM PracticeRoomRental prr WHERE prr.practiceRoom_id = :practiceRoom_id")
	List<PracticeRoomRental> findPracticeRoomListByPracticeRoom_id(int practiceRoom_id);
	
	@Query("SELECT prr.practiceRoom_address FROM PracticeRoomRental prr WHERE prr.practiceRoom_id = :practiceRoom_id")
	String findPracticeRoom_addressByPracticeRoom_id(int practiceRoom_id);
	
	@Query("SELECT prr.practiceRoom_price FROM PracticeRoomRental prr WHERE prr.practiceRoom_id = :practiceRoom_id")
	double findPracticeRoom_priceByPracticeRoom_id(int practiceRoom_id);
	
	
	
	
}
