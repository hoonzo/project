package com.ezen.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.PracticeRoomRentalOrder;

@Repository
public interface PracticeRoomRentalOrderRepository extends JpaRepository<PracticeRoomRentalOrder, Integer> {
	
	@Query("SELECT prro FROM PracticeRoomRentalOrder prro WHERE prro.lector_num = :lector_num")
	Page<PracticeRoomRentalOrder> myRentalListByLector_num(Pageable pageable, int lector_num);
	
	@Query("SELECT prro FROM PracticeRoomRentalOrder prro WHERE prro.practiceRoomOrder_id = :practiceRoomOrder_id")
	List<PracticeRoomRentalOrder> findPracticeRoomRentalListByPracticeRoomOrder_id(int practiceRoomOrder_id);
	
	@Modifying
	@Query("UPDATE PracticeRoomRentalOrder prro SET prro.cancel_check = 'O' WHERE prro.practiceRoomOrder_id = :practiceRoomOrder_id")
	void updateCancel_checkByPracticeRoomOrder_id(int practiceRoomOrder_id);
	
	@Query("SELECT prro.practiceRoomOrder_id FROM PracticeRoomRentalOrder prro "
			+ "WHERE prro.lector_num = :lector_num "
			+ "AND prro.rentalDate = :rentalDate "
			+ "AND prro.rentalStartTime = :rentalStartTime")
	int findPracticeRoomOrder_idByLector_numAndRentalDateAndRentalStartTime(int lector_num, 
															LocalDate rentalDate, String rentalStartTime);
	
	
}
