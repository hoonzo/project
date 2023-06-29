package com.ezen.Service;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	public int getCheckLectorRegistration(int user_id) {
		return lectorsRepository.checkLectorRegistration(user_id);
	}
	
	public Page<Lectors> getNotPermissionLectorsList(Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(Sort.Direction.DESC, "lector_num"));
		return lectorsRepository.findNotPermissionLectorsList(pageRequest);
	}
	
	public List<Lectors> getFindNotPermissionLectorsListByLectorNum(int lector_num) {
		return lectorsRepository.findNotPermissionLectorsListByLectorNum(lector_num);
	}
	
	@Transactional
	public void updateAdmin_Permission(int lector_num) {
		lectorsRepository.changeAdmin_Permission(lector_num);
	}
	
	public void deleteLectorBylector_num(int lector_num) {
		lectorsRepository.deleteById(lector_num);
	}
	
	public int getLector_num(int user_id) {
		return lectorsRepository.findLector_numByUser_id(user_id);
	}
	
	public List<Lectors> getLectorList(int lector_num) {
		return lectorsRepository.findAllByLector_num(lector_num);
	}
	
	@Transactional
	public void updateCareerByLector_num(String careerValue, int lector_num) {
		lectorsRepository.changeCareerByLector_num(careerValue, lector_num);
	}
	
	@Transactional
	public void updateLecture_room_addressByLector_num(String lecture_room_address_value, int lector_num) {
		lectorsRepository.changeLecture_room_addressByLector_num(lecture_room_address_value, lector_num);
	}
	
	@Transactional
	public void updatePrefer_personnelByLector_num(String prefer_personnel_value, int lector_num) {
		lectorsRepository.changePrefer_personnelByLector_num(prefer_personnel_value, lector_num);
	}
	
	@Transactional
	public void membershipJoin(Lectors lectors) {
		lectorsRepository.membershipJoin(lectors);
	}
	
	@Transactional
	public void renewalMembership(Lectors lectors) {
		lectorsRepository.renewalMembership(lectors);
	}

	public Timestamp getFindMembershipEndDateByLector_num(int lector_num) {
		return lectorsRepository.findMembershipEndDateByLector_num(lector_num);
	}
	
	public int checkMembershipByLector_num(int lector_num) {
		return lectorsRepository.checkMembershipByLector_num(lector_num);
	}
	
	@Transactional
	public void membershipEnd(int lector_num) {
		lectorsRepository.membershipEnd(lector_num);
	}
	
	public double findReview_scoreByLector_num(int lector_num) {
		return lectorsRepository.findReview_scoreByLector_num(lector_num);
	}
	
	public String findRevieWriteCheckByLector_num(int lector_num) {
		return lectorsRepository.findRevieWriteCheckByLector_num(lector_num);
	}
	
	@Transactional
	public void updateReview_scoreByLector_num(double review_score, int lector_num) {
		lectorsRepository.updateReview_scoreByLector_num(review_score, lector_num);
	}
	
	@Transactional
	public void updateReviewWriteCheckByUser_id(String user_id, int lector_num) {
		lectorsRepository.updateReviewWriteCheckByUser_id(user_id, lector_num);
	}
	
	public double findAll_review_scoreByLector_num(int lector_num) {
		return lectorsRepository.findAll_review_scoreByLector_num(lector_num);
	}
	
	@Transactional
	public void all_review_scoreUpdateByLector_num(double all_review_score, int lector_num) {
		lectorsRepository.all_review_scoreUpdateByLector_num(all_review_score, lector_num);
	}
	

}
