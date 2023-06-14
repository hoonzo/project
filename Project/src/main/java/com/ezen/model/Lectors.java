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
public class Lectors {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lectures_seq")
    @SequenceGenerator(name = "lectures_seq", sequenceName = "lectures_seq", allocationSize = 1)
	private int lecture_num;
	
	private int user_id;
	
	private String have_lecture_room;
	private String lecture_room_address = "";
	
	private String prefer_personnel;
	private String lecture_theme;
	private String career;
	private double review_score;
	
	private String membership;
	private String membershipType = "";
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
