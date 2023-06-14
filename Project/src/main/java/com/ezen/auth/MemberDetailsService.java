package com.ezen.auth;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezen.Repository.UserRepository;
import com.ezen.model.Users;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        Users users = userRepository.findByUsername(username);
        
        if(users != null){
            return new MemberDetails(users);
            
        } else {
        	throw new UsernameNotFoundException(username + " 사용자를 찾을 수 없음");
        }
    }

}
