package com.ezen.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class ComplainLecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complainLeture_seq")
	@SequenceGenerator(name = "complainLeture_seq", sequenceName = "complainLeture_seq", allocationSize = 1)
	private int complainLeture_num;
	
	private int user_id;
	private int lecture_num;
	
	private String complain_reason;
	private String complain_content;
	
	private String complain_resolve = "X";
	private String processingResult = "X";
	
	@CreationTimestamp
	private Timestamp createDate;

}
