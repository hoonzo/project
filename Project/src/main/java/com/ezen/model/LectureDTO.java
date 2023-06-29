package com.ezen.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LectureDTO {
	
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
	
	private int lecture_num;
	
	private int lector_num;
	private String lecture_theme;
	private String user_num;
	
	private int maxPersonnel;
	private String lecture_place;
	private String lecture_introduce;
	private double price;
	
	private LocalDate lecture_date;
	private LocalTime lecture_start_time;
	private LocalTime lecture_end_time;
	
	private double review_score;
	
	private String membership;
		
	private Timestamp createDate;
	
}
