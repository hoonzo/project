package com.ezen.model;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
@Data
@Entity
public class Users {
	
	public Users() {
		this.role = "ROLE_USER";
	}
	
	
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    private int id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String prefer_lecture;
    
    private double manner_score;
    
    @Column(columnDefinition = "number(10,0) default 0")
    private double all_review_score = 0;

    private String reviewWriteCheck;
    
    
    private double token;
    
    private String role; //ROLE_USER, ROLE_ADMIN
    
    private LocalDate dateApplicationForDelete;
    
    
    @CreationTimestamp
    private Timestamp createDate;
}