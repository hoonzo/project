package com.ezen.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.RechargeRepository;
import com.ezen.model.Recharge;


@Service
public class RechargeService {
	
	@Autowired
	private RechargeRepository rechargeRepository;
	
	
	public void getRechargeOrderSave(Recharge recharge) {
		rechargeRepository.save(recharge);
	}
	
	public Page<Recharge> findMyRechargeList(Pageable pageable, int user_id) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "rechargeOrderNum"));
		return rechargeRepository.findMyRechargeList(pageRequest, user_id);
	}
	
	
	
	
}
