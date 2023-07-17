package com.ezen.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.ComplainLectureRepository;
import com.ezen.model.ComplainLecture;


@Service
public class ComplainLectureService {
	
	@Autowired
	private ComplainLectureRepository complainLectureRepository;
	
	
	public void getSave(ComplainLecture cl) {
		complainLectureRepository.save(cl);
	}
	
	public Page<ComplainLecture> findComplainPageList(Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "complainLeture_num"));
		return complainLectureRepository.findComplainPageList(pageRequest);
	}
	
	public Page<ComplainLecture> findUnresolvedComplainPageList(Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "complainLeture_num"));
		return complainLectureRepository.findUnresolvedComplainPageList(pageRequest);
	}
	
	public Page<ComplainLecture> findResolvedComplainPageList(Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "complainLeture_num"));
		return complainLectureRepository.findResolvedComplainPageList(pageRequest);
	}
	
	public int findMyComplainByUser_idAndLecture_num(int user_id, int lecture_num) {
		return complainLectureRepository.findMyComplainByUser_idAndLecture_num(user_id, lecture_num);
	}
	
	public List<ComplainLecture> complainDetailListByComplainLeture_num(int complainLeture_num) {
		return complainLectureRepository.complainDetailListByComplainLeture_num(complainLeture_num);
	}
	
	public ComplainLecture findComplainLectureTypeByComplainLeture_num(int complainLeture_num) {
		return complainLectureRepository.findComplainLectureTypeByComplainLeture_num(complainLeture_num);
	}
	
	
	public Page<ComplainLecture> findComplainPageListByUser_id(Pageable pageable, int user_id) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "complainLeture_num"));
		return complainLectureRepository.findComplainPageListByUser_id(pageRequest, user_id);
	}
	
	public Page<ComplainLecture> findUnresolvedComplainPageListByUser_id(Pageable pageable, int user_id) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "complainLeture_num"));
		return complainLectureRepository.findUnresolvedComplainPageListByUser_id(pageRequest, user_id);
	}
	
	public Page<ComplainLecture> findResolvedComplainPageListByUser_id(Pageable pageable, int user_id) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 2, Sort.by(Sort.Direction.DESC, "complainLeture_num"));
		return complainLectureRepository.findResolvedComplainPageListByUser_id(pageRequest, user_id);
	}
	
	
	
	
}
