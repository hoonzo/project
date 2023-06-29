package com.ezen.Service;

import java.util.List; 

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ezen.Repository.UserRepository;
import com.ezen.model.Users;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Users getFindByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public String getFindRoleByUsername(String username) {
		return userRepository.findRoleByUsername(username);
	}
	
	public void getSave(Users users) {
		userRepository.save(users);
	}
	
	public List<Users> getUserlist(String username) {
		return userRepository.findAllByUsername(username);
	}
	
	public String getUserpassword(String username) {
		return userRepository.findPasswordByUsername(username);
	}
	
	public int getSameIdCheck(String username) {
		return userRepository.sameIdCheck(username);
	}
	
	public int getFindIdByUsername(String username) {
		return userRepository.findIdByUsername(username);
	}
	
	@Transactional
	public void getUpdateToken(String username, double token) {
		userRepository.rechargeToken(username, token);
	}

	public double getFindTokenByUsername(String username) {
		return userRepository.findTokenByUsername(username);
	}

	@Transactional
	public void getUpdatePassword(String username, String newPassword) {
		userRepository.changePassword(newPassword, username);
	}
	
	public String getFindNameByUsername(String username) {
		return userRepository.findNameByUsername(username);
	}
	
	public String getFindEmailByUsername(String username) {
		return userRepository.findEmailByUsername(username);
	}
	
	public String getFindPhoneByUsername(String username) {
		return userRepository.findPhoneByUsername(username);
	}
	
	public String getFindPrefer_lectureByUsername(String username) {
		return userRepository.findPrefer_lectureByUsername(username);
	}
	
	@Transactional
	public void getUpdateName(String newName, String username) {
		userRepository.changeName(newName, username);
	}
	
	@Transactional
	public void getUpdateEmail(String newEmail, String username) {
		userRepository.changeEmail(newEmail, username);
	}
	
	@Transactional
	public void getUpdatePhone(String newPhone, String username) {
		userRepository.changePhone(newPhone, username);
	}
	
	@Transactional
	public void getUpdatePrefer_lecture(String newPrefer_lecture, String username) {
		userRepository.changePrefer_lecture(newPrefer_lecture, username);
	}
	
	public List<Users> getAllUserlist() {
		return userRepository.findAllUserlist();
	}
	
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}
	
	@Transactional
	public void getUpdateRole(int id) {
		userRepository.changeRole(id);
	}
	
	
	// 페이징 기능 설명
	public Page<Users> getPagingUserlist(Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 4, Sort.by(Sort.Direction.DESC, "id"));
	    return userRepository.findAll(pageRequest);
	    
	    // int page = 1; // 요청 페이지
	    // int pageSize = 3; // 페이지당 항목 수
	    // Sort sort = Sort.by(Sort.Direction.DESC, "id"); // 정렬 기준
	    // Pageable pageable = PageRequest.of(page, pageSize, sort);
	}
	
	// 검색 기능
	public Page<Users> searchUserById(String keyword, Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 4, Sort.by(Sort.Direction.DESC, "id"));
		return userRepository.searchUserById(keyword, pageRequest);
	}
	
	public Page<Users> searchUserByIdAndRole(String keyword, String role, Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 4, Sort.by(Sort.Direction.DESC, "id"));
		return userRepository.searchUserByIdAndRole(keyword, role, pageRequest);
	}
	
	public Page<Users> searchUserByUsername(String keyword, Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 4, Sort.by(Sort.Direction.DESC, "id"));
		return userRepository.searchUserByUsername(keyword, pageRequest);
	}
	
	public Page<Users> searchUserByUsernameAndRole(String keyword, String role, Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 4, Sort.by(Sort.Direction.DESC, "id"));
		return userRepository.searchUserByUsernameAndRole(keyword, role, pageRequest);
	}
	
	public Page<Users> searchUserByName(String keyword, Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 4, Sort.by(Sort.Direction.DESC, "id"));
		return userRepository.searchUserByName(keyword, pageRequest);
	}
	
	public Page<Users> searchUserByNameAndRole(String keyword, String role, Pageable pageable) {
		Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), 4, Sort.by(Sort.Direction.DESC, "id"));
		return userRepository.searchUserByNameAndRole(keyword, role, pageRequest);
	}
	
	
	
	
	
	
	
	
	
}
