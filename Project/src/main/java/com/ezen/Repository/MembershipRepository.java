package com.ezen.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ezen.model.Membership;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
	
	
	
}
