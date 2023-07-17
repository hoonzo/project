package com.ezen.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class UserReview {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userReivew_seq")
	@SequenceGenerator(name = "userReivew_seq", sequenceName = "userReivew_seq", allocationSize = 1)
	private int userReivew_num;
	
	private String userReivew_content;
	private int user_id;
		
	private int lecture_num;
	private int lector_num;

	private double user_review_score;

	@Column(columnDefinition = "number(10,0) default 0")
	private int update_times;
	
	private String hide_review = "X";
		
	@CreationTimestamp
	private Timestamp createDate;
	
	

}
