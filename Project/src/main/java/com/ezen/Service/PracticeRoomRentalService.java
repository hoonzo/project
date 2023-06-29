package com.ezen.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.PracticeRoomRentalRepository;
import com.ezen.model.PracticeRoomRental;

@Service
public class PracticeRoomRentalService {
	
	@Autowired
	private PracticeRoomRentalRepository practiceRoomRentalRepository;
	
	
	
	
	public Page<PracticeRoomRental> findAllPracticeRoom(Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 3, Sort.by(Sort.Direction.DESC, "practiceRoom_id"));
		return practiceRoomRentalRepository.findAllPracticeRoom(pageRequest);
	}
	
	public Page<PracticeRoomRental> findPracticeRoomByKeword(Pageable pageable, String keyword) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 3, Sort.by(Sort.Direction.DESC, "practiceRoom_id"));
		return practiceRoomRentalRepository.findPracticeRoomByKeword(pageRequest, keyword);
	}
	
	public List<PracticeRoomRental> findPracticeRoomListByPracticeRoom_id(int practiceRoom_id) {
		return practiceRoomRentalRepository.findPracticeRoomListByPracticeRoom_id(practiceRoom_id);
	}
	
	public String findPracticeRoom_addressByPracticeRoom_id(int practiceRoom_id) {
		return practiceRoomRentalRepository.findPracticeRoom_addressByPracticeRoom_id(practiceRoom_id);
	}
	
	public double findPracticeRoom_priceByPracticeRoom_id(int practiceRoom_id) {
		return practiceRoomRentalRepository.findPracticeRoom_priceByPracticeRoom_id(practiceRoom_id);
	}

	
}
