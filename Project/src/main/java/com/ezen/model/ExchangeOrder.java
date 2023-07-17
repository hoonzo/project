package com.ezen.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOrder {
	
	public void ChangeWon() {
		
		if(membership.equals("X")) {
			
			this.changeWon = this.rechange_amount * 10000 - (this.rechange_amount * 10000 * 0.1);
		
		} else {
			this.changeWon = this.rechange_amount * 10000 - (this.rechange_amount * 10000 * 0.08);
		}
		
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchangeOrder_seq")
    @SequenceGenerator(name = "exchangeOrder_seq", sequenceName = "exchangeOrder_seq", allocationSize = 1)
	private int exchangeOrder_num;
	
	private int lector_num;
	private double rechange_amount;
	
	private double changeWon;
	
	private String bank;
	private String account;
	private String membership;
	
    @CreationTimestamp
    private Timestamp createDate;
	
}
