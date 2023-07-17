package com.ezen.model;

import java.sql.Timestamp; 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
public class Lecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lecture_seq")
	@SequenceGenerator(name = "lecture_seq", sequenceName = "lecture_seq", allocationSize = 1)
	private int lecture_num;
	
	private int lector_num;
	private String lecture_theme;
	private String user_num;
	
	private int maxPersonnel;
	
	@Column(columnDefinition = "number(10,0) default 0")
	private int maxPersonnelCheckNum;
	
	private String lecture_place;
	private String lecture_title;
	private String lecture_introduce;
	private double price;
	
	private LocalDate lecture_date;
	private LocalTime lecture_start_time;
	private LocalTime lecture_end_time;
	
	private String fullCheck = "X";
	private String rentalCheck;
	
	private double review_score;
	private String reviewWriteCheck;
	
	private String getTokenCheck = "X";
	
	@Column(columnDefinition = "number(10,0) default 0")
	private double all_review_score = 0;
	
	@Column(columnDefinition = "number(10,0) default 0")
	private int complainCount = 0;
	
	
	private String membership;
		
	@CreationTimestamp
	private Timestamp createDate;
	
	
	public void setLecture_date(String lecture_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.lecture_date = LocalDate.parse(lecture_date, formatter);
    }
    
    public void setLecture_start_time(String lecture_start_time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.lecture_start_time = LocalTime.parse(lecture_start_time, formatter);
    }
    
    public void setLecture_end_time(String lecture_end_time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.lecture_end_time = LocalTime.parse(lecture_end_time, formatter);
    }
	
    
}
