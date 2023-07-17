package com.ezen.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.ExchangeOrder;

@Repository
public interface ExchangeOrderRepository extends JpaRepository<ExchangeOrder, Integer> {
	
	@Query("SELECT exo.account FROM ExchangeOrder exo WHERE exo.lector_num = :lector_num")
	String findAccountByLector_num(int lector_num);
	
	@Query("SELECT exo.bank FROM ExchangeOrder exo WHERE exo.lector_num = :lector_num")
	String findBankByLector_num(int lector_num);
	
	@Query("SELECT exo FROM ExchangeOrder exo WHERE exo.lector_num = :lector_num")
	Page<ExchangeOrder> findExchangePageListByLector_num(Pageable pageable, int lector_num);
	
	
	
}
