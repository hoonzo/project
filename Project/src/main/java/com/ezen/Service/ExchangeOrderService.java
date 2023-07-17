package com.ezen.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.ExchangeOrderRepository;
import com.ezen.model.ExchangeOrder;

@Service
public class ExchangeOrderService {
	
	@Autowired
	private ExchangeOrderRepository exchangeOrderRepository;
	
	public void getSave(ExchangeOrder exchangeOrder) {
		exchangeOrderRepository.save(exchangeOrder);
	}
	
	public String findAccountByLector_num(int lector_num) {
		return exchangeOrderRepository.findAccountByLector_num(lector_num);
	}
	
	public String findBankByLector_num(int lector_num) {
		return exchangeOrderRepository.findBankByLector_num(lector_num);
	}
	
	public Page<ExchangeOrder> findExchangePageListByLector_num(Pageable pageable, int lector_num) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "exchangeOrder_num"));
		return exchangeOrderRepository.findExchangePageListByLector_num(pageRequest, lector_num);
	}
	
	
	
	
	
	
	
	
	
}
