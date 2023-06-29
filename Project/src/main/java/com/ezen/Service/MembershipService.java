package com.ezen.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.Repository.MembershipRepository;
import com.ezen.model.Membership;

@Service
public class MembershipService {
	
	@Autowired
	private MembershipRepository membershipRepository;
	
	
	
	public void insertMembership(Membership membership) {
		membershipRepository.save(membership);
	}
	
}
