package com.ezen.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
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
	
	public List<Recharge> getTokenOrderList(int user_id) {
		return rechargeRepository.findAllByUser_id(user_id);
	}
	
	
	
	
}
