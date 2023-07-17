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
public class Recharge {
	
	public void ChangeWon() {
		this.changeWon = this.pay * 10000 + (this.pay * 10000 * 0.1);
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rechargeOrder_seq")
    @SequenceGenerator(name = "rechargeOrder_seq", sequenceName = "rechargeOrder_seq", allocationSize = 1)
	private int rechargeOrderNum;
	
	private int user_id;
	private double pay;
	private String paymentMethod;
	private double changeWon;
	
    @CreationTimestamp
    private Timestamp createDate;
	
}
