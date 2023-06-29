package com.ezen.Service;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.LectureOrderRepository;
import com.ezen.model.LectureOrder;

@Service
public class LectureOrderService {

	@Autowired
	private LectureOrderRepository lectureOrderRepository;
	
	
	public void lectureOrderSave(LectureOrder lectureOrder) {
		lectureOrderRepository.save(lectureOrder);
	}
	
	public Page<LectureOrder> findAllLectureOrderListByUser_id(Pageable pageable, int user_id) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "lectureOrder_num"));
		return lectureOrderRepository.findAllLectureOrderListByUser_id(pageRequest, user_id);
	}
	
	@Transactional
	public void CancelLectureByUser_id(int user_id, int lecture_num) {
		lectureOrderRepository.CancelLectureByUser_id(user_id, lecture_num);
	}
	
	
	
	
	
	
	
	
	
}
