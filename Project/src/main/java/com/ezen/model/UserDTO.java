package com.ezen.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	 private int id;

	 private String username;
	 private String password;
	    
	 private String name;
	 private String email;
	 private String phone;
	 private String prefer_lecture;
	 private double manner_score;
	 private double token;
	 
	 private Role role;
	 private Timestamp createDate;
	 
}
