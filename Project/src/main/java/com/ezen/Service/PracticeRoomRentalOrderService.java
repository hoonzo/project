package com.ezen.Service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.PracticeRoomRentalOrderRepository;
import com.ezen.model.PracticeRoomRentalOrder;

@Service
public class PracticeRoomRentalOrderService {
	
	@Autowired
	private PracticeRoomRentalOrderRepository practiceRoomRentalOrderRepository;
	
	
	public void getSave(PracticeRoomRentalOrder prro) {
		practiceRoomRentalOrderRepository.save(prro);
	}
	
	public Page<PracticeRoomRentalOrder> myRentalListByLector_num(Pageable pageable, int lector_num) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "practiceRoomOrder_id"));
		return practiceRoomRentalOrderRepository.myRentalListByLector_num(pageRequest, lector_num);
	}
	
	public List<PracticeRoomRentalOrder> findPracticeRoomRentalListByPracticeRoomOrder_id(int practiceRoomOrder_id) {
		return practiceRoomRentalOrderRepository.findPracticeRoomRentalListByPracticeRoomOrder_id(practiceRoomOrder_id);
	}
	
	@Transactional
	public void updateCancel_checkByPracticeRoomOrder_id(int practiceRoomOrder_id) {
		practiceRoomRentalOrderRepository.updateCancel_checkByPracticeRoomOrder_id(practiceRoomOrder_id);
	}
	
	public int findPracticeRoomOrder_idByLector_numAndRentalDateAndRentalStartTime(int lector_num, 
			LocalDate rentalDate, String rentalStartTime) {
		return practiceRoomRentalOrderRepository.findPracticeRoomOrder_idByLector_numAndRentalDateAndRentalStartTime(lector_num, rentalDate, rentalStartTime);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
