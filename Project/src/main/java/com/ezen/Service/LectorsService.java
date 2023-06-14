package com.ezen.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.Repository.LectorsRepository;
import com.ezen.model.Lectors;

@Service
public class LectorsService {
	
	@Autowired
	LectorsRepository lectorsRepository;
	
	public void getSave(Lectors lectors) {
		lectorsRepository.save(lectors);
	}

}
