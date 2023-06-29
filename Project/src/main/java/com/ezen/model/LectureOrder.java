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
public class LectureOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lectureOrder_seq")
	@SequenceGenerator(name = "lectureOrder_seq", sequenceName = "lectureOrder_seq", allocationSize = 1)
	private int lectureOrder_num;
	
	private int lecture_num;
	private int lector_num;
	private int user_id;
	
	private String lecture_title;
	
	private String cancel_check = "X";
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
