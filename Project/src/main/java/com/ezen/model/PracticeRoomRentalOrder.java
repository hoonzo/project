package com.ezen.model;

import java.sql.Timestamp; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class PracticeRoomRentalOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "practiceRoomOrder_seq")
	@SequenceGenerator(name = "practiceRoomOrder_seq", sequenceName = "practiceRoomOrder_seq", allocationSize = 1)
	private int practiceRoomOrder_id;
	
	private int practiceRoom_id;
	private int lector_num;
	
	private LocalDate rentalDate;
	private String rentalStartTime;
	private String rentalEndTime;
	
	private String cancel_check = "X";
	
	@CreationTimestamp
	private Timestamp createTime;
	
	
	
	public void setRentalDate(String RentalDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.rentalDate = LocalDate.parse(RentalDate, formatter);
    }
}
