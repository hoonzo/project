package com.ezen.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.Recharge;

@Repository
public interface RechargeRepository extends JpaRepository<Recharge, Integer>{
	
	@Query("SELECT r FROM Recharge r WHERE r.user_id = :user_id ORDER BY rechargeOrderNum DESC")
	List<Recharge> findAllByUser_id(int user_id);
	
	
}
