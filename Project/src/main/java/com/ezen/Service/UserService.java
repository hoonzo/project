package com.ezen.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.Repository.UserRepository;
import com.ezen.model.UserDTO;
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
	
	
	
	
}
