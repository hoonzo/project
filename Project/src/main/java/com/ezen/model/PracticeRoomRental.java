package com.ezen.model;

import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@Entity
public class PracticeRoomRental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "practiceRoom_seq")
	@SequenceGenerator(name = "practiceRoom_seq", sequenceName = "practiceRoom_seq", allocationSize = 1)
	private int practiceRoom_id;
	
	private String practiceRoom_address;
	private int practiceRoom_maxPersonnel;
	
	private double practiceRoom_price;
	
}
